package phuhq.it.coffeeshoporder.A9_Admin_Menu.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import phuhq.it.coffeeshoporder.A10_Admin_User.View.A10_Admin_AddUser;
import phuhq.it.coffeeshoporder.A10_Admin_User.View.A10_Admin_ResetPassword;
import phuhq.it.coffeeshoporder.A11_Admin_Drinks.View.A11_Admin_NewDrinks;
import phuhq.it.coffeeshoporder.R;

public class A9_Admin_Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a9_admin_menu);
    }

    public void onNewUser(View view) {
        Intent intent = new Intent(A9_Admin_Menu.this, A10_Admin_AddUser.class);
        startActivity(intent);
    }
    public void onResetUser(View view) {
        Intent intent = new Intent(A9_Admin_Menu.this, A10_Admin_ResetPassword.class);
        startActivity(intent);
    }
    public void onNewDrink(View view) {
        Intent intent = new Intent(A9_Admin_Menu.this, A11_Admin_NewDrinks.class);
        startActivity(intent);
    }
}
