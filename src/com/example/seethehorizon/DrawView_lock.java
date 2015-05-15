/*
 * 带入参数功能已经实现
 * 带入number参数不能小于2，否则可能出现不正常的显示效果
 * 带入number的数字为除去中心圈的其他圈数
 */
package com.example.seethehorizon;


import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Vibrator;
import android.text.format.Time;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class DrawView_lock extends View { 
		
	//定义所需的变量和其他:

	int Htime0,Htime1,Htime2;		/*时间时*/		
	public Time NowTime;	/*获取时间*/		
	public int Stime0,Stime1;		/*时间秒*/		
	public int Mtime0,Mtime1;		/*时间分*/
	public int HAngle;		/*时角度*/		
	public int MAngle;		/*分角度*/
	public int SAngle;		/*秒角度*/
    public Handler mainHandler = null;
	private Vibrator vibrator; 
    Timer time = null;
    TimerTask task = null;
    
//    测试参数
    float x = 0;
    int y = 0;
    float z = 0;
    Boolean dblclick = false;
    
	//<!--初始化代码：
	int number = 3;
	boolean Is24 = false;
	int style = 1;
	int color0 = Color.RED;
	//测试代码结束！！
    
	//设置字:
	public int ViewWidth, ViewHeight; 	/*子空间的宽和高*/
	
	//初始化触屏位置
	int touch_x = 0;
	int touch_y = 0;
	
	public DrawView_lock(Context context) {  
		super(context);
	}

	int first_judge = 0; 		/*用于判断按下还是移动还是抬起,第一次判断*/
	int after_judge = 0; 	/*用于判断按下的区域是否为最小的圆的区域，第二次判断*/
	public long firstClick = 0; 	/*第一次的点击*/
	public long lastClick = 0;		/*第二次点击*/
	public int count = 0;		/*计算点击的次数*/
		
	public void onDraw(final Canvas canvas) {  
		super.onDraw(canvas);
	    

	    //获取View高度
	    ViewHeight = getHeight();
	    //获取View宽度
	    ViewWidth = getWidth();
	    
	    //<!--计算单位长度
	    int unit = 0;
	    int unit_length = 0;
	    unit = number * 2 + 3;
	    unit_length = ViewWidth / (unit * 2);
	    //计算单位长度完成-->
	    
		NowTime = new Time();
		NowTime.setToNow();
		//获取秒
		Stime1 = NowTime.second;
        //获取分
        Mtime1 = NowTime.minute;
        //获取时
        Htime1 = NowTime.hour;
	    
	    //<!--获取时间
		Stime0 = Stime1 + 1;
        SAngle = 6 * Stime0;
        //获取分
        Mtime0 = Mtime1 + 1;
        MAngle = 6 * Mtime0;
        if(Is24 == false)
        {
        	// 如果大于12点，就减去12，否则不减去。（用于绘制小时圆）
	        if(Htime1 > 12)
	        {
	        	Htime2 = Htime1 - 12;
	        }
	        else
	        {
	        	Htime2 = Htime1;
	        }
	        Htime0 = Htime2;
        }
        else
        {
        	Htime0 = Htime1;
        }
        
        //如果用户选择的是24小时的话，就用24小时显示，否则用12小时显示
        if(Is24 == false)
        {
	        HAngle = 30 * Htime0;
        }
        else
        {
        	HAngle = 15 * Htime0;
        }
        //获取时间完成-->
        
		//距离圆心（x,y）的距离，距离屏幕中心(x,y)的距离
		int round_centre_x,round_centre_y,screen_centre_x,screen_centre_y;//c,h,e;
		//初始化圆心距离
	    round_centre_x = round_centre_y = 0; 
	    //触摸点与圆心的距离
	    float distance_x_y = 0;
		//确定屏幕中心
		screen_centre_x = ViewWidth / 2;
		screen_centre_y = ViewHeight / 2;
		//确定圆心(圆心在屏幕中心)
		round_centre_x = screen_centre_x;
		round_centre_y = screen_centre_y;
		
		//<!--计算与圆心的距离
		if(round_centre_x > touch_x && round_centre_y > touch_y)
		{
			distance_x_y =  (int) Math.sqrt ((round_centre_x - touch_x) * (round_centre_x - touch_x)  + (round_centre_y - touch_y) * (round_centre_y - touch_y));	
		}
		else if(round_centre_x > touch_x && touch_y > round_centre_y)
		{
			distance_x_y =  (int) Math.sqrt ((touch_x - round_centre_x) * (touch_x - round_centre_x) + (touch_y - round_centre_y) * (touch_y - round_centre_y));	
		}	
		else if(touch_x > round_centre_x && round_centre_y > touch_y)
		{
			distance_x_y =  (int) Math.sqrt ((touch_x - round_centre_x) * (touch_x - round_centre_x) + (round_centre_y - touch_y) * (round_centre_y - touch_y));	
		}	
		else if(touch_x > round_centre_x && touch_y > round_centre_y)
		{
			distance_x_y =  (int) Math.sqrt ((touch_x - round_centre_x) * (touch_x - round_centre_x) + (touch_y - round_centre_y) * (touch_y - round_centre_y));
		}	
		//计算与圆心的距离完成-->
		
	    //定义画笔
	    Paint paint = new Paint();
	    //是否抗锯齿
	    paint.setAntiAlias(true); 
	    //使用图像抖动处理
	    paint.setDither(true);  
	    //画笔类型（是否为空心等）(充满)
	    paint.setStyle(Paint.Style.FILL);
	    paint.setColor(color0);
	    
	    this.postInvalidateDelayed(1);
	    
	    //下面两行的代码的位置可能还需要重新思考应该放在哪儿
	    if(dblclick){
		    canvas.drawCircle(screen_centre_x, screen_centre_y,unit_length * 3, paint);
	    }
	    
	    //设置画笔宽度
	    paint.setStrokeWidth(unit_length * 2 +3);
	    //什么都没点击的时候
	    if(first_judge == 0){
			if(after_judge == 0){
				show();
				//设置画笔为圆形
			    paint.setStrokeCap(Paint.Cap.ROUND);
				//设置为空心
				paint.setStyle(Paint.Style.STROKE);
				//第一个圆
				paint.setStrokeWidth(1);
				paint.setAlpha(255);
				canvas.drawCircle(screen_centre_x, screen_centre_y,unit_length * 3, paint);
				//第二个圆
				paint.setAlpha(150);
				paint.setStrokeWidth(unit_length * 2);
				RectF Srectf=new RectF(screen_centre_x - (unit_length * (3+1)) ,screen_centre_y - (unit_length * (3+1)) ,screen_centre_x + (unit_length * (3+1)), screen_centre_y + (unit_length * (3+1)));
				canvas.drawArc(Srectf, 270, HAngle, false, paint);
				//第三个圆
				paint.setAlpha(100);
				RectF Mrectf=new RectF(screen_centre_x - (unit_length * (3+3)) ,screen_centre_y - (unit_length * (3+3)) ,screen_centre_x + (unit_length * (3+3)), screen_centre_y + (unit_length * (3+3)));
				canvas.drawArc(Mrectf, 270, MAngle, false, paint);
				//第四个圆
				paint.setAlpha(64);
				RectF Hrectf=new RectF(screen_centre_x - (unit_length * (3+5)) ,screen_centre_y - (unit_length * (3+5)) ,screen_centre_x + (unit_length * (3+5)), screen_centre_y + (unit_length * (3+5)));
				canvas.drawArc(Hrectf, 270, SAngle, false, paint);
			}else if(after_judge == 1){
				if(x <= unit_length * 3){
			       	after_judge = 0;
			       	x = 0;
				}
				else if (x > unit_length * 3 && x <= unit_length * (3 +2)) {
				    phone();
			       	after_judge = 0;
			       	x = 0;
				}
				else if(x > unit_length * (3 +2) && (x <= unit_length * (3 +4))){
					ssm();
			       	after_judge = 0;
			       	x = 0;
				}
				else{
					home();
			       	after_judge = 0;
			       	x = 0;
				}
			}else{
				first_judge = 0;
				after_judge = 0;
			}
	    }
	    /*按下屏幕以后的动作*/
	    else if(first_judge == 1){
	    	//如果离圆心的距离小于最中心的圆的距离的话
			if(distance_x_y <= unit_length * 3){
				after_judge = 1;		//滑动到外圈时,启动相应的事件
				//初始化数据
        		//双击事件，用于启动手电筒

	        	//条件成立的话，给第二个值赋值
	        	 if(count == 1)
	        	{
		        	lastClick = System.currentTimeMillis();
	        		dblclick = dblclick(dblclick);
	        		count = 0;
			        firstClick = 0;
			        lastClick = 0;
	        	}
	        	//条件成立的话，给第一个值赋值
		        else if(count == 0)
		        {
		        	firstClick = System.currentTimeMillis();
		        	count = 1;
		        	timer(true);
		        }
        	}
	    }
	    //在屏幕中滑动的时候
	    else if (first_judge == 2) 
	    {
//	    	for(int yuan = 0; yuan <= number;yuan++){
//		    	paint.setAlpha(128);
//	    		paint.setStrokeWidth(unit_length * 2 +3);
//	    		canvas.drawCircle(screen_centre_x, screen_centre_y,unit_length * (  3 + yuan * 2), paint);
//	    	}
	    	if(after_judge == 1){
		    	if(x <= unit_length * 3){
		    		tv_troch();
				}
				else if (x > unit_length * 3 && x <= unit_length * (3 +2)) {
				    tv_phone();
				}
				else if(x > unit_length * (3 +2) && (x <= unit_length * (3 +4))){
					tv_ssm();
				}
				else{
					tv_home();
				}
				//目前已经将其设置为用户 颜色
		    	paint.setAlpha(128);
				//设置为实心
				paint.setStyle(Paint.Style.FILL);
				x = distance_x_y;
				if(x<unit_length * 3){
				canvas.drawCircle(screen_centre_x, screen_centre_y,unit_length * 3, paint);
				}else if(x < screen_centre_x){
				canvas.drawCircle(screen_centre_x, screen_centre_y,x, paint);
				}else{
					canvas.drawCircle(screen_centre_x, screen_centre_y,screen_centre_x, paint);
				}
			    this.postInvalidateDelayed(1);
			    x = x +1;
			    {
			    	paint.setAlpha(40);
			    	if(y<unit_length * 3){
						y = unit_length * 3;
			    	}else if(y < screen_centre_x){
						canvas.drawCircle(screen_centre_x, screen_centre_y,y,paint);
						y = y + 2;
					}else{
	//					z = 0;
						y = unit_length * 3;
					}
			    }
			    
		    }else{
		    	error();
		    	paint.setARGB(128, 255, 0, 0);
		    	paint.setStyle(Paint.Style.FILL);
				z = distance_x_y;
				if(z<unit_length * 3){
				canvas.drawCircle(screen_centre_x, screen_centre_y,unit_length * 3, paint);
				}else if(z < screen_centre_x){
				canvas.drawCircle(screen_centre_x, screen_centre_y,z, paint);
				}else{
					canvas.drawCircle(screen_centre_x, screen_centre_y,screen_centre_x, paint);
				}
			    this.postInvalidateDelayed(1);
			    z = z +1;
			    {
			    	paint.setAlpha(40);
			    	if(y<unit_length * 3){
						y = unit_length * 3;
			    	}else if(y < screen_centre_x){
						canvas.drawCircle(screen_centre_x, screen_centre_y,y,paint);
						y = y + 2;
					}else{
	//					z = 0;
						y = unit_length * 3;
					}
			    }
			    
		    }
//		    {
//		    	paint.setAlpha(40);
//		    	if(z < screen_centre_x){
//					canvas.drawCircle(screen_centre_x, screen_centre_y,z,paint);
//					if(y - unit_length * 3 < unit_length * 3){
//						
//					}else if((y - unit_length * 3 == unit_length * 3)){
//						int x = 0;
//						if (x == 0){
//						z = y - unit_length * 3;
//						x = 1;
//						}
//					}
//					else{						
//						z = z++;
//					}
//				}else{
//					z = unit_length * 3;
//				}
//		    }
		}
	    

//		//下面的开始是用于显示数据的
//	    //画笔属性
//	    paint.setTextSize(30);
//	    paint.setColor(Color.WHITE);
//	    paint.setStyle(Paint.Style.FILL);
//	    paint.setStrokeWidth(1);
//            
//
//	    canvas.drawText("圆心x轴坐标："+round_centre_x, 0, 40, paint);  
//	    canvas.drawText("圆心y轴坐标："+round_centre_y, 0, 80, paint);
//	    canvas.drawText("触摸点x坐标："+touch_x, 0, 120, paint);  
//	    canvas.drawText("触摸点y坐标："+touch_y, 0, 160, paint);
//	    canvas.drawText("触摸点与圆心的距离："+distance_x_y, 0, 200, paint);
//	    canvas.drawText("按下，移动，抬起："+first_judge, 0, 240, paint);
//	    canvas.drawText("第二次判断："+ after_judge, 0, 280, paint);
//	    canvas.drawText("当前第一次点击时间："+firstClick, 0, 320, paint);
//	    canvas.drawText("当前第二次点击时间："+lastClick, 0, 360, paint);
//	    canvas.drawText("当前c坐标："+count, 0, 400, paint);
//	    //显示数据代码到此为止
	}
	public boolean onTouchEvent(MotionEvent event) {  
   	 	//返回参数使运行以相应的动作
       int action = event.getAction();  
       touch_x = (int) event.getX();  
       touch_y = (int) event.getY();
       switch (action) { 
       // 触摸按下的事件  
       case MotionEvent.ACTION_DOWN:
       {
       		first_judge = 1;
       		Log.v("test", "ACTION_DOWN");
       }
       break;  
       // 触摸移动的事件  
       case MotionEvent.ACTION_MOVE: 
       {	
       		first_judge = 2;	
       		Log.v("test", "ACTION_MOVE");
       }
       break;  
       // 触摸抬起的事件  
       case MotionEvent.ACTION_UP:
       {
	       	touch_x = 0;
	       	touch_y = 0;
	       	first_judge = 0;
	       	Log.v("test", "ACTION_UP");
       }
       break;  
       } 
       return true;   
   }  
	public boolean dblclick(Boolean dblclick)
    {
		boolean  isok = false;
		if(lastClick - firstClick < 500 )
    	{
			if(dblclick){
				troch();
				count = 0;
				firstClick = 0;
	    		lastClick = 0;
	    		timer(false);
	    		isok = false;
				return isok;
			}else{
				troch();
				count = 0;
				firstClick = 0;
	    		lastClick = 0;
	    		timer(false);
	    		isok = true;
				return isok;
			}
    	}
    	else{
    		count = 0;
			firstClick = 0;
    		lastClick = 0;
    		timer(false);
    		return isok;
    	}
		
     }
	//启动相应的事件
    public void home()
    {
    	vibrate();
	    mainHandler.obtainMessage(Main.MSG_LOCK_SUCESS).sendToTarget();
     }
    public void phone()
    {
    	vibrate();
	    mainHandler.obtainMessage(Main.MY_PHONE).sendToTarget();
     }
    public void ssm()
    {
    	vibrate();
	    mainHandler.obtainMessage(Main.MY_SSM).sendToTarget();
     }
    //提醒相应的事件
    public void tv_home()
    {
	    mainHandler.obtainMessage(Main.MSG_LOCK_SUCESS_TV).sendToTarget();
     }
    public void tv_phone()
    {
	    mainHandler.obtainMessage(Main.MY_PHONE_TV).sendToTarget();
     }
    public void tv_ssm()
    {
	    mainHandler.obtainMessage(Main.MY_SSM_TV).sendToTarget();
     }
    public void tv_troch()
    {
    	vibrate();
	    mainHandler.obtainMessage(Main.MY_TROCH_TV).sendToTarget();
     }
    public void troch()
    {
	    mainHandler.obtainMessage(Main.MY_TROCH).sendToTarget();
     }
    public void show(){
    	mainHandler.obtainMessage(Main.SHOW).sendToTarget();
    }
    public void error()
    {
	    mainHandler.obtainMessage(Main.ERROR).sendToTarget();
     }
    public  void setMainHandler(Handler handler){
		mainHandler = handler;
    }
    public void timer(Boolean iscancel){
    if(!iscancel){
    	if(time != null){
	    	time.cancel();
	    	time = null;
    	}
    	if(task != null){
	    	task.cancel();
	    	task = null;
	    }
    	else{
	    	
	    }
    }else{
    	if(time != null){
	    	time.cancel();
	    	time = null;
    	}
	    if(task != null){
	    	task.cancel();
	    	task = null;
	    }
	    time = new Timer();
	    task = new TimerTask() {

		public void run() {
			count = 0;
			firstClick = 0;
    		lastClick = 0;
			}
		};
    	//500毫秒后清除点击事件
    	time.schedule(task, 400);
    	}
    }
    public void vibrate(){
		vibrator = (Vibrator)getContext().getSystemService(Context.VIBRATOR_SERVICE); 
		vibrator.vibrate(10);
    }
}
