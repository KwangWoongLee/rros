package tpgus.example.com.rros;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tpgus.example.com.rros.register.Regist_Select_type;


public class MainActivity extends AppCompatActivity {
    String email;
    private static OAuthLogin mOAuthLoginInstance;
    private static Context mContext;
    private OAuthLoginButton mOAuthLoginButton;


    private static String OAUTH_CLIENT_ID = "K43AENuL8ehBW4B6ntw1";
    private static String OAUTH_CLIENT_SECRET = "dQONjFV8G5";
    private static String OAUTH_CLIENT_NAME = "네이버 아이디로 로그인";

    private final String TAG = this.getClass().getSimpleName();
    Context context;
    SelectMemberInfo currentItem=new SelectMemberInfo();
    MemberInfoItem item= new MemberInfoItem();
    EditText id;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        Intent intent = new Intent(this,App_Loading.class);
        startActivity(intent);

        mContext = this;

        initData();
        initView();
        new RequestApiTask().execute();

    }


    public void onButtonClick(View v) throws Throwable {

//        switch (v.getId()) {
//            case R.id.login:{
//
//                String id1 = id.getText().toString();
//                String pw1= password.getText().toString();
//                if (TextUtils.isEmpty(id1)) {
//                    id.setError(getString(R.string.error_field_required));
//                }else if(TextUtils.isEmpty(pw1)) {
//                    password.setError(getString(R.string.error_field_required));
//                }
//                authentication();// 여기 활성화 하면 아래 3줄 주석
////                Intent intent = new Intent(getApplicationContext(),After_Login.class);
////                startActivity(intent);
////                finish();
//                break;
//            }
//
//            case R.id.sign_up:{
//
//                Intent intent = new Intent(getApplicationContext(),Regist_Select_type.class);
//                startActivity(intent);
//                break;
//            }
//            default:
//                break;
//        }
    }

    private SelectMemberInfo selectMemberInfo(){
        SelectMemberInfo item1 = new SelectMemberInfo();
        item1.id = "";
        item1.password = "";
        return item1;
        //입력된 정보들로 새로운 멤버 생성하고, 이 메소드 호출시 반환한다. <MyApp의 메소드와는 다름>
        //원래는 getMemberInfoItem이었으나 편의상 setMemberInfoItem으로 바꿈
    }

    private void authentication(){
        final SelectMemberInfo newItem = selectMemberInfo();

        RemoteService remoteService=ServiceGenerator.createService(RemoteService.class);
        Call<String> call = remoteService.selectMemberInfo(newItem);

        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    //String seq = response.body();

                        item.id=newItem.id;

                        currentItem.id=newItem.id;
                        currentItem.password=newItem.password;
                        ((MyApp)getApplicationContext()).setMemberInfoItem(item);
                        convertView();
                        return;



                }else{
                    int statusCode = response.code();
                    Toast.makeText(getApplicationContext(),"로그인 실패 :"+statusCode,Toast.LENGTH_LONG).show();
                    //                               ResponseBody errorBody = response.errorBody();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"네트워크 문제",Toast.LENGTH_LONG).show();


            }

        });

    }

    private void convertView() {
        Intent intent = new Intent(getApplicationContext(),After_Login.class);
        intent.putExtra("id",currentItem.id);
        startActivity(intent);
        finish();
    }

    private void initData() {
        mOAuthLoginInstance = OAuthLogin.getInstance();

        mOAuthLoginInstance.showDevelopersLog(true);
        mOAuthLoginInstance.init(mContext, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME);

    }

    @Override
    protected void onResume() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onResume();
    }

    private void initView() {

        mOAuthLoginButton = (OAuthLoginButton) findViewById(R.id.buttonOAuthLoginImg);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);

    }

    @SuppressLint("HandlerLeak")
    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                String accessToken = mOAuthLoginInstance.getAccessToken(mContext);
                String refreshToken = mOAuthLoginInstance.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginInstance.getExpiresAt(mContext);
                String tokenType = mOAuthLoginInstance.getTokenType(mContext);

                Intent intent = new Intent(mContext,After_Login.class);
                startActivity(intent);
                finish();


            } else {
                String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        }

    };



    private class RequestApiTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute()
        {
            //mApiResultText.setText((String) "");
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = "https://openapi.naver.com/v1/nid/me";
            String at = mOAuthLoginInstance.getAccessToken(mContext);

            return mOAuthLoginInstance.requestApi(mContext, at, url);
        }

        protected void onPostExecute(String content) {
            //mApiResultText.setText((String) content);

            try {

                //아래 읽고 이걸 읽어도 됨. JSONObject의 배열을 담는 공간이 JSONArray이다.
                JSONObject obj=new JSONObject(content);
                String a = obj.getString("response");

                //{"resultcode":"xx" ,"reponse":{"id":"xx","gender":"xx",...} }형태로 두개의{{}}로 진짜 필요한 reponse의 값들을 한 번더 감싸주었기 때문에
                //JSONObject 로 "reponse":{}의 {}안을 얻기 위해 String id=obj.getString("response");를 이용하여 바깥 {}을 벗겨주고
                //결과로 {"id":"xx","gender":"xx","name":"xx","birthday":"xxx"}를 얻어오고 a에 저장한다.
                //이 값을 다시 세분화하기 위해 한번더 JSONObject를 이용해서 a를 분리한다.
                JSONObject obj1 = new JSONObject(a);
                //그리고 이제 키값을 이용해 데이터를 이용할 수 있다.
                String id = obj1.getString("id");
                String nickname=obj1.getString("nickname");
                String name = obj1.getString("name");
                email=obj1.getString("email");
                String gender = obj1.getString("gender");
                String age=obj1.getString("age");
                String birthday = obj1.getString("birthday");
                String profile_image=obj1.getString("profile_image");
//                item=new MemberInfoItem(id,nickname,name,email,gender,age,birthday,profile_image);
//                ((MyApp)getApplicationContext()).setMemberInfoItem(item);

                saveMember(email);
//                Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(),gender,Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(),birthday,Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }
    private void saveMember(String Email){

        RemoteService remoteService=ServiceGenerator.createService(RemoteService.class);
        Call<ArrayList<MemberInfoItem>> call = remoteService.getMember(Email);

        call.enqueue(new Callback<ArrayList<MemberInfoItem>>() {

            @Override
            public void onResponse(Call<ArrayList<MemberInfoItem>> call, Response<ArrayList<MemberInfoItem>> response) {
                if (response.isSuccessful()) {
                    ArrayList<MemberInfoItem> a =response.body();

                    MemberInfoItem item2=new MemberInfoItem(a.get(0).getCustomerNo(),a.get(0).getId(),a.get(0).getCusName(),a.get(0).getPassword(),a.get(0).getPhoneNo(),a.get(0).getEmail(),a.get(0).getAge(),a.get(0).getFlag(),a.get(0).getImage(),a.get(0).getFk_CouponNo_cus());

                    ((MyApp)getApplicationContext()).setMemberInfoItem(item2);

                }else{

                }
            }

            @Override
            public void onFailure(Call<ArrayList<MemberInfoItem>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"네트워크 문제"+ t,Toast.LENGTH_LONG).show();


            }

        });}


}


//회원가입 값 유효 검사에 응용

//
//        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//                mPasswordView.setError(getString(R.string.error_invalid_password));
//                focusView = mPasswordView;
//                cancel = true;
//                }
//
//                // Check for a valid email address.
//                if (TextUtils.isEmpty(email)) {
//                mEmailView.setError(getString(R.string.error_field_required));
//                focusView = mEmailView;
//                cancel = true;
//                } else if (!isEmailValid(email)) {
//                mEmailView.setError(getString(R.string.error_invalid_email));
//                focusView = mEmailView;
//                cancel = true;
//                }
//
//                if (cancel) {
//                // There was an error; don't attempt login and focus the first
//                // form field with an error.
//                focusView.requestFocus();
//                } else {
//                // Show a progress spinner, and kick off a background task to
//                // perform the user login attempt.
//                showProgress(true);
//                mAuthTask = new UserLoginTask(email, password);
//                mAuthTask.execute((Void) null);
//                }
//                }
//
//private boolean isEmailValid(String email) {
//        //TODO: Replace this with your own logic
//        return email.contains("@");
//        }
//
//private boolean isPasswordValid(String password) {
//        //TODO: Replace this with your own logic
//        return password.length() > 4;
//        }

