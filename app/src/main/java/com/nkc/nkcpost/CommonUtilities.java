package com.nkc.nkcpost;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Jumpon-pc on 7/10/2558.
 */
public final class CommonUtilities {

    // give your server registration url here
    static final String SERVER_URL = "https://it.nkc.kku.ac.th/backend/api/gcm_register";

    // Google project id
    static final String SENDER_ID = "90267907696";
    static final String API_KEY = "AIzaSyA9wFLHlRZbstBfets6VdH_wz9PgvCAafo";

    /**
     * Tag used on log messages.
     */
    static final String TAG = "NKCPost GCM";

    static final String DISPLAY_MESSAGE_ACTION = "com.nkc.nkcpost.DISPLAY_MESSAGE";

    static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p/>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}
