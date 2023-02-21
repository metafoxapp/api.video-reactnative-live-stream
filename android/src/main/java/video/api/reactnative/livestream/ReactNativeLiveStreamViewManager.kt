package video.api.reactnative.livestream

import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import video.api.reactnative.livestream.utils.getCameraFacing
import video.api.reactnative.livestream.utils.toAudioConfig
import video.api.reactnative.livestream.utils.toVideoConfig


class ReactNativeLiveStreamViewManager : SimpleViewManager<ReactNativeLiveStreamView>() {
  override fun getName() = "ReactNativeLiveStreamView"

  override fun createViewInstance(reactContext: ThemedReactContext): ReactNativeLiveStreamView {
    return ReactNativeLiveStreamView(reactContext)
  }

  override fun receiveCommand(
    view: ReactNativeLiveStreamView,
    commandId: Int,
    args: ReadableArray?
  ) {
    super.receiveCommand(view, commandId, args)

    when (commandId) {
      ViewProps.Commands.START_STREAMING.ordinal -> {
        val requestId = args!!.getInt(0)
        val streamKey = args.getString(1)
        val url = try {
          args.getString(2)
        } catch (e: Exception) {
          null
        }
        view.startStreaming(requestId, streamKey, url)
      }
      ViewProps.Commands.STOP_STREAMING.ordinal -> view.stopStreaming()

      ViewProps.Commands.ZOOM_RATIO.ordinal -> {
        val zoomRatio = args!!.getDouble(0)
        view.zoomRatio = zoomRatio.toFloat()
      }

      ViewProps.Commands.TOGGLE_FLASH.ordinal -> {
        val enable = args!!.getBoolean(0)
        view.isFlashMode = enable;
      }
      else -> {
        throw IllegalArgumentException("Unsupported command %d received by %s. $commandId")
      }
    }
  }

  override fun getCommandsMap(): Map<String, Int> {
    return ViewProps.Commands.toCommandsMap()
  }

  override fun getExportedCustomDirectEventTypeConstants(): Map<String, *> {
    return ViewProps.Events.toEventsMap()
  }

  @ReactProp(name = ViewProps.VIDEO_CONFIG)
  fun setVideoConfig(view: ReactNativeLiveStreamView, videoMap: ReadableMap) {
    if (view.isStreaming) {
      view.videoBitrate = videoMap.getInt(ViewProps.BITRATE)
    } else {
      view.videoConfig = videoMap.toVideoConfig()
    }
  }

  @ReactProp(name = ViewProps.AUDIO_CONFIG)
  fun setAudioConfig(view: ReactNativeLiveStreamView, audioMap: ReadableMap) {
    view.audioConfig = audioMap.toAudioConfig()
  }

  @ReactProp(name = ViewProps.CAMERA)
  fun setCamera(view: ReactNativeLiveStreamView, newVideoCameraString: String) {
    view.camera = newVideoCameraString.getCameraFacing()
  }

  @ReactProp(name = ViewProps.IS_MUTED)
  fun isMuted(view: ReactNativeLiveStreamView, isMuted: Boolean) {
    view.isMuted = isMuted
  }

  @ReactProp(name = ViewProps.NATIVE_ZOOM_ENABLED)
  fun enablePinchedZoom(view: ReactNativeLiveStreamView, enablePinchedZoom: Boolean) {
    view.enablePinchedZoom = enablePinchedZoom
  }

  @ReactProp(name = ViewProps.ZOOM_RATIO)
  fun zoomRatio(view: ReactNativeLiveStreamView, zoomRatio: Double) {
    view.zoomRatio = zoomRatio.toFloat()
  }

  override fun onDropViewInstance(view: ReactNativeLiveStreamView) {
    super.onDropViewInstance(view)
    view.close()
  }
}
