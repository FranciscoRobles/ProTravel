package mx.itesm.projectprotravel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CodigoActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser user;

    TextView nombre;
    TextView destino;
    TextView partida;
    TextView tiempo;


    EditText code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo);

        mAuth=FirebaseAuth.getInstance();
        myRef= FirebaseDatabase.getInstance().getReference("");
        user=mAuth.getCurrentUser();

        code=(EditText)findViewById(R.id.editText7);

        nombre=(TextView)findViewById(R.id.textView3);
        destino=(TextView)findViewById(R.id.textView4);
        partida=(TextView)findViewById(R.id.textView5);
        tiempo=(TextView)findViewById(R.id.textView6);


    }

    public void codeButton(View v){

        final String sCode=code.getText().toString();

        //Validación de datos
        myRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child("Viajes").child(sCode).exists()){ //checks the code is correct
                            if(!(dataSnapshot.child("Users").child(user.getUid()).child("viaje"). //Checks the user is not already registered
                                    getValue().toString()).equals(sCode)){

                                //modifies the interface and the database
                                Toast.makeText(CodigoActivity.this,"Codigo Valido",Toast.LENGTH_SHORT).show();
                                nombre.setText(nombre.getText().toString()+" "+dataSnapshot.child("Viajes").child(sCode).child("nombre").getValue());
                                destino.setText(destino.getText().toString()+ " "+dataSnapshot.child("Viajes").child(sCode).child("destino").getValue());
                                partida.setText(destino.getText().toString()+" "+dataSnapshot.child("Viajes").child(sCode).child("partida").getValue());
                                tiempo.setText(tiempo.getText().toString()+" "+dataSnapshot.child("Viajes").child(sCode).child("tiempo").getValue());

                                //Agregando el viaje al usuario
                                myRef.child("Users").child(user.getUid()).child("viaje").setValue(sCode);

                                myRef.child("Viajes").child(sCode).child("viajeros").setValue(
                                        Integer.parseInt(dataSnapshot.child("Viajes").child(sCode).child("viajeros").getValue().toString())+1);

                            }


                        }else{
                            Toast.makeText(CodigoActivity.this,"Codigo Invalido",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }
}
