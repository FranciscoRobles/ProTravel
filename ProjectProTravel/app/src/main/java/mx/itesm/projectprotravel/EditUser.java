package mx.itesm.projectprotravel;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditUser extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference myRef;
    private Integer request;
    private String id;
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        request = intent.getIntExtra("activity", 0);

        user = FirebaseAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference("");
        name = (EditText)findViewById(R.id.editText11);

        final String code = id;
        myRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("Users").child(code).exists()){ //checks the code is correct
                            name.setText(dataSnapshot.child("Users").child(code).child("name").getValue().toString());
                        }else{
                            Toast.makeText(EditUser.this,"Couldn't update data",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    public void saveUpdate(View view){
        final String code = id;
        if(request == 0){
            myRef.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child("Users").child(code).exists()){ //checks the code is correct
                                myRef.child("Users").child(code).child("name").setValue(name.getText().toString());
                            }else{
                                Toast.makeText(EditUser.this,"Couldn't update data",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
            Intent intent = new Intent(this, SelectionActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        }
        else if(request == 1){

        }
    }
}
