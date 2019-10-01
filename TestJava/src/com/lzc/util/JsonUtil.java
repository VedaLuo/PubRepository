package com.lzc.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static Map<?,?> readJson(String json) throws Exception {
		Map<?,?> m = mapper.readValue(json, Map.class);		
		return m;
	}
		
	public static String toJson(Map<String,Object> map){
		String strJson = null;
        try{
        	map.put("result", "100");
        	mapper.setDateFormat(CommonUtil.datetimeFormat);
        	strJson=mapper.writeValueAsString(map); //转换    	
        	map.clear();
        }catch(Exception e){
        	e.printStackTrace();
        	strJson="{\"result\":\"99\",\"msg\":\""+e.getMessage()+"\"}";
        }
        return strJson;
	}
	
	public static String toJson(Object obj){
		String strJson = null;
		Map<String,Object> map = new HashMap<String,Object>();		
        try{
        	map.put("result", "100");
        	map.put("data", obj);
        	mapper.setDateFormat(CommonUtil.datetimeFormat);
        	strJson=mapper.writeValueAsString(map); //转换  
        }catch(Exception e){
        	e.printStackTrace();
        	strJson="{\"result\":\"99\",\"msg\":\""+e.getMessage()+"\"}";
        }
    	map.clear();
    	map=null;        
        return strJson;
	}
	
	public static String toJson(String code,Map<String,Object> map){
		String strJson = null;
        try{
        	map.put("result", code);
        	mapper.setDateFormat(CommonUtil.datetimeFormat);
        	strJson=mapper.writeValueAsString(map); //转换    	
        	map.clear();
        	map=null;
        }catch(Exception e){
        	e.printStackTrace();
        	strJson="{\"result\":\"99\",\"msg\":\""+e.getMessage()+"\"}";
        }
        return strJson;
	}
	
	public static String toListJson(String name,List<?> list){
		String strJson = null;
		Map<String,Object> map = new HashMap<String,Object>();		
        try{
        	map.put(name, list);
        	map.put("result", "100");
        	mapper.setDateFormat(CommonUtil.datetimeFormat);
        	strJson=mapper.writeValueAsString(map); //转换  
        }catch(Exception e){
        	e.printStackTrace();
        	strJson="{\"result\":\"99\",\"msg\":\""+e.getMessage()+"\"}";
        }
        
    	map.clear();
    	map=null;
    	if(list!=null){list.clear();}
    	list=null;        
        return strJson;
	}
	
	public static String toSuccessJson(String msg){
		String strJson = null;
        try{
        	Map<String,Object> map=new HashMap<String,Object>(); 
        	map.put("result", "100");
        	map.put("msg", msg);
        	mapper.setDateFormat(CommonUtil.datetimeFormat);
        	strJson=mapper.writeValueAsString(map); //转换  
        	map.clear();
        	map=null;
        }catch(Exception e){
        	e.printStackTrace();
        	strJson="{\"result\":\"99\",\"msg\":\""+e.getMessage()+"\"}";
        }
        return strJson;
	}
	
	public static String toErrorJson(String error){
        return toErrorJson("1",error);
	}
	
	public static String toErrorJson(String code,String error){
		if(StringUtil.isBlank(code)){code="unknown";}
		String strJson = null;
        try{
        	Map<String,Object> map=new HashMap<String,Object>(); 
        	map.put("result", code);
        	map.put("msg", error);
        	mapper.setDateFormat(CommonUtil.datetimeFormat);
        	strJson=mapper.writeValueAsString(map); //转换  
        	map.clear();
        	map=null;
        }catch(Exception e){
        	e.printStackTrace();
        	strJson="{\"result\":\"99\",\"msg\":\""+e.getMessage()+"\"}";
        }
        return strJson;
	}
	
}
