/*
 * ������������Ѿ�ʵ��
 * ����number��������С��2��������ܳ��ֲ���������ʾЧ��
 * ����number������Ϊ��ȥ����Ȧ������Ȧ��
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
		
	//��������ı���������:

	int Htime0,Htime1,Htime2;		/*ʱ��ʱ*/		
	public Time NowTime;	/*��ȡʱ��*/		
	public int Stime0,Stime1;		/*ʱ����*/		
	public int Mtime0,Mtime1;		/*ʱ���*/
	public int HAngle;		/*ʱ�Ƕ�*/		
	public int MAngle;		/*�ֽǶ�*/
	public int SAngle;		/*��Ƕ�*/
    public Handler mainHandler = null;
	private Vibrator vibrator; 
    Timer time = null;
    TimerTask task = null;
    
//    ���Բ���
    float x = 0;
    int y = 0;
    float z = 0;
    Boolean dblclick = false;
    
	//<!--��ʼ�����룺
	int number = 3;
	boolean Is24 = false;
	int style = 1;
	int color0 = Color.RED;
	//���Դ����������
    
	//������:
	public int ViewWidth, ViewHeight; 	/*�ӿռ�Ŀ�͸�*/
	
	//��ʼ������λ��
	int touch_x = 0;
	int touch_y = 0;
	
	public DrawView_lock(Context context) {  
		super(context);
	}

	int first_judge = 0; 		/*�����жϰ��»����ƶ�����̧��,��һ���ж�*/
	int after_judge = 0; 	/*�����жϰ��µ������Ƿ�Ϊ��С��Բ�����򣬵ڶ����ж�*/
	public long firstClick = 0; 	/*��һ�εĵ��*/
	public long lastClick = 0;		/*�ڶ��ε��*/
	public int count = 0;		/*�������Ĵ���*/
		
	public void onDraw(final Canvas canvas) {  
		super.onDraw(canvas);
	    

	    //��ȡView�߶�
	    ViewHeight = getHeight();
	    //��ȡView���
	    ViewWidth = getWidth();
	    
	    //<!--���㵥λ����
	    int unit = 0;
	    int unit_length = 0;
	    unit = number * 2 + 3;
	    unit_length = ViewWidth / (unit * 2);
	    //���㵥λ�������-->
	    
		NowTime = new Time();
		NowTime.setToNow();
		//��ȡ��
		Stime1 = NowTime.second;
        //��ȡ��
        Mtime1 = NowTime.minute;
        //��ȡʱ
        Htime1 = NowTime.hour;
	    
	    //<!--��ȡʱ��
		Stime0 = Stime1 + 1;
        SAngle = 6 * Stime0;
        //��ȡ��
        Mtime0 = Mtime1 + 1;
        MAngle = 6 * Mtime0;
        if(Is24 == false)
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
        
        //����û�ѡ�����24Сʱ�Ļ�������24Сʱ��ʾ��������12Сʱ��ʾ
        if(Is24 == false)
        {
	        HAngle = 30 * Htime0;
        }
        else
        {
        	HAngle = 15 * Htime0;
        }
        //��ȡʱ�����-->
        
		//����Բ�ģ�x,y���ľ��룬������Ļ����(x,y)�ľ���
		int round_centre_x,round_centre_y,screen_centre_x,screen_centre_y;//c,h,e;
		//��ʼ��Բ�ľ���
	    round_centre_x = round_centre_y = 0; 
	    //��������Բ�ĵľ���
	    float distance_x_y = 0;
		//ȷ����Ļ����
		screen_centre_x = ViewWidth / 2;
		screen_centre_y = ViewHeight / 2;
		//ȷ��Բ��(Բ������Ļ����)
		round_centre_x = screen_centre_x;
		round_centre_y = screen_centre_y;
		
		//<!--������Բ�ĵľ���
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
		//������Բ�ĵľ������-->
		
	    //���廭��
	    Paint paint = new Paint();
	    //�Ƿ񿹾��
	    paint.setAntiAlias(true); 
	    //ʹ��ͼ�񶶶�����
	    paint.setDither(true);  
	    //�������ͣ��Ƿ�Ϊ���ĵȣ�(����)
	    paint.setStyle(Paint.Style.FILL);
	    paint.setColor(color0);
	    
	    this.postInvalidateDelayed(1);
	    
	    //�������еĴ����λ�ÿ��ܻ���Ҫ����˼��Ӧ�÷����Ķ�
	    if(dblclick){
		    canvas.drawCircle(screen_centre_x, screen_centre_y,unit_length * 3, paint);
	    }
	    
	    //���û��ʿ��
	    paint.setStrokeWidth(unit_length * 2 +3);
	    //ʲô��û�����ʱ��
	    if(first_judge == 0){
			if(after_judge == 0){
				show();
				//���û���ΪԲ��
			    paint.setStrokeCap(Paint.Cap.ROUND);
				//����Ϊ����
				paint.setStyle(Paint.Style.STROKE);
				//��һ��Բ
				paint.setStrokeWidth(1);
				paint.setAlpha(255);
				canvas.drawCircle(screen_centre_x, screen_centre_y,unit_length * 3, paint);
				//�ڶ���Բ
				paint.setAlpha(150);
				paint.setStrokeWidth(unit_length * 2);
				RectF Srectf=new RectF(screen_centre_x - (unit_length * (3+1)) ,screen_centre_y - (unit_length * (3+1)) ,screen_centre_x + (unit_length * (3+1)), screen_centre_y + (unit_length * (3+1)));
				canvas.drawArc(Srectf, 270, HAngle, false, paint);
				//������Բ
				paint.setAlpha(100);
				RectF Mrectf=new RectF(screen_centre_x - (unit_length * (3+3)) ,screen_centre_y - (unit_length * (3+3)) ,screen_centre_x + (unit_length * (3+3)), screen_centre_y + (unit_length * (3+3)));
				canvas.drawArc(Mrectf, 270, MAngle, false, paint);
				//���ĸ�Բ
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
	    /*������Ļ�Ժ�Ķ���*/
	    else if(first_judge == 1){
	    	//�����Բ�ĵľ���С�������ĵ�Բ�ľ���Ļ�
			if(distance_x_y <= unit_length * 3){
				after_judge = 1;		//��������Ȧʱ,������Ӧ���¼�
				//��ʼ������
        		//˫���¼������������ֵ�Ͳ

	        	//���������Ļ������ڶ���ֵ��ֵ
	        	 if(count == 1)
	        	{
		        	lastClick = System.currentTimeMillis();
	        		dblclick = dblclick(dblclick);
	        		count = 0;
			        firstClick = 0;
			        lastClick = 0;
	        	}
	        	//���������Ļ�������һ��ֵ��ֵ
		        else if(count == 0)
		        {
		        	firstClick = System.currentTimeMillis();
		        	count = 1;
		        	timer(true);
		        }
        	}
	    }
	    //����Ļ�л�����ʱ��
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
				//Ŀǰ�Ѿ���������Ϊ�û� ��ɫ
		    	paint.setAlpha(128);
				//����Ϊʵ��
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
	    

//		//����Ŀ�ʼ��������ʾ���ݵ�
//	    //��������
//	    paint.setTextSize(30);
//	    paint.setColor(Color.WHITE);
//	    paint.setStyle(Paint.Style.FILL);
//	    paint.setStrokeWidth(1);
//            
//
//	    canvas.drawText("Բ��x�����꣺"+round_centre_x, 0, 40, paint);  
//	    canvas.drawText("Բ��y�����꣺"+round_centre_y, 0, 80, paint);
//	    canvas.drawText("������x���꣺"+touch_x, 0, 120, paint);  
//	    canvas.drawText("������y���꣺"+touch_y, 0, 160, paint);
//	    canvas.drawText("��������Բ�ĵľ��룺"+distance_x_y, 0, 200, paint);
//	    canvas.drawText("���£��ƶ���̧��"+first_judge, 0, 240, paint);
//	    canvas.drawText("�ڶ����жϣ�"+ after_judge, 0, 280, paint);
//	    canvas.drawText("��ǰ��һ�ε��ʱ�䣺"+firstClick, 0, 320, paint);
//	    canvas.drawText("��ǰ�ڶ��ε��ʱ�䣺"+lastClick, 0, 360, paint);
//	    canvas.drawText("��ǰc���꣺"+count, 0, 400, paint);
//	    //��ʾ���ݴ��뵽��Ϊֹ
	}
	public boolean onTouchEvent(MotionEvent event) {  
   	 	//���ز���ʹ��������Ӧ�Ķ���
       int action = event.getAction();  
       touch_x = (int) event.getX();  
       touch_y = (int) event.getY();
       switch (action) { 
       // �������µ��¼�  
       case MotionEvent.ACTION_DOWN:
       {
       		first_judge = 1;
       		Log.v("test", "ACTION_DOWN");
       }
       break;  
       // �����ƶ����¼�  
       case MotionEvent.ACTION_MOVE: 
       {	
       		first_judge = 2;	
       		Log.v("test", "ACTION_MOVE");
       }
       break;  
       // ����̧����¼�  
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
	//������Ӧ���¼�
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
    //������Ӧ���¼�
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
    	//500������������¼�
    	time.schedule(task, 400);
    	}
    }
    public void vibrate(){
		vibrator = (Vibrator)getContext().getSystemService(Context.VIBRATOR_SERVICE); 
		vibrator.vibrate(10);
    }
}
