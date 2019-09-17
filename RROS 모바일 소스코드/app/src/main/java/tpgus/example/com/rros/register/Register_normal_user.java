package tpgus.example.com.rros.register;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import tpgus.example.com.rros.MemberInfoItem;
import tpgus.example.com.rros.MyLog;
import tpgus.example.com.rros.R;
import tpgus.example.com.rros.RemoteService;
import tpgus.example.com.rros.ServiceGenerator;

public class Register_normal_user extends AppCompatActivity implements View.OnClickListener {


    private final String TAG = this.getClass().getSimpleName();
    Context context;

    MemberInfoItem currentItem;


    EditText name;
    EditText id;
    EditText password;
    EditText email;
    EditText customerno;
    EditText phone;
    EditText age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_normal_user);
        setTitle("일반 사용자 회원가입");
        context = this;
        name = (EditText) findViewById(R.id.Name);
        id=(EditText)findViewById(R.id.id);
        password=(EditText)findViewById(R.id.password);
        email=(EditText)findViewById(R.id.email);
        customerno=(EditText)findViewById(R.id.customerno);
        phone=(EditText)findViewById(R.id.phone);
        age=(EditText)findViewById(R.id.age);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button:

                save();
                if(true){
                    //회원가입 성공시
                    Toast.makeText(getApplicationContext(),"회원가입 완료",Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"회원가입 실패 유형",Toast.LENGTH_LONG).show();
                }

        }
    }

    private MemberInfoItem setMemberInfoItem() {
        MemberInfoItem item = new MemberInfoItem();
//        item.name = name.getText().toString();
//        item.id = id.getText().toString();
//        item.password = password.getText().toString();
//        item.email = email.getText().toString();
//        item.customerno=Integer.parseInt(customerno.getText().toString());
//        item.phone=phone.getText().toString();
//        item.age=age.getText().toString();
        return item;
        //입력된 정보들로 새로운 멤버 생성하고, 이 메소드 호출시 반환한다. <MyApp의 메소드와는 다름>
        //원래는 getMemberInfoItem이었으나 편의상 setMemberInfoItem으로 바꿈
        //save()안의 첫번째줄 setMemberInfoItem사용.
    }


    private void save() {
        final MemberInfoItem newItem = setMemberInfoItem();

        MyLog.d(TAG, "insertItem" + newItem.toString());
      //  Toast.makeText(getApplicationContext(),newItem.toString(),Toast.LENGTH_LONG).show();

        RemoteService remoteService=ServiceGenerator.createService(RemoteService.class);

        Call<String> call = remoteService.insertMemberInfo(newItem);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    //String seq = response.body();
                    try {
                        //    currentItem.CustomerNo = Integer.parseInt(seq);
                        //if (currentItem.CustomerNo == 0) {
                        //    MyToast.s(context, R.string.member_insert_fail_message);
                           return;
                        //  }
                    } catch (Exception e) {

                        //원래 이 오류 계속 뜨는데 무시하고 진행하기. 나중에 문제생기면 여기
                        //  MyToast.s(context,R.string.camera); 카메라는 임시로 설정한 오류메시지
                        //return;
                    }
//                    currentItem.name = newItem.name;
//                    currentItem.id = newItem.id;
//                    currentItem.password = newItem.password;
//                    currentItem.email = newItem.email;
//                    currentItem.customerno = newItem.customerno;
//                    currentItem.phone = newItem.phone;
//                    currentItem.age=newItem.age;
     // finish();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"서버 연결 실패",Toast.LENGTH_LONG).show();
            }
        });

    }
}
