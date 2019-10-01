package com.lzc.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class CommonUtil 
{
	public static SimpleDateFormat datetimeFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
	
	public static Timestamp getSysDateTime() {	 	
		String s=datetimeFormat.format(Calendar.getInstance().getTime());		
		return Timestamp.valueOf(s);
	}
	
	public static Date getSysDate() {
		return Calendar.getInstance().getTime();
	}
	
	public static String nextBillNo(String prefix) 
	{
		if(StringUtil.isBlank(prefix)) {return "";}
		if(prefix==null || prefix.trim().length()==0) {
			return "";
		}
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String serial_number = dateFormat.format(Calendar.getInstance().getTime());//日期8,格式yyyyMMddHHmmssSSS
		
		long temp=Math.round(Math.random()*89999+10000);
		String increase_number = temp+"";//增加号
		String order_no = prefix + serial_number + increase_number;
		return order_no;
	}
	
	public static void moveFile(final String filePathSrc,final String filePathDes) {
		File file = new File(filePathSrc);
		if(file.exists()) {			
			file.renameTo(new File(filePathDes));
		}
	}
	
	public static void deleteFile(final String filePath) {
		File file = new File(filePath);
		file.delete();		
	}
	
	public static void setPropertyValue(Class<?> cls, Object clsObj, String propName, Object paramValue, Class<?> paramType) throws Exception
	{
		String methodName="set"+propName.substring(0,1).toUpperCase()+propName.substring(1);
		Class[]  ParamTypes = new Class[]{paramType};			
		Method   method=cls.getMethod(methodName,ParamTypes); 
		Object[] params=new Object[]{paramValue};
		method.invoke(clsObj,params);
	}	
	
	public static <T> Object getPropertyValue(Class<?> cls, T t, String propName) throws Exception
	{
		String methodName="get"+propName.substring(0,1).toUpperCase()+propName.substring(1); 
		Class[]  ParamTypes = new Class[]{};			
		Method   method=cls.getMethod(methodName,ParamTypes); 
		Object[] params=new Object[]{};
		return method.invoke(t,params);
	}
	
	public static String generateId() {
		return UUID.randomUUID().toString();
	}
	
	public static String genToken() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * 获取GPS两点的距离米
	 * 经纬度1-jd1,wd1
	 * 经纬度2-jd2,wd2
	 * **/
	public static String getGpsDistance(String jd1,String wd1,String jd2,String wd2){
		String str="round(6378.138*2*asin(sqrt(pow(sin(("+wd1+"*pi()/180-"+wd2+"*pi()/180)/2),2)+cos("+wd1+"*pi()/180)*cos("+wd2+"*pi()/180)* pow(sin(("+jd1+"*pi()/180-"+jd2+"*pi()/180)/2),2)))*1000)";
		return str;
	}
	
	
	public static ByteBuffer ByteBufferPut(ByteBuffer buffer, byte[] src, int offset, int length){
		int pos = buffer.position();
		if(pos + length> buffer.capacity()){
			 ByteBuffer buffer2 = ByteBuffer.allocate(buffer.capacity()*2);
			 buffer2.put(buffer.array());
			 buffer=null;
			 buffer=buffer2;
		}
		buffer.put(src, offset, length);
		return buffer;		
	}	
	
    public static <T> T sourceToTarget(Object obj,Class<T> cls){    	
    	T t=null;
		try
		{
			t=cls.getDeclaredConstructor(null).newInstance(null);
			
			BeanInfo bi_from = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] arrPdsFrom = bi_from.getPropertyDescriptors();  
			
			BeanInfo bi = Introspector.getBeanInfo(cls);  
	        PropertyDescriptor[] arrPds = bi.getPropertyDescriptors();  
	        	        
            for (PropertyDescriptor pds : arrPds) 
            {
                String   propName = pds.getName();
                Class<?> propType = pds.getPropertyType();
                String typeName=propType.getName();
                
                if(propName.equals("class")){continue;}
                //System.out.println("1====="+propName+"::"+typeName);
                Object value=null;
                for (PropertyDescriptor pdsFrom : arrPdsFrom) {
                	String  propNameFrom = pdsFrom.getName();
                	if(propName.equals(propNameFrom)) {
                		value = CommonUtil.getPropertyValue(obj.getClass(), obj, propName);
                		break;
                	}
                }
                                                
                if(value==null){continue;}
                
            	try{
            		setPropertyValue(cls,t,propName,value,propType);	
            	}catch(Exception ex){
            		ex.printStackTrace();
            	}
            }	        
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}     	     
    	
    	return t;
    }
}
