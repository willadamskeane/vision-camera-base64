package com.visioncamerabase64plugin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.mrousavy.camera.frameprocessor.Frame;
import com.mrousavy.camera.frameprocessor.FrameProcessorPlugin;
import java.util.Map;

import android.media.Image;
import android.annotation.SuppressLint;


public class VisionCameraBase64Plugin extends FrameProcessorPlugin {
  @SuppressLint("NewApi")
  @Nullable
  @Override
  public Object callback(@NonNull Frame frame, @Nullable Map<String, Object> arguments) {
    // TODO: image format and quality must come from params
    return BitmapUtils.convertYuvToRgba(frame.getImage());
  }

  VisionCameraBase64Plugin(@Nullable Map<String, Object> options) {
    super(options);
  }
}