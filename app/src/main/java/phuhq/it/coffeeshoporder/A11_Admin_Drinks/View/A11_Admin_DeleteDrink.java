package phuhq.it.coffeeshoporder.A11_Admin_Drinks.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import phuhq.it.coffeeshoporder.A3_OrderDetails.Model.A3_Cls_Drinks;
import phuhq.it.coffeeshoporder.R;

public class A11_Admin_DeleteDrink extends AppCompatActivity {
    private Spinner spDrinkID;
    private TextView drinkID;
    private ArrayList<A3_Cls_Drinks> listDrink = new ArrayList<>();
    private ArrayList<String> listDrinkName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a11_admin_delete_drink);
        addControls();
        getDataFireBase();
        changeDataOnView();
    }

    //region MAIN LOAD
    public void addControls() {
        spDrinkID = findViewById(R.id.a10_reset_edUserID);
        drinkID = findViewById(R.id.drinkID);
    }

    //endregion

    //region GET LIST DRINK
    public void getDataFireBase() {
        try {
            // Get list from Firebase
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference users = database.getReference("CSO").child("TBM_Drink");
            users.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        listDrink.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            A3_Cls_Drinks drinks = ds.getValue(A3_Cls_Drinks.class);
                            listDrink.add(drinks);
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
        listDrinkName.clear();
        for (int i = 0; i < listDrink.size(); i++) {
            listDrinkName.add(listDrink.get(i).getDrinkName());
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listDrinkName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDrinkID.setAdapter(adapter);

    }
    //endregion

    //region ON TOUCH
    public void onCancel(View view) {
        A11_Admin_DeleteDrink.this.finish();
    }

    public void onDeleteDrink(View view) {
        onMessageAlertInfo();
    }

    //endregion

    //region DELETE DRINK
    public void changeDataOnView() {
        try {
            spDrinkID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String drinkName = spDrinkID.getSelectedItem().toString();
                    A3_Cls_Drinks clsDrinks = getDrinkInfo(drinkName);
                    setDataToView(clsDrinks);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public A3_Cls_Drinks getDrinkInfo(String drinkName) {
        A3_Cls_Drinks clsDrinks = new A3_Cls_Drinks();
        try {
            for (int i = 0; i < listDrink.size(); i++) {
                if (listDrink.get(i).getDrinkName().equals(drinkName)) {
                    clsDrinks = listDrink.get(i);
                    return clsDrinks;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clsDrinks;
    }

    public void setDataToView(A3_Cls_Drinks clsDrinks) {
        try {
            drinkID.setText(String.valueOf(clsDrinks.getDrinkID()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteDrink() {
        try {
            String dID = drinkID.getText().toString();
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference reference = database.getReference("CSO").child("TBM_Drink");
            reference.child(dID).removeValue();
            getDataFireBase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onMessageAlertInfo() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirm delete drink");
            builder.setMessage("Are you sure delete this drink ???");

            DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i) {
                        case DialogInterface.BUTTON_POSITIVE:
                            deleteDrink();
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