package phuhq.it.coffeeshoporder.A8_Chef.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import phuhq.it.coffeeshoporder.A8_Chef.Model.A8_Cls_Chef;
import phuhq.it.coffeeshoporder.A8_Chef.Model.A8_Cls_Chef_Details;
import phuhq.it.coffeeshoporder.A8_Chef.Presenter.A8_Chef_Details_Adapter;
import phuhq.it.coffeeshoporder.R;

import static phuhq.it.coffeeshoporder.G_Common.G_Common.STATUS_ORDER;
import static phuhq.it.coffeeshoporder.G_Common.G_Common.STATUS_PENDING;
import static phuhq.it.coffeeshoporder.G_Common.G_Common.tableOrder;

public class A8_Chef_View_Details extends AppCompatActivity {
    private ListView lvDrink;
    private ArrayList<A8_Cls_Chef> drinkListOrder;
    private int position;
    private TextView tvTableName;

    //region FORM EVENT
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a8_chef_view_details);
        mainLoad();
    }
    //endregion

    //region ADD CONTROLS AND LOAD
    public void addControls() {
        try {
            lvDrink = findViewById(R.id.a8_details_listView);
            tvTableName = findViewById(R.id.a8_details_table_name);
            Intent intent = getIntent();
            drinkListOrder = (ArrayList<A8_Cls_Chef>) intent.getSerializableExtra("ORDERS_DETAILS");
            position = intent.getIntExtra("POSITION", 0);
            tvTableName.setText(drinkListOrder.get(position).getTable());
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
            A8_Chef_Details_Adapter detailsAdapter = new A8_Chef_Details_Adapter(this, R.layout.a8_chef_view_details_item, drinkListOrder.get(0).getDrinksList());

            // Gán giá trị adapter cho listview
            this.lvDrink.setAdapter(detailsAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region ON TOUCH
    private void updateStatusTable() {
        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference tableMaster = database.getReference("CSO").child("TBM_Tables");
            tableMaster.child(drinkListOrder.get(position).getTableID()).child("status").setValue(STATUS_ORDER);

            DatabaseReference tableOrder = database.getReference("CSO").child("TBT_Orders");
            tableOrder.child(drinkListOrder.get(position).getTableID()).child("status").setValue(STATUS_ORDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onExit(View view) {
        A8_Chef_View_Details.this.finish();
    }

    public void onFinish(View view) {
        updateStatusTable();
        A8_Chef_View_Details.this.finish();
    }
    //endregion
}
