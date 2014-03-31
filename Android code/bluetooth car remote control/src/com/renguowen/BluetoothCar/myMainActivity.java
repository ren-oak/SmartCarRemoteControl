package com.renguowen.BluetoothCar;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import com.renguowen.BluetoothCar.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class myMainActivity extends Activity {	
	private final static int REQUEST_CONNECT_DEVICE = 1;    //宏定义查询设备句柄
	private final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";   //SPP服务UUID号
    // 返回时数据标签
    public static String EXTRA_DEVICE_ADDRESS = "Device address";
    private String address;
	static InputStream is;    //输入流，用来接收蓝牙数据
	
    private EditText edit0;    //发送数据输入
    private ScrollView sv;      //翻页
    private String smsg = "";    //显示用数据缓存
    private String fmsg = "";    //保存用数据缓存
    private ToggleButton tbtnSwitch;
    private Button btnDis;
    private Button btnUpdate;
    private Button btnAbout;
    static ListView paired_devices1; 
    static ListView new_devices1;
    public String filename=""; //用来保存存储的文件名
    BluetoothDevice _device = null;     //蓝牙设备
    static BluetoothSocket _socket = null;      //蓝牙通信socket
    boolean _discoveryFinished = false;    
    boolean bRun = true;
    boolean bThread = false;   
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;	
    private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();    //获取本地蓝牙适配器，即蓝牙设备
  //  /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        
        new_devices1 = (ListView) findViewById(R.id.new_devices1);
        paired_devices1 = (ListView) findViewById(R.id.paired_devices1);       
        tbtnSwitch=(ToggleButton)findViewById(R.id.tbtnSwitch);
        btnDis=(Button)findViewById(R.id.btnDis);
        btnUpdate=(Button)findViewById(R.id.btnUpdate);
        btnAbout=(Button)findViewById(R.id.btnAbout);
        tbtnSwitch.setOnClickListener(new openlistener());
        btnUpdate.setOnClickListener(new visible());
        btnAbout.setOnClickListener(new quit());
        btnDis.setOnClickListener(new OnClickListener(){	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(_bluetooth.isEnabled()==false)
						Toast.makeText(myMainActivity.this, getResources().getString(R.string.pleaseopen), Toast.LENGTH_SHORT).show();
					else
					{
						doDiscovery();						
					}							
			}        	
        });
       //如果打开本地蓝牙设备不成功，提示信息，结束程序      
        if (_bluetooth == null){
        	Toast.makeText(this, getResources().getString(R.string.nobluetooth), Toast.LENGTH_LONG).show();
            finish();
            return;
        }               
        // 初使化设备存储数组
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);////////////////////////////??????
        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);

        // 设置已配队设备列表
        
        ListView pairedListView = (ListView) findViewById(R.id.paired_devices1);
       
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        // 设置新查找设备列表
        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices1);
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(mDeviceClickListener);

        // 注册接收查找到设备action接收器
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        // 注册查找结束action接收器
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);       
        if(_bluetooth.isEnabled()==true)
			tbtnSwitch.setChecked(true);			
		else if(_bluetooth.isEnabled()==false)
			tbtnSwitch.setChecked(false);	       
    }	
    public void connecked()
    {
    	_device = _bluetooth.getRemoteDevice(address);
    	try{
    		if((_socket!=null))
    		{
    			is.close();
    			_socket.close();
    			_socket=null;
    		}
        	_socket = _device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
        	Log.v("address1:"+address, "address1"+address);
        }catch(IOException e){
        	Log.v("addressA:"+address, "addressA"+address);
        	Toast.makeText(this, getResources().getString(R.string.failcon), Toast.LENGTH_SHORT).show();
        }
    	
    	try{
        	_socket.connect();
        	Log.v("address2:"+address, "address2:"+address);
        	Toast.makeText(this, getResources().getString(R.string.con)+_device.getName()+getResources().getString(R.string.suc), Toast.LENGTH_SHORT).show();
        	//btn.setText("断开");
        }catch(IOException e){
        	try{
        		Log.v("addressB:"+address, "addressB"+address);
        		Toast.makeText(this, getResources().getString(R.string.failcon), Toast.LENGTH_SHORT).show();
        		_socket.close();
        		_socket = null;
        	}catch(IOException ee){
        		Log.v("addressC:"+address, "addressC"+address);
        		Toast.makeText(this, getResources().getString(R.string.failcon), Toast.LENGTH_SHORT).show();
        	}
        	
        	return;
        }
    	try{
        	Log.v("address3:"+address, "address3:"+address);
    		is = _socket.getInputStream();   //得到蓝牙数据输入流
    		Log.v("address4:"+address, "address4:"+address);
    		
    		Intent intent=new Intent(this,ControlActivity.class);
    		startActivity(intent);
    		
    		}catch(IOException e){
    			Toast.makeText(this, getResources().getString(R.string.rcdafail), Toast.LENGTH_SHORT).show();
    			return;
    		}
    }  
    //关闭程序掉用处理部分
    public void onDestroy(){
    	super.onDestroy();
    	if(_socket!=null)  //关闭连接socket
    	try{
    		_socket.close();
    	}catch(IOException e){}
    	_bluetooth.disable();  //关闭蓝牙服务
    }    
	class openlistener implements View.OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(_bluetooth.isEnabled()==false)
			{	 
				tbtnSwitch.setChecked(true);
				new Thread(){
		    	   public void run(){
		    		   if(_bluetooth.isEnabled()==false){
		        		_bluetooth.enable();
		    		   }
		    	   }   	   
		       }.start();			
		       Toast.makeText(myMainActivity.this, getResources().getString(R.string.opening), Toast.LENGTH_LONG).show();
		       
			}	
			if(_bluetooth.isEnabled()==true)
			{	 
				tbtnSwitch.setChecked(false);				
				_bluetooth.disable();			      
			}
		}		
		
	}	
	class visible implements View.OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(_bluetooth.isEnabled()==false)
				Toast.makeText(myMainActivity.this, getResources().getString(R.string.pleaseopen), Toast.LENGTH_SHORT).show();
			else
			{
				Intent localIntent2 = new Intent("android.bluetooth.adapter.action.REQUEST_DISCOVERABLE");
	            Intent localIntent3 = localIntent2.putExtra("android.bluetooth.adapter.extra.DISCOVERABLE_DURATION", 200);
	            startActivityForResult(localIntent2, 18);
			}
		}
		
	}
	class quit implements View.OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();			
		}
		
	}
	 private void doDiscovery() {
	      
	        // 在窗口显示查找中信息
	        setProgressBarIndeterminateVisibility(true);
	        setTitle(getResources().getString(R.string.searching));

	        // 显示其它设备（未配对设备）列表
	        findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

	        // 关闭再进行的服务查找
	        if (_bluetooth.isDiscovering()) {
	        	_bluetooth.cancelDiscovery();
	        }
	        //并重新开始
	        _bluetooth.startDiscovery();
	    }
	 // 选择设备响应函数 
	    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
	            // 准备连接设备，关闭服务查找
	            _bluetooth.cancelDiscovery();
	            // 得到mac地址
	            String info = ((TextView) v).getText().toString();
	            address = info.substring(info.length() - 17);
	            connecked();	       	            
	        }
	    };
	 // 查找到设备和搜索完成action监听器
	    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
	        @Override
	        public void onReceive(Context context, Intent intent) {
	            String action = intent.getAction();

	            // 查找到设备action
	            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
	                // 得到蓝牙设备
	                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	                // 如果是已配对的则略过，已得到显示，其余的在添加到列表中进行显示
	                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
	                    mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
	                }else{  //添加到已配对设备列表
	                	mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
	                	//mPairedDevicesArrayAdapter.add(mBtAdapter.getRemoteDevice(device.getAddress()).getName() + "\n" + device.getAddress());
	                }
	            // 搜索完成action
	            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
	               setProgressBarIndeterminateVisibility(false);
	                setTitle(getResources().getString(R.string.select));
	                if (mNewDevicesArrayAdapter.getCount() == 0) {
	                    String noDevices =  getResources().getString(R.string.nonewdevice);
	                   mNewDevicesArrayAdapter.add(noDevices);
	               }
	             
	            }
	        }
	    };	    
}