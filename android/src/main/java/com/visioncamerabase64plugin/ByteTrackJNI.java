package com.visioncamerabase64plugin;

public class ByteTrackJNI {
    static {
        System.loadLibrary("ByteTrackJNI");
    }

    public native void initTracker(double[] params);
    public native double[] updateTracker(double[] detections, int frameWidth, int frameHeight);
}
