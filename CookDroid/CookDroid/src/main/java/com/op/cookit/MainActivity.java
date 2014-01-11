package com.op.cookit;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.op.cookit.fragments.circles.CirclesFragment;
import com.op.cookit.fragments.fridge.FridgeFragment;
import com.op.cookit.fragments.product.ProductFragment;
import com.op.cookit.fragments.recipes.RecipesFragment;
import com.op.cookit.fragments.shoplist.ShopListFragment;
import com.op.cookit.fragments.shops.ShopsFragment;
import com.op.cookit.intro.Changelog;
import com.op.cookit.intro.Eula;
import com.op.cookit.model.inner.PersonLocal;

public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private long lastPressTime;

    private static final long DOUBLE_PRESS_INTERVAL = 3000000000l; // value in


    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        Fragment fragment = (Fragment) this.getFragmentManager().findFragmentByTag("ProductFragment");
        if(fragment != null){
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onSectionAttached(int number) {
        FragmentManager fragmentManager = getFragmentManager();
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_shoplist);
                ShopListFragment shopListFragment = ShopListFragment.newInstance("1", "2");
                fragmentManager.beginTransaction()
                        .replace(R.id.container, shopListFragment).addToBackStack(null)
                        .commit();
                break;
            case 2:
                mTitle = getString(R.string.title_refr);
                FridgeFragment fridgeFragment = FridgeFragment.newInstance();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fridgeFragment).addToBackStack(null)
                        .commit();
                break;
            case 3:
                mTitle = getString(R.string.title_scaner);
                ProductFragment productFragment = ProductFragment.newInstance();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, productFragment, "ProductFragment").addToBackStack(null)
                        .commit();
                break;
            case 4:
                mTitle = getString(R.string.title_circles);
                CirclesFragment circlesFragment = CirclesFragment.newInstance();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, circlesFragment).addToBackStack(null)
                        .commit();
                break;
            case 5:
                mTitle = getString(R.string.title_recipes);
                RecipesFragment fragment = RecipesFragment.newInstance();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();
                break;
            case 6:
                mTitle = getString(R.string.title_shops);
                ShopsFragment shopsFragment = ShopsFragment.newInstance();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, shopsFragment).addToBackStack(null)
                        .commit();
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    private void exit() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Log.v(AppBase.TAG, "onBackPressed() called");
        long pressTime = System.nanoTime();
        if (pressTime - lastPressTime <= DOUBLE_PRESS_INTERVAL) {
            // this is a double click event
            super.onBackPressed();
            exit();
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            } else {
                    Toast.makeText(this, getString(R.string.toast_press_back),
                    Toast.LENGTH_SHORT).show();
                    lastPressTime = pressTime;
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            PersonLocal personLocal = AppBase.getLoggedUser();
            if (personLocal != null) {
                getMenuInflater().inflate(R.menu.main_logged, menu);
                menu.getItem(0).setTitle(personLocal.getDisplayName());
            } else {
                getMenuInflater().inflate(R.menu.main, menu);
            }
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));


            //install shortcut
            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
            Integer appRuns = mPrefs.getInt(AppBase.APP_RUNS_COUNT, 0);
            Log.e(AppBase.TAG,"appRuns" + appRuns);
            if (appRuns == 0) {
                Log.e(AppBase.TAG,"install shortcut");

                AppBase.installShortcurt(this.getActivity());
            }

            Changelog.show(this.getActivity());

            if (!mPrefs.getBoolean(AppBase.PREF_EULA_ACCEPTED, false)) {
                Eula.show(this.getActivity());
            }

            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putInt(AppBase.APP_RUNS_COUNT, appRuns + 1);
            editor.commit();

            Log.e(AppBase.TAG,"requestForcedSync");
            AppBase.requestForcedSync();
            Log.e(AppBase.TAG, "requestForcedSync end");

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
