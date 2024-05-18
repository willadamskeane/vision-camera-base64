package com.visioncamerabase64plugin;

// import androidx.annotation.NonNull;
// import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import com.mrousavy.camera.frameprocessors.Frame;
import com.mrousavy.camera.frameprocessors.FrameProcessorPlugin;
import com.mrousavy.camera.frameprocessors.VisionCameraProxy;

import java.util.Map;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.media.Image;
import android.annotation.SuppressLint;


public class VisionCameraBase64Plugin extends FrameProcessorPlugin {
  @SuppressLint("NewApi")
  @Nullable
  @Override
  public Object callback(@NotNull Frame frame, @Nullable Map<String, Object> params) {
    // TODO: image format and quality must come from params
    return BitmapUtils.convertYuvToRgba(frame.getImage());
  }

  VisionCameraBase64Plugin(VisionCameraProxy proxy, @Nullable Map<String, Object> options) {
    super();
  }
}
