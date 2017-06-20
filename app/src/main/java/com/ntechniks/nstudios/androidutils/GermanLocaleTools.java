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

import android.support.annotation.NonNull;

/**
 * Official Git repository at https://github.com/marulka/android-utils
 * 
 * @author Nikola Georgiev
 * @version 1.0
 * @since 1.0
 * 
 */
public class GermanLocaleTools {

	/**
	 * The name of the class.
	 * 
	 * @since 1.0
	 */
	private static final String TAG = "GermanLocaleTools";

	// =================================================================================================================================

	/**
	 * Replaces all specific and Umlaut chars in the German alphabet with their
	 * equivalent strings.
	 * <P>
	 * {@code Ä => Ae,} <br>
	 * {@code Ü => Ue,} <br>
	 * {@code Ö => Oe,} <br>
	 * {@code ä => ae,} <br>
	 * {@code ü => ue,} <br>
	 * {@code ö => oe,} <br>
	 * {@code ß => ss}
	 * </p>
	 * 
	 * @param title
	 *            {@link String} - The String that will be filtered for Umlaut
	 *            chars.
	 * @return {@link String} - The filtered string, if no Umlaut chars found,
	 *         the returned string will be same as the given String param.
	 * @since 1.0
	 */
	public static String filterString(@NonNull String title) {

		final String methodName = "filterStringWithGermanLocale";

		if (Check.validString(TAG, "pageName", methodName, title)) {

			/*
			 * Array with all specific letters in the German alphabet and their
			 * equivalents.
			 */
			final String[][] umlautReplacements = { { new String("Ä"), "Ae" }, { new String("Ü"), "Ue" },
					{ new String("Ö"), "Oe" }, { new String("ä"), "ae" }, { new String("ü"), "ue" },
					{ new String("ö"), "oe" }, { new String("ß"), "ss" } };

			// Replaces the Umlaut chars in string with their equivalents.
			for (int i = 0; i < umlautReplacements.length; i++) {

				try {

					title = title.replace(umlautReplacements[i][0], umlautReplacements[i][1]);

				} catch (final Exception e) {
					Debug.error(TAG, "replacing Umlaut chars with equivalent strings", methodName, e);
				}

			}

		}

		return title;
	}

}
