package phuhq.it.coffeeshoporder.A9_Admin_Menu.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import phuhq.it.coffeeshoporder.A10_Admin_User.View.A10_Admin_AddUser;
import phuhq.it.coffeeshoporder.A10_Admin_User.View.A10_Admin_Delete_User;
import phuhq.it.coffeeshoporder.A10_Admin_User.View.A10_Admin_ResetPassword;
import phuhq.it.coffeeshoporder.A10_Admin_User.View.A10_Admin_UpdateUser;
import phuhq.it.coffeeshoporder.A5_ChangePassword.View.A5_ChangePassword;
import phuhq.it.coffeeshoporder.R;

public class A9_Admin_Menu_User extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a9_admin_menu_user);
    }

    public void onNewUser(View view){
        Intent intent = new Intent(A9_Admin_Menu_User.this, A10_Admin_AddUser.class);
        startActivity(intent);
    }

    public void onResetPass(View view){
        Intent intent = new Intent(A9_Admin_Menu_User.this, A10_Admin_ResetPassword.class);
        startActivity(intent);
    }
    public void onUpdateUser(View view){
        Intent intent = new Intent(A9_Admin_Menu_User.this, A10_Admin_UpdateUser.class);
        startActivity(intent);
    }

    public void onChangePassWord(View view){
        Intent intent = new Intent(A9_Admin_Menu_User.this, A5_ChangePassword.class);
        startActivity(intent);
    }
    public void onDeleteUser(View view){
        Intent intent = new Intent(A9_Admin_Menu_User.this, A10_Admin_Delete_User.class);
        startActivity(intent);
    }
}