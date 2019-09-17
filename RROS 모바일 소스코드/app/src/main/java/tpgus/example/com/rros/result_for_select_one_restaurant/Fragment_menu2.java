package tpgus.example.com.rros.result_for_select_one_restaurant;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import tpgus.example.com.rros.R;

public class Fragment_menu2 extends Fragment
{
    public static Fragment_menu2 newInstance(){
        Bundle args = new Bundle();
        Fragment_menu2 fragment =new Fragment_menu2();
        fragment.setArguments(args);
        return fragment;
    }


    public Fragment_menu2()
    {
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.fragment_menu2, container, false);
        return layout;
    }
}
