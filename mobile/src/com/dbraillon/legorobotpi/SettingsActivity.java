package com.dbraillon.legorobotpi;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		final EditText ipEditText = (EditText)findViewById(R.id.ipEditText);
		final EditText portEditText = (EditText)findViewById(R.id.portEditText);
		
		Button connectButton = (Button)findViewById(R.id.connectButton);
		connectButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				// Create a progressbar
		        final ProgressDialog pDialog = new ProgressDialog(SettingsActivity.this);
		        pDialog.setTitle("LegoRobotPi");
		        pDialog.setMessage("Connecting...");
		        pDialog.setIndeterminate(false);
		        pDialog.setCancelable(false);
		        pDialog.show();
				
				final String ip = ipEditText.getText().toString();
				String portString = portEditText.getText().toString();
				try {
					final int port = Integer.parseInt(portString);
					Thread thread = new Thread(new Runnable() {
						
						@Override
						public void run() {
							
							try {
								final InetAddress inetAddress = InetAddress.getByName(ip);
								if (!inetAddress.isReachable(10000)) {
									
									runOnUiThread(new Runnable() {
										
										@Override
										public void run() {
											
											AlertDialog.Builder aDialog = new Builder(SettingsActivity.this);
											aDialog.setMessage("Unreachable host.");
											aDialog.setTitle("LegoRobotPi");
											aDialog.setPositiveButton("OK", null);
											aDialog.create().show();
										}
									});
								}
								else {
									
									runOnUiThread(new Runnable() {
										
										@Override
										public void run() {
											
											Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
											intent.putExtra("INET_ADDRESS", inetAddress);
											intent.putExtra("PORT", port);
							                startActivity(intent);
										}
									});
								}
							} 
							catch (final UnknownHostException e) {
								
								runOnUiThread(new Runnable() {
									
									@Override
									public void run() {
										
										AlertDialog.Builder aDialog = new Builder(SettingsActivity.this);
										aDialog.setMessage("UnknownHostException: " + e.toString());
										aDialog.setTitle("LegoRobotPi");
										aDialog.setPositiveButton("OK", null);
										aDialog.create().show();
									}
								});	
							} 
							catch (final IOException e) {
								
								runOnUiThread(new Runnable() {
									
									@Override
									public void run() {
										
										AlertDialog.Builder aDialog = new Builder(SettingsActivity.this);
										aDialog.setMessage("UnknownHostException: " + e.toString());
										aDialog.setTitle("LegoRobotPi");
										aDialog.setPositiveButton("OK", null);
										aDialog.create().show();
									}
								});
							} 
							finally {
								
								pDialog.dismiss();
							}
						}
					});
					thread.start();
				}
				catch (NumberFormatException e) {
					
					AlertDialog.Builder aDialog = new Builder(SettingsActivity.this);
					aDialog.setMessage("NumberFormatException: " + e.toString());
					aDialog.setTitle("LegoRobotPi");
					aDialog.setPositiveButton("OK", null);
					aDialog.create().show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
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
