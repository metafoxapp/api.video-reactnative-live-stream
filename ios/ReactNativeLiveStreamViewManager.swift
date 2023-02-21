
import CoreGraphics
import Foundation

@objc(ReactNativeLiveStreamViewManager)
class ReactNativeLiveStreamViewManager: RCTViewManager {
    override static func requiresMainQueueSetup() -> Bool {
        return true
    }

    override func view() -> (ReactNativeLiveStreamView) {
        return ReactNativeLiveStreamView()
    }

    @objc func startStreamingFromManager(_ node: NSNumber, withRequestId requestId: NSNumber, withStreamKey streamKey: String, withUrl url: String?) {
        DispatchQueue.main.async {
            let component = self.bridge.uiManager.view(
                forReactTag: node
            ) as! ReactNativeLiveStreamView
            component.startStreaming(requestId: Int(truncating: requestId), streamKey: streamKey, url: url)
        }
    }

    @objc func stopStreamingFromManager(_ node: NSNumber) {
        DispatchQueue.main.async {
            let component = self.bridge.uiManager.view(
                forReactTag: node
            ) as! ReactNativeLiveStreamView
            component.stopStreaming()
        }
    }

    @objc func toggleFlashFromManager(_ node: NSNumber, value newValue: Bool) {
        DispatchQueue.main.async {
            let component = self.bridge.uiManager.view(
                forReactTag: node
            ) as! ReactNativeLiveStreamView
            component.toggleFlash(newValue: newValue)
        }
    }

    @objc func zoomRatioFromManager(_ node: NSNumber, withZoomRatio zoomRatio: NSNumber) {
        DispatchQueue.main.async {
            let component = self.bridge.uiManager.view(
                forReactTag: node
            ) as! ReactNativeLiveStreamView
            component.setZoomRatio(zoomRatio: CGFloat(zoomRatio))
        }
    }
}
