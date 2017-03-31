package jf.desarrollos.arroyitocomercial.Vista.Adaptador.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jf.desarrollos.arroyitocomercial.Controlador.Operacion;
import jf.desarrollos.arroyitocomercial.Controlador.Volley.AppController;
import jf.desarrollos.arroyitocomercial.Modelo.Notificacion;
import jf.desarrollos.arroyitocomercial.R;

public class NotificacionViewHolder extends RecyclerView.ViewHolder {

    private ImageView ivImagen;
    private ProgressBar pBar;
    private TextView titulo;
    private TextView mensaje;
    private TextView fechaFin;
    private Context contexto;

    public NotificacionViewHolder(View itemView) {
        super(itemView);

        ivImagen = (ImageView) itemView.findViewById(R.id.ivImagen);
        pBar = (ProgressBar) itemView.findViewById(R.id.pBar);
        pBar.setVisibility(View.GONE);
        titulo = (TextView) itemView.findViewById(R.id.tvTitulo);
        mensaje = (TextView) itemView.findViewById(R.id.tvMensaje);
        fechaFin = (TextView) itemView.findViewById(R.id.tvFechaFin);
    }

    public void bind(final Context contexto, final Notificacion model, int posicion) {
        this.contexto = contexto;

        titulo.setText(model.getTitulo());
        mensaje.setText(model.getMensaje());

        if (!model.getFechaFin().equals("0000-00-00")) {
            fechaFin.setText("Válido hasta el " + Operacion.formatearFecha(model.getFechaFin()));
        } else {
            fechaFin.setVisibility(View.GONE);
        }
    }

    private void cargarImagen(String URL) {

        ImageLoader imageLoader = AppController.getInstancia().getCargaImagen();

        imageLoader.get(URL, new ImageLoader.ImageListener() {

            public void onErrorResponse(VolleyError arg0) {
                pBar.setVisibility(View.VISIBLE);
            }

            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    pBar.setVisibility(View.GONE);
                    ivImagen.setImageBitmap(response.getBitmap());
                } else {
                    pBar.setVisibility(View.VISIBLE);
                    // set the loading image while the download is in progress
                }
            }
        });
    }
}
