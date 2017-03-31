package jf.desarrollos.arroyitocomercial.Vista.Fragmento;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
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

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import jf.desarrollos.arroyitocomercial.Controlador.Almacen;
import jf.desarrollos.arroyitocomercial.Controlador.Busqueda;
import jf.desarrollos.arroyitocomercial.Controlador.DBHelper;
import jf.desarrollos.arroyitocomercial.Controlador.Volley.AppController;
import jf.desarrollos.arroyitocomercial.Modelo.Ciudad;
import jf.desarrollos.arroyitocomercial.Modelo.Comercio;
import jf.desarrollos.arroyitocomercial.Modelo.Contract;
import jf.desarrollos.arroyitocomercial.Modelo.Direccion;
import jf.desarrollos.arroyitocomercial.Modelo.Email;
import jf.desarrollos.arroyitocomercial.Modelo.Foto;
import jf.desarrollos.arroyitocomercial.Modelo.Horario;
import jf.desarrollos.arroyitocomercial.Modelo.Provincia;
import jf.desarrollos.arroyitocomercial.Modelo.Rubro;
import jf.desarrollos.arroyitocomercial.Modelo.Sincronizacion;
import jf.desarrollos.arroyitocomercial.Modelo.SitioWeb;
import jf.desarrollos.arroyitocomercial.Modelo.Subrubro;
import jf.desarrollos.arroyitocomercial.Modelo.Telefono;
import jf.desarrollos.arroyitocomercial.R;
import jf.desarrollos.arroyitocomercial.Utilidades.Constantes;
import jf.desarrollos.arroyitocomercial.Vista.Adaptador.ItemAdapter;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.ComercioEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.HorarioEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.SubrubroEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.RubroEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.ProvinciaEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.TelefonoEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.EmailEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.SitioWebEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.DireccionEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.FotoEntry;

public class SubrubrosFragment extends Fragment {
    private final String TAG = OfertasFragment.class.getSimpleName();

    private RecyclerView reciclador;
    private ArrayList<Subrubro> items = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tvRuta;
    private TextView tvMensaje;
    private ProgressBar pBar;
    private DBHelper db;
    private Context context;
    private FloatingActionButton fabBack;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.subrubros_fragment, container, false);
        inicializarComponentes(v);
        return v;
    }

    private void inicializarComponentes(View v) {
        reciclador = (RecyclerView) v.findViewById(R.id.reciclador);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        reciclador.setLayoutManager(mLayoutManager);
        tvRuta = (TextView) v.findViewById(R.id.tvRuta);
        tvRuta.setText("Rubros / Subrubros");
        tvMensaje = (TextView) v.findViewById(R.id.tvMensaje);
        ocultarMensaje();
        pBar = (ProgressBar) v.findViewById(R.id.pBar);
        context = getActivity().getApplicationContext();
        db = new DBHelper(context);

        fabBack = (FloatingActionButton) v.findViewById(R.id.fabBack);
        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarRubros();
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.accent, R.color.primary, R.color.primary_dark);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cargarRubros();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2500);
            }
        });

        Bundle bundle = getArguments();
        final int idRubro = bundle.getInt("idRubro");
        mostrarSubrubros(idRubro);
    }

    private void mostrarRubros() {

        Fragment rubros = new RubrosFragment();

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.root_frame, rubros);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void mostrarSubrubros(final int idRubro) {
        if (!Almacen.getInstancia().getSubrubros().isEmpty()){
            ocultarMensaje();
            items.clear();
            for (Subrubro subrubro : Almacen.getInstancia().getSubrubros()){
                if (subrubro.getRubro().getId() == idRubro){
                    Busqueda busqueda = new Busqueda();
                    if (!busqueda.getComerciosPorSubrubro(subrubro.getId()).isEmpty()){
                        items.add(subrubro);
                    }
                }
            }
        } else {
            mostrarMensaje();
        }
        ArrayList<Object> models = new ArrayList<>();
        models.addAll(items);
        ItemAdapter itemAdapter = new ItemAdapter(getActivity(), models);
        itemAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view = v;
                mostrarComercios(idRubro);
            }
        });
        reciclador.setAdapter(itemAdapter);
        pBar.setVisibility(View.GONE);
    }

    private void mostrarComercios(int idRubro) {

        int idSubrubro = items.get(reciclador.getChildPosition(view)).getId();

        Fragment comercios = new ComerciosDeSubrubroFragment();

        Bundle args = new Bundle();
        args.putInt("idRubro", idRubro);
        args.putInt("idSubrubro", idSubrubro);
        comercios.setArguments(args);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.root_frame, comercios);
        if (idSubrubro != 0){
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public void cargarRubros() {

        String URL = Constantes.GET_RUBROS_A_SINCRONIZAR + "%27" + db.getUltimaSincronizacion(RubroEntry.TABLE_NAME).replace(" ","%20") + "%27";
        Log.i(TAG,"URL RUBRO "+URL);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){
                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject rubroJSON = (JSONObject) solicitud.get(i);

                            int id = rubroJSON.getInt(RubroEntry.ID);
                            String nombre = rubroJSON.getString(RubroEntry.NOMBRE);
                            String ultimaOperacion = rubroJSON.getString(RubroEntry.ULTIMA_OPERACION);
                            String timestampModificacion = rubroJSON.getString(RubroEntry.TIMESTAMP_MODIFICACION);
                            String timestamp = rubroJSON.getString(RubroEntry.TIMESTAMP);

                            Rubro rubro = new Rubro(id, nombre, ultimaOperacion, timestampModificacion, timestamp);
                            if (db.getRubro(id) == null){
                                db.insertarRubro(rubro);
                            } else {
                                db.actualizarRubro(rubro);
                            }
                        }
                    }
                    guardarSincronizacion(RubroEntry.TABLE_NAME);
                    transferirRubrosAlAlmacen();
                    cargarSubrubros();
                } catch (JSONException e) {
                    mostrarErrorDeDatos();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mostrarErrorDeConexion();
            }
        });
        AppController.getInstancia().addToRequestQueue(req, TAG);
    }

    public void transferirRubrosAlAlmacen() {
        Almacen almacen = Almacen.getInstancia();
        almacen.setRubros(db.getRubros());
    }

    public void cargarSubrubros() {

        String URL = Constantes.GET_SUBRUBROS_A_SINCRONIZAR + "%27" + db.getUltimaSincronizacion(SubrubroEntry.TABLE_NAME).replace(" ","%20") + "%27";

        Log.i(TAG,"URL SUBRUBRO "+URL);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){
                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject subrubroJSON = (JSONObject) solicitud.get(i);

                            int id = subrubroJSON.getInt(SubrubroEntry.ID);
                            String nombre = subrubroJSON.getString(SubrubroEntry.NOMBRE);
                            int idRubro = subrubroJSON.getInt(SubrubroEntry.ID_RUBRO);
                            String ultimaOperacion = subrubroJSON.getString(SubrubroEntry.ULTIMA_OPERACION);
                            String timestampModificacion = subrubroJSON.getString(SubrubroEntry.TIMESTAMP_MODIFICACION);
                            String timestamp = subrubroJSON.getString(SubrubroEntry.TIMESTAMP);

                            Rubro rubro;
                            if (idRubro == 0){
                                rubro = null;
                            } else {
                                rubro = db.getRubro(idRubro);
                            }

                            Subrubro subrubro = new Subrubro(id, nombre, ultimaOperacion, timestampModificacion, timestamp);
                            subrubro.setRubro(rubro);

                            if (db.getSubrubro(id) == null){
                                db.insertarSubrubro(subrubro);
                            } else {
                                db.actualizarSubrubros(subrubro);
                            }
                        }
                    }
                    guardarSincronizacion(SubrubroEntry.TABLE_NAME);
                    transferirSubrubrosAlAlmacen();
                    cargarComercios();
                } catch (JSONException e) {
                    mostrarErrorDeDatos();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mostrarErrorDeConexion();
            }
        });
        AppController.getInstancia().addToRequestQueue(req, TAG);
    }

    public void transferirSubrubrosAlAlmacen() {
        Almacen almacen = Almacen.getInstancia();
        almacen.setSubrubros(db.getSubrubros());
    }

    public void cargarComercios() {

        String URL = Constantes.GET_COMERCIOS_A_SINCRONIZAR + "%27" + db.getUltimaSincronizacion(ComercioEntry.TABLE_NAME).replace(" ","%20") + "%27";
        Log.i(TAG,"URL COMERCIO "+URL);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){
                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject comercioJSON = (JSONObject) solicitud.get(i);

                            int idComercio = comercioJSON.getInt(ComercioEntry.ID);
                            String razonSocial = comercioJSON.getString(ComercioEntry.RAZON_SOCIAL);
                            String nombreFantasia = comercioJSON.getString(ComercioEntry.NOMBRE_FANTASIA);
                            int idRubro = comercioJSON.getInt(ComercioEntry.ID_RUBRO);
                            int idSubrubro = comercioJSON.getInt(ComercioEntry.ID_SUBRUBRO);
                            String descripcion = comercioJSON.getString(ComercioEntry.DESCRIPCION);
                            String activo = comercioJSON.getString(ComercioEntry.ACTIVO);
                            String ultimaOperacion = comercioJSON.getString(ComercioEntry.ULTIMA_OPERACION);
                            String timestampModificacion = comercioJSON.getString(ComercioEntry.TIMESTAMP_MODIFICACION);
                            String timestamp = comercioJSON.getString(ComercioEntry.TIMESTAMP);

                            Rubro rubro;
                            if (idRubro == 0){
                                rubro = null;
                            } else {
                                rubro = db.getRubro(idRubro);
                            }

                            Subrubro subrubro;
                            if (idSubrubro == 0){
                                subrubro = null;
                            } else {
                                subrubro = db.getSubrubro(idSubrubro);
                            }

                            Comercio comercio = new Comercio(idComercio, razonSocial, nombreFantasia,rubro, subrubro, descripcion, activo, ultimaOperacion, timestampModificacion, timestamp);

                            if (db.getComercio(idComercio) == null){
                                db.insertarComercio(comercio);
                            } else {
                                db.actualizarComercio(comercio);
                            }
                        }
                    }
                    guardarSincronizacion(ComercioEntry.TABLE_NAME);
                    transferirComerciosAlAlmacen();
                    cargarHorarios();
                } catch (JSONException e) {
                    mostrarErrorDeDatos();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mostrarErrorDeConexion();
            }
        });
        AppController.getInstancia().addToRequestQueue(req, TAG);
    }

    public void transferirComerciosAlAlmacen() {
        Almacen almacen = Almacen.getInstancia();
        almacen.setComercios(db.getComercios());
    }

    public void cargarHorarios() {

        String URL = Constantes.GET_HORARIOS_A_SINCRONIZAR + "%27" + db.getUltimaSincronizacion(HorarioEntry.TABLE_NAME).replace(" ","%20") + "%27";
        Log.i(TAG,"URL HORARIO "+URL);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){

                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject horarioJSON = (JSONObject) solicitud.get(i);

                            int id = horarioJSON.getInt(HorarioEntry.ID);
                            String dia = horarioJSON.getString(HorarioEntry.DIA);
                            Time aperturaMañana = Time.valueOf(horarioJSON.getString(HorarioEntry.APERTURA_MANIANA));
                            Time cierreMañana = Time.valueOf(horarioJSON.getString(HorarioEntry.CIERRE_MANIANA));
                            Time aperturaTarde = Time.valueOf(horarioJSON.getString(HorarioEntry.APERTURA_TARDE));
                            Time cierreTarde = Time.valueOf(horarioJSON.getString(HorarioEntry.CIERRE_TARDE));
                            String ultimaOperacion = horarioJSON.getString(HorarioEntry.ULTIMA_OPERACION);
                            String timestampModificacion = horarioJSON.getString(HorarioEntry.TIMESTAMP_MODIFICACION);
                            String timestamp = horarioJSON.getString(HorarioEntry.TIMESTAMP);

                            Horario horario = new Horario(id, dia, aperturaMañana, cierreMañana, aperturaTarde, cierreTarde, ultimaOperacion, timestampModificacion, timestamp);

                            if (db.getHorario(id) == null){
                                db.insertarHorario(horario);
                            } else {
                                db.actualizarHorario(horario);
                            }


                        }
                    }
                    guardarSincronizacion(HorarioEntry.TABLE_NAME);
                    transferirHorariosAlAlmacen();
                    cargarHorariosComercio();
                } catch (JSONException e) {
                    Log.i(TAG,"ERROR DE DATOS HORARIO"+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG,"ERROR DE CONEXION HORARIO"+error);
            }
        });
        AppController.getInstancia().addToRequestQueue(req, TAG);
    }

    public void transferirHorariosAlAlmacen() {
        Almacen almacen = Almacen.getInstancia();
        almacen.setHorarios(db.getHorarios());
    }

    public void cargarHorariosComercio() {

        String URL = Constantes.GET_HORARIOS_COMERCIO_A_SINCRONIZAR + "%27" + db.getUltimaSincronizacion(Contract.HorarioComercioEntry.TABLE_NAME).replace(" ","%20") + "%27";
        Log.i(TAG,"URL HORARIO COMERCIO "+URL);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){

                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject horarioComercioJSON = (JSONObject) solicitud.get(i);

                            int id = horarioComercioJSON.getInt(Contract.HorarioComercioEntry.ID);
                            int idHorario = horarioComercioJSON.getInt(Contract.HorarioComercioEntry.ID_HORARIO);
                            int idComercio = horarioComercioJSON.getInt(Contract.HorarioComercioEntry.ID_COMERCIO);

                            Horario horario = db.getHorario(idHorario);
                            Comercio comercio = db.getComercio(idComercio);

                            if (horario != null && comercio != null) {
                                if (db.existeHorarioComercio(id)) {
                                    db.actualizarHorarioComercio(id, horario, comercio);
                                } else {
                                    db.insertarHorarioComercio(id, horario, comercio);
                                }
                            }
                        }
                    }
                    guardarSincronizacion(Contract.HorarioComercioEntry.TABLE_NAME);
                    db.transferirHorariosALosComercios();
                    cargarProvincias();
                } catch (JSONException e) {
                    mostrarErrorDeDatos();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mostrarErrorDeConexion();
            }
        });
        AppController.getInstancia().addToRequestQueue(req, TAG);
    }

    public void cargarProvincias() {

        String URL = Constantes.GET_PROVINCIAS_A_SINCRONIZAR + "%27" + db.getUltimaSincronizacion(ProvinciaEntry.TABLE_NAME).replace(" ","%20") + "%27%20GROUP%20BY%20d.id_provincia";
        Log.i(TAG,"URL PROVINCIA "+URL);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){
                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject provinciaJSON = (JSONObject) solicitud.get(i);

                            int id = provinciaJSON.getInt(ProvinciaEntry.ID);
                            String nombre = provinciaJSON.getString(ProvinciaEntry.NOMBRE);
                            String ultimaOperacion = provinciaJSON.getString(ProvinciaEntry.ULTIMA_OPERACION);
                            String timestampModificacion = provinciaJSON.getString(ProvinciaEntry.TIMESTAMP_MODIFICACION);
                            String timestamp = provinciaJSON.getString(ProvinciaEntry.TIMESTAMP);

                            Provincia provincia = new Provincia(id, nombre, ultimaOperacion, timestampModificacion, timestamp);

                            if (db.getProvincia(id) == null){
                                db.insertarProvincia(provincia);
                            } else {
                                db.actualizarProvincia(provincia);
                            }
                        }
                    }
                    guardarSincronizacion(ProvinciaEntry.TABLE_NAME);
                    transferirProvinciasAlAlmacen();
                    Log.i(TAG,"carga ciudades ");
                    cargarCiudades();
                } catch (JSONException e) {
                    mostrarErrorDeDatos();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mostrarErrorDeConexion();
            }
        });
        AppController.getInstancia().addToRequestQueue(req, TAG);
    }

    public void transferirProvinciasAlAlmacen() {
        Almacen almacen = Almacen.getInstancia();
        almacen.setProvincias(db.getProvincias());
    }

    public void cargarCiudades() {

        String URL = Constantes.GET_CIUDADES_A_SINCRONIZAR + "%27" + db.getUltimaSincronizacion(Contract.CiudadEntry.TABLE_NAME).replace(" ","%20") + "%27%20GROUP%20BY%20d.id_ciudad";
        Log.i(TAG,"URL CIUDAD "+URL);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){
                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject ciudadJSON = (JSONObject) solicitud.get(i);

                            int id = ciudadJSON.getInt(Contract.CiudadEntry.ID);
                            String nombre = ciudadJSON.getString(Contract.CiudadEntry.NOMBRE);
                            int idProvincia = ciudadJSON.getInt(Contract.CiudadEntry.ID_PROVINCIA);
                            String ultimaOperacion = ciudadJSON.getString(Contract.CiudadEntry.ULTIMA_OPERACION);
                            String timestampModificacion = ciudadJSON.getString(Contract.CiudadEntry.TIMESTAMP_MODIFICACION);
                            String timestamp = ciudadJSON.getString(Contract.CiudadEntry.TIMESTAMP);

                            Provincia provincia;
                            if (idProvincia == 0){
                                provincia = null;
                            } else {
                                provincia = db.getProvincia(idProvincia);
                            }

                            Ciudad ciudad = new Ciudad(id, nombre, provincia, ultimaOperacion, timestampModificacion, timestamp);

                            if (db.getCiudad(id) == null){
                                db.insertarCiudad(ciudad);
                            } else {
                                db.actualizarCiudad(ciudad);
                            }
                        }
                    }
                    guardarSincronizacion(ProvinciaEntry.TABLE_NAME);
                    transferirCiudadesAlAlmacen();
                    cargarDirecciones();
                } catch (JSONException e) {
                    mostrarErrorDeDatos();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mostrarErrorDeConexion();
            }
        });
        AppController.getInstancia().addToRequestQueue(req, TAG);
    }

    public void transferirCiudadesAlAlmacen() {
        Almacen almacen = Almacen.getInstancia();
        almacen.setCiudades(db.getCiudades());
    }

    public void cargarDirecciones() {

        String URL = Constantes.GET_DIRECCIONES_A_SINCRONIZAR + "%27" + db.getUltimaSincronizacion(DireccionEntry.TABLE_NAME).replace(" ","%20") + "%27";

        Log.i(TAG,"URL DIRECCION "+URL);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){
                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject direccionJSON = (JSONObject) solicitud.get(i);

                            int id = direccionJSON.getInt(DireccionEntry.ID);
                            String nombre = direccionJSON.getString(DireccionEntry.NOMBRE);
                            int altura = direccionJSON.getInt(DireccionEntry.ALTURA);
                            int piso = direccionJSON.getInt(DireccionEntry.NUMERO_PISO);
                            String departamento = direccionJSON.getString(DireccionEntry.DEPARTAMENTO);
                            String barrio = direccionJSON.getString(DireccionEntry.BARRIO);
                            int idCiudad = direccionJSON.getInt(DireccionEntry.ID_CIUDAD);
                            int idProvincia = direccionJSON.getInt(DireccionEntry.ID_PROVINCIA);
                            String tipo = direccionJSON.getString(DireccionEntry.TIPO);
                            String latitud = direccionJSON.getString(DireccionEntry.LATITUD);
                            String longitud = direccionJSON.getString(DireccionEntry.LONGITUD);
                            String ultimaOperacion = direccionJSON.getString(DireccionEntry.ULTIMA_OPERACION);
                            String timestampModificacion = direccionJSON.getString(DireccionEntry.TIMESTAMP_MODIFICACION);
                            String timestamp = direccionJSON.getString(DireccionEntry.TIMESTAMP);

                            Provincia provincia;
                            if (idProvincia != 0){
                                provincia = db.getProvincia(idProvincia);
                            } else {
                                provincia = null;
                            }

                            Ciudad ciudad;
                            if (idCiudad != 0){
                                ciudad = db.getCiudad(idCiudad);
                            } else {
                                ciudad = null;
                            }

                            Direccion direccion = new Direccion(id, nombre, altura, piso, departamento, barrio, ciudad, provincia, tipo,
                                    latitud, longitud, ultimaOperacion, timestampModificacion, timestamp);

                            if (db.getDireccion(id) == null){
                                db.insertarDireccion(direccion);
                            } else {
                                db.actualizarDireccion(direccion);
                            }
                        }
                    }
                    guardarSincronizacion(DireccionEntry.TABLE_NAME);
                    transferirDireccionesAlAlmacen();
                    cargarDireccionesComercio();
                } catch (JSONException e) {
                    mostrarErrorDeDatos();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mostrarErrorDeConexion();
            }
        });
        AppController.getInstancia().addToRequestQueue(req, TAG);
    }

    public void transferirDireccionesAlAlmacen() {
        Almacen almacen = Almacen.getInstancia();
        almacen.setDirecciones(db.getDirecciones());
    }

    public void cargarDireccionesComercio() {

        String URL = Constantes.GET_DIRECCIONES_COMERCIO_A_SINCRONIZAR + "%27" + db.getUltimaSincronizacion(Contract.DireccionComercioEntry.TABLE_NAME).replace(" ","%20") + "%27";
        Log.i(TAG,"URL DIRECCION COMERCIO "+URL);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){

                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject direccionComercioJSON = (JSONObject) solicitud.get(i);

                            int id = direccionComercioJSON.getInt(Contract.DireccionComercioEntry.ID);
                            int idDireccion = direccionComercioJSON.getInt(Contract.DireccionComercioEntry.ID_DIRECCION);
                            int idComercio = direccionComercioJSON.getInt(Contract.DireccionComercioEntry.ID_COMERCIO);

                            Direccion direccion = db.getDireccion(idDireccion);
                            Comercio comercio = db.getComercio(idComercio);

                            if (direccion != null && comercio != null) {
                                if (db.existeDireccionComercio(id)) {
                                    db.actualizarDireccionComercio(id, direccion, comercio);
                                } else {
                                    db.insertarDireccionComercio(id, direccion, comercio);
                                }
                            }
                        }
                    }
                    guardarSincronizacion(Contract.DireccionComercioEntry.TABLE_NAME);
                    db.transferirDireccionesALosComercios();
                    cargarTelefonos();
                } catch (JSONException e) {
                    mostrarErrorDeDatos();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mostrarErrorDeConexion();
            }
        });
        AppController.getInstancia().addToRequestQueue(req, TAG);
    }

    public void cargarTelefonos() {

        String URL = Constantes.GET_TELEFONOS_A_SINCRONIZAR + "%27" + db.getUltimaSincronizacion(TelefonoEntry.TABLE_NAME).replace(" ","%20") + "%27";
        Log.i(TAG,"URL TELEFONO "+URL);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){
                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject telefonosJSON = (JSONObject) solicitud.get(i);

                            int id = telefonosJSON.getInt(TelefonoEntry.ID);
                            String numero = telefonosJSON.getString(TelefonoEntry.NUMERO);
                            String ultimaOperacion = telefonosJSON.getString(TelefonoEntry.ULTIMA_OPERACION);
                            String timestampModificacion = telefonosJSON.getString(TelefonoEntry.TIMESTAMP_MODIFICACION);
                            String timestamp = telefonosJSON.getString(TelefonoEntry.TIMESTAMP);

                            Telefono telefono = new Telefono(id, numero, ultimaOperacion, timestampModificacion, timestamp);

                            if (db.getTelefono(id) == null){
                                db.insertarTelefono(telefono);
                            } else {
                                db.actualizarTelefono(telefono);
                            }
                        }
                    }
                    guardarSincronizacion(TelefonoEntry.TABLE_NAME);
                    transferirTelefonosAlAlmacen();
                    cargarTelefonosComercio();
                } catch (JSONException e) {
                    mostrarErrorDeDatos();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mostrarErrorDeConexion();
            }
        });
        AppController.getInstancia().addToRequestQueue(req, TAG);
    }

    public void transferirTelefonosAlAlmacen() {
        Almacen almacen = Almacen.getInstancia();
        almacen.setTelefonos(db.getTelefonos());
    }

    public void cargarTelefonosComercio() {

        String URL = Constantes.GET_TELEFONOS_COMERCIO_A_SINCRONIZAR + "%27" + db.getUltimaSincronizacion(Contract.TelefonoComercioEntry.TABLE_NAME).replace(" ","%20") + "%27";
        Log.i(TAG,"URL TELEFONO COMERCIO "+URL);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){

                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject telefonoComercioJSON = (JSONObject) solicitud.get(i);

                            int id = telefonoComercioJSON.getInt(Contract.TelefonoComercioEntry.ID);
                            int idTelefono = telefonoComercioJSON.getInt(Contract.TelefonoComercioEntry.ID_TELEFONO);
                            int idComercio = telefonoComercioJSON.getInt(Contract.TelefonoComercioEntry.ID_COMERCIO);

                            Telefono telefono = db.getTelefono(idTelefono);
                            Comercio comercio = db.getComercio(idComercio);

                            if (telefono != null && comercio != null) {
                                if (db.existeTelefonoComercio(id)) {
                                    db.actualizarTelefonoComercio(id, telefono, comercio);
                                } else {
                                    db.insertarTelefonoComercio(id, telefono, comercio);
                                }
                            }
                        }
                    }
                    guardarSincronizacion(Contract.TelefonoComercioEntry.TABLE_NAME);
                    db.transferirTelefonosALosComercios();

                    cargarEmails();
                } catch (JSONException e) {
                    mostrarErrorDeDatos();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mostrarErrorDeConexion();
            }
        });
        AppController.getInstancia().addToRequestQueue(req, TAG);
    }

    public void cargarEmails() {

        String URL = Constantes.GET_EMAILS_A_SINCRONIZAR + "%27" + db.getUltimaSincronizacion(EmailEntry.TABLE_NAME).replace(" ","%20") + "%27";
        Log.i(TAG,"URL EMAIL "+URL);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){
                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject emailJSON = (JSONObject) solicitud.get(i);

                            int id = emailJSON.getInt(EmailEntry.ID);
                            String direccion = emailJSON.getString(EmailEntry.DIRECCION);
                            String ultimaOperacion = emailJSON.getString(EmailEntry.ULTIMA_OPERACION);
                            String timestampModificacion = emailJSON.getString(EmailEntry.TIMESTAMP_MODIFICACION);
                            String timestamp = emailJSON.getString(EmailEntry.TIMESTAMP);

                            Email email = new Email(id, direccion, ultimaOperacion, timestampModificacion, timestamp);

                            if (db.getEmail(id) == null){
                                db.insertarEmail(email);
                            } else {
                                db.actualizarEmail(email);
                            }
                        }
                    }
                    guardarSincronizacion(EmailEntry.TABLE_NAME);
                    transferirEmailsAlAlmacen();
                    cargarEmailsComercio();
                } catch (JSONException e) {
                    mostrarErrorDeDatos();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mostrarErrorDeConexion();
            }
        });
        AppController.getInstancia().addToRequestQueue(req, TAG);
    }

    public void transferirEmailsAlAlmacen() {
        Almacen almacen = Almacen.getInstancia();
        almacen.setEmails(db.getEmails());
    }

    public void cargarEmailsComercio() {

        String URL = Constantes.GET_EMAILS_COMERCIO_A_SINCRONIZAR + "%27" + db.getUltimaSincronizacion(Contract.EmailComercioEntry.TABLE_NAME).replace(" ","%20") + "%27";
        Log.i(TAG,"URL EMAIL COMERCIO "+URL);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){

                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject emailComercioJSON = (JSONObject) solicitud.get(i);

                            int id = emailComercioJSON.getInt(Contract.EmailComercioEntry.ID);
                            int idEmail = emailComercioJSON.getInt(Contract.EmailComercioEntry.ID_EMAIL);
                            int idComercio = emailComercioJSON.getInt(Contract.EmailComercioEntry.ID_COMERCIO);

                            Email email = db.getEmail(idEmail);
                            Comercio comercio = db.getComercio(idComercio);

                            if (email != null && comercio != null) {
                                if (db.existeEmailComercio(id)) {
                                    db.actualizarEmailComercio(id, email, comercio);
                                } else {
                                    db.insertarEmailComercio(id, email, comercio);
                                }
                            }
                        }
                    }
                    guardarSincronizacion(Contract.EmailComercioEntry.TABLE_NAME);
                    db.transferirEmailsALosComercios();

                    cargarSitiosWebs();
                } catch (JSONException e) {
                    mostrarErrorDeDatos();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mostrarErrorDeConexion();
            }
        });
        AppController.getInstancia().addToRequestQueue(req, TAG);
    }

    public void cargarSitiosWebs() {

        String URL = Constantes.GET_SITIOS_WEBS_A_SINCRONIZAR + "%27" + db.getUltimaSincronizacion(SitioWebEntry.TABLE_NAME).replace(" ","%20") + "%27";
        Log.i(TAG,"URL SITIO WEB "+URL);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){
                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject sitioWebJSON = (JSONObject) solicitud.get(i);

                            int id = sitioWebJSON.getInt(SitioWebEntry.ID);
                            String url = sitioWebJSON.getString(SitioWebEntry.URL);
                            String ultimaOperacion = sitioWebJSON.getString(SitioWebEntry.ULTIMA_OPERACION);
                            String timestampModificacion = sitioWebJSON.getString(SitioWebEntry.TIMESTAMP_MODIFICACION);
                            String timestamp = sitioWebJSON.getString(SitioWebEntry.TIMESTAMP);

                            SitioWeb sitioWeb = new SitioWeb(id, url, ultimaOperacion, timestampModificacion, timestamp);

                            if (db.getSitioWeb(id) == null){
                                db.insertarSitioWeb(sitioWeb);
                            } else {
                                db.actualizarSitioWeb(sitioWeb);
                            }
                        }
                    }
                    guardarSincronizacion(SitioWebEntry.TABLE_NAME);
                    transferirSitiosWebsAlAlmacen();
                    cargarSitiosWebsComercio();
                } catch (JSONException e) {
                    mostrarErrorDeDatos();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mostrarErrorDeConexion();
            }
        });
        AppController.getInstancia().addToRequestQueue(req, TAG);
    }

    public void transferirSitiosWebsAlAlmacen() {
        Almacen almacen = Almacen.getInstancia();
        almacen.setSitiosWebs(db.getSitiosWebs());
    }

    public void cargarSitiosWebsComercio() {

        String URL = Constantes.GET_SITIOS_WEBS_COMERCIO_A_SINCRONIZAR + "%27" + db.getUltimaSincronizacion(Contract.SitioWebComercioEntry.TABLE_NAME).replace(" ","%20") + "%27";
        Log.i(TAG,"URL SITIO WEB COMERCIO "+URL);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){

                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject sitioWebComercioJSON = (JSONObject) solicitud.get(i);

                            int id = sitioWebComercioJSON.getInt(Contract.SitioWebComercioEntry.ID);
                            int idSitioWeb = sitioWebComercioJSON.getInt(Contract.SitioWebComercioEntry.ID_SITIO_WEB);
                            int idComercio = sitioWebComercioJSON.getInt(Contract.SitioWebComercioEntry.ID_COMERCIO);

                            SitioWeb sitioWeb = db.getSitioWeb(idSitioWeb);
                            Comercio comercio = db.getComercio(idComercio);

                            if (sitioWeb != null && comercio != null) {
                                if (db.existeSitioWebComercio(id)) {
                                    db.actualizarSitioWebComercio(id, sitioWeb, comercio);
                                } else {
                                    db.insertarSitioWebComercio(id, sitioWeb, comercio);
                                }
                            }
                        }
                    }
                    guardarSincronizacion(Contract.SitioWebComercioEntry.TABLE_NAME);
                    db.transferirSitiosWebsALosComercios();

                    cargarFotos();
                } catch (JSONException e) {
                    mostrarErrorDeDatos();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mostrarErrorDeConexion();
            }
        });
        AppController.getInstancia().addToRequestQueue(req, TAG);
    }

    public void cargarFotos() {

        String URL = Constantes.GET_FOTOS_A_SINCRONIZAR + "%27" + db.getUltimaSincronizacion(FotoEntry.TABLE_NAME).replace(" ","%20") + "%27";
        Log.i(TAG,"URL FOTOS "+URL);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){
                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject fotoJSON = (JSONObject) solicitud.get(i);

                            int id = fotoJSON.getInt(FotoEntry.ID);
                            String nombreArchivo = fotoJSON.getString(FotoEntry.NOMBRE_ARCHIVO);
                            String ultimaOperacion = fotoJSON.getString(FotoEntry.ULTIMA_OPERACION);
                            String timestampModificacion = fotoJSON.getString(FotoEntry.TIMESTAMP_MODIFICACION);
                            String timestamp = fotoJSON.getString(FotoEntry.TIMESTAMP);

                            Foto foto = new Foto(id, nombreArchivo, ultimaOperacion, timestampModificacion, timestamp);

                            if (db.getFoto(id) == null){
                                db.insertarFoto(foto);
                            } else {
                                db.actualizarFoto(foto);
                            }
                        }
                    }
                    guardarSincronizacion(FotoEntry.TABLE_NAME);
                    transferirFotosAlAlmacen();
                    cargarFotosComercio();
                } catch (JSONException e) {
                    mostrarErrorDeDatos();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mostrarErrorDeConexion();
            }
        });
        AppController.getInstancia().addToRequestQueue(req, TAG);
    }

    public void transferirFotosAlAlmacen() {
        Almacen almacen = Almacen.getInstancia();
        almacen.setFotos(db.getFotos());
    }

    public void cargarFotosComercio() {

        String URL = Constantes.GET_FOTOS_COMERCIO_A_SINCRONIZAR + "%27" + db.getUltimaSincronizacion(Contract.FotoComercioEntry.TABLE_NAME).replace(" ","%20") + "%27";
        Log.i(TAG,"URL FOTO COMERCIO "+URL);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){

                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject fotoComercioJSON = (JSONObject) solicitud.get(i);

                            int id = fotoComercioJSON.getInt(Contract.FotoComercioEntry.ID);
                            int idFoto = fotoComercioJSON.getInt(Contract.FotoComercioEntry.ID_FOTO);
                            int idComercio = fotoComercioJSON.getInt(Contract.FotoComercioEntry.ID_COMERCIO);

                            Foto foto = db.getFoto(idFoto);
                            Comercio comercio = db.getComercio(idComercio);

                            if (foto != null && comercio != null) {
                                if (db.existeFotoComercio(id)) {
                                    db.actualizarFotoComercio(id, foto, comercio);
                                } else {
                                    db.insertarFotoComercio(id, foto, comercio);
                                }
                            }
                        }
                    }
                    guardarSincronizacion(Contract.FotoComercioEntry.TABLE_NAME);
                    db.transferirFotosALosComercios();
                    Bundle bundle = getArguments();
                    int idRubro = bundle.getInt("idRubro");
                    if (idRubro != 0){
                        mostrarSubrubros(idRubro);
                    }
                } catch (JSONException e) {
                    mostrarErrorDeDatos();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mostrarErrorDeConexion();
            }
        });
        AppController.getInstancia().addToRequestQueue(req, TAG);
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
                        cargarRubros();
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
                        cargarRubros();
                    }
                })
                .setActionTextColor(Color.YELLOW)
                .show();
    }

    private void guardarSincronizacion(String clase) {
        String timestamp = (DateFormat.format("yyyy-MM-dd hh:mm:ss", new Date()).toString());
        db.insertarSincronización(new Sincronizacion(clase, timestamp, timestamp));
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