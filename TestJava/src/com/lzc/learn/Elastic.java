package com.lzc.learn;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class Elastic {
	
	private static RestHighLevelClient client = null;
	
	public static void test(){
		//create();
		//update();
		//delete();
		
		query();
	}

	public static void create(){
		
		Map<String,Object> map=new HashMap<>();
		map.put("userName", "张三");
		map.put("sendDate", new Date());
		map.put("msg", "你好李四");
		
		RestHighLevelClient client = getClient();
		try {
			
			//CreateIndexRequest request = new CreateIndexRequest("test_index");//创建索引
	        
			IndexRequest request = new IndexRequest("test_index", "doc", "id001")
	        .source(map)
	        .opType(DocWriteRequest.OpType.CREATE);
			
	        //同步执行
			IndexResponse response = client.index(request, RequestOptions.DEFAULT);
	        System.out.println("创建===========::"+response.getResult().toString());	      
	        
	        /***
	        //异步执行
	        client.indexAsync(request, RequestOptions.DEFAULT, new ActionListener<IndexResponse>(){
	            @Override
	            public void onResponse(IndexResponse response) {
	            	System.out.println("创建-异步:" + response.getResult().toString());
	            }

	            @Override
	            public void onFailure(Exception e) {
	            	System.out.println("===============数据异常");
	            	e.printStackTrace();
	            }
	        });
	        ****/
		}catch(Exception e) { 
			e.printStackTrace(); 
		}finally{
			close(client);//异步执行时候不要此句
		}
	}
	
	public static void query(){
		RestHighLevelClient client = getClient();
		try {
			GetRequest request = new GetRequest("test_index", "doc", "id001");
			
	        //同步执行
			GetResponse response = client.get(request, RequestOptions.DEFAULT);			
			System.out.println("索引库的数据:" + response.getSourceAsString());
			
			/*****
	        //异步执行
			client.getAsync(request, RequestOptions.DEFAULT, new ActionListener<GetResponse>(){
	            @Override
	            public void onResponse(GetResponse response) {
	            	System.out.println("索引库的数据-异步:" + response.getSourceAsString());
	            }

	            @Override
	            public void onFailure(Exception e) {
	            	System.out.println("===============索引库的数据异常");
	            	e.printStackTrace();
	            }
	        });
	        ****/
		}catch(Exception e) { 
			e.printStackTrace(); 
		}finally{
			close(client);//异步执行时候不要此句
		}
	}
	
	public static void update(){
		
		Map<String,Object> map=new HashMap<>();
		map.put("userName", "张三");
		map.put("sendDate", new Date());
		map.put("msg", "你好李四_U3");
		map.put("sex", "F");
		
		RestHighLevelClient client = getClient();
		try {
			
			//CreateIndexRequest request = new CreateIndexRequest("test_index");//创建索引
	        
			UpdateRequest request = new UpdateRequest("test_index", "doc", "id001").doc(map);
			
	        //同步执行
			//UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
	        //System.out.println("更新===========::"+response.getResult().toString());	      
	        
	        //异步执行
			client.updateAsync(request, RequestOptions.DEFAULT, new ActionListener<UpdateResponse>(){
	            @Override
	            public void onResponse(UpdateResponse response) {
	            	System.out.println("更新-异步:" + response.getResult().toString());
	            }

	            @Override
	            public void onFailure(Exception e) {
	            	System.out.println("===============数据异常");
	            	e.printStackTrace();
	            }
	        });
	        
		}catch(Exception e) { 
			e.printStackTrace(); 
		}finally{
			//close(client);//异步执行时候不要此句
		}
	}
	
	public static void delete(){
		RestHighLevelClient client = getClient();
		try {
			
			//CreateIndexRequest request = new CreateIndexRequest("test_index");//创建索引
	        
			DeleteRequest  request = new DeleteRequest ("test_index", "doc", "id001");
			
	        //同步执行
			DeleteResponse  response = client.delete(request, RequestOptions.DEFAULT);
	        System.out.println("删除===========::"+response.getResult().toString());	      
	        
			/***
	        //异步执行
			client.deleteAsync(request, RequestOptions.DEFAULT, new ActionListener<DeleteResponse >(){
	            @Override
	            public void onResponse(DeleteResponse response) {
	            	System.out.println("删除-异步:" + response.getResult().toString());
	            }

	            @Override
	            public void onFailure(Exception e) {
	            	System.out.println("===============数据异常");
	            	e.printStackTrace();
	            }
	        });
	        ****/
		}catch(Exception e) { 
			e.printStackTrace(); 
		}finally{
			close(client);//异步执行时候不要此句
		}
	}
	
	
	private static RestHighLevelClient getClient(){
		if(client!=null){return client;}
		String HOST="192.168.3.60";
		int    PORT=9200;		
		client = new RestHighLevelClient(
				RestClient.builder(
						new HttpHost(HOST, PORT, "http")
		));		
		return client;
	}
	
	private static void close(RestHighLevelClient client){
		if(client!=null){
			try{client.close();}catch(Exception ex){}
		}	 
	}
	
}
