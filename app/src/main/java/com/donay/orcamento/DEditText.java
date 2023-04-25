package com.donay.orcamento;

import android.app.Activity;
import android.widget.EditText;

public class DEditText {
    EditText editText;
    public DEditText(Activity activity, String editName){
        int id = activity.getResources().getIdentifier(editName, "id", activity.getPackageName());
        editText = activity.findViewById(id);
    }
    @Override
    public String toString(){
        return this.editText.getText().toString();
    }
    public double toDouble(){
        return Double.parseDouble(this.editText.getText().toString());
    }
}
