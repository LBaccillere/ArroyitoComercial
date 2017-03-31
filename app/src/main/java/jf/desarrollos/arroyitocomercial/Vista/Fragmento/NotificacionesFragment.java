package jf.desarrollos.arroyitocomercial.Vista.Fragmento;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jf.desarrollos.arroyitocomercial.Controlador.Busqueda;
import jf.desarrollos.arroyitocomercial.Controlador.Volley.AppController;
import jf.desarrollos.arroyitocomercial.Modelo.Comercio;
import jf.desarrollos.arroyitocomercial.Modelo.Notificacion;
import jf.desarrollos.arroyitocomercial.R;
import jf.desarrollos.arroyitocomercial.Utilidades.Constantes;
import jf.desarrollos.arroyitocomercial.Vista.Actividad.ComercioActivity;
import jf.desarrollos.arroyitocomercial.Vista.Adaptador.ItemAdapter;

public class NotificacionesFragment extends Fragment  {
    private final String TAG = EventosFragment.class.getSimpleName();

    public static final String ACTION_NOTIFY_NEW_PROMO = "NOTIFY_NEW_PROMO";

    private RecyclerView reciclador;
    private List<Notificacion> items = new ArrayList<>();
    private TextView tvMensaje;
    private ProgressBar pBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.notificaciones_fragment, container, false);
        inicializarComponentes(v);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSwipeRefreshLayout.setRefreshing(false);
        AppController.getInstancia().cancelPendingRequests();
    }

    @Override
    public void onStop() {
        super.onStop();
        AppController.getInstancia().cancelPendingRequests(TAG);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        AppController.getInstancia().cancelPendingRequests(TAG);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void inicializarComponentes(View v) {
        reciclador = (RecyclerView) v.findViewById(R.id.reciclador);
        reciclador.setHasFixedSize(true);
        RecyclerView.LayoutManager lManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(lManager);
        tvMensaje = (TextView) v.findViewById(R.id.tvMensaje);
        ocultarMensaje();
        pBar = (ProgressBar) v.findViewById(R.id.pBar);
        setHasOptionsMenu(true);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.accent, R.color.primary, R.color.primary_dark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cargarNotificaciones();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2500);
            }
        });

        cargarNotificaciones();
    }

    public void cargarNotificaciones() {

        JsonArrayRequest req = new JsonArrayRequest(Constantes.GET_NOTIFICACIONES, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){
                        items.clear();
                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject notificacionJSON = (JSONObject) solicitud.get(i);

                            int id = notificacionJSON.getInt("id");
                            String titulo = notificacionJSON.getString("titulo");
                            String mensaje = notificacionJSON.getString("mensaje");
                            String fechaEnvio = notificacionJSON.getString("fecha_envio");
                            String horaEnvio = notificacionJSON.getString("hora_envio");
                            String fechaFin = notificacionJSON.getString("fecha_fin");
                            int idComercio = notificacionJSON.getInt("id_comercio");

                            Comercio comercio;
                            if (idComercio != 0){
                                Busqueda busqueda = new Busqueda();
                                comercio = busqueda.getComercio(idComercio);
                            } else {
                                comercio = null;
                            }

                            items.add(new Notificacion(id, titulo, mensaje, fechaEnvio, horaEnvio, fechaFin, comercio));
                        }
                    }
                    mostrarNotificaciones();
                } catch (JSONException e) {
                    mostrarMensaje();
                    mostrarErrorDeDatos();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mostrarMensaje();
                mostrarErrorDeConexion();
            }
        });
        AppController.getInstancia().addToRequestQueue(req, TAG);
    }

    private void mostrarNotificaciones() {
        if (!items.isEmpty()){
            ocultarMensaje();
        }else {
            mostrarMensaje();
        }
        ArrayList<Object> models = new ArrayList<>();
        models.addAll(items);
        ItemAdapter itemAdapter = new ItemAdapter(getActivity(), models);
        itemAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inicializarActividadComercio(items.get(reciclador.getChildPosition(v)).getComercio().getId());
            }
        });
        reciclador.setAdapter(itemAdapter);
        pBar.setVisibility(View.GONE);
    }

    private void inicializarActividadComercio(int idComercio) {
        Intent comercioActivity = new Intent(getActivity(), ComercioActivity.class);
        comercioActivity.putExtra("idComercio", idComercio);
        startActivity(comercioActivity);
    }

    public void mostrarErrorDeDatos(){
        pBar.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
        Snackbar.make(getView(), Constantes.MENSAJE_ERROR_DATOS, Snackbar.LENGTH_INDEFINITE)
                .setAction("Reintentar", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ocultarMensaje();
                        pBar.setVisibility(View.VISIBLE);
                        cargarNotificaciones();
                    }
                })
                .setActionTextColor(Color.YELLOW)
                .show();
    }

    public void mostrarErrorDeConexion(){
        pBar.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
        Snackbar.make(getView(), Constantes.MENSAJE_SIN_CONEXION, Snackbar.LENGTH_INDEFINITE)
                .setAction("Reintentar", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ocultarMensaje();
                        pBar.setVisibility(View.VISIBLE);
                        cargarNotificaciones();
                    }
                })
                .setActionTextColor(Color.YELLOW)
                .show();
    }

    private void mostrarMensaje() {
        if (items.isEmpty()){
            tvMensaje.setVisibility(View.VISIBLE);
        }
    }

    private void ocultarMensaje() {
        if (items.isEmpty()){
            tvMensaje.setVisibility(View.GONE);
        }
    }
}
