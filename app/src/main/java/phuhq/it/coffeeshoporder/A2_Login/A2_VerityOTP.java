package phuhq.it.coffeeshoporder.A2_Login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import phuhq.it.coffeeshoporder.R;

public class A2_VerityOTP extends AppCompatActivity {
    //region AVAILABLE
    private EditText edCode;
    //endregion

    //region FORM EVENTS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a2_verity_otp);
    }
    //endregion

    //region ADD CONTROLS AND LOAD
    public void addControls() {
        edCode = findViewById(R.id.a1_verity_edOTP);
    }

    public void mainLoad() {
        addControls();
        senVerificationCode("");
    }
    //endregion

    //region SEND VERIFICATION CODE
    // send request OTP to Firebase
    public void senVerificationCode(String numPhone) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //endregion
}
