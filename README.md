# vision-camera-base64-v3

A simple VisionCamera FrameProcessor plugin. 
Convert the frame to base64 string format.

__Note:__ This plugin is only string and not include `data:image/blabla/;base64,`

## Installation

```sh
yarn add vision-camera-base64-v3
```

## Usage

```js
import { toBase64 } from 'vision-camera-base64-v3';

// ...
const process = useFrameProcessor((frame) => {
    'worklet'
    const imageAsBase64 = toBase64(frame)
}, [])
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob) 💜
