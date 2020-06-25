package phuhq.it.coffeeshoporder.A10_Admin_User.View;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import phuhq.it.coffeeshoporder.A2_Login.Model.A2_Cls_User;
import phuhq.it.coffeeshoporder.R;

import static phuhq.it.coffeeshoporder.G_Common.G_Common.getASCIICodeFromText;

public class A10_Admin_AddUser extends AppCompatActivity {
    private Spinner spPermission;
    private EditText edUserID, edPass, edFullName, edDate, edPhone, edAddress;
    private RadioButton rdMale, rdFeMale;
    private boolean flagCheck = false;
    //private String userID;

    //region FORM EVENT
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a10_admin_add_user);
        addControls();
        mainLoad();
    }
    //endregion

    //region MAIN LOAD
    public void addControls() {
        spPermission = findViewById(R.id.a10_addUser_spPermission);
        edUserID = findViewById(R.id.a10_addUser_edUserID);
        edPass = findViewById(R.id.a10_addUser_edPass);
        edFullName = findViewById(R.id.a10_addUser_edFullName);
        edDate = findViewById(R.id.a10_addUser_edDate);
        edPhone = findViewById(R.id.a10_addUser_edPhone);
        edAddress = findViewById(R.id.a10_addUser_edAddress);
        rdMale = findViewById(R.id.a10_addUser_rdMale);
        rdFeMale = findViewById(R.id.a10_addUser_rdFeMale);
    }

    public void mainLoad() {
        try {
            addPermission();
            onDateClick();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addPermission() {
        try {
            List<String> per = new ArrayList<>();
            per.add("admin");
            per.add("chef");
            per.add("employee");

            ArrayAdapter arrayAdapter = new ArrayAdapter<String>(A10_Admin_AddUser.this, android.R.layout.simple_list_item_1, per);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spPermission.setAdapter(arrayAdapter);
            spPermission.setSelection(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openDateDialog() {
        try {
            final Calendar calendar = Calendar.getInstance();
            int nam = calendar.get(Calendar.YEAR);
            int thang = calendar.get(Calendar.MONTH);
            int ngay = calendar.get(Calendar.DAY_OF_MONTH);

            final DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(year, month, dayOfMonth);
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                    edDate.setText(f.format(calendar.getTime()));
                }
            }, nam, thang, ngay);
            datePickerDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region SET DATA USER
    public A2_Cls_User onGetDataFromView() {
        A2_Cls_User clsUser = new A2_Cls_User();
        try {
            clsUser.setUserID(edUserID.getText().toString());
            String pass = getASCIICodeFromText(edPass.getText().toString(), 2);
            clsUser.setPassWord(pass);
            clsUser.setPermission(spPermission.getSelectedItem().toString());
            clsUser.setFullName(edFullName.getText().toString());
            clsUser.setDateOfBirth(edDate.getText().toString());
            clsUser.setPhone(edPhone.getText().toString());
            clsUser.setAddress(edAddress.getText().toString());
            if (rdMale.isChecked())
                clsUser.setGender("Male");
            else
                clsUser.setGender("FeMale");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return clsUser;
    }

    private void onDateClick() {
        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDateDialog();
            }
        });
    }

    public boolean checkUserExist(String userName) {
        try {
            // Check vaild user registered
            final FirebaseDatabase[] database = {FirebaseDatabase.getInstance()};
            final DatabaseReference users = database[0].getReference("CSO").child("TBM_Users").child(userName);
            users.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        if (dataSnapshot.getValue() != null) {
                            Toast.makeText(A10_Admin_AddUser.this, "Already user in system. Please try again!", Toast.LENGTH_SHORT).show();
                            edUserID.setError("Already user in system!");
                        } else {
                            addNewUser();
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
        return flagCheck;
    }

    private void addNewUser() {
        try {
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            A2_Cls_User clsInfo = onGetDataFromView();
            database.child("CSO").child("TBM_Users").child(clsInfo.getUserID()).setValue(clsInfo);
            A10_Admin_AddUser.this.finish();
            Toast.makeText(this, "Add new user successful", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCreateUser(View view) {
        try {
            if (edUserID.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please input information", Toast.LENGTH_SHORT).show();
                edUserID.setError("Input data here!");
            } else {
                checkUserExist(edUserID.getText().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCancel(View view) {
        A10_Admin_AddUser.this.finish();
    }
    //endregion
}
