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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class ViajeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout layout;
    private ActionBarDrawerToggle toggle;
    private NavigationView nv;
    //Número para saber a cuál actividad regresar
    private static final int ACTIVITY_SELECTION = 1;

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

        //Navigationbar
        layout = (DrawerLayout)findViewById(R.id.drawerLayoutLider);
        toggle = new ActionBarDrawerToggle(this, layout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        layout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView)findViewById(R.id.nav_view_lider);
        nv.setNavigationItemSelectedListener(this);
    }

    public void continuarButton(View v){
        int codigo=generarCodigo();
        Viaje viaje=new Viaje(nombre.getText().toString(),destino.getText().toString(),partida.getText().toString(),
                                tiempo.getText().toString(),user.getUid());
        myRef.child("Viajes").child(Integer.toString(codigo)).setValue(viaje);//le metes un objeto y obtiene datos de los getters
        myRef.child("Users").child(user.getUid()).child("leader").setValue(codigo);

        Intent intent=new Intent(this,LeaderControlActivity.class);
        intent.putExtra("codigo",Integer.toString(codigo));
        startActivity(intent);
        finish();
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
