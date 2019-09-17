package tpgus.example.com.rros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResInfo extends AppCompatActivity {

    String name;
    int licenseNo;
    ArrayList<FoodInfoItem> list;
    Intent intent;

    private ArrayList<menuListItem> mArrayList = new ArrayList<>();
    private RecyclerView mRecyclerView1;

    private MenuAdapter mAdapter;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_info);

        Intent data_intent = getIntent();
        licenseNo = data_intent.getIntExtra("LicenseNo", 0);
        name=data_intent.getStringExtra("name");
        request(licenseNo);


        tv=(TextView)findViewById(R.id.resname_textview);
        tv.setText(name);

        mRecyclerView1 = findViewById(R.id.recyclerView);

        // final Intent intent=new Intent(ResInfo.this,menuListItem.class);

        /*........ The new Line to be added .........*/
        mAdapter = new MenuAdapter(mArrayList, new OnMenuClickListener() {
            @Override
            public void onMenuViewItemClicked(int position, int id) {
                Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();

            }
        });

        mRecyclerView1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView1.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView1.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView1.setAdapter(mAdapter);//recyclerview에 adapter할당

        final Button btn = (Button) findViewById(R.id.resbutton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(btn.getContext(), Reservation.class);
                intent.putExtra("name",name);
                intent.putExtra("licenseNo",licenseNo);
                startActivity(intent);
                finish();
            }
        });

    }

    private void prepareData(String name, String price, String menuInfo) {
        menuListItem contact = null;
        contact = new menuListItem(name, price,menuInfo);
        mArrayList.add(contact);
        mAdapter.notifyDataSetChanged();//데이터 변경된거 알림
    }


    private void request(int licenseNo) {

        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<ArrayList<FoodInfoItem>> call = remoteService.listFoodInfo(licenseNo);

        call.enqueue(new Callback<ArrayList<FoodInfoItem>>() {

            @Override
            public void onResponse(Call<ArrayList<FoodInfoItem>> call, Response<ArrayList<FoodInfoItem>> response) {

                list = response.body();
                if (response.isSuccessful()) {

                    for (int i = 0; i < list.size(); i++) {
                        prepareData(list.get(i).menuName, list.get(i).price, list.get(i).menuInfo);

                    }
                }

            }

            @Override
            public void onFailure(Call<ArrayList<FoodInfoItem>> call, Throwable t) {

            }
        });
    }
}
