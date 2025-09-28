rootProject.name = "mcp"

plugins{
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

//pluginManageemnt {
//    repositories {
//        mavenCentral()
//    }
//}
//
//dependencyManagement {
//    repositories {
//        mavenCentral()
//    }
//}

include(":mcp-server")
include(":auth-server")

