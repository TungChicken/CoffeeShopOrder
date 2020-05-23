package phuhq.it.coffeeshoporder.A2_Login.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import phuhq.it.coffeeshoporder.A0_Main.MainActivity;
import phuhq.it.coffeeshoporder.R;

public class A2_Login extends AppCompatActivity {
    //region AVAILABLE
    private EditText edPhone;
    private ImageView imgFingerPrint;
    public BiometricPrompt biometricPrompt;
    public BiometricPrompt.PromptInfo promptInfo;
    private ProgressBar progressBar;
    //endregion

    //region FORM EVENTS
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a2_login);
        addControls();
        checkDeviceSupportFingerPrint();
    }
    //endregion

    //region ADD CONTROLS AND LOAD
    public void addControls() {
        edPhone = findViewById(R.id.a1_login_edPhone);
        imgFingerPrint = findViewById(R.id.a1_login_btnFingerprint);
        progressBar = findViewById(R.id.a2_progressBar);
    }
    //endregion

    //region FINGERPRINT
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void checkDeviceSupportFingerPrint() {
        try {
            // Kiểm tra thiết bị đang chạy Android 6.0 (M) hoặc cao hơn
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //Fingerprint API chỉ hỗ trợ từ Android 6.0 (M)
                FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(this);
                if (!fingerprintManagerCompat.isHardwareDetected()) {
                    // Thiết bị không hỗ trợ xác thưc vân tay
                    imgFingerPrint.setVisibility(View.GONE);
                    Toast.makeText(this, "Your device don't support fingerprint", Toast.LENGTH_SHORT).show();
                } else if (!fingerprintManagerCompat.hasEnrolledFingerprints()) {
                    // Người dùng chưa đăng ký vân tay với máy
                    Toast.makeText(this, "You are don't have sign fingerprint in device", Toast.LENGTH_SHORT).show();
                } else {
                    // Sẵn sàng xác thực vân tay
                    onFingerPrint();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fingerPrint() {
        try {
            Executor executor = Executors.newSingleThreadExecutor();
            FragmentActivity activity = this;

            biometricPrompt = new BiometricPrompt(activity, executor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                }

                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    Intent mainIntent = new Intent(A2_Login.this, MainActivity.class);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                }
            });

            promptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Verity your identity")
                    .setSubtitle("Confirm your fingerprint for login")
                    .setDescription("Touch sensor")
                    .setNegativeButtonText("Cancel")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onFingerPrint() {
        try {
            imgFingerPrint.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.P)
                @Override
                public void onClick(View v) {
                    fingerPrint();
                    biometricPrompt.authenticate(promptInfo);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region VERITY OTP
    public void VerityOTP(View view) {
        try {
            progressBar.setVisibility(View.VISIBLE);
            final String numPhone = edPhone.getText().toString().trim();
            // Check valid
            if (numPhone.length() != 10) {
                edPhone.setError("Enter a valid mobile");
                edPhone.requestFocus();
                progressBar.setVisibility(View.GONE);
                return;
            }
            // Check phone number registered
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference users = database.getReference("CSO").child("Users").child(numPhone);
            // Check exist
            users.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        Intent verityOTP = new Intent(A2_Login.this, A2_VerityOTP.class);
                        verityOTP.putExtra("PHONE", numPhone);
                        startActivity(verityOTP);
                        progressBar.setVisibility(View.GONE);
                    } else {
                        edPhone.setError("Phone number not registered");
                        edPhone.requestFocus();
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion
}
