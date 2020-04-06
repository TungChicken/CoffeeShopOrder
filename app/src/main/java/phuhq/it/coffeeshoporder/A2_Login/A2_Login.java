package phuhq.it.coffeeshoporder.A2_Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import phuhq.it.coffeeshoporder.R;

public class A2_Login extends AppCompatActivity {
    //region AVAILABLE
    private EditText edPhone;
    //endregion

    //region FORM EVENTS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a2_login);
        addControls();
    }
    //endregion

    //region ADD CONTROLS AND LOAD
    public void addControls() {
        edPhone = findViewById(R.id.a1_login_edPhone);
    }
    //endregion

    //region VERITY OTP
    public void VerityOTP(View view) {
        try {
            final String numPhone = edPhone.getText().toString().trim();
            // Check valid
            if (numPhone.isEmpty() || numPhone.length() > 10) {
                edPhone.setError("Enter a valid mobile");
                edPhone.requestFocus();
            } else {
                Intent verityOTP = new Intent(A2_Login.this, A2_VerityOTP.class);
                verityOTP.putExtra("PHONE", numPhone);
                startActivity(verityOTP);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion
}
