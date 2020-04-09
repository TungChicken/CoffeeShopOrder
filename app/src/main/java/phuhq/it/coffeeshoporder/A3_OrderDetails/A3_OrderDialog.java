package phuhq.it.coffeeshoporder.A3_OrderDetails;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import phuhq.it.coffeeshoporder.R;

/**
 * Dialog Order
 * 08/04/2020
 */
public class A3_OrderDialog extends BottomSheetDialogFragment {

    public A3_OrderDialog() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.a3_order_dialog, container, false);
    }
}
