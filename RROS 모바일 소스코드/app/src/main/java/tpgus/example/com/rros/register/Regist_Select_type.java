package tpgus.example.com.rros.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import tpgus.example.com.rros.R;

public class Regist_Select_type extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        setTitle("회원가입 유형");


        final RadioButton rbtn1 = (RadioButton)findViewById(R.id.radioButton1);
        final RadioButton rbtn2 = (RadioButton)findViewById(R.id.radioButton2);
        Button btn = (Button)findViewById(R.id.button);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(rbtn1.isChecked()){
                            Intent intent = new Intent(getApplicationContext(), Register_normal_user.class);
                            startActivity(intent);
                            finish();

                        }else if(rbtn2.isChecked()) {
                            Intent intent = new Intent(getApplicationContext(), Register_owner.class);
                            startActivity(intent);
                            finish();
                        }else{
                          Toast toast=Toast.makeText(getApplicationContext(),"유형을 선택하지 않았습니다.",Toast.LENGTH_LONG);
                          toast.show();

                        }

                    }
                });
    }
}
