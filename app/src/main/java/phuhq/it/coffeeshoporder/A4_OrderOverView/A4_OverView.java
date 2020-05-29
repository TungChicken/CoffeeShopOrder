package phuhq.it.coffeeshoporder.A4_OrderOverView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import phuhq.it.coffeeshoporder.A3_OrderDetails.Model.A3_Drinks;
import phuhq.it.coffeeshoporder.R;

public class A4_OverView extends AppCompatActivity {
    //region  AVAILABLE
    private ListView lvDrink;
    private List<A3_Drinks> drinkListOrder;
    private TextView tvOrderTotal;
    //endregion

    //region FORM EVENTS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a4_over_view);
        mainLoad();
    }
    //endregion

    //region ADD CONTROLS AND LOAD
    public void addControls() {
        try {
            lvDrink = findViewById(R.id.a4_listView);
            tvOrderTotal = findViewById(R.id.a4_tv_OrderTotal);
//            Intent intent = getIntent();
//            ArrayList<A3_Drinks> drinkList = (ArrayList<A3_Drinks>) intent.getSerializableExtra("ORDER");
//            drinkListOrder = new ArrayList<>();
//
//            assert drinkList != null;
//            for (int i = 0; i < drinkList.size(); i++) {
//                if (drinkList.get(i).getNowQty() > 0) {
//                    drinkListOrder.add(drinkList.get(i));
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mainLoad() {
        addControls();
        showDataToListView();
    }

    public void showDataToListView() {
        try {
            // Xóa dữ liệu cũ trên lưới
            lvDrink.setAdapter(null);
            // Khởi tạo apdater
            A4_OverViewAdapter detailsAdapter = new A4_OverViewAdapter(this, R.layout.a4_over_view_item, drinkListOrder);

            // Gán giá trị adapter cho listview
            this.lvDrink.setAdapter(detailsAdapter);

            // Tính tổng order
            double orderTotal = 0;
            for (int i = 0; i < drinkListOrder.size(); i++) {
                orderTotal += drinkListOrder.get(i).getNowQty() * drinkListOrder.get(i).getPrice();
            }
            tvOrderTotal.setText(String.valueOf(orderTotal));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region ON TOUCH
    public void OrderNow(View view) {
        try {
            Intent intent = new Intent(A4_OverView.this, A4_OrderComplete.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion
}
