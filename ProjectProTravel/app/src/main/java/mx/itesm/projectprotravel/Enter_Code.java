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

public class Enter_Code extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout layout;
    private ActionBarDrawerToggle toggle;
    private NavigationView nv;
    //Número para saber a cuál actividad regresar
    private static final int ACTIVITY_SELECTION = 4;

    private FirebaseAuth mAuth;
    EditText code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter__code);

        mAuth=FirebaseAuth.getInstance();
        code=(EditText)findViewById(R.id.editTextCodigo);

        //Navigationbar
        layout = (DrawerLayout)findViewById(R.id.drawerLayoutViajero3);
        toggle = new ActionBarDrawerToggle(this, layout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        layout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView)findViewById(R.id.nav_view_viajero3);
        nv.setNavigationItemSelectedListener(this);
    }

    public void codeButton(View view){
        Intent intent = new Intent(this, CodigoActivity.class);
        intent.putExtra("codigo", code.getText().toString());
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