package com.op.cookit.edits;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

import com.op.cookit.AppBase;
import com.op.cookit.R;
import com.op.cookit.syncadapter.ProductsContentProvider;


public class CircleActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>  {
    /**
     * Cursor adapter for controlling ListView results.
     */
    private SimpleCursorAdapter mAdapter;

    private OnFragmentInteractionListener mListener;

    private CircleActivity fragment = this;


    public CircleActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_circle);

        Cursor mCursor = getApplicationContext().getContentResolver().query(ProductsContentProvider.CONTENT_URI, null, null,
                null, null);
        mCursor.getColumnCount();

        //setHasOptionsMenu(true);

/*
        view = inflater.inflate(R.layout.action_circle,
                container, false);
*/
    }







    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // We only have one loader, so we can ignore the value of i.
        // (It'll be '0', as set in onCreate().)

        Log.e(AppBase.TAG, "onCreateLoader");


        return new CursorLoader(this,  // Context
                ProductsContentProvider.CONTENT_URI, // URI
                null,                // Projection
                null,                           // Selection
                null,                           // Selection args
                null); // Sort

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.changeCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.changeCursor(null);
    }


}
