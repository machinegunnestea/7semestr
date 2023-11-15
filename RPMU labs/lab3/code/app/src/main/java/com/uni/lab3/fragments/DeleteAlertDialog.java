package com.uni.lab3.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uni.lab3.R;

public class DeleteAlertDialog extends DialogFragment {
    public static String NOT_ALERT_DIALOG = "notAlertDialog";
    public static String FULLSCREEN = "fullScreen";
    public static String ALERT_TITLE = "alertTitle";
    public static String ALERT_MESSAGE = "alertMessage";
    public static String PRODUCT_ID = "productId";
    public static String PRODUCT_IDS = "productIds";
    public static String OK = "Ok";
    public static String CANCEL = "Cancel";

    public static String SELECT_DIALOG = "selectDialog";
    public static String DELETE_CONFIRMATION_DIALOG = "deleteConfirmationDialog";

    DialogListener dialogListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialogListener = (DialogListener) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (getArguments() != null) {
            builder.setTitle(getArguments().getString(ALERT_TITLE));
            builder.setMessage(getArguments().getString(ALERT_MESSAGE) + " " + getArguments().getInt(PRODUCT_ID));
            builder.setPositiveButton(OK, (dialog, which) -> {
                dialogListener.onConfirmDeleteProductId(getArguments().getInt(PRODUCT_ID));
                dialogListener.removeDeleteDialogFragments();
            });
            builder.setNegativeButton(CANCEL, (dialog, which) -> dismiss());
        }
        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_delete_dialog, container, false);
    }

    public interface DialogListener {
        void onConfirmDeleteProductId(int productId);
        void removeDeleteDialogFragments();
    }
}