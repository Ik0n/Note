package com.example.note;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentResultOwner;
import androidx.lifecycle.LifecycleOwner;

import com.example.note.data.InMemoryRepoImp;
import com.example.note.ui.EditNoteFragment;
import com.example.note.ui.NotesActivity;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePickerDialog extends DialogFragment {

    private Date formatDate;
    private DatePicker datePicker;
    private Bundle result;


    public static final String DATE_PICKER_DIALOG = "DatePickerDialog";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_datepicker, null);
        datePicker = view.findViewById(R.id.date_picker);
        view.findViewById(R.id.date_picker_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formatDate = new Date(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                Bundle result = new Bundle();
                result.putSerializable(DATE_PICKER_DIALOG, formatDate);
                getParentFragmentManager().setFragmentResult(DATE_PICKER_DIALOG, result);
                dismiss();
            }
        });


        return view;
    }

}
