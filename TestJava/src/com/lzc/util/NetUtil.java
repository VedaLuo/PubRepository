package com.lzc.util;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.lzc.model.StudentDemo.Student;

public class NetUtil {
	
	public static String http(String url) throws Exception{
		String result = null;
		// 创建httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建post方式请求对象
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = httpClient.execute(httpPost);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			result = EntityUtils.toString(response.getEntity(), "utf-8");
		}
		// 释放链接
		response.close();
		return result;
	}
	
	public static String httpEntity(String url) throws Exception{
		String result = null;
		// 创建httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建post方式请求对象
		HttpPost httpPost = new HttpPost(url);
		
		//参数形式
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("param1", "value1"));
		formparams.add(new BasicNameValuePair("param2", "value2"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
		
		//大串文本
		//StringEntity entity = new StringEntity("important message", ContentType.create("plain/text", Consts.UTF_8));
		//entity.setChunked(true);
		
		//文本文件
		//File file = new File("somefile.txt");
		//FileEntity entity = new FileEntity(file, ContentType.create("text/plain", "UTF-8")); 
		
		
        //InputStreamEntity entity = new InputStreamEntity(new FileInputStream(file), -1);
		//entity.setContentType("binary/octet-stream");
        //entity.setChunked(true);
		
		
		httpPost.setEntity(entity);//设置发送内容		
		CloseableHttpResponse response = httpClient.execute(httpPost);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			result = EntityUtils.toString(response.getEntity(), "utf-8");
		}
		// 释放链接
		response.close();
		return result;
	}
	
	public static String httpStream(String url, InputStream is) throws Exception{
		String result = null;
		// 创建httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建post方式请求对象
		HttpPost httpPost = new HttpPost(url);
		
		InputStreamEntity entity = new InputStreamEntity(is, is.available());  
		
		httpPost.setEntity(entity);//设置发送内容
		CloseableHttpResponse response = httpClient.execute(httpPost);
		
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			result = EntityUtils.toString(response.getEntity(), "utf-8");
		}
		// 释放链接
		response.close();
		return result;
	}
	
	public static String post(String url,byte[] postdata) throws Exception{
		 URL url1 = new URL(url); 
		 HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection(); 
		 httpURLConnection.setRequestMethod("POST"); 
		 httpURLConnection.setDoInput(true);  //get请求的话默认就行了，post请求需要setDoOutput(true)，这个默认是false的。
		 httpURLConnection.setDoOutput(true); //get请求的话默认就行了，post请求需要setDoOutput(true)，这个默认是false的。
		 httpURLConnection.setConnectTimeout(3000); 	
		 httpURLConnection.connect();
		 if(postdata!=null){
			 BufferedOutputStream outputStream=new BufferedOutputStream(httpURLConnection.getOutputStream()); 
			 outputStream.write(postdata); 
			 outputStream.flush(); 
		 }
		 
		 
		 int buf_size=1024*4;
		 ByteBuffer buffer = ByteBuffer.allocate(buf_size);
		 int datalen=0;
		 if(httpURLConnection.getResponseCode()==200){
			 InputStream ins = httpURLConnection.getInputStream();
			 int len = 0;
			 byte[] bt = new byte[buf_size];
			 while((len = ins.read(bt)) != -1) {				 
				 buffer = CommonUtil.ByteBufferPut(buffer,bt,0,len);
				 //buffer.put(bt, 0, len);
				 datalen=datalen + len;
				 System.out.println(buffer.capacity()+"===="+len+"::"+datalen+"::"+buffer.position());
			 }
			 ins.close();
		 }
		 if(datalen>0){
			 String str=new String(buffer.array(),0,datalen,"utf-8");
			 return str;
		 }
		 
		 return null;		
	}
	
	public static String post(String url,Student student) throws Exception{
		 URL url1 = new URL(url); 
		 HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection(); 
		 httpURLConnection.setRequestMethod("POST"); 
		 httpURLConnection.setDoInput(true);  //get请求的话默认就行了，post请求需要setDoOutput(true)，这个默认是false的。
		 httpURLConnection.setDoOutput(true); //get请求的话默认就行了，post请求需要setDoOutput(true)，这个默认是false的。
		 httpURLConnection.setConnectTimeout(3000); 	
		 httpURLConnection.connect();
		 if(student!=null){
			 student.writeTo(httpURLConnection.getOutputStream());
		 }
		 
		 
		 int buf_size=1024*4;
		 ByteBuffer buffer = ByteBuffer.allocate(buf_size);
		 int datalen=0;
		 if(httpURLConnection.getResponseCode()==200){
			 InputStream ins = httpURLConnection.getInputStream();
			 int len = 0;
			 byte[] bt = new byte[buf_size];
			 while((len = ins.read(bt)) != -1) {				 
				 buffer = CommonUtil.ByteBufferPut(buffer,bt,0,len);
				 //buffer.put(bt, 0, len);
				 datalen=datalen + len;
				 System.out.println(buffer.capacity()+"===="+len+"::"+datalen+"::"+buffer.position());
			 }
			 ins.close();
		 }
		 if(datalen>0){
			 String str=new String(buffer.array(),0,datalen,"utf-8");
			 return str;
		 }
		 
		 return null;		
	}
	
}
