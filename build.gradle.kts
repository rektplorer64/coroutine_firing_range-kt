import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    application
}
group = "me.ps.63"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}
dependencies {
    testImplementation(kotlin("test-junit"))

    implementation("io.reactivex.rxjava3:rxkotlin:3.0.1")
    implementation("io.reactivex.rxjava3:rxjava:3.0.12")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
}
tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}
application {
    mainClassName = "MainKt"
}

