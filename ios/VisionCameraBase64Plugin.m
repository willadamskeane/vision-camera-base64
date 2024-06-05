#import <Foundation/Foundation.h>
#import "VisionCameraBase64Plugin.h"

#import "VisionCameraBase64Plugin-Swift.h"

@implementation Plugins

    + (void) load {
        [FrameProcessorPluginRegistry addFrameProcessorPlugin:@"frameToBase64"
                                              withInitializer:^FrameProcessorPlugin*(NSDictionary* options) {
            return [[VisionCameraBase64Plugin alloc] init];
        }];
    }

@end
