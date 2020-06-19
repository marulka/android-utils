package com.ntechniks.nstudios.androidutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessControlException;
import java.util.Objects;
import java.util.zip.ZipInputStream;

/**
 * File Utilities class that helps saving files to a storage.
 *
 * @author Nikola Georgiev
 * @version 1.0
 * @since 1.2.0
 */
public final class FileUtils {

    public static final String TAG = FileUtils.class.getSimpleName();
    public static final String DEFAULT_DIR_PERMISSIONS = "rwxr-xr-x";

    private static final String NOT_PERMITTED = " Not Permitted!";

    /**
     * Main constructor with private accessor to prevent instantiating the class.
     *
     * @since 1.2.0
     */
    private FileUtils() {
        // Nothing to implement here.
    }

    /**
     * Use this method in order to save any file as {@link InputStream} into the device internal
     * storage files. The file will be located within the application package in directory
     * "files". The location of your application package is different to each Android system and
     * may vary. Here you can find just a sample path.
     * <p>
     * Example: "file:///data/data/user/0/org/example/files/your-sub-dir/"
     *
     * @param context     {@link Context} - The base application context.
     * @param inputStream {@link InputStream} - The file you'd like to save as input stream.
     * @param subDir      {@link String} - The sub-directory name where you'd like to save the file.
     * @param fileName    {@link String} - The name of the file that will be saved.
     * @param permissions {@link String} - In case the directory doesn't exist, it will create new
     *                    file with the following permissions attribute.
     * @return {@link Uri} - The Uri path of the saved file. NULL - in case either some of the
     * context, inputStream, and fileName has Null Pointer, the file cannot be saved due to some
     * interruption, missing permission, or the storage is full.
     * @throws AccessControlException - the AccessController to indicate that a requested access
     *                                (to a critical system resource such as the file system or
     *                                the network) is denied.
     * @throws IllegalAccessException {@link IllegalAccessException} -
     * @since 1.2.0
     */
    @Nullable
    public static Uri saveInternalFile(final Context context, final InputStream inputStream,
                                       @Nullable final String subDir, final String fileName,
                                       @Nullable final String permissions) throws IllegalAccessException {

        if (InitCheck.notPass(TAG, "saveInternalFile", context, inputStream, fileName)) {
            return null;
        }
        final File file = createInternalFile(context, subDir, fileName);
        return saveFile(inputStream, file, permissions);
    }

    /**
     * Use this method in order to save any file as {@link InputStream} into the device internal
     * or external storage files. First should check whether you have permissions to read/write
     * files at this location, otherwise RuntimeException will be thrown.
     *
     * @param inputStream {@link InputStream} - The file you'd like to save as input stream.
     * @param file        {@link String} - The file that will be saved.
     * @param permissions {@link String} - In case the directory doesn't exist, it will create new
     *                    file with the following permissions attribute.
     * @return {@link Uri} - The Uri path of the saved file. NULL - in case either some of the
     * inputStream, directory, or fileName has Null Pointer, the file cannot be saved due to some
     * interruption, missing permission, or the storage is full.
     * @throws AccessControlException - the AccessController to indicate that a requested access
     *                                (to a critical system resource such as the file system or
     *                                the network) is denied.
     * @throws IllegalAccessException {@link IllegalAccessException} -
     * @since 1.2.0
     */
    @Nullable
    public static Uri saveFile(final InputStream inputStream, final File file,
                               @Nullable final String permissions) throws IllegalAccessException {

        if (InitCheck.notPass(TAG, "saveFile", inputStream, file, permissions)) {
            return null;
        }
        return saveFileImpl(inputStream, file, permissions);
    }

    /**
     * Use this method in order to save any file as {@link InputStream} into the device internal
     * or external storage files. First should check whether you have permissions to read/write
     * files at this location, otherwise RuntimeException will be thrown.
     *
     * @param inputStream {@link InputStream} - The file you'd like to save as input stream.
     * @param directory   {@link String} - The directory name where you'd like to save the file.
     * @param fileName    {@link String} - The name of the file that will be saved.
     * @param accessMode  {@link String} - In case the directory doesn't exist, it will create new
     *                    file with the following permissions attribute.
     * @return {@link Uri} - The Uri path of the saved file. NULL - in case either some of the
     * inputStream, directory, or fileName has Null Pointer, the file cannot be saved due to some
     * interruption, missing permission, or the storage is full.
     * @throws AccessControlException - the AccessController to indicate that a requested access
     *                                (to a critical system resource such as the file system or
     *                                the network) is denied.
     * @throws IllegalAccessException {@link IllegalAccessException} -
     * @since 1.2.0
     */
    @Nullable
    public static Uri saveFile(final InputStream inputStream, final File directory,
                               final String fileName, final int accessMode) throws IllegalAccessException {

        if (InitCheck.notPass(TAG, "saveFile", inputStream, directory, fileName, accessMode)) {
            return null;
        }
        return saveFileImpl(inputStream, new File(directory, fileName),
                PermissionUtils.numberToString(accessMode));
    }

    @Nullable
    private static Uri saveFileImpl(@NonNull final InputStream inputStream, final File file,
                                    @Nullable final String permissions) throws IllegalAccessException {

        if (file == null) {
            return null;
        }
        if (!Objects.requireNonNull(createFile(file, permissions)).exists()) {
            return null;
        }

        // Buffer the output to the file
        final int bufferSize = 2048;
        try (final BufferedOutputStream bufferedOutputStream =
                     new BufferedOutputStream(new FileOutputStream(file), bufferSize)) {

            // Write the contents
            int count;
            final byte[] data = new byte[bufferSize];
            while ((count = inputStream.read(data, 0, bufferSize)) != -1) {
                bufferedOutputStream.write(data, 0, count);
            }
            // Flush the buffers
            bufferedOutputStream.flush();

        } catch (final IOException ioe) {
            Debug.error(TAG, "write a file " + file.getAbsolutePath(), "saveFileImpl", ioe);
            return null;
        }
        return Uri.fromFile(file);
    }

    /**
     * Use this method to save a temporary image file, in case the internal device storage is
     * writable. The file will be located within the application package in directory
     * "files/cache". The location of your application package is different to each Android system
     * and
     * may vary. Here you can find just a sample path.
     * <p>
     * Example: "file:///data/data/user/0/org/example/files/cache/your-sub-dir/"
     *
     * @param context  {@link Context} - The base application context.
     * @param bitmap   {@link Bitmap} - An instance of the image bitmap that will be saved.
     * @param subDir   {@link String} - The sub-directory name where you'd like to save the file.
     * @param fileName {@link String} - The name of the file that will be saved.
     * @return {@link Uri} - The Uri path of the saved file. NULL - in case either some of the
     * context, bitmap, and fileName have Null Pointer.
     * @throws AccessControlException - the AccessController to indicate that a requested access
     *                                (to a critical system resource such as the file system or
     *                                the network) is denied.
     * @throws IllegalAccessException {@link IllegalAccessException} -
     * @since 1.2.0
     */
    @Nullable
    public static Uri saveTempBitmap(final Context context, final Bitmap bitmap,
                                     @Nullable final String subDir, final String fileName) throws IllegalAccessException {

        return saveImage(context, bitmap, subDir, fileName);
    }

    /**
     * Use this method to save image file, in case the internal device storage is writable.
     *
     * @param context  {@link Context} - The base application context.
     * @param bitmap   {@link Bitmap} - An instance of the image bitmap that will be saved.
     * @param subDir   {@link String} - The sub-directory name where you'd like to save the file.
     * @param fileName {@link String} - The name of the file that will be saved.
     * @return {@link Uri} - The Uri path of the saved file. NULL - in case either some of the
     * context, bitmap, and fileName have Null Pointer.
     * @throws AccessControlException - the AccessController to indicate that a requested access
     *                                (to a critical system resource such as the file system or
     *                                the network) is denied.
     * @throws IllegalAccessException {@link IllegalAccessException} -
     * @since 1.2.0
     */
    @Nullable
    public static Uri saveImage(final Context context, final Bitmap bitmap,
                                @Nullable final String subDir, final String fileName) throws IllegalAccessException {

        if (Check.notNull(TAG, "bitmap", "saveImage", bitmap)) {
            return null;
        }
        final File file = createTempFile(context, subDir, fileName);
        if (file == null)
            return null;

        try (final FileOutputStream fos = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
        } catch (IOException ioe) {
            Debug.error(TAG, "convert and save bitmap file with name: " + fileName, "saveImage", ioe);
            return null;
        }
        return Uri.fromFile(file);
    }

    /**
     * @param file {@link File}
     * @throws IOException              {@link IOException} -
     * @throws IllegalArgumentException {@link IllegalArgumentException} -
     */
    public static boolean deleteFileIfExists(@NonNull final File file) throws IOException {

        Validate.notNull(file, "No valid file is provided for deletion.");

        boolean isSuccessful = false;
        if (file.exists()) {
            isSuccessful = file.delete();
            if (isSuccessful) {
                Debug.info(TAG, "File " + file.getName() + " successfully deleted.");
            } else {
                Debug.warn(TAG, "File " + file.getAbsolutePath() + " might be locked.",
                        "deleteFileIfExists");
            }
        }
        return isSuccessful;
    }

    public static void deleteDirRecrusive(final String directory, final boolean force) throws IOException {

        Runtime.getRuntime().exec("rm -r" + ((force) ? "f " : " ") + directory);
    }

    /**
     * Gets file Uri based on the file name and sub dir.
     *
     * @param context  {@link Context} - The base application context.
     * @param subDir   {@link String} - The sub-directory name where you'd like to save the file.
     * @param fileName {@link String} - The name of the file that is saved.
     * @return {@link Uri} - The Uri path of the saved file. NULL - in case either some of the
     * context, and fileName have Null Pointer, or the file cannot be created.
     * @throws AccessControlException - the AccessController to indicate that a requested access
     *                                (to a critical system resource such as the file system or
     *                                the network) is denied.
     * @throws IllegalAccessException {@link IllegalAccessException} -
     * @since 1.2.0
     */
    public static Uri getTempFileUri(final Context context, @Nullable final String subDir,
                                     final String fileName) throws IllegalAccessException {

        final File tempFile = createTempFile(context, subDir, fileName);
        return (tempFile != null) ? Uri.fromFile(tempFile) : null;
    }

    /**
     * Gets file Uri based on the file name and sub dir in the device's Internal Storage.
     *
     * @param context  {@link Context} - The base application context.
     * @param subDir   {@link String} - The sub-directory name where you'd like to save the file.
     * @param fileName {@link String} - The name of the file that is saved.
     * @return {@link Uri} - The Uri path of the saved file. NULL - in case either some of the
     * context, and fileName have Null Pointer, or the file cannot be created.
     * @throws AccessControlException - the AccessController to indicate that a requested access
     *                                (to a critical system resource such as the file system or
     *                                the network) is denied.
     * @throws IllegalAccessException {@link IllegalAccessException} -
     * @since 1.2.0
     */
    public static Uri getInternalFileUri(final Context context, @Nullable final String subDir,
                                         final String fileName) throws IllegalAccessException {

        final File tempFile = createInternalFile(context, subDir, fileName);
        return (tempFile != null) ? Uri.fromFile(tempFile) : null;
    }

    /**
     * Creates temporary file in the cache directory, in case file cannot be
     * created it will return Null Pointer.
     *
     * @param context  {@link Context} - The base application context.
     * @param subDir   {@link String} - The sub-directory name where you'd like to save the file.
     * @param fileName {@link String} - The name of the file that is saved.
     * @return {@link Uri} - The Uri path of the saved file. NULL - in case either some of the
     * context, and fileName have Null Pointer, or the file cannot be created.
     * @throws AccessControlException - the AccessController to indicate that a requested access
     *                                (to a critical system resource such as the file system or
     *                                the network) is denied.
     * @throws IllegalAccessException {@link IllegalAccessException} -
     * @since 1.2.0
     */
    public static File createTempFile(final Context context, @Nullable final String subDir,
                                      final String fileName) throws IllegalAccessException {

        if (InitCheck.notPass(TAG, "createTempFile", context, fileName))
            return null;

        if (isInternalStorageWritable(context)) {
            final String destinationDir = (subDir != null) ? File.separator + subDir : Empty.string();
            final String cacheDir = context.getCacheDir().getAbsolutePath();
            final File internalStorageSubDir = new File(cacheDir + destinationDir);
            return createFile(internalStorageSubDir, fileName, null);
        }
        return null;
    }

    /**
     * Creates file in the Internal Storage directory, in case file cannot be
     * created it will return Null Pointer.
     *
     * @param context  {@link Context} - The base application context.
     * @param subDir   {@link File} - The sub-directory name where you'd like to save the file.
     * @param fileName {@link String} - The name of the file that is saved.
     * @return {@link Uri} - The Uri path of the saved file. NULL - in case either some of the
     * context, and fileName have Null Pointer, or the file cannot be created.
     * @throws AccessControlException - the AccessController to indicate that a requested access
     *                                (to a critical system resource such as the file system or
     *                                the network) is denied.
     * @throws IllegalAccessException {@link IllegalAccessException} -
     * @since 1.2.0
     */
    public static File createInternalFile(final Context context, @Nullable final File subDir,
                                          final String fileName) throws IllegalAccessException {

        final String dir = (subDir != null) ? subDir.getAbsolutePath() : null;
        return createInternalFile(context, dir, fileName);
    }

    /**
     * Creates file in the Internal Storage directory, in case file cannot be
     * created it will return Null Pointer.
     *
     * @param context  {@link Context} - The base application context.
     * @param subDir   {@link String} - The sub-directory name where you'd like to save the file.
     * @param fileName {@link String} - The name of the file that is saved.
     * @return {@link Uri} - The Uri path of the saved file. NULL - in case either some of the
     * context, and fileName have Null Pointer, or the file cannot be created.
     * @throws AccessControlException - the AccessController to indicate that a requested access
     *                                (to a critical system resource such as the file system or
     *                                the network) is denied.
     * @throws IllegalAccessException {@link IllegalAccessException} -
     * @since 1.2.0
     */
    public static File createInternalFile(final Context context, @Nullable final String subDir,
                                          final String fileName) throws IllegalAccessException {

        if (InitCheck.notPass(TAG, "createInternalFile", context, fileName))
            return null;

        if (isInternalStorageWritable(context)) {
            final String fileSeparator =
                    (subDir != null && subDir.charAt(0) != File.separatorChar) ?
                            File.separator : Empty.string();
            final File internalStorageDir = context.getFilesDir();
            final File destinationDir = (subDir != null) ? new File(internalStorageDir,
                    fileSeparator + subDir) : internalStorageDir;
            return createFile(destinationDir, fileName, null);
        }
        return null;
    }

    /**
     * Creates {@link File} object based on a directory path and file name. It creates instance
     * only when the given directory is readable and the file can be written.
     *
     * @param directory   {@link File} - The directory where you'd like to save the file.
     * @param fileName    {@link String} - The name of the file that is saved.
     * @param permissions {@link String} - In case the directory doesn't exist, it will create
     *                    new file and dir with the following permissions attribute.
     * @return {@link File} - The newly created file in the given directory with the given file name.
     * @throws AccessControlException - the AccessController to indicate that a requested access
     *                                (to a critical system resource such as the file system or
     *                                the network) is denied.
     * @throws IllegalAccessException {@link IllegalAccessException} -
     * @since 1.2.0
     */
    @Nullable
    public static File createFile(@NonNull final File directory, @NonNull final String fileName,
                                  @Nullable String permissions) throws IllegalAccessException {

        final File file = (isExistingReadableDir(directory) || createDir(directory, permissions)) ?
                new File(directory, fileName) : null;
        return createFile(file, permissions);
    }

    /**
     * Creates {@link File} object based on a directory path and file name. It creates instance
     * only when the given directory is readable and the file can be written.
     *
     * @param file        {@link File} - The file that will be saved.
     * @param permissions {@link String} - In case the file doesn't exist, it will create new
     *                    file or dir with the following permissions attribute.
     * @return {@link File} - The newly created file in the given directory with the given file name.
     * @throws AccessControlException - the AccessController to indicate that a requested access
     *                                (to a critical system resource such as the file system or
     *                                the network) is denied.
     * @throws IllegalAccessException {@link IllegalAccessException} -
     * @since 1.2.0
     */
    @Nullable
    public static File createFile(final File file, @Nullable final String permissions) throws IllegalAccessException {

        if (Check.notNull(TAG, "file", "createFile", file) && !file.exists()) {
            try {
                if (file.createNewFile()) {
                    return changeFilePermissions(file, permissions);
                }
            } catch (final IOException ioe) {
                Debug.error(TAG, "create a file in the file storage", "createFile", ioe);
            }
        }
        return file;
    }

    /**
     * @param directoryPath {@link String} - The directory where you'd like to save.
     * @param permissions   {@link String} - In case the directory doesn't exist, it will create new
     *                      dir with the following permissions attribute.
     * @return {@link boolean} - Whether the directory has been created or not.
     * @throws AccessControlException - the AccessController to indicate that a requested access
     *                                (to a critical system resource such as the file system or
     *                                the network) is denied.
     * @throws IllegalAccessException {@link IllegalAccessException} -
     * @since 1.2.0
     */
    public static boolean createDir(final String directoryPath, @Nullable String permissions) throws IllegalAccessException {

        return createDir(new File(directoryPath), permissions);
    }

    /**
     * Creates only if doesn't exists.
     *
     * @param directory   {@link File} - The directory where you'd like to save.
     * @param permissions {@link String} - In case the directory doesn't exist, it will create new
     *                    dir with the following permissions attribute.
     * @return {@link boolean} - Whether the directory has been created or not.
     * @throws AccessControlException - the AccessController to indicate that a requested access
     *                                (to a critical system resource such as the file system or
     *                                the network) is denied.
     * @throws IllegalAccessException {@link IllegalAccessException} -
     * @since 1.2.0
     */
    public static boolean createDir(final File directory, @Nullable String permissions) throws IllegalAccessException {

        if (StringUtils.isEmpty(permissions)) {
            permissions = DEFAULT_DIR_PERMISSIONS;
        }
        return createDir(directory, PermissionUtils.permissionsToNumber(permissions));
    }

    /**
     * @param directory  {@link File} - The directory where you'd like to save.
     * @param accessMode {@link int} -
     * @return {@link boolean} - Whether the directory has been created or not.
     * @throws AccessControlException - the AccessController to indicate that a requested access
     *                                (to a critical system resource such as the file system or
     *                                the network) is denied.
     * @since 1.2.0
     */
    public static boolean createDir(final File directory, final int accessMode) {

        if (Check.isNull(TAG, "directory", "createDir", directory)) {
            return false;
        }
        try {
            boolean isCreated = false;
            if (!directory.exists()) {
                isCreated = directory.mkdirs();
            }
            changeFilePermissions(directory, accessMode);
            return directory.exists() || isCreated;
        } catch (SecurityException se) {
            Debug.error(TAG, "creating dir: " + directory + NOT_PERMITTED, "createDir", se);
            throw new AccessControlException(se.getLocalizedMessage());
        }
    }

    /**
     * This method checks whether the given directory is an actual directory, does exist and has
     * read privileges.
     *
     * @param directory {@link File} - The directory which you'd like to check whether exists and/or
     *                  is readable.
     * @return {@link boolean} - Whether the directory has been created or not.
     * @throws AccessControlException - the AccessController to indicate that a requested access
     *                                (to a critical system resource such as the file system or
     *                                the network) is denied.
     * @since 1.2.0
     */
    public static boolean isExistingReadableDir(final File directory) {

        final String methodName = "isExistingReadableDir";
        if (Check.notNull(TAG, "directory", methodName, directory)
                && directory.exists() && directory.isDirectory()) {
            try {
                return directory.canRead();
            } catch (final SecurityException se) {
                Debug.error(TAG, "checking readability of dir: " + directory + NOT_PERMITTED,
                        methodName, se);
                throw new AccessControlException(se.getLocalizedMessage());
            }
        }
        return false;
    }

    public static boolean isExistingWritableDir(final File directory) {

        if (isExistingReadableDir(directory)) {
            try {
                return directory.canWrite();
            } catch (final SecurityException se) {
                Debug.error(TAG, "checking writability of dir: " + directory + NOT_PERMITTED,
                        "isExistingWritableDir", se);
                throw new AccessControlException(se.getLocalizedMessage());
            }
        }
        return false;
    }

    /**
     * Checks whether a given file with name exists in the cache directory.
     *
     * @param context  {@link Context} - The base application context.
     * @param subDir   {@link String} - The sub-directory name where you'd like to save the file.
     * @param fileName {@link String} - The name of the file that is saved.
     * @return {@link boolean} - True - in case the file exists, False - in case the file does not
     * exist.
     * @throws AccessControlException - the AccessController to indicate that a requested access
     *                                (to a critical system resource such as the file system or
     *                                the network) is denied.
     * @throws IllegalAccessException {@link IllegalAccessException} -
     * @since 1.2.0
     */
    public static boolean isTempFileExisting(final Context context, final String subDir,
                                             final String fileName) throws IllegalAccessException {

        final File tempFile = createTempFile(context, subDir, fileName);
        return tempFile != null && tempFile.exists();
    }

    /**
     * Checks if internal storage is available for read and write.
     *
     * @param context {@link Context} - The base application context.
     * @return {@link boolean} - True - in case the internal storage of the device is writable,
     * False - in case has no permissions to write in the device internal storage.
     * @throws AccessControlException - the AccessController to indicate that a requested access
     *                                (to a critical system resource such as the file system or
     *                                the network) is denied.
     * @since 1.2.0
     */
    public static boolean isInternalStorageWritable(final Context context) {

        if (Check.isNull(TAG, "context", "isInternalStorageWritable", context))
            return false;

        // Create the storage directory if it does not exist
        final File internalStorageDir = context.getFilesDir();
        if (isExistingReadableDir(internalStorageDir)) {
            try {
                return internalStorageDir.canWrite();
            } catch (final SecurityException se) {
                final String whileDo =
                        "checking whether can write into dir: " + internalStorageDir.getAbsolutePath() +
                                NOT_PERMITTED;
                Debug.error(TAG, whileDo, "isExistingReadableDir", se);
                throw new AccessControlException(se.getLocalizedMessage());
            }
        }
        return false;
    }

    /**
     * Creates new {@link ZipInputStream} out of incoming {@link InputStream}. The UTF-8 charset
     * is used to decode the entry names. This method is Null-safe.
     *
     * @param inputStream {@link InputStream} - An input stream to the file, which will be used
     *                    to create {@link ZipInputStream}.
     * @return {@link ZipInputStream} - A new instance to a zip input stream. NULL - in case the
     * param inputStream has Null Pointer.
     * @since 1.2.0
     */
    public static ZipInputStream createZipInputStream(final InputStream inputStream) {

        if (Check.isNull(TAG, "inputStream", "createZipInputStream", inputStream)) {
            return null;
        }
        return new ZipInputStream(inputStream);
    }

    /**
     * @param folder
     * @param fileName
     * @return
     * @since 1.2.0
     */
    public static File changeFilePermissions(final File folder, final String fileName,
                                             final int permission) {

        return changeFilePermissions(new File(folder, fileName), permission);
    }

    /**
     * @param folderPath
     * @param fileName
     * @return
     * @since 1.2.0
     */
    public static File changeFilePermissions(final String folderPath, final String fileName, final int permission) {

        return changeFilePermissions(new File(folderPath, fileName), permission);
    }

    /**
     * Change files to "0777"
     *
     * @param path Path to file
     * @since 1.2.0
     */
    public static File changeFilePermissions(final String path, final int permission) throws
            SecurityException {

        final File file = new File(path);
        return changeFilePermissions(file, permission);
    }

    /**
     * @param file
     * @return
     * @since 1.2.0
     */
    public static File changeFilePermissions(final File file, final int number) throws
            SecurityException {

        String permissions = PermissionUtils.numberToString(number);
        return changeFilePermissions(file, permissions);
    }

    public static File changeFilePermissions(File file, @Nullable String permissions) throws
            SecurityException {

        if (permissions == null || !PermissionUtils.isValidPermissionsString(permissions)) {
            permissions = DEFAULT_DIR_PERMISSIONS;
        }
        if (Check.notNull(TAG, "file", "changeFilePermissions", file) && file.exists()) {
            file.setReadable(permissions.charAt(0) == 'r', permissions.charAt(3) != 'r');
            file.setWritable(permissions.charAt(1) == 'w', permissions.charAt(4) != 'w');
            file.setExecutable(permissions.charAt(2) == 'x', permissions.charAt(5) != 'x');
        }
        return file;
    }

    public void changeDirAccessRecursive(final File directory, final String permissions) throws IOException {

        final int accessMode = PermissionUtils.permissionsToNumber(permissions);
        if (Check.notNull(TAG, "directory", "changeDirAccessRecursively", directory) && accessMode > 0) {

            Runtime.getRuntime().exec("chmod -R " + accessMode + " " + directory.getAbsolutePath());
        }
    }

    /**
     * Depends on the Android device API will return the default Uri prefix.
     *
     * @return String - Returns file:// or content://.
     * @since 1.2.0
     */
    static String getUriPrefix() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return "content://";
        }
        return "file://";
    }
}