plugins {
    java
    application
}

allprojects {

    repositories {
        mavenCentral();
        maven("http://78.47.189.94:80/releases/") {
            isAllowInsecureProtocol = true
        }
    }

    dependencies {

    }

}
