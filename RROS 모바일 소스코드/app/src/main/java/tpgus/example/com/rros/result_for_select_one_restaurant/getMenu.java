package tpgus.example.com.rros.result_for_select_one_restaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tpgus.example.com.rros.FoodInfoItem;
import tpgus.example.com.rros.R;
import tpgus.example.com.rros.RemoteService;
import tpgus.example.com.rros.Reservation;
import tpgus.example.com.rros.ServiceGenerator;
import tpgus.example.com.rros.basket.BasketAdapter;
import tpgus.example.com.rros.basket.basketList;

public class getMenu extends AppCompatActivity {

    int license;
    private BasketAdapter bAdapter;
    private RecyclerView bVerticalView;
    private LinearLayoutManager bLayoutManager;
    ArrayList<list> arrayList;
    ArrayList<FoodInfoItem> foodInfoItems;
    ArrayList<basketList> b;
    ListView listView;
    MenuAdapter adapter;
    LinearLayout basketLayout;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation);
        name = findViewById(R.id.name);
        bVerticalView = (RecyclerView) findViewById(R.id.basket);
        arrayList=new ArrayList<list>();
        bLayoutManager = new LinearLayoutManager(getApplicationContext());
        bLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        bVerticalView.setLayoutManager(bLayoutManager);
        bAdapter = new BasketAdapter();
//        bAdapter.getMenu2(this);
        b = new ArrayList<basketList>();
        bAdapter.setData(b);
        bVerticalView.setAdapter(bAdapter);


        Intent data_intent = getIntent();
        license = data_intent.getIntExtra("license",0);
        String url = data_intent.getStringExtra("url");
        String intent_name = data_intent.getStringExtra("name");
        request(license);
        ImageView imageView = (ImageView) findViewById(R.id.image);
        Glide.with(getApplicationContext()).load(url).into(imageView);
        name.setText(intent_name);
        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Reservation.class);
                for(int i=0;i<b.size();i++){
                    intent.putExtra("menuimage_"+i,b.get(i).getFood_image());
                    intent.putExtra("menutype_"+i,b.get(i).getType());
                }
                intent.putExtra("size",b.size());
                intent.putExtra("license",license);
                startActivity(intent);
                finish();
            }
        });

    }

    private void request(int license) {

        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<ArrayList<FoodInfoItem>> call = remoteService.listFoodInfo(license);

        call.enqueue(new Callback<ArrayList<FoodInfoItem>>() {

            @Override
            public void onResponse(Call<ArrayList<FoodInfoItem>> call, Response<ArrayList<FoodInfoItem>> response) {


                if (response.isSuccessful()) {
                    foodInfoItems = response.body();


                    for (int i = 0; i < foodInfoItems.size(); i++) {
                        String url = "http://192.168.43.161:3000/Menu/"+foodInfoItems.get(i).MenuNo;
                        arrayList.add(new list(url,foodInfoItems.get(i).Name,foodInfoItems.get(i).Price,foodInfoItems.get(i).Info,foodInfoItems.get(i).type));
                    }

                    listView = (ListView)findViewById(R.id.basketview);
//                    adapter=new MenuAdapter(getApplicationContext(),arrayList);
//                    listView.setAdapter(adapter);


                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            b.add(0,new basketList("http://192.168.43.161:3000/Menu/"+foodInfoItems.get(position).MenuNo));
                            bAdapter.notifyDataSetChanged();

                }
            });


                }

            }

            @Override
            public void onFailure(Call<ArrayList<FoodInfoItem>> call, Throwable t) {

            }
        });
    }

    public void removeItem(int position){
        b.remove(position);
    }




}
