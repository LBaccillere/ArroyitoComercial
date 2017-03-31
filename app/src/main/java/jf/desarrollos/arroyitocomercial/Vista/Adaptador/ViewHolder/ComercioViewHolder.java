package jf.desarrollos.arroyitocomercial.Vista.Adaptador.ViewHolder;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import jf.desarrollos.arroyitocomercial.Controlador.Busqueda;
import jf.desarrollos.arroyitocomercial.Modelo.Comercio;
import jf.desarrollos.arroyitocomercial.Modelo.Telefono;
import jf.desarrollos.arroyitocomercial.R;
import jf.desarrollos.arroyitocomercial.Utilidades.RoundedLetterView;

public class ComercioViewHolder extends RecyclerView.ViewHolder {

    private TextView mFileNameTextView;
    public RoundedLetterView mRoundedLetterView;
    public ImageView ivCall;

    public ComercioViewHolder(final View itemView) {
        super(itemView);
        mRoundedLetterView = (RoundedLetterView) itemView.findViewById(R.id.rlv_name_view);
        mFileNameTextView = (TextView) itemView.findViewById(R.id.tv_name_holder);
        ivCall = (ImageView) itemView.findViewById(R.id.ivCall);
    }

    public void bind(final Context contexto, final Comercio model, int position) {
        if (model.getNombreFantasia().length() == 0) {
            mRoundedLetterView.setTitleText("A");
        } else {
            mRoundedLetterView.setTitleText(model.getNombreFantasia().substring(0, 1).toUpperCase());
        }
        if (position % 2 == 0) {
            ivCall.setImageResource(R.mipmap.ic_call_light);
            mRoundedLetterView.setBackgroundColor(contexto.getResources().getColor(R.color.recycler_view_selected_1));
        } else {
            ivCall.setImageResource(R.mipmap.ic_call_dark);
            mRoundedLetterView.setBackgroundColor(contexto.getResources().getColor(R.color.recycler_view_selected_2));
        }
        mFileNameTextView.setText(model.getNombreFantasia());

        ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getTelefonos().size() == 1) {
                    ArrayList<Telefono> telefono = model.getTelefonos();
                    llamarComercio(contexto, telefono.get(0).getNumero());
                } else {
                    mostrarOpcionesLlamada(contexto, model);
                }
            }
        });
    }

    public void mostrarOpcionesLlamada(final Context contexto, final Comercio comercio){
        final String MENSAJE_OPCION_FOTO = "Selecciona una teléfono";

        Activity activity = (Activity) contexto;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(contexto);
        final AlertDialog alerta = alertDialog.create();
        LayoutInflater inflater = activity.getLayoutInflater();
        View convertView = inflater.inflate(R.layout.opciones, null);
        alerta.setView(convertView);
        alerta.setTitle(MENSAJE_OPCION_FOTO);
        ListView lv = (ListView) convertView.findViewById(R.id.lvOpciones);
        String[] telefonos = new String[comercio.getTelefonos().size()];
        int i = 0;
        if (!comercio.getTelefonos().isEmpty()){
            for (Telefono telefono : comercio.getTelefonos()) {
                telefonos[i] = telefono.getNumero();
                i++;
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(contexto,android.R.layout.simple_list_item_1,telefonos);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                alerta.dismiss();
                llamarComercio(contexto, comercio.getTelefonos().get(position).getNumero());
            }
        });
        alerta.show();
    }

    public void llamarComercio(Context contexto, String telefono){
        final String MENSAJE_FALLO_LLAMADA = "Fallo la llamada.";
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + telefono));
            contexto.startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {
            Crouton.makeText((Activity) contexto, MENSAJE_FALLO_LLAMADA, Style.ALERT).show();
        }
    }
}
