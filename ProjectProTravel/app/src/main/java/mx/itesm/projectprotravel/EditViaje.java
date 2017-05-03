package mx.itesm.projectprotravel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditViaje extends AppCompatActivity {

    EditText name;
    EditText destino;
    EditText partida;
    EditText tiempo;
    TextView viaje;
    private String code;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_viaje);

        name = (EditText)findViewById(R.id.viajeName);
        destino = (EditText)findViewById(R.id.viajeDestino);
        partida = (EditText)findViewById(R.id.viajePartida);
        tiempo = (EditText)findViewById(R.id.viajeTiempo);
        viaje = (TextView) findViewById(R.id.textView8);
        Intent intent = getIntent();
        code = intent.getStringExtra("codigoViaje");
        myRef = FirebaseDatabase.getInstance().getReference("");

        myRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("Viajes").child(code).exists()){ //checks the code is correct
                            name.setText(dataSnapshot.child("Viajes").child(code).child("name").getValue().toString());
                            destino.setText(dataSnapshot.child("Viajes").child(code).child("destino").getValue().toString());
                            partida.setText(dataSnapshot.child("Viajes").child(code).child("partida").getValue().toString());
                            tiempo.setText(dataSnapshot.child("Viajes").child(code).child("tiempo").getValue().toString());
                        }else{
                            Toast.makeText(EditViaje.this,"Couldn't update data",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        viaje.setText("Código del viaje: "+code);

    }

    public void saveChanges(View view){
        myRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("Viajes").child(code).exists()){ //checks the code is correct
                            myRef.child("Viajes").child(code).child("name").setValue(name.getText().toString());
                            myRef.child("Viajes").child(code).child("destino").setValue(destino.getText().toString());
                            myRef.child("Viajes").child(code).child("partida").setValue(partida.getText().toString());
                            myRef.child("Viajes").child(code).child("tiempo").setValue(tiempo.getText().toString());
                        }else{
                            Toast.makeText(EditViaje.this,"Couldn't update data",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        Intent intent = new Intent(this, LeaderControlActivity.class);
        intent.putExtra("codigo", code);
    }
}
