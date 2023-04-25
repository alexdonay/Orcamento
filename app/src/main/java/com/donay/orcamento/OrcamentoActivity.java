package com.donay.orcamento;
import android.Manifest;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class OrcamentoActivity extends AppCompatActivity {
    static final int CAMERA = 1;
    private String currentPhotoPath;
    ImageView imageView;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    XCamera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orcamento);
        camera = new XCamera(this);
        imageView = findViewById(R.id.fotoTirada);

        Item itemDeserializado = (Item) getIntent().getSerializableExtra("item");
        String itemDescricao = itemDeserializado.getDescricao();
        int id = itemDeserializado.getId();
        TextView textViewCategoria = findViewById(R.id.txCategoria);
        textViewCategoria.setText(itemDescricao);

        new DButton(this, "btn_Tira_Foto", new DButton.OnClickListener() {
            @Override
            public void onClick() {

                if (ContextCompat.checkSelfPermission(OrcamentoActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(OrcamentoActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                }
                camera.dispatchTakePictureIntent();
                currentPhotoPath = camera.getmCurrentPhotoPath();
            }
        });
        new DButton(this, "btn_Salvar", new DButton.OnClickListener() {
            @Override
            public void onClick() {
                gravar(id);
            }
        });
    }
    public void gravar(int id){
        DBAdapter db = new DBAdapter(OrcamentoActivity.this);
        db.open();
        DEditText TxtLoja = new DEditText(this,"edit_text_loja");
        String loja = TxtLoja.toString();
        DEditText TxtValor = new DEditText(this, "edit_text_valor");
        Double valor = TxtValor.toDouble();
        DEditText TxtPagamento = new DEditText(this, "edit_text_pagamento");
        DEditText TxtObservacao = new DEditText(this, "edit_text_observacao");
        String pagamento = TxtPagamento.toString();
        String observacao = TxtObservacao.toString();
        db.salvarOrcamento(loja, valor, pagamento, observacao, currentPhotoPath,id);
        db.close();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            camera.setPic(imageView);
        }
    }
}
