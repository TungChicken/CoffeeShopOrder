package phuhq.it.coffeeshoporder.A4_OrderOverView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import phuhq.it.coffeeshoporder.A0_Main.View.MainActivity;
import phuhq.it.coffeeshoporder.R;

public class A4_OrderComplete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a4_order_complete);
    }

    public void backHome(View view){
        Intent intent = new Intent(A4_OrderComplete.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
