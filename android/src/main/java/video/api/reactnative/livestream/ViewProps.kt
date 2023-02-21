package video.api.reactnative.livestream

import com.facebook.react.common.MapBuilder

object ViewProps {
  // React props
  const val AUDIO_CONFIG = "audio"
  const val VIDEO_CONFIG = "video"
  const val IS_MUTED = "isMuted"
  const val CAMERA = "camera"
  const val ZOOM_RATIO = "zoomRatio"
  const val NATIVE_ZOOM_ENABLED = "enablePinchedZoom"

  // Audio and video configurations
  const val BITRATE = "bitrate"
  const val RESOLUTION = "resolution"
  const val FPS = "fps"
  const val GOP_DURATION = "gopDuration"
  const val SAMPLE_RATE = "sampleRate"
  const val IS_STEREO = "isStereo"

  enum class Commands(val action: String) {
    START_STREAMING("startStreamingFromManager"),
    STOP_STREAMING("stopStreamingFromManager"),
    ZOOM_RATIO("zoomRatioFromManager"),
    TOGGLE_FLASH("toggleFlashFromManager");

    companion object {
      fun toCommandsMap(): Map<String, Int> {
        return values().associate { it.action to it.ordinal }
      }
    }
  }

  enum class Events(val type: String) {
    ON_START_STREAMING("onStartStreaming"),
    CONNECTION_SUCCESS("onConnectionSuccess"),
    CONNECTION_FAILED("onConnectionFailed"),
    DISCONNECTED("onDisconnect");

    companion object {
      fun toEventsMap(): Map<String, *> {
        val builder: MapBuilder.Builder<String, Map<String, String>> = MapBuilder.builder()
        values().forEach {
          builder.put(it.type, MapBuilder.of("registrationName", it.type))
        }
        return builder.build()
      }
    }
  }
}
