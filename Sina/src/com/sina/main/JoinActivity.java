package com.sina.main;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

import com.sina.R;
import com.sina.componet.LoadingPopup;
import com.sina.net.HttpClientNet;
import com.sina.net.Params;
import com.sina.service.ServiceType;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class JoinActivity extends Activity implements View.OnClickListener , HttpClientNet.OnResponseListener{
  

    private Context mContext;
    
    private EditText m_oEmail;
    private EditText m_oPassword;
    private EditText m_oPasswordConfirm;
    private LinearLayout m_oBtnConfirm;
    private ImageView m_oImgDoubleCheck;
    
    private boolean checkDouble = false; 

    private LoadingPopup loading;
   
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);
        mContext = this;
        setUi();
       
    }
    public void setUi()
    {
    	m_oEmail = (EditText)findViewById(R.id.join_email);
    	m_oPassword = (EditText)findViewById(R.id.join_password);
    	m_oPasswordConfirm = (EditText)findViewById(R.id.join_password_confirm);
    	m_oBtnConfirm = (LinearLayout)findViewById(R.id.btn_confirm);
    	m_oImgDoubleCheck = (ImageView)findViewById(R.id.double_check_img);
    	m_oEmail.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus==false)
				{
					if((m_oEmail.getText().toString().equals("")))
					{
						
						Toast.makeText(JoinActivity.this, "Please enter id", Toast.LENGTH_SHORT).show();
					}
					else
					{
						reqeustDoubleId();
					}
				}
				
			}
		});
    	
    	m_oBtnConfirm.setOnClickListener(this);
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
    	m_oPasswordConfirm = null;
    	m_oBtnConfirm  = null;
    	m_oImgDoubleCheck = null;
    	m_oEmail = null;
    }
    public void reqeustDoubleId()
	{
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_JOIN_DOUBLE_ID);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("id", m_oEmail.getText().toString()));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		startProgressDialog();
	}
    public void requestJoin()
	{
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_JOIN);
		loginService.setCheck(true);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("id", m_oEmail.getText().toString()));
		loginParams.add(new Params("pwd", m_oPassword.getText().toString()));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		startProgressDialog();
	}
   
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			
			case R.id.btn_confirm : 
			{
				if(!checkDouble)
				{
					Toast.makeText(this, "id redundancy check", Toast.LENGTH_SHORT).show();
				}
				else if(m_oPassword.getText().toString().equals(""))
				{
					Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();			}
				else if(m_oPassword.getText().toString().length() < 4)
				{
					Toast.makeText(this, "Please enter at least a four-digit password", Toast.LENGTH_SHORT).show();
				}
				else if(!m_oPassword.getText().toString().equals(m_oPasswordConfirm.getText().toString()))
				{
					Toast.makeText(this, "Password and confirm the value is different.", Toast.LENGTH_SHORT).show();
				}
				else
				{
					requestJoin();
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
					Toast.makeText(this, "Id is available.", Toast.LENGTH_SHORT).show();
					checkDouble = true;
					m_oImgDoubleCheck.setBackgroundResource(R.drawable.check_double);
				}
				else
				{
					Toast.makeText(this, "There is a duplicate email.", Toast.LENGTH_SHORT).show();
					checkDouble = false;
					m_oImgDoubleCheck.setBackgroundResource(R.drawable.check_double_no);
				}
			}
			else if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG.JOIN"))
			{
				String result = object.getJSONObject("data").optString("result","");
				if(result.equals("true"))
				{
					Toast.makeText(this, "Join expire.", Toast.LENGTH_SHORT).show();
					finish();
				}
				else
				{
					Toast.makeText(this, "Join failed.", Toast.LENGTH_SHORT).show();
					finish();
				}
			}
			
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			stopProgressDialog() ;
		}
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

	


   

	
}
