package com.lzc.thread;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class MyAct {

	private int res=0;
	
	private ThreadLocal<Long> threadLocal = new ThreadLocal<>();
	
	private ConcurrentHashMap map=new ConcurrentHashMap();
	
	CountDownLatch  v;
	
	//全局单例模式
	private MyAct(){
		
	}
	
	public static MyAct instance(){
		return new MyAct();
	}
	
	public void act1(){
		res++;
	}
	
	public synchronized void act2(){
		res++;
	}
	
	public void act3(){
		Long me=null;
		if(threadLocal.get()==null){
			me=new Long(100);
			threadLocal.set(me);
		}else{
			me=threadLocal.get();
		}
	}
	
	public void out(String n){
		System.out.println("The end:"+res+"["+n+"]");
	}

	@Override
	protected void finalize() throws Throwable {
		threadLocal.remove();
	}
	
	
}
