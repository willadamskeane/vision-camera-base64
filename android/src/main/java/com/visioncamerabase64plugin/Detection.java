package com.visioncamerabase64plugin;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class Detection {

    private static final int MODEL_SIZE = 192;

    private static final double quantScale = 0.005039982497692108;
    private static final int quantZeroPoint = -103;

    public static double[][] transposeOutput(ArrayList<Double> output, int modelWidth, int modelHeight) {

        int numDetections = output.size() / 6;
        double[][] transposedOutput = new double[numDetections][5];

        for (int i = 0; i < numDetections; i++) {
            int pos = i * 6;
            transposedOutput[i][0] = transform(output.get(pos));
            transposedOutput[i][1] = transform(output.get(pos + 1));
            transposedOutput[i][2] = transform(output.get(pos + 2));
            transposedOutput[i][3] = transform(output.get(pos + 3));
            transposedOutput[i][4] = transform(output.get(pos + 4));
        }

        return transposedOutput;
    }

    private static double transform(double x) {
        return (x - quantZeroPoint) * quantScale;
    }

    public static List<Box> decodeDetections(double[][] transposedOutput) {
        List<Box> boxes = new ArrayList<>();

        for (double[] det : transposedOutput) {
            double x1 = det[0];
            double y1 = det[1];
            double x2 = det[2];
            double y2 = det[3];
            double width = x2 - x1;
            double height = y2 - y1;
            double score = det[4];
            boxes.add(new Box(x1, y1, width, height, score));
        }

        return boxes;
    }

    public static double iou(Box box1, Box box2) {
        double x1 = Math.max(box1.x1, box2.x1);
        double y1 = Math.max(box1.y1, box2.y1);
        double x2 = Math.min(box1.x1 + box1.width, box2.x1 + box2.width);
        double y2 = Math.min(box1.y1 + box1.height, box2.y1 + box2.height);
        double intersectionArea = Math.max(0, x2 - x1) * Math.max(0, y2 - y1);
        double box1Area = box1.width * box1.height;
        double box2Area = box2.width * box2.height;
        return intersectionArea / (box1Area + box2Area - intersectionArea);
    }

    public static List<Box> performNMS(List<Box> boxes, int maxDetections, double iouThreshold, double scoreThreshold) {
        List<Box> result = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        for (int i = 0; i < boxes.size(); i++) {
            if (boxes.get(i).score < scoreThreshold)
                continue;
            boolean keep = true;
            for (int j : indices) {
                if (iou(boxes.get(i), boxes.get(j)) > iouThreshold) {
                    keep = false;
                    break;
                }
            }
            if (keep)
                indices.add(i);
        }

        for (int i = 0; i < Math.min(indices.size(), maxDetections); i++) {
            result.add(boxes.get(indices.get(i)));
        }

        return result;
    }

    public static List<Box> detect(ArrayList<Double> output, int frameWidth, int frameHeight) {
        int modelWidth = MODEL_SIZE;
        int modelHeight = MODEL_SIZE;

        double[][] transposedOutput = transposeOutput(output, modelWidth, modelHeight);
        List<Box> decodedBoxes = decodeDetections(transposedOutput);
        // multiply by frameWidth and frameHeight

        List<Box> filteredBoxes = new ArrayList<>();
        for (Box box : decodedBoxes) {
            if (box.score > 0.1) {
                box.x1 *= frameWidth;
                box.y1 *= frameHeight;
                box.width *= frameWidth;
                box.height *= frameHeight;
                filteredBoxes.add(box);
            }
        }
        decodedBoxes = filteredBoxes;

        return decodedBoxes;
        // return performNMS(decodedBoxes, 100, 0.2, 0.0);

    }

    public static class Box {
        double x1;
        double y1;
        double width;
        double height;
        double score;

        public Box(double x1, double y1, double width, double height, double score) {
            this.x1 = x1;
            this.y1 = y1;
            this.width = width;
            this.height = height;
            this.score = score;
        }

        @Override
        public String toString() {
            return "Box{" +
                    "x1=" + x1 +
                    ", y1=" + y1 +
                    ", width=" + width +
                    ", height=" + height +
                    ", score=" + score +
                    '}';
        }
    }
}
