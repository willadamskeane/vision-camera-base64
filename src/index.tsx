import { VisionCameraProxy, type Frame } from 'react-native-vision-camera';

const _frameToBase64 = VisionCameraProxy.getFrameProcessorPlugin('frameToBase64');

export function frameToBase64(frame: Frame): object {
  'worklet';

  if (_frameToBase64 == null) {
    throw new Error('Failed to load Frame Processor Plugin "frameToBase64"!');
  }

  // @ts-ignore
  return _frameToBase64?.call(frame);
}
