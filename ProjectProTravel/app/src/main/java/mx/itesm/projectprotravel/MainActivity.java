package mx.itesm.projectprotravel;

import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    private FirebaseAuth mAuth;

    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        email=(EditText)findViewById(R.id.editText);
        password=(EditText)findViewById(R.id.editText2);



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null){
            Intent intent=new Intent(this,SelectionActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void createU(View v){
        Intent intent = new Intent(this, CreateUser.class);
        startActivity(intent);
    }

    public void login(View v){

        //autentica usuarios
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("", "signInWithEmail", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            //Pass the id of the user so it can be used in other activities
                            Toast.makeText(MainActivity.this, "Welcome",Toast.LENGTH_SHORT).show();
                            myRef = FirebaseDatabase.getInstance().getReference("");
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String id = "";
                            if(user.getEmail().toString().equals(email.getText().toString())){
                                id = user.getUid().toString();
                            }
                            changeToSelection(id);
                        }

                    }
                });
    }

    private void changeToSelection(String id){
        Intent intent=new Intent(this,SelectionActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
        finish();
    }
}
