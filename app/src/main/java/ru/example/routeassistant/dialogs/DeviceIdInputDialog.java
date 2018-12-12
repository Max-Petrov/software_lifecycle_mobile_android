package ru.kenguru.driverassistant.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import ru.kenguru.driverassistant.R;
import ru.kenguru.driverassistant.others.PreferencesEditor;

public class DeviceIdInputDialog extends DialogFragment {

    private EditText idEdit;

    private DeviceIdInputDialogListener mListener;

    public interface DeviceIdInputDialogListener {
        void onDialogPositiveClick(boolean result);
    }

    public void setDeviceIdInputDialogListener(DeviceIdInputDialogListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.device_id_input_dialog, null);
        idEdit = view.findViewById(R.id.device_id_input);
        builder.setView(view)
                .setTitle(R.string.device_id_dialog_title)
                .setCancelable(false)
                .setPositiveButton(R.string.device_id_dialog_btn_ok_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String idText = idEdit.getText().toString();
                        if (idText.equals("")) {
                            mListener.onDialogPositiveClick(false);
                        }
                        else {
                            long deviceId = Long.valueOf(idText);
                            PreferencesEditor.getInstance().setDeviceId(deviceId);
                            mListener.onDialogPositiveClick(true);
                        }
                    }
                });
        return builder.create();
    }
}
