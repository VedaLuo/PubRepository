package com.lzc.model;

import java.io.Serializable;
import java.util.*;

import com.lzc.ann.MyAnnotation.MyClassAnnotation;
import com.lzc.ann.MyAnnotation.MyFieldAnnotation;
import com.lzc.ann.MyAnnotation.Yts;
import com.lzc.ann.MyAnnotation.Yts.YtsType;

@MyClassAnnotation(desc = "hi, here the class", uri = "hello my uri")
@Yts(classType =YtsType.util)
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@MyFieldAnnotation(desc = "The class field", uri = "com.test.annotation.Test#id")
	private String id;
	
	private String code;
	private String name;
	private int    age;
	private Date   date;
	
    private Map map; //测试用  
    private List list; //测试用  
    private Set set; //测试用  
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public Set getSet() {
		return set;
	}
	public void setSet(Set set) {
		this.set = set;
	}
	
}
