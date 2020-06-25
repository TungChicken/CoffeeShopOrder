package phuhq.it.coffeeshoporder.A9_Admin_Menu.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import phuhq.it.coffeeshoporder.A11_Admin_Drinks.View.A11_Admin_DeleteDrink;
import phuhq.it.coffeeshoporder.A11_Admin_Drinks.View.A11_Admin_NewDrinks;
import phuhq.it.coffeeshoporder.A11_Admin_Drinks.View.A11_Admin_UpdateDrink;
import phuhq.it.coffeeshoporder.R;

public class A9_Admin_Menu_Drink extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a9_admin_menu_drink);
    }


    public void onNewDrink(View view){
        Intent intent = new Intent(A9_Admin_Menu_Drink.this, A11_Admin_NewDrinks.class);
        startActivity(intent);
    }

    public void onUpdateDrink(View view){
        Intent intent = new Intent(A9_Admin_Menu_Drink.this, A11_Admin_UpdateDrink.class);
        startActivity(intent);
    }

    public void onDeleteDrink(View view){
        Intent intent = new Intent(A9_Admin_Menu_Drink.this, A11_Admin_DeleteDrink.class);
        startActivity(intent);
    }
}