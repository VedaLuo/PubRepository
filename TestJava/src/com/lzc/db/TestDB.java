package com.lzc.db;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;

import com.lzc.model.Row;
import com.lzc.model.SysArea;
import com.lzc.util.NetUtil;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class TestDB 
{
	
	public static void moveData()
	{
		Connection con=DB.getMySqlDB("myapp");
		String sql=null;
		int ii=0;
		
		try
		{
			sql="select * from sys_org order by org_code limit 3";
			ResultSet rs=con.createStatement().executeQuery(sql);			
			while(rs.next()) {
				String id=rs.getString("org_id");
				String code=rs.getString("org_code");
				//String name=rs.getString("org_name");
				//String province=rs.getString("province");
				//String city=rs.getString("city");

				System.out.println(code+"::"+id+"::"+ii);	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
			try{if(con!=null)con.close();}catch(Exception e){e.printStackTrace();}
		}
	}
	

	
	
	public static void makeBigData()
	{
		//Connection con1=DB.getMySqlDB("tkf_zhxy");
		Connection con2=DB.getMySqlDB("veda_app");
		String sql=null;
		try
		{
			String ref_org_id="1c373087-0ff6-4eed-aaf5-519fae31a98b";			
			//sql=" select * from sys_user where org_id='"+ref_org_id+"' ";
			//ResultSet rsMan=con2.createStatement().executeQuery(sql);
			
			sql=" select * from sys_org where short_name='高州三中' order by org_code ";
			ResultSet rs=con2.createStatement().executeQuery(sql);
			
			int i=0;
			while(rs.next()) {
				String org_id=rs.getString("org_id");
				String org_code=rs.getString("org_code");
				if(ref_org_id.equals(org_id)) {continue;}
				System.out.println((++i)+"======="+org_id+"::"+org_code);
				if(i<=1400) {continue;}
				
				sql=" insert into sys_user(user_id,org_id,user_type,login_name,user_name,is_valid,create_datetime,user_tel) "
				   +" select uuid() as user_id,'"+org_id+"' as org_id,user_type,CONCAT('"+org_code+"_',u.login_name) as login_name,user_name,is_valid,now() as create_datetime,user_tel from sys_tmp u ";	
				con2.createStatement().executeUpdate(sql);
				
				//sql = " insert into sys_user_auth(user_id,login_pass) "
				//	+ " select user_id,'E10ADC3949BA59ABBE56E057F20F883E' as pass from sys_user where org_id='"+org_id+"' ";
				//con2.createStatement().executeUpdate(sql);
				
				/****
				while(rsMan.next()) 
				{
					String login_name=rsMan.getString("login_name");
					String user_name=rsMan.getString("user_name");
					String user_tel=rsMan.getString("user_tel");
					if(user_tel==null) {user_tel="";}
					
					login_name=org_code+"_"+login_name;
					//System.out.println((++i)+"======="+login_name+"::"+user_name +"::"+user_tel);
					
					String id=java.util.UUID.randomUUID().toString();				
					sql = " insert into sys_user(user_id,org_id,user_type,login_name,user_name,is_valid,create_datetime,user_tel) values ('"+id+"','"+org_id+"','USER','"+login_name+"','"+user_name+"','Y',now(),'"+user_tel+"')";
					con2.createStatement().executeUpdate(sql);
					sql = " insert into sys_user_auth(user_id,login_pass) values ('"+id+"','E10ADC3949BA59ABBE56E057F20F883E') ";
					con2.createStatement().executeUpdate(sql);
				}
				rsMan.first();
				***/
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
			//try{if(con1!=null)con1.close();}catch(Exception e){e.printStackTrace();}
			try{if(con2!=null)con2.close();}catch(Exception e){e.printStackTrace();}
		}
	}
	
	
	public static void setupOrg() {
		Connection con=DB.getMySqlDB("myapp");
		try
		{
			String org_name="";
			String org_manage_type="";
			String short_name="";
			String eng_name="";
			String licence_code="";
			String licence_issuer="";
			String official_man_name="";
			String official_man_id="";
			String contact_man="";
			String contact_tel="";
			String contact_email="";
			String post_code="";
			String org_sort="";
			String address="";
			
			//'868fc344-b708-4919-bacc-611820cb3baa'
			//'101b5f28-61ef-405f-b1e3-a70bce25f18f'
			
			String sql="select * from sys_org where org_id='101b5f28-61ef-405f-b1e3-a70bce25f18f'";
			ResultSet rs1=con.createStatement().executeQuery(sql);
			if(rs1.next()) {
				org_name=rs1.getString("org_name");
				org_manage_type=rs1.getString("org_manage_type");
				short_name=rs1.getString("short_name");
				eng_name=rs1.getString("eng_name");
				licence_code=rs1.getString("licence_code");
				licence_issuer=rs1.getString("licence_issuer");
				official_man_name=rs1.getString("official_man_name");
				official_man_id=rs1.getString("official_man_id");
				contact_man=rs1.getString("contact_man");
				contact_tel=rs1.getString("contact_tel");
				contact_email=rs1.getString("contact_email");
				post_code=rs1.getString("post_code");
				org_sort=rs1.getString("org_sort");
				address=rs1.getString("address");
			}
			

		    sql="select * from sys_area";
			ResultSet rs=con.createStatement().executeQuery(sql);
			int i=0;
			int code=3175;//00000028
			int len=8;
			while(rs.next()) {
				String area_code=rs.getString("area_code");
				String area_name=rs.getString("area_name");
				String province=rs.getString("province");
				String city=rs.getString("city");
				String post_no=rs.getString("post_no");
				String jd=rs.getString("gps_jd");
				String wd=rs.getString("gps_wd");
				if(city==null) {continue;}
				if(post_no==null) {continue;}
				
				System.out.println((++i)+"area_name========="+area_name);
				System.out.println("post_no========="+post_no);
				
				Long tmp=Long.valueOf(code);
				String s="";
				for(int ii=0;ii<len;ii++) {s=s+"0";}
				s=s+tmp;			
				s=s.substring(s.length()-len);
				
				String prefix=province + city + (city.equals(area_name)?"":area_name);
				String id=java.util.UUID.randomUUID().toString();	
				String org_code=s;
				String name= prefix + org_name;
				post_code=post_no;
				
			
				sql="insert into sys_org(org_id,parent_id,org_code,org_name,"
				   +"org_manage_type,short_name,eng_name,licence_code,licence_issuer,official_man_name,official_man_id,contact_man,contact_tel,contact_email,post_code,org_sort,address,"  
				   +"create_datetime,is_verify,is_valid,level_code,gps_jd,gps_wd,area_id) values ("
				   + "'"+id+"','0','"+org_code+"','"+name+"',"
				   +"?,?,?,?,?,?,?,?,?,?,?,?,?,"
				   +"now(),'Y','Y','"+id+"',"+jd+","+wd+",'"+area_code+"')";
				
				PreparedStatement pre=con.prepareStatement(sql);
				pre.setObject(1, org_manage_type);
				pre.setObject(2, (city + (city.equals(area_name)?"":area_name))+short_name);
				pre.setObject(3, eng_name);
				pre.setObject(4, licence_code);
				pre.setObject(5, prefix+licence_issuer);
				pre.setObject(6, official_man_name);
				pre.setObject(7, official_man_id);
				pre.setObject(8, contact_man);
				pre.setObject(9, contact_tel);
				pre.setObject(10, contact_email);
				pre.setObject(11, post_code);
				pre.setObject(12, org_sort);	
				pre.setObject(13, prefix+address);
				pre.executeUpdate();
				
				code++;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
			try{if(con!=null)con.close();}catch(Exception e){e.printStackTrace();}
		}		
	}
	
	
	public static void testQuery()
	{
		Connection con=DB.getMySqlDB("myapp");
		try
		{
			String sql="select now() as f1,curdate() as f2,curtime() as f3";
			ResultSet rs=con.createStatement().executeQuery(sql);
			if(rs.next()) {
				System.out.println("f1========="+rs.getString("f1"));
				System.out.println("f2========="+rs.getString("f2"));
				System.out.println("f3========="+rs.getString("f3"));
			}
			
			/***
			String sql="select * from order_food_sync";
			ResultSet rs=con.createStatement().executeQuery(sql);
			ResultSetMetaData md=rs.getMetaData();
			for(int i=1;i<=md.getColumnCount();i++)
			{
				System.out.println("private String "+md.getColumnName(i)+";");
			}
			***/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
			try{if(con!=null)con.close();}catch(Exception e){e.printStackTrace();}
		}
	}
	
	public static void moveToMongodb2() {
		Vector<String> vid=new Vector<String>();
		Vector<String> vcode=new Vector<String>();
		List<Map<String,Object>> listData=new ArrayList<Map<String,Object>>();
		
		String sql=null;
		Connection con=DB.getMySqlDB("myapp");
		try {
			sql="select org_id,org_code from sys_org order by org_code";
			ResultSet rs1=con.createStatement().executeQuery(sql);
			while(rs1.next()) {
				vid.add(rs1.getString("org_id"));
				vcode.add(rs1.getString("org_code"));
			}
			rs1.close();
			System.out.println("============"+vid.size());
			sql="select * from veda_app2.sys_user where org_id='a8b7ba97-9a20-49b1-8412-2a204dd8ff4b'";			
			ResultSet rs=con.createStatement().executeQuery(sql);
			ResultSetMetaData md=rs.getMetaData();
			int z=0;
			while(rs.next()) {
				Map<String,Object> map=new HashMap<String,Object>();
				for(int i=1;i<=md.getColumnCount();i++)
				{					
					//System.out.println("private String "+md.getColumnName(i)+";");
					String name=md.getColumnName(i);
					Object value=rs.getObject(name);
					map.put(name, value);
				}
				listData.add(map);
			}			
			rs.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
			try{if(con!=null)con.close();}catch(Exception e){e.printStackTrace();}
		}
		
		
		MongoClient mongoClient = null;
		ClientSession session = null;		
		try{
			//mongoClient = new MongoClient( "192.168.1.94" , 27017 );
			
			//集群方式
			mongoClient = new MongoClient(Arrays.asList(
							new ServerAddress("192.168.3.61", 27017),
							new ServerAddress("192.168.3.62", 27017),
							new ServerAddress("192.168.3.63", 27017)));
			
			System.out.println("Connect to database successfully");	
			
			MongoDatabase mdb = mongoClient.getDatabase("myapp"); 
			MongoCollection<Document> collection = mdb.getCollection("sys_user");
			
			String id="user_id";
			int z=0;
			for(Map<String,Object> map : listData) {
				for(int i=0;i<vid.size();i++) {
					String org_id=vid.get(i);
					String org_code=vcode.get(i);
					String id_value=UUID.randomUUID().toString();
					Document doc = new Document(id, id_value);
					
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						String name=entry.getKey();						
						if(name.equals(id)) {continue;}
						Object value=entry.getValue();
						if(name.equals("org_id")) { value=org_id;}
						if(name.equals("login_name")) { value= org_code + "_" + value;  }
						doc.append(name, value);
					}
					
					collection.insertOne(doc);
				}
				System.out.println("============"+z);
				z++;
			}
			
		}catch(Exception e) {
			if(session!=null) {session.abortTransaction();}
			e.printStackTrace();
		}finally {
			if(mongoClient!=null) {mongoClient.close();}
			try{if(con!=null)con.close();}catch(Exception e){e.printStackTrace();}
		}
		
		vid.clear();
		vcode.clear();
		listData.clear();
	}
	
	
	public static void moveToMongodb(String table,String id_name) {	
		String sql=null;
		Connection con=DB.getMySqlDB("myapp");
		
		MongoClient mongoClient = null;
		ClientSession session = null;
		try{
			mongoClient = new MongoClient( "192.168.3.64" , 27017 );
			
			//集群方式
			//mongoClient = new MongoClient(Arrays.asList(
			//				new ServerAddress("192.168.3.61", 27017),
			//				new ServerAddress("192.168.3.62", 27017),
			//				new ServerAddress("192.168.3.63", 27017)));
			
			System.out.println("Connect to database successfully");	
			
			//session = mongoClient.startSession();
			//session.startTransaction();
			
			MongoDatabase mdb = mongoClient.getDatabase("myapp"); 
			MongoCollection<Document> collection = mdb.getCollection(table);
			
			sql=" select * from "+table + " where user_id='19534e4a-27c2-4aa3-8362-5ef644e13694' ";
			ResultSet rs=con.createStatement().executeQuery(sql);
			ResultSetMetaData md=rs.getMetaData();
			int row=0;
			while(rs.next()) {
				Document doc = new Document(id_name, rs.getString(id_name));
				for(int i=1;i<=md.getColumnCount();i++)
				{					
					//System.out.println("private String "+md.getColumnName(i)+";");
					String name=md.getColumnName(i);
					if(name.equals(id_name)) {continue;}
					Object value=rs.getObject(name);
					doc.append(name, value);
				}
				collection.insertOne(doc);
				row++;
			}
			
			System.out.println("======="+table+"::"+row);
		}catch(Exception e) {
			if(session!=null) {session.abortTransaction();}
			e.printStackTrace();
		}finally {
			if(mongoClient!=null) {mongoClient.close();}
			try{if(con!=null)con.close();}catch(Exception e){e.printStackTrace();}
		}

	}
	

    private static List<Row> readExcel()
    {    	
    	//http://poi.apache.org/components/spreadsheet/quick-guide.html    	
    	String filePath="E:\\temp\\公司分类.xlsx";
    	
    	List<Row> list=new ArrayList<Row>();
    	
        String values = null;
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
				//System.out.print(i + "行:");
				Row r= new Row();
				for (int j = row.getFirstCellNum(); j < row.getPhysicalNumberOfCells(); j++) {
					// 通过 row.getCell(j).toString() 获取单元格内容，
					cell = row.getCell(j).toString();
					//System.out.print(j+"["+cell + "]\t");
					if(j==0) {r.f1=cell;}
					if(j==1) {r.f2=cell;}
					if(j==2) {r.f3=cell;}
					if(j==3) {r.f4=cell;}
					if(j==4) {r.f5=cell;}
					if(j==5) {r.f6=cell;}
					r.n++;
				}
				//System.out.println("");
				list.add(r);
			}
		} catch (Exception e) {
			System.out.println("已运行xlRead() : " + e);
		}
		
		return list;
    }
}
