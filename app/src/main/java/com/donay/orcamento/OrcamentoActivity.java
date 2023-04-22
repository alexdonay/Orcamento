package com.donay.orcamento;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class OrcamentoActivity extends AppCompatActivity {
    static final int CAMERA = 1;
    private String currentPhotoPath;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orcamento);
        Item itemDeserializado = (Item) getIntent().getSerializableExtra("item");
        String itemDescricao = itemDeserializado.getDescricao();
        int id = itemDeserializado.getId();
        TextView textViewCategoria = findViewById(R.id.txCategoria);
        textViewCategoria.setText(itemDescricao);
        Button btn_tira_foto = findViewById(R.id.btn_Tira_Foto);
        Button btn_Salvar = findViewById(R.id.btn_Salvar);

        btn_tira_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        btn_Salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBAdapter db = new DBAdapter(OrcamentoActivity.this);
                db.open();
                EditText TxtLoja = findViewById(R.id.edit_text_loja);
                EditText TxtValor = findViewById(R.id.edit_text_valor);
                EditText TxtPagamento = findViewById(R.id.edit_text_pagamento);
                EditText TxtObservacao = findViewById(R.id.edit_text_observacao);
                String loja = TxtLoja.getText().toString();
                Double valor = Double.parseDouble(TxtValor.getText().toString());
                String pagamento = TxtPagamento.getText().toString();
                String observacao = TxtObservacao.getText().toString();

                db.salvarOrcamento(loja, valor, pagamento, observacao, currentPhotoPath,id);
                finish();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(OrcamentoActivity.this,
                        "com.donay.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            ImageView imageView = findViewById(R.id.fotoTirada);
            setPic(imageView);
        }
    }

    private void setPic(ImageView imageView) {
        int targetW = 120;
        int targetH = 360;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }
}