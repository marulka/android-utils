# Android-Utils v1.0 - Documentation

Every developer needs a tools set which can saves him a lot of time writing a same code blocks and/or checks on and on. One of the common things that you should do, 
in case you want to have a fail-safe program, is to check each time you call an objects function for a Null Pointer. The NullPointerException is most common mistake 
every developer does. The Andoid-Utils library gives you easy and flexible solution, which may helps you, like makes your code safer. It definitely will save your time, 
and even more, will helps you much more easier to debug your code.

How to use the Android-Utils?
--------

**Check.class**

* The **notNull()** static method in the Check class, gives you ability to make  Null-Pointer-check of every object, which extends the Object class. The method returns boolean 
variable, which makes it perfect to use it inside an IF ELSE statement. Example:

```java
if ( Check.notNull( "MyActivity", "view TextView", "onCreate", view ) ) {

	view.getText();	// It's now safe to call getText method
}
```

Using three String parameters, the method logic will print out a debug information as a Warning message in the Android system log, in case the object parameter has 
a Null Pointer. This will makes the problematic point of the code to highlight itself in the LogCat, which increase the chance to spot the issue and to fix this bug much 
faster. Example:

```log
03-09 19:21:18.320: W/MyActivity(30116): The view TextView is null. The onCreate method won't proceed forward.
```

* The **positiveInt()** static method checks, whether the given integer parameter is positive (var > 0), or not. Many times, the function we want to use is most likely to crash, 
when the given int parameter is negative or equals to 0 (zero), and a IligableArgumentException might be thrown. Also, when we want to check, if a given resource id 
is valid. The method returns boolean variable, which makes it perfect to use it inside an IF ELSE statement. Example:

```java
private void setImage(ImageView view, int imageId) {

	if ( Check.positiveInt( "MyActivity", "imageId", "setImage", imageId ) ) {
		view.setResource( imageId );	// It's now safe to call setResource method.
	}
}
```

Using three String parameters, the method logic will print out a debug information as a Warning message in the Android system log, in case the integer parameter has 
a negative or zero value. This will makes the problematic point of the code to highlight itself in the LogCat, which increase the chance to spot the issue and to fix this 
bug much faster. Example:

```log
03-09 19:21:18.320: W/MyActivity(30116): The basic int variable imageId has invalid value (less than 1). The setImage method won't proceed forward.
```

* The **validString()** static method checks, whether the given variable has a pointer and this String has length higher than 0 (zero).
Mant times, if the variable is an empty string, than proceeding with the execution of specific heavy code is useless. In such a cases, 
this method is very helpful. The method returns boolean variable, which makes it perfect to use it inside an IF ELSE statement. Example:

```java
if ( Check.validString( "MyActivity", "editBoxContent", "sendMessage", editBoxContent ) ) {
  
	HttpService.send( editBoxContent, secondPeerAddress );
}
```
Using three String parameters, the method logic will print out a debug information as a Warning message in the Android system log, in case the integer parameter has a negative or zero value. This will makes the problematic point of the code to highlight itself 
in the LogCat, which increase the chance to spot the issue and to fix this bug much faster. Example:

```log
03-09 19:21:18.320: W/MyActivity(30116): The editBoxContent String is null. The sendMessage method won't proceed forward.
```
**or**
```log
"03-09 19:21:18.320: W/MyActivity(30116): The basic int variable imageId has invalid value (less than 1). The setImage method won't proceed forward."
```

**Debug.class**

* The **dMsg()** static method shows a custom Log message as a debug info. Also, there will be printed information about the class name and method, which this event occurred in. Using the setDebugMode() method, you can controll, whether these type of messages will appear in the log, or not. Example:

```java
Debug.dMsg("MyActivity", "The value of the result is " + myVariable, "compareMyData");
```
Using three String parameters, the method logic will print out a debug information as a Debug message in the Android system log. This will makes the problematic point of the code to highlight itself 
in the LogCat, which increases your chances to spot the issue and to fix this bug much faster. Example:
```log
"03-09 19:21:18.320: D/MyActivity(30116): The value of the result is 256. Occurred in the compareMyData method."
```

* The **error()** static method shows a custom Log message as an error. Also, there will be printed information about the class name and method, which this event occurred in. Using the setDebugMode() method, you can controll, whether these type of messages will appear in the log, or not. Example:

```java
try {
	// Implement some logic here.
} catch (Exception e) {

	Debug.error("MyActivity", "get variables from database", "onCreate", e);
}
```
Using four String parameters, the method logic will print out a debug information as an Error message in the Android system log. This will makes the problematic point of the code to highlight itself 
in the LogCat, which increases your chances to spot the issue and to fix this bug much faster. Example:
```log
03-09 19:21:18.320: E/MyActivity(30116): An error occurred while trying to get variables from database. Occurred in the onCreate method.
03-09 19:21:18.515: E/AndroidRuntime(30116): FATAL EXCEPTION: ModernAsyncTask #1
03-09 19:21:18.515: E/AndroidRuntime(30116): java.lang.RuntimeException: An error occurred while executing doInBackground()
03-09 19:21:18.515: E/AndroidRuntime(30116): 	at android.support.v4.content.ModernAsyncTask$3.done(ModernAsyncTask.java:142)
...
```

* The **info** static method shows a custom Log message as an information. No additional information will be shown, just the emiter class name and your message. Using the setDebugMode() method, you can controll, whether these type of messages will appear in the log, or not. Example:

```java
Debug.info("MyActivity", "Successfully sent an http request.");
```
Using two String parameters, the method logic will print out a debug information as an Informational message in the Android system log. This will makes the problematic point of the code to highlight itself 
in the LogCat, which increases your chances to spot the issue and to fix this bug much faster. Example:
```log
"03-09 19:21:18.320: I/MyActivity(30116): Successfully sent an http request."
```

* The **setDebugMode()** static method sets the debug mode for your app. In case, you would like to see in the system log only one or few type of debug messages, which you implemeted in your code. This method gives you controll on the system log output of your code. By default, all types are allowed. Example:

```java
Debug.setDebugMode(Debug.DEBUG_MODE_ONLY_ERRORS);
```

* The **warn()** static method shows a custom Log message as a warning. Also, there will be printed information about the class name and the method, in which the event occurred. Using the setDebugMode() method, you can controll, whether these type of messages will appear in the log, or not. Example:

```java
if (tableExists) {

	Debug.warn("MyActivity", "The table with the same name already exists.");
}
```
Using two String parameters, the method logic will print out a debug information as a Warning message in the Android system log. This will makes the problematic point of the code to highlights itself in the LogCat, which increases your chances to spot the issue and to fix this bug much faster. Example:
```log
"03-09 19:21:18.320: W/MyActivity(30116): The table with the same name already exists."
```

* The **wIns()** static method shows Log message for Not Instance Of Object as a warning. Also, there will be printed information about the class name and the method, in which the event occurred.  Using the setDebugMode() method, you can controll, whether these type of messages will appear in the log, or not. Example:

```java
if (context instanceof Activity) {

	// Implement your code here.
	
} else {
	
	Debug.wIns("MyFragment", "activity Context", "FragmentActivity", "onResume");
}
```
Using four String parameters, the method logic will print out a debug information as a Warning message in the Android system log. This will makes the problematic point of the code to highlight itself in the LogCat, which increases your chances to spot the issue and to fix this bug much faster. Example:
```log
"03-09 19:21:18.320: W/MyFragment(30116): The activity Context is NOT instance of the FragmentActivity. The onResume method won't proceed forward."
```

* The **wLength()** static method shows a Warning as a Log message when object has a Zero length. Also, there will be printed information about the class name and the method, in which the event occurred. Using the setDebugMode() method, you can controll, whether these type of messages will appear in the log, or not. Example:

```java
if (myString.length() > 0) {
	
	// Implement your code.
} else {
	
	Debug.wLength("MyActivity", "myString String", "onCreate");
}
```
Using three String parameters, the method logic will print out a debug information as a Warning message in the Android system log. This will makes the problematic point of the code to highlights itself in the LogCat, which increases your chances to spot the issue and to fix this bug much faster. Example:
```log
"03-09 19:21:18.320: W/MyActivity(30116): The activity Context is NOT instance of the FragmentActivity. The onResume method won't proceed forward."
```

* The **wNull()** static method shows Log message for Null Pointer as a warning. Also, there will be printed information about the class name and the method, in which the event occurred. Using the setDebugMode() method, you can controll, whether these type of messages will appear in the log, or not. Example:

```java
if (myVariable != null) {

	// Write some code here.
} else {
	
	Debug.wNull("MyActivity", "myVariable Object", "onCereate");
}
```
Using three String parameters, the method logic will print out a debug information as a Warning message in the Android system log. This will makes the problematic point of the code to highlights itself in the LogCat, which increases your chances to spot the issue and to fix this bug much faster. Example:
```log
"03-09 19:21:18.320: W/MyActivity(30116): The myVariable Object is null. The onCreate won't proceed forward."
```

* The **wSize** static method shows Log message for a Zero size as a warning. Also, there will be printed information about the class name and the method, in which the event occurred. Using the setDebugMode() method, you can controll, whether these type of messages will appear in the log, or not. Example:

```java
if (myMap.size() > 0) {
	
	// Implement some code here.
} else {
	
	Debug.wSize("MyActivity", "myMap Object", "onCreate");
}
```
Using three String parameters, the method logic will print out a debug information as a Warning message in the Android system log. This will makes the problematic point of the code to highlights itself in the LogCat, which increases your chances to spot the issue and to fix this bug much faster. Example:
```log
"03-09 19:21:18.320: W/MyActivity(30116): The myMap Object has ZERO size. The onCreate won't proceed forward."
```
**DialogFactory.class**

* The **buildCustomContextMenuDialog()** static method builds AlertDialog which shows list of items represented as an ListView parameter. The alert dialog does NOT have any buttons for feedback. Note: You should always call the show() method to pop-up the message on the device screen.

```java
// String array or List Collection which holds the content lines.
final String[] arrayOfStrings = new String[] { "First line", "Second line", "Third line" };
// ListView to holds the custom context menu items.
ListView listview = new ListView(this.activity);
// The layout which will be used for each list line.
int listItemLayout = android.R.layout.simple_list_item_single_choice;
// The list adapter that will take care of the list items.
ListAdapter adapter = new ArrayAdapter<String>(this.activity, listItemLayout, arrayOfStrings);

// Add your list adapter to the list view.
listview.setAdapter(adapter);
// Implement your logic on item is selected.
listview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

		// Get the selected item from the String array.
		String myChoise = arrayOfStrings[position];
		// Do something with the result, as calling an external method.
		doAction(myChoise);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

		// Implement some code here.
	}
});

DialogFactory.buildCustomContextMenuDialog(this.activity, listview).show();
```

* The **buildInfoDialog()** static method builds AlertDialog which shows only information to the User and has only a neutral button for feedback. Note: You should always call the show() method to pop-up the message on the device screen.

```java
// The resource ID of this dialog Title.
int title = R.string.dialogTitle;

// The resource ID of this dialog Message.
int message = R.string.dialogMessage;

// The resource ID of the neutral button text.
int neutralButtonName = R.string.ok;

// The listener which will be called when the user presses the button.
// Put Empty.onClickListener() as a parameter, in case you don't want to
// implement code (prevents NullPointerException on some platforms).
OnClickListener listener = new android.content.DialogInterface.OnClickListener() {

	@Override
	public void onClick(DialogInterface dialog, int which) {

		// Implement your code here.
	}
};

DialogFactory.buildInfoDialog(this.activity, title, message, neutralButtonName, listener).show();
```

[1]: https://github.com/marulka/android-utils/releases
