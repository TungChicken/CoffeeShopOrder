package phuhq.it.coffeeshoporder.A5_ChangePassword.View;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.util.Strings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import phuhq.it.coffeeshoporder.A2_Login.Model.A2_Cls_User;
import phuhq.it.coffeeshoporder.G_Common.G_Common;
import phuhq.it.coffeeshoporder.R;

public class A5_ChangePassword extends AppCompatActivity {
    //region AVAILABLE
    private EditText edOldPass, edNewPass, edRepeatPass;
    private ProgressBar progressBar;
    private A2_Cls_User clsUser;
    //endregion

    //region FORM EVENTS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a5_change_password);
        addControls();
    }
    //endregion

    //region ADD CONTROLS AND LOAD
    public void addControls() {
        edOldPass = findViewById(R.id.a5_change_edOldPass);
        edNewPass = findViewById(R.id.a5_change_edNewPass);
        edRepeatPass = findViewById(R.id.a5_change_edRepeat);
        progressBar = findViewById(R.id.a2_progressBar);
    }
    //endregion

    //region CHANGE PASSWORD
    public void onChangePass(View view) {
        try {
            progressBar.setVisibility(View.VISIBLE);
            final String oldPass = edOldPass.getText().toString();
            final String newPass = edNewPass.getText().toString();
            final String repeatPass = edRepeatPass.getText().toString();
            // Check valid
            if (Strings.isEmptyOrWhitespace(oldPass)) {
                edOldPass.setError(("Please enter your old password"));
                edOldPass.requestFocus();
                progressBar.setVisibility(View.GONE);
                return;
            } else if (Strings.isEmptyOrWhitespace(newPass)) {
                edNewPass.setError(("Please enter your new password"));
                edNewPass.requestFocus();
                progressBar.setVisibility(View.GONE);
                return;
            } else if (!newPass.equals(repeatPass)) {
                edRepeatPass.setError(("New password not match"));
                edRepeatPass.requestFocus();
                progressBar.setVisibility(View.GONE);
                return;
            }

            // Check vaild user registered
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference users = database.getReference("CSO").child("TBM_Users").child(G_Common.userLogin);
            users.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        if (dataSnapshot.getValue() != null) {
                            clsUser = new A2_Cls_User();
                            clsUser = dataSnapshot.getValue(A2_Cls_User.class);
                            assert clsUser != null;
                            String oldPasswordDB = G_Common.getTextFromASCIICode(clsUser.getPassWord(), 2);
                            if (oldPasswordDB.equals(oldPass)) {
                                DatabaseReference changePassword = database.getReference("CSO").child("TBM_Users");
                                String newPassMD5 = G_Common.getASCIICodeFromText(newPass, 2);
                                changePassword.child(G_Common.userLogin).child("passWord").setValue(newPassMD5);
                                Toast.makeText(A5_ChangePassword.this, "Change password successful!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                finish();
                            } else {
                                Toast.makeText(A5_ChangePassword.this, "Old password is wrong. Please try again!", Toast.LENGTH_LONG).show();
                                edOldPass.requestFocus();
                                progressBar.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(A5_ChangePassword.this, "User not registered. Please contact admin", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
