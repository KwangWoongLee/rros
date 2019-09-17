package tpgus.example.com.rros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import tpgus.example.com.rros.basket.basketList;
import tpgus.example.com.rros.result_for_select_one_restaurant.getMenu2;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SelectActivity extends AppCompatActivity {
    String menuImage="";
    String menuName ="";
    int license;
    int quantity = 1;
    TextView quan;
    TextView sum;
    String menuPrice;
    Button add, reserv;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ((MyApp)getApplicationContext()).setBasketLists(b);
    }

    ArrayList<basketList> b;
    int s= 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        add = (Button)findViewById(R.id.add);
        reserv = (Button)findViewById(R.id.reserv);
        reserv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Reservation.class);
                intent.putExtra("license",license);

                b.add(new basketList(menuImage,menuName,quantity+"",menuPrice));
                ((MyApp)getApplicationContext()).setBasketLists(b);
                finish();
                startActivity(intent);

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),getMenu2.class);
                intent.putExtra("menuImage",menuImage);
                intent.putExtra("menuName",menuName);
                intent.putExtra("count",quantity+"");
                intent.putExtra("price",menuPrice);
                setResult(1001, intent);
                ((MyApp)getApplicationContext()).setBasketLists(b);
                finish();

            }
        });


        Intent data_intent = getIntent();
        menuImage = data_intent.getStringExtra("menuImage");
        menuPrice = data_intent.getStringExtra("menuPrice");
        menuName = data_intent.getStringExtra("menuName");
        license = data_intent.getIntExtra("license",3);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        TextView menuprice = (TextView) findViewById(R.id.menuPrice);
        TextView menuname = (TextView) findViewById(R.id.menuName);
        sum = (TextView) findViewById(R.id.sum);
        b= ((MyApp)getApplicationContext()).getBasketLists();

        s = Integer.parseInt(menuPrice) * quantity;
        String price=Integer.toString(s);
        sum.setText(s/1000+ "," + price.substring(price.length()-3, price.length())+ "원");
        if(Integer.parseInt(menuPrice)!=0)
            menuprice.setText(Integer.parseInt(menuPrice)/1000+ "," +menuPrice.substring(menuPrice.length()-3, menuPrice.length())+ "원");
        else
            menuprice.setText("0원");
        menuname.setText(menuName);
        Glide.with(getApplicationContext()).load(menuImage).into(imageView);

        ImageView plus = (ImageView) findViewById(R.id.img_add);
        ImageView minus = (ImageView) findViewById(R.id.img_minus);
        quan= (TextView) findViewById(R.id.quantity);


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                quan.setText(""+quantity);
                s=Integer.parseInt(menuPrice) * quantity;
                String price=Integer.toString(s);
                sum.setText(s/1000+ "," + price.substring(price.length()-3, price.length())+ "원");
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity--;
                quan.setText(""+quantity);
                s=Integer.parseInt(menuPrice) * quantity;
                    if(s!=0){
                    String price=Integer.toString(s);
                    sum.setText(s/1000+ "," + price.substring(price.length()-3, price.length())+ "원");
            }
            }
        });
    }



}
