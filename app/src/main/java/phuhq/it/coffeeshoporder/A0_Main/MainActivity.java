package phuhq.it.coffeeshoporder.A0_Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import phuhq.it.coffeeshoporder.A3_OrderDetails.A3_OrderDetails;
import phuhq.it.coffeeshoporder.A3_OrderDetails.A3_OrderDialog;
import phuhq.it.coffeeshoporder.A3_OrderDetails.A3_OrderDialogMore;
import phuhq.it.coffeeshoporder.R;

public class MainActivity extends AppCompatActivity {
    //region AVAILABLE
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
    }
    //endregion

    //region ON TOUCH
    public void onSmoothieClick(View view) {
        showDialogOrder(1);
    }

    public void onCoffeeClick(View view) {
        showDialogOrder(2);
    }

    public void onOrderDetails(View view) {
        Intent orderDetails = new Intent(MainActivity.this, A3_OrderDetails.class);
        startActivity(orderDetails);
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
