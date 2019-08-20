package com.js.smart.ui.dialog;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.js.smart.common.app.AntiShakeOnClickListener;
import com.js.smart.common.ui.dialog.DialogBuilder;
import com.js.smart.ui.R;


/**
 * Created by Js on 2016/6/22.
 */
public class GenderDialog extends DialogBuilder<GenderDialog> {

    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;

    public GenderDialog(Context context) {
        super(context);
        view = LayoutInflater.from(context).inflate(R.layout.dialog_gender, null);
        radioGroup = view.findViewById(R.id.radioGroup1);
        radioButton1 = view.findViewById(R.id.radioButton1);
        radioButton2 = view.findViewById(R.id.radioButton2);
    }

    public GenderDialog showGenderDialog(String text, OnGenderListener listener) {

        if (radioButton2.getText().toString().equals(text))
            radioGroup.check(R.id.radioButton2);
        else
            radioGroup.check(R.id.radioButton1);

        view.findViewById(R.id.btn1).setOnClickListener(new AntiShakeOnClickListener() {
            @Override
            protected void antiShakeOnClick(View v) {
                String content = radioButton1.getId() == radioGroup.getCheckedRadioButtonId() ? radioButton1.getText().toString() : radioButton2.getText().toString();
                listener.change(content);
            }
        });

        setDialog(show());
        return this;
    }

    public interface OnGenderListener {
        void change(String text);
    }

}
