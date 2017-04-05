package mx.itesm.projectprotravel;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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

public class CodigoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout layout;
    private ActionBarDrawerToggle toggle;
    private NavigationView nv;
    //Número para saber a cuál actividad regresar
    private static final int ACTIVITY_SELECTION = 3;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    String codigo;
    TextView nombre;
    TextView destino;
    TextView partida;
    TextView tiempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo);

        mAuth=FirebaseAuth.getInstance();
        myRef= FirebaseDatabase.getInstance().getReference("");

        nombre=(TextView)findViewById(R.id.textView3);
        destino=(TextView)findViewById(R.id.textView4);
        partida=(TextView)findViewById(R.id.textView5);
        tiempo=(TextView)findViewById(R.id.textView6);

        Intent intent = getIntent();
        codigo = intent.getStringExtra("codigo");

        //Navigationbar
        layout = (DrawerLayout)findViewById(R.id.drawerLayoutViajero);
        toggle = new ActionBarDrawerToggle(this, layout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        layout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView)findViewById(R.id.nav_view_viajero);
        nv.setNavigationItemSelectedListener(this);

        final String sCode= codigo;
        //Validación de datos
        myRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child("Viajes").child(sCode).exists()){ //checks the code is correct
                            //modifies the interface and the database
                            nombre.setText(nombre.getText().toString()+" "+dataSnapshot.child("Viajes").child(sCode).child("nombre").getValue());
                            destino.setText(destino.getText().toString()+ " "+dataSnapshot.child("Viajes").child(sCode).child("destino").getValue());
                            partida.setText(destino.getText().toString()+" "+dataSnapshot.child("Viajes").child(sCode).child("partida").getValue());
                            tiempo.setText(tiempo.getText().toString()+" "+dataSnapshot.child("Viajes").child(sCode).child("tiempo").getValue());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    public void codeButton(View v){



    }

    //Necessary for the navigationbar to work correctly
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.nav_account){
            Intent intent=new Intent(this,EditUser.class);
            intent.putExtra("activity", ACTIVITY_SELECTION);
            intent.putExtra("codigoViaje", codigo);
            startActivity(intent);

        }else if(item.getItemId() == R.id.nav_logout){
            mAuth.signOut();
            Intent intent=new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //removes all the previous activities
            startActivity(intent);
            finish();
        }
        layout.closeDrawer(GravityCompat.START);
        return true;
    }
}
