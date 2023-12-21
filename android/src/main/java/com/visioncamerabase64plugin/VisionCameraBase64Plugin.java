package com.visioncamerabase64plugin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.mrousavy.camera.frameprocessor.Frame;
import com.mrousavy.camera.frameprocessor.FrameProcessorPlugin;
import java.util.Map;

import android.media.Image;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Base64;
import androidx.camera.core.ImageProxy;
import java.io.ByteArrayOutputStream;


public class VisionCameraBase64Plugin extends FrameProcessorPlugin {
  @SuppressLint("NewApi")
  @Nullable
  @Override
  public Object callback(@NonNull Frame frame, @Nullable Map<String, Object> arguments) {
    // TODO: image format and quality must come from params
    Bitmap.CompressFormat imageFormat = Bitmap.CompressFormat.PNG;
    int quality = 100;

    Bitmap bitmap = BitmapUtils.getBitmap(frame);
    return bitmapToBase64(bitmap, imageFormat, quality);
  }

  /** Converts a bitmap to base64 format string */
  public static String bitmapToBase64(Bitmap bitmap, Bitmap.CompressFormat format, int quality)
  {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    bitmap.compress(format, quality, outputStream);

    return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
  }

  VisionCameraBase64Plugin(@Nullable Map<String, Object> options) {
    super(options);
  }
}