package com.op.cookit;

import android.os.Environment;

/**
 *class with app settings const
*/
public class SettingsConst
{
	public static final String SETTINGS_FILE = Environment.getExternalStorageDirectory() + "/Android/data/com.op.cookcloud/preferences.dat";
	public static final String SETTINGS ="op.cookdroid";
	
	public static final String PREF_CHANGELOG = "changelog";
	public static final String PREF_APP_VERSION = "app.version"; 
	
	public static final String PREF_EULA = "eula";
	public static final String PREF_EULA_ACCEPTED = "eula.accepted";

	public static final int DB_VERSION=6;

}
