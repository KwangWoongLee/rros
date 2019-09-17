package tpgus.example.com.rros.ListView_Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import tpgus.example.com.rros.Fragment_Map;
import tpgus.example.com.rros.R;

public class Adapter extends BaseAdapter {

    Context context;
    ArrayList<list> arrayList;
    TextView review;
    TextView name;
    TextView addr;
    TextView type;
    TextView info;
    ImageView profile;
    Fragment_Map map;

    public Adapter(Context context, ArrayList<list> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() { //리스트 뷰가 몇개의 아이템을 갖고 있는가?
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int position) { //현재 어떤 아이템인가?
        return this.arrayList.get(position);
    }

    @Override
    public long getItemId(int position) { //현재 포지션
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//xml과 연결하고 화면에 표시하는 부분 여기서 반복문을 이용해 반복생성


            convertView=LayoutInflater.from(context).inflate(R.layout.homelist_item,null);
            name=(TextView)convertView.findViewById(R.id.name);
            addr=(TextView)convertView.findViewById(R.id.MenuInfo);
            type=(TextView)convertView.findViewById(R.id.LicenseType);
            review=(TextView)convertView.findViewById(R.id.LicenseReview);
            profile=(ImageView)convertView.findViewById(R.id.image);

            name.setText(arrayList.get(position).getName());
            type.setText(arrayList.get(position).getType());
            addr.setText(arrayList.get(position).getAddr());
            review.setText(arrayList.get(position).getReview());
            Glide.with(context).load(arrayList.get(position).getProfile_image()).into(profile);

        return convertView;
    }
}

