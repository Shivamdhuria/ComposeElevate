import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

}

group = "io.github.shivamdhuria.elevate"
version = "0.0.5"

kotlin {
    jvm()
    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                implementation(libs.kotlinx.collections.immutable)

                //put your multiplatform dependencies here
            }
        }
    }
}

android {
    namespace = "io.github.shivamdhuria.android"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(group.toString(), "library", version.toString())

    pom {
        name = "Compose Elevate"
        description = "A library."
        inceptionYear = "2025"
        url = "https://github.com/Shivamdhuria/ComposeElevate"
        licenses {
            license {

                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "https://www.apache.org/licenses/LICENSE-2.0.txt"

            }
        }
        developers {
            developer {
                id = "Shivamdhuria"
                name = "Shivam Dhuria"
                url = "https://github.com/Shivamdhuria"
            }
        }
        scm {
            url = "https://github.com/Shivamdhuria/ComposeElevate"
            connection = "scm:git:git://github.com/Shivamdhuria/ComposeElevate.git"
            developerConnection = "scm:git:ssh://github.com/Shivamdhuria/ComposeElevate.git"
        }
    }
}

