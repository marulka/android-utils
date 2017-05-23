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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.NonNull;

/**
 * Official Git repository at https://github.com/marulka/android-utils
 * 
 * @author Nikola Georgiev
 * @version 1.0
 * @since 1.0
 * 
 */
public class WaterMarkerFactory {

	/**
	 * The name of the class.
	 * 
	 * @since 1.0
	 */
	private static final String TAG = "WaterMarkerFactory";

	// =================================================================================================================================

	/**
	 * Places a water-mark text label at the specific bottom right position on a
	 * {@link Bitmap} image.
	 * 
	 * @param oldImage
	 *            {@link Bitmap} - The source {@link Bitmap} image where the
	 *            water-mark will be placed.
	 * @param watermark
	 *            {@link String} - The text {@link String} which will be placed
	 *            as a water-mark.
	 * @param color
	 *            int - The text color of the water-mark.
	 * @param alpha
	 *            int - The text alpha blending of the water-mark.
	 * @param size
	 *            int - The text size of the water-mark.
	 * @param underline
	 *            boolean - Is there going to be a text underline. True if yes,
	 *            otherwise false.
	 * @return {@link Bitmap}- Returns the source {@link Bitmap} image,
	 *         including the water-mark on it.
	 * @since 1.0
	 */
	public static Bitmap mark(@NonNull Bitmap oldImage, @NonNull String watermark, int color, int alpha, int size,
			boolean underline) {

		final String methodName = "mark";
		Bitmap result = null;

		if (InitCheck.comboCheck(TAG, methodName, new Object[] { oldImage, watermark }, new int[] { alpha, size })) {

			Point location = null;
			final Paint paint = new Paint();

			try {

				final int width = oldImage.getWidth();
				final int height = oldImage.getHeight();

				location = getLocation(width, height, watermark, size);

				result = Bitmap.createBitmap(width, height, oldImage.getConfig());

			} catch (final Exception e) {
				Debug.error(TAG, "decoding file to Bitmap", methodName, e);
			}

			final Canvas canvas = new Canvas(result);
			canvas.drawBitmap(oldImage, 0, 0, null);

			paint.setColor(color);
			paint.setAlpha(alpha);
			paint.setTextSize(size);
			paint.setAntiAlias(true);
			paint.setUnderlineText(underline);

			if (Check.notNull(TAG, "location", methodName, location)) {
				canvas.drawText(watermark, location.x, location.y, paint);
			}

		}

		return result;
	}

	// =================================================================================================================================

	/**
	 * Determines the text water-mark position on the image, according to the
	 * text size.
	 * 
	 * @param width
	 *            int - The width of the image.
	 * @param height
	 *            int - The height of the image.
	 * @param watermark
	 *            {@link String} - The text {@link String}, which will be used
	 *            as water-mark.
	 * @param size
	 *            int - The text size of the water-mark.
	 * @return {@link Point} - The point where the text water-mark will be
	 *         placed at.
	 * @since 1.0
	 */
	private static Point getLocation(int width, int height, @NonNull String watermark, int size) {

		if (InitCheck.comboCheck(TAG, "getLocation", new Object[] { watermark }, new int[] { width, height, size })) {

			final int length = watermark.length();

			if ((length * size) < width) {

				final int x = width - (length * size);
				final int y = height - (size * 2);

				return new Point(x, y);
			}

		}

		return null;
	}
}
