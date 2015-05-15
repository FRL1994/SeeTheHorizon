/*
 * 这是管理两个半个屏幕activity的主activity
 */
package com.example.seethehorizon;


import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {


	private Time NowTime;
    private Camera camera;
    private boolean isopent = false;
	//判断锁(判断是否进入静默模式)
	private boolean isUp = false;
	public static int MSG_LOCK_SUCESS = 1;
	public static int MY_PHONE = 2;
	public static int MY_SSM = 3;
	public static int MY_TROCH = 4;
	public static int MSG_LOCK_SUCESS_TV = 5;
	public static int MY_PHONE_TV = 6;
	public static int MY_SSM_TV = 7;
	public static final int MY_TROCH_TV = 8;
	public static final int SHOW = 9;
	public static final int ERROR = 10;
	//定义一个time计时器
	Timer timer=new Timer();
	
    private String mYear;  
    private String mMonth;  
    private String mDay;  
    private String mWay;  
    private String showDay;
    private String Mtime;
    private int show = 1;
    private String showtext_one;
    private String showtext_two;
	boolean Is24 = false;
	boolean or = false;
	int style = 1;
    Timer time = null;
    TimerTask task = null;
	int Stime1;		/*时间秒*/		
	int Mtime1;		/*时间分*/
	int Htime0,Htime1,Htime2;		/*时间时*/		
	int color0 = Color.argb(255, 255, 255, 255);
	
	@SuppressLint("CutPasteId")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final LinearLayout main = (LinearLayout)findViewById(R.id.main);//主界面控件
		final LinearLayout linearlayout_datatime = (LinearLayout)findViewById(R.id.datatime);//时间控件
		final LinearLayout linearlayout_lock = (LinearLayout)findViewById(R.id.lock);//锁屏控件
		final LinearLayout datatime2 = (LinearLayout)findViewById(R.id.datatime2);//锁屏控件
		final TextView time_tv = (TextView)findViewById(R.id.time);
		final TextView day = (TextView)findViewById(R.id.day);
		DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        final int width = metric.widthPixels;     // 屏幕宽度（像素）
        final int height = metric.heightPixels;   // 屏幕高度（像素）
        
        init();
        
        final int linearlayout_time_height = height - width - 80;  //计算出各个控件的高度和宽度(80为状态栏的高度)
		linearlayout_datatime.setLayoutParams(new LinearLayout.LayoutParams(width, linearlayout_time_height)); //设置布局中datatime控件的宽度和高度
		linearlayout_lock.setLayoutParams(new LinearLayout.LayoutParams(width, width));//设置布局中lock控件的宽度和高度
		//设置下方圆的布局
		final LinearLayout layout_lock = (LinearLayout) findViewById(R.id.lock);
		final DrawView_lock lock_view = new DrawView_lock(this); 
		//这里开始传递数据
		lock_view.color0 = color0;
		lock_view.Is24 = or;
		lock_view.style = style;
		
		layout_lock.addView(lock_view);
		lock_view.setMainHandler(mHandler);
		
		final Runnable time_tv_run = new Runnable() {
			
			@Override
			public void run() {
				NowTime = new Time();
				NowTime.setToNow();
				//获取秒
				Stime1 = NowTime.second;
		        //获取分
		        Mtime1 = NowTime.minute;
		        //获取时
		        Htime1 = NowTime.hour;
		        //如果用户选择的是24小时的话，就用24小时显示，否则用12小时显示
		        if(or == false)
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
		        
		        if(Mtime1<10){
		        	Mtime = "0" + Mtime1;
		        }else{
		        	Mtime = Mtime1 + "";
		        }
		        //获取时
		        Htime1 = NowTime.hour;
		        if(show == 1){
		        	time_tv.setTextSize(100);
		        	time_tv.setText(Htime0 + ":" + Mtime);
		        	day.setText(StringData());
		        }
		        else{
		        	time_tv.setTextSize(80);
			        time_tv.setText(showtext_one);
			        day.setText(showtext_two);
		        }
			}
		};
		
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				linearlayout_datatime.post(time_tv_run);
			}
		},200,200);
		
		linearlayout_datatime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isUp == false){
					toast(isUp);
				}else {
					toast(isUp);
				}
			}
		});
		
		linearlayout_datatime.setOnLongClickListener(new OnLongClickListener() {
			
			@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
			public boolean onLongClick(View v) {
				if(isUp == false){
					linearlayout_datatime.setLayoutParams(new LinearLayout.LayoutParams(width, height));
					linearlayout_lock.setLayoutParams(new LinearLayout.LayoutParams(width, 0));
					layout_lock.setVisibility(View.GONE);
		             getWindow().clearFlags(
                             WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		             getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
					main.setBackgroundColor(Color.BLACK);
					datatime2.setVisibility(View.GONE);
					time_tv.setTextColor(Color.argb(100, 255, 255, 255));
					isUp = true;
					toast(isUp);
//					MyLogger.showLogWithLineNum(1, "Main", "isUp = "+ isUp);
				}else {
					linearlayout_datatime.setLayoutParams(new LinearLayout.LayoutParams(width, linearlayout_time_height));
					linearlayout_lock.setLayoutParams(new LinearLayout.LayoutParams(width, width));
					layout_lock.setVisibility(View.VISIBLE);
		             getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		             getWindow().setFlags(
		                                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
		                                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
					main.setBackgroundColor(Color.argb(159, 0, 0, 0));
					time_tv.setTextColor(Color.WHITE);
					datatime2.setBackgroundColor(Color.BLACK);
					datatime2.setVisibility(View.VISIBLE);
					isUp = false;
					toast(isUp);
//					MyLogger.showLogWithLineNum(1, "Main", "isUp = "+ isUp);
				}
			return false;
			}
		});
		

	}
	//这里的值是用户给设置的值
	private void init() {
		or = false;
		color0 = Color.argb(255, 0, 255, 100);
		style = 1;
	}
	
	private String StringData() {
		final Calendar c = Calendar.getInstance();  
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));  
        mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份  
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份  
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码  
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));  
        if("1".equals(mWay)){  
            mWay ="天";  
        }else if("2".equals(mWay)){  
            mWay ="一";  
        }else if("3".equals(mWay)){  
            mWay ="二";  
        }else if("4".equals(mWay)){  
            mWay ="三";  
        }else if("5".equals(mWay)){  
            mWay ="四";  
        }else if("6".equals(mWay)){  
            mWay ="五";  
        }else if("7".equals(mWay)){  
            mWay ="六";  
        }  
        showDay = "星期" + mWay + " " + mYear + "年" + mMonth + "月" + mDay + "日";
        return showDay; 
	}

	@SuppressLint("HandlerLeak")
	public Handler mHandler =new Handler (){
		
		public void handleMessage(Message msg){

			if(MSG_LOCK_SUCESS == msg.what)
			{
				show = 1;
				Intent i= new Intent(Intent.ACTION_MAIN);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //提示如果是服务里调用，必须加入new task标识
				i.addCategory(Intent.CATEGORY_HOME);
				startActivity(i);
				kg();
				finish();
			}
			else if(MY_PHONE == msg.what)
			{
				show = 1;
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
				startActivity(intent);
				kg();
				finish();
			}
			else if(MY_SSM == msg.what)
			{
				show = 1;
				Intent intent = new Intent();
				ComponentName comp = new ComponentName("com.android.mms",
						"com.android.mms.ui.ConversationList");
				intent.setComponent(comp);
				intent.setAction("android.intent.action.VIEW");
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
				startActivity(intent);
				kg();
				finish();
			}
			else if(MY_TROCH == msg.what)
			{
				show = 1;
				if(!isopent)
				{	
					camera = Camera.open();
					Parameters params = camera.getParameters();
					params.setFlashMode(Parameters.FLASH_MODE_TORCH);
					camera.setParameters(params);
					camera.startPreview();
					isopent = true;
				}
				else
				{
					camera.stopPreview();
					camera.release();
					isopent = false;
				}
			}
			else if(MSG_LOCK_SUCESS_TV == msg.what){
				show = 2;
				showtext_one = "解 锁";
				showtext_two = "松开手指解锁";
			}
			else if(MY_PHONE_TV == msg.what)
			{
				show = 2;
				showtext_one = "电 话";
				showtext_two = "松开手指拨打电话";
			}
			else if(MY_SSM_TV == msg.what)
			{
				show = 2;
				showtext_one = "短 信";
				showtext_two = "松开手指发送短信";
			}
			else if(MY_TROCH_TV == msg.what)
			{
				show = 2;
				showtext_one = "滑 动";
				showtext_two = "感觉到震动以后滑动";
			}
			else if(ERROR == msg.what)
			{
				show = 2;
				showtext_one = "失 效";
				showtext_two = "未从中心区域开始滑动，请重新滑动";
			}else if(SHOW == msg.what)
			{
				show = 1;
			}
			else{
				show = 1;
			}
		}
		public void kg()
	    {
	    	if(!isopent)
			{
			}
			else
			{
				camera.stopPreview();
				camera.release();
				isopent = false;
			}
	    }
	};
	@SuppressLint("ShowToast")
	//用Toast来显示提示消息
	public void toast(Boolean isUp) {
		if(isUp){
			Toast.makeText(Main.this, "长按时间退出勿扰模式", Toast.LENGTH_SHORT).show();
//			MyLogger.showLogWithLineNum(1, "Main", "isUp = "+ isUp.toString());
		}else {
			Toast.makeText(Main.this, "长按时间进入勿扰模式", Toast.LENGTH_SHORT).show();
//			MyLogger.showLogWithLineNum(1, "Main", "isUp = "+ isUp.toString());
		}
	}
}