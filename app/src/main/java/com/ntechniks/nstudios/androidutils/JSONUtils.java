package com.ntechniks.nstudios.androidutils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Util class to help with various JSON reformings.
 *
 * @author Nikola Georgiev
 * @version 1.0
 * @since 1.2.0
 */
public final class JSONUtils {

    private static final String TAG = JSONUtils.class.getSimpleName();

    /**
     * Main constructor with private accessor to prevent instantiating the class.
     *
     * @since 1.2.0
     */
    private JSONUtils() {
        // Nothing to implement here.
    }

    /**
     * Parses the JSON string raw data to a {@link JSONObject}.
     *
     * @param data {@link String} - The raw data as string, it suppose to be JSON object as string.
     * @return {@link JSONObject} - The JSON string raw data as {@link JSONObject}. In case an
     * error occurs during the parsing process, it will return Null Pointer.
     * @since 1.2.0
     */
    public static JSONObject parseJSONData(final String data) {

        if (Check.isNull(TAG, "data", "parseJSONData", data)) {
            return null;
        }

        try {
            return new JSONObject(data);
        } catch (JSONException je) {
            Debug.error(TAG, "An error occurred, while trying to parse a string to JSON object.",
                    "parseJSONData", je);
        }
        return null;
    }
}
