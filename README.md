# Java Bookstore GUI
Made using Java ImGui bindings.

# Build from source
1. You must download the OpenSans-Light font (from fonts.google.com or another source)
2. You must put the .ttf font (that you can find in the downloaded zip) in the example/src/main/resources folder
3. Run ./gradlew :example:run to see the app

# Note
In case you update the version of imgui java, update the version at:
```
allprojects {
    group = 'imgui-java'
    version = "v1.88.0" <- **HERE**

    repositories {
        mavenCentral()
    }

    ...
}
```