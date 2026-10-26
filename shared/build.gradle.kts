import java.util.Properties
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room3)
    alias(libs.plugins.wire)
    alias(libs.plugins.detekt)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-opt-in=androidx.compose.material3.ExperimentalMaterial3Api")
        // Las clases expect/actual (AppDatabaseConstructor, LogScheduler) siguen en beta: quita el aviso.
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }

    android {
       namespace = "org.ucb.appp1.shared"
       compileSdk = libs.versions.android.compileSdk.get().toInt()
       minSdk = libs.versions.android.minSdk.get().toInt()

       compilerOptions {
           jvmTarget = JvmTarget.JVM_17
       }
       androidResources {
           enable = true
       }
       withHostTest {
           isIncludeAndroidResources = true
       }
       withDeviceTestBuilder {
           sourceSetTreeName = "test"
       }.configure {
           instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
       }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.compose.uiTooling)
            implementation(libs.koin.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.androidx.room3.sqlite.wrapper)
            implementation(libs.androidx.work.runtime.ktx)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.navigation.compose)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor3)
            implementation(libs.androidx.room3.runtime)
            implementation(libs.androidx.sqlite.bundled)
            implementation(libs.firebase.database)
            implementation(libs.firebase.config)
            implementation(libs.firebase.firestore)
            implementation(libs.firebase.messaging)
            implementation("edu.ucb.ds:shared:1.0.0-SNAPSHOT")
            implementation(libs.wire.grpc.client)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
    add("kspAndroid", libs.androidx.room3.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room3.compiler)
    add("kspIosArm64", libs.androidx.room3.compiler)
}

room3 {
    schemaDirectory("$projectDir/schemas")
}

apply(from = "loco.gradle.kts")

wire {
    sourcePath {
        srcDir("src/commonMain/proto")
    }
    kotlin {
        rpcRole = "client"
    }
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    ignoreFailures = false
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    source.setFrom(
        "src/commonMain/kotlin",
        "src/androidMain/kotlin",
        "src/iosMain/kotlin",
        "src/commonTest/kotlin",
    )
}

// =====================================================================
// API keys fuera del código. Se leen de:
//   1) local.properties  ->  tmdb.apiKey=TU_API_KEY      (este archivo NO se sube a Git)
//   2) variable de entorno TMDB_API_KEY
// y se genera build/generated/secrets/.../Secrets.kt, que se compila junto con commonMain.
// =====================================================================
abstract class GenerateSecrets : DefaultTask() {

    @get:Input
    abstract val tmdbApiKey: Property<String>

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @TaskAction
    fun generate() {
        if (tmdbApiKey.get().isBlank()) {
            logger.warn("tmdb.apiKey no está en local.properties ni en TMDB_API_KEY: el catálogo no cargará.")
        }
        val file = outputDir.file("org/ucb/appp1/core/config/Secrets.kt").get().asFile
        file.parentFile.mkdirs()
        file.writeText(
            """
            |package org.ucb.appp1.core.config
            |
            |// Archivo generado por la tarea generateSecrets. No editar.
            |internal object Secrets {
            |    const val TMDB_API_KEY = "${tmdbApiKey.get()}"
            |}
            |""".trimMargin()
        )
    }
}

val localProperties = providers
    .fileContents(rootProject.layout.projectDirectory.file("local.properties"))
    .asText
    .map { text -> Properties().apply { load(text.reader()) } }

val generateSecrets = tasks.register<GenerateSecrets>("generateSecrets") {
    tmdbApiKey.set(
        localProperties.map { it.getProperty("tmdb.apiKey") }
            .orElse(providers.environmentVariable("TMDB_API_KEY"))
            .orElse("")
    )
    outputDir.set(layout.buildDirectory.dir("generated/secrets/kotlin"))
}

kotlin.sourceSets.commonMain {
    kotlin.srcDir(generateSecrets.map { it.outputDir })
}
