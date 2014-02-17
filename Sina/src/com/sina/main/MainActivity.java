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

public class MainActivity extends Activity implements View.OnClickListener , HttpClientNet.OnResponseListener{
	private Context mContext;
    private LinearLayout m_oBtnCamera;
    private LinearLayout m_oBtnGallery;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mContext = this;
        setUi();
	}
	public void setUi()
    {
    	m_oBtnCamera = (LinearLayout)findViewById(R.id.camera);
    	m_oBtnGallery = (LinearLayout)findViewById(R.id.gallery);
    	m_oBtnCamera.setOnClickListener(this);
    	m_oBtnGallery.setOnClickListener(this);
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
        m_oBtnCamera = null;
    	m_oBtnGallery = null;
    }
   
	
//	public void startProgressDialog() 
//	{
//		if( loading == null )
//		{
//			loading = new LoadingPopup(this);
//			loading.start();
//		}
//	}
//	
//	public void stopProgressDialog() 
//	{
//		if( loading != null )
//		{
//			loading.stop();
//			loading = null;
//		}
//	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.camera :
			{
				break;
			}
			case R.id.gallery :
			{
				break;
			}
		}
		
	}

	@Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		try{
			
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
//			if(check == true)
//				stopProgressDialog() ;
		}
	}
}
