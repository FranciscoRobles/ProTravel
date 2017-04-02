package mx.itesm.projectprotravel;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateUser extends AppCompatActivity {

    EditText nombre;
    EditText email;
    EditText password;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        mAuth = FirebaseAuth.getInstance();

        nombre = (EditText)findViewById(R.id.editText8);
        email=(EditText)findViewById(R.id.editText10);
        password=(EditText)findViewById(R.id.editText9);
    }

    public void create(View v){

        //crea usuarios en firebase
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(CreateUser.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        else{
                            //mAuth = FirebaseAuth.getInstance();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            DatabaseReference myRef= FirebaseDatabase.getInstance().getReference("");

                            User user1=new User(nombre.getText().toString(),user.getEmail());
                            myRef.child("Users").child(user.getUid()).setValue(user1);
                            changeToSelection(user.getUid().toString());

                        }
                    }
                });
    }

    private void changeToSelection(String id){
        Intent intent=new Intent(this,SelectionActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
