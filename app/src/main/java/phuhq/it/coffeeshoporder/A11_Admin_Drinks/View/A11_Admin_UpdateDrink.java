package phuhq.it.coffeeshoporder.A11_Admin_Drinks.View;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

import phuhq.it.coffeeshoporder.A3_OrderDetails.Model.A3_Cls_Drinks;
import phuhq.it.coffeeshoporder.R;

public class A11_Admin_UpdateDrink extends AppCompatActivity {
    private EditText drinkID, dinkNameUpdate, price;
    private Spinner spDrinkName;
    private ArrayList<A3_Cls_Drinks> listDrink = new ArrayList<>();
    private ArrayList<String> listDrinkName = new ArrayList<>();

    //region FORM EVENTS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a11_admin_update_drink);
        addControls();
        mainLoad();
    }
    //endregion

    //region MAIN LOAD
    public void addControls() {
        drinkID = findViewById(R.id.a11_newDrink_edDrinkID);
        dinkNameUpdate = findViewById(R.id.a11_newDrink_edName);
        price = findViewById(R.id.a11_newDrink_edPrice2);
        spDrinkName = findViewById(R.id.a11_newDrink_edDrinkName);
    }

    public void mainLoad() {
        getDataFireBase();
        changeDataOnView();
    }
    //endregion

    //region ON TOUCH
    public void onCancel(View view) {
        A11_Admin_UpdateDrink.this.finish();
    }

    public void onUpdate(View view) {
        upDateDrink();
    }

    public void changeDataOnView() {
        try {
            spDrinkName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String drinkName = spDrinkName.getSelectedItem().toString();
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
            dinkNameUpdate.setText(clsDrinks.getDrinkName());
            price.setText(String.valueOf(clsDrinks.getPrice()));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        for (int i = 0; i < listDrink.size(); i++) {
            listDrinkName.add(listDrink.get(i).getDrinkName());
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listDrinkName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDrinkName.setAdapter(adapter);

    }
    //endregion

    //region UPDATE
    public A3_Cls_Drinks onGetDataFromView() {
        A3_Cls_Drinks clsDrinks = new A3_Cls_Drinks();
        try {
            clsDrinks.setDrinkID(Integer.parseInt(drinkID.getText().toString()));
            clsDrinks.setDrinkName(dinkNameUpdate.getText().toString());
            clsDrinks.setPrice(Integer.parseInt(price.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clsDrinks;
    }

    private void upDateDrink() {
        try {
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            A3_Cls_Drinks clsInfo = onGetDataFromView();

            database.child("CSO").child("TBM_Drink").child(String.valueOf(clsInfo.getDrinkID())).child("drinkName").setValue(clsInfo.getDrinkName());
            database.child("CSO").child("TBM_Drink").child(String.valueOf(clsInfo.getDrinkID())).child("price").setValue(clsInfo.getPrice());

            Toast.makeText(this, "Update drink successful", Toast.LENGTH_SHORT).show();
            A11_Admin_UpdateDrink.this.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion
}