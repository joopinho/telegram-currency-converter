import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.8"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
}

group = "com.jjj"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

val jaxb by configurations.creating

configurations {
    implementation.configure {
        exclude(module = "spring-boot-starter-tomcat")
        exclude("org.apache.tomcat")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    implementation("org.springframework.ws:spring-ws-core")
    implementation("org.glassfish.jaxb:jaxb-runtime:2.3.5")
    implementation(files("$buildDir/classes/jaxb").builtBy("genJaxb"))
    jaxb("com.sun.xml.bind:jaxb-xjc:2.3.5")
}
tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}


tasks.register("genJaxb") {
    ext["sourcesDir"] = "$buildDir/generated-sources/jaxb"
    ext["classesDir"] = "$buildDir/classes/jaxb"
    ext["schema"] = "src/main/resources/wsdl/DailyInfo.wsdl"

    outputs.dir(ext["classesDir"]!!)

    doLast {
        ant.withGroovyBuilder {
            "taskdef"(
                "name" to "xjc", "classname" to "com.sun.tools.xjc.XJCTask",
                "classpath" to jaxb.asPath
            )
            mkdir(ext["sourcesDir"]!!)
            mkdir(ext["classesDir"]!!)

            "xjc"(
                "destdir" to ext["sourcesDir"], "schema" to ext["schema"],
                "package" to "com.jjj.currencyConverter.wsdl"
            ) {
                "arg"("value" to "-wsdl")
                "produces"("dir" to ext["sourcesDir"], "includes" to "**/*.java")
            }

            "javac"(
                "destdir" to ext["classesDir"], "source" to 1.8, "target" to 1.8, "debug" to true,
                "debugLevel" to "lines,vars,source",
                "classpath" to jaxb.asPath
            ) {
                "src"("path" to ext["sourcesDir"])
                "include"("name" to "**/*.java")
                "include"("name" to "*.java")
            }

            "copy"("todir" to ext["classesDir"]) {
                "fileset"("dir" to ext["sourcesDir"], "erroronmissingdir" to false) {
                    "exclude"("name" to "**/*.java")
                }
            }
        }
    }
}
