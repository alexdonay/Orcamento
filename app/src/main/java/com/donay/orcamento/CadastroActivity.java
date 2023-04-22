package com.donay.orcamento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBAdapter db = new DBAdapter(this);
        setContentView(R.layout.activity_cadastro);
        Button btn_gravar = findViewById(R.id.btn_gravar);
        EditText txt_descricao = findViewById(R.id.txt_descricao);
        btn_gravar.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View v) {
                if(txt_descricao.getText().toString()!=""){
                    db.salvaCategoria(txt_descricao.getText().toString());
                    finish();
                }

            }
        });
    }
}