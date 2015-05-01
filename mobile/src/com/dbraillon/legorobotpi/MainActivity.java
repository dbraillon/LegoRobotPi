package com.dbraillon.legorobotpi;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;


public class MainActivity extends Activity {

	private Socket _socket;
	private OutputStream _out;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final ProgressDialog pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setTitle("LegoRobotPi");
        pDialog.setMessage("Connecting...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	
        	final InetAddress inetAddress = (InetAddress)extras.getSerializable("INET_ADDRESS");
        	final int port = extras.getInt("PORT");
        	
        	final Button upButton = (Button)findViewById(R.id.upButton);
            final Button downButton = (Button)findViewById(R.id.downButton);
            final Button leftButton = (Button)findViewById(R.id.leftButton);
            final Button rightButton = (Button)findViewById(R.id.rightButton);
            final Button showVideoButton = (Button)findViewById(R.id.showVideoButton);
            
            showVideoButton.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				
    				// Start NewActivity.class
                    Intent myIntent = new Intent(MainActivity.this,
                            VideoActivity.class);
                    startActivity(myIntent);
    			}
    		});
            
            upButton.setOnTouchListener(new OnTouchListener() {
    			
    			@Override
    			public boolean onTouch(View v, MotionEvent event) {
    				
    				if (event.getAction() == MotionEvent.ACTION_DOWN) {
    					
    					try {
    						_out.write("fb_forward".getBytes());
    						_out.flush();
    					} 
        				catch (final IOException e) {
        					
        					runOnUiThread(new Runnable() {
    							
    							@Override
    							public void run() {
    								
    								AlertDialog.Builder aDialog = new Builder(MainActivity.this);
    								aDialog.setMessage("IOException: " + e.toString());
    								aDialog.setTitle("LegoRobotPi");
    								aDialog.setPositiveButton("OK", null);
    								aDialog.create().show();
    								
    								Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
    								startActivity(intent);
    							}
    						});
        				}
    				}
    				else if (event.getAction() == MotionEvent.ACTION_UP) {
    					
    					v.performClick();
    					try {
    						_out.write("fb_stop".getBytes());
    						_out.flush();
    					} 
        				catch (final IOException e) {
        					
        					runOnUiThread(new Runnable() {
    							
    							@Override
    							public void run() {
    								
    								AlertDialog.Builder aDialog = new Builder(MainActivity.this);
    								aDialog.setMessage("IOException: " + e.toString());
    								aDialog.setTitle("LegoRobotPi");
    								aDialog.setPositiveButton("OK", null);
    								aDialog.create().show();
    								
    								Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
    								startActivity(intent);
    							}
    						});
        				}
    				}
    				
    				return false;
    			}
    		});
            
            downButton.setOnTouchListener(new OnTouchListener() {
    					
    			@Override
    			public boolean onTouch(View v, MotionEvent event) {
    				
    				if (event.getAction() == MotionEvent.ACTION_DOWN) {
    					
    					try {
    						_out.write("fb_backward".getBytes());
    						_out.flush();
    					} 
        				catch (final IOException e) {
        					
        					runOnUiThread(new Runnable() {
    							
    							@Override
    							public void run() {
    								
    								AlertDialog.Builder aDialog = new Builder(MainActivity.this);
    								aDialog.setMessage("IOException: " + e.toString());
    								aDialog.setTitle("LegoRobotPi");
    								aDialog.setPositiveButton("OK", null);
    								aDialog.create().show();
    								
    								Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
    								startActivity(intent);
    							}
    						});
        				}
    				}
    				else if (event.getAction() == MotionEvent.ACTION_UP) {
    					
    					v.performClick();
    					try {
    						_out.write("fb_stop".getBytes());
    						_out.flush();
    					} 
        				catch (final IOException e) {
        					
        					runOnUiThread(new Runnable() {
    							
    							@Override
    							public void run() {
    								
    								AlertDialog.Builder aDialog = new Builder(MainActivity.this);
    								aDialog.setMessage("IOException: " + e.toString());
    								aDialog.setTitle("LegoRobotPi");
    								aDialog.setPositiveButton("OK", null);
    								aDialog.create().show();
    								
    								Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
    								startActivity(intent);
    							}
    						});
        				}
    				}
    				
    				return false;
    			}
    		});
            
            leftButton.setOnTouchListener(new OnTouchListener() {
    			
    			@Override
    			public boolean onTouch(View v, MotionEvent event) {
    				
    				if (event.getAction() == MotionEvent.ACTION_DOWN) {
    					
    					try {
    						_out.write("lr_left".getBytes());
    						_out.flush();
    					} 
        				catch (final IOException e) {
        					
        					runOnUiThread(new Runnable() {
    							
    							@Override
    							public void run() {
    								
    								AlertDialog.Builder aDialog = new Builder(MainActivity.this);
    								aDialog.setMessage("IOException: " + e.toString());
    								aDialog.setTitle("LegoRobotPi");
    								aDialog.setPositiveButton("OK", null);
    								aDialog.create().show();
    								
    								Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
    								startActivity(intent);
    							}
    						});
        				}
    				}
    				else if (event.getAction() == MotionEvent.ACTION_UP) {
    					
    					v.performClick();
    					try {
    						_out.write("lr_stop".getBytes());
    						_out.flush();
    					} 
        				catch (final IOException e) {
        					
        					runOnUiThread(new Runnable() {
    							
    							@Override
    							public void run() {
    								
    								AlertDialog.Builder aDialog = new Builder(MainActivity.this);
    								aDialog.setMessage("IOException: " + e.toString());
    								aDialog.setTitle("LegoRobotPi");
    								aDialog.setPositiveButton("OK", null);
    								aDialog.create().show();
    								
    								Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
    								startActivity(intent);
    							}
    						});
        				}
    				}
    				
    				return false;
    			}
    		});
            
            rightButton.setOnTouchListener(new OnTouchListener() {
    			
    			@Override
    			public boolean onTouch(View v, MotionEvent event) {
    				
    				if (event.getAction() == MotionEvent.ACTION_DOWN) {
    					
    					try {
    						_out.write("lr_right".getBytes());
    						_out.flush();
    					} 
        				catch (final IOException e) {
        					
        					runOnUiThread(new Runnable() {
    							
    							@Override
    							public void run() {
    								
    								AlertDialog.Builder aDialog = new Builder(MainActivity.this);
    								aDialog.setMessage("IOException: " + e.toString());
    								aDialog.setTitle("LegoRobotPi");
    								aDialog.setPositiveButton("OK", null);
    								aDialog.create().show();
    								
    								Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
    								startActivity(intent);
    							}
    						});
        				}
    				}
    				else if (event.getAction() == MotionEvent.ACTION_UP) {
    					
    					v.performClick();
    					try {
    						_out.write("lr_stop".getBytes());
    						_out.flush();
    					} 
        				catch (final IOException e) {
        					
        					runOnUiThread(new Runnable() {
    							
    							@Override
    							public void run() {
    								
    								AlertDialog.Builder aDialog = new Builder(MainActivity.this);
    								aDialog.setMessage("IOException: " + e.toString());
    								aDialog.setTitle("LegoRobotPi");
    								aDialog.setPositiveButton("OK", null);
    								aDialog.create().show();
    								
    								Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
    								startActivity(intent);
    							}
    						});
        				}
    				}
    				
    				return false;
    			}
    		});
            
            Thread thread = new Thread(new Runnable() {
    			
    			@Override
    			public void run() {
    				
    				try {
    		        	_socket = new Socket(inetAddress, port);
    					_out = _socket.getOutputStream();
    				} 
    				catch (final UnknownHostException e) {
    					
    					runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								
								AlertDialog.Builder aDialog = new Builder(MainActivity.this);
								aDialog.setMessage("UnknownHostException: " + e.toString());
								aDialog.setTitle("LegoRobotPi");
								aDialog.setPositiveButton("OK", null);
								aDialog.create().show();
								
								Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
								startActivity(intent);
							}
						});
    				} 
    				catch (final IOException e) {
    					
    					runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								
								AlertDialog.Builder aDialog = new Builder(MainActivity.this);
								aDialog.setMessage("IOException: " + e.toString());
								aDialog.setTitle("LegoRobotPi");
								aDialog.setPositiveButton("OK", null);
								aDialog.create().show();
								
								Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
								startActivity(intent);
							}
						});
    				}
    				finally {
    					
    					runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								
								pDialog.dismiss();
							}
						});
    				}
    			}
    		});
            thread.start();
        }
        else {
        	
        	AlertDialog.Builder aDialog = new Builder(MainActivity.this);
			aDialog.setMessage("No connection information.");
			aDialog.setTitle("LegoRobotPi");
			aDialog.setPositiveButton("OK", null);
			aDialog.create().show();
			
			Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
			startActivity(intent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
}
