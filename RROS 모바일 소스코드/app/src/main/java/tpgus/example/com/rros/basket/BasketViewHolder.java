package tpgus.example.com.rros.basket;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tpgus.example.com.rros.R;

public class BasketViewHolder extends RecyclerView.ViewHolder {

    TextView Name;
    TextView Count;
    ImageView Image;
    TextView Price;

    public BasketViewHolder(View itemView) {
        super(itemView);

        Image = (ImageView) itemView.findViewById(R.id.image);
        Name = (TextView) itemView.findViewById(R.id.menuname);
        Count = (TextView) itemView.findViewById(R.id.count);
    }
}
