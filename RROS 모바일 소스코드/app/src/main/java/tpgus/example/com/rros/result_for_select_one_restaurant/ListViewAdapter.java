package tpgus.example.com.rros.result_for_select_one_restaurant;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import tpgus.example.com.rros.R;
import tpgus.example.com.rros.Reservation;
import tpgus.example.com.rros.SelectActivity;

class ListviewAdapter extends BaseExpandableListAdapter {
    ArrayList<ParentItem> parentItems; //부모 리스트를 담을 배열
    ArrayList<ArrayList<ChildItem>> childItems; //자식 리스트를 담을 배열

    //각 리스트의 크기 반환
    @Override
    public int getGroupCount() {
        return parentItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childItems.get(groupPosition).size();
    }

    //리스트의 아이템 반환
    @Override
    public ParentItem getGroup(int groupPosition) {
        return parentItems.get(groupPosition);
    }

    @Override
    public ChildItem getChild(int groupPosition, int childPosition) {
        return childItems.get(groupPosition).get(childPosition);
    }

    //리스트 아이템의 id 반환
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //동일한 id가 항상 동일한 개체를 참조하는지 여부를 반환
    @Override
    public boolean hasStableIds() {
        return true;
    }

    //리스트 각각의 row에 view를 설정
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v = convertView;
        Context context = parent.getContext();

        //convertView가 비어있을 경우 xml파일을 inflate 해줌
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.parent_item, parent, false);
        }

        //View들은 반드시 아이템 레이아웃을 inflate한 뒤에 작성할 것
        ImageView arrow = (ImageView) v.findViewById(R.id.arrow);
        TextView type = (TextView) v.findViewById(R.id.name);


        //그룹 펼쳐짐 여부에 따라 아이콘 변경
        if (isExpanded)
            arrow.setImageResource(R.drawable.arrowup1);
        else
            arrow.setImageResource(R.drawable.arrowup);

        //리스트 아이템의 내용 설정
        type.setText(getGroup(groupPosition).getType());

        return v;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v = convertView;
        final Context context = parent.getContext();

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.child_item, parent, false);
        }

        TextView name = (TextView) v.findViewById(R.id.name);
        TextView price = (TextView) v.findViewById(R.id.price);
        ImageView image = (ImageView) v.findViewById(R.id.image);


        name.setText(getChild(groupPosition, childPosition).getName());
        price.setText(getChild(groupPosition, childPosition).getPrice());
        Glide.with(context).load(getChild(groupPosition, childPosition).getImage()).into(image);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //인텐트
                Intent intent = new Intent(context, SelectActivity.class);
                intent.putExtra("menuImage",getChild(groupPosition, childPosition).getImage());
                intent.putExtra("menuName",getChild(groupPosition, childPosition).getName());
                intent.putExtra("menuPrice",getChild(groupPosition, childPosition).getPrice());
                context.startActivity(intent);

            }
        });
        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    //리스트에 새로운 아이템을 추가
    public void addItem(int groupPosition, ChildItem item) {
        childItems.get(groupPosition).add(item);
    }

    //리스트 아이템을 삭제
    public void removeChild(int groupPosition, int childPosition) {
        childItems.get(groupPosition).remove(childPosition);
    }
}