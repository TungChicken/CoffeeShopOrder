package phuhq.it.coffeeshoporder.A7_TableList.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import phuhq.it.coffeeshoporder.A3_OrderDetails.View.A3_OrderDetails;
import phuhq.it.coffeeshoporder.A7_TableList.Model.A7_Cls_Table;
import phuhq.it.coffeeshoporder.A7_TableList.Presenter.A7_Adapter;
import phuhq.it.coffeeshoporder.R;

import static phuhq.it.coffeeshoporder.G_Common.G_Common.STATUS_FREE;
import static phuhq.it.coffeeshoporder.G_Common.G_Common.STATUS_ORDER;
import static phuhq.it.coffeeshoporder.G_Common.G_Common.STATUS_PENDING;
import static phuhq.it.coffeeshoporder.G_Common.G_Common.flagTableList;

public class A7_TableList extends AppCompatActivity {
    //region  AVAILABLE
    private GridView lvTable;
    List<A7_Cls_Table> tableList = new ArrayList<>();
    private TextView tvDes, tvTitle;
    //endregion

    //region FORM EVENTS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a7_table_list);
        lvTable = findViewById(R.id.a7_tablelist_lv);
        tvDes = findViewById(R.id.a7_tablelist_description);
        tvTitle = findViewById(R.id.a7_tablelist_title);
        mainLoad();
    }
    //endregion

    //region MAIN LOAD
    public void mainLoad() {
        getDataTableList();
        onTouch();
    }
    //endregion

    //region GET DATA TABLE LIST
    public void getDataTableList() {
        try {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference tables = database.getReference("CSO").child("TBM_Tables");
            tables.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        tableList.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            A7_Cls_Table clsTable = ds.getValue(A7_Cls_Table.class);
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
            // Xóa dữ liệu cũ trên lưới
            lvTable.setAdapter(null);

            // Kiểm tra trạng thái
            if (flagTableList) {
                tvDes.setText("All table order in shop");
                tvTitle.setText("Select a table for get invoice");
                for (Iterator<A7_Cls_Table> iterator = tableList.iterator(); iterator.hasNext(); ) {
                    A7_Cls_Table clsTable = iterator.next();
                    if (clsTable.getStatus().equals(STATUS_FREE) || clsTable.getStatus().equals(STATUS_PENDING)) {
                        iterator.remove();
                    }
                }
            } else {
                tvDes.setText("All table free and pending in shop");
                tvTitle.setText("Select a table for order drink");
                for (Iterator<A7_Cls_Table> iterator = tableList.iterator(); iterator.hasNext(); ) {
                    A7_Cls_Table clsTable = iterator.next();
                    if (clsTable.getStatus().equals(STATUS_ORDER)) {
                        iterator.remove();
                    }
                }
            }
            // Khởi tạo apdater
            A7_Adapter detailsAdapter = new A7_Adapter(this, R.layout.a7_table_list_item, tableList);

            // Gán giá trị adapter cho listview
            this.lvTable.setAdapter(detailsAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region ON TOUCH
    private void onTouch() {
        try {
            lvTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(A7_TableList.this, A3_OrderDetails.class);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion
}
