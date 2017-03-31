package jf.desarrollos.arroyitocomercial.Vista.Fragmento;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
import jf.desarrollos.arroyitocomercial.Controlador.DBHelper;
import jf.desarrollos.arroyitocomercial.Controlador.Volley.AppController;
import jf.desarrollos.arroyitocomercial.Controlador.Volley.EndlessRecyclerViewScrollListener;
import jf.desarrollos.arroyitocomercial.Modelo.Comercio;
import jf.desarrollos.arroyitocomercial.Modelo.Direccion;
import jf.desarrollos.arroyitocomercial.Modelo.Oferta;
import jf.desarrollos.arroyitocomercial.R;
import jf.desarrollos.arroyitocomercial.Utilidades.Constantes;
import jf.desarrollos.arroyitocomercial.Vista.Adaptador.ItemAdapter;

public class OfertasFragment extends Fragment {
    private final String TAG = OfertasFragment.class.getSimpleName();

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView reciclador;
    private List<Oferta> items = new ArrayList<>();
    private ItemAdapter itemAdapter;
    private TextView tvMensaje;
    private ProgressBar pBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    public OfertasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ofertas_fragment, container, false);
        inicializarComponentes(v);
        return v;
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
        Log.i(TAG,"ENTRO ");
        reciclador = (RecyclerView) v.findViewById(R.id.reciclador);
        reciclador.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(linearLayoutManager);
        tvMensaje = (TextView) v.findViewById(R.id.tvMensaje);
        ocultarMensaje();
        pBar = (ProgressBar) v.findViewById(R.id.pBar);
        Context context = getActivity().getApplicationContext();
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.activity_main_swipe_refresh_layout);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.accent, R.color.primary, R.color.primary_dark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cargarOfertas();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2500);
            }
        });

        cargarOfertas();
    }

    public void cargarOfertas() {
        Log.i(TAG,"CARGA OFERTAS ");
        Log.i(TAG,"URL OFERTA "+ Constantes.GET_OFERTAS);
        JsonArrayRequest req = new JsonArrayRequest(Constantes.GET_OFERTAS, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {

                try {
                    if(solicitud.length() > 0){
                        items.clear();
                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject ofertaJSON = (JSONObject) solicitud.get(i);

                            int idOferta = ofertaJSON.getInt("id");
                            String foto = ofertaJSON.getString("foto");
                            String titulo = ofertaJSON.getString("titulo");
                            String descripcion = ofertaJSON.getString("descripcion");
                            double precio = ofertaJSON.getDouble("precio");
                            int descuento = ofertaJSON.getInt("descuento");
                            int idComercio = ofertaJSON.getInt("id_comercio");
                            int idDireccion = 0;
                            String sitioWeb = ofertaJSON.getString("sitio_web");
                            String fechaInicio = ofertaJSON.getString("fecha_inicio");
                            String fechaFin = ofertaJSON.getString("fecha_fin");
                            String horaInicio = ofertaJSON.getString("hora_inicio");
                            String horaFin = ofertaJSON.getString("hora_fin");
                            int prioridad = ofertaJSON.getInt("prioridad");

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

                            items.add(new Oferta(idOferta, foto, titulo, descripcion, precio, descuento, comercio, direccion, sitioWeb,
                                    fechaInicio, fechaFin, horaInicio, horaFin, prioridad));
                        }
                    }
                    mostrarOfertas();
                } catch (JSONException e) {
                    Log.i(TAG,"ERROR DATOS ");
                    mostrarMensaje();
                    mostrarErrorDeDatos();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG,"ERROR CONEXION ");
                mostrarMensaje();
                mostrarErrorDeConexion();
            }
        });
        AppController.getInstancia().addToRequestQueue(req, TAG);
    }

    private void mostrarOfertas() {
        Log.i(TAG,"MOSTRAR OFERTAS ");
        if (!items.isEmpty()){
            ocultarMensaje();
            final ArrayList<Object> models = new ArrayList<>();
            models.addAll(items);
            Log.i(TAG,"ITEMS "+models);
            itemAdapter = new ItemAdapter(getActivity(), models);
            reciclador.setAdapter(itemAdapter);
        }else {
            mostrarMensaje();
        }
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
                        cargarOfertas();
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
                        cargarOfertas();
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