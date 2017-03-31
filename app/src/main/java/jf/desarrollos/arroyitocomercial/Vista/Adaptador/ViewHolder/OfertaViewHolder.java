package jf.desarrollos.arroyitocomercial.Vista.Adaptador.ViewHolder;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jf.desarrollos.arroyitocomercial.Controlador.Operacion;
import jf.desarrollos.arroyitocomercial.Controlador.Volley.AppController;
import jf.desarrollos.arroyitocomercial.Modelo.Oferta;
import jf.desarrollos.arroyitocomercial.R;
import jf.desarrollos.arroyitocomercial.Utilidades.Constantes;
import jf.desarrollos.arroyitocomercial.Utilidades.MemUtils;
import jf.desarrollos.arroyitocomercial.Vista.Actividad.ComercioActivity;
import jf.desarrollos.arroyitocomercial.Vista.Fragmento.ComerciosDeSubrubroFragment;

public class OfertaViewHolder extends RecyclerView.ViewHolder {
    private final String TAG = OfertaViewHolder.class.getSimpleName();

    private ImageView ivOferta;
    private ProgressBar pBar;
    private Button btnContacto;
    private TextView tvDireccion;
    private ImageView ivDireccion;
    private TextView tvDescripcion;
    private TextView tvComercio;
    private TextView tvTitulo;
    private TextView tvPrecio;
    private TextView fechaFin;
    private RelativeLayout layoutOferta;
    private Context contexto;

    public OfertaViewHolder(View itemView) {
        super(itemView);

        ivOferta = (ImageView) itemView.findViewById(R.id.ivOferta);
        pBar = (ProgressBar) itemView.findViewById(R.id.pBar);
        btnContacto = (Button) itemView.findViewById(R.id.btnContacto);
        tvDireccion = (TextView) itemView.findViewById(R.id.tvDireccion);
        ivDireccion = (ImageView) itemView.findViewById(R.id.ivDireccion);
        ivDireccion.setVisibility(View.GONE);
        tvDescripcion = (TextView) itemView.findViewById(R.id.tvDescripcion);
        tvComercio = (TextView) itemView.findViewById(R.id.tvComercio);
        tvTitulo = (TextView) itemView.findViewById(R.id.tvTitulo);
        tvPrecio = (TextView) itemView.findViewById(R.id.tvPrecio);
        fechaFin = (TextView) itemView.findViewById(R.id.tvFechaFin);
        layoutOferta = (RelativeLayout) itemView.findViewById (R.id.layoutOferta);
    }

    public void bind(final Context contexto, final Oferta model, int posicion) {

        this.contexto = contexto;

        if(!model.getTitulo().equals("")){
            tvTitulo.setText(model.getTitulo());
        } else {
            tvTitulo.setVisibility(View.GONE);
        }

        if(model.getComercio() != null){
            tvComercio.setText(model.getComercio().getNombreFantasia());
            tvComercio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inicializarActividadComercio(model.getComercio().getId());
                }
            });
        } else {
            tvComercio.setVisibility(View.GONE);
        }

        final String URL = Constantes.GET_SQL_IMAGEN + "nombre-archivo="+model.getFoto() + "&carpeta=oferta";
        cargarImagen(URL);

        if(!model.getDescripcion().equals("")){
            tvDescripcion.setText(model.getDescripcion());
        } else {
            tvDescripcion.setVisibility(View.GONE);
        }

        if (model.getPrecio() == 0.0){
            tvPrecio.setVisibility(View.GONE);
        } else {
            DecimalFormat df = new DecimalFormat("#,###.##");
            tvPrecio.setText("$"+df.format(model.getPrecio()));
        }

        if (!model.getFechaFin().equals("0000-00-00")) {
            String fechaActual = DateFormat.format("yyyy-MM-dd HH:mm:ss", new Date()).toString();
            if ((model.getFechaFin() + " " + model.getHoraFin()).compareTo(fechaActual) <= 0) {
                fechaFin.setText("Oferta finalizada");
            } else {
                fechaFin.setText("Oferta válida hasta el " + Operacion.formatearFecha(model.getFechaFin()) + "a las " + Operacion.formatearHora(model.getHoraFin()));
            }
        } else {
            fechaFin.setVisibility(View.GONE);
        }

        btnContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!model.getSitioWeb().equals("")){
                    inicializarActividadSitioWeb(model.getSitioWeb());
                } else if (model.getComercio() != null){
                    inicializarActividadComercio(model.getComercio().getId());
                }
            }
        });
    }

    private void inicializarActividadSitioWeb(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uriUrl);
        contexto.startActivity(intent);
    }

    private void inicializarActividadComercio(int idComercio) {
        Intent comercioActivity = new Intent(contexto, ComercioActivity.class);
        comercioActivity.putExtra("idComercio", idComercio);
        contexto.startActivity(comercioActivity);
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
                    ivOferta.setImageBitmap(redimensionarImagenMaximo(response.getBitmap()));
                } else {
                    pBar.setVisibility(View.VISIBLE);
                    // set the loading image while the download is in progress
                }
            }
        });
    }

    public Bitmap redimensionarImagenMaximo(Bitmap mBitmap){
        //Se define el maximo ancho o alto que tendra la imagen final
        int maxAncho = getAnchoPantalla();
        int maxAlto = maxAncho * 3;

        //Ancho y alto de la imagen original
        int anchoImagenOriginal = mBitmap.getWidth();
        int altoImagenOriginal = mBitmap.getHeight();

        //Se calcula ancho y alto de la imagen final
        float escalaAncho = ((float) maxAncho) / anchoImagenOriginal;
        float escalaAlto = ((float) maxAlto) / altoImagenOriginal;

        int nuevoAncho = maxAncho;
        int nuevoAlto = (int) (altoImagenOriginal*escalaAncho);

        layoutOferta.getLayoutParams().width = nuevoAncho;
        layoutOferta.getLayoutParams().height = nuevoAlto;

        //create scaled bitmap using Matrix
        Matrix matrix = new Matrix();
        matrix.postScale(escalaAncho, escalaAncho);

        Bitmap bitmapScaled = Bitmap.createBitmap(
                mBitmap,
                0, 0,
                mBitmap.getWidth(), mBitmap.getHeight(),
                matrix, true);

        return bitmapScaled;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int getAnchoPantalla(){
        Activity activity =(Activity) contexto;
        Display display = activity.getWindowManager().getDefaultDisplay();
        String displayName = display.getName();  // minSdkVersion=17+

// Tamaño en píxeles
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

// dpi
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int heightPixels = metrics.heightPixels;
        int widthPixels = metrics.widthPixels;
        int densityDpi = metrics.densityDpi;
        float xdpi = metrics.xdpi;
        float ydpi = metrics.ydpi;

// Deprecated
        int screenHeight = display.getHeight();
        int screenWidth = display.getWidth();

// Orientación
        int orientation = contexto.getResources().getConfiguration().orientation;
        return screenWidth;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void getDimencion(){
        Activity activity =(Activity) contexto;
        Display display = activity.getWindowManager().getDefaultDisplay();
        String displayName = display.getName();  // minSdkVersion=17+

// Tamaño en píxeles
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

// dpi
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int heightPixels = metrics.heightPixels;
        int widthPixels = metrics.widthPixels;
        int densityDpi = metrics.densityDpi;
        float xdpi = metrics.xdpi;
        float ydpi = metrics.ydpi;
        Log.i("INFO", "Ancho en píxeles  = " + widthPixels);
        Log.i("INFO", "Alto en píxeles   = " + heightPixels);
        Log.i("INFO", "Densidad dpi      = " + densityDpi);
        Log.i("INFO", "x dpi             = " + xdpi);
        Log.i("INFO", "y dpi             = " + ydpi);

// Deprecated
        int screenHeight = display.getHeight();
        int screenWidth = display.getWidth();
        Log.i("INFO", "Alto de pantalla  = " + screenHeight);
        Log.i("INFO", "Ancho de pantalla = " + screenWidth);

// Orientación
        int orientation = contexto.getResources().getConfiguration().orientation;
        Log.i("INFO", "Orientación       = " + orientation);
    }
}
