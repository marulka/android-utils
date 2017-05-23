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

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Official Git repository at https://github.com/marulka/android-utils
 * 
 * @author Nikola Georgiev
 * @version 1.01
 * @since 1.0
 * 
 */
public class TimeDateUtils {

	/**
	 * Returns the current date and time according to the device locale. The
	 * date style is Medium (such as Jan 12, 1952), and the time style is Short
	 * (such as 3:30pm).
	 * 
	 * @return {@link String} - Returns the current date and time as single
	 *         {@link String}.
	 * @since 1.01
	 */
	public static String getTimeAndDate() {

		final Calendar calendar = Calendar.getInstance();
		final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT,
				Locale.getDefault());

		return dateFormat.format(calendar.getTime());

	}

	// =================================================================================================================================

	/**
	 * Returns the current date according to the device locale. The date style
	 * is Medium (such as Jan 12, 1952).
	 * 
	 * @return {@link String} - Returns the current date as a {@link String}.
	 * @since 1.0
	 */
	public static String getDate() {

		final Calendar calendar = Calendar.getInstance();
		final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());

		return dateFormat.format(calendar.getTime());

	}

	// =================================================================================================================================

	/**
	 * Returns the current month of the Year. The date format is 2 digits number
	 * between 1 to 12.
	 * 
	 * @return {@link String} - The current mount value as a {@link String}.
	 * @since 1.0
	 */
	public static String getMounth() {

		final Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);

		// Scope from 0 - 11. We need to add value of 1
		month++;

		if (month < 10) {
			return "0" + String.valueOf(month);
		}

		return String.valueOf(month);
	}

	// =================================================================================================================================

	/**
	 * Returns the current year.
	 * 
	 * @return {@link String} - The current year value as a {@link String}.
	 * @since 1.0
	 */
	public static String getYear() {

		final Calendar c = Calendar.getInstance();
		final int year = c.get(Calendar.YEAR);

		return String.valueOf(year);
	}

	// =================================================================================================================================

	/**
	 * Returns the current day of the month. The value is 2 digits number
	 * between 1 and 31.
	 * 
	 * @return {@link String} - The current day value as a {@link String}.
	 * @since 1.0
	 */
	public static String getDay() {

		final Calendar c = Calendar.getInstance();
		final int date = c.get(Calendar.DATE);

		return String.valueOf(date);
	}

}
