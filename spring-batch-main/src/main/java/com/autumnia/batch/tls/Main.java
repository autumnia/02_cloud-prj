package com.autumnia.batch.tls;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Main {
	public static void main(String[] args) {
		//String url = "https://www.naver.com";
		String url = "https://oneappdev.lottemart.com/v1/openapi/delivery/v1/DeliveryProgressStateInform";
		String body = "{ \"DELI_NO\": \"210103026307\"" + ",  " + " \"DELI_STATUS_CD\": \"43\"}";

		try {
			HttpsConn conn = new HttpsConn.Builder()
					.setUrl(url)
					.setMethod("POST")
					.setDoOutput(true)
					.setBody(body)
					.setRequestProperty("Content-Type", "application/json; utf-8")					
					//.setConnectTimeout(30000)
					//.setReadTimeout(30000)						
					//.setRequestProperty("appKey", "0fb2d73d-0909-4848-923c-5062110364f0")
					.build();
			conn.run();
			
			System.out.println( conn.getData() );
			
		} catch ( Exception e ) {
			System.out.println( e.getMessage() );
		}

	}
}
