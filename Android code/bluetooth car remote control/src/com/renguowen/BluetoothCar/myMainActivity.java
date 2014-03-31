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
	private final static int REQUEST_CONNECT_DEVICE = 1;    //�궨���ѯ�豸���
	private final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";   //SPP����UUID��
    // ����ʱ���ݱ�ǩ
    public static String EXTRA_DEVICE_ADDRESS = "Device address";
    private String address;
	static InputStream is;    //������������������������
	
    private EditText edit0;    //������������
    private ScrollView sv;      //��ҳ
    private String smsg = "";    //��ʾ�����ݻ���
    private String fmsg = "";    //���������ݻ���
    private ToggleButton tbtnSwitch;
    private Button btnDis;
    private Button btnUpdate;
    private Button btnAbout;
    static ListView paired_devices1; 
    static ListView new_devices1;
    public String filename=""; //��������洢���ļ���
    BluetoothDevice _device = null;     //�����豸
    static BluetoothSocket _socket = null;      //����ͨ��socket
    boolean _discoveryFinished = false;    
    boolean bRun = true;
    boolean bThread = false;   
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;	
    private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();    //��ȡ�����������������������豸
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
       //����򿪱��������豸���ɹ�����ʾ��Ϣ����������      
        if (_bluetooth == null){
        	Toast.makeText(this, getResources().getString(R.string.nobluetooth), Toast.LENGTH_LONG).show();
            finish();
            return;
        }               
        // ��ʹ���豸�洢����
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);////////////////////////////??????
        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);

        // ����������豸�б�
        
        ListView pairedListView = (ListView) findViewById(R.id.paired_devices1);
       
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        // �����²����豸�б�
        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices1);
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(mDeviceClickListener);

        // ע����ղ��ҵ��豸action������
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        // ע����ҽ���action������
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
        	//btn.setText("�Ͽ�");
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
    		is = _socket.getInputStream();   //�õ���������������
    		Log.v("address4:"+address, "address4:"+address);
    		
    		Intent intent=new Intent(this,ControlActivity.class);
    		startActivity(intent);
    		
    		}catch(IOException e){
    			Toast.makeText(this, getResources().getString(R.string.rcdafail), Toast.LENGTH_SHORT).show();
    			return;
    		}
    }  
    //�رճ�����ô�����
    public void onDestroy(){
    	super.onDestroy();
    	if(_socket!=null)  //�ر�����socket
    	try{
    		_socket.close();
    	}catch(IOException e){}
    	_bluetooth.disable();  //�ر���������
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
	      
	        // �ڴ�����ʾ��������Ϣ
	        setProgressBarIndeterminateVisibility(true);
	        setTitle(getResources().getString(R.string.searching));

	        // ��ʾ�����豸��δ����豸���б�
	        findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

	        // �ر��ٽ��еķ������
	        if (_bluetooth.isDiscovering()) {
	        	_bluetooth.cancelDiscovery();
	        }
	        //�����¿�ʼ
	        _bluetooth.startDiscovery();
	    }
	 // ѡ���豸��Ӧ���� 
	    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
	            // ׼�������豸���رշ������
	            _bluetooth.cancelDiscovery();
	            // �õ�mac��ַ
	            String info = ((TextView) v).getText().toString();
	            address = info.substring(info.length() - 17);
	            connecked();	       	            
	        }
	    };
	 // ���ҵ��豸���������action������
	    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
	        @Override
	        public void onReceive(Context context, Intent intent) {
	            String action = intent.getAction();

	            // ���ҵ��豸action
	            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
	                // �õ������豸
	                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
	                // ���������Ե����Թ����ѵõ���ʾ�����������ӵ��б��н�����ʾ
	                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
	                    mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
	                }else{  //��ӵ�������豸�б�
	                	mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
	                	//mPairedDevicesArrayAdapter.add(mBtAdapter.getRemoteDevice(device.getAddress()).getName() + "\n" + device.getAddress());
	                }
	            // �������action
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