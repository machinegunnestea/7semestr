package com.uni.lab3.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.uni.lab3.activities.MainActivity;
import com.uni.lab3.R;
import com.uni.lab3.model.Product;

public class FullProductInfoFragment extends Fragment {
    public static String FRAGMENT_TAG = "fullProductInfoFragment";

    public FullProductInfoFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity mainActivity = (MainActivity) getActivity();
        Bundle arguments = getArguments();
        if (mainActivity != null && arguments != null) {
            Product product = (Product) getArguments().getSerializable("product");
            TextView fullProductInfoTextView = view.findViewById(R.id.fullProductInfoTextView);
            fullProductInfoTextView.setText(product.toString());
            Button clearButton = view.findViewById(R.id.clearButton);
            clearButton.setOnClickListener((v) -> mainActivity.removeFullProductInfoFragment(this));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_full_product_info, container, false);
    }
}