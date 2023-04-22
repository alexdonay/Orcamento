package com.donay.orcamento;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.donay.orcamento.databinding.ActivityMainBinding;

import java.util.List;


public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    public void mostraCategorias(Cursor cursor){
        String livro = "Id: " + cursor.getString(0) + "\n";

        Toast.makeText(this, livro, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
    }

    @Override
    public void onStart(){
        super.onStart();
        DBAdapter db = new DBAdapter(this);
        db.open();
        Cursor cursor = db.extraiTodasCategorias();
        String[] from = new String[]{"descricao"};
        int[] to = new int[]{android.R.id.text1};
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, from, to, 0);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(cursorAdapter);
        db.close();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetalhesActivity.class);
                intent.putExtra("item", position+1);
                startActivity(intent);
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                db.open();
                Cursor cursor = db.extraiCategoriaID(position+1);
                cursor.moveToLast();
                @SuppressLint("Range") String descricao = cursor.getString(cursor.getColumnIndex("descricao"));
                Item item = new Item(position+1,descricao);
                db.close();

                Intent intent = new Intent(MainActivity.this, OrcamentoActivity.class);
                intent.putExtra("item", item);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent intent = new Intent(this, CadastroActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}