package tpgus.example.com.rros.result_for_select_one_restaurant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import tpgus.example.com.rros.R;
import tpgus.example.com.rros.Reservation;
import tpgus.example.com.rros.SelectActivity;
import tpgus.example.com.rros.basket.BasketViewHolder;
import tpgus.example.com.rros.basket.basketList;


public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {
    Context context;
    private ArrayList<MenuList> Item;
    int licenseNo;
    public void setLicenseNo(int l){
        licenseNo = l;
    }
    public void setData(ArrayList<MenuList> list){
        Item = list;
    }
    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

// 사용할 아이템의 뷰를 생성해준다.
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu1_item, parent, false);

        MenuViewHolder holder = new MenuViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MenuViewHolder holder, final int position) {
        MenuList data = Item.get(position);
        Glide.with(context).load(Item.get(position).getFood_image()).into(holder.Image);
        holder.Name.setText(Item.get(position).getName());
        holder.Price.setText(Item.get(position).getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SelectActivity.class);
                intent.putExtra("license",licenseNo);
                intent.putExtra("menuImage",Item.get(position).getFood_image());
                intent.putExtra("menuName",Item.get(position).getName());
                intent.putExtra("menuPrice",Item.get(position).getPrice());
                ((Activity) context).startActivityForResult(intent,1001);

            }
        });
    }

    @Override
    public int getItemCount() {

        return Item.size();
    }

}
