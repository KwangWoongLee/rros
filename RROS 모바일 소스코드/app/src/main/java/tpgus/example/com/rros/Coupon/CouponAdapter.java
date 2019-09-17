package tpgus.example.com.rros.Coupon;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tpgus.example.com.rros.Fragment_Coupon;
import tpgus.example.com.rros.R;
import tpgus.example.com.rros.RecyclerViewItem;
import tpgus.example.com.rros.RemoteService;
import tpgus.example.com.rros.ServiceGenerator;
import tpgus.example.com.rros.positionInfo;

public class CouponAdapter extends RecyclerView.Adapter<CouponViewHolder> {
    Context context;
    private ArrayList<CouponItem> Item;
    CouponViewHolder holder;
    SlidingUpPanelLayout m;
    Fragment_Coupon fragment_coupon;
    GoogleMap map;
    AdapterCallback adapterCallback;
    LatLng myLocation;
    double x,y;

    public CouponAdapter(AdapterCallback callback) {
        this.adapterCallback = callback;
    }

    private ListAdapterListener mListener;

    public interface ListAdapterListener { // create an interface
        void onClickAtOKButton(int position,LatLng myLocation); // create callback function
    }

    public void setData(ArrayList<CouponItem> list){
        Item = list;
    }
    public void getLayout(SlidingUpPanelLayout m){
        this.m =m;
    }

    public CouponAdapter(Fragment_Coupon f, GoogleMap map, ListAdapterListener l){
        this.fragment_coupon = f;
        this.map = map;
        this.mListener = l;
    }
    @NonNull
    @Override
    public CouponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

// 사용할 아이템의 뷰를 생성해준다.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coupon_item2, parent, false);
        context = parent.getContext();
        holder = new CouponViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(final CouponViewHolder holder, final int position) {
        CouponItem data = Item.get(position);
        holder.ContentTitle.setText(Item.get(position).getContent());
        holder.Due.setText("유효기간 : " +Item.get(position).getLife());
        holder.Name.setText(Item.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fragment_coupon.setView(Item.get(position).getCall(),Item.get(position).getName());
                m.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);

                requestPosition(Integer.parseInt(Item.get(position).getFk_LienseNo_coupon()));


            }
        });
    }

    @Override
    public int getItemCount() {
        return Item.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void requestPosition(final int position){
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);
        Call<ArrayList<positionInfo>> call=remoteService.getPosition(position);

        call.enqueue(new Callback<ArrayList<positionInfo>>() {
            @Override
            public void onResponse(Call<ArrayList<positionInfo>> call, Response<ArrayList<positionInfo>> response) {

                if(response.isSuccessful()){
                    ArrayList<positionInfo> plist;
                    plist=response.body();
                    x= plist.get(0).LicenseX;
                    y = plist.get(0).LicenseY;
                    mListener.onClickAtOKButton(position,new LatLng(x,y));



                }
            }
            @Override
            public void onFailure(Call<ArrayList<positionInfo>> call, Throwable t) {

            }

        });

    }
}
