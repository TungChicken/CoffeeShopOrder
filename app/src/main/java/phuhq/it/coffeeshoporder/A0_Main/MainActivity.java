package phuhq.it.coffeeshoporder.A0_Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import phuhq.it.coffeeshoporder.A3_OrderDetails.A3_DialogOrder;
import phuhq.it.coffeeshoporder.R;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
    //region AVAILABLE
    private CardView btnCoffee;
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
        btnCoffee = findViewById(R.id.view3);
    }

    public void mainLoad() {
        addControls();
    }
    //endregion

    //region ON TOUCH
    public void onCoffeeClick(View view) {
//        Intent order = new Intent(MainActivity.this, A3_DialogOrder.class);
//        startActivity(order);
        A3_DialogOrder dialogOrder = new A3_DialogOrder(MainActivity.this);
        dialogOrder.show();
    }
    //endregion
}
