package phuhq.it.coffeeshoporder.A11_Admin_Drinks.View;

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

import java.util.ArrayList;

import phuhq.it.coffeeshoporder.A10_Admin_User.View.A10_Admin_ResetPassword;
import phuhq.it.coffeeshoporder.A3_OrderDetails.Model.A3_Cls_Drinks;
import phuhq.it.coffeeshoporder.R;

public class A11_Admin_NewDrinks extends AppCompatActivity {
    ArrayList<A3_Cls_Drinks> drinkList = new ArrayList<>();
    private EditText edDrinkID, edDrinkName, edPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a11_admin_new_drink);
        addControls();
        mainLoad();
    }

    public void addControls() {
        edDrinkID = findViewById(R.id.a11_newDrink_edDrinkID);
        edDrinkName = findViewById(R.id.a11_newDrink_edDrinkName);
        edPrice = findViewById(R.id.a11_newDrink_edPrice);
    }

    public A3_Cls_Drinks getDataFromView() {
        A3_Cls_Drinks a3ClsDrinks = new A3_Cls_Drinks();
        a3ClsDrinks.setDrinkID(Integer.parseInt(edDrinkID.getText().toString()));
        a3ClsDrinks.setDrinkName(edDrinkName.getText().toString());
        a3ClsDrinks.setPrice(Integer.parseInt(edPrice.getText().toString()));

        return a3ClsDrinks;
    }

    public void mainLoad() {
        getDrinkListCurrent();

    }

    public void onCreateDrink(View view) {
        try {
            addNewDrink();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCancel(View view) {
        A11_Admin_NewDrinks.this.finish();
    }

    public void getDrinkListCurrent() {
        try {
            // Get list from Firebase
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference drinks = database.getReference("CSO").child("TBM_Drink");
            drinks.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        drinkList.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            A3_Cls_Drinks drink = ds.getValue(A3_Cls_Drinks.class);
                            drinkList.add(drink);
                        }
                        setNextDrinkID();
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

    public int getMaxIDDrink() {
        int drinkID = 1;
        try {
            for (int i = 0; i < drinkList.size(); i++) {
                if (drinkList.get(i).getDrinkID() >= drinkID) {
                    drinkID = drinkList.get(i).getDrinkID();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drinkID;
    }

    public void setNextDrinkID() {
        int nextID = getMaxIDDrink() + 1;
        edDrinkID.setText(String.valueOf(nextID));
    }

    public void addNewDrink() {
        try {
            A3_Cls_Drinks a3ClsDrinks = new A3_Cls_Drinks();
            a3ClsDrinks = getDataFromView();
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            database.child("CSO").child("TBM_Drink").child(edDrinkID.getText().toString()).setValue(a3ClsDrinks);
            Toast.makeText(this, "Add a new drink successful", Toast.LENGTH_SHORT).show();
            A11_Admin_NewDrinks.this.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
