import { VisionCameraProxy, type Frame } from 'react-native-vision-camera';

const plugin = VisionCameraProxy.initFrameProcessorPlugin('frameToBase64');

export function toBase64(frame: Frame): object {
  'worklet';

  if (plugin == null) {
    throw new Error('Failed to load Frame Processor Plugin "frameToBase64"!');
  }

  // @ts-ignore
  return plugin?.call(frame);
}
