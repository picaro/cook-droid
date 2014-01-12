package com.op.cookit.fragments.circles;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

import com.op.cookit.AppBase;
import com.op.cookit.MainActivity;
import com.op.cookit.R;
import com.op.cookit.fragments.shops.ShopFragment;
import com.op.cookit.syncadapter.ProductsContentProvider;
import com.op.cookit.syncadapter.SyncUtils;


public class CirclesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>  {
    /**
     * Cursor adapter for controlling ListView results.
     */
    private SimpleCursorAdapter mAdapter;

    private OnFragmentInteractionListener mListener;

    private CirclesFragment fragment = this;
    private View view;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Create account, if needed
        SyncUtils.CreateSyncAccount(activity);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ShopsFragment.
     */
    public static CirclesFragment newInstance() {
        CirclesFragment fragment = new CirclesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public CirclesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Cursor mCursor = getActivity().getApplicationContext().getContentResolver().query(ProductsContentProvider.CONTENT_URI, null, null,
                null, null);
        mCursor.getColumnCount();

        view = inflater.inflate(R.layout.fragment_circles,
                container, false);
        setHasOptionsMenu(true);
        return view;

    }


    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater menuInflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_add, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_add:
                CircleFragment circleFragment = CircleFragment.newInstance();
                ((MainActivity)getActivity())
                        .setActionBarTitle(getResources().getText(R.id.action_add).toString());
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.container, circleFragment,CircleFragment.class.getName())
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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


        return new CursorLoader(getActivity(),  // Context
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
