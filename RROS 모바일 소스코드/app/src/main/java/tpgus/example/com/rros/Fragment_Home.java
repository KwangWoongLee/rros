package tpgus.example.com.rros;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ViewFlipper;

import java.util.ArrayList;

public class Fragment_Home extends Fragment {

    ArrayList<ReviewItem> c1List,c2List,c3List;
    ListView c1ListView,c2ListView,c3ListView;
    ReviewItemAdapter c1Adapter,c2Adapter,c3Adapter;
    ViewFlipper flipper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home,container,false);

        flipper= (ViewFlipper) rootView.findViewById(R.id.flipper);
        Animation showIn= AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
        flipper.setInAnimation(showIn);
        flipper.setOutAnimation(getActivity(), android.R.anim.slide_out_right);

        flipper.setFlipInterval(2000);//플리핑 간격(1000ms)
        flipper.startFlipping();


        c1List = new ArrayList<ReviewItem>();
        c2List = new ArrayList<ReviewItem>();
        c3List = new ArrayList<ReviewItem>();
        c1ListView= (ListView) rootView.findViewById(R.id.c1listView);
        c2ListView= (ListView) rootView.findViewById(R.id.c2listView);
        c3ListView= (ListView) rootView.findViewById(R.id.c3listView);
        c1Adapter =  new ReviewItemAdapter();
        c2Adapter =  new ReviewItemAdapter();
        c3Adapter =  new ReviewItemAdapter();
        c1Adapter.addItem(new ReviewItem("아리아떼","카페","4.3 `분위기 좋은 곳'","역삼동 668-3 청송빌딩",R.drawable.m1));
        c1Adapter.addItem(new ReviewItem("아그레아블","카페","4.0 2층짜리 넓은","서울 강남구 역삼로 110 태양21 ",R.drawable.m2));
        c1Adapter.addItem(new ReviewItem("로랑","카페","4.3 강남의 편한곳에있는곳","서울 강남구 봉은사로2길 24 ",R.drawable.lorang));
        c1Adapter.addItem(new ReviewItem("마라훠궈","중식","2.5 너무매워요","주소",R.drawable.mara));


        c2Adapter.addItem(new ReviewItem("워너비박스","한식","3.8 배달가능","서울 구로구 디지털로33길 11 ",R.drawable.m3));
        c2Adapter.addItem(new ReviewItem("호토모토","일식","4.0 일본 정통 도시락","서울 구로구 디지털로26길 123 ",R.drawable.m4));
        c2Adapter.addItem(new ReviewItem("로랑","카페","4.3 강남의 편한곳에있는곳","서울 강남구 봉은사로2길 24 ",R.drawable.lorang));
        c2Adapter.addItem(new ReviewItem("아리아떼","카페","4.3 `분위기 좋은 곳'","역삼동 668-3 청송빌딩",R.drawable.m1));


        c3Adapter.addItem(new ReviewItem("진진차이나&포차","주점","4.4 분위기 좋은 포차","인천 남동구 경인로 520 탑프라자",R.drawable.m6));
        c3Adapter.addItem(new ReviewItem("린치타이","주점","3.8 중식 안주가 맛있는","서울 강서구 방화대로47가길 41 ",R.drawable.m7));
        c3Adapter.addItem(new ReviewItem("아그레아블","카페","4.0 2층짜리 넓은","서울 강남구 역삼로 110 태양21 ",R.drawable.m2));
        c3Adapter.addItem(new ReviewItem("로랑","카페","4.3 강남의 편한곳에있는곳","서울 강남구 봉은사로2길 24 ",R.drawable.lorang));
        c3Adapter.addItem(new ReviewItem("아리아떼","카페","4.3 `분위기 좋은 곳'","역삼동 668-3 청송빌딩",R.drawable.m1));

        c1ListView.setAdapter(c1Adapter);
        c2ListView.setAdapter(c2Adapter);
        c3ListView.setAdapter(c3Adapter);



        c1Adapter.notifyDataSetChanged();
        c2Adapter.notifyDataSetChanged();
        c3Adapter.notifyDataSetChanged();



        return rootView;
    }


    class ReviewItemAdapter extends BaseAdapter {
        ArrayList<ReviewItem> items=new ArrayList<ReviewItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        public void addItem(ReviewItem item){
            items.add(item);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        //각 아이템을 표시하는 함수
        public View getView(int position, View convertView, ViewGroup parent) {
            //MyItemView view = new MyItemView(getApplicationContext());
            //리스트 재사용을 위해서
            ReviewItemView view=null;
            if(convertView==null){
                view = new ReviewItemView(getActivity());
            }else{
                view=(ReviewItemView) convertView;
            }
            ReviewItem item=items.get(position);
            view.setAddr(item.getAddr());
            view.setImage(item.getImg());
            view.setName(item.getResname());
            view.setType(item.getType());
            view.setReview(item.getReview());
            return view;
        }
    }
}
