package tpgus.example.com.rros.Coupon;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import tpgus.example.com.rros.R;

public class CouponViewHolder extends RecyclerView.ViewHolder {

    TextView Name;
    TextView ContentTitle;
    TextView Condition;
    TextView Due;
    int position;
    TextView CouponCall;
    TextView CouponName;
    public CouponViewHolder(View itemView) {
        super(itemView);

        Name = (TextView) itemView.findViewById(R.id.cupon_name);
        ContentTitle = (TextView) itemView.findViewById(R.id.discount_contents);
        Due = (TextView) itemView.findViewById(R.id.due_date);
        Condition=(TextView) itemView.findViewById(R.id.condition_content);
        CouponName = (TextView)itemView.findViewById(R.id.CouponName);

        CouponCall = (TextView) itemView.findViewById(R.id.CouponCall);
        position = getAdapterPosition();
    }
}