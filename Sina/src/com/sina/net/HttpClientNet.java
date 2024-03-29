package com.sina.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.sina.service.ServiceType;

import android.os.Handler;


public class HttpClientNet {

	private String domainAddress = ""; // 도메인 어드레스
	private List<NameValuePair> params; // 파라미터 모
	private HttpClient httpClient = new DefaultHttpClient(); // 클라이언트 연결	
	private boolean check = false;
	/**
	 * 생성자
	 */
	public HttpClientNet() {
		params = new ArrayList<NameValuePair>();
	}

	public HttpClientNet(int serviceType) {
		this.domainAddress = ServiceType.getInstance().getUrl(serviceType);
		params = new ArrayList<NameValuePair>();
	}

	public HttpClientNet(String domainAddress) {
		this.domainAddress = domainAddress;
		params = new ArrayList<NameValuePair>();
	}

	public HttpClientNet(String domainAddress, List<NameValuePair> params) {
		this.domainAddress = domainAddress;
		this.params = params;
	}
	
	public String doHttpFilePost() throws Exception
	{
		
		StringBuffer	strbuffer = new StringBuffer();
		BufferedReader	inreader = null;
		HttpResponse response = null;
		HttpPost 	 request = new HttpPost();
		try {	
			request.setURI(new URI(domainAddress));
			//MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, boundary, Charset.forName("utf-8"));
	        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	
			for( int i = 0 ; i < params.size() ; i++ )
			{
				if( params.get(i).getName().equals("thum") )
				{
					if(params.get(i).getValue() != null && !params.get(i).getValue().equals(""))
					{
							File file = new File(params.get(i).getValue());
							ContentBody bin = new FileBody(file, "image/jpeg");
							entity.addPart(params.get(i).getName(), bin);
					}
				}
				else
				{
					entity.addPart(params.get(i).getName(), new StringBody(params.get(i).getValue(), Charset.forName("utf-8")));
				}
			}
	
	        request.setEntity(entity);
	        
			
	        //Util.eLog("HttpClientImpl.doHttpPost", String.format("encoded request URL = (%s), params = %s", url, EntityUtils.toString(request.getEntity() )) );
	////
	//		setHttpStatus(0);
	
			response = httpClient.execute(request);					
	//		status = response.getStatusLine();		
	//		setHttpStatus(status.getStatusCode());
	
			inreader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
			//inreader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			{			
				String 	strline = new String();
			
				while ((strline = inreader.readLine()) != null) {
					strbuffer.append(strline);
				}
			}
			return strbuffer.toString();
		}
		finally {
			if (inreader != null) {
				try {
					inreader.close();
				} catch (IOException ie) {
					throw new Exception(ie.getMessage());
				}
			}			
		}		
	}
	// post방식 실제 데이터 이동 HttpPost 메소드 사용
	public String httpToPost() {
		StringBuffer strbuffer = new StringBuffer();
		BufferedReader inreader = null;
		StatusLine status = null;
		HttpResponse response = null;

		try {
			HttpPost request = new HttpPost(domainAddress);

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,
					"utf-8");

			request.setEntity(entity);
			response = httpClient.execute(request);

			inreader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent(), "utf-8"));
			// inreader = new BufferedReader(new
			// InputStreamReader(response.getEntity().getContent()));
			{
				String strline = new String();

				while ((strline = inreader.readLine()) != null) {
					strbuffer.append(strline);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return strbuffer.toString();
	}

	public String httpToPost(int serviceType) {
		StringBuffer strbuffer = new StringBuffer();
		BufferedReader inreader = null;
		StatusLine status = null;
		HttpResponse response = null;

		try {
			HttpPost request = new HttpPost(ServiceType.getInstance().getUrl(
					serviceType));

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,
					"utf-8");

			request.setEntity(entity);
			response = httpClient.execute(request);

			inreader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent(), "utf-8"));
			// inreader = new BufferedReader(new
			// InputStreamReader(response.getEntity().getContent()));
			{
				String strline = new String();

				while ((strline = inreader.readLine()) != null) {
					strbuffer.append(strline);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return strbuffer.toString();
	}

	public String httpToPost(String url) {
		StringBuffer strbuffer = new StringBuffer();
		BufferedReader inreader = null;
		StatusLine status = null;
		HttpResponse response = null;

		try {
			HttpPost request = new HttpPost(url);

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,
					"utf-8");

			request.setEntity(entity);
			response = httpClient.execute(request);

			inreader = new BufferedReader(new InputStreamReader(response
					.getEntity().getContent(), "utf-8"));
			// inreader = new BufferedReader(new
			// InputStreamReader(response.getEntity().getContent()));
			{
				String strline = new String();

				while ((strline = inreader.readLine()) != null) {
					strbuffer.append(strline);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return strbuffer.toString();
	}
	
	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public void setParam(ArrayList<Params> params) {
		for (Params p : params) {
			this.params.add(new BasicNameValuePair(p.getId(), p.getValue()));
		}
	}

	/**
	 * 실제 클래스들에서 사용하게 되는 메소드 호출하면 통신이 일어나고 리턴을 OnResponseListener 가 받음
	 * 
	 * @param callbackListener
	 */

	public void doAsyncExecute(OnResponseListener callbackListener) {
		AsyncHttpDoPost httpDoPost = new AsyncHttpDoPost(new Handler(),
				new CallbackWrapper(callbackListener));

		httpDoPost.start();
	}

	protected class AsyncHttpDoPost extends Thread {
		private Handler handler = null;
		private CallbackWrapper wrapper = null;
		private String reqUrl = null;
		private String resContent;

		public AsyncHttpDoPost(Handler handler, CallbackWrapper wrapper) {
			this.handler = handler;
			this.wrapper = wrapper;
		}

		public AsyncHttpDoPost(Handler handler, String reqUrl,
				CallbackWrapper wrapper) {
			this.handler = handler;
			this.wrapper = wrapper;
			this.reqUrl = reqUrl;
		}

		public AsyncHttpDoPost(Handler handler, int serviceType,
				CallbackWrapper wrapper) {
			this.handler = handler;
			this.wrapper = wrapper;
			this.reqUrl = ServiceType.getInstance().getUrl(serviceType);
		}

		/**
		 * 런 하게 되면 통신이 일어나고 그 리턴값을 저장하고 쓰레드 CallBackWapper 를 돌려 시점을 맞
		 */
		@Override
		public void run() {
			try {

				// resContent = doHttpPost(httpClient, reqUrl);
				if(check)
				{
					resContent = doHttpFilePost();
				}
				else
				{
					resContent = httpToPost();
				}
			}
			// catch( ConnectTimeoutException e )
			// {
			// e.printStackTrace();
			// }
			// catch( SocketTimeoutException e )
			// {
			// e.printStackTrace();
			// }
			catch (Exception e) {
				e.printStackTrace();
			}
			wrapper.setResContent(resContent);
			handler.post(wrapper);
		}
	}

	public interface OnResponseListener {
		public void onResponseReceived(String resContent);
	}

	/**
	 * 이 콜랙 웹퍼가 런 되면 인터페이스 OnResposeReceived call!
	 * 
	 * @author takeoff004
	 * 
	 */
	protected class CallbackWrapper implements Runnable {
		private OnResponseListener callbackActivity;
		private String resContent;

		public CallbackWrapper(OnResponseListener callbackActivity) {
			this.callbackActivity = callbackActivity;
		}

		@Override
		public void run() {
			callbackActivity.onResponseReceived(resContent);
		}

		public void setResContent(String resContent) {
			this.resContent = resContent;
		}
	}
	
	
	
	
/*
	public void HttpFileUpload(String urlString, String params, String fileName) {
	    try {

	        mFileInputStream = new FileInputStream(fileName);
	        connectUrl = new URL(urlString);
	        Log.d("Test", "mFileInputStream  is " + mFileInputStream);

	        // open connection 
	        HttpURLConnection conn = (HttpURLConnection)connectUrl.openConnection();   
	        conn.setDoInput(true);
	        conn.setDoOutput(true);
	        conn.setUseCaches(false);
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Connection", "Keep-Alive");
	        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

	        // write data
	        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
	        dos.writeBytes(twoHyphens + boundary + lineEnd);
	        dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + fileName+"\"" + lineEnd);
	        dos.writeBytes(lineEnd);

	        int bytesAvailable = mFileInputStream.available();
	        int maxBufferSize = 1024;
	        int bufferSize = Math.min(bytesAvailable, maxBufferSize);

	        byte[] buffer = new byte[bufferSize];
	        int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);

	        Log.d("Test", "image byte is " + bytesRead);

	        // read image
	        while (bytesRead > 0) {
	            dos.write(buffer, 0, bufferSize);
	            bytesAvailable = mFileInputStream.available();
	            bufferSize = Math.min(bytesAvailable, maxBufferSize);
	            bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
	        } 

	        dos.writeBytes(lineEnd);
	        dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

	        // close streams
	        Log.e("Test" , "File is written");
	        mFileInputStream.close();
	        dos.flush(); // finish upload...   

	        // get response
	        int ch;
	        InputStream is = conn.getInputStream();
	        StringBuffer b =new StringBuffer();
	        while( ( ch = is.read() ) != -1 ){
	            b.append( (char)ch );
	        }
	        String s=b.toString(); 
	        Log.e("Test", "result = " + s);
	        mEdityEntry.setText(s);
	        dos.close();   

	    } catch (Exception e) {
	        Log.d("Test", "exception " + e.getMessage());
	        // TODO: handle exception
	    }  
	}
*/
}
