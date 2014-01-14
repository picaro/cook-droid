package com.op.cookit.edits;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.op.cookit.AppBase;
import com.op.cookit.R;
import com.op.cookit.model.Person;

import java.util.Calendar;

/**
 * Activity which displays a register screen
 */
public class ShopActivity extends Activity {

    /**
     * Keep track of the register task to ensure we can cancel it if requested.
     */
    private SignUpAsyncTask mAuthTask = null;

    private ShopActivity fragment = this;

    // Values for email and password at the time of the register attempt.
    private String mName;
//    private String mLastName;
//    private String mEmail;
//    private String mPassword;

    private AppBase appBase;


    // UI references.
    private EditText mNameView;
//    private EditText mLastNameView;
//    private EditText mEmailView;
//    private EditText mPasswordView;
    private View mFormView;
    private View mStatusView;
    private TextView mStatusMessageView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_shop);
//        view = inflater.inflate(R.layout.action_shop,
//                container, false);

        // Set up the login form.
        //   mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
//        mEmailView = (EditText) view.findViewById(R.id.email);
//        mEmailView.setText(mEmail);
//
//        mNameView = (EditText) view.findViewById(R.id.first_name);
//        mNameView.setText(mName);
//
//        mLastNameView = (EditText) view.findViewById(R.id.last_name);
//        mLastNameView.setText(mLastName);
//
//        mPasswordView = (EditText) view.findViewById(R.id.password);
//        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == R.id.login || id == EditorInfo.IME_NULL) {
//                    attemptRequest();
//                    return true;
//                }
//                return false;
//            }
//        });
//
        appBase = (AppBase) getApplication();

        mFormView = findViewById(R.id.signup_form);
        mStatusView = findViewById(R.id.status);
        mStatusMessageView = (TextView) findViewById(R.id.status_message);

        restoreActionBar();
        ImageButton ibItem1 = (ImageButton) findViewById(R.id.action_save);
        ibItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRequest();
            }
        });
//        setHasOptionsMenu(true);

//        view.findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                attemptRequest();
//            }
//        });

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_save:
//                attemptRequest();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
////        getMenuInflater().inflate(R.menu.menu_edit, menu);
//        super.onCreateOptionsMenu(menu);
//        return true;
//    }

    /**
     * Attempts to register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptRequest() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
      //  mNameView.setError(null);
      //  mLastNameView.setError(null);
      //  mEmailView.setError(null);
     //   mPasswordView.setError(null);

        // Store values at the time of the login attempt.
    //    mName = mNameView.getText().toString();
     //   mLastName = mLastNameView.getText().toString();
     //   mEmail = mEmailView.getText().toString();
      //  mPassword = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password.
//        if (TextUtils.isEmpty(mPassword)) {
//            mPasswordView.setError(getString(R.string.error_field_required));
//            focusView = mPasswordView;
//            cancel = true;
//        } else if (mPassword.length() < 4) {
//            mPasswordView.setError(getString(R.string.error_invalid_password));
//            focusView = mPasswordView;
//            cancel = true;
//        }
//
//        // Check for a valid email address.
//        if (TextUtils.isEmpty(mEmail)) {
//            mEmailView.setError(getString(R.string.error_field_required));
//            focusView = mEmailView;
//            cancel = true;
//        } else if (!mEmail.contains("@")) {
//            mEmailView.setError(getString(R.string.error_invalid_email));
//            focusView = mEmailView;
//            cancel = true;
//        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mStatusMessageView.setText(R.string.login_progress_signing_in);
            showProgress(true);
            mAuthTask = new SignUpAsyncTask();
            mAuthTask.execute((Void) null);
        }
    }

    private void restoreActionBar() {
        LayoutInflater inflator = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.menu_edit, null);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled (false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(v);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mStatusView.setVisibility(View.VISIBLE);
            mStatusView.animate()
                    .setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
                        }
                    });

            mFormView.setVisibility(View.VISIBLE);
            mFormView.animate()
                    .setDuration(shortAnimTime)
                    .alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                        }
                    });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
            mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class SignUpAsyncTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
 //           Person person = new Person();
 //           person.setFirstName(mName);
//            person.setLastName(mLastName);
//            person.setEmail(mEmail);
//            person.setPassword(mPassword);
 //           person.setGender("M");
   //         person.setDate_registration(Calendar.getInstance().getTimeInMillis());
        //    AppBase.clientRest. signUP(person);
            AppBase.clientRest.logIn();//TODO real data
            return true;
        }


        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                //getActivity().getFragmentManager().beginTransaction().remove(fragment).commit();
            } else {
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}