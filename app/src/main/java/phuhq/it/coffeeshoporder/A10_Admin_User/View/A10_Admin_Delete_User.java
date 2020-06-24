package phuhq.it.coffeeshoporder.A10_Admin_User.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import phuhq.it.coffeeshoporder.A2_Login.Model.A2_Cls_User;
import phuhq.it.coffeeshoporder.R;

public class A10_Admin_Delete_User extends AppCompatActivity {
    private Spinner spUserID;
    private ArrayList<A2_Cls_User> listUser = new ArrayList<>();
    private ArrayList<String> listUserID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a10_admin_delete_user);
        addControls();
        getDataFireBase();
    }

    //region MAIN LOAD
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
        listUserID.clear();
        for (int i = 0; i < listUser.size(); i++) {
            if (!listUser.get(i).getUserID().equals("Admin"))
                listUserID.add(listUser.get(i).getUserID());
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listUserID);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUserID.setAdapter(adapter);

    }
    //endregion

    //region ON TOUCH
    public void onCancel(View view) {
        A10_Admin_Delete_User.this.finish();
    }

    public void onDeleteUser(View view) {
        onMessageAlertInfo();
    }

    public void deleteUser() {
        try {
            String userID = spUserID.getSelectedItem().toString();
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference reference = database.getReference("CSO").child("TBM_Users");
            reference.child(userID).removeValue();
            getDataFireBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region DELETE USER
    public void onMessageAlertInfo() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirm delete user");
            builder.setMessage("Are you sure delete this user ???");

            DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i) {
                        case DialogInterface.BUTTON_POSITIVE:
                            deleteUser();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            builder.setPositiveButton("OK", clickListener);
            builder.setNegativeButton("Cancel", clickListener);
            builder.setIcon(R.drawable.a10_admin_ic_delete_user);

            Dialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion
}