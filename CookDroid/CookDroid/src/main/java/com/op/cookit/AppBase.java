package com.op.cookit;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;

import com.op.cookit.model.Person;
import com.op.cookit.syncadapter.GenericAccountService;
import com.op.cookit.syncadapter.ProductsContentProvider;
import com.op.cookit.util.ShopListRest;

import java.util.Locale;

/**
 * c lass with app settings const
 */
public class AppBase extends Application {
    public static final String SETTINGS_FILE = Environment.getExternalStorageDirectory() + "/Android/data/com.op.cookcloud/preferences.dat";
    public static final String SETTINGS = "op.cookdroid";

    public static final String TAG = "cookdroid";


    public static final String PREF_CHANGELOG = "changelog";
    public static final String PREF_APP_VERSION = "app.version";

    public static final String PREF_EULA = "eula";
    public static final String PREF_EULA_ACCEPTED = "eula.accepted";

    //public static final int DB_VERSION=6;

    public static final String BASE_URL = "http://cookcloud.jelastic.neohost.net/";
    public static final String BASE_REST_URL = BASE_URL + "rest/";
    public static final String APP_RUNS_COUNT = "APP_RUNS_COUNT";

    public static ShopListRest shopListRest = new ShopListRest();

    private static SharedPreferences mPrefs;

    private Account appAccount;

    @Override
    public void onCreate() {
        super.onCreate();
        setupAppAccount();

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mPrefs.edit();

    }

    public static void installShortcurt(Context context) {
        Log.d(TAG,"installShortcurt");
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(new ComponentName(context.getPackageName(), MainActivity.class.getName()));

        Intent result = new Intent();
        result.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        result.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(R.string.app_name));
        Parcelable iconResource = Intent.ShortcutIconResource.fromContext(context, R.drawable.ic_launcher);
        result.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);
        result.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        result.putExtra("duplicate", false);
        context.sendBroadcast(result);
    }


    public void requestForcedSync() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(appAccount, ProductsContentProvider.CONTENT_AUTHORITY, bundle);
    }

    private void setupAppAccount() {
        AccountManager accountManager = AccountManager.get(this);
        Account[] accounts = accountManager.getAccountsByType(GenericAccountService
                .ACCOUNT_TYPE);
        if (accounts.length > 0) {
            appAccount = accounts[0];
            return;
        }
        Log.i(TAG, "UASpring account not present, creating...");

        appAccount = new Account(GenericAccountService.ACCOUNT_NAME, GenericAccountService.ACCOUNT_TYPE);
        //usually it will contain real user info, like login/pass
        accountManager.addAccountExplicitly(appAccount, null, null);

        //Enable AutoSync
        ContentResolver.setSyncAutomatically(appAccount, ProductsContentProvider.CONTENT_AUTHORITY, true);
    }

    public static void saveLoggedUser(Person person) {
        SharedPreferences.Editor editor = mPrefs.edit();

//        editor.putString(AppConstants.PREFS_KEY_USER_NAME, account.getName());
//        editor.putString(AppConstants.PREFS_KEY_USER_FIST_NAME, account.getFirstName());
//        editor.putString(AppConstants.PREFS_KEY_USER_LAST_NAME, account.getLastName());
//        editor.putString(AppConstants.PREFS_KEY_USER_DISPLAY_NAME, account.getDisplayName());
//        editor.putString(AppConstants.PREFS_KEY_USER_EMAIL, account.getEmail());
//        editor.putString(AppConstants.PREFS_KEY_USER_PHONE_NUMBER, account.getPhoneNumber());
//        editor.putString(AppConstants.PREFS_KEY_USER_FULL_PHONE_NUMBER, account.getFullPhoneNumber());
//        editor.putString(AppConstants.PREFS_KEY_USER_PHONE_PREFIX, account.getPhonePrefix());
//        editor.putString(AppConstants.PREFS_KEY_USER_ACCOUNT_TYPE, account.getAccountType().name());
//        editor.putString(AppConstants.PREFS_KEY_USER_SECURE_QUESTION, account.getSecurityQuestion());
//        editor.putLong(AppConstants.PREFS_KEY_USER_TIME_UPDATED, account.getTimeUpdated());
//        savePassword(account, editor, false);

        editor.commit();

    }

//    public void updateUserCredentials(UserAccount account) {
//
//        Editor editor = mPrefs.edit();
//
//        updateIfNotEmpty(editor, AppConstants.PREFS_KEY_USER_NAME, account.getName());
//        updateIfNotEmpty(editor, AppConstants.PREFS_KEY_USER_FIST_NAME, account.getFirstName());
//        updateIfNotEmpty(editor, AppConstants.PREFS_KEY_USER_LAST_NAME, account.getLastName());
//        updateIfNotEmpty(editor, AppConstants.PREFS_KEY_USER_DISPLAY_NAME, account.getDisplayName());
//        updateIfNotEmpty(editor, AppConstants.PREFS_KEY_USER_EMAIL, account.getEmail());
//        updateIfNotEmpty(editor, AppConstants.PREFS_KEY_USER_PHONE_NUMBER, account.getPhoneNumber());
//        updateIfNotEmpty(editor, AppConstants.PREFS_KEY_USER_FULL_PHONE_NUMBER, account.getFullPhoneNumber());
//        updateIfNotEmpty(editor, AppConstants.PREFS_KEY_USER_PHONE_PREFIX, account.getPhonePrefix());
//        updateIfNotEmpty(editor, AppConstants.PREFS_KEY_USER_ACCOUNT_TYPE, account.getAccountType().name()); //NULLPOINTER
//        updateIfNotEmpty(editor, AppConstants.PREFS_KEY_USER_TIME_UPDATED, account.getTimeUpdated());
//        updateIfNotEmpty(editor, AppConstants.PREFS_KEY_USER_SECURE_QUESTION, account.getSecurityQuestion());
//        savePassword(account, editor, true);
//
//        editor.commit();
//    }


    /**
     * @return
     */
    public String getApplicationVersionName() {

        String name = getPackageName();
        PackageInfo pi;

        try {
            pi = getPackageManager().getPackageInfo(name, 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {

        }

        return "";
    }

    public Locale getLocale() {
        return getResources().getConfiguration().locale;
    }

    public String getDeviceInformation() {

        String SEPERATOR = ",";

        StringBuilder sb = new StringBuilder();
        sb.append(android.os.Build.MANUFACTURER);
        sb.append(SEPERATOR);
        sb.append(android.os.Build.DEVICE);
        sb.append(SEPERATOR);
        sb.append(android.os.Build.MODEL);
        sb.append(SEPERATOR);
        //sb.append("PRODUCT=" + android.os.Build.PRODUCT);
        //sb.append(SEPERATOR);
        sb.append(android.os.Build.VERSION.SDK);
        //sb.append(System.getProperty("os.version"));

        return sb.toString();

    }

}
