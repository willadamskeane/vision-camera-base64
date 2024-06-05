#include "ByteTrackJNI.h"
#include "BYTETracker.h" // Include the correct ByteTrack header file

JNIEXPORT void JNICALL Java_com_visioncamerabase64plugin_ByteTrackJNI_initTracker(JNIEnv *env, jobject obj, jdoubleArray params){
    jsize length = env->GetArrayLength(params);
    jdouble *paramArray = env->GetDoubleArrayElements(params, 0);

    // Call the init method with the parameters
    BYTETracker::getInstance().init(paramArray);

    env->ReleaseDoubleArrayElements(params, paramArray, 0);
}

JNIEXPORT jdoubleArray JNICALL Java_com_visioncamerabase64plugin_ByteTrackJNI_updateTracker(JNIEnv *env, jobject obj, jdoubleArray detections, jint frameWidth, jint frameHeight)
{
    jsize length = env->GetArrayLength(detections);
    jdouble *detectionArray = env->GetDoubleArrayElements(detections, 0);

    // Update the ByteTrack tracker with the provided detections
    Eigen::MatrixXf detections_matrix = Eigen::Map<Eigen::Matrix<double, Eigen::Dynamic, Eigen::Dynamic, Eigen::RowMajor>>(detectionArray, length / 6, 6).cast<float>();
    std::vector<KalmanBBoxTrack> trackedObjects = BYTETracker::getInstance().process_frame_detections(detections_matrix);

    env->ReleaseDoubleArrayElements(detections, detectionArray, 0);

    // Convert the tracked objects to a jdoubleArray
    jdoubleArray result = env->NewDoubleArray(trackedObjects.size() * 6);
    for (size_t i = 0; i < trackedObjects.size(); ++i)
    {
        const auto &track = trackedObjects[i];
        const auto &track = trackedObjects[i];
        env->SetDoubleArrayRegion(result, i * 6, 6, track.tlwh().data());
    }

    return result;
}
