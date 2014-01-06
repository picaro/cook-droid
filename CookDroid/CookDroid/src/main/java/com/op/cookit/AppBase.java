package com.op.cookit;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.ContentResolver;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.op.cookit.syncadapter.GenericAccountService;
import com.op.cookit.syncadapter.ProductsContentProvider;
import com.op.cookit.util.ShopListRest;

/**
 *c lass with app settings const
*/
public class AppBase extends Application
{
	public static final String SETTINGS_FILE = Environment.getExternalStorageDirectory() + "/Android/data/com.op.cookcloud/preferences.dat";
	public static final String SETTINGS ="op.cookdroid";

    public static final String TAG ="cookdroid";


	public static final String PREF_CHANGELOG = "changelog";
	public static final String PREF_APP_VERSION = "app.version"; 
	
	public static final String PREF_EULA = "eula";
	public static final String PREF_EULA_ACCEPTED = "eula.accepted";

	//public static final int DB_VERSION=6;

    public static final String BASE_URL = "http://cookcloud.jelastic.neohost.net/";
    public static final String BASE_REST_URL = BASE_URL + "rest/";

    public static ShopListRest shopListRest = new ShopListRest();

    private Account appAccount;

    @Override
    public void onCreate() {
        super.onCreate();
        setupAppAccount();
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
}
