package tpgus.example.com.rros.result_for_select_one_restaurant;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tpgus.example.com.rros.R;

public class MenuViewHolder extends RecyclerView.ViewHolder {

    TextView Name;
    TextView Info;
    ImageView Image;
    TextView Price;

    public MenuViewHolder(View itemView) {
        super(itemView);

        Image = (ImageView) itemView.findViewById(R.id.image);
        Name = (TextView) itemView.findViewById(R.id.name);
        Price = (TextView) itemView.findViewById(R.id.price);

    }
}
