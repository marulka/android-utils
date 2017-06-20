package com.ntechniks.nstudios.androidutils;

/*
 * Copyright (C) 2017 Nikola Georgiev
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>
 */

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Official Git repository at https://github.com/marulka/android-utils
 * 
 * @author Nikola Georgiev
 * @version 1.06
 * @since 1.0
 * 
 */
public class Debug {

	/**
	 * Show all log messages (the default).
	 * 
	 * @since 1.03
	 */
	public static final int DEBUG_MODE_ALL_ON = 0x00;
	/**
	 * Show expected log messages for regular usage, as well as the message
	 * levels Warning, and Error.
	 * 
	 * @since 1.03
	 */
	public static final int DEBUG_MODE_AT_LEAST_INFORMATIONAL = 0x10;
	/**
	 * Show possible issues that are not yet errors, as well as the message
	 * level Error.
	 * 
	 * @since 1.03
	 */
	public static final int DEBUG_MODE_AT_LEAST_WARNINGS = 0x20;
	/**
	 * Show debug log messages that are useful during development only, as well
	 * as the message levels Info, Warning, and Error.
	 * 
	 * @since 1.03
	 */
	public static final int DEBUG_MODE_AT_LEAST_DEBUGINFO = 0x30;
	/**
	 * Show issues that have caused errors.
	 * 
	 * @since 1.03
	 */
	public static final int DEBUG_MODE_ONLY_ERRORS = 0x40;
	/**
	 * No log messages will be shown.
	 * 
	 * @since 1.03
	 */
	public static final int DEBUG_MODE_ALL_OFF = 0x50;

	/**
	 * @since 1.03
	 */
	private static int sDebugMode = DEBUG_MODE_ALL_ON;

	// =================================================================================================================================

	/**
	 * Shows Log message for Null Pointer as a warning. Also, there will be
	 * printed information about the class name and the method, in which the
	 * event occurred.
	 * <p>
	 * {@code Output Example: "The variable Object is null. The method() won't proceed forward."}
	 * </p>
	 * 
	 * @param tag
	 *            {@link String} - The name of the caller Class.
	 * @param variableName
	 *            {@link String} - The name of the variable and/or object type.
	 * @param methodName
	 *            {@link String} - The name of the method in which his event
	 *            occurred.
	 * @since 1.0
	 */
	public static void wNull(@NonNull String tag, @NonNull String variableName, @NonNull String methodName) {

		if ((sDebugMode != DEBUG_MODE_ONLY_ERRORS) || (sDebugMode != DEBUG_MODE_ALL_OFF)) {

			final String msg = "The " + variableName + " is null. The " + methodName + " won't proceed forward.";
			Log.w(tag, msg);
		}

	}

	// =================================================================================================================================

	/**
	 * Shows Log message for a Zero size as a warning. Also, there will be
	 * printed information about the class name and the method, in which the
	 * event occurred.
	 * <p>
	 * {@code Output Example: "The variable Object has ZERO size. The method() won't proceed forward."}
	 * </p>
	 * 
	 * @param tag
	 *            {@link String} - The name of the caller Class.
	 * @param variableName
	 *            {@link String} - The name of the variable and/or object type.
	 * @param methodName
	 *            {@link String} - The name of the method in which his event
	 *            occurred.
	 * @since 1.0
	 */
	public static void wSize(@NonNull String tag, @NonNull String variableName, @NonNull String methodName) {

		if ((sDebugMode != DEBUG_MODE_ONLY_ERRORS) || (sDebugMode != DEBUG_MODE_ALL_OFF)) {

			final String msg = "The " + variableName + " has ZERO size. The " + methodName + " won't proceed forward.";
			Log.w(tag, msg);
		}

	}

	// =================================================================================================================================

	/**
	 * Shows Log message for Zero length as a warning. Also, there will be
	 * printed information about the class name and the method, in which the
	 * event occurred.
	 * <p>
	 * {@code Output Example: "The variable Object has ZERO length. The method() won't proceed forward."}
	 * </p>
	 * 
	 * @param tag
	 *            {@link String} - The name of the caller Class.
	 * @param variableName
	 *            {@link String} - The name of the variable and/or object type.
	 * @param methodName
	 *            {@link String} - The name of the method in which his event
	 *            occurred.
	 * @since 1.0
	 */
	public static void wLength(@NonNull String tag, @NonNull String variableName, @NonNull String methodName) {

		if ((sDebugMode != DEBUG_MODE_ONLY_ERRORS) || (sDebugMode != DEBUG_MODE_ALL_OFF)) {

			final String msg = "The " + variableName + " has ZERO length. The " + methodName
					+ " won't proceed forward.";
			Log.w(tag, msg);
		}

	}

	// =================================================================================================================================

	/**
	 * Shows a custom Log message as an error. Also, there will be printed
	 * information about the class name and the method, in which the event
	 * occurred.
	 * <p>
	 * {@code Output Example: "An error occurred while trying to close stream. Occurred in the myFunction() method."}
	 * </p>
	 * 
	 * @param tag
	 *            {@link String} - The name of the caller Class.
	 * @param whileDo
	 *            {@link String} - Description of the action that may cause this
	 *            error.
	 * @param methodName
	 *            {@link String} - The name of the method in which his event
	 *            occurred.
	 * @param e
	 *            {@link Throwable} - The detailed Exception that has been
	 *            thrown.
	 * @since 1.0
	 */
	public static void error(@NonNull String tag, @NonNull String whileDo, @NonNull String methodName, Throwable e) {

		if ((sDebugMode == DEBUG_MODE_ONLY_ERRORS) || (sDebugMode != DEBUG_MODE_ALL_OFF)) {

			final String msg = "An error occurred while trying to " + whileDo + ". Occurred in the " + methodName
					+ " method.";
			Log.e(tag, msg, e);
		}

	}

	// =================================================================================================================================

	/**
	 * Shows Log message for Not Instance Of Object as a warning. Also, there
	 * will be printed information about the class name and the method, in which
	 * the event occurred.
	 * <p>
	 * {@code Output Example: "The variable String is NOT instance of the Collection. The myFunction() method won't proceed forward."}
	 * </p>
	 * 
	 * @param tag
	 *            {@link String} - The name of the caller Class.
	 * @param variable
	 *            {@link String} - The name of the variable and/or object type.
	 * @param comparator
	 *            {@link String} - The object type that this variable was
	 *            compared to.
	 * @param methodName
	 *            {@link String} - The name of the method in which his event
	 *            occurred.
	 * @since 1.01
	 */
	public static void wIns(@NonNull String tag, @NonNull String variable, @NonNull String comparator,
			@NonNull String methodName) {

		if ((sDebugMode != DEBUG_MODE_ONLY_ERRORS) || (sDebugMode != DEBUG_MODE_ALL_OFF)) {

			Log.w(tag, "The " + variable + " is NOT instance of the " + comparator + ". The " + methodName
					+ " method won't proceed forward.");
		}

	}

	// =================================================================================================================================

	/**
	 * Shows a custom Log message as a warning. Also, there will be printed
	 * information about the class name and the method, in which the event
	 * occurred.
	 * <p>
	 * {@code Output Example: "Your warning message here. The myFunction() won't proceed forward."}
	 * </p>
	 * 
	 * @param tag
	 *            {@link String} - The name of the caller Class.
	 * @param warning
	 *            {@link String} - A custom warning message which will be shown.
	 * @param methodName
	 *            {@link String} - The name of the method in which his event
	 *            occurred.
	 * @since 1.02
	 */
	public static void warn(@NonNull String tag, @NonNull String warning, @NonNull String methodName) {

		if ((sDebugMode != DEBUG_MODE_ONLY_ERRORS) || (sDebugMode != DEBUG_MODE_ALL_OFF)) {

			Log.w(tag, warning + " The " + methodName + " won't proceed forward.");
		}

	}

	// =================================================================================================================================

	/**
	 * Shows a custom Log message as a debug info. Also, there will be printed
	 * information about the class name and the method, in which the event
	 * occurred.
	 * <p>
	 * {@code Output Example: "Your debug info message here. Occurred in the myFunction() method."}
	 * </p>
	 * 
	 * @param tag
	 *            {@link String} - The name of the caller Class.
	 * @param debugMsg
	 *            {@link String} - A custom debug info message will be shown.
	 * @param methodName
	 *            {@link String} - The name of the method in which his event
	 *            occurred.
	 * @since 1.04
	 */
	public static void dMsg(@NonNull String tag, @NonNull String debugMsg, @NonNull String methodName) {

		if (sDebugMode != DEBUG_MODE_ALL_OFF) {

			Log.d(tag, debugMsg + " Occurred in the " + methodName + " method.");
		}

	}

	// =================================================================================================================================

	/**
	 * Shows a custom Log message as an information. No additional information
	 * will be shown, just the emitter class name and your message.
	 *
	 * <p>
	 * {@code Output Example: "Your warning message here."}
	 * </p>
	 *
	 * @param tag
	 *            {@link String} - The name of the caller Class.
	 * @param info
	 *            {@link String} - A custom informational message which will be
	 *            shown.
	 * @since 1.06
	 */
	public static void info(@NonNull String tag, @NonNull String info) {

		if ((sDebugMode != DEBUG_MODE_AT_LEAST_WARNINGS) || (sDebugMode != DEBUG_MODE_ONLY_ERRORS)
				|| (sDebugMode != DEBUG_MODE_ALL_OFF)) {

			Log.i(tag, info);
		}
	}

	// =================================================================================================================================

	/**
	 * Returns the current Debug Mode.
	 * 
	 * @return int
	 * @since 1.03
	 */
	public static int getDebugMode() {
		return sDebugMode;
	}

	// =================================================================================================================================

	/**
	 * @param debugMode
	 *            int - the sDebugMode to set
	 * @since 1.03
	 */
	public static void setDebugMode(int debugMode) {
		Debug.sDebugMode = debugMode;
	}
}
