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
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

	EditText id,password;
	Button login,signup;
	String id_text, pw_text;
	
	//ID 저장을 위한 shared preference
	SharedPreferences pref;
	SharedPreferences.Editor editor;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기 (setcontentview 전에 불러줘야함)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.login);
        
        
        id = (EditText) findViewById(R.id.id);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);
        
    	pref = getSharedPreferences("pref", MODE_PRIVATE);
    	editor = pref.edit();
    	
    	//이미 로그인 한적이 있으면 shared reference에서 ID 불러오기
        if(pref.getString("ID", null) != null)
        	id.setText(pref.getString("ID",""));
        
       //로그인 버튼
        login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				id_text = id.getText().toString();
				pw_text = password.getText().toString();
				//http post를 요청하기위해 asynctask 를 불러온다
				new ProcessLoginTask().execute(null, null, null);
			}
		});
        
        //회원가입 버튼
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
				Toast.makeText(getApplicationContext(), "아이디나 비밀번호가 잘못됬습니다.", Toast.LENGTH_SHORT).show();
			} else{
				Toast.makeText(getApplicationContext(), "안녕하세요, " + responseBody, Toast.LENGTH_SHORT).show();
				//shared reference에서 아이디 저장
				editor.putString("ID", responseBody);
				editor.commit();
				Intent main = new Intent(getApplicationContext(), Main.class);
				startActivity(main);
			}
		}
	}
        
}
