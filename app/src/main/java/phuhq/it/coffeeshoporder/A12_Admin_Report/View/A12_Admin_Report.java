package phuhq.it.coffeeshoporder.A12_Admin_Report.View;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import phuhq.it.coffeeshoporder.A4_OrderOverView.Model.A4_Cls_Invoice;
import phuhq.it.coffeeshoporder.G_Common.G_Common;
import phuhq.it.coffeeshoporder.R;

public class A12_Admin_Report extends AppCompatActivity {
    private Spinner spinner;
    ArrayList<A4_Cls_Invoice> tableList = new ArrayList<>();
    private TextView tvTotalInvoice, tvAmount, tvCost, tvNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a12_admin_report);
        spinner = findViewById(R.id.spinner);

        tvTotalInvoice = findViewById(R.id.textView33);
        tvAmount = findViewById(R.id.textView35);
        tvCost = findViewById(R.id.textView38);
        tvNet = findViewById(R.id.textView39);
        addTypeReport();
        getDataTableList();
    }

    public void addTypeReport() {
        try {
            List<String> per = new ArrayList<>();
            per.add("Daily");
            per.add("Weekly");
            per.add("Monthly");
            per.add("Yearly");

            ArrayAdapter arrayAdapter = new ArrayAdapter<String>(A12_Admin_Report.this, android.R.layout.simple_list_item_1, per);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
            spinner.setSelection(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDataTableList() {
        try {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference tables = database.getReference("CSO").child("TBT_Invoices");

            tables.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        tableList.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            A4_Cls_Invoice clsTable = ds.getValue(A4_Cls_Invoice.class);
                            tableList.add(clsTable);
                        }
                        showData();
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

    public void showData() {
        try {
            int amount = 0;
            tvTotalInvoice.setText(String.valueOf(tableList.size()));
            for (int i=0; i <tableList.size(); i++){
                amount += tableList.get(i).getTotal();
            }
            tvAmount.setText(G_Common.getDecimalFormattedString(String.valueOf(amount)));
            tvNet.setText(G_Common.getDecimalFormattedString(String.valueOf(amount)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}