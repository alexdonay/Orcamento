package com.donay.orcamento;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DetalhesAdapter extends ArrayAdapter<Orcamento> {

    private Context context;
    private ArrayList<Orcamento> orcamentos;

    public DetalhesAdapter(Context context, ArrayList<Orcamento> orcamentos) {
        super(context, R.layout.detalhesadapter, orcamentos);
        this.context = context;
        this.orcamentos = orcamentos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.detalhesadapter, parent, false);

        TextView txtLoja = (TextView) rowView.findViewById(R.id.txtLoja);
        TextView txtValor = (TextView) rowView.findViewById(R.id.txtValor);
        TextView txtDescricao = (TextView) rowView.findViewById(R.id.txtDescricao);
        ImageView imgFoto = (ImageView) rowView.findViewById(R.id.imgFoto);

        Orcamento orcamento = orcamentos.get(position);
        txtLoja.setText(orcamento.getLoja());
        txtValor.setText(String.format("R$ %.2f", orcamento.getValor()));
        txtDescricao.setText(orcamento.getDescricao());

        // seta a imagem do orcamento, caso exista
        if (!orcamento.getFoto().equals("")) {
            Bitmap bitmap = BitmapFactory.decodeFile(orcamento.getFoto());
            imgFoto.setImageBitmap(bitmap);
        }

        return rowView;
    }
}
