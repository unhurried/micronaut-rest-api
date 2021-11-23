plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.21"
    id("org.jetbrains.kotlin.kapt") version "1.5.21"
    id("com.github.johnrengelman.shadow") version "7.1.0"
    id("io.micronaut.application") version "2.0.8"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.5.21"
}

version = "0.1"
group = "io.github.unhurried.micronautrestapi"

val kotlinVersion=project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("io.github.unhurried.*")
    }
}

dependencies {
    kapt("io.micronaut:micronaut-http-validation")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    runtimeOnly("ch.qos.logback:logback-classic")
    implementation("io.micronaut:micronaut-validation")

    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Micronaut Data
    runtimeOnly("com.h2database:h2")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    kapt("io.micronaut.data:micronaut-data-processor")
    implementation("io.micronaut.data:micronaut-data-hibernate-jpa")

    // Micronaut JAX-RS
    annotationProcessor("io.micronaut.jaxrs:micronaut-jaxrs-processor")
    implementation("io.micronaut.jaxrs:micronaut-jaxrs-server")

    // Bean Introspection
    kapt("io.micronaut:micronaut-inject-java")
    runtimeOnly("io.micronaut:micronaut-core")
}

application {
    mainClass.set("io.github.unhurried.micronautrestapi.ApplicationKt")
}
java {
    sourceCompatibility = JavaVersion.toVersion("11")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}
