package phuhq.it.coffeeshoporder.A0_Main.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import phuhq.it.coffeeshoporder.A2_Login.Model.A2_Cls_User;
import phuhq.it.coffeeshoporder.A3_OrderDetails.View.A3_OrderDetails;
import phuhq.it.coffeeshoporder.A3_OrderDetails.View.A3_OrderDialog;
import phuhq.it.coffeeshoporder.A3_OrderDetails.View.A3_OrderDialogMore;
import phuhq.it.coffeeshoporder.A6_UserInfo.View.A6_UserInfo;
import phuhq.it.coffeeshoporder.A7_TableList.View.A7_TableList;
import phuhq.it.coffeeshoporder.R;

import static phuhq.it.coffeeshoporder.G_Common.G_Common.flagTableList;

public class MainActivity extends AppCompatActivity {
    //region AVAILABLE
    //public A2_Cls_User clsUser;
    //endregion

    //region FROM EVENT
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLoad();
    }
    //endregion

    //region ADD CONTROLS AND LOAD
    public void addControls() {
    }

    public void mainLoad() {
        addControls();
        //clsUser = (A2_Cls_User) getIntent().getSerializableExtra("USER");
    }
    //endregion

    //region ON TOUCH
    public void onSmoothieClick(View view) {
        showDialogOrder(1);
    }

    public void onCoffeeClick(View view) {
        showDialogOrder(2);
    }

    public void onOrder(View view) {
        flagTableList = false; // order, pending
        Intent intent = new Intent(MainActivity.this, A7_TableList.class);
        startActivity(intent);
    }

    public void onInvoice(View view) {
        flagTableList = true; // invoice
        Intent intent = new Intent(MainActivity.this, A7_TableList.class);
        startActivity(intent);
    }

    public void onUserAccount(View view) {
        Intent intent = new Intent(MainActivity.this, A6_UserInfo.class);
        //intent.putExtra("USER", clsUser);
        startActivity(intent);
    }
    //endregion

    //region BOTTOM DIALOG
    private void showDialogOrder(int screen) {
        try {
            // Only call xml file
//            View dialogView = getLayoutInflater().inflate(R.layout.a3_order_dialog, null);
//            BottomSheetDialog dialog = new BottomSheetDialog(this);
//            dialog.setContentView(dialogView);
//            dialog.show();

            // Call fragment
            if (screen == 1) {
                A3_OrderDialogMore orderDialogMore = new A3_OrderDialogMore();
                orderDialogMore.show(getSupportFragmentManager(), orderDialogMore.getTag());
            } else {
                A3_OrderDialog orderDialog = new A3_OrderDialog();
                orderDialog.show(getSupportFragmentManager(), orderDialog.getTag());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion
}
