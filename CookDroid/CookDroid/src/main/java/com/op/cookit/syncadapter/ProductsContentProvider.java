package com.op.cookit.syncadapter;

//import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.ContextWrapper;
import android.content.UriMatcher;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid. content.ContentProvider;
import com.activeandroid.app.Application;
import com.op.cookit.AppBase;
import com.op.cookit.model.inner.ProductLocal;

import java.util.List;

/**
 * Created by Udini on 6/22/13.
 */
public class ProductsContentProvider extends ContentProvider {

    public static final String CONTENT_AUTHORITY = "com.op.cookit.syncadapter";

    // path
    public static final String CONTACT_PATH = "products";

    // Общий Uri
    public static final Uri CONTENT_URI = Uri.parse("content://"
            + CONTENT_AUTHORITY + "/" + CONTACT_PATH);

    // общий Uri
    static final int URI_CONTACTS = 1;

    // Uri с указанным ID
    static final int URI_CONTACTS_ID = 2;

    public static class Columns {
        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String OUTID = "outid";
        public static final String NOTE = "note";
        public static final String IMGPATH = "imgpath";
        public static final String SHOPLISTID = "shoplistid";
        public static final String CROSSED = "crossed";
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    // описание и создание UriMatcher
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(CONTENT_AUTHORITY, CONTACT_PATH, URI_CONTACTS);
        uriMatcher.addURI(CONTENT_AUTHORITY, CONTACT_PATH + "/#", URI_CONTACTS_ID);
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
        List<ProductLocal> productLocalList = ProductLocal.recentItems();
        final MatrixCursor c = new MatrixCursor(
                new String[]{Columns.ID, Columns.OUTID, Columns.NAME, Columns.NOTE, Columns.IMGPATH, Columns.SHOPLISTID, Columns.CROSSED});
        for (ProductLocal productLocal : productLocalList) {
            c.addRow(new Object[]{productLocal.getId(), new Integer(1), productLocal.getName(), productLocal.getNote(), "http://www.revillweb.com/wp-content/uploads/2013/12/json.jpg", 1, true});
        }
        return c;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(AppBase.TAG, "insert, " + uri.toString());
        if (uriMatcher.match(uri) != URI_CONTACTS)
            throw new IllegalArgumentException("Wrong URI: " + uri);

        ProductLocal productLocal = new ProductLocal();
        //productLocal.se
        Integer rowID = 1;
        //db = dbHelper.getWritableDatabase();
        //long rowID = db.insert(CONTACT_TABLE, null, values);
        Uri resultUri = ContentUris.withAppendedId(CONTENT_URI, rowID);
        // уведомляем ContentResolver, что данные по адресу resultUri изменились
        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
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