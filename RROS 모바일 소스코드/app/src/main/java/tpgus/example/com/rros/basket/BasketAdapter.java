package tpgus.example.com.rros.basket;

import android.content.Context;
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
import tpgus.example.com.rros.result_for_select_one_restaurant.getMenu;
import tpgus.example.com.rros.result_for_select_one_restaurant.getMenu2;


public class BasketAdapter extends RecyclerView.Adapter<BasketViewHolder> {
    Context context;
    private ArrayList<basketList> Item;
    getMenu2 a;
    Reservation r;
    public void setData(ArrayList<basketList> list){
        Item = list;
    }
    public void getMenu2(getMenu2 a){ this.a = a;}
    public void getReservation(Reservation r){ this.r = r;}
    @NonNull
    @Override
    public BasketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

// 사용할 아이템의 뷰를 생성해준다.
        context = parent.getContext();

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.basket, parent, false);

        BasketViewHolder holder = new BasketViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final BasketViewHolder holder, final int position) {
        basketList data = Item.get(position);

        Glide.with(context).load(Item.get(position).getFood_image()).into(holder.Image);
        holder.Name.setText(Item.get(position).getName());
        holder.Count.setText("(수량 : " + Item.get(position).getCount()+ ")");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Item.size()>0) {
                    if(a!=null)
                    a.removeItem(position);
                    else{
                        r.sum -= Integer.parseInt(Item.get(position).getCount())*Integer.parseInt(Item.get(position).getPrice());
                        r.removeItem(position);

                        String price=Integer.toString(r.sum);
                        if(r.sum!=0)
                            r.total.setText(r.sum/1000+ "," + price.substring(price.length()-3, price.length()));
                        else
                            r.total.setText("0");
                    }
                    notifyItemRemoved(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {

        return Item.size();
    }

}
