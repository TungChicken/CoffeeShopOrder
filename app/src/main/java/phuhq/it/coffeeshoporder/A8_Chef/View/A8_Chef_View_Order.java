package phuhq.it.coffeeshoporder.A8_Chef.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import phuhq.it.coffeeshoporder.A7_TableList.Model.A7_Cls_Table;
import phuhq.it.coffeeshoporder.A7_TableList.Presenter.A7_Adapter;
import phuhq.it.coffeeshoporder.A8_Chef.Model.A8_Cls_Chef;
import phuhq.it.coffeeshoporder.A8_Chef.Model.A8_Cls_Chef_Details;
import phuhq.it.coffeeshoporder.A8_Chef.Presenter.A8_Chef_Adapter;
import phuhq.it.coffeeshoporder.R;

import static phuhq.it.coffeeshoporder.G_Common.G_Common.STATUS_FREE;
import static phuhq.it.coffeeshoporder.G_Common.G_Common.STATUS_ORDER;
import static phuhq.it.coffeeshoporder.G_Common.G_Common.STATUS_PENDING;
import static phuhq.it.coffeeshoporder.G_Common.G_Common.flagTableList;

public class A8_Chef_View_Order extends AppCompatActivity {
    private GridView lvTable;
    ArrayList<A8_Cls_Chef> tableList = new ArrayList<>();
    ArrayList<A8_Cls_Chef> drinkList = new ArrayList<>();

    //region FORM EVENT
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a8_chef_view_order);
        lvTable = findViewById(R.id.a8_tableList_lv);
        getDataTableList();
        onClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //getDataTableList();
    }

    //endregion

    //region GET DATA TABLE LIST
    public void getDataTableList() {
        try {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference tables = database.getReference("CSO").child("TBT_Orders");

            tables.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        tableList.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            A8_Cls_Chef clsTable = ds.getValue(A8_Cls_Chef.class);
                            tableList.add(clsTable);
                        }
                        LoadDrinkList();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LoadDrinkList() {
        try {
            // Chỉ hiển thị những bàn đang trạng thái pending
            drinkList.clear();
            assert tableList != null;
            for (int i = 0; i < tableList.size(); i++) {
                if (tableList.get(i).getStatus().equals(STATUS_PENDING)) {
                    drinkList.add(tableList.get(i));
                }
            }

            // Xóa dữ liệu cũ trên lưới
            lvTable.setAdapter(null);

            // Khởi tạo apdater
            A8_Chef_Adapter detailsAdapter = new A8_Chef_Adapter(this, R.layout.a8_chef_view_order_item, drinkList);

            // Gán giá trị adapter cho listview
            this.lvTable.setAdapter(detailsAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region ON TOUCH
    public void onClick() {
        try {
            lvTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        Intent intent = new Intent(A8_Chef_View_Order.this, A8_Chef_View_Details.class);
                        intent.putExtra("ORDERS_DETAILS", drinkList);
                        intent.putExtra("POSITION", position);
                        startActivity(intent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion
}
