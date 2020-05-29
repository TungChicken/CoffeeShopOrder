package phuhq.it.coffeeshoporder.A4_OrderOverView.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import phuhq.it.coffeeshoporder.A3_OrderDetails.Model.A3_Cls_Drinks;
import phuhq.it.coffeeshoporder.A3_OrderDetails.Model.A3_Cls_Order;
import phuhq.it.coffeeshoporder.A4_OrderOverView.Presenter.A4_OverViewAdapter;
import phuhq.it.coffeeshoporder.R;

import static phuhq.it.coffeeshoporder.G_Common.G_Common.STATUS_PENDING;
import static phuhq.it.coffeeshoporder.G_Common.G_Common.getDecimalFormattedString;
import static phuhq.it.coffeeshoporder.G_Common.G_Common.tableOrder;

public class A4_OverView extends AppCompatActivity {
    //region  AVAILABLE
    private ListView lvDrink;
    private ArrayList<A3_Cls_Drinks> drinkListOrder;
    private TextView tvOrderTotal;
    private int orderTotal = 0;
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
            Intent intent = getIntent();
            ArrayList<A3_Cls_Drinks> drinkList = (ArrayList<A3_Cls_Drinks>) intent.getSerializableExtra("ORDERS");
            drinkListOrder = new ArrayList<>();

            assert drinkList != null;
            for (int i = 0; i < drinkList.size(); i++) {
                if (drinkList.get(i).getNowQty() > 0) {
                    drinkListOrder.add(drinkList.get(i));
                }
            }
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
            for (int i = 0; i < drinkListOrder.size(); i++) {
                orderTotal += drinkListOrder.get(i).getNowQty() * drinkListOrder.get(i).getPrice();
            }
            tvOrderTotal.setText(getDecimalFormattedString(String.valueOf(orderTotal)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region SET DATA ORDER
    private void setDataOrder() {
        try {
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            A3_Cls_Order clsOrder =  new A3_Cls_Order(tableOrder, "order", drinkListOrder, orderTotal);
            database.child("CSO").child("TBT_Orders").child(tableOrder).setValue(clsOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateStatusTable() {
        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("CSO").child("TBM_Tables");
            myRef.child(tableOrder).child("status").setValue(STATUS_PENDING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region ON TOUCH
    public void OrderNow(View view) {
        try {
            setDataOrder();
            updateStatusTable();
            Intent intent = new Intent(A4_OverView.this, A4_OrderComplete.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion
}
