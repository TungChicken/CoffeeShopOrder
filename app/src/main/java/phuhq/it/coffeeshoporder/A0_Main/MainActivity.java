package phuhq.it.coffeeshoporder.A0_Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import phuhq.it.coffeeshoporder.R;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
    //region AVAILABLE
    private RecyclerView newProductRecyclerView;

    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLoad();
    }

    //region ADD CONTROLS AND LOAD
    public void addControls() {
        newProductRecyclerView = findViewById(R.id.a0_listType);
    }

    public void mainLoad() {
        addControls();
    }
    //endregion

    //region PRODUCT RECYCLER VIEW
    //endregion
}
