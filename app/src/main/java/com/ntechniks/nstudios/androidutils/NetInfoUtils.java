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

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

/**
 * Official Git repository at https://github.com/marulka/android-utils
 *
 * @author Nikola Georgiev
 * @version 1.0
 * @since 1.0
 */
public class NetInfoUtils {

    private static final String TAG = "NetInfoUtils";

    // =================================================================================================================================

    /**
     * Checks whether the device is connected to some network, which basically means that it should
     * has Internet Connection. It is not a 100% sure, that the device has internet connection.
     * Always call hasInternetConnection() before attempting to perform data transactions.
     *
     * @param context Context - The activity or application context.
     * @return boolean - Returns true, if the Coarse-grained network state is CONNECTED, otherwise
     * false.
     * @since 1.0
     */
    public static boolean isConnected(@NonNull Context context) {

        String methodName = "isConnected";
        NetworkInfo network = getNetworkInfo(context);

        /*
        * Will check for NullPointer. The getNetworkInfo() may returns NULL when there
        * is no default network.
        */
        if (Check.notNull(TAG, "network NetworkInfo", methodName, network)) {

            if (network.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }

        return false;
    }

    // =================================================================================================================================

    /**
     * Gets the current connection info of the active device interface.
     *
     * @param context Context - The activity or application context.
     * @return NetworkInfo - Returns the active connection info.  Returns NULL when there
     * is no default network.
     * @since 1.0
     */
    public static NetworkInfo getNetworkInfo(@NonNull Context context) {

        String methodName = "getNetworkInfo";
        NetworkInfo network = null;

        if (Check.notNull(TAG, "context Context", methodName, context)) {

            String connectivity = Context.CONNECTIVITY_SERVICE;
            ConnectivityManager connectivityMgr = (ConnectivityManager) context.getSystemService(connectivity);

            /*
            * Will check for NullPointer. The getSystemService() may returns NULL, in case the
            * service is not presented.
            */
            if (Check.notNull(TAG, "connectivity ConnectivityManager", methodName, connectivityMgr)) {

                network = connectivityMgr.getActiveNetworkInfo();
            }
        }

        return network;
    }

    // =================================================================================================================================

    /**
     * Checks whether the device is connected to some network, and this network has a route to the
     * World Wide Web, which means that it should has Internet Connection. Always call this before
     * attempting to perform data transactions.
     *
     * @param context Context - The activity or application context.
     * @return boolean - Returns true, if the active network interface is ready to proceed data to
     * the ethernet, otherwise false.
     * @since 1.0
     */
    public static boolean hasInternetConnection(@NonNull Context context) {

        String methodName = "hasInternetConnection";
        NetworkInfo network = getNetworkInfo(context);

        /*
        * Will check for NullPointer. The getNetworkInfo() may returns NULL when there
        * is no default network.
        */
        if (Check.notNull(TAG, "network NetworkInfo", methodName, network)) {

            if (network.isConnected()) {
                return true;
            }
        }

        return false;
    }

    // =================================================================================================================================

    /**
     * Checks whether the device is connected to some network, and this network has a route to the
     * World Wide Web, which means that it should has Internet Connection. Always call this before
     * attempting to perform data transactions.
     *
     * @param context Context - The activity or application context.
     * @return boolean - Returns tue, if active network interface is the WIFI,
     * otherwise false.
     * @since 1.0
     */
    public static boolean isWiFiConnected(@NonNull Context context) {

        String methodName = "isWiFiConnected";
        NetworkInfo network = getNetworkInfo(context);

        /*
        * Will check for NullPointer. The getNetworkInfo() may returns NULL when there
        * is no default network.
        */
        if (Check.notNull(TAG, "network NetworkInfo", methodName, network)) {

            if (network.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }

        return false;
    }
}
