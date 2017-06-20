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

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.CharUtils;

import android.support.annotation.NonNull;

/**
 * Official Git repository at https://github.com/marulka/android-utils
 * 
 * @author Nikola Georgiev
 * @version 1.01
 * @since 1.0
 * 
 */
public class NameUtils {

	/**
	 * The name of the class.
	 * 
	 * @since 1.0
	 */
	private static final String TAG = "NameUtils";

	/**
	 * Array with all the special chars which are not allowed to participate in
	 * the table name or column name.
	 * 
	 * @since 1.0
	 */
	private static char[] sInvalidChars = new char[] { '@', '#', '$', '%', '&', '*', '-', '+', '(', ')', '!', '"',
			'\'', ':', ';', '/', '?', '~', '`', '|', '{', '}', '=', '[', ']', '<', '>', '^', '.', ',', '\\', ' ' };

	// =================================================================================================================================

	/**
	 * Escapes the special chars to avoid crashes and exceptions when your code
	 * sends a request to a SQL database. Replaces all special chars in the name
	 * with their int value, if the first char is different than Alphabetic, it
	 * will replace it with down slash ('_').
	 * <p>
	 * List with the chars which are not allowed in the table names and column
	 * names: {@code @, #, $, %, &, *, -, +, (, ), !, ", ', :, ;, /, ?, ~, `,
	 * |, , }, =, [, ], <, >, ^, ., ,, \, 'empty space'}.
	 * </p>
	 * 
	 * @param name
	 *            {@link String} - The {@link String} which will be filtered for
	 *            special chars.
	 * @return {@link String} - The filtered {@link String} without special
	 *         chars.
	 * @since 1.0
	 */
	public static String escape(@NonNull String name) {

		final String methodName = "escape";
		String resultString = null;

		if (Check.validString(TAG, "string String", methodName, name)) {

			/*
			 * If the first char is different than Alphabetic, it will replace
			 * it with down slash ('_')
			 */
			final char firstChar = name.charAt(0);
			resultString = (!CharUtils.isAsciiAlpha(firstChar)) ? "_" : Empty.string();

			for (int i = 0; i < name.length(); i++) {

				try {

					final char ch = name.charAt(i);

					resultString += (!ArrayUtils.contains(sInvalidChars, ch)) ? String.valueOf(ch) : ("_"
							+ String.valueOf((int) ch) + "_");

				} catch (final Exception e) {
					Debug.error(TAG, "replace special chars with their int value", methodName, e);
				}

			}

		}

		return resultString;
	}
}
