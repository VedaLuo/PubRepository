package com.lzc.thread;

public class MyThread {

	public static void test(){
		
		MyAct m=MyAct.instance();
		
		MyThread myThread=new MyThread();
		//启动10条线程
		for(int i=1;i<11;i++)
		{
			new Thread(myThread.new CellRun("N"+i,m)).start();
		}
	}
	
	private class CellRun implements Runnable{
		
		private String no;
		private MyAct ma;
		
		public CellRun(String no,MyAct ma){
			this.no=no;
			this.ma=ma;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{Thread.sleep(1000);}catch(Exception e){}
			//试试  act1换act2
			ma.act1();     //最后结果不一定为10
			//ma.act2();   //最后结果肯定为10
			ma.out(no);
		}
		
	}
}
