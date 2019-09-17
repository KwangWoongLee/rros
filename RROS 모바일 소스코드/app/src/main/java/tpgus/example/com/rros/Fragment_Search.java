package tpgus.example.com.rros;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

public class Fragment_Search extends Fragment {

    After_Login activity;
    ViewPager viewPager;
    Fragment_Search.ViewPagerAdapter viewPagerAdapter;



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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        viewPagerAdapter = new Fragment_Search.ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);


        return rootView;

    }
    public static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private static final int NUM_ITEMS = 5;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return Fragment_Search1.newInstance();
            }
            else if(position == 1){
                return Fragment_Search2.newInstance();
            } else if (position==2){
                return Fragment_Search3.newInstance();
            } else if (position==3){
                return Fragment_Search4.newInstance();
            }else
                return Fragment_Search5.newInstance();

        }

        @Override
        public CharSequence getPageTitle(int position) {

            if(position == 0){
                return "일식";
            }
            else if(position == 1){
                return "카페";
            } else if (position==2){
                return "중식";
            } else if (position==3){
                return "즉석음식";
            } else
                return "분식";

        }
    }



}

