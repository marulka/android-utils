package com.ntechniks.nstudios.androidutils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Utility class that helps extracting common currency logics.
 *
 * @author Nikola Georgiev
 * @version 1.0
 * @since 1.1.0
 */
public final class BigDecimalUtils {

    public static final String TAG = BigDecimalUtils.class.getSimpleName();

    /**
     * Main constructor with private accessor to prevent instantiating the class.
     *
     * @since 1.2.0
     */
    private BigDecimalUtils() {
        // Nothing to implement here.
    }

    /**
     * This method formats a parsed {@link BigDecimal} value to a string according to the current
     * device locale.
     *
     * @param number {@link BigDecimal} - The decimal number that should be formatted.
     * @return {@link String} - The formatted decimal number as string.
     * @since 1.1.0
     */
    public static String formatBigDecimalAsString(final BigDecimal number) {

        if (Check.isNull(TAG, "number", "formatBigDecimalAsString", number))
            return null;

        final NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
        if (nf instanceof DecimalFormat) {
            final DecimalFormat formatter = (DecimalFormat) nf;
            formatter.setDecimalSeparatorAlwaysShown(true);
            formatter.setMinimumFractionDigits(2);
            formatter.setMaximumFractionDigits(2);
            formatter.setRoundingMode(RoundingMode.HALF_UP);
            try {
                return formatter.format(number);
            } catch (NumberFormatException nfe) {
                Debug.warn(TAG, "The number input " + number + " is wrong type.",
                        "formatBigDecimalAsString");
            }
        }
        return null;
    }
}
