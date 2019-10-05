package com.lzc.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.lzc.ann.MyAnnotation.MyClassAnnotation;
import com.lzc.db.TestDB;
import com.lzc.db.TestMongo;
import com.lzc.learn.Elastic;
import com.lzc.learn.QrCode;
import com.lzc.model.StudentDemo;
import com.lzc.model.StudentDemo.Student;
import com.lzc.model.SysArea;
import com.lzc.model.SysAreaDTO;
import com.lzc.model.SysAreaGdDTO;
import com.lzc.model.SysAreaGxDTO;
import com.lzc.model.SysGroupRoleUserDTO;
import com.lzc.model.SysOrg;
import com.lzc.model.SysUser;
import com.lzc.model.User;
import com.lzc.thread.MyThread;
import com.lzc.util.CommonUtil;
import com.lzc.util.JsonUtil;
//import com.lzc.util.JsonUtil;
import com.lzc.util.MongoUtil;
import com.lzc.util.NetUtil;
import com.lzc.util.WeiXinUtil;


public class Main {

	private static SimpleDateFormat dateFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	

	
	public static void main(String[] args) 
	{
	    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss sss");
	    Calendar cal=Calendar.getInstance();
	    String date1=dateFormat.format(cal.getTime());
	    long time=cal.getTimeInMillis();
		System.out.println("Begin[002]--"+time+"--.........."+date1);
		
		int NCPU = Runtime.getRuntime().availableProcessors();
		System.out.println("cpu="+NCPU);
		
		
		
		//testJson();
		//HGUtil.test();
		//readExcel();
		
		//SysArea s=new SysArea();
		//s.setArea_code("0001");
		//s.setArea_name("0002");
		//SysAreaDTO dto=CommonUtil.sourceToTarget(s, SysAreaDTO.class);
		//System.out.println("1========::"+dto.getArea_code());
		//System.out.println("2========::"+dto.getArea_name());
		
		
		//testBase64();
		//testProtobuf(null);

		//downHttpFile();
		
		//try {
		//WeiXinUtil.prePay(appId, openid, mch_id, nonceStr, "PU201909270001", 100, "14.23.150.211", secret, notify_url, wx_pay_url);
		//}catch(Exception e) {
		//	e.printStackTrace();
		//}
		//readBook(null);
		
		//readExcel();
		
		//testHttp();
		
		//TestDB.testQuery();
		//TestMongo.test();
		
		//MyThread.test();
		//makeTree();
		
		makeSpanTable();

		
		Calendar cal2=Calendar.getInstance();
	    String date2=dateFormat.format(cal2.getTime());
	    //System.out.println("End............!"+cal.getTime().getTime());
		System.out.println("End............!"+date2);
		System.out.println("guid::"+UUID.randomUUID().toString());
	}
	
	private static void makeSpanTable() {
		int n=0;
		List<SysGroupRoleUserDTO> list = new ArrayList<>();
		for(int i=0;i<3;i++) {
			for(int j=0;j<4;j++) {
				for(int k=0;k<3;k++) {
					SysGroupRoleUserDTO dto=new SysGroupRoleUserDTO();
					dto.setGroupId("A"+i);
					dto.setGroupName("A组"+i);
					dto.setRoleId("A"+i+"_"+j);
					dto.setRoleName("角色"+j);
					dto.setRealName("用户"+(++n));
					dto.setEmail("email-"+i+"_"+j+"_"+k);
					dto.setTel("tel:"+i+"_"+j+"_"+k);
					list.add(dto);
				}
			}
		}
		
		int spanGroup=1;
		int spanRole=1;
		for(int i=list.size()-1;i>=0;i--) {
			SysGroupRoleUserDTO dto=list.get(i);
			if(i-1<0) {
				dto.setSpanGroup(spanGroup);
				dto.setSpanRole(spanRole);
			}else {
				SysGroupRoleUserDTO dto2=list.get(i-1);
				if(dto.getGroupName()!=null && dto.getGroupName().equals(dto2.getGroupName())) {
					spanGroup++;
					if(dto.getRoleName()!=null && dto.getRoleName().equals(dto2.getRoleName())) {
						spanRole++;
					} else {
						dto.setSpanRole(spanRole);
						spanRole=1;
					}
				}else {
					dto.setSpanGroup(spanGroup);
					dto.setSpanRole(spanRole);
					spanGroup=1;
					spanRole=1;
				}
			}			
		}
		
		for(SysGroupRoleUserDTO dto : list) {
			System.out.println(dto);
		}
		
	}

	private static void makeTree() {
		
		List<Map<String,Object>> listData=new ArrayList<>();
		Map<String,Object> row = new HashMap<>();
		row.put("id", "10");
		row.put("parentId", "");
		row.put("name", "name10");
		listData.add(row);
		
		Map<String,Object> row2 = new HashMap<>();
		row2.put("id", "20");
		row2.put("parentId", "");
		row2.put("name", "name20");
		listData.add(row2);	
		
		Map<String,Object> row11 = new HashMap<>();
		row11.put("id", "11");
		row11.put("parentId", "10");
		row11.put("name", "name20");
		listData.add(row11);				
		
		Map<String,Object> row12 = new HashMap<>();
		row12.put("id", "12");
		row12.put("parentId", "10");
		row12.put("name", "name20");
		listData.add(row12);
		
		Map<String,Object> row111 = new HashMap<>();
		row111.put("id", "111");
		row111.put("parentId", "11");
		row111.put("name", "name111");
		listData.add(row111);			
		
		//没有存在的父级数据
		Map<String,Object> n1 = new HashMap<>();
		n1.put("id", "30");
		n1.put("parentId", "1000");
		n1.put("name", "name30");
		listData.add(n1);
		
		Map<String,Object> n2 = new HashMap<>();
		n2.put("id", "31");
		n2.put("parentId", "30");
		n2.put("name", "name30");
		listData.add(n2);		
		
		
		Map<String,Object> tree = new HashMap<>();
		tree.put("id","");
		tree.put("name","tree");
		tree.put("children", new ArrayList<>());
		loadChild(listData,tree);

		//int i=listData.size();
		//while(i>0) {
		//	i--;
		//	loadChild(listData,listData.get(0));
		//}
		
		
		
				
		for(Map<String,Object> x : (List<Map<String,Object>>)tree.get("children")) {
			System.out.println(x);
		}
		
		System.out.println("left=="+listData.size());
		
		for(Map y : listData) {
			System.out.println(y);
		}
		
		
	
	}
	
	private static void loadChild(List<Map<String,Object>> listData, Map<String,Object> parent) {		
		for(int i=0;i<listData.size();i++) 
		{
			Map<String,Object> row = listData.get(i);					
			String pId=(String)row.get("parentId");			
			if(pId.equals(parent.get("id"))){
				List<Map<String,Object>> children = (List)parent.get("children");		
				if(children == null) { 
					children =  new ArrayList<>();
					parent.put("children",children);
				}
				children.add(row);
				listData.remove(i);
				i--;
				loadChild(listData,row);
			}
		}
	}
	
	
	

    private static void readBook(String enc)
    {    	
    	//http://poi.apache.org/components/spreadsheet/quick-guide.html    	
    	int i=0;
    	String filePath="E:/dowload/语文一下人教16版18印_1191.GTEBOK";
		try {
			InputStream in = new FileInputStream(filePath);
			InputStreamReader sr = null;
			if(enc==null) { sr = new InputStreamReader(in);}
			else          { sr = new InputStreamReader(in, enc);}
			BufferedReader reader = new BufferedReader(sr);
			String line=null;
			while ((line = reader.readLine()) != null) 
			{
				System.out.println(line);
				i++;
				if(i>100)break;
			}
			in.close();
			
		} catch (Exception e) {
			System.out.println("已运行xlRead() : " + e);
		}
    }
	
	private static void testHttp() {
		try {
			var request = HttpRequest.newBuilder().uri(URI.create("https://javastack.cn")).GET().build();
			var client = HttpClient.newHttpClient();
			
			// 同步
			//HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			//System.out.println("=======同步==========================\n"+response.body());
			
			System.out.println("============异步==========================\n");
			
			// 异步
			client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
			.thenApply(HttpResponse::body)
			.thenAccept(body->{
				System.out.println("01xxxxxxxxxxxxxxxxxxxx\n"+body+"\n02xxxxxxxxxxxxxxxxxxxxxxxx\n");
			}).exceptionally(ex -> {
				System.out.println("err============"+ex.getMessage());
	            return null;
	        });
			
			//异步例子
			//https://blog.csdn.net/itguangit/article/details/78624404
			
			
			Thread.sleep(4000);//等等异步结果
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void downHttpFile() {
		String path="e:/download/ps/";
		
		int i=-1;
		while(i<=0){
			String name="rq"+i;
			String url="http://z.syasn.com/rq/"+name;			
			String filePath=path+name+".mp4";
			
			try {				
		        var httpClient = (HttpURLConnection)URI.create(url).toURL().openConnection();
		        httpClient.setRequestMethod("GET");
		        
		        //var statusCode = httpClient.getResponseCode();
		        
		        if (httpClient.getContentType().contains("application/octet-stream")) {		        	
		        	 InputStream stream_in = (InputStream)(httpClient.getContent());
		        	 FileOutputStream stream_out = new FileOutputStream(filePath);						
		        	 stream_in.transferTo(stream_out);
		        	 stream_in.close();
		        	 stream_out.close();
		        	 System.out.println(filePath+"============OK");
		        	 Thread.sleep(4000);//等等异步结果		        	 		        	 
		        }else{
		        	 System.out.println("no=======["+url+"]");
		        }
				
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("err================"+url);
			}			
			i++;
			
			//if(i>0) {break;}
		}

	}
	
	
	private static void testAnn() {
		Class<?> cls = User.class;
		//String table_name = cls.getAnnotation(Table.class).name();
		String uri = cls.getAnnotation(MyClassAnnotation.class).uri();
		System.out.println("=========::"+uri);
	}
	
	private static void testProtobuf(java.io.InputStream input) {
		Student.Builder builder = Student.newBuilder();
		builder.setNumber(1);
		builder.setName("Tom");		
		Student student = builder.build();
		System.out.println("==========\n"+student.toString());
		
		byte[] array = student.toByteArray(); 
		try { 
			Student student1 = Student.parseFrom(array); 
			//Student student1 = Student.parseFrom(input);
			System.out.println(student1.toString()); //在这里打印性别的值 
			System.out.println(student1.getName()); 
		}catch(Exception e) { 
			e.printStackTrace(); 
		}
	}

    public static void readExcel()
    {    	
    	//http://poi.apache.org/components/spreadsheet/quick-guide.html    	
    	String filePath="E:\\temp\\tmp2.xlsx";
    	
    	Object date=null;
		try {
			InputStream is = new FileInputStream(filePath);
			// 构造 XSSFWorkbook 对象，strPath 传入文件路径
			XSSFWorkbook xwb = new XSSFWorkbook(is);
			// 读取第一章表格内容
			XSSFSheet sheet = xwb.getSheetAt(0);
			// 定义 row、cell
			XSSFRow row = null;
			String cell = null;
			// 循环输出表格中的内容			
			for (int i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				if(i==1) {
					date = row.getCell(1);
					break;
				}
				System.out.print(i + "行:");
				for (int j = row.getFirstCellNum(); j < row.getPhysicalNumberOfCells(); j++) {
					// 通过 row.getCell(j).toString() 获取单元格内容，
					if(row.getCell(j)==null) {continue;}
					cell = row.getCell(j).toString();
					System.out.print(j+"["+cell + "]\t");
				}
				System.out.println("");
			}
		} catch (Exception e) {
			System.out.println("已运行xlRead() : " + e);
		}
		
		if(date!=null) {
			XSSFCell cell = (XSSFCell) date;
			String strDate = cell.toString();
			System.out.println("===cell type =="+cell.getCellType()+"::"+cell);
			if(cell.getCellType()==CellType.NUMERIC) {
				String s=String.valueOf(Double.valueOf(cell.toString()).intValue());
				if(s.length()<6) {
					Date StartDate = HSSFDateUtil.getJavaDate(Double.valueOf(strDate));
					System.out.println("====1date is =="+StartDate);
				}else {
					System.out.println("====2date is ==");
				}
			}
			if(cell.getCellType()==CellType.STRING) {
				strDate=strDate.replace("年","").replace("月", "");				
				System.out.println("====3date is =="+strDate);//2019年10月
			}
		}
    }
	
	
	public static void getGPS(String address,String[] ret) {
		String jd=null,wd=null;
		try {
			//String address="高州人民政府";
			String url = "http://api.map.baidu.com/geocoder/v2/?address=" + address + "&output=json&ak=FG7wxr1VUj0k2NwoO3yXzymd&callback=?";
			String json=NetUtil.http(url);
			Map m=JsonUtil.readJson(json);
			if("0".equals(m.get("status").toString())) {
				Map mm=(Map)m.get("result");
				Map gps=(Map)mm.get("location");
				jd=gps.get("lng").toString();
				wd=gps.get("lat").toString();
			}
			System.out.println(address+"====="+jd+","+wd);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		ret[0]=jd;
		ret[1]=wd;	
	}
	
	
	public static void weixin() {
		try {
			//System.out.println("md5=====::"+toMD5("123456"));
			String code="023QnfWB00oo7f2LFcXB0hlhWB0QnfW5";
			String url="https://api.weixin.qq.com/sns/jscode2session?appid=wx76c859bcb489cad6&secret=4980b50d045a921b64d50636b8f97e5b&js_code="+code+"&grant_type=authorization_code";
			String ret=NetUtil.http(url);
			System.out.println("001===============\n"+ret);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testDecode() {
		String session_key="meAjXeLeLz1jMb1sxcsnmg==";
		String iv="ZYmjGCyv0Zi54QddbOnhbA==";
		String encryptedData="L7B6DMO8AAW3POJlvlg8TxVHvRcmRq9XgsV0KeL0Ob5udu/9VfE3szTysxGcHvJAKJ5OoMrGTQr1B1dUz5MAmx3gOWEjllR/McaIYHQZ0gec5oOFNcsDJ+0lzfCXcW2xu0tTd7x/I0l6Ijcgt+ueKqQc6QaWKqlUCu5wEGhEW2e3f25uGANKuyU3/pstxMZvXqRPwLm61AQF4GgeDHRGFecRvfuir90UJXnNNpcBfhLncqtK28kETAwhTfrZoqMD1NOUJ0NQXojOj3oxUyTHE6k4Awqr0phVEl2BykoJGAA8yB7LvPP7UqJnTkjOMiIU4x4farZ3T1l25wkVgwu00r8no7CoK5D/m8m+zJelcij3ZXL8kmM1eCSUTq3A9Y8q4aCdipxhsc07kf1fEdI7EPKHra+0tMq5G42KZg6NBd93hROSYS5Lj2rXhK+cJ39j";
		
		try {
			byte[] data = Base64.decodeBase64(encryptedData);  
			byte[] iv_spec = Base64.decodeBase64(iv);  
			byte[] key = Base64.decodeBase64(session_key);  
			
			String str=decodeData(key, iv_spec, data);
			System.out.println("=============\n"+str);
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

	public static String toMD5(String s) throws Exception {
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
	}
	
	

	private static void testBase64()
	{
		String str="二进制数据编码为BASE64字符串 ";
		String str64=new String(Base64.encodeBase64(str.getBytes()));
		String str2=new String(Base64.decodeBase64(str64));
		
		System.out.println(str);
		System.out.println(str64);
		System.out.println(str2);
	}
	
	private static void testJson()
	{
		//String objParam="{\"menuId\":\"641\",\"currentPage\":1}";
		String listParam="[{\"name\":\"是否标准化\",\"column\":\"field_1\",\"type\":\"string\"},{\"name\":\"指标行编码\",\"column\":\"field_2\",\"type\":\"string\"}]";
		User user=new User();
		user.setId("1");
		user.setName("lzc");
		user.setCode("001");
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			
	        String json=mapper.writeValueAsString(user); //将对象转换成json  
	        System.out.println("01====\n"+json);  
	         
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("user", user);
	        json=mapper.writeValueAsString(map); //将对象转换成json  
	        System.out.println("02====\n"+json); 	        
	        
	        
	        System.out.println();
			
			//Map m = mapper.readValue(objParam, Map.class);
	    	//System.out.println(m.get("name"));
	        
	        List list = mapper.readValue(listParam, ArrayList.class);
			for(Object obj : list) {
		        Map m=(Map)obj;
		        System.out.println(m.get("name"));
			}
	
			
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	public static String GetNewOrderNo(int order_type) 
	{
		String prefix = "P";//前缀  订单类型1：P固定；F自由；其他；
		if(order_type==0)//固定
		{
			prefix = "P";
		}else if(order_type==1)//自由
		{
			prefix = "F";
		}else
		{
			return "";
		}
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String serial_number = dateFormat.format(new java.util.Date());//日期8,格式yyyyMMddHHmmssSSS
	
		long temp=Math.round(Math.random()*89999+10000);
		String increase_number = temp+"";//增加号
		String order_no = prefix + serial_number + increase_number;
		return order_no;
	}

	
	private static java.util.Date getTestDate()
	{
		Random rand = new Random();
		//生成区间 [64, 128] 中随机值的代码为：
		//rand.nextInt(65) + 64;
		
		int year=rand.nextInt(10)+2010;//2010-2020
		int month=rand.nextInt(11);
		int day=rand.nextInt(28);

		Calendar cal=Calendar.getInstance();
		cal.set(year, month, day, 0, 0 ,0);
		
		return cal.getTime();
	}

	
	private static void testImg(String text) {
		int width=100;
		int height=30;
		//ByteArrayOutputStream jpgStream = new ByteArrayOutputStream();
		Random r=new Random();
		
		try 
		{
	        BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	        Graphics2D g=(Graphics2D)image.getGraphics();
	        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	        g.setColor(Color.white);
	        g.fillRect(0,0,width,height);
	        
	        for(int i=1;i<10;i++) {
	        	g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
	        	g.drawLine(i*10, 0, i*10, height);	        	
	        }
	        
	        for(int j=1;j<3;j++) {
	        	g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
	        	int y=r.nextInt(30);
	        	g.drawLine(0, y, width, y);	  
	        }
	        
	        char[] word=text.toCharArray();
	        for(int i=0;i<word.length;i++) {
	        	g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
		        //g.setFont( new Font ( "隶书" , Font.PLAIN , 25));	  
		        g.setFont( new Font ( "隶书" , Font.PLAIN , r.nextInt(8)+18));	  
	        	g.drawString(String.valueOf(word[i]),15+i*12,20);
	        }
	        
	        g.dispose();
	        ImageIO.write(image, "jpg", new File("e://temp//t.jpg"));
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

	}


	
	public static List<String> readFileByLine(String filePath,boolean notBlank,String enc)
	{
		List<String> list=new ArrayList<String>();
		File file = new File(filePath);
		if(file.exists())
		{
			try
			{
				InputStream in = new FileInputStream(file);
				InputStreamReader sr = null;
				if(enc==null) { sr = new InputStreamReader(in);}
				else          { sr = new InputStreamReader(in, enc);}
				BufferedReader reader = new BufferedReader(sr);
				String line=null;
				int i=0;
				while ((line = reader.readLine()) != null) 
				{
					if(notBlank && line.trim().length()==0) {continue; }
					list.add(line);
					
					i++;
					if(i%500000==0)
					{
						System.out.println(i);
						//System.out.println(line);
						long free=getMemInfo();
						if(free<2000){break;}
					}
				}
				in.close();
				System.out.println("total:"+i);
			}
			catch(Exception e)
			{
				list.clear();
				e.printStackTrace();
			}
		}
		return list;
	}	
	
	public static void moveFile(final String filePathSrc,final String filePathDes) {
		File file = new File(filePathSrc);
		if(file.exists()) {			
			file.renameTo(new File(filePathDes));
		}
	}
	
    public static long getMemInfo()
    {
        OperatingSystemMXBean mem = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        //System.out.println("Total RAM：" + mem.getTotalPhysicalMemorySize() / 1024 / 1024 + "MB");
        long free=mem.getFreePhysicalMemorySize() / 1024 / 1024;
        System.out.println("Available　RAM：" + free + "MB");
        return free;
    }
}
