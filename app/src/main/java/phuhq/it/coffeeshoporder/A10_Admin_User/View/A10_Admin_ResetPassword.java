package phuhq.it.coffeeshoporder.A10_Admin_User.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import phuhq.it.coffeeshoporder.R;

import static phuhq.it.coffeeshoporder.G_Common.G_Common.PASS_DEFAULT;
import static phuhq.it.coffeeshoporder.G_Common.G_Common.STATUS_ORDER;

public class A10_Admin_ResetPassword extends AppCompatActivity {
    private EditText edUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a10_admin_reset_password);
        addControls();
    }

    public void addControls() {
        edUserID = findViewById(R.id.a10_reset_edUserID);
    }

    public void onCancel(View view) {
        A10_Admin_ResetPassword.this.finish();
    }

    public void onResetPassword(View view) {
        resetPassword();
    }

    private void resetPassword() {
        try {
            final String userID = edUserID.getText().toString();
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
