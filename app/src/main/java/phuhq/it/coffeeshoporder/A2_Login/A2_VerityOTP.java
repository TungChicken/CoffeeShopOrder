package phuhq.it.coffeeshoporder.A2_Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import phuhq.it.coffeeshoporder.A0_Main.MainActivity;
import phuhq.it.coffeeshoporder.R;

public class A2_VerityOTP extends AppCompatActivity {
    //region AVAILABLE
    private EditText edCode;
    private String verificationId;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    //endregion

    //region FORM EVENTS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a2_verity_otp);
        mainLoad();
    }
    //endregion

    //region ADD CONTROLS AND LOAD
    public void addControls() {
        edCode = findViewById(R.id.a1_verity_edOTP);
    }

    public void mainLoad() {
        addControls();
        mAuth = FirebaseAuth.getInstance();
        //progressBar = findViewById(R.id.progressbar);
        String phoneNumber = getIntent().getStringExtra("PHONE");
        sendVerificationCode(phoneNumber);
    }
    //endregion

    //region SEND VERIFICATION CODE
    // send request OTP to Firebase
    public void sendVerificationCode(String numPhone) {
        try {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+84" + numPhone,
                    60,
                    TimeUnit.SECONDS,
                    this,
                    mCallbacks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            // Getting the code by send sms
            String code = phoneAuthCredential.getSmsCode();
            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                edCode.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(A2_VerityOTP.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }
    };

    //endregion

    //region VERIFY
    private void verifyVerificationCode(String OTP) {
        try {
            // Creating the credential
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, OTP);

            // Singing the user
            signInWithPhoneAuthCredential(credential);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent mainIntent = new Intent(A2_VerityOTP.this, MainActivity.class);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                } else {
                    //verification unsuccessful.. display an error message
                    String message = "Something was wrong, we will fix it soon ...";
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        message = "Invalid code entered...";
                    }
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                    snackbar.setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                    snackbar.show();
                }
            }
        });
    }
    //endregion

    //region CONFIRM
    public void OnConfirm(View view) {
        try {
            final String mCode = edCode.getText().toString().trim();
            if (mCode.isEmpty() || mCode.length() < 6) {
                edCode.setError("Enter code...");
                edCode.requestFocus();
            } else {
                verifyVerificationCode(mCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion
}
