/*
 * Copyright Amazon.com, Inc. or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

import software.amazon.adot.configureImages

plugins {
  java

  application
  id("com.google.cloud.tools.jib")
}

dependencies {
  implementation("io.opentelemetry:opentelemetry-api")
  implementation("com.sparkjava:spark-core")
  implementation("com.google.code.gson:gson:2.10.1")
  implementation("org.slf4j:slf4j-simple")
}
java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}

// not publishing images to hubs in this configuration - local build only through jibDockerBuild
// if localDocker property is set to true then the image will only be pulled from Docker Daemon
tasks {
  named("jib") {
    enabled = false
  }
}
jib {
  configureImages(
    "public.ecr.aws/docker/library/amazoncorretto:23-alpine",
    "aws-appsignals-tests-native-http-client-app",
    localDocker = rootProject.property("localDocker")!! == "true",
    multiPlatform = false,
  )
}
