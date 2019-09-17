package tpgus.example.com.rros.register;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import tpgus.example.com.rros.R;

public class Register_owner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_owner);
        setTitle("사업자 회원가입");

        Button btn =(Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(true){

                    Toast.makeText(getApplicationContext(),"회원가입 완료",Toast.LENGTH_LONG).show();
                    finish();

                }else{
                    Toast.makeText(getApplicationContext(),"회원가입 실패 유형",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
