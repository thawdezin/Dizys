import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.thawdezin"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("androidx.compose.ui:ui:1.1.1")
                implementation("androidx.compose.ui:ui-tooling:1.1.1")
                implementation("androidx.compose.material:material:1.1.1")
                implementation("androidx.compose.runtime:runtime-livedata:1.1.1")
                implementation("androidx.compose.ui:ui-tooling:1.1.1")

                implementation("io.ktor:ktor-client-core:2.3.4")
                implementation("io.ktor:ktor-client-json:2.3.4")
                implementation("io.ktor:ktor-client-serialization:2.3.4")
                implementation("io.ktor:ktor-client-android:2.3.4")

                implementation("io.ktor:ktor-client-core:1.6.5")
                implementation("io.ktor:ktor-client-cio:1.6.5")

                implementation("io.github.microutils:kotlin-logging:2.0.6")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.3.0")

                implementation("io.ktor:ktor-client-core:2.3.4")
            }
        }
        val jvmTest by getting
    }
}


compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Dizys"
            packageVersion = "1.0.0"
        }


    }

}

