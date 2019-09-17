package tpgus.example.com.rros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CompleteReservation extends AppCompatActivity {
    TextView name;
    TextView phone;
    TextView time;
    TextView no;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_reservation);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        time = findViewById(R.id.time);
        no = findViewById(R.id.no);

        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        phone.setText(intent.getStringExtra("phone"));
        time.setText(intent.getStringExtra("time"));
        no.setText(intent.getStringExtra("reservationNo"));

        Button ok = (Button) findViewById(R.id.btn_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
