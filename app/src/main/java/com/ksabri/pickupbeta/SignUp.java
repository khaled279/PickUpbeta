package com.ksabri.pickupbeta;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
        EditText firstName ;
        EditText lastName ;
        EditText phoneNumber;
        EditText email ;
        EditText password ;
        private FirebaseAuth auth ;
        private  boolean dataSet ;
        private ProgressBar progressBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findViewById(R.id.textSignUp).setOnClickListener(this);
        findViewById(R.id.confirm).setOnClickListener(this);
        auth = FirebaseAuth.getInstance() ;
        dataSet = true;
        progressBar = findViewById(R.id.progressbar) ;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textSignUp : startActivity(new Intent(this , Login.class));
            break;
            case R.id.confirm : getData(); break;
        }
    }
    private String[] data = new String[5] ;
    private void getData(){
        firstName = findViewById(R.id.editText);
        lastName = findViewById(R.id.editText2);
        phoneNumber = findViewById(R.id.phoneNumber);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        data[0] = firstName.getText().toString().trim() ;
        data[1] =  lastName.getText().toString().trim() ;
        data[2] = phoneNumber.getText().toString().trim() ;
        data[3]= email.getText().toString().trim() ;
        data[4]= password.getText().toString().trim();
        for(int i = 0 ; i<data.length ; i++){
            if (data[i].isEmpty()){
                switch (i){
                    case 0 : firstName.setError(getString(R.string.error_first_name)); dataSet=false; break;
                    case 1 : lastName.setError(getString(R.string.error_last_name));dataSet=false; break;
                    case 2 : phoneNumber.setError(getString(R.string.error_phone_number));dataSet =false; break;
                    case 3 :  email.setError(getString(R.string.error_email_empty));dataSet =false; break;
                    case 4 : password.setError(getString(R.string.error_password_required)); dataSet =false;  break;
                    default: dataSet = true ;
                }

            }else  {
                switch (i){
                    case 2 : if (data[2].length()<10)phoneNumber.setError(getString(R.string.error_phone_number)); dataSet =false; break;
                    case 3 : if(!(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches())){
                        email.setError(getString(R.string.email_valid)); dataSet =false; break ;}
                        case 4 : if (data[4].length() < 6){ password.setError(getString(R.string.password_too_short)); dataSet =false; break;}
                        default: dataSet  =  true ;
                    }
                }}

                if (dataSet){
                    progressBar.setVisibility(View.VISIBLE);
                    auth.createUserWithEmailAndPassword(data[3],data[4]).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Log.d("Auth" , "Authintication complete") ;
                                FirebaseUser user = auth.getInstance().getCurrentUser()  ;
                                User userPhone = new User(data[0] +" "+ data[1] , data[2], data[3]) ;
                                Log.d("tmam", "onComplete: " + data[0] + data[1]);
                                FirebaseDatabase.getInstance().getReference("user").child(user.getUid())
                                        .setValue(userPhone).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@androidx.annotation.NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(SignUp.this , "Regirstration successful" , Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }else {
                                progressBar.setVisibility(View.GONE);
                                if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                    Toast.makeText(SignUp.this , getString(R.string.email_used), Toast.LENGTH_LONG).show();
                                }else Toast.makeText(getApplicationContext() , task.getException().getMessage() , Toast.LENGTH_LONG);
                            }
                        }
                    }) ;
                }


    }

}
