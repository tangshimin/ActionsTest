package ffmpeg

import net.bramp.ffmpeg.FFmpeg
import java.io.File
import java.util.*

fun main() {
    val fFmpeg = FFmpeg(findFFmpegPath())
    println("ffmpeg exists:"+fFmpeg.isFFmpeg)
}

fun findFFmpegPath(): String {
    val path: String = if(isWindows()){
        getResourcesFile("ffmpeg/ffmpeg.exe").absolutePath
    }else{
        getResourcesFile("ffmpeg/ffmpeg").absolutePath
    }
    return path
}




/**
 * 获得资源文件
 * @param path 文件路径
 */
fun getResourcesFile(path: String): File {
    val file = if (File(path).isAbsolute) {
        File(path)
    } else {
        composeAppResource(path)
    }
    return file
}


fun composeAppResource(path: String): File {
    val property = "compose.application.resources.dir"
    val dir = System.getProperty(property)
    return if (dir != null) {
        //打包之后的环境
        File(dir).resolve(path)
    } else {// 开发环境
        // 通用资源
        var file = File("resources/common/$path")
        // window 操作系统专用资源
        if (!file.exists()) {
            file = File("resources/windows/$path")
        }
        // macOS 操作系统专用资源
        if (!file.exists() && isMacOS()) {
            val arch = System.getProperty("os.arch").lowercase()
            file = if (arch == "arm" || arch == "aarch64") {
                File("resources/macos-arm64/$path")
            }else {
                File("resources/macos-x64/$path")
            }
        }
        file
    }
}


fun isMacOS(): Boolean {
    val os = System.getProperty("os.name", "generic").lowercase(Locale.ENGLISH)
    return os.indexOf("mac") >= 0 || os.indexOf("darwin") >= 0
}

fun isWindows(): Boolean {
    val os = System.getProperty("os.name", "generic").lowercase(Locale.ENGLISH)
    return os.indexOf("windows") >= 0
}
