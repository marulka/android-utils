package com.ntechniks.nstudios.androidutils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.apache.commons.lang3.StringUtils;

import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

/**
 * @author Nikola Georgiev
 * @version 1.0
 * @since 1.2.0
 */
public final class PermissionUtils {

    private static final String TAG = PermissionUtils.class.getSimpleName();

    /**
     * Main constructor with private accessor to prevent instantiating the class.
     *
     * @since 1.2.0
     */
    private PermissionUtils() {
        // Nothing to implement here.
    }

    /**
     * Checks whether a permission is required in order to access specific device resource. Use
     * specific permission type listed in the {@link android.Manifest.permission}.
     *
     * @param context        {@link Activity} - The activity context for this permissions checking.
     * @param permissionType {@link String} - The permission type key as string.
     * @return {@link boolean} - Returns true when the Android version is before Marshmallow, and/or
     * the specific permission is not yet granted, and/or should ask the user once again to grant
     * it.
     * @since 1.2.0
     */
    public static boolean shouldAskForPermission(@NonNull final Activity context, @NonNull final String permissionType) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (ContextCompat.checkSelfPermission(context, permissionType) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return ActivityCompat.shouldShowRequestPermissionRationale(context, permissionType);
    }

    /**
     * Make Android asks the user for the required permission. Use unique request code in order
     * to recognize the returned back result in your activity context.
     *
     * @param context         {@link Activity} - The activity context for this permissions checking.
     * @param requestCode     {@link int} - The code which will be used as response when returns back
     *                        to the activity with result.
     * @param permissionTypes {@link String} - The permission type keys as array, or comma
     *                        separated params.
     * @since 1.2.0
     */
    public static void requestPermission(final Activity context, int requestCode,
                                         final String... permissionTypes) {

        if (InitCheck.notPass(TAG, "requestPermission", context, permissionTypes)) {
            return;
        }
        ActivityCompat.requestPermissions(context, permissionTypes, requestCode);
    }

    /**
     * Builds permissions set out of a permission number code.
     *
     * @param perms {@link int} - Number representation of the permissions.
     * @return {@link Set} - Returns set of file permissions.
     * @since 1.2.0
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Set<PosixFilePermission> permissions(final int perms) {

        return PosixFilePermissions.fromString(numberToString(perms));
    }

    /**
     * Converts number permission code to characters coded permission string.
     *
     * @param permissionsAsInt {@link int} - Number presentation of the file permissons.
     * @return {@link String} - Returns characters coded permission string.
     */
    public static String numberToString(final int permissionsAsInt) {

        final char[] ds = Integer.toString(permissionsAsInt).toCharArray();
        final char[] ss = {'-', '-', '-', '-', '-', '-', '-', '-', '-'};
        for (int i = ds.length - 1; i >= 0; i--) {
            int n = ds[i] - '0';
            if (i == ds.length - 1) {
                if ((n & 1) != 0) ss[8] = 'x';
                if ((n & 2) != 0) ss[7] = 'w';
                if ((n & 4) != 0) ss[6] = 'r';
            } else if (i == ds.length - 2) {
                if ((n & 1) != 0) ss[5] = 'x';
                if ((n & 2) != 0) ss[4] = 'w';
                if ((n & 4) != 0) ss[3] = 'r';
            } else if (i == ds.length - 3) {
                if ((n & 1) != 0) ss[2] = 'x';
                if ((n & 2) != 0) ss[1] = 'w';
                if ((n & 4) != 0) ss[0] = 'r';
            }
        }
        return new String(ss);
    }

    /**
     * @param permissions {@link String}
     * @return {@link int}
     * @throws NumberFormatException
     */
    public static int permissionsToNumber(final String permissions) throws NumberFormatException {

        final StringBuilder modeNumber = new StringBuilder();
        if (isValidPermissionsString(permissions)) {
            modeNumber.append(getSum(permissions.substring(0, 3)));
            modeNumber.append(getSum(permissions.substring(3, 6)));
            modeNumber.append(getSum(permissions.substring(6, 9)));
        } else {
            Debug.warn(TAG, permissions + " is not a valid access permissions string",
                    "permissionsToNumber");
        }
        return Integer.parseInt((modeNumber.length() > 0) ? modeNumber.toString() : "-1");
    }

    /**
     * @param permissions {@link String}
     * @return {@link boolean}
     */
    public static boolean isValidPermissionsString(final String permissions) {

        return StringUtils.isNotBlank(permissions) && permissions.length() == 9;
    }

    private static int getSum(final String subString) {

        int sum = 0;
        for (final char c : subString.toCharArray()) {
            switch (c) {
                case 'r':
                    sum += 4;
                    break;
                case 'w':
                    sum += 2;
                    break;
                case 'x':
                    sum += 1;
                    break;
                default:
                    break;
            }
        }
        return sum;
    }
}
