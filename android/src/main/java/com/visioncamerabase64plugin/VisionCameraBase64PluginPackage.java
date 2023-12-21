package com.visioncamerabase64plugin;

import androidx.annotation.NonNull;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.mrousavy.camera.frameprocessor.FrameProcessorPlugin;
import com.mrousavy.camera.frameprocessor.FrameProcessorPluginRegistry;
import com.visioncamerabase64.VisionCameraBase64Plugin;

import java.util.Collections;
import java.util.List;

public class VisionCameraBase64PluginPackage implements ReactPackage {
  static {
    FrameProcessorPluginRegistry.addFrameProcessorPlugin(
            "frameToBase64",
            options -> new VisionCameraBase64Plugin(options)
    );
  }

  @NonNull
  @Override
  public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
    return Collections.emptyList();
  }

  @NonNull
  @Override
  public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
    return Collections.emptyList();
  }
}

