package jf.desarrollos.arroyitocomercial.Vista.Actividad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.format.DateFormat;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import jf.desarrollos.arroyitocomercial.Controlador.Almacen;
import jf.desarrollos.arroyitocomercial.Controlador.DBHelper;
import jf.desarrollos.arroyitocomercial.Controlador.Operacion;
import jf.desarrollos.arroyitocomercial.Controlador.Volley.AppController;
import jf.desarrollos.arroyitocomercial.Modelo.Ciudad;
import jf.desarrollos.arroyitocomercial.Modelo.Comercio;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.ComercioEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.HorarioEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.HorarioComercioEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.SubrubroEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.RubroEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.ProvinciaEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.CiudadEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.TelefonoEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.TelefonoComercioEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.EmailEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.EmailComercioEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.SitioWebEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.SitioWebComercioEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.DireccionEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.DireccionComercioEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.FotoEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.FotoComercioEntry;
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

public class SplashActivity extends Activity {
    private static final String TAG = SplashActivity.class.getSimpleName();

    public static final int segundos = 1;
    public static final int milisegundos = segundos*1000;
    private DBHelper db;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        inicializarComponentes();
    }

    private void inicializarComponentes(){
        context = this;
        db = new DBHelper(context);
        verificarTokenNotificacion();
        //verificarUltimaSincronizacion();
    }

    private void verificarTokenNotificacion() {

        SharedPreferences preferenciasUsuario = getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE);
        String tokenNotifiacion = preferenciasUsuario.getString("token_notificacion", "");
        if (tokenNotifiacion.equals("")){
            sendTokenToServer(FirebaseInstanceId.getInstance().getToken());
        } else {
            if (!tokenNotifiacion.equals(FirebaseInstanceId.getInstance().getToken())) {
                sendTokenToServer(FirebaseInstanceId.getInstance().getToken());
            } else {
                cargarRubros();
            }
        }
    }

    /*private void verificarUltimaSincronizacion() {

        /*SharedPreferences preferenciasUsuario = getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE);
        String ultimaSincronizacion = preferenciasUsuario.getString("sync", "");
        if (ultimaSincronizacion.equals("")){
            cargarRubros();
        } else {

            if (!ultimaSincronizacion.compareTo(db.getUltimaSincronizacion(ComercioEntry.TABLE_NAME))) {
                sendTokenToServer(FirebaseInstanceId.getInstance().getToken());
            } else {
                verificarUltimaSincronizacion();
            }
        }
        String timestamp = (DateFormat.format("yyyy-MM-dd HH:mm:ss", new Date()).toString());
        Log.i(TAG,"fecha 1 hoy " + timestamp);
        Log.i(TAG,"fecha 2 comer " + db.getUltimaSincronizacion(ComercioEntry.TABLE_NAME));
        Log.i(TAG,"diferencia " + Operacion.diferenciaDeFechas(timestamp,db.getUltimaSincronizacion(ComercioEntry.TABLE_NAME)));
    }*/

    private void sendTokenToServer(final String fcmToken) {
        Log.i(TAG, "sendTokenToServer");

        String email = "prueba@correo.com";
        String password = "prueba@correo.com";
        String tokenNotificacion = fcmToken;
        String ultimaOperacion = "A";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long timestampModificacion = timestamp.getTime();
        long sTimestamp = timestamp.getTime();

        final String CONSULTA = "INSERT INTO usuario " +
                "(email,password,token_notificacion,ultima_operacion,timestamp_modificacion, timestamp) " +
                "VALUES ('"+email+"','"+password+"','"+tokenNotificacion+"'," +
                "'"+ultimaOperacion+"','"+timestampModificacion+"','"+sTimestamp+"')";
        final String OBJETO = "USUARIO";

        HashMap<String, String> map = new HashMap<>();
        map.put("consulta", CONSULTA);
        map.put("nombreObjeto", OBJETO);

        JSONObject jsonObject = new JSONObject(map);
        AppController.getInstancia().addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Constantes.SET_TOKEN,
                        jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject solictud) {
                                Log.i(TAG,"token correcto");
                                guardarToken(fcmToken);
                                cargarRubros();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i(TAG,"token incorrecto");
                                transferirDatosAlAlmacen();
                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        headers.put("Accept", "application/json");
                        return headers;
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8" + getParamsEncoding();
                    }
                }
        );
    }

    private void guardarToken(String fcmToken) {
        SharedPreferences preferenciasUsuario = getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editarPreferencias = preferenciasUsuario.edit();
        editarPreferencias.putString("token_notificacion", String.valueOf(fcmToken));
        Log.i(TAG,"token = "+fcmToken);
        editarPreferencias.apply();
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
                    transferirDatosAlAlmacen();
                    Log.i(TAG,"ERROR DE DATOS RUBRO "+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                transferirDatosAlAlmacen();
                Log.i(TAG,"ERROR DE CONEXION RUBRO"+error);
            }
        });
        AppController.getInstancia().addToRequestQueue(req);
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
                    transferirDatosAlAlmacen();
                    Log.i(TAG,"ERROR DE DATOS SUBRUBRO"+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                transferirDatosAlAlmacen();
                Log.i(TAG,"ERROR DE CONEXION SUBRUBRO"+error);
            }
        });
        AppController.getInstancia().addToRequestQueue(req);
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
                Log.i(TAG,"CANTIDAD DE COMERCIOS "+solicitud.length());
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
                    transferirDatosAlAlmacen();
                    Log.i(TAG,"ERROR DE DATOS COMERCIO"+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                transferirDatosAlAlmacen();
                Log.i(TAG,"ERROR DE CONEXION COMERCIO"+error);
            }
        });
        AppController.getInstancia().addToRequestQueue(req);
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
        AppController.getInstancia().addToRequestQueue(req);
    }

    public void transferirHorariosAlAlmacen() {
        Almacen almacen = Almacen.getInstancia();
        almacen.setHorarios(db.getHorarios());
    }

    public void cargarHorariosComercio() {

        String URL = Constantes.GET_HORARIOS_COMERCIO_A_SINCRONIZAR + "%27" + db.getUltimaSincronizacion(HorarioComercioEntry.TABLE_NAME).replace(" ","%20") + "%27";
        Log.i(TAG,"URL HORARIO COMERCIO "+URL);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){

                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject horarioComercioJSON = (JSONObject) solicitud.get(i);

                            int id = horarioComercioJSON.getInt(HorarioComercioEntry.ID);
                            int idHorario = horarioComercioJSON.getInt(HorarioComercioEntry.ID_HORARIO);
                            int idComercio = horarioComercioJSON.getInt(HorarioComercioEntry.ID_COMERCIO);
                            Log.i(TAG,"id horario "+idHorario + " id comercio "+idComercio);
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
                    guardarSincronizacion(HorarioComercioEntry.TABLE_NAME);
                    db.transferirHorariosALosComercios();
                    cargarProvincias();
                } catch (JSONException e) {
                    transferirDatosAlAlmacen();
                    Log.i(TAG,"ERROR DE DATOS HORARIO COMERCIO"+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                transferirDatosAlAlmacen();
                Log.i(TAG,"ERROR DE CONEXION HORARIO COMERCIO"+error);
            }
        });
        AppController.getInstancia().addToRequestQueue(req);
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
                    transferirDatosAlAlmacen();
                    Log.i(TAG,"ERROR DE DATOSPROVINCIA"+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                transferirDatosAlAlmacen();
                Log.i(TAG,"ERROR DE CONEXIONPROVINCIA"+error);
            }
        });
        AppController.getInstancia().addToRequestQueue(req);
    }

    public void transferirProvinciasAlAlmacen() {
        Almacen almacen = Almacen.getInstancia();
        almacen.setProvincias(db.getProvincias());
    }

    public void cargarCiudades() {

        String URL = Constantes.GET_CIUDADES_A_SINCRONIZAR + "%27" + db.getUltimaSincronizacion(CiudadEntry.TABLE_NAME).replace(" ","%20") + "%27%20GROUP%20BY%20d.id_ciudad";
        Log.i(TAG,"URL CIUDAD "+URL);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){
                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject ciudadJSON = (JSONObject) solicitud.get(i);

                            int id = ciudadJSON.getInt(CiudadEntry.ID);
                            String nombre = ciudadJSON.getString(CiudadEntry.NOMBRE);
                            int idProvincia = ciudadJSON.getInt(CiudadEntry.ID_PROVINCIA);
                            String ultimaOperacion = ciudadJSON.getString(CiudadEntry.ULTIMA_OPERACION);
                            String timestampModificacion = ciudadJSON.getString(CiudadEntry.TIMESTAMP_MODIFICACION);
                            String timestamp = ciudadJSON.getString(CiudadEntry.TIMESTAMP);

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
                    transferirDatosAlAlmacen();
                    Log.i(TAG,"ERROR DE DATOS CIUDAD"+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                transferirDatosAlAlmacen();
                Log.i(TAG,"ERROR DE CONEXION CIUDAD"+error);
            }
        });
        AppController.getInstancia().addToRequestQueue(req);
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
                    transferirDatosAlAlmacen();
                    Log.i(TAG,"ERROR DE DATOS DIRECCION"+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                transferirDatosAlAlmacen();
                Log.i(TAG,"ERROR DE CONEXION DIRECCION"+error);
            }
        });
        AppController.getInstancia().addToRequestQueue(req);
    }

    public void transferirDireccionesAlAlmacen() {
        Almacen almacen = Almacen.getInstancia();
        almacen.setDirecciones(db.getDirecciones());
    }

    public void cargarDireccionesComercio() {

        String URL = Constantes.GET_DIRECCIONES_COMERCIO_A_SINCRONIZAR + "%27" + db.getUltimaSincronizacion(DireccionComercioEntry.TABLE_NAME).replace(" ","%20") + "%27";
        Log.i(TAG,"URL DIRECCION COMERCIO "+URL);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){

                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject direccionComercioJSON = (JSONObject) solicitud.get(i);

                            int id = direccionComercioJSON.getInt(DireccionComercioEntry.ID);
                            int idDireccion = direccionComercioJSON.getInt(DireccionComercioEntry.ID_DIRECCION);
                            int idComercio = direccionComercioJSON.getInt(DireccionComercioEntry.ID_COMERCIO);

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
                    guardarSincronizacion(DireccionComercioEntry.TABLE_NAME);
                    db.transferirDireccionesALosComercios();
                    cargarTelefonos();
                } catch (JSONException e) {
                    transferirDatosAlAlmacen();
                    Log.i(TAG,"ERROR DE DATOS DIRECCION COMERCIO"+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                transferirDatosAlAlmacen();
                Log.i(TAG,"ERROR DE CONEXION DIRECCION COMERCIO"+error);
            }
        });
        AppController.getInstancia().addToRequestQueue(req);
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
                    transferirDatosAlAlmacen();
                    Log.i(TAG,"ERROR DE DATOS TELEFONO"+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                transferirDatosAlAlmacen();
                Log.i(TAG,"ERROR DE CONEXION TELEFONO"+error);
            }
        });
        AppController.getInstancia().addToRequestQueue(req);
    }

    public void transferirTelefonosAlAlmacen() {
        Almacen almacen = Almacen.getInstancia();
        almacen.setTelefonos(db.getTelefonos());
    }

    public void cargarTelefonosComercio() {

        String URL = Constantes.GET_TELEFONOS_COMERCIO_A_SINCRONIZAR + "%27" + db.getUltimaSincronizacion(TelefonoComercioEntry.TABLE_NAME).replace(" ","%20") + "%27";
        Log.i(TAG,"URL TELEFONO COMERCIO "+URL);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){

                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject telefonoComercioJSON = (JSONObject) solicitud.get(i);

                            int id = telefonoComercioJSON.getInt(TelefonoComercioEntry.ID);
                            int idTelefono = telefonoComercioJSON.getInt(TelefonoComercioEntry.ID_TELEFONO);
                            int idComercio = telefonoComercioJSON.getInt(TelefonoComercioEntry.ID_COMERCIO);

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
                    guardarSincronizacion(TelefonoComercioEntry.TABLE_NAME);
                    db.transferirTelefonosALosComercios();

                    cargarEmails();
                } catch (JSONException e) {
                    transferirDatosAlAlmacen();
                    Log.i(TAG,"ERROR DE DATOS TELEFONO COMERCIO"+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                transferirDatosAlAlmacen();
                Log.i(TAG,"ERROR DE CONEXION TELEFONO COMERCIO"+error);
            }
        });
        AppController.getInstancia().addToRequestQueue(req);
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
                    transferirDatosAlAlmacen();
                    Log.i(TAG,"ERROR DE DATOS EMAIL"+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                transferirDatosAlAlmacen();
                Log.i(TAG,"ERROR DE CONEXION EMAIL"+error);
            }
        });
        AppController.getInstancia().addToRequestQueue(req);
    }

    public void transferirEmailsAlAlmacen() {
        Almacen almacen = Almacen.getInstancia();
        almacen.setEmails(db.getEmails());
    }

    public void cargarEmailsComercio() {

        String URL = Constantes.GET_EMAILS_COMERCIO_A_SINCRONIZAR + "%27" + db.getUltimaSincronizacion(EmailComercioEntry.TABLE_NAME).replace(" ","%20") + "%27";
        Log.i(TAG,"URL EMAIL COMERCIO "+URL);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){

                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject emailComercioJSON = (JSONObject) solicitud.get(i);

                            int id = emailComercioJSON.getInt(EmailComercioEntry.ID);
                            int idEmail = emailComercioJSON.getInt(EmailComercioEntry.ID_EMAIL);
                            int idComercio = emailComercioJSON.getInt(EmailComercioEntry.ID_COMERCIO);

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
                    guardarSincronizacion(EmailComercioEntry.TABLE_NAME);
                    db.transferirEmailsALosComercios();

                    cargarSitiosWebs();
                } catch (JSONException e) {
                    transferirDatosAlAlmacen();
                    Log.i(TAG,"ERROR DE DATOS EMAIL COMERCIO"+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                transferirDatosAlAlmacen();
                Log.i(TAG,"ERROR DE CONEXION EMAIL COMERCIO"+error);
            }
        });
        AppController.getInstancia().addToRequestQueue(req);
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
                    transferirDatosAlAlmacen();
                    Log.i(TAG,"ERROR DE DATOS SITIO WEB"+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                transferirDatosAlAlmacen();
                Log.i(TAG,"ERROR DE CONEXION SITIO WEB"+error);
            }
        });
        AppController.getInstancia().addToRequestQueue(req);
    }

    public void transferirSitiosWebsAlAlmacen() {
        Almacen almacen = Almacen.getInstancia();
        almacen.setSitiosWebs(db.getSitiosWebs());
    }

    public void cargarSitiosWebsComercio() {

        String URL = Constantes.GET_SITIOS_WEBS_COMERCIO_A_SINCRONIZAR + "%27" + db.getUltimaSincronizacion(SitioWebComercioEntry.TABLE_NAME).replace(" ","%20") + "%27";
        Log.i(TAG,"URL SITIO WEB COMERCIO "+URL);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){

                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject sitioWebComercioJSON = (JSONObject) solicitud.get(i);

                            int id = sitioWebComercioJSON.getInt(SitioWebComercioEntry.ID);
                            int idSitioWeb = sitioWebComercioJSON.getInt(SitioWebComercioEntry.ID_SITIO_WEB);
                            int idComercio = sitioWebComercioJSON.getInt(SitioWebComercioEntry.ID_COMERCIO);

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
                    guardarSincronizacion(SitioWebComercioEntry.TABLE_NAME);
                    db.transferirSitiosWebsALosComercios();

                    cargarFotos();
                } catch (JSONException e) {
                    transferirDatosAlAlmacen();
                    Log.i(TAG,"ERROR DE DATOS SITIO WEB COMERCIO"+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                transferirDatosAlAlmacen();
                Log.i(TAG,"ERROR DE CONEXION SITIO WEB COMERCIO"+error);
            }
        });
        AppController.getInstancia().addToRequestQueue(req);
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
                    transferirDatosAlAlmacen();
                    Log.i(TAG,"ERROR DE DATOS FOTOS"+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                transferirDatosAlAlmacen();
                Log.i(TAG,"ERROR DE CONEXION FOTOS"+error);
            }
        });
        AppController.getInstancia().addToRequestQueue(req);
    }

    public void transferirFotosAlAlmacen() {
        Almacen almacen = Almacen.getInstancia();
        almacen.setFotos(db.getFotos());
    }

    public void cargarFotosComercio() {

        String URL = Constantes.GET_FOTOS_COMERCIO_A_SINCRONIZAR + "%27" + db.getUltimaSincronizacion(FotoComercioEntry.TABLE_NAME).replace(" ","%20") + "%27";
        Log.i(TAG,"URL FOTO COMERCIO "+URL);
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){

                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject fotoComercioJSON = (JSONObject) solicitud.get(i);

                            int id = fotoComercioJSON.getInt(FotoComercioEntry.ID);
                            int idFoto = fotoComercioJSON.getInt(FotoComercioEntry.ID_FOTO);
                            int idComercio = fotoComercioJSON.getInt(FotoComercioEntry.ID_COMERCIO);

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
                    guardarSincronizacion(FotoComercioEntry.TABLE_NAME);
                    db.transferirFotosALosComercios();
                    inicializarSplash();
                } catch (JSONException e) {
                    transferirDatosAlAlmacen();
                    Log.i(TAG,"ERROR DE DATOS FOTO COMERCIO"+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                transferirDatosAlAlmacen();
                Log.i(TAG,"ERROR DE CONEXION FOTO COMERCIO"+error);
            }
        });
        AppController.getInstancia().addToRequestQueue(req);
    }

    public void transferirDatosAlAlmacen() {
        Almacen almacen = Almacen.getInstancia();
        almacen.setRubros(db.getRubros());
        almacen.setSubrubros(db.getSubrubros());
        almacen.setComercios(db.getComercios());
        almacen.setProvincias(db.getProvincias());
        almacen.setCiudades(db.getCiudades());
        almacen.setHorarios(db.getHorarios());
        db.transferirHorariosALosComercios();
        almacen.setDirecciones(db.getDirecciones());
        db.transferirDireccionesALosComercios();
        almacen.setTelefonos(db.getTelefonos());
        db.transferirTelefonosALosComercios();
        almacen.setEmails(db.getEmails());
        db.transferirEmailsALosComercios();
        almacen.setSitiosWebs(db.getSitiosWebs());
        db.transferirSitiosWebsALosComercios();
        almacen.setFotos(db.getFotos());
        db.transferirFotosALosComercios();
        inicializarSplash();
    }

    private void guardarSincronizacion(String clase) {
        String timestamp = (DateFormat.format("yyyy-MM-dd HH:mm:ss", new Date()).toString());
        db.insertarSincronización(new Sincronizacion(clase, timestamp, timestamp));
    }

    public void inicializarSplash(){

        new CountDownTimer(milisegundos,1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {
                //pbCarga.setProgress((int)((milisegundos-millisUntilFinished)/1000));
            }

            @Override
            public void onFinish() {
                inicializarActividadInicio();
            }
        }.start();
    }

    private void inicializarActividadInicio() {
        Intent inicio = new Intent(SplashActivity.this,MainActivity.class);
        startActivity(inicio);
        finish();
    }
}
