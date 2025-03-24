package com.christianserwedevs.comprevo.Utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.christianserwedevs.comprevo.R;

public class ConfirmationDialog {

    public interface ConfirmationListener {
        void onConfirm();
        void onCancel();
    }

    public static void show(Context context, ConfirmationListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_confirmation, null);

        TextView confirmationText = view.findViewById(R.id.confirmationText);
        Button confirmButton = view.findViewById(R.id.confirmButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);

        confirmationText.setText("Are you sure you want to go back to the main menu?\n\nYour progress will not be saved.");

        confirmButton.setOnClickListener(v -> {
            listener.onConfirm();
        });

        cancelButton.setOnClickListener(v -> {
            listener.onCancel();
        });

        builder.setView(view);
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
