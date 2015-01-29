package com.example.localsns;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

	EditText id,password;
	Button login,signup;
	String id_text, pw_text;
//	saveCookie pref = new saveCookie(this);
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        id = (EditText) findViewById(R.id.id);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);
        
        login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				id_text = id.getText().toString();
				pw_text = password.getText().toString();
				new ProcessLoginTask().execute(null, null, null);
			}
		});
        
        signup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent signup = new Intent(getApplicationContext(),Signup.class);
				startActivity(signup);
			}
		});
                
    }
    
    private class ProcessLoginTask extends AsyncTask<Void, Void, Void> {
		HttpResponse response = null;
		String responseBody = null; 

		@Override
		protected Void doInBackground(Void... params) {
				HttpClient httpClient = new DefaultHttpClient();
				String urlString = "http://54.64.246.22:9000/login";
				HttpPost httpPost = new HttpPost(urlString);

				try {
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair> (2);
					nameValuePairs.add(new BasicNameValuePair("id", id_text));
					nameValuePairs.add(new BasicNameValuePair("pw", pw_text));
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					
					response = httpClient.execute(httpPost);
					responseBody = EntityUtils.toString(response.getEntity());
										
				} catch (ClientProtocolException e) {
				} catch (IOException e) {
				}
			return null;
		}
		
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			Log.e("responsebody"," " + responseBody);
			
			if (responseBody.equals("none")) {
				Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
				//Intent myIntent = new Intent(getApplicationContext(),Category.class);
				//startActivity(myIntent);
			} else
				Toast.makeText(getApplicationContext(), "Hello, " + responseBody, Toast.LENGTH_SHORT).show();
		}
	}
        
}
