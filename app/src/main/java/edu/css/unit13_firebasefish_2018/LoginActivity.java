package edu.css.unit13_firebasefish_2018;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnCreate;
    EditText etEmail;
    EditText etPassword;

    //Helps declare firebase authentication for the application
    private FirebaseAuth mAuth;

    /**
     * onCreate - helps generate the basic login layout for the application. Lets the application
     * recognize each button and text field found within the Login Activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance(); //declare object for Firebase
        btnLogin = findViewById(R.id.buttonLogin); //declares button object for Login
        btnCreate = findViewById(R.id.buttonCreate); //declares button object for Creating an account
        etEmail = findViewById(R.id.editTextEmail); //edit text field for entering email
        etPassword = findViewById(R.id.editTextPassword); //edit text field for entering password

        /**
         * btnLogin - sets an onClick listener for login button
         * helps get text and sends it to the SignIn method
         */
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(etEmail.getText().toString(), etPassword.getText().toString());
            }
        });

        /**
         * btnCreate - sets an onClick listener for creating an account button
         * helps get text from the email and password fields and sends them to
         * the Create an account
         */

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(etEmail.getText().toString(), etPassword.getText().toString());
            }
        });
    }

    /**
     * SignIn - Method that displays a toast message if the user does not enter in the correct password
     * as the one created for the email. SignIn also fails if the password is less than six characters long.
     * If the password is correct, the activity finishes and moves onto the MainActivity.
     * @param email
     * @param password
     */
    private void signIn(String email, String password){
        //sign in the recurrent user with email and password previously created.
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() { //add to listener
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) { //when failed
                    Toast.makeText(LoginActivity.this, "SignIn--Authentication failed.", Toast.LENGTH_LONG).show();
                } else {
                    //return to MainActivity is login works
                    finish();
                }
            }
        });
    }

    /**
     * createAccount - Helps the user generate an account so that they can access the MainActivity. If the
     * user creates a password that is less than six characters long, it will display a messsage describing
     * that the authentification failed. If successful, the account will be created.
     * @param email
     * @param password
     */
    private void createAccount(String email, String password) {
        //create account for new users
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {  //update listener.
                if (!task.isSuccessful()) { //when failed
                    Toast.makeText(LoginActivity.this, "createAccount--Authentication failed.",Toast.LENGTH_LONG).show();
                } else {
                    //return to MainActivity is login works
                    finish();
                }
            }
        });
    }
}
