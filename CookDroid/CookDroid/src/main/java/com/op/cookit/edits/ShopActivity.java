package com.op.cookit.edits;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.op.cookit.AppBase;
import com.op.cookit.R;
import com.op.cookit.model.Shop;

import java.io.File;

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
    private AppBase appBase;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;

    // UI references.
    private EditText mNameView;

    private View mFormView;
    private View mStatusView;
    private TextView mStatusMessageView;

    private Uri mImageCaptureUri;
    Bitmap mPhoto;
    boolean mPhotoChanged;
    ImageButton mPhotoImageView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_shop);
        appBase = (AppBase) getApplication();

        mFormView = findViewById(R.id.signup_form);
        mStatusView = findViewById(R.id.status);
        mStatusMessageView = (TextView) findViewById(R.id.status_message);
        mNameView = (EditText) findViewById(R.id.name);
        restoreActionBar();
        ImageButton ibItem1 = (ImageButton) findViewById(R.id.action_save);
        ibItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRequest();
            }
        });

        //Create path for temp file
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                "tmp_contact_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
        mPhotoImageView = (ImageButton) findViewById(R.id.imageButton);
        mPhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePicture.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                takePicture.putExtra("return-data", true);
                startActivityForResult(takePicture, PICK_FROM_CAMERA);
            }
        });
    }

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
        mNameView.setError(null);
        mName = mNameView.getText().toString();
        boolean cancel = false;
        View focusView = null;

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
        actionBar.setDisplayShowHomeEnabled(false);
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
            Shop shop = new Shop();
            shop.setName(mName);
            shop.setUserid(1);
            AppBase.clientRest.addShop(shop);
            return true;
        }


        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {

            case CROP_FROM_CAMERA: {
                final Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");

                    mPhoto = photo;
                    mPhotoChanged = true;


                    mPhotoImageView.setImageBitmap(photo);
                    //setPhotoPresent(true);
                }

                //Wysie_Soh: Delete the temporary file
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) {
                    f.delete();
                }

                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.showSoftInput(mPhotoImageView, InputMethodManager.SHOW_IMPLICIT);

                break;
            }

            case PICK_FROM_CAMERA: {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");
                intent.putExtra("scaleUpIfNeeded", true);
                intent.putExtra("outputX", 126);
                intent.putExtra("outputY", 126);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_CAMERA);

                break;

            }
        }
    }
}
