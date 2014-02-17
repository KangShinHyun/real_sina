package com.sina.main;

import java.util.ArrayList;

import org.json.JSONObject;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.sina.R;
import com.sina.componet.LoadingPopup;
import com.sina.net.HttpClientNet;
import com.sina.net.Params;
import com.sina.object.LoginInfoObject;
import com.sina.service.ServiceType;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoginActivity extends Activity implements View.OnClickListener , HttpClientNet.OnResponseListener{
	private Context mContext;
    private LoadingPopup loading;
    private EditText m_oEmail;
    private EditText m_oPassword;
    private LinearLayout m_oBtnFacebookConfirm;
    private LinearLayout m_oBtnConfirm;
    private LinearLayout m_oBtnJoin;
    private LinearLayout m_oBtnAutoLogin;
    private ImageView m_oImgAutoLogin;
    private boolean checkAutoLogin = true;
    private String id;
    private boolean check = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		mContext = this;
        setUi();
	}
	public void setUi()
    {
    	m_oEmail = (EditText)findViewById(R.id.login_email);
    	m_oPassword = (EditText)findViewById(R.id.login_password);
    	m_oBtnConfirm = (LinearLayout)findViewById(R.id.btn_login);
    	m_oBtnFacebookConfirm = (LinearLayout)findViewById(R.id.btn_facebook_login);
    	m_oBtnJoin = (LinearLayout)findViewById(R.id.btn_join);
    	m_oBtnAutoLogin  = (LinearLayout)findViewById(R.id.btn_autologin);
    	m_oImgAutoLogin = (ImageView)findViewById(R.id.auto_login_check);
    	m_oBtnAutoLogin.setOnClickListener(this);
    	m_oBtnFacebookConfirm.setOnClickListener(this);
    	m_oBtnConfirm.setOnClickListener(this);
    	m_oBtnJoin.setOnClickListener(this);
    }
  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	    protected void onResume() {
	        super.onResume();

	 }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onDestroy() {
       
        super.onDestroy();
        m_oEmail = null;
    	m_oPassword = null;
    	m_oBtnConfirm = null;
    	m_oBtnFacebookConfirm = null;
    	m_oBtnAutoLogin  = null;
    	m_oImgAutoLogin = null;
    	m_oBtnConfirm = null;
    }
    public void reqeustDoubleId()
	{
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_JOIN_DOUBLE_ID);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("id", id));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		startProgressDialog();
	}
    public void requestJoin(String email, String pwd)
   	{
   		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_JOIN);
   		ArrayList<Params> loginParams = new ArrayList<Params>();
   		SharedPreferences sp = getSharedPreferences("autologin", MODE_PRIVATE);
   		SharedPreferences.Editor editer = sp.edit();
   		loginParams.add(new Params("facebook", sp.getBoolean("facebook", true)+""));
   		loginParams.add(new Params("id", email));
   		loginParams.add(new Params("pwd", pwd));
   		loginService.setParam(loginParams);
   		loginService.doAsyncExecute(this);
   		startProgressDialog();
   	}
    public void requestJoin(String email, String pwd, String name,String thum)
	{
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_JOIN);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		SharedPreferences sp = getSharedPreferences("autologin", MODE_PRIVATE);
		SharedPreferences.Editor editer = sp.edit();
		loginParams.add(new Params("facebook", sp.getBoolean("facebook", true)+""));
		loginParams.add(new Params("id", email));
		loginParams.add(new Params("pwd", pwd));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		startProgressDialog();
	}
    public void requestLogin()
	{
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_LOGIN);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		SharedPreferences sp = getSharedPreferences("autologin", MODE_PRIVATE);
		SharedPreferences.Editor editer = sp.edit();
		loginParams.add(new Params("facebook", sp.getBoolean("facebook", false)+""));
		loginParams.add(new Params("id",m_oEmail.getText().toString()));
		loginParams.add(new Params("pwd",m_oPassword.getText().toString()));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		startProgressDialog();
	}
    public void requestLogin(String id)
	{
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_LOGIN);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		SharedPreferences sp = getSharedPreferences("autologin", MODE_PRIVATE);
		SharedPreferences.Editor editer = sp.edit();
		loginParams.add(new Params("facebook", sp.getBoolean("facebook", true)+""));
		loginParams.add(new Params("id",id));
		loginParams.add(new Params("pwd",""));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		startProgressDialog();
	}
	
	public void startProgressDialog() 
	{
		if( loading == null )
		{
			loading = new LoadingPopup(this);
			loading.start();
		}
	}
	
	public void stopProgressDialog() 
	{
		if( loading != null )
		{
			loading.stop();
			loading = null;
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.btn_join:
			{
				Intent intent =  new Intent(this.getApplicationContext(), JoinActivity.class);
				startActivity(intent);
				break;
			}
			case R.id.btn_autologin :
			{
				if(checkAutoLogin)
				{
					m_oImgAutoLogin.setBackgroundResource(R.drawable.checkbox);
					checkAutoLogin = false;
				}
				else
				{
					
					m_oImgAutoLogin.setBackgroundResource(R.drawable.checkbox_o);
					checkAutoLogin = true;
				}
			}
			case R.id.btn_facebook_login : 
			{
				 Session.openActiveSession(this, true, new Session.StatusCallback() {

		              // callback when session changes state
		              @Override
		              public void call(Session session, SessionState state, Exception exception) {
		                  if (session.isOpened()) {
		                      Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

								@Override
								public void onCompleted(GraphUser user,
										Response response) {
									// TODO Auto-generated method stub
									if (user != null) {
		    							try
		    							{
		    								id = user.asMap().get("email").toString();
		    								String mUserId = user.getId();
		    								SharedPreferences sp = getSharedPreferences("autologin", MODE_PRIVATE);
		    								SharedPreferences.Editor editer = sp.edit();
		    								editer.putBoolean("autologinboolean", true);
		    								editer.putBoolean("facebook", true);
		    								editer.putString("id", id);
		    								editer.putString("pwd", "");
		    								editer.commit();
		    								reqeustDoubleId();
		    							}
		    							catch(Exception e)
		    							{
		    								e.printStackTrace();
		    							}
		    						}
									
								}
		                    });
		                  } else {
		                      
		                  }
		              }
		            });
		            break;
			}
			case R.id.btn_login : 
			{
				if(m_oEmail.getText().toString().equals(""))
				{
					Toast.makeText(this, "Please enter your Id", Toast.LENGTH_SHORT).show();
				}
				else if(m_oPassword.getText().toString().equals("") )
				{
					Toast.makeText(this, "Please enter your Pwd", Toast.LENGTH_SHORT).show();
				}
				else
				{
					
					requestLogin();
				}
				break;
			}
		}
		
	}

	@Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		try{
			Object o = resContent;
			JSONObject object = new JSONObject(resContent);
			if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_JOIN_DOUBLE_ID"))
			{
				String result = object.getJSONObject("data").optString("result","");
				if(result.equals("true"))
				{
					check = false;
					requestJoin(id, "");
				}
				else
				{
					check = false;
					requestLogin(id);
				}
			}
			else if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG.JOIN"))
			{
				String result = object.getJSONObject("data").optString("result","");
				if(result.equals("true"))
				{
					check = false;
					requestLogin(id);
				}
				else
				{
					check = false;
					Toast.makeText(mContext, "Please re-registering", Toast.LENGTH_SHORT).show();
				}
			}
			else if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_LOGIN"))
			{
				String result = object.getJSONObject("data").optString("result","");
				String reason = object.getJSONObject("data").optString("reason","");
				if(result.equals("true"))
				{
					check = true;
					String id = object.getJSONObject("data").optString("id","");
					String pwd = object.getJSONObject("data").optString("pwd","");
					LoginInfoObject.getInstance().setLogin(id, pwd);
					Intent intent =  new Intent(this.getApplicationContext(), MainActivity.class);
					startActivity(intent);
					finish();
					
					SharedPreferences sp = getSharedPreferences("autologin", MODE_PRIVATE);
					SharedPreferences.Editor editer = sp.edit();
					
					editer.putBoolean("autologinboolean", true);
					editer.putString("id", id);
					editer.putString("pwd", pwd);
					editer.commit();
				}
				else
				{
					check = true;
					Toast.makeText(this, reason, Toast.LENGTH_LONG).show();
				}
			}
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			if(check == true)
				stopProgressDialog() ;
		}
	}
}
