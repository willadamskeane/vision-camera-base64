package com.visioncamerabase64plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import android.util.Log;
// import org.opencv.android.OpenCVLoader;

public class Detection {

    private static final int MODEL_SIZE = 192;
    private static final double quantScale = 0.0040843975730240345;
    private static final int quantZeroPoint = -122;

    // private static ByteTrackJNI trackerJNI = new ByteTrackJNI();
    
    static {
        // if (!OpenCVLoader.initDebug()) {
        //     Log.e("Detection", "OpenCV initialization failed");
        // } else {
        //     Log.d("Detection", "OpenCV initialization succeeded");
        // }

        // Initialize the tracker with default parameters
        // track_params.a1 = paramArray[0];
        // track_params.a2 = paramArray[1];
        // track_params.wx = paramArray[2];
        // track_params.wy = paramArray[3];
        // track_params.vmax = paramArray[4];
        // track_params.max_age = paramArray[5];
        // track_params.high_score = paramArray[6];
        // track_params.conf_threshold = paramArray[7];
        // track_params.dt = paramArray[8];
        // track_params.Ki = std::vector<double>(paramArray + 9, paramArray + 13);
        // track_params.Ko = std::vector<double>(paramArray + 13, paramArray + 17);


        // trackerJNI.initTracker(new double[] {0.25, 30, 0.8, 30});
    }

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

    public static List<Box> detect(ArrayList<Double> output, int frameWidth, int frameHeight) {
        int modelWidth = MODEL_SIZE;
        int modelHeight = MODEL_SIZE;

        double[][] transposedOutput = transposeOutput(output, modelWidth, modelHeight);
        List<Box> decodedBoxes = decodeDetections(transposedOutput);

        // Multiply by frameWidth and frameHeight
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

        // Prepare detections for JNI tracker
        double[] detections = new double[decodedBoxes.size() * 6];
        for (int i = 0; i < decodedBoxes.size(); i++) {
            Box box = decodedBoxes.get(i);
            detections[i * 6] = box.x1;
            detections[i * 6 + 1] = box.y1;
            detections[i * 6 + 2] = box.width;
            detections[i * 6 + 3] = box.height;
            detections[i * 6 + 4] = box.score;
            detections[i * 6 + 5] = box.id;
        }


        // Update tracker and get updated boxes
        // double[] trackedObjects = trackerJNI.updateTracker(detections, frameWidth, frameHeight);

        // Convert trackedObjects to Box list
        List<Box> result = new ArrayList<>();
        // for (int i = 0; i < trackedObjects.length; i += 6) {
        //     result.add(new Box(
        //         trackedObjects[i], 
        //         trackedObjects[i + 1], 
        //         trackedObjects[i + 2], 
        //         trackedObjects[i + 3], 
        //         trackedObjects[i + 4], 
        //         (int) trackedObjects[i + 5]
        //     ));
        // }

        return result;
    }

    public static class Box {
        double x1;
        double y1;
        double width;
        double height;
        double score;
        int id;

        public Box(double x1, double y1, double width, double height, double score) {
            this.x1 = x1;
            this.y1 = y1;
            this.width = width;
            this.height = height;
            this.score = score;
        }

        public Box(double x1, double y1, double width, double height, double score, int id) {
            this(x1, y1, width, height, score);
            this.id = id;
        }

        @Override
        public String toString() {
            return "Box{" +
                    "x1=" + x1 +
                    ", y1=" + y1 +
                    ", width=" + width +
                    ", height=" + height +
                    ", score=" + score +
                    ", id=" + id +
                    '}';
        }
    }
}
