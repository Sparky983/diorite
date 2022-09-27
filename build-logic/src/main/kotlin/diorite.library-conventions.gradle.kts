/*
 * Copyright 2022 Sparky983
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    `java-library`
    `maven-publish`
    id("diorite.base-conventions")
//    id("org.checkerframework")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    implementation("ch.qos.logback:logback-classic:1.2.9")

    compileOnly("org.jetbrains:annotations:23.0.0")

    compileOnly("org.checkerframework:checker-qual:3.6.1")

    testImplementation(platform("org.junit:junit-bom:5.9.0"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifactId = "diorite-${project.name}"

            pom {
                description.set(project.description)
                url.set("https://github.com/sparky983/diorite")
                licenses {
                    license {
                        name.set("Apache License 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        name.set("Sparky")
                        id.set("sparky983")
                        url.set("https://github.com/sparky983")
                    }
                }
                issueManagement {
                    system.set("Github")
                    url.set("https://github.com/sparky983/diorite/issues")
                }
                scm {
                    url.set("https://github.com/sparky983/diorite")
                    connection.set("scm:git:git://github.com/sparky983/diorite.git")
                    developerConnection.set("scm:git:ssh://git@github.com:sparky983/diorite.git")
                }
            }
        }
        repositories {
            maven {
                name = "sparky"
                url = if (project.version.toString().endsWith("-SNAPSHOT")) {
                    uri("https://repo.sparky983.me/snapshots")
                } else {
                    uri("https://repo.sparky983.me/releases")
                }
                credentials(PasswordCredentials::class)
                authentication {
                    create<BasicAuthentication>("basic")
                }
            }
        }
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
