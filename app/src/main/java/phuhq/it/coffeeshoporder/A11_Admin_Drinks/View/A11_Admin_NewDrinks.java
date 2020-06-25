package phuhq.it.coffeeshoporder.A11_Admin_Drinks.View;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
        a3ClsDrinks.setImage("http://");

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
                    } else {
                        edDrinkID.setText("1");
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
            A3_Cls_Drinks a3ClsDrinks = getDataFromView();

            if (checkAlreadyDrinkName(a3ClsDrinks.getDrinkName())) {
                edDrinkName.setError("Drink name already exist");
                Toast.makeText(A11_Admin_NewDrinks.this, "Drink name already exist", Toast.LENGTH_SHORT).show();
            } else {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                database.child("CSO").child("TBM_Drink").child(edDrinkID.getText().toString()).setValue(a3ClsDrinks, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError == null) {
                            Toast.makeText(A11_Admin_NewDrinks.this, "Add new dirk successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(A11_Admin_NewDrinks.this, "Fail to add new dirk!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                A11_Admin_NewDrinks.this.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkAlreadyDrinkName(String drinkName) {
        try {
            for (int i = 0; i < drinkList.size(); i++) {
                if (drinkName.equals(drinkList.get(i).getDrinkName()))
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
