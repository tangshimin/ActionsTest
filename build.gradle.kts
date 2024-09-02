import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("net.bramp.ffmpeg:ffmpeg:0.7.0")

    implementation("junit:junit:4.13.2")
    testImplementation(compose.desktop.uiTestJUnit4)
}

compose.desktop {
    application {
        mainClass = "MainKt"
        jvmArgs += listOf("-Dfile.encoding=UTF-8")
        jvmArgs += listOf("-Dstdout.encoding=UTF-8")
        jvmArgs += listOf("-Dstderr.encoding=UTF-8")
        jvmArgs += listOf("-Dsun.stdout.encoding=UTF-8")
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "记事本"
            packageVersion = "1.0.0"
            vendor = "深圳市龙华区幕境网络工作室"
            licenseFile.set(project.file("LICENSE"))
            windows{
//                console = true
                dirChooser = true
                menuGroup = "幕境"
            }
        }
    }
}
