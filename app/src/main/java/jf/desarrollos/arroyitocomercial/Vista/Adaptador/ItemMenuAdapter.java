package jf.desarrollos.arroyitocomercial.Vista.Adaptador;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import jf.desarrollos.arroyitocomercial.Modelo.ItemMenu;
import jf.desarrollos.arroyitocomercial.R;

public class ItemMenuAdapter extends ArrayAdapter<ItemMenu> {

    Context contexto;
    int resLayout;
    List<ItemMenu> listaItemMenu;

    public ItemMenuAdapter(Context context, int resLayout, List<ItemMenu> listNavItems) {
        super(context, resLayout, listNavItems);

        this.contexto = context;
        this.resLayout = resLayout;
        this.listaItemMenu = listNavItems;
    }

    @SuppressLint("ViewHolder") @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(contexto, resLayout, null);

        TextView tvTitle  = (TextView) v.findViewById(R.id.titulo);
        ImageView navIcon =  (ImageView) v.findViewById(R.id.icon_menu);

        ItemMenu navItem = listaItemMenu.get(position);

        tvTitle.setText(navItem.getTitulo());
        navIcon.setImageResource(navItem.getIcono());

        return v;
    }
}