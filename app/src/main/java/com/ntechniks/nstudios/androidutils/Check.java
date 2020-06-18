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

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Official Git repository at https://github.com/marulka/android-utils
 *
 * @author Nikola Georgiev
 * @version 1.01
 * @since 1.0
 */
public class Check {

    /**
     * Name of the class file.
     *
     * @since 1.0
     */
    public static final String TAG = "Check";

    /**
     * Main constructor with private accessor to prevent instantiating the class.
     *
     * @since 1.2.0
     */
    private Check() {
        // Nothing to implement here.
    }

    // =================================================================================================================================

    /**
     * Does all these lines-consuming Null Pointer checks, which we all have to
     * write every time we call some member's field. When the object variable
     * has null pointer null, there will be printed debug information in the
     * Logcat with the given variable name, the name of the class the this check
     * was called from, and the method name where this check was called from.
     *
     * @param tag          {@link String} - The name of the caller Class.
     * @param variableName {@link String} - The variable name and variable type.
     * @param methodName   {@link String} - The name of the method caller.
     * @param obj          {@link Object} - The variable you want to check for null
     *                     pointer.
     * @return boolean - Returns true, if the variable does have Null
     * Pointer. Returns false, if one of the parameters has valid Pointer, including either if
     * the parameter <b>tag</b>,
     * <b>variableName</b>, or <b>methodName</b> strings has zero
     * length.
     * @since 1.0
     */
    public static boolean isNull(@NonNull String tag, @NonNull String variableName,
                                 @NonNull String methodName, Object obj) {

        return !notNull(tag, variableName, methodName, obj);
    }

    // =================================================================================================================================

    /**
     * Does all these lines-consuming Null Pointer checks, which we all have to
     * write every time we call some member's field. When the object variable
     * has null pointer null, there will be printed debug information in the
     * Logcat with the given variable name, the name of the class the this check
     * was called from, and the method name where this check was called from.
     *
     * @param tag          {@link String} - The name of the caller Class.
     * @param variableName {@link String} - The variable name and variable type.
     * @param methodName   {@link String} - The name of the method caller.
     * @param obj          {@link Object} - The variable you want to check for null
     *                     pointer.
     * @return boolean - Returns true, if the variable does NOT have Null
     * Pointer. Returns false, if one of the parameters has Null
     * Pointer, including either if the parameter <b>tag</b>,
     * <b>variableName</b>, or <b>methodName</b> strings has zero
     * length.
     * @since 1.0
     */
    public static boolean notNull(@NonNull String tag, @NonNull String variableName,
                                  @NonNull String methodName, @Nullable Object obj) {

        if (obj != null) {
            return true;
        }
        Debug.wNull(tag, variableName, methodName);
        return false;
    }

    // =================================================================================================================================

    /**
     * Does all these lines-consuming check in your source code, if the
     * {@link String} variable has null pointer, or the variable has zero
     * length. When the String variable has null pointer, there will be printed
     * debug information in the Logcat with the given variable name, the name of
     * the class the this check was called from, and the method name where this
     * check was called from.
     *
     * @param tag          {@link String} - The name of the caller Class.
     * @param variableName {@link String} - The variable name and variable type.
     * @param methodName   {@link String} - The name of the method caller.
     * @param str          {@link Object} - The {@link String} variable you want to
     *                     check.
     * @return boolean - Returns true, if the variable does NOT have Null
     * Pointer, and the {@link String} has length higher than 0 (zero).
     * Returns false, if one of the parameters has Null Pointer,
     * including either if the parameter <b>tag</b>,
     * <b>variableName</b>, or <b>methodName</b> strings has zero
     * length.
     * @since 1.0
     */
    public static boolean validString(@NonNull String tag, @NonNull String variableName, @NonNull String methodName,
                                      @Nullable String str) {

        variableName += " String";

        return notNull(tag, variableName, methodName, str)
                && positiveInt(tag, variableName, methodName, str.length());
    }

    // =================================================================================================================================

    /**
     * Does all these lines-consuming check in your source code, if the
     * {@link String} variable has null pointer, or the variable has zero
     * length. When the String variable has null pointer, there will be printed
     * debug information in the Logcat with the given variable name, the name of
     * the class the this check was called from, and the method name where this
     * check was called from.
     *
     * @param tag          {@link String} - The name of the caller Class.
     * @param variableName {@link String} - The variable name and variable type.
     * @param methodName   {@link String} - The name of the method caller.
     * @param str          {@link Object} - The {@link String} variable you want to
     *                     check.
     * @return boolean - Returns true, if the variable has Null
     * Pointer, and the {@link String} has length equals to 0 (zero).
     * Returns also true, if one of the parameters has Null Pointer,
     * including either if the parameter <b>tag</b>,
     * <b>variableName</b>, or <b>methodName</b> strings has Null Pointer.
     * @since 1.2.0
     */
    public static boolean invalidString(@NonNull String tag, @NonNull String variableName,
                                        @NonNull String methodName,
                                        @Nullable String str) {

        return !validString(tag, variableName, methodName, str);
    }

    // =================================================================================================================================

    /**
     * @param tag           {@link String} - The name of the caller Class.
     * @param intName       - the name of the integer variable.
     * @param methodName    {@link String} - The name of the method caller.
     * @param basicVariable int - The integer variable which will be checked if it is
     *                      positive (Higher than 0).
     * @return boolean - Returns true, if the variable is positive (higher than
     * 0). Returns false, if the variable <b>basicVariable</b> equals to
     * 0 (zero), it is negative (less than 0), and/or one of the
     * parameters has Null Pointer, including either if the parameter
     * <b>tag</b>, <b>variableName</b>, or <b>methodName</b> strings has
     * zero length.
     * @since 1.0
     */
    public static boolean positiveInt(@NonNull String tag, @NonNull String
            intName, @NonNull String methodName,
                                      @IntRange int basicVariable) {

        if (basicVariable > 0) {
            return true;
        }
        Debug.warn(tag, "The basic int variable " + intName + " has invalid value (less than 1).", methodName);
        return false;
    }

    /**
     * Checks is a given object is instance of a given class type. In case the object is not instance
     * of this type, then will print a debug information out in the system log.
     *
     * @param tag        {@link String} - The name of the caller Class.
     * @param obj        {@link Object} - The object, which instance will be checked.
     * @param classType  {@link Class} - he class type, which the object instance will be compared to.
     * @param methodName {@link String} - The name of the method caller.
     * @return boolean - Returns true only if the object is instance of the given class type, otherwise false.
     */
    public static boolean instanceOf(@NonNull String tag, @Nullable Object
            obj, @NonNull Class<?> classType, @NonNull String methodName) {

        boolean isInstanceOf = false;
        if (InitCheck.pass(tag, methodName, obj, classType)) {

            isInstanceOf = classType.isAssignableFrom(obj.getClass());
            if (!isInstanceOf) {
                Debug.wIns(tag, "obj " + obj.getClass().getSimpleName(), classType.getSimpleName(), methodName);
            }
        }
        return isInstanceOf;
    }
}
