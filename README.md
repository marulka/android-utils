# android-utils
Android-Utils v1.0

Every developer needs a tools set which can saves him a lot of time writing a same code blocks and/or checks on and on. One of the common things that you should do, 
in case you want to have a fail-safe program, is to check each time you call an objects function for a Null Pointer. The NullPointerException is most common mistake 
every developer does. The Andoid-Utils library gives you easy and flexible solution, which may helps you, like makes your code safer. It definitely will save your time, 
and even more, will helps you much more easier to debug your code.

Download
--------
You can download a jar from GitHub's [releases page][1].

Or use Gradle:

```gradle
repositories {
  mavenCentral() // jcenter() works as well because it pulls from Maven Central
}

dependencies {
  compile 'com.ntechniks.nstudios.android-utils:android-utils:1.1.0'
  compile 'com.android.support:support-v4:26.0.+'
}
```

Or Maven:

```xml
<dependency>
  <groupId>com.ntechniks.nstudios</groupId>
  <artifactId>android-utils</artifactId>
  <version>1.1.0</version>
</dependency>
<dependency>
  <groupId>com.google.android</groupId>
  <artifactId>support-v4</artifactId>
  <version>r7</version>
</dependency>
```

How to use the adroid-utils?
--------

Checkout the [documentation.md][2] for pages on a variety of topics, and see the [javadocs][3].

```java
import com.ntechniks.nstudios.androidutils.*
```

Status
------

[**Version 1.0**][4] is a stable public release. The library is currently under development on the `master` branch.

Comments/bugs/questions/pull requests are always welcome! Please read [contributing.md][5] on how to report issues.

Compatibility
-------------

 * **Android SDK**: Android-Utils requires a minimum API level of 9.
 * **Glide 3.7**: Some of the functions in the library, related with ImageViews are depending on the unofficial Google library Glide. Visit the project [page][6] for more info.
 * **Apache Commons-Lang3 3.5**: Some features are using the Apache library. Learn more about this library at the [official page][7].

Build
-----
Building Android-Utils with gradle is fairly straight forward:

```shell
git clone git@github.com:marulka/android-utils.git # use https://github.com/marulka/android-utils.git if "Permission Denied"
cd android-utils
git submodule init && git submodule update
./gradlew jar
```

**Note**: Make sure your *Android SDK* has the *Android Support Repository* installed, and that your `$ANDROID_HOME` environment
variable is pointing at the SDK or add a `local.properties` file in the root project with a `sdk.dir=...` line.

Development
-----------
Follow the steps in the [Build](#build) section to setup the project and then edit the files however you wish.
[Android Studio][8] cleanly imports both Android-Utils' source and is the recommended way to work with Android-Utils.

To open the project in [Android Studio][8]:

1. Go to *File* menu or the *Welcome Screen*
2. Click on *Open...*
3. Navigate to Android-Utils' root directory.
4. Select `build.gradle`

Getting Help
------------
To report a specific problem or feature request, [open a new issue on Github][9]. For questions, suggestions, or
anything else, or [mail me][10].

Contributing
------------
Before submitting pull requests, contributors must sign Google's [individual contributor license agreement][11].

Author
------
Nikola Georgiev - @marulka on GitHub

License
-------
GNU General Public License, v3.0. See the [LICENSE][12] file for details.


[1]: https://github.com/marulka/android-utils/releases
[2]: https://github.com/marulka/android-utils/blob/master/documentation.md
[3]: https://marulka.github.io/android-utils/
[4]: https://github.com/marulka/android-utils/releases/tag/1.0.0
[5]: https://github.com/marulka/android-utils/blob/1.0.0/contributing.md
[6]: https://github.com/bumptech/glide
[7]: https://commons.apache.org/proper/commons-lang/
[8]: https://developer.android.com/studio/index.html
[9]: https://github.com/marulka/android-utils/issues
[10]: mailto://nikola.georgiev@mail.bg
[11]: https://developers.google.com/open-source/cla/individual
[12]: https://github.com/marulka/android-utils/blob/1.0.0/LICENSE
