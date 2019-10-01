package com.lzc.db;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.lzc.util.MongoUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class TestMongo {

	public static void test() {
		MongoClient mongoClient = null;
		mongoClient = new MongoClient( "192.168.3.80" , 27017 );
		MongoDatabase db=mongoClient.getDatabase("myapp");
		
		listCollect(db,"imp_test2_data");

		mongoClient.close();
	}
	
	public static void listCollect(MongoDatabase db,String name) {
		MongoCollection<Document> collection = db.getCollection(name);
		System.out.println("size=========::"+collection.countDocuments());
		FindIterable<Document> list=null;
		list = collection.find();
		MongoCursor<Document> cursor = list.iterator();
		int ii=1;
		while(cursor.hasNext()) {
			Document doc=cursor.next();
			System.out.println(ii+"=========::"+doc.toJson());
			ii++;
			if(ii>1000) {break;}
		}
	}
	
	public static void testIndex()
	{
		MongoClient mongoClient = null;
		mongoClient = new MongoClient( "192.168.3.64" , 27017 );
		MongoDatabase db=mongoClient.getDatabase("myapp");
		MongoCollection<Document> collection = db.getCollection("sys_user");
		ListIndexesIterable<Document> listIndex = collection.listIndexes();  
		MongoCursor<Document> cursorIndex = listIndex.iterator();  
		while (cursorIndex.hasNext()) {  
			Document index = cursorIndex.next();  
			System.out.println(index.toJson());
		}  
		cursorIndex.close();
		
		mongoClient.close();
	}
	

	
	public static void testMongo() {
		MongoClient mongoClient = null;
		mongoClient = new MongoClient( "192.168.3.64" , 27017 );
		MongoDatabase db=mongoClient.getDatabase("myapp");
		//MongoCollection<Document> collection = db.getCollection("sys_user");
		MongoCollection<Document> collection = db.getCollection("sys_org");
		System.out.println("size=========::"+collection.countDocuments());
		
		//List<SysUser> list=findList(db,"sys_user",Filters.eq("org_id", "ffae701e-58f8-43a3-970d-4addb8497c57"),null,SysUser.class);
		
		//System.out.println("xxxx=========::"+list.size());
		
		/***/
		FindIterable<Document> list=null;
		//Pattern pattern = Pattern.compile("^.*" + "_13671" + ".*$"); //不区分大小写 like %_13671%		
		//list = collection.find(Filters.regex("login_name", pattern));
		list = collection.find();
		MongoCursor<Document> cursor = list.iterator();
		int i=0;
		while(cursor.hasNext()) {
			Document doc=cursor.next();
			System.out.println(i+"=========::"+doc.toJson());
			System.out.println(i+"xxxxxxxxx::"+doc.get("_id"));
			//doc.remove("_id");
			//System.out.println(i+"2========::"+doc.toJson());
			//SysOrg obj=MongoUtil.toObj(doc, SysOrg.class);			
					
			i++;
			if(i>10) {break;}
		}
		System.out.println("=========::"+i);		
		/****/

		
		mongoClient.close();
	}
	
	public static void testMongo2(Bson filters) {
		MongoClient mongoClient = null;
		mongoClient = new MongoClient( "192.168.3.64" , 27017 );
		MongoDatabase db=mongoClient.getDatabase("mytest");
		MongoCollection<Document> collection = db.getCollection("temp");
		
		long n=0;
		//collection.updateMany(Filters.eq("count",20), "{}");
		//n=collection.updateMany(Filters.eq("count",28), new Document("$set", new Document("count",26))).getModifiedCount() ;
		
		
		FindIterable<Document> list=null;
		//list = collection.find(Filters.eq("count",20));
		list = collection.find();
		MongoCursor<Document> cursor = list.iterator();
		int i=0;
		while(cursor.hasNext()) {
			Document doc=cursor.next();
			System.out.println(i+"=========::"+doc.toJson());
			System.out.println(i+"xxxxxxxxx::"+doc.get("_id"));
			//doc.remove("_id");
			//System.out.println(i+"2========::"+doc.toJson());
			//SysOrg obj=MongoUtil.toObj(doc, SysOrg.class);			
					
			i++;
		}
		System.out.println(n+"=========::"+i);		
		/****/
		
		
		 System.out.println("001xxxxxxxxxxxxxxxxxxxxxxxxxxxx::"+max(db,"temp","count",null));
		
/////////////////////////////////////////////////////////////////////////////////
	   /***
       BasicDBObject match = new BasicDBObject("$match", new BasicDBObject("type", "mdb")); 
       BasicDBObject project = new BasicDBObject("$project", new BasicDBObject("type", 1).append("count", 1).append("_id", 0)); 
       BasicDBObject groupFields = new BasicDBObject("_id", null);
       groupFields.append("out_name", new BasicDBObject( "$sum", "$count"));
       groupFields.append("out_name2", new BasicDBObject( "$max", "$count"));
       BasicDBObject group = new BasicDBObject("$group", groupFields); 
       
       
       List<BasicDBObject> listParam=new ArrayList<>();
       //listParam.add(match);
       //listParam.add(project);
       listParam.add(group);
      
       
       AggregateIterable<Document> listRes = collection.aggregate(listParam);
		MongoCursor<Document> cursor2 = listRes.iterator();
		while(cursor2.hasNext()) {
			Document doc=cursor2.next();
			System.out.println(i+"=========::"+doc.toJson());
			System.out.println(i+"+++++++++::"+doc.get("out_name2").getClass().getName());
		}
		****/
/////////////////////////////////////////////////////////////////////////////////		
		mongoClient.close();
	}
	
	
	public static <T> List<T> findList(MongoDatabase db, String collectName,Bson filters, Bson sorts, Class<T> cls){
		int MAX=8000;
		
		List<T> listRes=new ArrayList<T>();
		MongoCollection<Document> collection = db.getCollection(collectName);
		FindIterable<Document> list = collection.find(filters);
		if(sorts!=null) { list = list.sort(sorts); }
		MongoCursor<Document> cursor = list.iterator();
		int i=0;
		while(cursor.hasNext()) {
			Document doc=cursor.next();			
			listRes.add(MongoUtil.toObj(doc, cls));
			i++;
			if(i>=MAX) {break;}
		}
		return listRes;
	}
	
	public static long max(MongoDatabase db, String collectName, String fieldName,BasicDBObject match) {
	       Integer ret = null;
	       BasicDBObject groupFields = new BasicDBObject("_id", null);
	       groupFields.append("max_name", new BasicDBObject( "$max", "$"+fieldName));
	       BasicDBObject group = new BasicDBObject("$group", groupFields); 
	       
	       List<BasicDBObject> listParam=new ArrayList<>();
	       if(match!=null) {listParam.add(match);}
	       listParam.add(group);
	       
	       MongoCollection<Document> collection = db.getCollection(collectName);
	       AggregateIterable<Document> listRes = collection.aggregate(listParam);
	       MongoCursor<Document> cursor = listRes.iterator();
	       if(cursor.hasNext()) {
				Document doc=cursor.next();
				ret  = doc.getInteger("max_name");
	       }
	       
	       return ret;
	}
	
	public static void testLookup(MongoDatabase db) {		
		BasicDBObject match = new BasicDBObject("$match", new BasicDBObject("item_code", "B1803")); 
		BasicDBObject lookup = new BasicDBObject();
		lookup.append("from","sys_class");
		lookup.append("localField","class_id");
		lookup.append("foreignField","class_id");
		lookup.append("as","listClass");
		
		BasicDBObject lookup2 = new BasicDBObject();
		lookup2.append("from","item_type");
		lookup2.append("localField","type_id");
		lookup2.append("foreignField","type_id");
		lookup2.append("as","listType");
		
		BasicDBObject join = new BasicDBObject("$lookup", lookup);
		BasicDBObject join2= new BasicDBObject("$lookup", lookup2);
		
		System.out.println("join1::"+join.toJson());
		System.out.println("join2::"+join2.toJson());
		
		List<BasicDBObject> listParam=new ArrayList<>();
		listParam.add(match);
		listParam.add(join);
		listParam.add(join2);
		
		MongoCollection<Document> collection = db.getCollection("org_item");
		AggregateIterable<Document> listRes = collection.aggregate(listParam);
		
		
		MongoCursor<Document> cursor = listRes.iterator();
		while(cursor.hasNext()) {
		Document doc=cursor.next();
		System.out.println("=========::"+doc.toJson());
		}
	}
	
	public static void testAggregate(MongoDatabase db) {		
		BasicDBObject match = new BasicDBObject("$match", new BasicDBObject("item_code", "B1803")); 
		BasicDBObject project = new BasicDBObject("$project", new BasicDBObject("type", 1)
				.append("count", 1).append("_id", 0)); 
		
		
		BasicDBObject groupFields = new BasicDBObject("_id", null);
		groupFields.append("out_name", new BasicDBObject( "$sum", "$count"));
		groupFields.append("out_name2", new BasicDBObject( "$max", "$count"));
		BasicDBObject group = new BasicDBObject("$group", groupFields);
		
		
		BasicDBObject lookup = new BasicDBObject();
		lookup.append("from","sys_class");
		lookup.append("localField","class_id");
		lookup.append("foreignField","class_id");
		lookup.append("as","listClass");
		
		BasicDBObject lookup2 = new BasicDBObject();
		lookup2.append("from","item_type");
		lookup2.append("localField","type_id");
		lookup2.append("foreignField","type_id");
		lookup2.append("as","listType");
		
		BasicDBObject join = new BasicDBObject("$lookup", lookup);
		BasicDBObject join2= new BasicDBObject("$lookup", lookup2);
		
		System.out.println("join::"+join.toJson());
		
		List<BasicDBObject> listParam=new ArrayList<>();
		listParam.add(match);
		//listParam.add(project);
		//listParam.add(group);
		listParam.add(join);
		listParam.add(join2);
		
		MongoCollection<Document> collection = db.getCollection("org_item");
		AggregateIterable<Document> listRes = collection.aggregate(listParam);
		
		
		MongoCursor<Document> cursor = listRes.iterator();
		while(cursor.hasNext()) {
		Document doc=cursor.next();
		System.out.println("=========::"+doc.toJson());
		}
	}
	
	
}
