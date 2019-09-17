package tpgus.example.com.rros;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResActivity extends AppCompatActivity {

    TextView tv;

    ArrayList<Result_Search_Restaurant> list ;

    String name;
    private ArrayList<ResListitem> mArrayList = new ArrayList<>();

    private RecyclerView mRecyclerView1;

    private CustomContactAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res);
        Intent data_intent = getIntent();
        name=data_intent.getStringExtra("restaurant_name");

        tv=(TextView)findViewById(R.id.textView);

        request(name);

        mRecyclerView1 = findViewById(R.id.recyclerView);

        final Intent intent=new Intent(ResActivity.this,ResInfo.class);

        /*........ The new Line to be added .........*/
        mAdapter = new CustomContactAdapter(mArrayList, new OnRecyclerClickListener() {
            @Override
            public void onRecyclerViewItemClicked(int position, int id) {
                //Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_SHORT).show();
                intent.putExtra("LicenseNo",list.get(position).LicenseNo);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });

        mRecyclerView1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView1.setItemAnimator( new DefaultItemAnimator());
        mRecyclerView1.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView1.setAdapter(mAdapter);//recyclerview에 adapter할당

    }

    private void prepareData(String name, String info, String _type) {
        ResListitem contact = null;
        contact = new ResListitem(name,info,_type);
        mArrayList.add(contact);
        mAdapter.notifyDataSetChanged();//데이터 변경된거 알림
    }


    private void request(String name) {

        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<ArrayList<Result_Search_Restaurant>> call = remoteService.result_search(name);

        call.enqueue(new Callback<ArrayList<Result_Search_Restaurant>>() {

            @Override
            public void onResponse(Call<ArrayList<Result_Search_Restaurant>> call, Response<ArrayList<Result_Search_Restaurant>> response) {

                list = response.body();

                 if (response.isSuccessful()) {

                    for(int i=0;i<list.size();i++) {
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Result_Search_Restaurant>> call, Throwable t) {

            }



        });
    }




}