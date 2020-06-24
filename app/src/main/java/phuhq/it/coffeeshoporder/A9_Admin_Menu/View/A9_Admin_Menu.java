package phuhq.it.coffeeshoporder.A9_Admin_Menu.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import phuhq.it.coffeeshoporder.R;

public class A9_Admin_Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a9_admin_menu);
    }

    public void onUserManagement(View view) {
        Intent intent = new Intent(A9_Admin_Menu.this, A9_Admin_Menu_User.class);
        startActivity(intent);
    }
    public void onDrinkManagement(View view) {
        Intent intent = new Intent(A9_Admin_Menu.this, A9_Admin_Menu_Drink.class);
        startActivity(intent);
    }
    public void onReportManagement(View view) {
        Intent intent = new Intent(A9_Admin_Menu.this, A9_Admin_Menu_Report.class);
        startActivity(intent);
    }

}
