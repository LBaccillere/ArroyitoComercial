package jf.desarrollos.arroyitocomercial.Controlador.Volley;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    private static AppController instancia;
    private ImageLoader imagenCarga;
    private RequestQueue colaSolicitud;

    @Override
    public void onCreate() {
        super.onCreate();
        instancia = this;
    }

    public static synchronized AppController getInstancia() {
        return instancia;
    }

    public RequestQueue getColaSolicitudes() {
        if (colaSolicitud == null) {
            colaSolicitud = Volley.newRequestQueue(getApplicationContext());
        }

        return colaSolicitud;
    }

    public ImageLoader getCargaImagen() {
        getColaSolicitudes();
        if (imagenCarga == null) {
            imagenCarga = new ImageLoader(this.colaSolicitud,
                    new LruBitmapCache());
        }
        return this.imagenCarga;
    }

    public <T> void addToRequestQueue(Request<T> solicitud, String tag) {
        solicitud.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getColaSolicitudes().add(solicitud);
    }

    public <T> void addToRequestQueue(Request<T> solicitud) {
        solicitud.setTag(TAG);
        getColaSolicitudes().add(solicitud);
    }

    public void cancelPendingRequests(Object tag) {
        if (colaSolicitud != null) {
            colaSolicitud.cancelAll(tag);
        }
    }

    public void cancelPendingRequests() {
        if (colaSolicitud != null) {
            colaSolicitud.cancelAll(TAG);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
