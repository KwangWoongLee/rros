package tpgus.example.com.rros.result_for_select_one_restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tpgus.example.com.rros.FoodInfoItem;
import tpgus.example.com.rros.MyApp;
import tpgus.example.com.rros.R;
import tpgus.example.com.rros.RemoteService;
import tpgus.example.com.rros.Reservation;
import tpgus.example.com.rros.SelectActivity;
import tpgus.example.com.rros.ServiceGenerator;
import tpgus.example.com.rros.basket.BasketAdapter;
import tpgus.example.com.rros.basket.basketList;

public class getMenu2 extends AppCompatActivity {
    DrawerLayout drawerLayout;
    static int license;
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
    Button res;
    Bundle bundle;
    private TabLayout tabLayout;
    int check =0;
    NestedScrollView scrollView;
    private ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    View drawer_layout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation2);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_layout1 = (View) findViewById(R.id.drawer_layout1);
        res = (Button) findViewById(R.id.btn_res);
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Reservation.class);
                intent.putExtra("size",b.size());

                for(int i=0;i<b.size();i++){
                    intent.putExtra("basket"+i+"name",b.get(i).getName());
                    intent.putExtra("basket"+i+"image",b.get(i).getFood_image());
                    intent.putExtra("basket"+i+"count",b.get(i).getCount());
                    intent.putExtra("basket"+i+"price",b.get(i).getPrice());

                }

                intent.putExtra("license",license);
                startActivity(intent);
            }
        });
        bVerticalView = (RecyclerView) findViewById(R.id.basketview);
        bLayoutManager = new LinearLayoutManager(getApplicationContext());

        b = ((MyApp)getApplicationContext()).getBasketLists();
        ((MyApp)getApplicationContext()).setBasketLists(b);
        bVerticalView.setLayoutManager(bLayoutManager);
        bAdapter = new BasketAdapter();
        bAdapter.getMenu2(this);
        bAdapter.setData(b);
        bVerticalView.setAdapter(bAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawer_layout1);
            }
        });

        scrollView = (NestedScrollView) findViewById(R.id.scroll);
        scrollView.setFillViewport(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = findViewById(R.id.name);

        Intent data_intent = getIntent();
        license = data_intent.getIntExtra("license", 0);

        String url = data_intent.getStringExtra("url");
        String intent_name = data_intent.getStringExtra("name");
        ImageView imageView = (ImageView) findViewById(R.id.drawImage);
        Glide.with(getApplicationContext()).load(url).into(imageView);
//        name.setText(intent_name);
        setTitle(intent_name);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        b= ((MyApp)getApplicationContext()).getBasketLists();
        bAdapter.setData(b);
        bVerticalView.setAdapter(bAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==1001)
        {    b.add(new basketList(data.getStringExtra("menuImage"),data.getStringExtra("menuName"),data.getStringExtra("count"),data.getStringExtra("price")));

        bAdapter.setData(b);


        bVerticalView.setAdapter(bAdapter);
    }
    }
    public static class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private static final int NUM_ITEMS = 2;


        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                Bundle bundle = new Bundle();
                bundle.putInt("license",license);
                Fragment_menu1 a =Fragment_menu1.newInstance();
                a.setArguments(bundle);
                return a;
            } else{
                Bundle bundle = new Bundle();
                bundle.putInt("license",license);
                Fragment_menu3 a =Fragment_menu3.newInstance();
                a.setArguments(bundle);
                return a;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if (position == 0) {
                return "메뉴";
            } else{
                return "정보";
            }

        }


    }

    public void removeItem(int position){
        b.remove(position);
    }

}