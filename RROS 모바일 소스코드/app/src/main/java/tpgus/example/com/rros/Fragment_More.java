package tpgus.example.com.rros;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tpgus.example.com.rros.ListView_Search.getLicenseInfo;
import tpgus.example.com.rros.ListView_Search.list;

public class Fragment_More extends Fragment {
    After_Login activity;
    ImageView myImage;
    TextView myEmail;
    TextView myName;
    ArrayList<getLicenseInfo> list;
    Bitmap bitmap; // 비트맵 객체
    String url;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = (After_Login) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_more, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);


        myImage = (ImageView) rootView.findViewById(R.id.myImage);
        myName = (TextView) rootView.findViewById(R.id.myName);
        myEmail = (TextView) rootView.findViewById(R.id.myEmail);
        test();


        ViewGroup profile = (ViewGroup) rootView.findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        return rootView;

    }


    public void test() {
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);
        Call<ArrayList<getLicenseInfo>> call = remoteService.getRestaurant();
        call.enqueue(new Callback<ArrayList<getLicenseInfo>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<getLicenseInfo>> call, @NonNull Response<ArrayList<getLicenseInfo>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    myEmail.setText(list.get(0).Email);
                    myName.setText(list.get(0).CusName);
                    url = "http://192.168.43.161:3000/myImage/" + list.get(0).Image;
                    Glide.with(getActivity()).load(url).into(myImage);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<getLicenseInfo>> call, Throwable t) {
                Toast.makeText(getContext(), "실패" + t, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private static final int NUM_ITEMS = 3;


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
                return Fragment_More1.newInstance();
            } else if (position == 1) {
                return Fragment_More2.newInstance();
            } else {
                return Fragment_More3.newInstance();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if (position == 0) {
                return "다녀왔어요";
            } else if (position == 1) {
                return "가고싶어요";
            } else {
                return "쿠폰겟했음";
            }

        }


    }
}
