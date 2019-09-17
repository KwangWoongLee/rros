package tpgus.example.com.rros;
import android.Manifest;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.arch.lifecycle.ViewModelProvider;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tpgus.example.com.rros.Beacon.Beacon;
import tpgus.example.com.rros.Coupon.CouponItem;
import tpgus.example.com.rros.Fragment_Coupon;
import tpgus.example.com.rros.Fragment_Home;
import tpgus.example.com.rros.Fragment_More;
import tpgus.example.com.rros.Fragment_Search;
import tpgus.example.com.rros.GpsInfo;
import tpgus.example.com.rros.R;
import tpgus.example.com.rros.RemoteService;
import tpgus.example.com.rros.ServiceGenerator;
import tpgus.example.com.rros.request_Beacon_Flag;

public class After_Login extends AppCompatActivity implements SensorEventListener {
    public MemberInfoItem m;
    String image;
    String content;
    String date;
    SensorManager mSensorManager;
    Sensor mAccelerometer;
    private long mShakeTime;
    private static final int SHAKE_SKIP_TIME = 500;
    private static final float SHAKE_THRESHOLD_GRAVITY = 2.7F;
    int mShakeCount = 0;

    TextView t;
    String netid = "U+Net641C";
    String netpwd = "DDA4014110";
    String id = "my_channel_01";
    String LicenseNo = "1";
    String userID;
    Fragment_More fragment_more;
    Fragment_Home fragment_home;
    Fragment_Search fragment_search;
    Fragment_Coupon fragment_review;
    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;
    URL url;
    // GPSTracker class
    private GpsInfo gps;
    ConnectivityManager manager;

    ArrayList<CouponItem> list;


    //----------------------------B E A C O N ----------------------------------------------------------
    BluetoothAdapter mBluetoothAdapter;
    BluetoothLeScanner mBluetoothLeScanner;
    BluetoothLeAdvertiser mBluetoothLeAdvertiser;

    BluetoothAdapter mBluetoothAdapter1; //
    BluetoothLeScanner mBluetoothLeScanner1;//
    BluetoothLeAdvertiser mBluetoothLeAdvertiser1;//

    Vector<Beacon> beacon;
    Vector<Beacon> beacon1;//

    private static final int PERMISSIONS = 100;

    ConstraintLayout constraintLayout; //일단 test용 dialog띄울 화면 아이디

    ScanSettings.Builder mScanSettings;
    List<ScanFilter> scanFilters;
    ScanSettings.Builder mScanSettings1;//
    List<ScanFilter> scanFilters1;//

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.KOREAN);
    //----------------------------B E A C O N - E N D -----------------------------------------------------

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_home).commit();
                    return true;
                case R.id.search:
                    fragment_search = new Fragment_Search();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_search).commit();
                    return true;
                case R.id.review:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_review).commit();
                    return true;
                case R.id.more:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_more).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after__login);
        Context context = getApplicationContext();
//        SharedPreference sharedPreference = context.getSharedPreferences("key",Context.MODE_PRIVATE);
        //센서
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //

        String str=getIntent().getStringExtra("fragment");
        if(str!=null){
            if(str.equals("intent")){
            }
        }

        m=((MyApp)getApplicationContext()).getMemberInfoItem();



        fragment_home = new Fragment_Home();
        fragment_review = new Fragment_Coupon();
        fragment_search = new Fragment_Search();
        fragment_more = new Fragment_More();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_home).commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //pushAlarm
        Intent id = getIntent();
//        userID = id.getStringExtra("id");
        userID = "lgu4821";
        //------------------------B E A C O N --------------------------------
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS);
        constraintLayout = (ConstraintLayout) findViewById(R.id.container);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        mBluetoothLeAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();

        mBluetoothAdapter1 = BluetoothAdapter.getDefaultAdapter();//
        mBluetoothLeScanner1 = mBluetoothAdapter1.getBluetoothLeScanner();//
        mBluetoothLeAdvertiser1 = mBluetoothAdapter1.getBluetoothLeAdvertiser();//

        beacon = new Vector<>();
        beacon1 = new Vector<>();//

        mScanSettings1 = new ScanSettings.Builder();//
        mScanSettings1.setMatchMode(ScanSettings.SCAN_MODE_LOW_LATENCY);//
        ScanSettings scanSettings1 = mScanSettings1.build();//

        mScanSettings = new ScanSettings.Builder();
        mScanSettings.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY);
        ScanSettings scanSettings = mScanSettings.build();
        // 얘는 스캔 주기를 2초로 줄여주는 Setting입니다.
        // 공식문서에는 위 설정을 사용할 때는 다른 설정을 하지 말고
        // 위 설정만 단독으로 사용하라고 되어 있네요 ^^
        // 위 설정이 없으면 테스트해 본 결과 약 10초 주기로 스캔을 합니다.


        scanFilters = new Vector<>();
        ScanFilter.Builder scanFilter = new ScanFilter.Builder();
        scanFilter.setDeviceAddress("C8:FD:19:03:5B:F0"); //ex) C8:FD:19:02:7E:B0 //C8:FD:19:03:5B:F0
        ScanFilter scan = scanFilter.build();
        scanFilters.add(scan);


//        **************************scanfFilters1으로는 플래그 1일 때, 와이파이 연결 -> 플래그 2로 변경 -> 플래그 2면 쿠폰생성 ->스캔필터 종료 정상적으로 다 작동
//        scanFilters1과 scanFilters는 다른 비콘을 써야함 scanFilters는 쿠폰이 있다면 알림이 가능 용도
        // 당장은 비콘이 한 개라 같은 비콘으로 사용함
        scanFilters1 = new Vector<>();// scanfFilters1
        ScanFilter.Builder scanFilter1 = new ScanFilter.Builder();//
        scanFilter1.setDeviceAddress("C8:FD:19:02:7E:B0");//C8:FD:19:02:7E:B0//
        ScanFilter scan1 = scanFilter1.build();//
        scanFilters1.add(scan1);//


        ActivityManager manager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list=manager.getRunningTasks(1);
        ActivityManager.RunningTaskInfo info=list.get(0);
//        Toast.makeText(getApplicationContext(),info.topActivity.getClassName(),Toast.LENGTH_LONG).show();
        if(info.topActivity.getClassName().equals("tpgus.example.com.rros.After_Login")) {

            mBluetoothLeScanner.startScan(scanFilters, scanSettings, mScanCallback);
            // filter와 settings 기능을 사용하지 않을 때는
            mBluetoothLeScanner.startScan(mScanCallback);

            String str1=getIntent().getStringExtra("fragment");
            if(str1!=null){
                if(str1.equals("intent")){
                    navigation.setSelectedItemId(R.id.review);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_review).commit();
                    mBluetoothLeScanner.stopScan(mScanCallback);
                }
            }


        }

        mBluetoothLeScanner1.startScan(scanFilters1, scanSettings1, mScanCallback1);
        mBluetoothLeScanner1.startScan(mScanCallback1);
        //-----------------------------B E A C O N - E N D ------------------------------------


        gps = new GpsInfo(After_Login.this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }


    ScanCallback mScanCallback1 = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            try {
                ScanRecord scanRecord1 = result.getScanRecord();
                Log.d("getTxPowerLevel()", scanRecord1.getTxPowerLevel() + "");
                Log.d("onScanResult()", result.getDevice().getAddress() + "\n" + result.getRssi() + "\n" + result.getDevice().getName()
                        + "\n" + result.getDevice().getBondState() + "\n" + result.getDevice().getType());

                final ScanResult scanResult1 = result;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                beacon.add(0, new Beacon(scanResult1.getDevice().getAddress(), scanResult1.getRssi(), simpleDateFormat.format(new Date())));
                            }
                        });
                    }
                }).start();
                request();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
        }
    };

    //-------------------------------B E A C O N --------------------------------------
    ScanCallback mScanCallback = new ScanCallback() {

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            try {
                ScanRecord scanRecord = result.getScanRecord();
                Log.d("getTxPowerLevel()", scanRecord.getTxPowerLevel() + "");
                Log.d("onScanResult()", result.getDevice().getAddress() + "\n" + result.getRssi() + "\n" + result.getDevice().getName()
                        + "\n" + result.getDevice().getBondState() + "\n" + result.getDevice().getType());

                final ScanResult scanResult = result;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                beacon.add(0, new Beacon(scanResult.getDevice().getAddress(), scanResult.getRssi(), simpleDateFormat.format(new Date())));
                            }
                        });

                    }
                }).start();
                alertCoupon("C8:FD:19:03:5B:F0",userID);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
            Log.d("onBatchScanResults", results.size() + "");
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.d("onScanFailed()", errorCode + "");
        }
    };

    private void createAlert() {
        android.app.AlertDialog.Builder alert_ex = new android.app.AlertDialog.Builder(this);

        alert_ex.setView(R.layout.coupon_item2);

        alert_ex.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        android.app.AlertDialog alert = alert_ex.create();
        alert.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBluetoothLeScanner.stopScan(mScanCallback);
        mBluetoothLeScanner1.startScan(mScanCallback1);
    }


    // -------------------------------B E A C O N - E N D ----------------------------------------------

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(After_Login.this);
        builder.setMessage("어플을 종료하시겠습니까?");
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                After_Login.super.onBackPressed();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //------------push알람 채널 설정-----------------------------
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setPushAlarm() {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        CharSequence name = getString(R.string.app_name);//언제 쓰이는 건지 모르겠음.

// The user-visible description of the channel.
        String description = getString(R.string.app_name);//언제 쓰이는 건지 모르겠음.

        int importance = NotificationManager.IMPORTANCE_LOW;

        NotificationChannel mChannel = new NotificationChannel(id, name, importance);

// Configure the notification channel.
        mChannel.setDescription(description);
        mChannel.enableLights(true);

// Sets the notification light color for notifications posted to this
// channel, if the device supports this feature.
        mChannel.setLightColor(Color.RED);
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

        mNotificationManager.createNotificationChannel(mChannel);

    }

    public void setPush() {

        Intent notiIconClickIntent = new Intent(this, After_Login.class);
        notiIconClickIntent .putExtra("fragment", "intent");
        notiIconClickIntent .setAction(Intent.ACTION_MAIN);
        notiIconClickIntent .addCategory(Intent.CATEGORY_LAUNCHER);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(After_Login.class);
        stackBuilder.addNextIntent(notiIconClickIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

//        Intent intent=new Intent(this,After_Login.class);
//        intent.putExtra("fragment","intent");
//        TaskStackBuilder stackBuilder=TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(After_Login.class);
//       stackBuilder.addNextIntent(intent);
//        PendingIntent pendingIntent=stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, id)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("쿠폰 알림")
                        .setContentText("쿠폰을 사용할 수 있습니다.")
                        .setDefaults(Notification.DEFAULT_VIBRATE)//DEFAULT_VIBRATE 진동
                        .setAutoCancel(true)//true 알림 누르면 사라지고 이벤트(현재 인텐트-화면이동) false 계속 남아있음
                        .setContentIntent(pendingIntent);
        //createAlert();

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());

    }

    private void request() {
        request_Beacon_Flag rbf = new request_Beacon_Flag();
        rbf.Id = userID;

        final RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);
        Call<String> call = remoteService.postId(rbf);

        call.enqueue(new Callback<String>() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override

            public void onResponse(Call<String> call, Response<String> response) {


                if (response.isSuccessful()) {

                    if (response.body().toString().equals("1")) {
                        DialogSimple();
                    }

                    if(response.body().toString().equals("2"))
                    {
                        requestCoupon("C8:FD:19:03:5B:F0",userID);
                        mBluetoothLeScanner1.stopScan(mScanCallback1);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });

    }



    private void alertCoupon(String mac,String id) {

        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);
        Call<ArrayList<CouponItem>> call = remoteService.alertCoupon(mac,id);
        call.enqueue(new Callback<ArrayList<CouponItem>>() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ArrayList<CouponItem>> call, Response<ArrayList<CouponItem>> response) {
                if(response.isSuccessful()){
                    list=response.body();
                    image=list.get(0).getImage();
                    content=list.get(0).getContent();
                    date=list.get(0).getCouponDate();
                    setPushAlarm();
                    setPush();
                    mBluetoothLeScanner.stopScan(mScanCallback);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CouponItem>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),""+t,Toast.LENGTH_LONG).show();

            }
        });
    }

    private void requestCoupon(String mac,String id) {

        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);
        Call<String> call = remoteService.getCoupon(mac, id);
        call.enqueue(new Callback<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            isAccessCoarseLocation = true;
        }

        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }

    // 전화번호 권한 요청
    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }

    private void DialogSimple(){
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setMessage("매장의 와이파이에 연결하시겠습니까?").setCancelable(
                false).setPositiveButton("연결",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //
                        WifiManager wifiManager=(WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE); //활성화 한지 와이파이 체크 하기 위함
                        manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);


//NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);//와이파이 연결 체크
// wifi 또는 모바일 네트워크 어느 하나라도 연결이 되어있다면,
                        if (wifi.isConnected()) {
                            Toast.makeText(getApplicationContext(),"이미연결되어있습니다.",Toast.LENGTH_LONG).show();


                        } else {
                            Toast.makeText(getApplicationContext(),"WIFI 활성화를 위해 인텐트 시도.", Toast.LENGTH_LONG).show();
/////////활성화 되지 않았다면 활성화 코드 추가

                            wifiManager.setWifiEnabled(true); //와이파이 활성화
                    Intent intentConfirm = new Intent();
                    intentConfirm.setAction("android.settings.WIFI_SETTINGS");
                    startActivity(intentConfirm);

                            WifiConfiguration wifiConfig = new WifiConfiguration();
                            wifiConfig.SSID = String.format("\"%s\"", netid);
                            wifiConfig.preSharedKey = String.format("\"%s\"", netpwd);

                            int netId = wifiManager.addNetwork(wifiConfig);
                            wifiManager.disconnect();
                            wifiManager.enableNetwork(netId, true);
                            wifiManager.reconnect();
                        }
                    }
                }).setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alt_bld.create();
        // Title for AlertDialog
        alert.setTitle("Title");
        // Icon for AlertDialog
        alert.setIcon(R.drawable.wifi_vector_logo);
        alert.show();
    }


    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this,
                mAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float axisX = event.values[0];
            float axisY = event.values[1];
            float axisZ = event.values[2];

            float gravityX = axisX / SensorManager.GRAVITY_EARTH;
            float gravityY = axisY / SensorManager.GRAVITY_EARTH;
            float gravityZ = axisZ / SensorManager.GRAVITY_EARTH;

            Float f = gravityX * gravityX + gravityY*gravityY + gravityZ*gravityZ;
            double squaredD = Math.sqrt(f.doubleValue());
            float gForce = (float) squaredD;
            if(gForce > SHAKE_THRESHOLD_GRAVITY){
                long currentTime = System.currentTimeMillis();
                if(mShakeTime + SHAKE_SKIP_TIME > currentTime){
                    return;
                }
                mShakeTime = currentTime;
                mShakeCount++;
                shake_flag();
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

//
//    private class GetJSONObject extends AsyncTask<URL, Void, JSONObject>{
//
//        @Override
//        protected JSONObject doInBackground(URL... urls){
//            HttpURLConnection con =null;
//
//            try{
//                con= (HttpURLConnection)urls[0].openConnection();
//                int response = con.getResponseCode();
//                if(response == HttpURLConnection.HTTP_OK){
//                    StringBuilder builder = new StringBuilder();
//                    try(BufferedReader reader = new BufferedReader(
//                            new InputStreamReader(con.getInputStream()))) {
//                        String line;
//                        while ((line = reader.readLine()) !=null){
//                            builder.append(line);
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                } return new JSONObject(builder.toString());
//            } else {
//
//                }
//            } catch (Exception e){
//                e.printStackTrace();
//            } finally{
//                con.disconnect();
//            } return null;
//        }
//
//
//        @Override
//        protected void onPostExecute(JSONObject jsonObject){
//
//        }
//    }
public void shake_flag(){

    RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);
    Call<String> call = remoteService.shake(m.CustomerNo);
    call.enqueue(new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            if(response.isSuccessful()){
                Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            Toast.makeText(getApplicationContext(),""+t,Toast.LENGTH_LONG).show();

        }
    });

}
}