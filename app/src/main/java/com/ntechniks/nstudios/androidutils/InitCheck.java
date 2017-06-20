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
 * 
 */

import java.util.Collection;

import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Official Git repository at https://github.com/marulka/android-utils
 * 
 * @author Nikola Georgiev
 * @version 1.04
 * @since 1.0
 * 
 */
public class InitCheck {

	/**
	 * The name of the class.
	 * 
	 * @since 1.0
	 */
	private static final String TAG = "InitCheck";

	// =================================================================================================================================

	/**
	 * Checks at same time object array with variables and integer array with
	 * ints. The object array will be checked with the pass() method, see
	 * InitCheck.pass method. All integers in the int[] will be checked,
	 * whether they are positive or not. In case of incorrect variables, please
	 * check log with the Logcat.
	 * 
	 * @param tag
	 *            {@link String} - The name of the class caller .
	 * @param methodName
	 *            {@link String} - The name of the method caller.
	 * @param objects
	 *            Object[] - Array of objects which extend the {@link Object},
	 *            and will pass through a validation check.
	 * @param integers
	 *            int[] - Array with all the int variables that will be checked.
	 * @return boolean - Returns true if all the variables are valid, including
	 *         the tag, methodName, and objects parameters, otherwise will
	 *         returns false.
	 * @since 1.03
	 */
	public static boolean comboCheck(@NonNull String tag, @NonNull String methodName, @NonNull Object[] objects,
			int[] integers) {

		final String method = "comboCheck";
		boolean hasIncorrectValues = false;

		if (pass(TAG, method, tag, methodName, objects, integers )) {

			for (int i = 0; i < integers.length; i++) {
				hasIncorrectValues |= !Check.positiveInt(tag, "#" + i, methodName, integers[i]);
			}

			return pass(tag, methodName, objects) && !hasIncorrectValues;
		}

		return false;
	}

	// =================================================================================================================================

	/**
	 * 
	 * Checks the objects in the objects array, first of all, if they have valid
	 * pointer, and depending on the object type, if they are valid
	 * {@link String}, {@link Collection}, or {@link Bundle}. <br>
	 * - A valid {@link String} will be each string, which has a pointer and the
	 * length is bigger than 0 (zero). <br>
	 * - A valid {@link Collection} will be each collection, which has a pointer
	 * and the size is bigger than 0 (zero). <br>
	 * - A valid {@link Bundle} will be each bundle, which has a pointer and it
	 * is not empty.
	 * 
	 * @param tag
	 *            {@link String} - The name of the class caller.
	 * @param methodName
	 *            {@link String}- The name of the method caller.
	 * @param objects
	 *            Object... - Array of objects which extend the {@link Object},
	 *            and will pass through a validation check.
	 * @return boolean - Returns true if all the variables are valid, including
	 *         the tag, methodName, and objects parameters, otherwise will
	 *         returns false.
	 * @since 1.0
	 */
	public static boolean pass(@NonNull String tag, @NonNull String methodName, Object... objects) {

		final String pass = "pass";
		boolean hasNull = false;
		boolean isNull = false;

		if (Check.notNull(tag, "objects Object[]", methodName, objects)) {

            for (Object object : objects) {

                try {

                    isNull = checkInstance(tag, methodName, isNull, object);

                } catch (final Exception e) {
                    Debug.error(TAG, "check all the variables in the objects array", pass, e);
                }

                hasNull |= isNull;
            }

		}

		return !hasNull;
	}

	// =================================================================================================================================

	/**
	 * 
	 * Check a single variable, whether it is a valid object, or not. First of
	 * all checks, if t does have a valid pointer, and depending on the object
	 * type, if it is a valid {@link String}, {@link Collection}, or
	 * {@link Bundle}. <br>
	 * - A valid {@link String} will be each string, which has a pointer and the
	 * length is bigger than 0 (zero). <br>
	 * - A valid {@link Collection} will be each collection, which has a pointer
	 * and the size is bigger than 0 (zero). <br>
	 * - A valid {@link Bundle} will be each bundle, which has a pointer and it
	 * is not empty.
	 * 
	 * @param tag
	 *            {@link String} - The name of the class caller.
	 * 
	 * @param methodName
	 *            {@link String} - The name of the method caller.
	 * @param isNull
	 *            boolean- A default value, which will be returned in case the
	 *            given variable as parameter is not valid.
	 * @param object
	 *            {@link Object} - The variable, which will pass through a
	 *            validation check. The variable must extends the {@link Object}
	 *            .
	 * @return boolean
	 * @since 1.03
	 */
	public static boolean checkInstance(@NonNull String tag, @NonNull String methodName, boolean isNull, Object object) {

		if (object instanceof String) {
			if (((String) object).length() == 0) {

				isNull = true;

				Debug.wLength(tag, "String", methodName);
			}

		} else if (object instanceof Collection) {
			if (((Collection<?>) object).size() == 0) {

				isNull = true;

				Debug.wSize(tag, "Collection", methodName);
			}

		} else if (object instanceof Bundle) {
			if (((Bundle) object).isEmpty()) {

				isNull = true;

				Debug.wSize(tag, "object Bundle", methodName);
			}

		} else if (object == null) {

			isNull = true;

			Debug.wNull(tag, "Object", methodName);
		}

		return isNull;
	}
}
