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

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

/**
 * Official Git repository at https://github.com/marulka/android-utils
 * 
 * @author Nikola Georgiev
 * @version 1.02
 * @since 1.0
 * 
 */
public class ImageScaleUtils {

	/**
	 * The name of the class.
	 * 
	 * @since 1.0
	 */
	private static final String TAG = "ImageScaleUtils";

	// =================================================================================================================================

	/**
	 * Computes the scale ratio which should be used to down-scale the image to
	 * fit in the screen. The image will be obtained by the provided image path
	 * as String. In case the image is smaller than the device screen, the ratio
	 * will be 1.0, which means, the up-scale is not going to be computed.
	 * 
	 * @param activity
	 *            {@link Activity} - The activity context.
	 * @param path
	 *            {@link String} - The String path of the image.
	 * @return float - Returns the image ratio, the result is in between 0.0f
	 *         and 1.0f.
	 * @since 1.0
	 */
	public static float getScaleRatio(@NonNull Activity activity, @NonNull String path) {

		final Rect imageDimens = getImageDimens(path);
		final Rect windowDimens = getScreenDimens(activity);

		final float scaleRatio = getScaleRatio(imageDimens, windowDimens);

		return scaleRatio;
	}

	// =================================================================================================================================

	/**
	 * Computes the scale ratio which should be used to down-scale the image to
	 * fit in the screen. Dimensions of the image and window will be used as
	 * Rect parameters. In case the image is smaller than the device screen, the
	 * ratio will be 1.0, which means, the up-scale is not going to be computed.
	 * 
	 * @param imageDimens
	 *            {@link Rect} - The image dimensions as a Rect object.
	 * @param windowDimens
	 *            {@link Rect} - The window dimensions as a Rect object.
	 * @return float
	 * @since 1.0
	 */
	public static float getScaleRatio(@NonNull Rect imageDimens, @NonNull Rect windowDimens) {

		if (InitCheck.pass(TAG, "getScaleRatio", new Object[] { imageDimens, windowDimens })) {

			int viewWidth;
			final int imageWidth = imageDimens.top;

			viewWidth = (isLandscape(windowDimens)) ? (windowDimens.top / 2) : windowDimens.top;

			if (viewWidth > imageWidth) {
				final float scaleRatio = (imageWidth * 1.0F) / (viewWidth * 1.0F);
				return scaleRatio;
			}
		}

		return 1.0F;
	}

	// =================================================================================================================================

	/**
	 * Gets the device screen dimensions and put them in a Rect object.
	 * 
	 * @param activity
	 *            {@link Activity} - The activity context.
	 * @return {@link Rect} - The device screen dimensions represented as a
	 *         rectangle.
	 * @since 1.0
	 */
	public static Rect getScreenDimens(@NonNull Activity activity) {

		if (Check.notNull(TAG, "activity Activity", "getScreenDimens", activity)) {

			final DisplayMetrics displayMetrics = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

			final int height = displayMetrics.heightPixels;
			final int width = displayMetrics.widthPixels;

			return new Rect(height, width, height, width);
		}

		return null;
	}

	// =================================================================================================================================

	/**
	 * Will check the orientation of a given rectangle.
	 * 
	 * @param rec
	 *            {@link Rect} - The rectangle which should be checked.
	 * @return boolean - If the rectangle orientation is in landscape will
	 *         return true, if not it will return false.
	 * @since 1.0
	 */
	public static boolean isLandscape(@NonNull Rect rec) {

		if (Check.notNull(TAG, "rec Rect", "isLandscape", rec)) {

			final int width = rec.top;
			final int height = rec.left;

			if (width > height) return true;
		}

		return false;
	}

	// =================================================================================================================================

	/**
	 * Gets the image dimensions and put them in a Rect object.
	 * 
	 * @param imagePath
	 *            {@link String} - The String path to the image file.
	 * @return Rect - The image dimensions represented as a rectangle object.
	 * @since 1.0
	 */
	public static Rect getImageDimens(@NonNull String imagePath) {

		if (Check.validString(TAG, "imagePath String", "getImageDimens", imagePath)) {

			/*
			 * The BitmapFactory will only decode the file to get its dimension,
			 * the bitmap image won't be loaded in the memory.
			 */
			final Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(imagePath, options);

			final int imageHeight = options.outHeight;
			final int imageWidth = options.outWidth;

			return new Rect(imageHeight, imageWidth, imageHeight, imageWidth);
		}

		return null;
	}
}
