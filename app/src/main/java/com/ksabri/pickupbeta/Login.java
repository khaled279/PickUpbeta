package com.ksabri.pickupbeta;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.regex.Matcher;

public class Login extends AppCompatActivity  implements View.OnClickListener,View.OnLongClickListener{
    EditText editText ;
    EditText editText1 ;
    TextView textView ;
    Button logIn ;
    FirebaseAuth auth ;
    String email ;
    String password ;
    Boolean isDataGood = true ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
            logIn = findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        editText1 = findViewById(R.id.editText2);
       textView =  findViewById(R.id.textRegister) ;
       textView.setOnClickListener(this);
       auth = FirebaseAuth.getInstance() ;
       logIn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
          switch (v.getId()){
              //case R.id.textForgotPassword : startActivity(new Intent(this , ForgotPassword.class));
            //  break;
              case R.id.button: if(validateData()){
                  auth.signInWithEmailAndPassword(email ,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){  Intent intent = new Intent(getApplicationContext() , Home.class);
                          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                          startActivity(intent);}
                       else {
                           Toast.makeText(getApplicationContext() , task.getException().getMessage() , Toast.LENGTH_LONG);
                       }
                      }
                  });
              }break;
              case R.id.textRegister : startActivity(new Intent(this , SignUp.class));
          }
    }
        int id ;
    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()){
            case R.id.editText : editText.selectAll(); break;
            case R.id.editText2: editText1.selectAll(); break;

        }


        return false ;
    }
    private  Boolean validateData(){
        email = editText.getText().toString().trim() ;
        password = editText1.getText().toString().trim() ;
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editText.setError(getString(R.string.email_valid));
            isDataGood = false ;
        }
        if (password.isEmpty() || password.length() <6){
            editText1.setError(getString(R.string.password_too_short));
            isDataGood = false;
        }else return true ;

    return isDataGood ; }
}
