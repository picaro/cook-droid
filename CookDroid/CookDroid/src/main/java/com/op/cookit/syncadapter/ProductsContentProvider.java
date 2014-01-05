package com.op.cookit.syncadapter;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import com.op.cookit.AppBase;

/**
 * Created by Udini on 6/22/13.
 */
public class ProductsContentProvider extends ContentProvider {

    public static final String CONTENT_AUTHORITY = "com.example.android.network.sync.basicsyncadapter";

    public static final Uri CONTENT_URI =
            Uri.parse("content://com.op.cookit.syncadapter.provider");

    public static class Columns {
        public static final String ID = "_id";
        public static final String CITY = "city";
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public String getType(Uri uri) {
        return "CONTENT_TYPE_DIR";
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.e(AppBase.TAG, ">>query ");

        //final ContentResolver contentResolver = getContext().getContentResolver();
        //Cursor c = contentResolver.query(uri, projection, null, null, null);

        final MatrixCursor c = new MatrixCursor(
                new String[]{Columns.ID, Columns.CITY});
        c.addRow(new Object[]{new Integer(1), "city"});

        return c;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        int token = URI_MATCHER.match(uri);
//        switch (token) {
//            case PATH_TOKEN: {
//                long id = db.insert(TvShowsDbHelper.TVSHOWS_TABLE_NAME, null, values);
//                if (id != -1)
//                    getContext().getContentResolver().notifyChange(uri, null);
//                return TvShowsContract.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
//            }
//            default: {
        throw new UnsupportedOperationException("URI: " + uri + " not supported.");
//            }
//        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        int token = URI_MATCHER.match(uri);
//        int rowsDeleted = -1;
//        switch (token) {
//            case (PATH_TOKEN):
//                rowsDeleted = db.delete(TvShowsDbHelper.TVSHOWS_TABLE_NAME, selection, selectionArgs);
//                break;
//            case (PATH_FOR_ID_TOKEN):
//                String tvShowIdWhereClause = TvShowsDbHelper.TVSHOWS_COL_ID + "=" + uri.getLastPathSegment();
//                if (!TextUtils.isEmpty(selection))
//                    tvShowIdWhereClause += " AND " + selection;
//                rowsDeleted = db.delete(TvShowsDbHelper.TVSHOWS_TABLE_NAME, tvShowIdWhereClause, selectionArgs);
//                break;
//            default:
        throw new IllegalArgumentException("Unsupported URI: " + uri);
//        }
//        // Notifying the changes, if there are any
//        if (rowsDeleted != -1)
//            getContext().getContentResolver().notifyChange(uri, null);
//        return rowsDeleted;
    }

    /**
     * Man..I'm tired..
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}