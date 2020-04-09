package phuhq.it.coffeeshoporder.A3_OrderDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import phuhq.it.coffeeshoporder.R;

public class A3_OrderDialogMore extends BottomSheetDialogFragment {
    public A3_OrderDialogMore() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.a3_order_dialog_more, container, false);
    }
}
