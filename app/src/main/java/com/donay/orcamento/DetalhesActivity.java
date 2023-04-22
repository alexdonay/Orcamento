package com.donay.orcamento;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
public class DetalhesActivity extends AppCompatActivity {
    private static final String ID = "_id";
    private static final String ORCAMENTO_DESCRICAO = "descricao";
    private static final String ORCAMENTO_VALOR = "valor";
    private static final String ORCAMENTO_LOJA = "loja";
    private static final String ORCAMENTO_FORMA_PAGTO = "formaPgto";
    private static final String ORCAMENTO_FOTO = "foto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);
    }

    public void onStart() {
        super.onStart();
        DBAdapter db = new DBAdapter(this);
        db.open();
        int idCategoria2 = getIntent().getIntExtra("item",0);


        Cursor cursor = db.extraiOrcamentoPorCategoria(idCategoria2);
        MatrixCursor cursor2 = new MatrixCursor(new String[] {"_id", "loja", "valor", "formaPagto", "descricao", "foto", "categoria"});

        /*cursor.addRow(new Object[] {1, "loja", 125, "formaPagto", "descricao", "foto", 1});*/

        ArrayList<Orcamento> listaOrcamentos = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                String loja = cursor.getString(cursor.getColumnIndex("loja"));
                double valor = cursor.getDouble(cursor.getColumnIndex("valor"));
                String formaPagto = cursor.getString(cursor.getColumnIndex("formaPgto"));
                String descricao = cursor.getString(cursor.getColumnIndex("descricao"));
                String fotoPath = cursor.getString(cursor.getColumnIndex("foto"));
                int idCategoria = cursor.getInt(cursor.getColumnIndex("IDCategoria"));
                Orcamento orcamento = new Orcamento(id, loja, valor, formaPagto, descricao, fotoPath, idCategoria);
                listaOrcamentos.add(orcamento);
            } while (cursor.moveToNext());

            DetalhesAdapter adapter = new DetalhesAdapter(this, listaOrcamentos);
            ListView listView = findViewById(R.id.listViewOrcamentos);
            listView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Não foi possível obter os dados do banco de dados.", Toast.LENGTH_SHORT).show();
        }


    }

}