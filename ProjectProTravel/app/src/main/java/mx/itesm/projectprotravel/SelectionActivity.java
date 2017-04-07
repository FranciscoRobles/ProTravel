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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.R.id.list;

public class SelectionActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout layout;
    private ActionBarDrawerToggle toggle;
    private NavigationView nv;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser user;
    //Número para saber a cuál actividad regresar
    private static final int ACTIVITY_SELECTION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        mAuth = FirebaseAuth.getInstance();
        myRef=FirebaseDatabase.getInstance().getReference("");
        user=mAuth.getCurrentUser();
        //Navigationbar
        layout = (DrawerLayout)findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, layout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        layout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView)findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(this);
    }

    public void liderButton(View v){

        myRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child("Users").child(user.getUid()).child("leader").exists()){
                            Intent intent=new Intent(SelectionActivity.this,LeaderControlActivity.class);
                            intent.putExtra("codigo",dataSnapshot.child("Users").child(user.getUid()).child("leader").getValue().toString());
                            startActivity(intent);
                        }else{
                            Intent intent=new Intent(SelectionActivity.this,ViajeActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    public void viajeroButton(View v){

        myRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(Integer.parseInt(dataSnapshot.child("Users").child(user.getUid()).child("viaje").getValue().toString())!=0){
                            Intent intent=new Intent(SelectionActivity.this,CodigoActivity.class);
                            intent.putExtra("codigo",dataSnapshot.child("Users").child(user.getUid()).child("viaje").getValue().toString());
                            startActivity(intent);
                        }else{
                            Intent intent=new Intent(SelectionActivity.this,Enter_Code.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

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
            startActivity(intent);
            finish();
        }
        layout.closeDrawer(GravityCompat.START);
        return true;
    }
}
