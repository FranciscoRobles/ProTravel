package mx.itesm.projectprotravel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LeaderControlActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser user;

    TextView id;
    String codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_control);

        Intent intent=getIntent();

        mAuth=FirebaseAuth.getInstance();
        myRef= FirebaseDatabase.getInstance().getReference("");
        user=mAuth.getCurrentUser();

        id=(TextView)findViewById(R.id.textView7);

        codigo=intent.getStringExtra("codigo");
        id.setText(codigo);

    }

    public void deleteButton(View v){
        myRef.child("Viajes").child(codigo).removeValue();
        finish();
    }

    public void signalButton(View v){
        Intent intent = new Intent(this, SignActivity.class);
        startActivity(intent);
    }
}
