package com.renguowen.BluetoothCar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
/*
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.JedisPool;
import org.apache.commons.pool.impl.*;
*/
import android.net.wifi.*;
//import java.net.*;
//import redis.clients.jedis.Jedis;


import com.renguowen.BluetoothCar.R;
import com.renguowen.BluetoothCar.myMainActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup;

public class ControlActivity extends Activity{
	  boolean bRun = true;
	  boolean bThread = false;
	  private TextView dis;
	  private ScrollView sv;
	  private EditText edit0;
	  private TextView text0;
	  private String smsg;
	  private String fmsg;
	  private String filename="";
	  private Button btnA;
	  private Button btnL;
	  private Button btnR;
	  private Button btnB;
	  private Button btnS;
	  private Button btnBZ;
	  private Button btnXX;
	  private Button btnwifiContorl;
	  private RadioButton radiobtnon;
	  private RadioButton radiobtnoff;
	  private RadioGroup radioGroup;
	  private static final boolean banduan = true;
		
		private static final String TAG = "RGWbluetoothsmartcar";
	 
	@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);   
	        
	        text0 = (TextView)findViewById(R.id.Text0);  //提示栏
	        edit0 = (EditText)findViewById(R.id.Edit0);   //输入   
	        radiobtnon=(RadioButton)findViewById(R.id.radio1);
	        radiobtnoff=(RadioButton)findViewById(R.id.radio0);//http://blog.csdn.net/liu_zhen_wei/article/details/6718681
	        /******************************************************/
	        
	 /*       StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
	        .detectDiskReads()//当发生磁盘读操作时输出  
	        .detectDiskWrites()//当发生磁盘写操作时输出  
	        .detectNetwork()   // or .detectAll() for all detectable problems
	        ////访问网络时输出，这里可以替换为detectAll() 就包括了磁盘读写和网络I/O
	        .penaltyLog()//以日志的方式输出  
	        .build());
	      
	        
	        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
	        .detectLeakedSqlLiteObjects()//探测SQLite数据库操作 
	        .detectLeakedClosableObjects()
	        .penaltyLog()//以日志的方式输出  
	        .penaltyDeath()
	        .build());

   
	        Log.v("start","aa");
	        Jedis jedis = new Jedis("60.10.1.205",8080);	        
	        Log.v("http","bb");	     
	        MyListener myjedis = new MyListener();	        
	        Log.v("channel","cc");	       
	         jedis.subscribe(myjedis,"command");
	        jedis.disconnect();
	        Log.v("end","dd");
*/		        
	        /******************************************************/
	        init(); 	
	        
	 }  
	 private void init() { 
				
	/**
	 * @return *****************************************************/
	//设置按钮功能       			            	            
		            //前进
		            btnA=(Button)findViewById(R.id.btna);
		            btnA.setOnTouchListener(new Button.OnTouchListener(){
		                   OutputStream os;
						@Override
						public boolean onTouch(View v, MotionEvent event) {
							// TODO Auto-generated method stub
							String message;
							byte[] msgBuffer;
							int action = event.getAction();
							
							switch(action)
							{
							case MotionEvent.ACTION_DOWN:
							try {
								 os = myMainActivity._socket.getOutputStream();

		                      } catch (IOException e) {
		                          Log.e(TAG, "ON RESUME: Output stream creation failed.", e);
		                      }


		                      message = "a";

		                     msgBuffer = message.getBytes();

		                      try {
		                      	os.write(msgBuffer);

		                      } catch (IOException e) {
		                              Log.e(TAG, "ON RESUME: Exception during write.", e);
		                      }
							break;
							
							case MotionEvent.ACTION_UP:
								try {
			                      	os = myMainActivity._socket.getOutputStream();

			                      } catch (IOException e) {
			                          Log.e(TAG, "ON RESUME: Output stream creation failed.", e);
			                      }


			                      message = "s";

			                      msgBuffer = message.getBytes();

			                      try {
			                      	os.write(msgBuffer);

			                      } catch (IOException e) {
			                              Log.e(TAG, "ON RESUME: Exception during write.", e);
			                      }
								break;
							}
							return false;
						}
		            });
		            
		            //后退
		            btnB=(Button)findViewById(R.id.btnb);
		            btnB.setOnTouchListener(new Button.OnTouchListener(){
		                   OutputStream os;
						@Override
						public boolean onTouch(View v, MotionEvent event) {
							// TODO Auto-generated method stub
							String message;
							byte[] msgBuffer;
							int action = event.getAction();
							
							switch(action)
							{
							case MotionEvent.ACTION_DOWN:
							try {
								 os = myMainActivity._socket.getOutputStream();

		                      } catch (IOException e) {
		                          Log.e(TAG, "ON RESUME: Output stream creation failed.", e);
		                      }


		                      message = "b";

		                     msgBuffer = message.getBytes();

		                      try {
		                      	os.write(msgBuffer);

		                      } catch (IOException e) {
		                              Log.e(TAG, "ON RESUME: Exception during write.", e);
		                      }
							break;
							
							case MotionEvent.ACTION_UP:
								try {
			                      	os = myMainActivity._socket.getOutputStream();

			                      } catch (IOException e) {
			                          Log.e(TAG, "ON RESUME: Output stream creation failed.", e);
			                      }


			                      message = "s";

			                      msgBuffer = message.getBytes();

			                      try {
			                      	os.write(msgBuffer);

			                      } catch (IOException e) {
			                              Log.e(TAG, "ON RESUME: Exception during write.", e);
			                      }
								break;
							}
							return false;
						}
		            });
		            
		            //左转
		            btnL=(Button)findViewById(R.id.btnl);
		            btnL.setOnTouchListener(new Button.OnTouchListener(){
		                   OutputStream os;
						@Override
						public boolean onTouch(View v, MotionEvent event) {
							// TODO Auto-generated method stub
							String message;
							byte[] msgBuffer;
							int action = event.getAction();
							
							switch(action)
							{
							case MotionEvent.ACTION_DOWN:
							try {
								 os = myMainActivity._socket.getOutputStream();

		                      } catch (IOException e) {
		                          Log.e(TAG, "ON RESUME: Output stream creation failed.", e);
		                      }


		                      message = "l";

		                     msgBuffer = message.getBytes();

		                      try {
		                      	os.write(msgBuffer);

		                      } catch (IOException e) {
		                              Log.e(TAG, "ON RESUME: Exception during write.", e);
		                      }
							break;
							
							case MotionEvent.ACTION_UP:
								try {
			                      	os = myMainActivity._socket.getOutputStream();

			                      } catch (IOException e) {
			                          Log.e(TAG, "ON RESUME: Output stream creation failed.", e);
			                      }


			                      message = "s";

			                      msgBuffer = message.getBytes();

			                      try {
			                      	os.write(msgBuffer);

			                      } catch (IOException e) {
			                              Log.e(TAG, "ON RESUME: Exception during write.", e);
			                      }
								break;
							}
							return false;
						}
		            });
		            
		            //右转
		            btnR=(Button)findViewById(R.id.btnr);
		            btnR.setOnTouchListener(new Button.OnTouchListener(){
		                   OutputStream os;
						@Override
						public boolean onTouch(View v, MotionEvent event) {
							// TODO Auto-generated method stub
							String message;
							byte[] msgBuffer;
							int action = event.getAction();
							
							switch(action)
							{
							case MotionEvent.ACTION_DOWN:
							try {
								 os = myMainActivity._socket.getOutputStream();

		                      } catch (IOException e) {
		                          Log.e(TAG, "ON RESUME: Output stream creation failed.", e);
		                      }
		                      message = "r";
		                     msgBuffer = message.getBytes();

		                      try {
		                      	os.write(msgBuffer);

		                      } catch (IOException e) {
		                              Log.e(TAG, "ON RESUME: Exception during write.", e);
		                      }
							break;
							
							case MotionEvent.ACTION_UP:
								try {
			                      	os = myMainActivity._socket.getOutputStream();

			                      } catch (IOException e) {
			                          Log.e(TAG, "ON RESUME: Output stream creation failed.", e);
			                      }


			                      message = "s";

			                      msgBuffer = message.getBytes();

			                      try {
			                      	os.write(msgBuffer);

			                      } catch (IOException e) {
			                              Log.e(TAG, "ON RESUME: Exception during write.", e);
			                      }
								break;
							}
							return false;
						}

		            });
		            //避障
		            
		            btnBZ=(Button)findViewById(R.id.btnbz);
		            btnBZ.setOnTouchListener(new Button.OnTouchListener(){
		                   OutputStream os;
						@Override
						public boolean onTouch(View v, MotionEvent event) {
							// TODO Auto-generated method stub
							String message;
							byte[] msgBuffer;
							int action = event.getAction();
							
							switch(action)
							{
							case MotionEvent.ACTION_DOWN:
							try {
								 os = myMainActivity._socket.getOutputStream();

		                      } catch (IOException e) {
		                          Log.e(TAG, "ON RESUME: Output stream creation failed.", e);
		                      }
		                      message = "bz";
		                     msgBuffer = message.getBytes();
		                      try {
		                      	os.write(msgBuffer);

		                      } catch (IOException e) {
		                              Log.e(TAG, "ON RESUME: Exception during write.", e);
		                      }
							break;
							
							case MotionEvent.ACTION_UP:
								try {
			                      	os = myMainActivity._socket.getOutputStream();

			                      } catch (IOException e) {
			                          Log.e(TAG, "ON RESUME: Output stream creation failed.", e);
			                      }


			                      message = "s";

			                      msgBuffer = message.getBytes();

			                      try {
			                      	os.write(msgBuffer);

			                      } catch (IOException e) {
			                              Log.e(TAG, "ON RESUME: Exception during write.", e);
			                      }
								break;
							}
							return false;
						}
		            });
		            
		            //巡线
		            btnXX=(Button)findViewById(R.id.btnxx);
		            btnXX.setOnTouchListener(new Button.OnTouchListener(){
		                   OutputStream os;
						@Override
						public boolean onTouch(View v, MotionEvent event) {
							// TODO Auto-generated method stub
							String message;
							byte[] msgBuffer;
							int action = event.getAction();
							
							switch(action)
							{
							case MotionEvent.ACTION_DOWN:
							try {
								 os = myMainActivity._socket.getOutputStream();

		                      } catch (IOException e) {
		                          Log.e(TAG, "ON RESUME: Output stream creation failed.", e);
		                      }


		                      message = "xx";

		                     msgBuffer = message.getBytes();

		                      try {
		                      	os.write(msgBuffer);

		                      } catch (IOException e) {
		                              Log.e(TAG, "ON RESUME: Exception during write.", e);
		                      }
							break;
							
							case MotionEvent.ACTION_UP:
								try {
			                      	os = myMainActivity._socket.getOutputStream();

			                      } catch (IOException e) {
			                          Log.e(TAG, "ON RESUME: Output stream creation failed.", e);
			                      }


			                      message = "s";

			                      msgBuffer = message.getBytes();

			                      try {
			                      	os.write(msgBuffer);

			                      } catch (IOException e) {
			                              Log.e(TAG, "ON RESUME: Exception during write.", e);
			                      }
								break;
							}
							return false;
						}
		            });
		            
		            //停止  
		            btnS=(Button)findViewById(R.id.btns);
		            btnS.setOnTouchListener(new Button.OnTouchListener(){
		                 OutputStream os;
						@Override
						public boolean onTouch(View v, MotionEvent event) {
							// TODO Auto-generated method stub
							if(event.getAction()==MotionEvent.ACTION_DOWN)
							 try {
			                      	os = myMainActivity._socket.getOutputStream();

			                      } catch (IOException e) {
			                          Log.e(TAG, "ON RESUME: Output stream creation failed.", e);
			                      }


			                      String message = "s";

			                      byte[] msgBuffer = message.getBytes();

			                      try {
			                      	os.write(msgBuffer);

			                      } catch (IOException e) {
			                              Log.e(TAG, "ON RESUME: Exception during write.", e);
			                      }
							return false;
						}		            			         
		            });  
		            

					radioGroup = (RadioGroup)findViewById(R.id.radioGroup1);  
			        radioGroup.setOnCheckedChangeListener(new RadioGroupListener());
		      
 } 

	 
	 /***********************************************************/
/*	 public class MyListener extends JedisPubSub {
	        public void onMessage(String channel, String message) {
	        	Log.v("onMessage:","start"+message+"end");
	        	 sendmessage(message);
					
	        }

	        public void onSubscribe(String channel, int subscribedChannels) {
	        	
	        	
	        }

	        public void onUnsubscribe(String channel, int subscribedChannels) {
	        }

	        public void onPSubscribe(String pattern, int subscribedChannels) {
	        }

	        public void onPUnsubscribe(String pattern, int subscribedChannels) {
	        }

	        public void onPMessage(String pattern, String channel,String message) {
	        	Log.v("OnPMessage msg:","start"+message+"end");
	        }
        
	        public void sendmessage(String message)
	        {
	        	OutputStream os = null;	
	        	
	   			
	   			 try {
	                 	os = myMainActivity._socket.getOutputStream();

	                 } catch (IOException e) {
	                     Log.e(TAG, "ON RESUME: Output stream creation failed.", e);
	                 }
          
	        	
	        	
	        	
	    
	        	
	                 if(message.equalsIgnoreCase("forward")){
	                	 Log.v("forward","aa");
	                	 String com = "a";

		                 byte[] msgBuffer = com.getBytes();
		                 
		                 try {
		                 	os.write(msgBuffer);

		                 } catch (IOException e) {
		                         Log.e(TAG, "ON RESUME: Exception during write.", e);
		                 }
	                	 
	                	 
	                 }
	                 else if(message.equalsIgnoreCase("backward")){
	                	 Log.v("backward","aa");
	                	 String com = "b";

		                 byte[] msgBuffer = com.getBytes();
		                 
		                 try {
		                 	os.write(msgBuffer);

		                 } catch (IOException e) {
		                         Log.e(TAG, "ON RESUME: Exception during write.", e);
		                 } 
	                	 
	                	 
	                 }
	                 else if (message.equalsIgnoreCase("clockwise")){
	                	 Log.v("clockwise","aa");
	                	 String com = "r";

		                 byte[] msgBuffer = com.getBytes();
		                 
		                 try {
		                 	os.write(msgBuffer);

		                 } catch (IOException e) {
		                         Log.e(TAG, "ON RESUME: Exception during write.", e);
		                 }
	                	 
	                 }
	                 else if (message.equalsIgnoreCase("anticlockwise")){
	                	 Log.v("anti","aa");
	                	 String com = "l";

		                 byte[] msgBuffer = com.getBytes();
		                 
		                 try {
		                 	os.write(msgBuffer);

		                 } catch (IOException e) {
		                         Log.e(TAG, "ON RESUME: Exception during write.", e);
		                 }
	                	 
	                 }
	                 else{
	                	 String com = "s";
	                	 Log.v("stop:","ss");
		                 byte[] msgBuffer = com.getBytes();
		                 
		                 try {
		                 	os.write(msgBuffer);

		                 } catch (IOException e) {
		                         Log.e(TAG, "ON RESUME: Exception during write.", e);
		                 }
		 
	                 }    
	           
	        }
	
	}
*/
	 /***********************************************************/

	 public void sendmessage(String message)
     {
     	OutputStream os = null;	
     	
			
			 try {
              	os = myMainActivity._socket.getOutputStream();

              } catch (IOException e) {
                  Log.e(TAG, "ON RESUME: Output stream creation failed.", e);
              }
     }
	 
	 
	 
	 
	 
	 
	 
	 
	 class RadioGroupListener implements RadioGroup.OnCheckedChangeListener{  		            
         @Override  
             public void onCheckedChanged(RadioGroup group, int checkedId) {  
               // TODO Auto-generated method stub   
          if(checkedId == radiobtnon.getId()){   //启动远程控制端口 	         	         	  
            }else if(checkedId == radiobtnoff.getId()){    //关闭远程控制端口		
                } 	           
               }  
        } 
	 
	 /************************************************************/
	 public void onSendButtonClicked(View v){
	    	try{
	    		OutputStream os = myMainActivity._socket.getOutputStream();   //蓝牙连接输出流
	    		byte[] bos = edit0.getText().toString().getBytes();	
	    		os.write(bos);	    
	    		edit0.setText("");   //清空	    		
	    	}catch(IOException e){ 
	    		finish();
	    	}  	
	    }
	  //消息处理队列
	    Handler handler= new Handler(){
	    	public void handleMessage(Message msg){
	    		super.handleMessage(msg);
	    		dis.setText(smsg);   //显示数据 
	    		Log.v("smsg:", smsg);
	    		sv.scrollTo(0,dis.getMeasuredHeight()); //跳至数据最后一页
	    	}
	    };	    
	    //退出按键响应函数
	    public void onQuitButtonClicked(View v){
	    	
	    	 try{	    	    	
	    		 myMainActivity.is.close();
	    		 myMainActivity._socket.close();
	    		 myMainActivity._socket = null;
	    	    	bRun = false;
	    	    
	    	    }catch(IOException e){}   	    	
	    	finish();
	    }		
}
