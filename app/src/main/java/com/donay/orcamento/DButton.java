package com.donay.orcamento;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class DButton {
    private Button button;
    private OnClickListener listener;

    public DButton(Activity activity,  String buttonName, OnClickListener listener) {
        int id = activity.getResources().getIdentifier(buttonName, "id", activity.getPackageName());

        this.button = activity.findViewById(id);
        this.listener = listener;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick();
            }
        });
    }

    public interface OnClickListener {
        void onClick();
    }
}
