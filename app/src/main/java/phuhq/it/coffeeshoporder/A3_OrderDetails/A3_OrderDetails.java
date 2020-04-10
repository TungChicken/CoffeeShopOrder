package phuhq.it.coffeeshoporder.A3_OrderDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import phuhq.it.coffeeshoporder.A4_OrderOverView.A4_OverView;
import phuhq.it.coffeeshoporder.R;

public class A3_OrderDetails extends AppCompatActivity {
    //region  AVAILABLE
    private ListView lvDrink;
    private List<A3_Drink> drinkList;
    //endregion

    //region FORM EVENTS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a3_order_details);
        lvDrink = findViewById(R.id.a3_listView);
        LoadDrinkList();
    }

    //endregion

    //region TEMP - CREATE DATA FOR TEST
    public void LoadDrinkList() {
        try {
            // Xóa dữ liệu cũ trên lưới
            lvDrink.setAdapter(null);

            // Khởi tạo dữ liệu tạm
            drinkList = new ArrayList<>();
            A3_Drink d1 = new A3_Drink();
            d1.setDrinkName("Coffee");
            d1.setPrice(2.0);
            d1.setImage(R.drawable.c1);

            A3_Drink d2 = new A3_Drink();
            d2.setDrinkName("Juice");
            d2.setPrice(1.5);
            d2.setImage(R.drawable.c2);

            A3_Drink d3 = new A3_Drink();
            d3.setDrinkName("Orange Smoothie");
            d3.setPrice(1.8);
            d3.setImage(R.drawable.c3);

            A3_Drink d4 = new A3_Drink();
            d4.setDrinkName("Coffee");
            d4.setPrice(2.0);
            d4.setImage(R.drawable.c1);

            A3_Drink d5 = new A3_Drink();
            d5.setDrinkName("Juice");
            d5.setPrice(1.6);
            d5.setImage(R.drawable.c2);

            A3_Drink d6 = new A3_Drink();
            d6.setDrinkName("Orange Smoothie");
            d6.setPrice(2.5);
            d6.setImage(R.drawable.c3);

            A3_Drink d7 = new A3_Drink();
            d7.setDrinkName("Coffee");
            d7.setPrice(3.1);
            d7.setImage(R.drawable.c1);

            A3_Drink d8 = new A3_Drink();
            d8.setDrinkName("Juice");
            d8.setPrice(5.4);
            d8.setImage(R.drawable.c2);

            A3_Drink d9 = new A3_Drink();
            d9.setDrinkName("Orange Smoothie");
            d9.setPrice(6.0);
            d9.setImage(R.drawable.c3);

            drinkList.add(d1);
            drinkList.add(d2);
            drinkList.add(d3);
            drinkList.add(d4);
            drinkList.add(d5);
            drinkList.add(d6);
            drinkList.add(d7);
            drinkList.add(d8);
            drinkList.add(d9);
            // Khởi tạo apdater
            A3_OrderDetailsAdapter detailsAdapter = new A3_OrderDetailsAdapter(this, R.layout.a3_order_details_item, drinkList);

            // Gán giá trị adapter cho listview
            this.lvDrink.setAdapter(detailsAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region ON TOUCH
    public void AddOrder(View view) {
        try {
            ArrayList<A3_Drink> drinkLists = new ArrayList<>(this.drinkList);
            Intent overViewOrder = new Intent(A3_OrderDetails.this, A4_OverView.class);
            overViewOrder.putExtra("ORDER", drinkLists);
            startActivity(overViewOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion
}
