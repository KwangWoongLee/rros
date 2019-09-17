package tpgus.example.com.rros;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import noman.googleplaces.Place;

import noman.googleplaces.PlacesException;

import noman.googleplaces.PlacesListener;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tpgus.example.com.rros.Coupon.CouponAdapter;
import tpgus.example.com.rros.Coupon.CouponItem;
import tpgus.example.com.rros.Coupon.CouponViewHolder;
import tpgus.example.com.rros.ListView_Search.getLicenseInfo;
import tpgus.example.com.rros.ListView_Search.list;
import tpgus.example.com.rros.result_for_select_one_restaurant.getMenu;

import static android.support.constraint.Constraints.TAG;

public class Fragment_Coupon extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    public SlidingUpPanelLayout mLayout;
    private RecyclerView mVerticalView;
    private CouponAdapter mAdapter;
    TextView size;
    private LinearLayoutManager mLayoutManager;
    private int MAX_ITEM_COUNT = 50;
    ArrayList<positionInfo> plist;
    ArrayList<CouponItem> list;
    private GpsInfo gps;
    private GoogleMap mMap;
    public MapView mapView;
    private int ZOOM_LEVEL = 3;
    private static final int PERMISSION_REQUEST_CODE = 1;
    Fragment_Coupon f = this;
    public String a ="하이";
    JSONArray jj;
    LatLng myLocation;
    URL url;
    Bitmap b;
    String userID="lgu4821";
    ArrayList<positionInfo> xylist;
    MarkerOptions makerOptions;
    TextView Call;
    TextView Name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_coupon, container, false);
        // RecyclerView binding
        Call = (TextView)rootView.findViewById(R.id.CouponCall);
        Name = (TextView)rootView.findViewById(R.id.CouponName);
        mVerticalView = (RecyclerView) rootView.findViewById(R.id.coupon_list);
        gps = new GpsInfo(getContext());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        size= (TextView) rootView.findViewById(R.id.numOfCoupon);

        Location me = new Location("me");
        me.setLatitude(gps.getLatitude());
        me.setLongitude(gps.getLongitude());
        Location other = new Location("other");
        float distance = me.distanceTo(other);

        mapView = (MapView)rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this); // 비동기적 방식으로 구글 맵 실행



        list = new ArrayList<>();
        request();



        mLayout = (SlidingUpPanelLayout) rootView.findViewById(R.id.sliding_layout);

        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i(TAG, "onPanelStateChanged " + newState);
            }
        });
        mLayout.setAnchorPoint(0.6f);
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });


        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initializeLocation();
                }
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        xylist = new ArrayList<>();


        mMap.setOnMarkerClickListener(this);


        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null ;
            }

            @Override
            public View getInfoContents(Marker marker) { // 마커 클릭시 원하는 창을 띄워주는 메소드
//                View infoWindow = getLayoutInflater().inflate(R.layout.locationinfo, (FrameLayout)findViewById(R.id.map),false);


                return null;

            }
        });


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, PERMISSION_REQUEST_CODE);
            return;
        }
        initializeLocation();


    }

    @SuppressLint("MissingPermission")
    public void initializeLocation() {
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        myLocation = new LatLng(gps.getLatitude(),gps.getLongitude());
        mMap.addMarker(new MarkerOptions().position(myLocation).title("Marker in MyLocation"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,16));
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        return true;
    }

    private void request() {


        final RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);
        Call<ArrayList<CouponItem>> call = remoteService.reqCoupon(userID);

        call.enqueue(new Callback<ArrayList<CouponItem>>() {

            @Override
            public void onResponse(Call<ArrayList<CouponItem>> call, Response<ArrayList<CouponItem>> response) {
                if(response.isSuccessful()){

                    mLayoutManager = new LinearLayoutManager(getContext());
//                    mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 기본값이 VERTICAL

                    mVerticalView.setLayoutManager(mLayoutManager);
                    mAdapter = new CouponAdapter(f,mMap, new CouponAdapter.ListAdapterListener() {
                        @Override
                        public void onClickAtOKButton(int position,LatLng myLocation) {
                            mMap.addMarker(new MarkerOptions().position(myLocation).title("Marker in MyLocation"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,16));
                        }
                    });


                    ArrayList<CouponItem> data;
                    data = response.body();

                    for(int i=0; i<data.size();i++){
                        list.add(new CouponItem(data.get(i).getCall(),data.get(i).getFk_LienseNo_coupon(),data.get(i).getImage(),data.get(i).getCouponContent(),data.get(i).getCouponDate(),data.get(i).getName(),data.get(i).getCondition()));
                    }
                    size.setText(""+list.size());
                    mAdapter.getLayout(mLayout);
                    mAdapter.setData(list);
                    mVerticalView.setAdapter(mAdapter);



                }

            }

            @Override
            public void onFailure(Call<ArrayList<CouponItem>> call, Throwable t) {

            }
        });

    }

    public void setView(String call, String name){
        Call.setText(call);
        Name.setText(name);
    }

    public void setSize(String Size){
        size.setText(Size);
    }

}
