package mx.itesm.projectprotravel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class ViajeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser user;

    EditText nombre;
    EditText destino;
    EditText partida;
    EditText tiempo;

    boolean repeated=false;
    int randomNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viaje);

        mAuth=FirebaseAuth.getInstance();
        myRef= FirebaseDatabase.getInstance().getReference("");
        user=mAuth.getCurrentUser();

        nombre=(EditText)findViewById(R.id.editText3);
        destino=(EditText)findViewById(R.id.editText4);
        partida=(EditText)findViewById(R.id.editText5);
        tiempo=(EditText)findViewById(R.id.editText6);
    }

    public void continuarButton(View v){
        int codigo=generarCodigo();
        Viaje viaje=new Viaje(nombre.getText().toString(),destino.getText().toString(),partida.getText().toString(),
                                tiempo.getText().toString(),user.getUid());
        myRef.child("Viajes").child(Integer.toString(codigo)).setValue(viaje); //le metes un objeto y obtiene datos de los getters

        Intent intent=new Intent(this,LeaderControlActivity.class);
        intent.putExtra("codigo",Integer.toString(codigo));
        startActivity(intent);
    }

    private int generarCodigo(){//genera un código y lo revisa en la base de datos
        Random r=new Random();



        do{
            repeated=false;
            randomNum=r.nextInt(99999);

            myRef.child("Viajes").addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Get user value
                            if(dataSnapshot.child(Integer.toString(randomNum)).exists()){
                                repeated=true;

                            }
                            else{
                                repeated=false;
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }while(repeated);

        return randomNum;

    }
}
