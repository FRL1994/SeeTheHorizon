/*
 * ���ǹ������������Ļactivity����activity
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
	//�ж���(�ж��Ƿ���뾲Ĭģʽ)
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
	//����һ��time��ʱ��
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
	int Stime1;		/*ʱ����*/		
	int Mtime1;		/*ʱ���*/
	int Htime0,Htime1,Htime2;		/*ʱ��ʱ*/		
	int color0 = Color.argb(255, 255, 255, 255);
	
	@SuppressLint("CutPasteId")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final LinearLayout main = (LinearLayout)findViewById(R.id.main);//������ؼ�
		final LinearLayout linearlayout_datatime = (LinearLayout)findViewById(R.id.datatime);//ʱ��ؼ�
		final LinearLayout linearlayout_lock = (LinearLayout)findViewById(R.id.lock);//�����ؼ�
		final LinearLayout datatime2 = (LinearLayout)findViewById(R.id.datatime2);//�����ؼ�
		final TextView time_tv = (TextView)findViewById(R.id.time);
		final TextView day = (TextView)findViewById(R.id.day);
		DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        final int width = metric.widthPixels;     // ��Ļ��ȣ����أ�
        final int height = metric.heightPixels;   // ��Ļ�߶ȣ����أ�
        
        init();
        
        final int linearlayout_time_height = height - width - 80;  //����������ؼ��ĸ߶ȺͿ��(80Ϊ״̬���ĸ߶�)
		linearlayout_datatime.setLayoutParams(new LinearLayout.LayoutParams(width, linearlayout_time_height)); //���ò�����datatime�ؼ��Ŀ�Ⱥ͸߶�
		linearlayout_lock.setLayoutParams(new LinearLayout.LayoutParams(width, width));//���ò�����lock�ؼ��Ŀ�Ⱥ͸߶�
		//�����·�Բ�Ĳ���
		final LinearLayout layout_lock = (LinearLayout) findViewById(R.id.lock);
		final DrawView_lock lock_view = new DrawView_lock(this); 
		//���￪ʼ��������
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
				//��ȡ��
				Stime1 = NowTime.second;
		        //��ȡ��
		        Mtime1 = NowTime.minute;
		        //��ȡʱ
		        Htime1 = NowTime.hour;
		        //����û�ѡ�����24Сʱ�Ļ�������24Сʱ��ʾ��������12Сʱ��ʾ
		        if(or == false)
		        {
		        	// �������12�㣬�ͼ�ȥ12�����򲻼�ȥ�������ڻ���СʱԲ��
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
		        //��ȡʱ
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
	//�����ֵ���û������õ�ֵ
	private void init() {
		or = false;
		color0 = Color.argb(255, 0, 255, 100);
		style = 1;
	}
	
	private String StringData() {
		final Calendar c = Calendar.getInstance();  
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));  
        mYear = String.valueOf(c.get(Calendar.YEAR)); // ��ȡ��ǰ���  
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// ��ȡ��ǰ�·�  
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// ��ȡ��ǰ�·ݵ����ں���  
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));  
        if("1".equals(mWay)){  
            mWay ="��";  
        }else if("2".equals(mWay)){  
            mWay ="һ";  
        }else if("3".equals(mWay)){  
            mWay ="��";  
        }else if("4".equals(mWay)){  
            mWay ="��";  
        }else if("5".equals(mWay)){  
            mWay ="��";  
        }else if("6".equals(mWay)){  
            mWay ="��";  
        }else if("7".equals(mWay)){  
            mWay ="��";  
        }  
        showDay = "����" + mWay + " " + mYear + "��" + mMonth + "��" + mDay + "��";
        return showDay; 
	}

	@SuppressLint("HandlerLeak")
	public Handler mHandler =new Handler (){
		
		public void handleMessage(Message msg){

			if(MSG_LOCK_SUCESS == msg.what)
			{
				show = 1;
				Intent i= new Intent(Intent.ACTION_MAIN);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //��ʾ����Ƿ�������ã��������new task��ʶ
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
				showtext_one = "�� ��";
				showtext_two = "�ɿ���ָ����";
			}
			else if(MY_PHONE_TV == msg.what)
			{
				show = 2;
				showtext_one = "�� ��";
				showtext_two = "�ɿ���ָ����绰";
			}
			else if(MY_SSM_TV == msg.what)
			{
				show = 2;
				showtext_one = "�� ��";
				showtext_two = "�ɿ���ָ���Ͷ���";
			}
			else if(MY_TROCH_TV == msg.what)
			{
				show = 2;
				showtext_one = "�� ��";
				showtext_two = "�о������Ժ󻬶�";
			}
			else if(ERROR == msg.what)
			{
				show = 2;
				showtext_one = "ʧ Ч";
				showtext_two = "δ����������ʼ�����������»���";
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
	//��Toast����ʾ��ʾ��Ϣ
	public void toast(Boolean isUp) {
		if(isUp){
			Toast.makeText(Main.this, "����ʱ���˳�����ģʽ", Toast.LENGTH_SHORT).show();
//			MyLogger.showLogWithLineNum(1, "Main", "isUp = "+ isUp.toString());
		}else {
			Toast.makeText(Main.this, "����ʱ���������ģʽ", Toast.LENGTH_SHORT).show();
//			MyLogger.showLogWithLineNum(1, "Main", "isUp = "+ isUp.toString());
		}
	}
}