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

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import org.apache.commons.lang3.StringUtils;

/**
 * Official Git repository at https://github.com/marulka/android-utils
 *
 * @author Nikola Georgiev
 * @version 1.01
 * @since 1.0
 */
public class Empty {

    /**
     * Main constructor with private accessor to prevent instantiating the class.
     *
     * @since 1.2.0
     */
    private Empty() {
        // Nothing to implement here.
    }

    /**
     * Returns empty String. The string HAS a pointer, but also has a ZERO
     * length.
     *
     * @return {@link String}
     * @since 1.0
     */
    public static String string() {
        return StringUtils.EMPTY;
    }

    // =============================================================================================

    /**
     * Returns empty {@link DialogInterface.OnClickListener}. The listener HAS
     * pointer, but does NOT have an implemented business logic inside the
     * onClick() callback.
     *
     * @return {@link DialogInterface.OnClickListener}
     * @since 1.0
     */
    public static DialogInterface.OnClickListener onClickListener() {

        return new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Does nothing.
            }
        };
    }
}
