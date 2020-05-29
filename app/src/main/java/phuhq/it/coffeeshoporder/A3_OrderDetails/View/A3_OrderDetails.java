package phuhq.it.coffeeshoporder.A3_OrderDetails.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import phuhq.it.coffeeshoporder.A3_OrderDetails.Model.A3_Cls_Order;
import phuhq.it.coffeeshoporder.A3_OrderDetails.Presenter.A3_OrderDetailsAdapter;
import phuhq.it.coffeeshoporder.A3_OrderDetails.Model.A3_Cls_Drinks;
import phuhq.it.coffeeshoporder.A4_OrderOverView.View.A4_OverView;
import phuhq.it.coffeeshoporder.G_Common.G_Common;
import phuhq.it.coffeeshoporder.R;

import static phuhq.it.coffeeshoporder.G_Common.G_Common.tableOrder;

public class A3_OrderDetails extends AppCompatActivity {
    //region  AVAILABLE
    private ListView lvDrink;
    ArrayList<A3_Cls_Drinks> drinkList = new ArrayList<>();
    private ImageView imgBanner;
    //endregion

    //region FORM EVENTS
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a3_order_details);
        lvDrink = findViewById(R.id.a3_listView);
        imgBanner = findViewById(R.id.imageView3);
        mainLoad();
    }

    //endregion

    //region MAIN LOAD
    public void mainLoad() {
        setImageBanner();
        getDataFireBase();
    }
    //endregion

    //region GET LIST DATA DRINKS
    public void getDataFireBase() {
        try {
            // Get list from Firebase
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference drinks = database.getReference("CSO").child("TBM_Drink");
            drinks.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        drinkList.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            A3_Cls_Drinks drink = ds.getValue(A3_Cls_Drinks.class);
                            drinkList.add(drink);
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
            lvDrink.setAdapter(null);

            // Khởi tạo apdater
            A3_OrderDetailsAdapter detailsAdapter = new A3_OrderDetailsAdapter(this, R.layout.a3_order_details_item, drinkList);

            // Gán giá trị adapter cho listview
            this.lvDrink.setAdapter(detailsAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setImageBanner() {
        try {
            // Get list from Firebase
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference drinks = database.getReference("CSO").child("TBM_Banner").child("OrderDetails");
            drinks.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        Picasso.with(A3_OrderDetails.this).load(dataSnapshot.getValue().toString())
                                .placeholder(R.drawable.banner1)
                                .error(R.drawable.banner1).into(imgBanner);
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

    //endregion

    //region ON TOUCH
    public void AddOrder(View view) {
        try {
            int orderTotal = 0;
            // Tính tổng order
            for (int i = 0; i < drinkList.size(); i++) {
                orderTotal += drinkList.get(i).getNowQty() * drinkList.get(i).getPrice();
            }
            if (orderTotal > 0) {
                Intent intent = new Intent(A3_OrderDetails.this, A4_OverView.class);
                intent.putExtra("ORDERS", drinkList);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please select drinks", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion
}
