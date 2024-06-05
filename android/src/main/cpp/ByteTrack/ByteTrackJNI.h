#pragma once

#include <jni.h>
#include "BYTETracker.h" // Include the correct ByteTrack header file

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL Java_com_visioncamerabase64plugin_ByteTrackJNI_initTracker(JNIEnv *, jobject, jdoubleArray);
JNIEXPORT jdoubleArray JNICALL Java_com_visioncamerabase64plugin_ByteTrackJNI_updateTracker(JNIEnv *, jobject, jdoubleArray, jint, jint);

#ifdef __cplusplus
}
#endif
