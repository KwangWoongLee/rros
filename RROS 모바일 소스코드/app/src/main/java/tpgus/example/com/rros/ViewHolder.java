package tpgus.example.com.rros;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

class ViewHolder extends RecyclerView.ViewHolder {

    TextView Name;
    TextView Type;
    ImageView Image;
    TextView Addr;
    TextView Review;

    public ViewHolder(View itemView) {
        super(itemView);

        Image = (ImageView) itemView.findViewById(R.id.image);
        Name = (TextView) itemView.findViewById(R.id.name);
        Type = (TextView) itemView.findViewById(R.id.LicenseType);
        Addr = (TextView) itemView.findViewById(R.id.MenuInfo);
        Review = (TextView) itemView.findViewById(R.id.LicenseReview);


    }
}
