package mx.itesm.projectprotravel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

    }

    public void sendCheck(View v){
        Intent intent = new Intent(this, CodigoActivity.class);
        startActivity(intent);
    }
}
