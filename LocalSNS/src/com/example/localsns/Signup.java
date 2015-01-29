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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends Activity {

	EditText new_id,new_password;
	Button signup;
	String id_text,pw_text;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기 (setcontentview 전에 불러줘야함)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.signup);
        
        new_id = (EditText) findViewById(R.id.new_id);
        new_password = (EditText) findViewById(R.id.new_password);
        signup = (Button) findViewById(R.id.do_signup);
        
        signup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				id_text = new_id.getText().toString();
				pw_text = new_password.getText().toString();
				new ProcessSignupTask().execute(null,null,null);
							
			}
		});
        
        
    }
    
    private class ProcessSignupTask extends AsyncTask<Void, Void, Void> {
		HttpResponse response = null;
		String responseBody = null;

		@Override
		protected Void doInBackground(Void... params) {
				HttpClient httpClient = new DefaultHttpClient();
				String urlString = "http://54.64.246.22:9000/signup";
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

			if (responseBody.equals("already_exists")) {
				Toast.makeText(getApplicationContext(), "이미 존재하는 아이디입니다", Toast.LENGTH_SHORT).show();
				//Intent myIntent = new Intent(getApplicationContext(),Category.class);
				//startActivity(myIntent);
			} else
				Toast.makeText(getApplicationContext(), "환영합니다, " + responseBody, Toast.LENGTH_SHORT).show();
		}
	}
    
    
}
