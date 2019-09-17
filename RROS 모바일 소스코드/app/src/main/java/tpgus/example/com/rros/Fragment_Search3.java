package tpgus.example.com.rros;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tpgus.example.com.rros.ListView_Search.Adapter;
import tpgus.example.com.rros.ListView_Search.getLicenseInfo;
import tpgus.example.com.rros.ListView_Search.list;
import tpgus.example.com.rros.result_for_select_one_restaurant.getMenu;

public class Fragment_Search3 extends Fragment{
    int selectedItem;
    boolean is;
    String urlS;
    ListView listView;
    Adapter adapter;
    ArrayList<list> arrayList;
    ImageButton btn1, btn2, btn3;
    boolean compare=false;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private int MAX_ITEM_COUNT = 80;
    GetJSONObject task;
    JSONArray jj;
    URL url;
    String size;
    private GpsInfo gps;
    ArrayList<getLicenseInfo> list;
    ImageView myImage;
    Bitmap b;
    int radius;
    Location me;
    Location other;
    public static Fragment_Search3 newInstance() {
        Fragment_Search3 fragment = new Fragment_Search3();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.search_1, container, false);

        listView = (ListView)rootView.findViewById(R.id.listview);
        compareRestaurant(3000);
        //
//        sortRestaurant(1);
        btn1 = (ImageButton) rootView.findViewById(R.id.button1);
        btn2 = (ImageButton) rootView.findViewById(R.id.button2);
        btn3 = (ImageButton) rootView.findViewById(R.id.button3);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogRadioFilter();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogRadioRadius();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MapActivity.class);
                startActivity(intent);
            }
        });
        return rootView;

    }


    public void sortRestaurant(int orderType){
        RemoteService  remoteService = ServiceGenerator.createService(RemoteService.class);
        Call<ArrayList<getLicenseInfo>> call= remoteService.sortRestaurant(orderType);
        call.enqueue(new Callback<ArrayList<getLicenseInfo>>() {
            @Override
            public void onResponse(Call<ArrayList<getLicenseInfo>> call, Response<ArrayList<getLicenseInfo>> response) {
                if(response.isSuccessful()) {
                    list=response.body();

                    listView = (ListView)getView().findViewById(R.id.listview);
                    arrayList=new ArrayList<list>();

                    for(int i=0;i<list.size();i++){
                        String url = "http://192.168.43.161:3000/License/"+list.get(i).LicenseNo;

                        arrayList.add(new list(url,list.get(i).Name,list.get(i).type,list.get(i).address,list.get(i).review,list.get(i).LicenseNo));

                    }
                    adapter=new Adapter(getContext(),arrayList);
                    listView.setAdapter(adapter);


                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Intent intent = new Intent(getActivity(), getMenu.class);
//                            intent.putExtra("license",Integer.parseInt(list.get(position).license)); //list.get(i).license는 제이슨 객체 자체
                            intent.putExtra("license",arrayList.get(position).getLicense()); //arrayList.get(i).getLicense 는 위에서 json객체를 형변환해서
                            intent.putExtra("url",arrayList.get(position).getProfile_image());
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ArrayList<getLicenseInfo>> call, Throwable t) {

            }
        });
    }




    private void DialogRadioFilter(){
        final CharSequence[] option = {"리뷰순", "거리순", "평점순", "이름순"};
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(getContext());
        alt_bld.setIcon(R.drawable.common_google_signin_btn_icon_light_focused);
        alt_bld.setTitle("필터링 옵션을 선택하세요!");
        alt_bld.setSingleChoiceItems(option, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                selectedItem = item;
            }
        });
        alt_bld.setPositiveButton("완료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Collections.sort(arrayList, new Comparator<tpgus.example.com.rros.ListView_Search.list>() {
                    @Override
                    public int compare(tpgus.example.com.rros.ListView_Search.list o1, tpgus.example.com.rros.ListView_Search.list o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
                adapter=new Adapter(getContext(),arrayList);
                listView.setAdapter(adapter);

                dialog.cancel();

            }
        });
        AlertDialog alert = alt_bld.create();
        alert.show();
    }

    private void DialogRadioRadius(){
        final CharSequence[] option = {"500m", "1km", "3km", "5km"};
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(getContext());

        alt_bld.setIcon(R.drawable.common_google_signin_btn_icon_light_focused);
        alt_bld.setTitle("반경을 선택하세요!");
        alt_bld.setSingleChoiceItems(option, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item){
                    case 0:
                        radius = 500;
                        break;

                    case 1:
                        radius = 1000;
                        break;

                    case 2:
                        radius = 3000;
                        break;

                    case 3:
                        radius = 5000;
                        break;
                }
            }
        });
        alt_bld.setPositiveButton("완료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                compareRestaurant(radius);
            }
        });
        AlertDialog alert = alt_bld.create();
        alert.show();
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

    public void compareRestaurant(int radius){
        RemoteService  remoteService = ServiceGenerator.createService(RemoteService.class);
        gps = new GpsInfo(getContext());
        me = new Location("me");
        me.setLatitude(gps.getLatitude());
        me.setLongitude(gps.getLongitude());
        other = new Location("other");
        float distance = me.distanceTo(other);
        task = new GetJSONObject();
        try {
            url = new URL("https://openapi.gg.go.kr/Genrestrtchifood?KEY=41c752ab2353447084d1a529476eec16&TYPE=json&SIGUN_NM=%EC%84%B1%EB%82%A8%EC%8B%9C");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        try {
            JSONObject jsonObject = task.execute(url).get();
            JSONArray j = jsonObject.getJSONArray("Genrestrtchifood");
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

        arrayList=new ArrayList<list>();

        int i = 0;
        while (i < MAX_ITEM_COUNT) {
            try {
                other.setLatitude(Float.parseFloat(jj.getJSONObject(i).getString("REFINE_WGS84_LAT")));
                other.setLongitude(Float.parseFloat(jj.getJSONObject(i).getString("REFINE_WGS84_LOGT")));


                if(me.distanceTo(other)<=radius) {

                    Call<ArrayList<getLicenseInfo>> call= remoteService.compareRestaurant(jj.getJSONObject(i).getString("BIZPLC_NM"));
                    final int final_i = i;
                    call.enqueue(new Callback<ArrayList<getLicenseInfo>>() {

                        @Override
                        public void onResponse(Call<ArrayList<getLicenseInfo>> call, Response<ArrayList<getLicenseInfo>> response) {
                            if(response.isSuccessful()) {
                                list=response.body();
                                try {
                                    arrayList.add(new list("http://rros.ap-northeast-2.elasticbeanstalk.com/License/"+list.get(0).LicenseNo,list.get(0).Name,list.get(0).type,jj.getJSONObject(final_i).getString("REFINE_ROADNM_ADDR"),list.get(0).review,list.get(0).LicenseNo));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            if (response.code()==555)
                                try {
                                    arrayList.add(new list("http://rros.ap-northeast-2.elasticbeanstalk.com/License/0", jj.getJSONObject(final_i).getString("BIZPLC_NM"), "기타", jj.getJSONObject(final_i).getString("REFINE_ROADNM_ADDR"), "존재하는 리뷰가 없습니다", 1));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            adapter=new Adapter(getContext(),arrayList);
                            listView.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<ArrayList<getLicenseInfo>> call, Throwable t) {
                            Toast.makeText(getActivity(),""+t,Toast.LENGTH_LONG).show();
                        }
                    });


// data.add(new RecyclerViewItem("",jj.getJSONObject(i).getString("BIZPLC_NM"),jj.getJSONObject(i).getString("SANITTN_BIZCOND_NM"),jj.getJSONObject(i).getString("REFINE_ROADNM_ADDR"),"","",other.getLatitude(),other.getLongitude()));
                }} catch (JSONException e) {
                e.printStackTrace();
            }
            i++;
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), getMenu.class);
//                            intent.putExtra("license",Integer.parseInt(list.get(position).license)); //list.get(i).license는 제이슨 객체 자체
                intent.putExtra("license",arrayList.get(position).getLicense()); //arrayList.get(i).getLicense 는 위에서 json객체를 형변환해서
                intent.putExtra("url",arrayList.get(position).getProfile_image());
                startActivity(intent);
            }
        });



    }

}