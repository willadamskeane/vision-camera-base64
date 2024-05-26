package com.visioncamerabase64plugin;

import com.mrousavy.camera.frameprocessors.Frame;
import com.mrousavy.camera.frameprocessors.FrameProcessorPlugin;
import com.mrousavy.camera.frameprocessors.VisionCameraProxy;
import com.mrousavy.camera.core.FrameInvalidError;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.util.Log;

import com.visioncamerabase64plugin.Detection;

public class VisionCameraBase64Plugin extends FrameProcessorPlugin {
    @SuppressLint("NewApi")
    @Nullable
    @Override
    public Object callback(@NotNull Frame frame, @Nullable Map<String, Object> params) throws FrameInvalidError {
        // Extract the Map data from the params
        if (params == null || !params.containsKey("output")) {
            throw new FrameInvalidError();
        }

        @SuppressWarnings("unchecked")
        HashMap<?, ?> outputMap = (HashMap<?, ?>) ((ArrayList<?>) params.get("output")).get(0);
        Log.d("outputMap", outputMap.toString());

        // Assuming the frame format is YUV_420_888 and the data needs to be converted to a byte array for processing
        int frameWidth = ((Double) params.get("width")).intValue();
        int frameHeight = ((Double) params.get("height")).intValue();

        // Convert the Map to an ArrayList<Double> by sorting the keys first
        ArrayList<Double> output = new ArrayList<>(outputMap.size());
        outputMap.keySet().stream()
            .map(key -> Integer.parseInt((String) key))
            .sorted()
            .forEach(index -> output.add((Double) outputMap.get(String.valueOf(index))));
        // Perform detection
        List<Detection.Box> detections = Detection.detect(output, frameWidth, frameHeight);

        // Convert detections to a format suitable for return
        List<Map<String, Object>> detectionResults = new ArrayList<>();
        for (Detection.Box box : detections) {
            Map<String, Object> result = new HashMap<>();
            result.put("x", box.x1);
            result.put("y", box.y1);
            result.put("width", box.width);
            result.put("height", box.height);
            result.put("score", box.score);
            detectionResults.add(result);
        }

        return detectionResults;
    }

    VisionCameraBase64Plugin(VisionCameraProxy proxy, @Nullable Map<String, Object> options) {
        super();
    }
}
