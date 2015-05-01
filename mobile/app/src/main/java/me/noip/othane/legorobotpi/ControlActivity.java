package me.noip.othane.legorobotpi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class ControlActivity extends Activity {

    private String mIp;
    private int mPort;
    private Socket mSocket;
    private OutputStream mOutputStream;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        // UI
        final Button upButton = (Button)findViewById(R.id.upButton);
        final Button downButton = (Button)findViewById(R.id.downButton);
        final Button leftButton = (Button)findViewById(R.id.leftButton);
        final Button rightButton = (Button)findViewById(R.id.rightButton);
        //final Button showVideoButton = (Button)findViewById(R.id.showVideoButton);

        upButton.setOnTouchListener(forwardButtonListener);
        downButton.setOnTouchListener(backwardButtonListener);
        leftButton.setOnTouchListener(leftButtonListener);
        rightButton.setOnTouchListener(rightButtonListener);

        // Progress
        mProgressDialog = new ProgressDialog(ControlActivity.this);
        mProgressDialog.setTitle("LegoRobotPi");
        mProgressDialog.setMessage("Connecting...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        // Get data from previous view
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mIp = extras.getString("IP");
            mPort = Integer.parseInt(extras.getString("PORT"));
            Thread thread = new Thread(socketRunnable);
            thread.start();
        } else {
            AlertDialog.Builder aDialog = new AlertDialog.Builder(ControlActivity.this);
            aDialog.setMessage("No connection information.");
            aDialog.setTitle("LegoRobotPi");
            aDialog.setPositiveButton("OK", null);
            aDialog.create().show();

            finish();
            //Intent intent = new Intent(LegoRobotPiActivity.this, ConnectionActivity.class);
            //startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {

        try {
            mOutputStream.close();
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }

    private Runnable socketRunnable = new Runnable() {

        @Override
        public void run() {

            try {
                mSocket = new Socket(InetAddress.getByName(mIp), mPort);
                mOutputStream = mSocket.getOutputStream();
            } catch (final UnknownHostException e) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        AlertDialog.Builder aDialog = new AlertDialog.Builder(ControlActivity.this);
                        aDialog.setMessage("UnknownHostException: " + e.toString());
                        aDialog.setTitle("LegoRobotPi");
                        aDialog.setPositiveButton("OK", null);
                        aDialog.create().show();

                        finish();
                        //Intent intent = new Intent(LegoRobotPiActivity.this, ConnectionActivity.class);
                        //startActivity(intent);
                    }
                });
            } catch (final IOException e) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        AlertDialog.Builder aDialog = new AlertDialog.Builder(ControlActivity.this);
                        aDialog.setMessage("IOException: " + e.toString());
                        aDialog.setTitle("LegoRobotPi");
                        aDialog.setPositiveButton("OK", null);
                        aDialog.create().show();

                        finish();
                        //Intent intent = new Intent(LegoRobotPiActivity.this, ConnectionActivity.class);
                        //startActivity(intent);
                    }
                });
            } finally {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        mProgressDialog.dismiss();
                    }
                });
            }
        }
    };

    private View.OnTouchListener leftButtonListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                try {
                    if (mOutputStream.)
                    mOutputStream.write("left".getBytes());
                    mOutputStream.flush();
                }
                catch (final IOException e) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            AlertDialog.Builder aDialog = new AlertDialog.Builder(ControlActivity.this);
                            aDialog.setMessage("IOException: " + e.toString());
                            aDialog.setTitle("LegoRobotPi");
                            aDialog.setPositiveButton("OK", null);
                            aDialog.create().show();

                            finish();
                            //Intent intent = new Intent(LegoRobotPiActivity.this, ConnectionActivity.class);
                            //startActivity(intent);
                        }
                    });
                }
            }
            else if (event.getAction() == MotionEvent.ACTION_UP) {

                v.performClick();
                try {
                    mOutputStream.write("stop_turn".getBytes());
                    mOutputStream.flush();
                }
                catch (final IOException e) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            AlertDialog.Builder aDialog = new AlertDialog.Builder(ControlActivity.this);
                            aDialog.setMessage("IOException: " + e.toString());
                            aDialog.setTitle("LegoRobotPi");
                            aDialog.setPositiveButton("OK", null);
                            aDialog.create().show();

                            finish();
                            //Intent intent = new Intent(LegoRobotPiActivity.this, ConnectionActivity.class);
                            //startActivity(intent);
                        }
                    });
                }
            }

            return false;
        }
    };

    private View.OnTouchListener rightButtonListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                try {
                    mOutputStream.write("right".getBytes());
                    mOutputStream.flush();
                }
                catch (final IOException e) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            AlertDialog.Builder aDialog = new AlertDialog.Builder(ControlActivity.this);
                            aDialog.setMessage("IOException: " + e.toString());
                            aDialog.setTitle("LegoRobotPi");
                            aDialog.setPositiveButton("OK", null);
                            aDialog.create().show();

                            finish();
                            //Intent intent = new Intent(LegoRobotPiActivity.this, ConnectionActivity.class);
                            //startActivity(intent);
                        }
                    });
                }
            }
            else if (event.getAction() == MotionEvent.ACTION_UP) {

                v.performClick();
                try {
                    mOutputStream.write("stop_turn".getBytes());
                    mOutputStream.flush();
                }
                catch (final IOException e) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            AlertDialog.Builder aDialog = new AlertDialog.Builder(ControlActivity.this);
                            aDialog.setMessage("IOException: " + e.toString());
                            aDialog.setTitle("LegoRobotPi");
                            aDialog.setPositiveButton("OK", null);
                            aDialog.create().show();

                            finish();
                            //Intent intent = new Intent(LegoRobotPiActivity.this, ConnectionActivity.class);
                            //startActivity(intent);
                        }
                    });
                }
            }

            return false;
        }
    };

    private View.OnTouchListener forwardButtonListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                try {
                    mOutputStream.write("forward".getBytes());
                    mOutputStream.flush();
                }
                catch (final IOException e) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            AlertDialog.Builder aDialog = new AlertDialog.Builder(ControlActivity.this);
                            aDialog.setMessage("IOException: " + e.toString());
                            aDialog.setTitle("LegoRobotPi");
                            aDialog.setPositiveButton("OK", null);
                            aDialog.create().show();

                            finish();
                            //Intent intent = new Intent(LegoRobotPiActivity.this, ConnectionActivity.class);
                            //startActivity(intent);
                        }
                    });
                }
            }
            else if (event.getAction() == MotionEvent.ACTION_UP) {

                v.performClick();
                try {
                    mOutputStream.write("stop_go".getBytes());
                    mOutputStream.flush();
                }
                catch (final IOException e) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            AlertDialog.Builder aDialog = new AlertDialog.Builder(ControlActivity.this);
                            aDialog.setMessage("IOException: " + e.toString());
                            aDialog.setTitle("LegoRobotPi");
                            aDialog.setPositiveButton("OK", null);
                            aDialog.create().show();

                            finish();
                            //Intent intent = new Intent(LegoRobotPiActivity.this, ConnectionActivity.class);
                            //startActivity(intent);
                        }
                    });
                }
            }

            return false;
        }
    };

    private View.OnTouchListener backwardButtonListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                try {
                    mOutputStream.write("backward".getBytes());
                    mOutputStream.flush();
                }
                catch (final IOException e) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            AlertDialog.Builder aDialog = new AlertDialog.Builder(ControlActivity.this);
                            aDialog.setMessage("IOException: " + e.toString());
                            aDialog.setTitle("LegoRobotPi");
                            aDialog.setPositiveButton("OK", null);
                            aDialog.create().show();

                            finish();
                            //Intent intent = new Intent(LegoRobotPiActivity.this, ConnectionActivity.class);
                            //startActivity(intent);
                        }
                    });
                }
            }
            else if (event.getAction() == MotionEvent.ACTION_UP) {

                v.performClick();
                try {
                    mOutputStream.write("stop_go".getBytes());
                    mOutputStream.flush();
                }
                catch (final IOException e) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            AlertDialog.Builder aDialog = new AlertDialog.Builder(ControlActivity.this);
                            aDialog.setMessage("IOException: " + e.toString());
                            aDialog.setTitle("LegoRobotPi");
                            aDialog.setPositiveButton("OK", null);
                            aDialog.create().show();

                            finish();
                            //Intent intent = new Intent(LegoRobotPiActivity.this, ConnectionActivity.class);
                            //startActivity(intent);
                        }
                    });
                }
            }

            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
