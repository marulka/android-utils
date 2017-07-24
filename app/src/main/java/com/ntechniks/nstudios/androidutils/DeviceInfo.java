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

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.NonNull;

/**
 * Official Git repository at https://github.com/marulka/android-utils
 *
 * @author Nikola Georgiev
 * @version 1.0
 * @since 1.1.1
 */
public class DeviceInfo {

    /**
     * The name of the class.
     *
     * @since 1.0
     */
    private static final String TAG = "DeviceInfo";

    // =================================================================================================================================

    /**
     * The available memory on the system. This number should not be considered
     * absolute: due to the nature of the kernel, a significant portion of this
     * memory is actually in use and needed for the overall system to run well.
     *
     * @return long - The available memory on the system.
     * @since 1.0
     */
    public static long getAvailableRamMemory() {

        final ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();

        return mi.availMem;

    }

    // =================================================================================================================================

    /**
     * Notice target API above 16 (Jelly Bean). The total memory accessible by
     * the kernel. This is basically the RAM size of the device, not including
     * below-kernel fixed allocations like DMA buffers, RAM for the baseband
     * CPU, etc.
     *
     * @return long - The total memory on the system. In case API under 16, it
     * will return the available memory instead.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static long getTotalRamMemory() {

        final ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return mi.totalMem;
        }

        return getAvailableRamMemory();
    }

    // =================================================================================================================================

    /**
     * Notice target API above 16 (Jelly Bean). The memory used by the kernel
     * and other applications.
     *
     * @return long - The memory used by the system an other applications. In
     * case API under 16, it will return invalid value (-1).
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static long getUsedRamMemory() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            final long totalMemory = getTotalRamMemory();
            final long availableMemory = getAvailableRamMemory();
            final long freeMemory = totalMemory - availableMemory;

            return freeMemory;
        }

        return -1;
    }

    // =================================================================================================================================

    /**
     * Whether the screen orientation mode is landscape, no matter if the screen
     * angle is 90 or 270 degrees.
     *
     * @param context Context - The context which the activity is started in.
     * @throws NullPointerException - In case the Context parameter is NULL, the method will
     *                              throw NullPointerException.
     * @since 1.0
     */
    public static boolean isLandscapeMode(@NonNull Context context) {

        boolean isLandscape = false;

        if (context == null) {
            throw new NullPointerException();
        }

        final int orientation = getScreenOrientation(context);

        // The value of isLandscape will be true only when the screen
        // orientation is Landscape, otherwise it is safer to return false.
        switch (orientation) {

            case Configuration.ORIENTATION_LANDSCAPE:
                isLandscape = true;
                break;

            default:
                break;
        }

        return isLandscape;
    }

    // =================================================================================================================================

    /**
     * The device screen orientation in degrees. Could be a value of 0, 90, 180,
     * or 270, and dependce on the device manufacturer. Default see
     * Configuration.ORIENTATION_LANDSCAPE} and
     * Configuration.ORIENTATION_PORTRAIT.
     *
     * @param context Context - The context which the activity is started in.
     * @return int - Screen axle direction in degrees.
     * @since 1.0
     */
    public static int getScreenOrientation(@NonNull Context context) {

        final String methodName = "getScreenOrientation";

        int orientation = 0; // Default angel in Portrait Mode.
        Configuration configuration = null;

        if (Check.notNull(TAG, "activity AbstractFragmentActivity", methodName, context)) {

            configuration = context.getResources().getConfiguration();
        }

        if (Check.notNull(TAG, "configuration Configuration", methodName, configuration)) {

            orientation = configuration.orientation;

        } else {
            Debug.warn(TAG, "Cannot determin the screen orientation.", methodName);
        }

        return orientation;
    }
}