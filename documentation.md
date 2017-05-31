# Android-Utils v1.0 - Documentation

Every developer needs a tools set which can saves him a lot of time writing a same code blocks and/or checks on and on. One of the common things that you should do, 
in case you want to have a fail-safe program, is to check each time you call an objects function for a Null Pointer. The NullPointerException is most common mistake 
every developer does. The Andoid-Utils library gives you easy and flexible solution, which may helps you, like makes your code safer. It definitely will save your time, 
and even more, will helps you much more easier to debug your code.

How to use the Android-Utils?
--------

**Check.class**

* The **notNull()** method in the Check class, gives you ability to make  Null-Pointer-check of every object, which extends the Object class. The method returns boolean 
variable, which makes it perfect to use it inside an IF ELSE statement. Example:

```java
if ( Check.notNull( "MyActivity", "view TextView", "onCreate", view ) ) {

	view.getText();	// It's now safe to call getText method
}
```

Using three more String parameters, the method logic will print out a debug information as a Warning message in the Android system log, in case the object parameter has 
a Null Pointer. This will makes the problematic point of the code to highlight itself in the LogCat, which increase the chance to spot the issue and to fix this bug much 
faster. Example:

```log
03-09 19:21:18.320: W/MyActivity(30116): The view TextView is null. The onCreate method won't proceed forward.
```

* The **positiveInt()** method checks, whether the given integer parameter is positive (var > 0), or not. Many times, the function we want to use is most likely to crash, 
when the given int parameter is negative or equals to 0 (zero), and a IligableArgumentException might be thrown. Also, when we want to check, if a given resource id 
is valid. The method returns boolean variable, which makes it perfect to use it inside an IF ELSE statement. Example:

```java
private void setImage(ImageView view, int imageId) {

	if ( Check.positiveInt( "MyActivity", "imageId", "setImage", imageId ) ) {
		view.setResource( imageId );	// It's now safe to call setResource method.
	}
}
```

Using three more String parameters, the method logic will print out a debug information as a Warning message in the Android system log, in case the integer parameter has 
a negative or zero value. This will makes the problematic point of the code to highlight itself in the LogCat, which increase the chance to spot the issue and to fix this 
bug much faster. Example:

```log
03-09 19:21:18.320: W/MyActivity(30116): The basic int variable imageId has invalid value (less than 1). The setImage method won't proceed forward.
```

* The **validString()** method checks, whether the given variable has a pointer and this String has length higher than 0 (zero).
Mant times, if the variable is an empty string, than proceeding with the execution of specific heavy code is useless. In such a cases, 
this method is very helpful. The method returns boolean variable, which makes it perfect to use it inside an IF ELSE statement. Example:

```java
if ( Check.validString( "MyActivity", "editBoxContent", "sendMessage", editBoxContent ) ) {
  
	HttpService.send( editBoxContent, secondPeerAddress );
}
```
Using three more String parameters, the method logic will print out a debug information as a Warning message in the Android system log, 
in case the integer parameter has a negative or zero value. This will makes the problematic point of the code to highlight itself 
in the LogCat, which increase the chance to spot the issue and to fix this bug much faster. Example:

```log
03-09 19:21:18.320: W/MyActivity(30116): The editBoxContent String is null. The sendMessage method won't proceed forward.
```
**or**
```log
"03-09 19:21:18.320: W/MyActivity(30116): The basic int variable imageId has invalid value (less than 1). The setImage method won't proceed forward.
```

[1]: https://github.com/marulka/android-utils/releases
