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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LeaderControlActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout layout;
    private ActionBarDrawerToggle toggle;
    private NavigationView nv;
    //Número para saber a cuál actividad regresar
    private static final int ACTIVITY_SELECTION = 2;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser user;

    TextView codigoViaje;
    String codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_control);

        Intent intent=getIntent();

        mAuth=FirebaseAuth.getInstance();
        myRef= FirebaseDatabase.getInstance().getReference("");
        user=mAuth.getCurrentUser();

        codigoViaje=(TextView)findViewById(R.id.textView7);

        codigo=intent.getStringExtra("codigo");
        codigoViaje.setText(codigo);

        //Navigationbar
        layout = (DrawerLayout)findViewById(R.id.drawerLayoutLider2);
        toggle = new ActionBarDrawerToggle(this, layout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        layout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView)findViewById(R.id.nav_view_lider2);
        nv.setNavigationItemSelectedListener(this);

    }

    public void deleteButton(View v){
        myRef.child("Viajes").child(codigo).removeValue();
        finish();
    }

    public void signalButton(View v){
        Intent intent = new Intent(this, SignActivity.class);
        startActivity(intent);
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
