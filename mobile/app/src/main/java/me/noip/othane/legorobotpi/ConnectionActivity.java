package me.noip.othane.legorobotpi;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class ConnectionActivity extends Activity {

    private static final String[] DUMMY_CONNECTIONS = new String[]{
            "192.168.1.50:1337", "127.0.0.1:1337"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserConnectionTask mConnTask = null;

    // UI references.
    private AutoCompleteTextView mIpView;
    private EditText mPortView;
    private View mProgressView;
    private View mConnectionFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        // Set up the login form.
        mIpView = (AutoCompleteTextView) findViewById(R.id.ip);

        mPortView = (EditText) findViewById(R.id.port);
        mPortView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.connection || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mConnectionButton = (Button) findViewById(R.id.connection_button);
        mConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mConnectionFormView = findViewById(R.id.connection_form);
        mProgressView = findViewById(R.id.connection_progress);
    }

    public void attemptLogin() {
        if (mConnTask != null) {
            return;
        }

        // Reset errors.
        mIpView.setError(null);
        mPortView.setError(null);

        // Store values at the time of the connection attempt.
        String ip = mIpView.getText().toString();
        String port = mPortView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid port, if the user entered one.
        if (!TextUtils.isEmpty(port) && !isPortValid(port)) {
            mPortView.setError(getString(R.string.error_invalid_port));
            focusView = mPortView;
            cancel = true;
        }

        // Check for a valid ip address.
        if (TextUtils.isEmpty(ip)) {
            mIpView.setError(getString(R.string.error_field_required));
            focusView = mIpView;
            cancel = true;
        } else if (!isIpValid(ip)) {
            mIpView.setError(getString(R.string.error_invalid_ip));
            focusView = mIpView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mConnTask = new UserConnectionTask(ip, port);
            mConnTask.execute((Void) null);
        }
    }

    private boolean isIpValid(String ip) {
        return ip.contains(".");
    }

    private boolean isPortValid(String port) {
        return port.length() > 3;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mConnectionFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mConnectionFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mConnectionFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mConnectionFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous connection task used to connect
     * the user.
     */
    public class UserConnectionTask extends AsyncTask<Void, Void, Boolean> {

        private final String mIp;
        private final String mPort;

        UserConnectionTask(String ip, String port) {
            mIp = ip;
            mPort = port;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                final InetAddress inetAddress = InetAddress.getByName(mIp);
                if (inetAddress.isReachable(20000)) {
                    return true;
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mConnTask = null;
            showProgress(false);

            if (success) {
                Intent intent = new Intent(ConnectionActivity.this, ControlActivity.class);
                intent.putExtra("IP", mIp);
                intent.putExtra("PORT", mPort);
                startActivity(intent);
            } else {
                mPortView.setError(getString(R.string.error_cant_connect));
                mPortView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mConnTask = null;
            showProgress(false);
        }
    }
}
