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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Official Git repository at https://github.com/marulka/android-utils
 *
 * @author Nikola Georgiev
 * @version 1.05
 * @since 1.0
 */
@SuppressLint("NewApi")
public class ImageViewUtils {

    /**
     * The name of the class.
     *
     * @since 1.0
     */
    private static final String TAG = "ImageViewUtils";

    // =================================================================================================================================

    /**
     * Sets image to ImageView by given int resource id. The image will be
     * scaled to fit into the {@link ImageView}, if the image is bigger, than
     * the {@link ImageView} dimensions
     *
     * @param activity {@link Activity} - The activity context in which the
     *                 {@link ImageView} will be attached to.
     * @param view     {@link ImageView} - The {@link ImageView} that the drawable
     *                 resource will be added to.
     * @param resId    int - The int resource id of the drawable.
     * @since 1.04
     */
    public static void setImageByResId(@NonNull Activity activity, @NonNull ImageView view, @AnyRes int resId) {

        if (InitCheck.comboCheck(TAG, "setImageByResId", new Object[]{activity, view}, new int[]{resId})) {

            final Uri uri = getDrawableResUri(activity, resId);

            setImageByUri(activity, view, uri);
        }

    }

    // =================================================================================================================================

    /**
     * Sets image to ImageView by given image {@link Uri} path. The image will
     * be scaled to fit into the {@link ImageView}, if the image is bigger, than
     * the {@link ImageView} dimensions
     *
     * @param activity {@link Activity} - The activity context in which the
     *                 {@link ImageView} will be attached to.
     * @param view     {@link ImageView} - The {@link ImageView} that the drawable
     *                 resource will be added to.
     * @param uri      {@link Uri} - The Uri of the image which should be added to
     *                 the {@link ImageView}.
     * @since 1.04
     */
    public static void setImageByUri(@NonNull Activity activity, @NonNull ImageView view, @NonNull Uri uri) {

        if (InitCheck.pass(TAG, "setImage", activity, view, uri)) {

            final String pathByUri = getFilePathByUri(activity, uri);

            setImageByPath(activity, view, pathByUri);
        }

    }

    // =================================================================================================================================

    /**
     * Sets image to ImageView by given image path as a {@link String}. The
     * image will be scaled to fit into the {@link ImageView}, if the image is
     * bigger, than the {@link ImageView} dimensions
     *
     * @param activity {@link Activity} - The activity context in which the
     *                 {@link ImageView} will be attached to.
     * @param view     {@link ImageView} - The {@link ImageView} that the drawable
     *                 resource will be added to.
     * @param path     {@link String} - The path of the image which should be added
     *                 to the {@link ImageView}.
     * @since 1.02
     */
    public static void setImageByPath(@NonNull Activity activity, @NonNull ImageView view, @NonNull String path) {

        if (Check.notNull(TAG, "view ImageView", "setImageByPath", view)) {

            // Will get scale ratio only if the image is bigger than image view.
            final float sizeMultiplier = ImageScaleUtils.getScaleRatio(activity, path);

            Glide.with(activity) // The activity context.
                    .load(path) // Loads the image by path.
                    .thumbnail(0.3F) // Scaled thumbnail for preload.
                    .fitCenter() // Fits the image to fit in the center.
                    .sizeMultiplier(sizeMultiplier) // Scales the loaded image
                    .crossFade(1) // Time in seconds to cross-fade.
                    .into(view); // The ImageView to load the image to.

            // Will set the Tag to be the image path.
            view.setTag(path);
        }

    }

    // =================================================================================================================================

    /**
     * Gets the file path by a given {@link Uri} of the file. Supported file://
     * and content:// Uri files.
     *
     * @param context {@link Context}
     * @param uri     {@link Uri}
     * @return {@link String}
     * @since 1.0
     */
    public static String getFilePathByUri(@NonNull Context context, @NonNull Uri uri) {

        if (InitCheck.pass(TAG, "getPathByUri", context, uri)) {

            // check here to KITKAT or new version
            final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {

                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }

                }

                // DownloadsProvider
                if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                }

                // MediaProvider
                if (isMediaDocument(uri)) {

                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};

                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }

            }

            // MediaStore (and general)
            if ("content".equalsIgnoreCase(uri.getScheme())) {

                // Return the remote address
                if (isGooglePhotosUri(uri)) return uri.getLastPathSegment();

                return getDataColumn(context, uri, null, null);
            }

            // File
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }

        }

        return null;
    }

    // =================================================================================================================================

    /**
     * Gets the data column from a {@link Uri} object by given selection and
     * selection arguments.
     *
     * @param context       {@link Context} - The context of the application.
     * @param uri           {@link Uri} - The {@link Uri} of the file.
     * @param selection     {@link String} - The selection as String, which will be used
     *                      for this {@link Uri}.
     * @param selectionArgs String[] - The selection arguments as a {@link String}[],
     *                      which will be used for the {@link Uri}.
     * @return {@link String} - The file path as a {@link String}.
     * @since 1.0
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        final String methodName = "getDataColumn";

        if (InitCheck.pass(TAG, methodName, context, uri, selection, selectionArgs)) {

            Cursor cursor = null;
            final String column = "_data";
            final String[] projection = {column};

            try {

                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);

                if (Check.notNull(TAG, "cursor Cursor", methodName, cursor)) {
                    if (cursor.moveToFirst()) {

                        final int index = cursor.getColumnIndexOrThrow(column);

                        return cursor.getString(index);
                    }
                }

            } catch (Exception e) {
                Debug.error(TAG, "moving through the cursor", methodName, e);
            } finally {
                if (Check.notNull(TAG, "cursor Cursor", methodName, cursor))
                    cursor.close();
            }

        }

        return null;
    }

    // =================================================================================================================================

    /**
     * Get Uri from Drawable resource, or any other resource type, if you wish.
     *
     * @param context    {@link Context} - The context of the application.
     * @param drawableId int - The Drawable resource id.
     * @return {@link Uri} - The {@link Uri} of the given drawable resource.
     * @since 1.04
     */
    public static Uri getDrawableResUri(@NonNull Context context, @AnyRes int drawableId) {

        if (InitCheck.comboCheck(TAG, "getDrawableResUri", new Object[]{context}, new int[]{drawableId})) {

            final Resources resources = context.getResources();
            final Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                    + resources.getResourcePackageName(drawableId) + '/' + resources.getResourceTypeName(drawableId)
                    + '/' + resources.getResourceEntryName(drawableId));

            return imageUri;
        }

        return null;
    }

    // =================================================================================================================================

    /**
     * Checks if the authority of the {@link Uri} is an External Storage
     * Document.
     *
     * @param uri {@link Uri} - The file {@link Uri} that will be checked.
     * @return boolean - Returns true if is an External Storage Document,
     * otherwise will returns false.
     * @since 1.0
     */
    public static boolean isExternalStorageDocument(@NonNull Uri uri) {

        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    // =================================================================================================================================

    /**
     * Checks if the authority of the {@link Uri} is a Downloads Document.
     *
     * @param uri {@link Uri} - The file {@link Uri} that will be checked.
     * @return boolean - Returns true if is a Downloads Document, otherwise will
     * returns false.
     * @since 1.0
     */
    public static boolean isDownloadsDocument(@NonNull Uri uri) {

        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    // =================================================================================================================================

    /**
     * Checks if the authority of the {@link Uri} is a Media Document.
     *
     * @param uri {@link Uri} - The file {@link Uri} that will be checked.
     * @return boolean - Returns true if is a Media Document, otherwise will
     * returns false.
     * @since 1.0
     */
    public static boolean isMediaDocument(@NonNull Uri uri) {

        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    // =================================================================================================================================

    /**
     * Checks if the authority of the {@link Uri} is a Photos Content.
     *
     * @param uri {@link Uri} - The file {@link Uri} that will be checked.
     * @return boolean - Returns true if is a Photos Content, otherwise will
     * returns false.
     * @since 1.0
     */
    public static boolean isGooglePhotosUri(@NonNull Uri uri) {

        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
