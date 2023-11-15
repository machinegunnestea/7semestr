package com.uni.lab3.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.uni.lab3.R;
import com.uni.lab3.activities.MainActivity;

import java.util.Arrays;

public class ProductSelectorDialog extends DialogFragment {
    public static String PRODUCT_IDS = "productIds";
    public static String SELECT_DIALOG = "selectDialog";

    ProductSelectorDialog.DialogListener dialogListener;
    MainActivity.SelectProductIdReasons reason;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialogListener = (ProductSelectorDialog.DialogListener) getActivity();
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Spinner productIdSpinner = view.findViewById(R.id.productIdSpinner);
        final Button selectProductButton = view.findViewById(R.id.selectProductButton);
        final Button cancelSelectButton = view.findViewById(R.id.cancelSelectButton);

        if (getArguments() != null
                && getArguments().getSerializable(PRODUCT_IDS) != null
                && getArguments().getSerializable(MainActivity.REASON) != null) {
            reason = (MainActivity.SelectProductIdReasons) getArguments().getSerializable(MainActivity.REASON);

            String[] productIds = Arrays.stream(((int[]) getArguments().getSerializable(PRODUCT_IDS))).mapToObj(Integer::toString).toArray(String[]::new);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item, productIds);
            productIdSpinner.setAdapter(arrayAdapter);

            // select dialog
            selectProductButton.setOnClickListener(view1 -> dialogListener.onSelectProductId(Integer.parseInt(productIdSpinner.getSelectedItem().toString()), reason));
            cancelSelectButton.setOnClickListener(view12 -> dismiss());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_dialog, container, false);
    }

    public interface DialogListener {
        void onSelectProductId(int selectedProductId, MainActivity.SelectProductIdReasons reason);
    }
}
