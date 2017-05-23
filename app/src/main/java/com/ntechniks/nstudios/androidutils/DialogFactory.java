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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.ntechniks.nstudios.androidutils.R;

/**
 * Official Git repository at https://github.com/marulka/android-utils
 * 
 * @author Nikola Georgiev
 * @version 1.07
 * @since 1.0
 * 
 */
public class DialogFactory {

	/**
	 * The name of the class.
	 * 
	 * @since 1.0
	 */
	private static final String TAG = "DialogFactory";

	// =================================================================================================================================

	/**
	 * Builds AlertDialog which shows only information to the User and has only
	 * neutral button for feedback. Note: You should always call the show()
	 * method to pop-up the message on the device screen.
	 * 
	 * @param activity
	 *            {@link Activity} - The activity which this AlertDialog will be
	 *            attached to.
	 * @param title
	 *            int - The string resource which will be displayed as Title.
	 * @param message
	 *            int - The string resource which will be displayed as
	 *            informational message.
	 * @param neutralButtonName
	 *            int - The string resource which will be displayed as neutral
	 *            button.
	 * @param listener
	 *            {@link OnClickListener} - Callback that will be attached as
	 *            listener, when the neutral button has been pressed.
	 * @return {@link android.app.AlertDialog.Builder}- The built AlertDialog
	 *         which should be displayed.
	 * @since 1.0
	 */
	public static AlertDialog.Builder buildInfoDialog(@NonNull Activity activity, @StringRes int title,
			@StringRes int message, @StringRes int neutralButtonName, @NonNull OnClickListener listener) {

		AlertDialog.Builder adb = null;

		if (InitCheck.comboCheck(TAG, "buildInfoDialog", new Object[] { activity, listener }, new int[] { title,
				message, neutralButtonName })) {

			// Initialize Dialog.
			adb = new AlertDialog.Builder(activity);

			// Set Dialog parameters.
			adb.setTitle(title);
			adb.setMessage(message);
			adb.setNeutralButton(neutralButtonName, listener);

		}

		return adb;
	}

	// =================================================================================================================================

	/**
	 * Builds AlertDialog which shows list for selection represented as an
	 * optional view parameter. The alert dialog has positive and negative
	 * buttons for feedback. Note: You should always call the show() method to
	 * pop-up the message on the device screen.
	 * 
	 * @param activity
	 *            {@link Activity} - The activity which this AlertDialog will be
	 *            attached to.
	 * @param title
	 *            int - The string resource which will be displayed as Title.
	 * @param optionalView
	 *            {@link View} - The view which will be added to the Dialog
	 *            under the title.
	 * @param positiveButtonName
	 *            int - The string resource which will be displayed as positive
	 *            button label.
	 * @param negativeButtonName
	 *            int - The string resource which will be displayed as negative
	 *            button label.
	 * @param negativeListener
	 *            {@link OnClickListener} - The callback that will be called
	 *            when the negative button is pressed.
	 * @param positiveListener
	 *            {@link OnClickListener} - The callback that will be called
	 *            when the positive button is pressed.
	 * @return {@link android.app.AlertDialog.Builder} - The built AlertDialog
	 *         which will be displayed.
	 * @since 1.0
	 */
	public static AlertDialog.Builder buildSelectFromListDialog(@NonNull Activity activity, @StringRes int title,
			@NonNull View optionalView, @StringRes int positiveButtonName, @StringRes int negativeButtonName,
			@NonNull OnClickListener negativeListener, @NonNull OnClickListener positiveListener) {

		final AlertDialog.Builder adb = buildPositiveNegativeDialog(activity, title, optionalView, positiveButtonName,
				negativeButtonName, negativeListener, positiveListener);

		return adb;
	}

	// =================================================================================================================================

	/**
	 * Builds AlertDialog which shows list of items represented as an ListView
	 * parameter. The alert dialog does NOT have any buttons for feedback. Note:
	 * You should always call the show() method to pop-up the message on the
	 * device screen.
	 * 
	 * @param activity
	 *            {@link Activity} - The activity which this AlertDialog will be
	 *            attached to.
	 * @param listview
	 *            {@link View} - The ListView which will be used to represent
	 *            the context items.
	 * @return {@link android.app.AlertDialog.Builder} - The built AlertDialog
	 *         which will be displayed.
	 * @since 1.04
	 */
	public static AlertDialog.Builder buildCustomContextMenuDialog(@NonNull Activity activity,
			@NonNull ListView listview) {

		AlertDialog.Builder adb = null;

		if (InitCheck.pass(TAG, "buildSelectFromListDialog", new Object[] { activity, listview })) {

			// Initialize Dialog.
			adb = new AlertDialog.Builder(activity);

			// Set Dialog parameters.
			adb.setView(listview);

		}

		return adb;
	}

	// =================================================================================================================================

	/**
	 * Builds AlertDialog which has an optional view with position under the
	 * dialog Title. The Title is a String parameter. The alert dialog has
	 * positive and negative buttons for feedback. Note: You should always call
	 * the show() method to pop-up the message on the device screen.
	 * 
	 * @param activity
	 *            {@link Activity} - The activity which this AlertDialog will be
	 *            attached to.
	 * @param title
	 *            int - The string resource which will be displayed as Title.
	 * @param optionalView
	 *            {@link View} - The view which will be added to the Dialog
	 *            under the title.
	 * @param positiveButtonName
	 *            int - The string resource which will be displayed as positive
	 *            button label.
	 * @param negativeButtonName
	 *            int - The string resource which will be displayed as negative
	 *            button label.
	 * @param negativeListener
	 *            {@link OnClickListener} - The callback that will be called
	 *            when the negative button is pressed.
	 * @param positiveListener
	 *            {@link OnClickListener} - The callback that will be called
	 *            when the positive button is pressed.
	 * @return {@link android.app.AlertDialog.Builder} - The built AlertDialog
	 *         which will be displayed.
	 * @since 1.0
	 */
	public static AlertDialog.Builder buildPositiveNegativeDialog(@NonNull Activity activity, @NonNull String title,
			@NonNull View optionalView, @StringRes int positiveButtonName, @StringRes int negativeButtonName,
			@NonNull OnClickListener negativeListener, @NonNull OnClickListener positiveListener) {

		AlertDialog.Builder adb = null;

		if (InitCheck.comboCheck(TAG, "buildPositiveNegativeDialog", new Object[] { activity, title, optionalView,
				negativeListener, positiveListener }, new int[] { negativeButtonName, positiveButtonName })) {

			// Initialize Dialog.
			adb = new AlertDialog.Builder(activity);

			// Set Dialog parameters.
			adb.setTitle(title);
			adb.setView(optionalView);
			adb.setNegativeButton(negativeButtonName, negativeListener);
			adb.setPositiveButton(positiveButtonName, positiveListener);

		}

		return adb;
	}

	// =================================================================================================================================

	/**
	 * Builds AlertDialog which has an optional view with position under the
	 * dialog Title. The Title is an int id of the string resource. The alert
	 * dialog has positive and negative buttons for feedback. Note: You should
	 * always call the show() method to pop-up the message on the device screen.
	 * 
	 * @param activity
	 *            {@link Activity} - The activity which this AlertDialog will be
	 *            attached to.
	 * @param title
	 *            int - The string resource which will be displayed as Title.
	 * @param optionalView
	 *            {@link View} - The view which will be added to the Dialog
	 *            under the title.
	 * @param positiveButtonName
	 *            int - The string resource which will be displayed as positive
	 *            button label.
	 * @param negativeButtonName
	 *            int - The string resource which will be displayed as negative
	 *            button label.
	 * @param negativeListener
	 *            {@link OnClickListener} - The callback that will be called
	 *            when the negative button is pressed.
	 * @param positiveListener
	 *            {@link OnClickListener} - The callback that will be called
	 *            when the positive button is pressed.
	 * @return {@link android.app.AlertDialog.Builder} - The built AlertDialog
	 *         which should be displayed.
	 * @since 1.06
	 */
	public static AlertDialog.Builder buildPositiveNegativeDialog(@NonNull Activity activity, int title,
			@NonNull View optionalView, @StringRes int positiveButtonName, @StringRes int negativeButtonName,
			@NonNull OnClickListener negativeListener, @NonNull OnClickListener positiveListener) {

		if (InitCheck.comboCheck(TAG, "buildPositiveNegativeDialog", new Object[] { activity }, new int[] { title })) {

			final String titleAsString = activity.getString(title);

			return buildPositiveNegativeDialog(activity, titleAsString, optionalView, positiveButtonName,
					negativeButtonName, negativeListener, positiveListener);
		}

		return null;
	}

	// =================================================================================================================================

	/**
	 * Builds AlertDialog which has optional View under the Title and a neutral
	 * button for feedback. Note: You should always call the show() method to
	 * pop-up the message on the device screen.
	 * 
	 * @param activity
	 *            {@link Activity} - The activity which this AlertDialog will be
	 *            attached to.
	 * @param title
	 *            int - The string resource which will be displayed as Title.
	 * @param optionalView
	 *            {@link View} - The view which will be added to the Dialog
	 *            under the title.
	 * @param neutralButtonName
	 *            int - The string resource which will be displayed as positive
	 *            button label.
	 * @param neutralListener
	 *            {@link OnClickListener} - The callback that will be called
	 *            when the positive button is pressed.
	 * @return {@link android.app.AlertDialog.Builder} - The built AlertDialog
	 *         which will be displayed.
	 * @since 1.0
	 */
	public static AlertDialog.Builder buildNeutralOptionsDialog(@NonNull Activity activity, @NonNull String title,
			@NonNull View optionalView, @StringRes int neutralButtonName, @NonNull OnClickListener neutralListener) {

		AlertDialog.Builder adb = null;

		if (InitCheck.comboCheck(TAG, "buildNeutralOptionsDialog", new Object[] { activity, title, optionalView,
				neutralListener }, new int[] { neutralButtonName })) {

			// Initialize Dialog.
			adb = new AlertDialog.Builder(activity);

			// Set Dialog parameters.
			adb.setTitle(title);
			adb.setView(optionalView);
			adb.setNeutralButton(neutralButtonName, neutralListener);

		}

		return adb;
	}

	// =================================================================================================================================

	/**
	 * Shows up a Toast message with SHORT duration. The message should be a
	 * String.
	 * 
	 * @param context
	 *            {@link Context} - The context that the Toast will be attached
	 *            to.
	 * @param message
	 *            {@link String} - The String that will be shown as a message in
	 *            the Toast.
	 * @since 1.05
	 */
	public static void showToastMessage(@NonNull Context context, @NonNull String message) {

		if (InitCheck.pass(TAG, "showToastMessage", new Object[] { context, message })) {

			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		}

	}

	// =================================================================================================================================

	/**
	 * Shows up a Toast message with SHORT duration. The message parameter
	 * should be the int id of the string resource.
	 * 
	 * @param context
	 *            {@link Context} - The context that the Toast will be attached
	 *            to.
	 * @param messageResId
	 *            int - The id of the string resource which will be shown as a
	 *            message.
	 * @since 1.05
	 */
	public static void showToastMessage(@NonNull Context context, @StringRes int messageResId) {

		if (InitCheck.comboCheck(TAG, "showToastMessage", new Object[] { context }, new int[] { messageResId })) {

			Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();
		}

	}

	// =================================================================================================================================

	/**
	 * Initializes the container layout and the views for the Credentials
	 * Dialog.
	 * 
	 * @param activity
	 *            {@link Activity} - The activity which the Alert Dialog will be
	 *            attached to.
	 * @return {@link LinearLayout} - The Linear layout which will be used as an
	 *         optional View and container.
	 * @since 1.07
	 */
	public static LinearLayout initCredentialsDialogViews(@NonNull Activity activity) {

		final String methodName = "initCredentialsDialogViews";
		LinearLayout container = null;
		final ViewGroup nullParent = null;

		if (Check.notNull(TAG, "activity Activity", methodName, activity)) {

			container = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.dialog_login_form, nullParent);

			if (Check.notNull(TAG, "container LinearLayout", methodName, container)) {

				final EditText passwrdField = (EditText) container.findViewById(R.id.dialogLoginFormEditBoxPasswrd);
				final CheckBox checkBox = (CheckBox) container.findViewById(R.id.dialogLoginFormCheckBoxShowPasswrd);

				if (Check.notNull(TAG, "checkBox CheckBox", methodName, checkBox)) {

					checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

							if (Check.notNull(TAG, "passwrdField EditText", "onCheckedChanged in " + methodName,
									passwrdField)) {

								passwrdField.setTransformationMethod((isChecked) ? null
										: new PasswordTransformationMethod());

							}
						}
					});
				}
			}
		}

		return container;
	}
}