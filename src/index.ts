<<<<<<< HEAD
import { VisionCameraProxy, type Frame } from "react-native-vision-camera";

const plugin = VisionCameraProxy.initFrameProcessorPlugin("frameToBase64");

export function toBase64(frame: Frame, params: object): object {
  "worklet";
=======
import { VisionCameraProxy, type Frame } from 'react-native-vision-camera';

const plugin = VisionCameraProxy.initFrameProcessorPlugin('frameToBase64');

export function toBase64(frame: Frame): object {
  'worklet';
>>>>>>> 00b7aa5 (merge)

  if (plugin == null) {
    throw new Error('Failed to load Frame Processor Plugin "frameToBase64"!');
  }

  // @ts-ignore
<<<<<<< HEAD
  return plugin?.call(frame, params);
=======
  return plugin?.call(frame);
>>>>>>> 00b7aa5 (merge)
}
