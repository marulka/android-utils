package com.ntechniks.nstudios.androidutils;

/**
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

import android.support.annotation.NonNull;

/**
 * Official Git repository at https://github.com/marulka/android-utils
 * 
 * @author Nikola Georgiev
 * @version 1.15
 * @since 1.0
 * 
 */
public class StringArraySerializer {

	/**
	 * The name of the class.
	 * 
	 * @since 1.0
	 */
	private static final String TAG = "StringArraySerializer";
	/**
	 * The char which will separate the entries.
	 * 
	 * @since 1.0
	 */
	private static final String DELIMITER = ":";

	// =================================================================================================================================

	/**
	 * Serializes array with {@link String}s, the output will be a single
	 * {@link String}. As a separator in the serialization will be used the
	 * double points char ':'.
	 * <p>
	 * <b>Example: </b><br>
	 * Parameter: String[] { First, Second, Third } <br>
	 * Returns: "First:Second:Third"
	 * </p>
	 * 
	 * @param content
	 *            String[] - String array with the strings to serialize.
	 * @return {@link String} - The serialization result {@link String}.
	 * @since 1.0
	 */
	public static String serialize(@NonNull String[] content) {

		final String methodName = "serialize";

		if (InitCheck.pass(TAG, methodName, new Object[] { content })) {

			String result = Empty.string();
			boolean isFirst = true;

			for (int i = 0; i < content.length; i++) {

				final String string = content[i];

				if (Check.validString(TAG, "string", methodName, string)) {
					if (isFirst) {
						result = new String(string);
						isFirst = false;
					} else {
						result = result + DELIMITER + string;
					}
				}

			}

			return result;
		}

		return null;
	}

	// =================================================================================================================================

	/**
	 * Deserializes a {@link String} and returns a {@link String} array. The
	 * serialized {@link String} separator must be the double points char ':'.
	 * <p>
	 * <b>Example: </b><br>
	 * Parameter: "First:Second:Third" <br>
	 * Returns: String[] { First, Second, Third }
	 * </p>
	 * 
	 * @param content
	 *            {@link String} - The serialized String which will be
	 *            deserialized.
	 * @return String[] - Returns a {@link String} array with deserialized
	 *         {@link String}s.
	 * @since 1.0
	 */
	public static String[] deserializeStringArray(@NonNull String content) {

		final String methodName = "deserializeStringArray";

		if (Check.validString(TAG, "content", methodName, content)) {

			final String[] result = content.split(DELIMITER);

			if (InitCheck.pass(TAG, methodName, new Object[] { result })) {

				return result;
			}

		}

		return null;
	}

	// =================================================================================================================================

	/**
	 * Deserializes a {@link String} and returns int array. The serialized
	 * {@link String} separator must be the double points char ':'.
	 * <p>
	 * <b>Example: </b><br>
	 * Parameter: "100:200:300" <br>
	 * Returns: int[] { 100, 200, 300 }
	 * </p>
	 * 
	 * @param asString
	 *            {@link String} - The serialized String which will be
	 *            deserialized.
	 * @return int[] - Returns int array with deserialized integers.
	 * @since 1.0
	 */
	public static int[] deserializeIntArray(@NonNull String asString) {

		final String methodName = "deserializeIntArray";

		if (Check.validString(TAG, "asString", methodName, asString)) {

			final String[] asStringArray = deserializeStringArray(asString);

			if (Check.notNull(TAG, "asStringArray String[]", methodName, asStringArray)) {

				final int length = asStringArray.length;
				if (length > 0) {

					final int[] viewsArray = new int[length];

					for (int i = 0; i < length; i++) {

						try {

							final String string = asStringArray[i];

							// Default value, just for fail
							// safe.
							int parseInt = -1;

							if (Check.validString(TAG, "in element " + i + " from " + length + " string", methodName,
									string)) {

								parseInt = Integer.parseInt(string);
							}

							if (parseInt <= 0) {
								Debug.warn(TAG, "The parseInt int has INVALID value." + "In element " + i + " from "
										+ length + ".", methodName);
							}

							viewsArray[i] = parseInt;

						} catch (final Exception e) {
							Debug.error(TAG, "to parce String[] to int[]", methodName, e);
						}

					}

					return viewsArray;

				} else {
					Debug.wLength(TAG, "asStringArray String[]", methodName);
				}

			}

		}

		return null;
	}
}
