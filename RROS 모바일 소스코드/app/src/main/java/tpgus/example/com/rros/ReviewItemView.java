package tpgus.example.com.rros;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReviewItemView extends LinearLayout {
    TextView Name;
    TextView Type;
    ImageView Image;
    TextView Address;
    TextView Review;

    public ReviewItemView(Context context) {
        super(context);

        init(context);
    }

    public ReviewItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public void init(Context context){
        //리스트 뷰에 들어갈 각 아이템은 하나의 뷰(이 뷰는 뷰 그룹)는 일종의 부분화면이므로 아이템 레이아웃을 인플레이션 한 후 설정해야함.
        //인플레이션 후
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.homelist_item, this,true);
        //설정
        Name=(TextView)findViewById(R.id.name);
        Type=(TextView)findViewById(R.id.LicenseType);
        Address=(TextView)findViewById(R.id.MenuInfo);
        Review=(TextView)findViewById(R.id.LicenseReview);
        Image=(ImageView)findViewById(R.id.image);
    }

    public void setName(String name) {Name.setText(name); }
    public void setAddr(String addr) {
        Address.setText(addr);
    }
    public void setType(String type) {
        Type.setText(type);
    }
    public void setReview(String review) {
        Review.setText(review);
    }

    public void setImage(int image) {
        Image.setImageResource(image);
    }
}
