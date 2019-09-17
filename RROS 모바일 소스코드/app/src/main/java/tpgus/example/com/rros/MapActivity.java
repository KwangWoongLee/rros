package tpgus.example.com.rros;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private RecyclerView mVerticalView;
    private VerticalAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private GpsInfo gps;
    private GoogleMap mMap;
    private int ZOOM_LEVEL = 3;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private int MAX_ITEM_COUNT = 50;
    GetJSONObject task;
    JSONArray jj;
    URL url;
    String size;
    ArrayList<RecyclerViewItem> data;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        gps = new GpsInfo(MapActivity.this);
        Location me = new Location("me");
        me.setLatitude(gps.getLatitude());
        me.setLongitude(gps.getLongitude());
        Location other = new Location("other");
        float distance = me.distanceTo(other);


        mapFragment.getMapAsync(this);
        task = new GetJSONObject();
        try {
            url = new URL("https://openapi.gg.go.kr/Genrestrtlunch?KEY=41c752ab2353447084d1a529476eec16&TYPE=json&SIGUN_NM=%EC%84%B1%EB%82%A8%EC%8B%9C");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        try {
            JSONObject jsonObject = task.execute(url).get();
            JSONArray j = jsonObject.getJSONArray("Genrestrtlunch");
            JSONArray jh = j.getJSONObject(0).getJSONArray("head");
            jj = j.getJSONObject(1).getJSONArray("row");
            size = jh.getJSONObject(0).getString("list_total_count");
            String addr= jj.getJSONObject(1).getString("REFINE_ROADNM_ADDR");


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // RecyclerView binding
        mVerticalView = (RecyclerView) findViewById(R.id.vertical_list);

        // init Data
        data = new ArrayList<>();

        int i = 0;
        while (i < MAX_ITEM_COUNT) {
            try {
                other.setLatitude(Float.parseFloat(jj.getJSONObject(i).getString("REFINE_WGS84_LAT")));
                other.setLongitude(Float.parseFloat(jj.getJSONObject(i).getString("REFINE_WGS84_LOGT")));


                if(me.distanceTo(other)<=5000)
                    data.add(new RecyclerViewItem("",jj.getJSONObject(i).getString("BIZPLC_NM"),jj.getJSONObject(i).getString("SANITTN_BIZCOND_NM"),jj.getJSONObject(i).getString("REFINE_ROADNM_ADDR"),"","",other.getLatitude(),other.getLongitude()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            i++;
        }




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
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 기본값이 VERTICAL

        mVerticalView.setLayoutManager(mLayoutManager);
        mAdapter = new VerticalAdapter();
        mAdapter.setData(data);
        mVerticalView.setAdapter(mAdapter);
        final SnapHelper mSnapHelper = new PagerSnapHelper();
        mSnapHelper.attachToRecyclerView(mVerticalView);

        mVerticalView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //mLayoutManager.findFirstVisibleItemPosition()

                LatLng myLocation = new LatLng(data.get(mLayoutManager.findFirstVisibleItemPosition()).getX(),data.get(mLayoutManager.findFirstVisibleItemPosition()).getY());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,16));
            }
        });
        mMap.setOnMarkerClickListener(this);
        for(int i=0; i<data.size();i++){
            MarkerOptions makerOptions = new MarkerOptions();
            makerOptions
                    .position(new LatLng(data.get(i).getX(), data.get(i).getY()))
                    .title(data.get(i).getName()); // 타이틀.

            mMap.addMarker(makerOptions);
        }
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null ;
            }

            @Override
            public View getInfoContents(Marker marker) { // 마커 클릭시 원하는 창을 띄워주는 메소드
                View infoWindow = getLayoutInflater().inflate(R.layout.locationinfo, (FrameLayout)findViewById(R.id.map),false);


                return null;

            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {
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

        LatLng myLocation = new LatLng(gps.getLatitude(),gps.getLongitude());
        mMap.addMarker(new MarkerOptions().position(myLocation).title("Marker in MyLocation"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,16));
    }


    public void setLocation(LatLng latLng, String locname) {
        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in " + locname));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.setMinZoomPreference(ZOOM_LEVEL);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        return true;
    }

    private class GetJSONObject extends AsyncTask<URL, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(URL... urls){
            HttpURLConnection con =null;

            try{
                con= (HttpURLConnection)urls[0].openConnection();
                int response = con.getResponseCode();
                if(response == HttpURLConnection.HTTP_OK){
                    StringBuilder builder = new StringBuilder();
                    try(BufferedReader reader = new BufferedReader(
                            new InputStreamReader(con.getInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) !=null){
                            builder.append(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } return new JSONObject(builder.toString());
                } else {

                }
            } catch (Exception e){
                e.printStackTrace();
            } finally{
                con.disconnect();
            } return null;
        }


}
}
