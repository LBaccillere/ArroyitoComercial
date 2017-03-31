package jf.desarrollos.arroyitocomercial.Vista.Fragmento;

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
import jf.desarrollos.arroyitocomercial.Modelo.Direccion;
import jf.desarrollos.arroyitocomercial.Modelo.Evento;
import jf.desarrollos.arroyitocomercial.R;
import jf.desarrollos.arroyitocomercial.Utilidades.Constantes;
import jf.desarrollos.arroyitocomercial.Vista.Adaptador.ItemAdapter;

public class EventosFragment extends Fragment {
    private final String TAG = EventosFragment.class.getSimpleName();

    private RecyclerView reciclador;
    private List<Evento> items = new ArrayList<>();
    private TextView tvMensaje;
    private ProgressBar pBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.eventos_fragment, container, false);
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
                        cargarEventos();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2500);
            }
        });

        cargarEventos();
    }

    public void cargarEventos() {

        JsonArrayRequest req = new JsonArrayRequest(Constantes.GET_EVENTOS, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){
                        items.clear();
                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject eventoJSON = (JSONObject) solicitud.get(i);

                            int idEvento = eventoJSON.getInt("id");
                            String foto = eventoJSON.getString("foto");
                            String titulo = eventoJSON.getString("titulo");
                            String descripcion = eventoJSON.getString("descripcion");
                            double precio = eventoJSON.getDouble("precio");
                            int idComercio = eventoJSON.getInt("id_comercio");
                            int idDireccion = 0;
                            String sitioWeb = eventoJSON.getString("sitio_web");
                            String fechaInicio = eventoJSON.getString("fecha_inicio");
                            String fechaFin = eventoJSON.getString("fecha_fin");
                            String horaInicio = eventoJSON.getString("hora_inicio");
                            String horaFin = eventoJSON.getString("hora_fin");
                            Comercio comercio;

                            if (idComercio != 0){
                                Busqueda busqueda = new Busqueda();
                                comercio = busqueda.getComercio(idComercio);
                            } else {
                                comercio = null;
                            }

                            Direccion direccion;
                            if (idDireccion != 0){
                                Busqueda busqueda = new Busqueda();
                                direccion = busqueda.getDireccion(idDireccion);
                            } else {
                                direccion = null;
                            }

                            items.add(new Evento(idEvento, foto, titulo, descripcion, precio, sitioWeb, comercio, direccion, fechaInicio, fechaFin, horaInicio, horaFin));
                        }
                    }
                    mostrarEventos();
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

    private void mostrarEventos() {
        if (!items.isEmpty()){
            ocultarMensaje();
        }else {
            mostrarMensaje();
        }
        ArrayList<Object> models = new ArrayList<>();
        models.addAll(items);
        ItemAdapter itemAdapter = new ItemAdapter(getActivity(), models);
        reciclador.setAdapter(itemAdapter);
        pBar.setVisibility(View.GONE);
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
                        cargarEventos();
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
                        cargarEventos();
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
