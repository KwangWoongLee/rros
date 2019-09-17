package tpgus.example.com.rros;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Button;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tpgus.example.com.rros.basket.BasketAdapter;
import tpgus.example.com.rros.basket.basketList;

public class Reservation extends AppCompatActivity {
    private BasketAdapter bAdapter;
    private RecyclerView bVerticalView;
    private LinearLayoutManager bLayoutManager;
    ArrayList<basketList> b;
    MemberInfoItem memberInfoItem;
    Button btn3;
    private final String TAG = this.getClass().getSimpleName();
    Context context;
    ReservationInfo currentItem;
    public int sum=0;
    int licenseNo;
    Spinner NofP;
    EditText NoOfPeople;
    public TextView total;
    String NoP;
    TextView name;
    String[] item;
    String ReservationTime;
    String Content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        bVerticalView = (RecyclerView) findViewById(R.id.reservlist);
        bLayoutManager = new LinearLayoutManager(getApplicationContext());
        bLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        bVerticalView.setLayoutManager(bLayoutManager);
        bAdapter = new BasketAdapter();
        bAdapter.getReservation(this);
        b= ((MyApp)getApplicationContext()).getBasketLists();
        total = (TextView) findViewById(R.id.totalcost);
        btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reservate();
            }
        });
        memberInfoItem=((MyApp)getApplicationContext()).getMemberInfoItem();
        NofP = (Spinner)findViewById(R.id.NofP);
        item = new String[]{"예약 인원을 선택하세요", "1","2","3","4","5","6","7","8"};
        name = (TextView) findViewById(R.id.resName);
        name.setText(memberInfoItem.CusName);
        ArrayAdapter<String> sAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item,item);
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NoOfPeople = (EditText)findViewById(R.id.request);
        NofP.setAdapter(sAdapter);

        NofP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NoP= item[position];

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                NoP = "0";
            }
        });
        Intent intent = getIntent();
        b = ((MyApp)getApplicationContext()).getBasketLists();
        context = this;
//        NoOfPeople = (EditText) findViewById(R.id.NoOfPeople);
        int size =intent.getIntExtra("size",0);
        String s= "";
        for(int i=0; i<b.size() ; i++){
//            b.add(new basketList(intent.getStringExtra("basket"+i+"image"),intent.getStringExtra("basket"+i+"name"),intent.getStringExtra("basket"+i+"count"),intent.getStringExtra("basket"+i+"price")));
            sum += Integer.parseInt(b.get(i).getCount())*Integer.parseInt(b.get(i).getPrice());
        }
        bAdapter.setData(b);
        bVerticalView.setAdapter(bAdapter);

        if(sum!=0) {
            String price = Integer.toString(sum);
            total.setText(sum / 1000 + "," + price.substring(price.length() - 3, price.length()));
        }else
            total.setText("0");

        licenseNo=intent.getIntExtra("license",99);

        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker_signup);


        datePicker.setMaxDate(System.currentTimeMillis() - 1000);
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker_signup);



        int ResYear = datePicker.getYear();
        int ResMonth = datePicker.getMonth() + 1;
        int ResDay = datePicker.getDayOfMonth();

        String ResTime = timePicker.getHour() + "시"  + timePicker.getMinute() + "분";

        ReservationTime = "" + ResYear + "년" +  ResMonth + "월" + ResDay + "일" + ResTime;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ((MyApp)getApplicationContext()).setBasketLists(b);

    }

    private ReservationInfo reservationInfo() {
        ReservationInfo item = new ReservationInfo();
        item.fk_LicenseNo=licenseNo;
        item.ReservationTime = ReservationTime;
        item.Content = NoOfPeople.getText().toString();
        item.fk_customerNo = memberInfoItem.CustomerNo;
        item.NoOfPeople = NoP;
        item.menuNo=2;
        return item;
    }

    private void reservate() {
        final ReservationInfo newItem = reservationInfo();

        //  Toast.makeText(getApplicationContext(),newItem.toString(),Toast.LENGTH_LONG).show();

        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<ReservationInfo> call = remoteService.reservationInfo(newItem);
        call.enqueue(new Callback<ReservationInfo>() {
            @Override
            public void onResponse(Call<ReservationInfo> call, Response<ReservationInfo> response) {

                if (response.isSuccessful()) {
                    currentItem.ReservationTime = newItem.ReservationTime;
                    currentItem.NoOfPeople = newItem.NoOfPeople;
                    currentItem.fk_customerNo = newItem.fk_customerNo;
                    currentItem.fk_LicenseNo = newItem.fk_LicenseNo;
                    currentItem.Content = newItem.Content;
                    currentItem.menuNo = newItem.menuNo;
                    Toast.makeText(getApplicationContext(),""+currentItem.fk_customerNo,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ReservationInfo> call, Throwable t) {
            }


        });

        sendSMS("01058529537","예약이 완료되었습니다. 30분 이내로 방문하여주세요^^ 즐겁고 행복한 하루되길 기원합니다~ ");
        Intent intent = new Intent(this,CompleteReservation.class);
        intent.putExtra("name",memberInfoItem.getCusName());
        intent.putExtra("time",ReservationTime);
        intent.putExtra("phone",memberInfoItem.getPhoneNo());
        intent.putExtra("reservationNo",ReservationTime+1);
        intent.putExtra("image","사진");

        startActivity(intent);

    }
    public void removeItem(int position){
        b.remove(position);
    }

    private void sendSMS(String phoneNumber, String message){
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERD";

        Intent intent = new Intent(Intent.ACTION_PICK);

        PendingIntent sentPI = PendingIntent.getBroadcast(this,0, new Intent(SENT),0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this,0, new Intent(DELIVERED),0);


        SmsManager sms = SmsManager.getDefault();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Ask for permision
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},1);
        }

        sms.sendTextMessage(phoneNumber,null,message,sentPI,deliveredPI);
    }

}
