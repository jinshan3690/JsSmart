package com.js.smart.ui.dialog;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.js.smart.common.ui.dialog.DialogBuilder;
import com.js.smart.ui.R;


/**
 * Created by Js on 2016/6/22.
 */
public class GenderDialog extends DialogBuilder<GenderDialog> {

    public GenderDialog(Context context) {
        super(context);
    }

    public Dialog showGenderDialog(String text, OnGenderListener listener) {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_gender, null);
        RadioGroup radioGroup = view.findViewById(R.id.radioGroup1);
        RadioButton radioButton1 = view.findViewById(R.id.radioButton1);
        RadioButton radioButton2 = view.findViewById(R.id.radioButton2);
        if(radioButton2.getText().toString().equals(text))
            radioGroup.check(R.id.radioButton2);
        else
            radioGroup.check(R.id.radioButton1);

        view.findViewById(R.id.btn1).setOnClickListener(v -> {
            String content = radioButton1.getId() == radioGroup.getCheckedRadioButtonId() ? radioButton1.getText().toString() : radioButton2.getText().toString();
            listener.change(content);
        });

        return show();
    }

    public interface OnGenderListener {
        void change(String text);
    }

}
