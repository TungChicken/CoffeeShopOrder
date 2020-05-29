package phuhq.it.coffeeshoporder.A2_Login.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.util.Strings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import phuhq.it.coffeeshoporder.A0_Main.View.MainActivity;
import phuhq.it.coffeeshoporder.A2_Login.Model.A2_Cls_User;
import phuhq.it.coffeeshoporder.G_Common.G_Common;
import phuhq.it.coffeeshoporder.R;

public class A2_Login_Pass extends AppCompatActivity {
    //region AVAILABLE
    private EditText edUser, edPass;
    private ProgressBar progressBar;
    private A2_Cls_User clsUser;
    //endregion

    //region FORM EVENTS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a2_login_password);
        addControls();
    }
    //endregion

    //region ADD CONTROLS AND LOAD
    public void addControls() {
        edUser = findViewById(R.id.a1_login_edUserName);
        edPass = findViewById(R.id.a1_login_edPass);
        progressBar = findViewById(R.id.a2_progressBar);
    }
    //endregion

    //region LOGIN
    public void onLogin(View view) {
        try {
            progressBar.setVisibility(View.VISIBLE);
            final String userName = edUser.getText().toString().trim();
            final String passWord = edPass.getText().toString().trim();
            // Check valid
            if (Strings.isEmptyOrWhitespace(userName)) {
                edUser.setError(("Please enter your username"));
                edUser.requestFocus();
                progressBar.setVisibility(View.GONE);
                return;
            } else if (Strings.isEmptyOrWhitespace(passWord)) {
                edPass.setError(("Please enter your pasword"));
                edPass.requestFocus();
                progressBar.setVisibility(View.GONE);
                return;
            }

            // Check vaild user registered
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference users = database.getReference("CSO").child("Users").child(userName);
            users.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        if (dataSnapshot.getValue() != null) {
                            clsUser = new A2_Cls_User();
                            clsUser = dataSnapshot.getValue(A2_Cls_User.class);
                            assert clsUser != null;
                            if (clsUser.getPassWord().equals(passWord)) {
                                G_Common.userLogin = userName;
                                Intent intent = new Intent(A2_Login_Pass.this, MainActivity.class);
                                intent.putExtra("USER", clsUser);
                                startActivity(intent);
                                progressBar.setVisibility(View.GONE);
                                finish();
                            } else {
                                Toast.makeText(A2_Login_Pass.this, "Wrong password. Please try again!", Toast.LENGTH_LONG).show();
                                edPass.requestFocus();
                                progressBar.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(A2_Login_Pass.this, "User not registered. Please contact admin", Toast.LENGTH_LONG).show();
                            edUser.requestFocus();
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
