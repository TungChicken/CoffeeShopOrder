package phuhq.it.coffeeshoporder.A10_Admin_User.View;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class A10_Admin_UpdateUser extends AppCompatActivity {
    private ArrayList<A2_Cls_User> listUser = new ArrayList<>();
    private ArrayList<String> listUserID = new ArrayList<>();
    private Spinner spUserID, spPermission;
    private EditText fullname, dateofbirth, phone, address;
    private RadioButton rdMale, rdFeMale;

    //region FORM EVENTS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a10_admin_update_user);
        addControls();
        mainLoad();
    }
    //endregion

    //region MAIN LOAD
    public void addControls() {
        try {
            spUserID = findViewById(R.id.a11_sp_userid);
            spPermission = findViewById(R.id.a10_addUser_spPermission);
            fullname = findViewById(R.id.a10_addUser_edFullName);
            dateofbirth = findViewById(R.id.a10_addUser_edDate);
            phone = findViewById(R.id.a10_addUser_edPhone);
            address = findViewById(R.id.a10_addUser_edAddress);
            rdMale = findViewById(R.id.a10_addUser_rdMale);
            rdFeMale = findViewById(R.id.a10_addUser_rdFeMale);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mainLoad() {
        addPermission();
        onDateClick();
        getDataFireBase();
        changeDataOnView();
    }

    public void setDataSpinner() {
        for (int i = 0; i < listUser.size(); i++) {
            listUserID.add(listUser.get(i).getUserID());
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listUserID);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUserID.setAdapter(adapter);

    }

    public void addPermission() {
        try {
            List<String> per = new ArrayList<>();
            per.add("admin");
            per.add("chef");
            per.add("employee");

            ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, per);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spPermission.setAdapter(arrayAdapter);
            spPermission.setSelection(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onDateClick() {
        dateofbirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDateDialog();
            }
        });
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
                    dateofbirth.setText(f.format(calendar.getTime()));
                }
            }, nam, thang, ngay);
            datePickerDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region ON TOUCH
    public void onCancel(View view) {
        A10_Admin_UpdateUser.this.finish();
    }

    public void onUpdate(View view) {
        upDateUser();
    }
    //endregion

    //region GET LIST USER
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

    public A2_Cls_User getUserInfo(String userID) {
        A2_Cls_User clsUser = new A2_Cls_User();
        try {
            for (int i = 0; i < listUser.size(); i++) {
                if (listUser.get(i).getUserID().equals(userID)) {
                    clsUser = listUser.get(i);
                    return clsUser;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clsUser;
    }

    public void changeDataOnView() {
        try {
            spUserID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String userID = spUserID.getSelectedItem().toString();
                    A2_Cls_User clsUser = getUserInfo(userID);
                    setDataToView(clsUser);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDataToView(A2_Cls_User clsUser) {
        try {
            fullname.setText(clsUser.getFullName());
            dateofbirth.setText(clsUser.getDateOfBirth());
            phone.setText(clsUser.getPhone());
            address.setText(clsUser.getAddress());
            if (clsUser.getPermission().equals("admin")) {
                spPermission.setSelection(0);
            } else if (clsUser.getPermission().equals("chef")) {
                spPermission.setSelection(1);
            } else {
                spPermission.setSelection(2);
            }

            if (clsUser.getGender().equals("Male")) {
                rdMale.setChecked(true);
                rdFeMale.setChecked(false);
            } else {
                rdMale.setChecked(false);
                rdFeMale.setChecked(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region UPDATE
    public A2_Cls_User onGetDataFromView() {
        A2_Cls_User clsUser = new A2_Cls_User();
        try {
            clsUser.setUserID(spUserID.getSelectedItem().toString());
            clsUser.setPermission(spPermission.getSelectedItem().toString());
            clsUser.setFullName(fullname.getText().toString());
            clsUser.setDateOfBirth(dateofbirth.getText().toString());
            clsUser.setPhone(phone.getText().toString());
            clsUser.setAddress(address.getText().toString());
            if (rdMale.isChecked())
                clsUser.setGender("Male");
            else
                clsUser.setGender("FeMale");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return clsUser;
    }

    private void upDateUser() {
        try {
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            A2_Cls_User clsInfo = onGetDataFromView();

            database.child("CSO").child("TBM_Users").child(clsInfo.getUserID()).child("address").setValue(clsInfo.getAddress());
            database.child("CSO").child("TBM_Users").child(clsInfo.getUserID()).child("dateOfBirth").setValue(clsInfo.getDateOfBirth());
            database.child("CSO").child("TBM_Users").child(clsInfo.getUserID()).child("fullName").setValue(clsInfo.getFullName());
            database.child("CSO").child("TBM_Users").child(clsInfo.getUserID()).child("gender").setValue(clsInfo.getGender());
            database.child("CSO").child("TBM_Users").child(clsInfo.getUserID()).child("permission").setValue(clsInfo.getPermission());
            database.child("CSO").child("TBM_Users").child(clsInfo.getUserID()).child("phone").setValue(clsInfo.getPhone());
            A10_Admin_UpdateUser.this.finish();
            Toast.makeText(this, "Update user successful", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion
}