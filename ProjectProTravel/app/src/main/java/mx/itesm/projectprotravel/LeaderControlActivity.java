package mx.itesm.projectprotravel;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LeaderControlActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        AdapterView.OnItemClickListener{

    private DrawerLayout layout;
    private ActionBarDrawerToggle toggle;
    private NavigationView nv;

    //Número para saber a cuál actividad regresar
    private static final int ACTIVITY_SELECTION = 2;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser user;

    //Attributes of listview
    private ArrayList<User> viajeros;
    private UserAdapter adapter;
    private ListView list;

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
        codigoViaje.setText("Código de viaje: "+codigo);


        //ListView
        viajeros=new ArrayList<User>();
        adapter=new UserAdapter(viajeros,this);
        list=(ListView)findViewById(R.id.ListViewViajeros);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);



        //Navigationbar
        layout = (DrawerLayout)findViewById(R.id.drawerLayoutLider2);
        toggle = new ActionBarDrawerToggle(this, layout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        layout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView)findViewById(R.id.nav_view_lider2);
        nv.setNavigationItemSelectedListener(this);




        //Base de datos en tiempo real
        Query query=myRef.child("Users").orderByChild("viaje").equalTo(Integer.parseInt(codigo));

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                viajeros=new ArrayList<User>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post

                    User u=new User(postSnapshot.child("name").getValue().toString(),postSnapshot.child("email").getValue().toString());
                    String status=postSnapshot.child("status").getValue().toString();

                    if(status!=null){ //safety measure
                        u.setStatus(postSnapshot.child("status").getValue().toString());
                    }

                    viajeros.add(u);

                }
                adapter=new UserAdapter(viajeros,LeaderControlActivity.this);
                list.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public void deleteButton(View v){
        Query query=myRef.child("Users").orderByChild("viaje").equalTo(Integer.parseInt(codigo));

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post

                    String key = postSnapshot.getKey();
                    myRef.child("Users").child(key).child("viaje").setValue(0);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myRef.child("Viajes").child(codigo).removeValue();
        myRef.child("Users").child(user.getUid()).child("leader").removeValue();
        Intent intent=new Intent(this, SelectionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //removes all the previous activities
        startActivity(intent);
        finish();
    }

    public void signalButton(View v){
        Query query=myRef.child("Users").orderByChild("viaje").equalTo(Integer.parseInt(codigo));

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post

                    String key = postSnapshot.getKey();
                    myRef.child("Users").child(key).child("status").setValue("notify");
                    myRef.child("Users").child(key).child("status").setValue("");
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
            intent.putExtra("codigoViaje", codigo);
            startActivity(intent);
        }else if(item.getItemId() == R.id.nav_logout){
            mAuth.signOut();
            Intent intent=new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //removes all the previous activities
            startActivity(intent);
            finish();
        }
        else if(item.getItemId() == R.id.nav_viaje){
            Intent intent = new Intent(this, EditViaje.class);
            intent.putExtra("codigoViaje", codigo);
            startActivity(intent);
        }
        layout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Query query=myRef.child("Users").orderByChild("email").equalTo(viajeros.get(position).getEmail());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post

                    String key = postSnapshot.getKey();
                    myRef.child("Users").child(key).child("status").setValue("notify");
                    myRef.child("Users").child(key).child("status").setValue("");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        

    }
}
