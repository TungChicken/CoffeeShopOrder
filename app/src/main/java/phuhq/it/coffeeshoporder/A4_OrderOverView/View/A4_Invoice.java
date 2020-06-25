package phuhq.it.coffeeshoporder.A4_OrderOverView.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import phuhq.it.coffeeshoporder.A4_OrderOverView.Model.A4_Cls_Invoice;
import phuhq.it.coffeeshoporder.A4_OrderOverView.Presenter.A4_Invoice_Adapter;
import phuhq.it.coffeeshoporder.A8_Chef.Model.A8_Cls_Chef;
import phuhq.it.coffeeshoporder.R;

import static phuhq.it.coffeeshoporder.G_Common.G_Common.STATUS_FREE;
import static phuhq.it.coffeeshoporder.G_Common.G_Common.STATUS_ORDER;
import static phuhq.it.coffeeshoporder.G_Common.G_Common.getDecimalFormattedString;
import static phuhq.it.coffeeshoporder.G_Common.G_Common.tableNameInvoice;

public class A4_Invoice extends AppCompatActivity {
    ArrayList<A8_Cls_Chef> tableList = new ArrayList<>();
    ArrayList<A8_Cls_Chef> drinkList = new ArrayList<>();
    private ListView lvDrink;
    private TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a4_invoice);
        lvDrink = findViewById(R.id.a4_listView);
        total = findViewById(R.id.a4_tv_OrderTotal);
        getDataTableList();
    }

    public void getDataTableList() {
        try {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference tables = database.getReference("CSO").child("TBT_Orders");

            tables.addListenerForSingleValueEvent(new ValueEventListener() {
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
            // Chỉ hiển thị những bàn đang trạng thái order
            drinkList.clear();
            assert tableList != null;
            for (int i = 0; i < tableList.size(); i++) {
                if (tableList.get(i).getStatus().equals(STATUS_ORDER) && tableList.get(i).getTableID().equals(tableNameInvoice)) {
                    drinkList.add(tableList.get(i));
                }
            }

            // Xóa dữ liệu cũ trên lưới
            lvDrink.setAdapter(null);
            // Khởi tạo apdater
            A4_Invoice_Adapter detailsAdapter = new A4_Invoice_Adapter(this, R.layout.a4_invoice_item, drinkList.get(0).getDrinksList());

            // Gán giá trị adapter cho listview
            this.lvDrink.setAdapter(detailsAdapter);

            // Tổng tiền
            this.total.setText(getDecimalFormattedString(String.valueOf(drinkList.get(0).getTotal())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onPrintBill(View view) {
        try {
            onMessageAlertInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateStatusTable() {
        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("CSO").child("TBM_Tables");
            myRef.child(tableNameInvoice).child("status").setValue(STATUS_FREE);

            DatabaseReference myOrders = database.getReference("CSO").child("TBT_Orders");
            myOrders.child(tableNameInvoice).removeValue();

            setDataInvoice();
            Toast.makeText(this, "Print Bill successful", Toast.LENGTH_SHORT).show();
            A4_Invoice.this.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDataInvoice() {
        try {
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            A4_Cls_Invoice clsInvoice = new A4_Cls_Invoice(tableNameInvoice, drinkList.get(0).getTable(), "invoice", "25062020", drinkList,drinkList.get(0).getTotal());
            database.child("CSO").child("TBT_Invoices").child(tableNameInvoice).setValue(clsInvoice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //region PRINT INVOICE
    public void onMessageAlertInfo() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirm print bill");
            builder.setMessage("Are you sure print this bill ?");

            DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i) {
                        case DialogInterface.BUTTON_POSITIVE:
                            updateStatusTable();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            builder.setPositiveButton("OK", clickListener);
            builder.setNegativeButton("Cancel", clickListener);
            builder.setIcon(R.drawable.a10_admin_ic_delete_user);

            Dialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion
}