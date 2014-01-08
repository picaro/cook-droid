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
import android.view.View;

import com.google.gson.Gson;
import com.op.cookit.model.inner.PersonLocal;
import com.op.cookit.syncadapter.GenericAccountService;
import com.op.cookit.syncadapter.ProductsContentProvider;
import com.op.cookit.util.remote.ClientRest;

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
    public static final String PREFS_KEY_PERSON = "PREFS_KEY_PERSON";

    public static ClientRest clientRest = new ClientRest();

    private static SharedPreferences mPrefs;

    private static Account appAccount;

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


    public static void requestForcedSync() {
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

    public static void saveLoggedUser(PersonLocal person) {
        SharedPreferences.Editor editor = mPrefs.edit();
        String personJSON =  new Gson().toJson(person, PersonLocal.class);
        editor.putString(PREFS_KEY_PERSON, personJSON);
        editor.commit();
    }

    public static Boolean isUserLogged(){
        return false;
    }

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


    public static String getDeviceInformation(View view) {
        String SEP = ";";
        StringBuilder sb = new StringBuilder();
        sb.append(android.os.Build.MANUFACTURER).append(SEP);
        sb.append(android.os.Build.DEVICE).append(SEP);
        sb.append(android.os.Build.MODEL).append(SEP);
        sb.append(view.getResources().getConfiguration().locale);
        return sb.toString();
    }

}
