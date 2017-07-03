Android-Utils v1.1.0

- Added support of Android API26 Alpha (Android 8 - Code name "O").
- Updated dependancy for the Android Support v4 library.
- Overrided minSDKversion for the Android Support v4 library.
- New overloaded buildNeutralOptionsDialog() method in the DialogFactory class.
- New overloaded buildInfoDialog() method in the DialogFactory class.
- Marked the comboCheck() method in the InitCheck class as Deprecated.
- New NetInfoUtils class, which includes the isConneted(), hasInternetConnection(), isWiFiConnected() and getNetworkInfo() methods.
- New buildProgressDialog() method in the DialogFactory class.
- New instanceOf() method in the Check class.
- Updated and replaced with gradle dependency some libraries.
- The project is uploaded to the maven and OSSRH.

Android-Utils v1.0.2

- Bug fix. Missing features in the build.gradle file to fixing issue with compiling using java jdk8.
- Missing javadoc info for the positiveInt method of Check class file.
- Bug fix. Escaped semicolons in javadoc comments section.

Android-Utils v1.0.1

- New info() method in the Debug class.
- Changed the method pass() parameter Object[] in the InitCheck class to accept list of params as Object...
- Removed @NonNull annotation to the string parameter e Throwable of he error() method in Debug.class file.
- Missing null pointer check in the getDataColumn() method of ImageViewUtils class and in the mark() method of WaterMarkerFactory class.
- Code cleanup.
- Added compability with Java 8 in the build.gradle file.