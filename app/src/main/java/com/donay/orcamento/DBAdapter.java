package com.donay.orcamento;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DBAdapter {
    public static final String CATEGORIA_TABELA ="categoria";
    public static final String ID = "_id";
    public static final String CATEGORIA_DESCRICAO = "descricao";

    public static final String ORCAMENTO_TABELA = "orcamento";
    public static final String ORCAMENTO_LOJA = "loja";
    public static final String ORCAMENTO_VALOR = "valor";
    public static final String ORCAMENTO_FORMA_PAGTO = "formaPgto";
    public static final String ORCAMENTO_DESCRICAO = "descricao";
    public static final String ORCAMENTO_FOTO = "foto";
    public static final String ID_CATEGORIA = "IDCategoria";

    private static final String TAG = "DBAdapter";

    private static final String DATABASE_NAME = "Exemplo01BD";
    private static final int DATABASE_VERSION =1;
    private static final String CRIA_TABELA_CATEGORIA = "CREATE TABLE " + CATEGORIA_TABELA +
            "("+ ID + " integer primary key autoincrement, " +
            CATEGORIA_DESCRICAO +" text not null);" ;

    private static final String CRIA_TABELA_ORCAMENTO = "CREATE TABLE " + ORCAMENTO_TABELA +
            "("+ ID + " integer primary key autoincrement, " +
            ORCAMENTO_LOJA +" text not null, " +
            ORCAMENTO_VALOR + " real not null, "+
            ORCAMENTO_FORMA_PAGTO + " text not null, "+
            ORCAMENTO_DESCRICAO + " text not null, "+
            ORCAMENTO_FOTO + " text not null, "+
            ID_CATEGORIA + " integer not null, "+
            "FOREIGN KEY (" + ID_CATEGORIA + ") REFERENCES " + CATEGORIA_TABELA + "(" + ID + "));";

    private final Context contexto;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx){
        this.contexto = ctx;
        DBHelper = new DatabaseHelper(contexto);
    }
    private static class DatabaseHelper extends SQLiteOpenHelper{
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){

            try{
                db.execSQL(CRIA_TABELA_CATEGORIA );
                db.execSQL(CRIA_TABELA_ORCAMENTO );
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){
            Log.w(TAG, "Atualizando a base de dados a partir da versao " + oldVersion
                    + " para " + newVersion + ",isso irá destruir todos os dados antigos");

            db.execSQL("DROP TABLE IF EXISTS " + CRIA_TABELA_ORCAMENTO);
            db.execSQL("DROP TABLE IF EXISTS " + CATEGORIA_TABELA);
            onCreate(db);
        }

    }
    public DBAdapter open() throws SQLException{
        db = DBHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        DBHelper.close();
    }


    public long salvaCategoria(String descricao) {
        ContentValues valores = new ContentValues();
        valores.put(CATEGORIA_DESCRICAO, descricao);

        open();
        long resultado = db.insert(CATEGORIA_TABELA, null, valores);
        db.close();

        return resultado;
    }
    public long salvarOrcamento(String loja, double valor, String formaPgto, String descricao, String foto, long idCategoria) {
        ContentValues valores = new ContentValues();
        valores.put(ORCAMENTO_LOJA, loja);
        valores.put(ORCAMENTO_VALOR, valor);
        valores.put(ORCAMENTO_FORMA_PAGTO, formaPgto);
        valores.put(ORCAMENTO_DESCRICAO, descricao);
        valores.put(ORCAMENTO_FOTO, foto);
        valores.put(ID_CATEGORIA, idCategoria);

        open();
        long resultado = db.insert(ORCAMENTO_TABELA, null, valores);
        close();

        return resultado;
    }
    public Cursor extraiOrcamentoPorCategoria(long ID_categoria) {
        open();
        Cursor cursor = db.query(ORCAMENTO_TABELA, null, ID_CATEGORIA + "=?",
                new String[] { String.valueOf(ID_categoria) }, null, null, null);

        return cursor;
    }
    public Cursor extraiTodasCategorias() {
        String colunas[] ={ID,CATEGORIA_DESCRICAO};
        return db.query(CATEGORIA_TABELA,colunas, null, null, null, null, null);

    }
    public Cursor extraiCategoriaID(int id){
        String colunas[] ={ID,CATEGORIA_DESCRICAO};


        return db.query(CATEGORIA_TABELA,colunas, ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
    }
    public Cursor extraiOrcamentoTodos() {
        open();
        Cursor cursor = db.query(ORCAMENTO_TABELA, null,null,
                null, null, null, null);

        return cursor;
    }

}

