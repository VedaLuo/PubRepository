package com.lzc.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class WeiXinUtil {

	
	public static String openid (String appId,String secret,String js_code) throws Exception {
		String url="https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+secret+"&js_code="+js_code+"&grant_type=authorization_code";
		String json=NetUtil.http(url);
		//json正确格式:{"session_key":"ejKLhi4cjM1Pa4dTjyyfpg==","openid":"otrI95TF6WO3NNx9lIVmXke6VjOM"}
		//json错误格式:{"errcode":40029,"errmsg":"invalid code, hints: [ req_id: xuJGAA05182160 ]"}
		return json;
	}
	
	public static void testOpen() {
		//微信解密方式
		String session_key="meAjXeLeLz1jMb1sxcsnmg==";
		String iv="ZYmjGCyv0Zi54QddbOnhbA==";
		String encryptedData="L7B6DMO8AAW3POJlvlg8TxVHvRcmRq9XgsV0KeL0Ob5udu/9VfE3szTysxGcHvJAKJ5OoMrGTQr1B1dUz5MAmx3gOWEjllR/McaIYHQZ0gec5oOFNcsDJ+0lzfCXcW2xu0tTd7x/I0l6Ijcgt+ueKqQc6QaWKqlUCu5wEGhEW2e3f25uGANKuyU3/pstxMZvXqRPwLm61AQF4GgeDHRGFecRvfuir90UJXnNNpcBfhLncqtK28kETAwhTfrZoqMD1NOUJ0NQXojOj3oxUyTHE6k4Awqr0phVEl2BykoJGAA8yB7LvPP7UqJnTkjOMiIU4x4farZ3T1l25wkVgwu00r8no7CoK5D/m8m+zJelcij3ZXL8kmM1eCSUTq3A9Y8q4aCdipxhsc07kf1fEdI7EPKHra+0tMq5G42KZg6NBd93hROSYS5Lj2rXhK+cJ39j";
		
		try {
			byte[] data = Base64.decodeBase64(encryptedData);  
			byte[] iv_spec = Base64.decodeBase64(iv);  
			byte[] key = Base64.decodeBase64(session_key);  
			
			String str=decodeData(key, iv_spec, data);
			System.out.println("=============\n"+str);
			//结果为: {"openId":"otrI95TF6WO3NNx9lIVmXke6VjOM","nickName":"崇","gender":0,"language":"zh_CN","city":"","province":"","country":"","avatarUrl":"https://wx.qlogo.cn/mmopen/vi_32/iaK9eSOw4rw6EkHapU7Tu1nvbyVO9UrTmszfoZ3fJ4iaSbFuRaYEzt06jQgWCKKs5F413Aq6F8wwqTXNia0N2iazfA/132","watermark":{"timestamp":1542274683,"appid":"wx76c859bcb489cad6"}}
		}catch(Exception e) {
			e.printStackTrace();
		}
 	}
	
	public static String decodeData(byte[] key, byte[] iv, byte[] encData) throws Exception {
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
	    return new String(cipher.doFinal(encData),"UTF-8");
	}
	
	
	/****
	*统一下单 
	* https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=9_1&index=1
   <xml>
   <appid>wx2421b1c4370ec43b</appid>
   <attach>支付测试</attach>
   <body>JSAPI支付测试</body>
   <mch_id>10000100</mch_id>
   <detail><![CDATA[{ "goods_detail":[ { "goods_id":"iphone6s_16G", "wxpay_goods_id":"1001", "goods_name":"iPhone6s 16G", "quantity":1, "price":528800, "goods_category":"123456", "body":"苹果手机" }, { "goods_id":"iphone6s_32G", "wxpay_goods_id":"1002", "goods_name":"iPhone6s 32G", "quantity":1, "price":608800, "goods_category":"123789", "body":"苹果手机" } ] }]]></detail>
   <nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>
   <notify_url>http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php</notify_url>
   <openid>oUpF8uMuAJO_M2pxb1Q9zNjWeS6o</openid>
   <out_trade_no>1415659990</out_trade_no>
   <spbill_create_ip>14.23.150.211</spbill_create_ip>
   <total_fee>1</total_fee>
   <trade_type>JSAPI</trade_type>
   <sign>0CB01533B8C1EF103065174F50BCA001</sign>
   </xml> 	 
   ///////////////////////返回
	<xml>
   <return_code><![CDATA[SUCCESS]]></return_code>
   <return_msg><![CDATA[OK]]></return_msg>
   <appid><![CDATA[wx2421b1c4370ec43b]]></appid>
   <mch_id><![CDATA[10000100]]></mch_id>
   <nonce_str><![CDATA[IITRi8Iabbblz1Jc]]></nonce_str>
   <openid><![CDATA[oUpF8uMuAJO_M2pxb1Q9zNjWeS6o]]></openid>
   <sign><![CDATA[7921E432F65EB8ED0CE9755F0E86D72F]]></sign>
   <result_code><![CDATA[SUCCESS]]></result_code>
   <prepay_id><![CDATA[wx201411101639507cbf6ffd8b0779950874]]></prepay_id>
   <trade_type><![CDATA[JSAPI]]></trade_type>
   </xml>       
   ****/
	public static Map<String,String> prePay(String appId, String openid, String mch_id, String nonceStr, String order_code, int total_fee, 
			String ip, String secret, String notify_url, String wx_pay_url) throws Exception {
		TreeMap<String,Object> params = new TreeMap<String,Object>();
		params.put("appid", appId); //微信分配的小程序ID
		params.put("mch_id", mch_id);//微信支付分配的商户号
		params.put("device_info","WEB");//设备号  String(32)
		params.put("nonce_str",nonceStr);//	随机字符串	 String(32)	
		params.put("body","火号平台支付");//	商品描述	是	String(128)	腾讯充值中心-QQ会员充值
		params.put("out_trade_no", order_code);//	商户订单号	是	String(32)
		params.put("total_fee", total_fee); //	订单总金额 单位为分	是	Int	
		params.put("spbill_create_ip", ip);//	终端IP	是	String(16)	123.12.12.123
		params.put("notify_url", notify_url);//	通知地址	是	String(256)	
		params.put("trade_type", "JSAPI");//	交易类型	是	String(16)	JSAPI
		params.put("openid", openid);//	用户标识	否	String(128)	
		//params.put("sign_type", "MD5");//	签名类型	否	String(32)	MD5
        params.put("sign", getWxPaySign(params, secret));  
        
        String xml=mapToXml(params);
        String xmlRet=sendHttpsRequest(wx_pay_url, xml);
        Map<String,String> map=parseXml(xmlRet);
                
		return map;
	}
	
	
	/**
	 * 对参数按照key=value的格式，并按照参数名ASCII字典序排序
	 * **/
	public static String getWxPaySign(Map<String,Object> map, String key_secret) throws Exception {
		if(StringUtil.isBlank(key_secret)){ throw new Exception("支付密钥不能为空");}		
		Set<String> keySet = map.keySet();		
		String[] str = new String[map.size()];
		str = keySet.toArray(str);
		Arrays.sort(str);
		StringBuilder tmp = new StringBuilder();
		for (int i = 0; i < str.length; i++) {
			if(str[i]!=null&&str[i].trim().equalsIgnoreCase("sign")){//不参与签名
				continue;
			}
			
			Object v=map.get(str[i]);
			if(v==null || v.toString().trim().length()==0){//值为空不参与签名
				continue;
			}
			
			String t = str[i] + "=" + map.get(str[i]) + "&";			
			tmp.append(t);			
		}
		
		if (key_secret != null) {
			tmp.append("key=" + key_secret);
		}
		
		//System.out.println(tmp.toString());
		
		String sign=StringUtil.toMD5(tmp.toString()).toUpperCase();		
		if(StringUtil.isBlank(sign)){ throw new Exception("生成签名内容为空");}		
		return sign;
	}
	
 	private static String mapToXml(Map<String,Object> parameters)throws Exception{  
        StringBuffer sb = new StringBuffer();  
        sb.append("<xml>");          
        Set<String> set = parameters.keySet();
        for(String key : set) {
            Object value = parameters.get(key);
            if ("attach".equalsIgnoreCase(key)||"body".equalsIgnoreCase(key)||"sign".equalsIgnoreCase(key)) {  
                sb.append("<"+key+">"+"<![CDATA["+value+"]]></"+key+">");  
            }else {  
                sb.append("<"+key+">"+value+"</"+key+">");  
            }  
        }
        sb.append("</xml>");  
        return sb.toString();  
    }   
	
 	
    /***
	 * 
	 * @param requestUrl
	 * @param requestMethod
	 * @param data 要发送的数据
	 * @return
	 * 
	 */
    private static String sendHttpsRequest(String strUrl, String data)throws Exception {
	    OutputStream outStream =null; 
	    InputStream iStream =null;    
	    InputStreamReader iStreamReader =null;    
	    BufferedReader bReader =null;  
      try {    
          URL url = new URL(strUrl);    
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();    
            
          conn.setDoOutput(true);    
          conn.setDoInput(true);    
          conn.setUseCaches(false);    
          // 设置请求方式（GET/POST）    
          conn.setRequestMethod("POST");    
          conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");    
          // 当outputStr不为null时向输出流写数据    
          if (null != data) {    
              outStream= conn.getOutputStream();  
              // 注意编码格式    
              outStream.write(data.getBytes("UTF-8"));    
              outStream.close();
              outStream=null;
          }    
          // 从输入流读取返回内容    
          iStream = conn.getInputStream();    
          iStreamReader = new InputStreamReader(iStream, "utf-8");    
          bReader = new BufferedReader(iStreamReader);  
          String str = null;  
          StringBuffer buffer = new StringBuffer();    
          while ((str = bReader.readLine()) != null) {    
              buffer.append(str);    
          }    
          // 释放资源    
          bReader.close();    
          iStreamReader.close();    
          iStream.close();    
          iStream = null;    
          iStreamReader=null;
          bReader=null;
          conn.disconnect();    
          return buffer.toString();    
      } catch (Exception e) {    
          throw e; 
      }finally{
    	  if(outStream!=null){outStream.close();outStream=null;}
    	  if(bReader!=null){bReader.close();bReader=null;}
    	  if(iStreamReader!=null){iStreamReader.close();iStreamReader=null;}
    	  if(iStream!=null){iStream.close();iStream=null;}
      }    
    }
    
    //xml解析    
    public static Map<String, String> parseXml(String strxml) throws Exception {    
    	Map<String,String> map = new HashMap<String,String>();  
    	InputStream in = null;
    	try {
    		in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));   
        	SAXBuilder saxBuilder = new SAXBuilder();
        	Document document = saxBuilder.build(in);
        	//获取根节点
        	Element root = document.getRootElement();
        	if(root.getChildren()!=null) {
        		for(Element e : root.getChildren()) {
        			map.put(e.getName(), e.getValue());
        			//System.out.println(e.getName()+"==="+e.getValue());
        		}
        	}    		
    	}catch(Exception e) {
    		throw e;
    	}finally {
    		if(in!=null) {in.close();}
    	}
    	return map;
    }
    
}
