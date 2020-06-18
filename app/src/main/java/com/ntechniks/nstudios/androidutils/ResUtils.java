package com.ntechniks.nstudios.androidutils;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;

import org.apache.commons.lang3.Validate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Utility class to help various Resource file manipulations.
 *
 * @author Nikola Georgiev
 * @version 1.0
 * @since 1.2.0
 */
public class ResUtils {

    public static final String TAG = "ResUtils";
    public static final String RES_TYPE_RAW = "raw";

    /**
     * Main constructor with private accessor to prevent instantiating the class.
     *
     * @since 1.2.0
     */
    private ResUtils() {
        // Nothing to implement here.
    }

    /**
     * Unpacking a zip file stored as a application resource file. By specifying the recourse name
     * it will load the zip file and will unzip it into specific directory.
     *
     * @param context     {@link Context} - The current context.
     * @param destFolder  {@link String} - The directory path to the destination.
     * @param resName     {@link String} - The resource name of the file.
     * @param permissions {@link String} - In case the files don't exist, it will create new
     *                    file or dir with the following permissions attribute.
     * @throws FileNotFoundException                           - In case the file was not found.
     * @throws android.content.res.Resources.NotFoundException - In case the resource was not found.
     * @throws IOException                                     - In case there was read/write problem during unpacking.
     * @throws IllegalArgumentException
     * @since 1.2.0
     */
    public static void unpackZipResourceByName(final Context context, String destFolder,
                                               String resName,
                                               @Nullable final String permissions) throws IOException {

        final int id = getResIdByName(context, resName, RES_TYPE_RAW);
        unpackZipResource(id, context, destFolder, permissions);
    }

    /**
     * Unpacking a zip file stored as a application resource file. By specifying the recourse ID
     * it will load the zip file and will unzip it into specific directory.
     *
     * @param id          {@link int} - The resource id of the file.
     * @param context     {@link Context} - The current context.
     * @param destFolder  {@link String} - The directory path to the destination.
     * @param permissions {@link String} - In case the files don't exist, it will create new
     *                    file or dir with the following permissions attribute.
     * @throws FileNotFoundException                           - In case the file was not found.
     * @throws android.content.res.Resources.NotFoundException - In case the resource was not found.
     * @throws IOException                                     - In case there was read/write problem during unpacking.
     * @throws IllegalArgumentException
     * @since 1.2.0
     */
    public static void unpackZipResource(final int id, final Context context,
                                         final String destFolder,
                                         @Nullable final String permissions) throws IOException {

        unpackZipResource(id, context, destFolder, permissions, true);
    }

    /**
     * Unpacking a zip file stored as a application resource file. By specifying the recourse ID
     * it will load the zip file and will unzip it into specific directory.
     *
     * @param id             {@link int} - The resource id of the file.
     * @param context        {@link Context} - The current context.
     * @param destFolder     {@link String} - The directory path to the destination.
     * @param permissions    {@link String} - In case the files don't exist, it will create new
     *                       file or dir with the following permissions attribute.
     * @param shouldOverride {@link boolean} - Should override the destination files if already
     *                       exist.
     * @throws FileNotFoundException                           - In case the file was not found.
     * @throws android.content.res.Resources.NotFoundException - In case the resource was not found.
     * @throws IOException                                     - In case there was read/write problem during unpacking.
     * @throws IllegalArgumentException                        - If the {@param context} has Null pointer will throw
     *                                                         RuntimeException.
     * @since 1.2.0
     */
    public static void unpackZipResource(@RawRes final int id, final Context context,
                                         @Nullable final String destFolder,
                                         @Nullable final String permissions,
                                         final boolean shouldOverride) throws IOException {

        // Open the ZipInputStream
        final ZipInputStream zip = getZipInputStream(context, id);
        if (zip == null) {
            throw new Resources.NotFoundException("Cannot load raw resource file with id: " + id);
        }
        unpackZipFile(destFolder, zip, permissions, shouldOverride);

    }

    /**
     * Unpacking a zip file stored as a local file. By specifying the file input stream
     * it will load the zip file and will unzip it into specific directory.
     *
     * @param destFolder     {@link String} - The directory path to the destination.
     * @param fis            {@link FileInputStream} - Input stream to the zip file.
     * @param permissions    {@link String} - In case the files don't exist, it will create new
     *                       file or dir with the following permissions attribute.
     * @param shouldOverride {@link boolean} - Should override the destination files if already
     *                       exist.
     * @throws FileNotFoundException    - In case the file was not found.
     * @throws IOException              - In case there was read/write problem during unpacking.
     * @throws IllegalArgumentException - If the {@param context} has Null pointer will throw
     *                                  RuntimeException.
     * @since 1.2.0
     */
    public static void unpackZipFile(@Nullable final String destFolder, final FileInputStream fis,
                                     @Nullable final String permissions,
                                     final boolean shouldOverride) throws IOException {

        unpackZipFile(destFolder, new ZipInputStream(fis), permissions, shouldOverride);
    }

    /**
     * Unpacking a zip file stored as a local file. By specifying the file input stream
     * it will load the zip file and will unzip it into specific directory.
     *
     * @param destFolder     {@link String} - The directory path to the destination.
     * @param zip            {@link ZipInputStream} - Input stream to the zip file.
     * @param permissions    {@link String} - In case the files don't exist, it will create new
     *                       file or dir with the following permissions attribute.
     * @param shouldOverride {@link boolean} - Should override the destination files if already
     *                       exist.
     * @throws FileNotFoundException    - In case the file was not found.
     * @throws IOException              - In case there was read/write problem during unpacking.
     * @throws IllegalArgumentException - If the {@param context} has Null pointer will throw
     *                                  RuntimeException.
     * @since 1.2.0
     */
    public static void unpackZipFile(@Nullable final String destFolder, final ZipInputStream zip,
                                     @Nullable final String permissions,
                                     final boolean shouldOverride) throws IOException {

        Validate.notNull(zip, "The zip should be valid object.");

        // Loop through all the files and folders
        for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {

            final String innerFileName = entry.getName();
            final String destinationDir = (destFolder != null) ? destFolder : Empty.string();
            final File innerFile = new File(destinationDir, innerFileName);

            Debug.dMsg(TAG, "Extracting: " + innerFileName + "...", "unpackZipFile");
            if (innerFile.exists() && shouldOverride) {
                FileUtils.deleteFileIfExists(innerFile);
            }
            if (shouldOverride || !innerFile.exists()) {
                // Check if it is a folder
                if (entry.isDirectory()) {
                    // Its a folder, create that folder
                    FileUtils.createDir(innerFile, permissions);
                } else {
                    // Create a file output stream
                    FileUtils.saveFile(zip, innerFile, permissions);
                }
            }
            // Close the current entry
            zip.closeEntry();
        }
        zip.close();
    }

    /**
     * Creates input stream out of a resource file with specific resource ID, stored in the RAW
     * resources package.
     *
     * @param id      {@link int} - The resource id of the file.
     * @param context {@link Context} - The current context.
     * @return {@link ZipInputStream} - Returns input stream to the resource file.
     * @throws android.content.res.Resources.NotFoundException - In case the resource was not found.
     * @since 1.2.0
     */
    @Nullable
    public static ZipInputStream getZipInputStream(@NonNull final Context context,
                                                   @RawRes final int id) {

        final InputStream in = getRaw(context, id);
        return (in != null) ? FileUtils.createZipInputStream(in) : null;
    }

    /**
     * Creates input stream out of a resource file with specific resource ID, stored in the RAW
     * resources package.
     *
     * @param context {@link Context} - The current context. Null-safe.
     * @param id      {@link int} - The resource id of the file.
     * @return {@link InputStream} - Returns input stream to the resource file.
     * @throws android.content.res.Resources.NotFoundException - In case the resource was not found.
     * @since 1.2.0
     */
    @Nullable
    public static InputStream getRaw(final Context context, @RawRes final int id) {

        if (Check.isNull(TAG, "context", "getRaw", context)) {
            return null;
        }
        return context.getResources().openRawResource(id);
    }

    /**
     * Return a resource identifier for the given resource name. A fully qualified resource name
     * is of the form "package:type/entry". The first two components (package and type) are
     * optional if defType and defPackage, respectively, are specified here.
     *
     * @param context {@link Context} - The current context. Null-safe.
     * @param resName {@link String} - The resource name of the file.
     * @param resType {@link String} - Optional default resource type to find, if "type/" is not
     *                included in the name. Can be null to require an explicit type.
     * @return {@link int} - Returns the resource id of the file, associated resource identifier.
     * Returns 0 if no such resource was found; the {@param context},
     * {@param resName}, or {@param restype} is invalid. (0 is not a valid
     * resource ID.)
     * @since 1.2.0
     */
    @IntegerRes
    public static int getResIdByName(final Context context, final String resName, final String resType) {

        if (InitCheck.notPass(TAG, "getResIdByName", context, resName, resType)) {
            return 0;
        }
        final String packageName = context.getPackageName();
        return context.getResources().getIdentifier(resName, resType, packageName);
    }

}