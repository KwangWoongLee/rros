package tpgus.example.com.rros.result_for_select_one_restaurant;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tpgus.example.com.rros.FoodInfoItem;
import tpgus.example.com.rros.R;
import tpgus.example.com.rros.RemoteService;
import tpgus.example.com.rros.ServiceGenerator;
import tpgus.example.com.rros.basket.BasketAdapter;

public class Fragment_menu1 extends Fragment
{
    public int license;
    private RecyclerView mVerticalView;
    private MenuAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<MenuList> mList;
    ExpandableListView listView;
    ListviewAdapter adapter;
    ArrayList<ParentItem> groupList = new ArrayList<>(); //부모 리스트
    ArrayList<ArrayList<ChildItem>> childList = new ArrayList<>(); //자식 리스트
    ArrayList<ArrayList<ChildItem>> typeArray = new ArrayList<>();
    private BasketAdapter bAdapter;
    private RecyclerView bVerticalView;
    private LinearLayoutManager bLayoutManager;
    ArrayList<list> arrayList;
    ArrayList<FoodInfoItem> foodInfoItems;


    public static Fragment_menu1 newInstance(){
        Bundle args = new Bundle();
        Fragment_menu1 fragment =new Fragment_menu1();
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_menu1()
    {
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Bundle bundle = getArguments();

        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.fragment_menu1, container, false);
        listView = (ExpandableListView) layout.findViewById(R.id.expandable_list);
        mVerticalView = (RecyclerView) layout.findViewById(R.id.basketview);
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // 기본값이 VERTICAL

        mVerticalView.setLayoutManager(mLayoutManager);
        mAdapter = new MenuAdapter();

        arrayList = new ArrayList<list>();
        request(bundle.getInt("license"));



        return layout;
    }


    //리스트 초기화 함수
    public void setListItems(int size, List<String> typelist) {
        groupList.clear();
        childList.clear();

        childList.addAll(typeArray);

        //부모 리스트 내용 추가
        for (int i = 0; i <= size-1; i++) {
            groupList.add(new ParentItem(typelist.get(i)));
        }

        adapter.notifyDataSetChanged();
    }

    private void request(final int license) {

        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<ArrayList<FoodInfoItem>> call = remoteService.listFoodInfo(license);

        call.enqueue(new Callback<ArrayList<FoodInfoItem>>() {

            @Override
            public void onResponse(Call<ArrayList<FoodInfoItem>> call, Response<ArrayList<FoodInfoItem>> response) {


                if (response.isSuccessful()) {
                    foodInfoItems = response.body();
                    List<String> typelist = new ArrayList<String>();


                    mList = new ArrayList<MenuList>();
                    for (int i = 0; i < foodInfoItems.size(); i++) {
                        String url = "http://192.168.43.161:3000/Menu/" + foodInfoItems.get(i).MenuNo;
                        arrayList.add(new list(url, foodInfoItems.get(i).Name, foodInfoItems.get(i).Price, foodInfoItems.get(i).Info,foodInfoItems.get(i).type));

                        mList.add(new MenuList(arrayList.get(i).getFood_image(),arrayList.get(i).getName(),arrayList.get(i).getPrice()));

                        typelist.add(i,foodInfoItems.get(i).type);

                    }
                    for (int i = 0; i < typelist.size(); i++) {
                        for (int j = 0; j < typelist.size(); j++) {
                            if (i == j) {
                            } else if (typelist.get(j).equals(typelist.get(i))) {
                                typelist.remove(j);
                            }
                        }
                    }


                    for(int i=0;i<typelist.size();i++){
                        typeArray.add(i,new ArrayList<ChildItem>());
                        for(int j=0; j<mList.size();j++){
                            if(arrayList.get(j).getType().compareTo(typelist.get(i))==0){
                                typeArray.get(i).add(new ChildItem(arrayList.get(j).getName(),arrayList.get(j).getPrice(),arrayList.get(j).getFood_image()));
                            }
                        }


                    }

                    mAdapter.setLicenseNo(license);
                    mAdapter.setData(mList);
                    mVerticalView.setAdapter(mAdapter);
                    adapter = new ListviewAdapter();
                    adapter.parentItems = groupList;
                    adapter.childItems = childList;
                    setListItems(typelist.size(),typelist);
                    listView.setAdapter(adapter);
                    listView.setGroupIndicator(null); //리스트뷰 기본 아이콘 표시 여부



                    //리스트 클릭시 지출 항목이 토스트로 나타난다
                    listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                        @Override
                        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                            return true;
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<ArrayList<FoodInfoItem>> call, Throwable t) {

            }
        });
    }
}
