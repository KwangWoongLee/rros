package tpgus.example.com.rros;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tpgus.example.com.rros.R;

class VerticalAdapter extends RecyclerView.Adapter<ViewHolder> {

    private ArrayList<RecyclerViewItem> Item;

    public void setData(ArrayList<RecyclerViewItem> list){
        Item = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

// 사용할 아이템의 뷰를 생성해준다.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.homelist_item, parent, false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecyclerViewItem data = Item.get(position);
        holder.Name.setText(Item.get(position).getName());
        holder.Addr.setText(Item.get(position).getAddr());
        holder.Type.setText(Item.get(position).getType());
        holder.Review.setText(Item.get(position).getReview());


    }

    @Override
    public int getItemCount() {
        return Item.size();
    }




}
