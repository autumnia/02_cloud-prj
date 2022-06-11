package com.autumnia.batch.tls;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpsConn {
	private String data;
	private HttpURLConnection conn;
	private String body;
	
	public String getData() {
		return this.data;
	}
	
	public void run() {
		StringBuffer sb = null; 
		
		try {
			sb = new StringBuffer();
			sendBody();
			getData(sb);	
		} 
		catch (Exception e) {
			System.out.println(e.toString());
		}	
		finally {
			sb.setLength(0);
		}
	}
	
	public void setMethod( String method ) throws Exception {
		this.conn.setRequestMethod( method );
	}

	public void getData(StringBuffer sb) throws MalformedURLException, IOException {
	//		conn.setRequestMethod("POST");
	//		conn.setDoOutput(true);
	//		conn.setConnectTimeout(30000);
	//		conn.setReadTimeout(30000);		
	//		conn.setRequestProperty("appKey", "0fb2d73d-0909-4848-923c-5062110364f0");
	//		conn.setRequestProperty("Content-Type", "application/json");
		
			InputStreamReader in = new InputStreamReader((InputStream) conn.getContent());

				BufferedReader br = new BufferedReader(in);
				String line;
				while ( (line = br.readLine() ) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				
			in.close();
		conn.disconnect();
		
		this.data =  sb.toString();
	}

	private void sendBody() throws IOException {
		OutputStream outputStream = conn.getOutputStream() ; 
		outputStream.write(body.getBytes());
		outputStream.flush();
	}

	// 빌더 패턴
	final static class Builder {
		private HttpURLConnection conn;
		private URL uri;
		private String body ;
		
        public Builder() { }

        public Builder setRequestProperty(String key, String value) throws Exception{
        	conn.setRequestProperty(key, value);
        	
        	return this;
        }             
        
        public Builder setReadTimeout(int value) throws Exception{
        	conn.setReadTimeout(value);
        	return this;
        }                        
        
        public Builder setConnectTimeout(int value) throws Exception{
        	conn.setConnectTimeout(value);
        	return this;
        }                
        
        public Builder setDoOutput(Boolean value) throws Exception{
        	conn.setDoOutput(value);
        	return this;
        }        
        
        public Builder setMethod(String method) throws Exception{
        	conn.setRequestMethod(method);
        	return this;
        }
        
        public Builder setUrl(String url) throws Exception{
        	uri = new URL( url );
        	setSSLContext( getTrustAllCerts() );
        	conn = (HttpURLConnection)uri.openConnection();
            return this;
        }
        
		private void setSSLContext(TrustManager[] trustAllCerts) throws NoSuchAlgorithmException, KeyManagementException {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		}
		
		private TrustManager[] getTrustAllCerts() {
			TrustManager[] trustAllCerts = new TrustManager[] { 
					new X509TrustManager() {
						public java.security.cert.X509Certificate[] getAcceptedIssuers() {
							return null;
						}
	
						public void checkClientTrusted(X509Certificate[] certs, String authType) {	}
	
						public void checkServerTrusted(X509Certificate[] certs, String authType) {}
					} 
			};
			return trustAllCerts;
		}	        

		public Builder setBody(String body) {
			this.body = body;
			
			return this;
		}     		
		
        public HttpsConn build() {
            return new HttpsConn(this);
        }


	 }	
	
	// 생성자 시리즈
    public HttpsConn(Builder builder) {
    	this.conn = builder.conn;
    	this.body = builder.body;
    }

	public HttpsConn() {
		
	}
}
