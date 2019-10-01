package com.lzc.util;

import java.security.MessageDigest;

public class StringUtil {
	
	public static boolean isBlank(Object obj){
		if(obj==null) {return true;}
		if(obj.toString().length()==0){return true;}
		return false;
	}
	
	public static String toStr(Object obj){
		if(obj==null){return "";}
		return obj.toString();
	}
	
	public static boolean isNotBlank(Object obj){
		if(obj==null) {return false;}
		if(obj.toString().length()==0){return false;}
		return true;
	}
	
	public static String toMD5(String s) {
		try 
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(s.getBytes("utf-8"));
			final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
			StringBuilder ret = new StringBuilder(bytes.length * 2);
			for (int i = 0; i < bytes.length; i++) 
			{
				ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
				ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
			}
			return ret.toString();
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
