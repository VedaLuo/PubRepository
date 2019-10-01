package com.lzc.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.model.Filters;

public class MongoUtil {
	
	public static Bson Filters_like(String name, String value){
		Pattern pattern = Pattern.compile("^.*" + value + ".*$"); //不区分大小写 like %_value%
		return Filters.regex(name, pattern);
	}
	
    public static <T> T toObj(Document doc,Class<T> cls){
    	T t=null;
		try
		{
			t=cls.newInstance();
			BeanInfo bi = Introspector.getBeanInfo(cls);  
	        PropertyDescriptor[] pds = bi.getPropertyDescriptors();  
            for (int z = 0; z < pds.length; z++) 
            {
                String   propName = pds[z].getName();
                Class<?> propType = pds[z].getPropertyType();
                String typeName=propType.getName();
                
                if(propName.equals("class")){continue;}
                //System.out.println(propName+"::"+typeName);
                
                Object value = doc.get(propName);
                if(value==null){continue;}
              
                String valueClsName=value.getClass().getName();                
                if(typeName.equals(valueClsName)){
                	CommonUtil.setPropertyValue(cls,t,propName,value,propType);	
                }else{
                	try{
                		setPropertyValue(cls,t,propName,value,propType);	
                	}catch(Exception ex){
                		ex.printStackTrace();
                	}
                }
            }	        
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}     	     
    	
    	return t;
    }
	
    
	private static void setPropertyValue(Class<?> cls, Object clsObj, String propName, Object paramValue, Class<?> paramType) throws Exception
	{
		if(paramValue==null){return;}
		String typeName=paramType.getName();
		//String valueClsName=paramValue.getClass().getName();
		//System.out.println(propName+"::"+typeName+"::"+paramValue.getClass().getName()+"::"+paramValue);		
		if(typeName.equals("long") || typeName.equals("java.lang.Long")){
			Object value=paramValue;
			CommonUtil.setPropertyValue(cls,clsObj,propName,value,paramType);	
			return;
		}
		
		if(typeName.equals("int") || typeName.equals("java.lang.Integer")){
			Object value=paramValue;
			CommonUtil.setPropertyValue(cls,clsObj,propName,value,paramType);	
			return;
		}
		
		if(typeName.equals("java.math.BigDecimal")){			
			java.math.BigDecimal value=new java.math.BigDecimal(paramValue.toString());
			CommonUtil.setPropertyValue(cls,clsObj,propName,value,paramType);	
			return;
		}
		
		if(typeName.equals("java.sql.Timestamp")){
			String s=CommonUtil.datetimeFormat.format(paramValue);
			java.sql.Timestamp value=java.sql.Timestamp.valueOf(s);
			CommonUtil.setPropertyValue(cls,clsObj,propName,value,paramType);	
			return;
		}
    }
	
}
