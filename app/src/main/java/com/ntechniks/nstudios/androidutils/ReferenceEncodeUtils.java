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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
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
public class ReferenceEncodeUtils {

	/**
	 * The name of the class.
	 * 
	 * @since 1.0
	 */
	private static final String TAG = "ReferenceEncodeUtils";

	/**
	 * The key which will be used to identify the saved list item index in the
	 * savedBundle {@link Bundle} in the context {@link Activity}.
	 * 
	 * @since 1.0
	 */
	public static final String KEY_ITEM_INDEX = "index";
	/**
	 * The key which will be used to identify the saved page number in the
	 * savedBundle {@link Bundle} in the context {@link Activity}.
	 * 
	 * @since 1.0
	 */
	public static final String KEY_PAGE_ID = "page";
	/**
	 * The key which will be used to identify the saved pages count in the
	 * savedBundle {@link Bundle} in the context {@link Activity}.
	 * 
	 * @since 1.0
	 */
	public static final String KEY_TOTAL_COUNT = "count";

	// =================================================================================================================================

	/**
	 * Example: If refNum is "1/13", index number is 1, then the encoded int
	 * should be 2020113. Blocks break out of this encoded integer:
	 * {multiplier}{index+1}{page}{totalCount} or {2}{02}{01}{13}.The first
	 * digit (multiplier) tells how much digits should a single block have.
	 * 
	 * @param refNum
	 *            {@link String} - The reference number of the page.
	 * @param index
	 *            int - The list item index.
	 * @param divider
	 *            char - A specific char, which developer is using in the
	 *            reference number to separate the page number and the total
	 *            pages count.
	 * @return int - The encoded parameters as 32bit integer.
	 * @since 1.0
	 */
	public static int encodeRequestCode(@NonNull String refNum, int index, char divider) {

		final String methodName = "encodeRequestCode";
		int key = -1;

		if (Check.validString(TAG, "refNum", methodName, refNum)) {

			if (Check.validString(TAG, "refNum String", methodName, refNum)) {

				final int deviderPos = findDivider(refNum, divider);

				if (Check.positiveInt(TAG, "deviderPos", methodName, deviderPos)) {

					final int pageId = getPageIdFromString(refNum, deviderPos);
					final int totalCount = getTotalCount(refNum, deviderPos);
					final int multiplier = getMultiplier(index, pageId, totalCount);

					key = encode(pageId, totalCount, index, multiplier);

				}
			}
		}

		return key;
	}

	// =================================================================================================================================

	/**
	 * Gets the Page number from the page reference number, using predefined
	 * divider position.
	 * 
	 * @param refNum
	 *            {@link String} - The page reference number.
	 * @param dividerPos
	 *            int - The position of the divider in the reference number.
	 * @return int - The page number.
	 * @since 1.0
	 */
	private static int getPageIdFromString(@NonNull String refNum, int dividerPos) {

		final String methodName = "getPageIdFromString";
		int pageId = -1; // Fail value

		if (InitCheck.comboCheck(TAG, methodName, new Object[] { refNum }, new int[] { dividerPos })) {

			try {

				final int endIndex = dividerPos;
				final String substring = refNum.substring(0, endIndex);
				pageId = Integer.parseInt(substring);

			} catch (final NumberFormatException nfe) {
				Debug.error(TAG, "parse String to int", methodName, nfe);
			}

		}

		return pageId;
	}

	// =================================================================================================================================

	/**
	 * Gets the Pages count from the page reference number, using predefined
	 * divider position.
	 * 
	 * @param refNum
	 *            {@link String} - The page reference number.
	 * @param dividerPos
	 *            int - The position of the divider in the reference number.
	 * @return int - The pages count.
	 * 
	 * @since 1.0
	 */
	private static int getTotalCount(@NonNull String refNum, int dividerPos) {

		final String methodName = "getTotalCount";
		int totalCount = -1; // Fail value.

		if (InitCheck.comboCheck(TAG, methodName, new Object[] { refNum }, new int[] { dividerPos })) {

			try {

				final int endIndex = refNum.length();
				final String substring = refNum.substring(dividerPos + 1, endIndex);
				totalCount = Integer.parseInt(substring);

			} catch (final NumberFormatException nfe) {
				Debug.error(TAG, "parse String to int", methodName, nfe);
			}

		}

		return totalCount;
	}

	// =================================================================================================================================

	/**
	 * Define the multiplier using the index, pageId, and totalCount parameters.
	 * 
	 * @param index
	 *            int - The list item index number.
	 * @param pageId
	 *            int - The page number.
	 * @param totalCount
	 *            int - The pages total count.
	 * @return int - Multiplier, which represents the digits count of the
	 *         biggest number of the given parameters.
	 * @since 1.0
	 */
	private static int getMultiplier(int index, int pageId, int totalCount) {

		final String methodName = "getMultiplier";
		int multiplier = -1; // Fail value.

		if (InitCheck.comboCheck(TAG, methodName, new Object[] { methodName }, new int[] { pageId, totalCount })) {

			final int maxInt = Math.max(Math.max(index, pageId), totalCount);
			final String maxIntAsString = String.valueOf(maxInt);
			multiplier = maxIntAsString.length();

		}

		return multiplier;
	}

	// =================================================================================================================================

	/**
	 * Encodes the page Id, total count, and list item index, using the
	 * multiplier. The result is a 32bit integer.
	 * 
	 * @param pageId
	 *            int - The page number.
	 * @param totalCount
	 *            int - The total pages count.
	 * @param index
	 *            int - The list item index.
	 * @param multiplier
	 *            int - The multiplier, which represents the digits count of the
	 *            biggest number of this parameters.
	 * @return int - Returns encoded 32bit integer.
	 * @since 1.0
	 */
	private static int encode(int pageId, int totalCount, int index, int multiplier) {

		final String methodName = "encode";
		int keyCode = -1;

		if (InitCheck.comboCheck(TAG, methodName, new Object[] { methodName }, new int[] { pageId, totalCount })) {

			String i = String.valueOf(index);
			String page = String.valueOf(pageId);
			String count = String.valueOf(totalCount);

			final char[] iArray = i.toCharArray();
			final char[] pageArray = page.toCharArray();
			final char[] countArray = count.toCharArray();

			i = expandString(multiplier, i, iArray);
			page = expandString(multiplier, page, pageArray);
			count = expandString(multiplier, count, countArray);

			final String requestCodeAsString = String.valueOf(multiplier) + i + page + count;

			keyCode = Integer.parseInt(requestCodeAsString);
		}

		return keyCode;
	}

	// =================================================================================================================================

	/**
	 * Expands a given {@link String} according to a multiplier. If the base
	 * string has length of 2, but the multiplier equals 3, than the ase string
	 * will be expanded to a string with length of 3 and a char with 0 (zero)
	 * will be added on the newly added char places.
	 * 
	 * @param multiplier
	 *            int - The multiplier, which will be used to expand the base
	 *            {@link String}.
	 * @param baseString
	 *            {@link String} - The base {@link String} which will be
	 *            expanded.
	 * @param charArray
	 *            char[]
	 * @return {@link String}
	 * @since 1.01
	 */
	private static String expandString(int multiplier, @NonNull String baseString, char[] charArray) {

		if (InitCheck.comboCheck(TAG, "expandString", new Object[] { charArray }, new int[] { multiplier })) {

			if (charArray.length >= multiplier) {

				baseString = new String(charArray, multiplier - (baseString.length()), multiplier);

			} else {

				final char[] newCharArray = new char[multiplier - charArray.length];
				Arrays.fill(newCharArray, '0');

				baseString = new String(newCharArray);
				baseString += new String(charArray, 0, baseString.length());

			}

		}

		return baseString;
	}

	// =================================================================================================================================

	/**
	 * Finds the place of the divider in a reference number {@link String}.
	 * 
	 * @param refNum
	 *            {@link String}
	 * @return int
	 * @since 1.0
	 */
	private static int findDivider(@NonNull String refNum, char divider) {

		final String methodName = "findDivider";

		if (Check.validString(TAG, "refNum", methodName, refNum)) {

			for (int i = 0; i < refNum.length(); i++) {

				try {

					final char letter = refNum.charAt(i);
					if (letter == divider) {
						return i;
					}

				} catch (final Exception e) {
					Debug.error(TAG, "get char at position " + i + " in the refNum String", methodName, e);
				}
			}

		}

		return -1;
	}

	// =================================================================================================================================

	/**
	 * Decodes a request code and returns a map of values.
	 * 
	 * @param requestCode
	 *            int - The request code which will be decoded.
	 * @return Map<String, Integer> - Map with the decoded values. Keys are
	 *         {@link String}s and the values are {@link Integer}s.
	 * @since 1.0
	 */
	public static Map<String, Integer> decode(int requestCode) {

		final String methodName = "decode";
		final Map<String, Integer> valuesMap = new HashMap<String, Integer>();

		try {

			final String requestCodeAsString = String.valueOf(requestCode);

			final int multiplier = Integer.valueOf(String.valueOf(requestCodeAsString.charAt(0)));

			if (Check.positiveInt(TAG, "multiplier", methodName, multiplier)) {

				final int aBeginIndex = 1;
				final int aEndIndex = aBeginIndex + multiplier;
				final int bBeginIndex = aEndIndex;
				final int bEndIndex = bBeginIndex + multiplier;
				final int cBeginIndex = bEndIndex;
				final int cEndIndex = cBeginIndex + multiplier;

				final String indexSubstring = requestCodeAsString.substring(aBeginIndex, aEndIndex);
				final String pageSubstring = requestCodeAsString.substring(bBeginIndex, bEndIndex);
				final String countSubstring = requestCodeAsString.substring(cBeginIndex, cEndIndex);

				final int index = Integer.parseInt(indexSubstring);
				final int pageId = Integer.parseInt(pageSubstring);
				final int pagesCount = Integer.parseInt(countSubstring);

				valuesMap.put(KEY_ITEM_INDEX, index);
				valuesMap.put(KEY_PAGE_ID, pageId);
				valuesMap.put(KEY_TOTAL_COUNT, pagesCount);

			}

		} catch (final Exception e) {
			Debug.error(TAG, "get blocks from encoded number " + requestCode, methodName, e);
		}

		return valuesMap;
	}

	// =================================================================================================================================

	/**
	 * Decodes a request code and returns the page reference number
	 * {@link String}.
	 * 
	 * @param requestCode
	 *            int - The request code which will be decoded.
	 * @return {@link String} - The page reference number.
	 * @since 1.0
	 */
	public static String decodePageRefNumber(int requestCode) {

		final String methodName = "decodeListingRefNumber";
		String tag = null;

		if (Check.positiveInt(TAG, "requestCode", methodName, requestCode)) {

			final Map<String, Integer> valuesMap = decode(requestCode);
			if (Check.notNull(TAG, "valuesMap Map", methodName, valuesMap)) {

				final int pageId = valuesMap.get(KEY_PAGE_ID);
				final int totalCount = valuesMap.get(KEY_TOTAL_COUNT);
				tag = String.valueOf(pageId) + "/" + String.valueOf(totalCount);

			}

		}

		return tag;
	}

	// =================================================================================================================================

	/**
	 * Decodes a request code nd returns the list item index.
	 * 
	 * @param requestCode
	 *            int - The request code.
	 * @return int - The list item index position.
	 * @since 1.0
	 */
	public static int decodeItemIndex(int requestCode) {

		final String methodName = "decodeTopicIndex";
		int topicIndex = -1;

		if (Check.positiveInt(TAG, "requestCode", methodName, requestCode)) {

			final Map<String, Integer> valuesMap = decode(requestCode);

			if (Check.notNull(TAG, "valuesMap Map", methodName, valuesMap)) {
				topicIndex = valuesMap.get(KEY_ITEM_INDEX);
			}

		}

		return topicIndex;
	}
}
