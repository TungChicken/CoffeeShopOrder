package phuhq.it.coffeeshoporder.A6_UserInfo.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.Strings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import phuhq.it.coffeeshoporder.A0_Main.View.MainActivity;
import phuhq.it.coffeeshoporder.A2_Login.Model.A2_Cls_User;
import phuhq.it.coffeeshoporder.A2_Login.View.A2_Login_Pass;
import phuhq.it.coffeeshoporder.A5_ChangePassword.View.A5_ChangePassword;
import phuhq.it.coffeeshoporder.G_Common.G_Common;
import phuhq.it.coffeeshoporder.R;

import static phuhq.it.coffeeshoporder.G_Common.G_Common.userLogin;

public class A6_UserInfo extends AppCompatActivity {

    //region KHAI BÁO BIẾN TOÀN CỤC
    public A2_Cls_User clsUser;
    // controls
    private TextView fullname, fullname2, gender, email, address, username, accountType, dateofbirth, changepass, logout, editInfo;
    private ImageView imgUser;
    //endregion

    //region FORM EVENTS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a6_user_info);
        mainLoad();
    }
    //endregion

    //region ADD CONTROLS AND LOAD
    // Add controls
    public void addControls() {
        try {
            fullname = findViewById(R.id.a2_tvFullname);
            fullname2 = findViewById(R.id.a2_tvFullname2);
            gender = findViewById(R.id.a2_tvGender);
            email = findViewById(R.id.a2_tvEmail);
            address = findViewById(R.id.a2_tvAddress);
            username = findViewById(R.id.a2_tvPhoneNumber);
            accountType = findViewById(R.id.a2_tvAccountType);
            dateofbirth = findViewById(R.id.a2_tvDateofBirth);
            changepass = findViewById(R.id.a2_tvChangePass);
            logout = findViewById(R.id.a2_tvLogout);
            editInfo = findViewById(R.id.a2_tvUpdate);
            imgUser = findViewById(R.id.a2_imgUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mainLoad() {
        try {
            addControls();
            getDataUserLogin();
            //clsUser = (A2_Cls_User) getIntent().getSerializableExtra("USER");
            //getDataToView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDataToView() {
        try {
            fullname.setText(clsUser.getFullName());
            fullname2.setText(clsUser.getFullName());
            gender.setText(clsUser.getGender());
            email.setText(clsUser.getEmail());
            address.setText(clsUser.getAddress());
            dateofbirth.setText(clsUser.getDateOfBirth());
            accountType.setText(clsUser.getPermission());
            username.setText(userLogin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region GET DATA USER
    public void getDataUserLogin() {
        try {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference users = database.getReference("CSO").child("TBM_Users").child(userLogin);
            users.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        if (dataSnapshot.getValue() != null) {
                            clsUser = new A2_Cls_User();
                            clsUser = dataSnapshot.getValue(A2_Cls_User.class);
                            getDataToView();
                        } else {
                            Toast.makeText(A6_UserInfo.this, "Can't get user info. Please contact admin", Toast.LENGTH_LONG).show();
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

    //region ON TOUCH
    public void onChangePassword(View view) {
        try {
            Intent intent = new Intent(A6_UserInfo.this, A5_ChangePassword.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onLogout(View view) {
        Intent intent = new Intent(A6_UserInfo.this, A2_Login_Pass.class);
        startActivity(intent);
        finish();
    }
    //endregion
}
