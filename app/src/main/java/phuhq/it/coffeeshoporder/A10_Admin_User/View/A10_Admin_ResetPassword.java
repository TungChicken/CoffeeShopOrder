package phuhq.it.coffeeshoporder.A10_Admin_User.View;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import phuhq.it.coffeeshoporder.A2_Login.Model.A2_Cls_User;
import phuhq.it.coffeeshoporder.R;

import static phuhq.it.coffeeshoporder.G_Common.G_Common.PASS_DEFAULT;

public class A10_Admin_ResetPassword extends AppCompatActivity {
    private Spinner spUserID;
    private ArrayList<A2_Cls_User> listUser = new ArrayList<>();
    private ArrayList<String> listUserID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a10_admin_reset_password);
        addControls();
        getDataFireBase();
    }

    public void addControls() {
        spUserID = findViewById(R.id.a10_reset_edUserID);
    }

    public void getDataFireBase() {
        try {
            // Get list from Firebase
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference users = database.getReference("CSO").child("TBM_Users");
            users.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        listUser.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            A2_Cls_User user = ds.getValue(A2_Cls_User.class);
                            listUser.add(user);
                        }
                        setDataSpinner();
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

    public void setDataSpinner() {
        for (int i = 0; i < listUser.size(); i++) {
            listUserID.add(listUser.get(i).getUserID());
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listUserID);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUserID.setAdapter(adapter);

    }

    public void onCancel(View view) {
        A10_Admin_ResetPassword.this.finish();
    }

    public void onResetPassword(View view) {
        resetPassword();
    }

    private void resetPassword() {
        try {
            final String userID = spUserID.getSelectedItem().toString();
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference userMaster = database.getReference("CSO").child("TBM_Users").child(userID);
            userMaster.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        DatabaseReference user = database.getReference("CSO").child("TBM_Users");
                        user.child(userID).child("passWord").setValue(PASS_DEFAULT);
                        Toast.makeText(A10_Admin_ResetPassword.this, "Reset password successful", Toast.LENGTH_SHORT).show();
                        A10_Admin_ResetPassword.this.finish();
                    } else {
                        Toast.makeText(A10_Admin_ResetPassword.this, "Wrong UserID, please try again!", Toast.LENGTH_LONG).show();
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
}
