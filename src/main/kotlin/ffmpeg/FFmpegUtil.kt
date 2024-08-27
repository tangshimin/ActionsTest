package ffmpeg

import java.io.File
import java.util.*


fun findFFmpegPath(): String {
    val path: String
    if(isWindows()){
        path = getResourcesFile("ffmpeg/ffmpeg.exe").absolutePath
    }else{

        path =  getResourcesFile("ffmpeg/ffmpeg").absolutePath
        if(!File(path).exists()){
            println("ffmpeg not unzipped")
            val zipFile = getResourcesFile("ffmpeg/ffmpeg.zip")
            // 必须使用系统命令行解压缩，否则会报错
            val process = Runtime.getRuntime().exec("unzip -o ${zipFile.absolutePath} -d ${zipFile.parentFile.absolutePath}")
            process.waitFor()
            if (!process.isAlive){
                println("unzip ffmpeg success")
                val property = "compose.application.resources.dir"
                val dir = System.getProperty(property)
                //打包之后的环境，幕境已经安装到 macOS 了。
                if (dir != null) {
                    zipFile.delete()
                    println("delete ffmpeg.zip file")
                }

            }


        }


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
