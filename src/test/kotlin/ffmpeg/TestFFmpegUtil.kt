package ffmpeg

import net.bramp.ffmpeg.FFmpeg
import org.junit.Test

class TestFFmpegUtil {

    @Test
    fun `Test isFFmpeg`() {
        val fFmpeg = FFmpeg(findFFmpegPath())
        assert(fFmpeg.isFFmpeg)
    }

}