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
import android.widget.Toast;

public class SelectionActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout layout;
    private ActionBarDrawerToggle toggle;
    private NavigationView nv;
    private String id;
    private static final int ACTIVITY_SELECTION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        layout = (DrawerLayout)findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, layout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        layout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView)findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
    }

    public void liderButton(View v){
        Intent intent=new Intent(this,ViajeActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void viajeroButton(View v){
        Intent intent=new Intent(this,CodigoActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

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
            Toast.makeText(this, "mi cuenta", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,EditUser.class);
            intent.putExtra("id", id);
            intent.putExtra("activity", ACTIVITY_SELECTION);
            startActivity(intent);

        }
        layout.closeDrawer(GravityCompat.START);
        return true;
    }
}
