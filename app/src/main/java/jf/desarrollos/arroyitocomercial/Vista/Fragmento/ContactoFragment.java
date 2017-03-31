package jf.desarrollos.arroyitocomercial.Vista.Fragmento;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import jf.desarrollos.arroyitocomercial.Controlador.Validacion;
import jf.desarrollos.arroyitocomercial.Controlador.Volley.AppController;
import jf.desarrollos.arroyitocomercial.R;
import jf.desarrollos.arroyitocomercial.Utilidades.Constantes;

public class ContactoFragment extends Fragment {
    private final String TAG = ContactoFragment.class.getSimpleName();

    private EditText etNombre;
    private EditText etEmail;
    private EditText etTelefono;
    private EditText etSitioWeb;
    private EditText etAsunto;
    private EditText etMensaje;
    private ProgressDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contacto_fragment, container, false);
        inicializarComponentes(v);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    private void inicializarComponentes(View v) {
        etNombre = (EditText) v.findViewById(R.id.etNombre);
        etEmail = (EditText) v.findViewById(R.id.etEmail);
        etTelefono = (EditText) v.findViewById(R.id.etTelefono);
        etSitioWeb = (EditText) v.findViewById(R.id.etSitioWeb);
        etAsunto = (EditText) v.findViewById(R.id.etAsunto);
        etMensaje = (EditText) v.findViewById(R.id.etMensaje);
        Button btnEnviar = (Button) v.findViewById(R.id.btnEnviar);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        setHasOptionsMenu(true);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!existeCampoVacio()) {
                    if (sonCamposValidos()) {
                        mostrarDialogo("Enviando, espere...");
                        enviarEmail();
                    }
                }
            }
        });
    }

    private void enviarEmail() {

        final String nombre = etNombre.getText().toString();
        final String email = etEmail.getText().toString();
        final String telefono = etTelefono.getText().toString();
        final String asunto = etAsunto.getText().toString();
        final String mensaje = etMensaje.getText().toString();String ultimaOperacion = "A";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long timestampModificacion = timestamp.getTime();
        long sTimestamp = timestamp.getTime();

        final String CONSULTA = "INSERT INTO contacto " +
                "(nombre,email,telefono,asunto,mensaje,ultima_operacion,timestamp_modificacion,timestamp) " +
                "VALUES ('"+nombre+"','"+email+"','"+telefono+"'," +
                "'"+asunto+"','"+mensaje+"'," +
                "'"+ultimaOperacion+"','"+timestampModificacion+"','"+sTimestamp+"')";
        final String OBJETO = "contacto";

        Log.i(TAG, CONSULTA);

        HashMap<String, String> map = new HashMap<>();
        map.put("consulta", CONSULTA);
        map.put("nombreObjeto", OBJETO);
        map.put("nombre", nombre);
        map.put("email_remitente",email);
        map.put("telefono", telefono);
        map.put("asunto",asunto);
        map.put("mensaje",mensaje);

        JSONObject jsonObject = new JSONObject(map);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                Constantes.SET_SQL_EMAIL_CONTACTO,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject solictud) {
                        ocultarDialogo();
                        Crouton.makeText(getActivity(), "Enviado correctamente.", Style.CONFIRM).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ocultarDialogo();
                        Log.e(TAG,error.toString());
                        Snackbar.make(getView(), Constantes.MENSAJE_SIN_CONEXION, Snackbar.LENGTH_LONG).show();
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
        };
        mostrarDialogo("Enviando, espere...");
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstancia().addToRequestQueue(jsonObjectRequest);
    }

    private boolean sonCamposValidos() {
        boolean sonValidos = true;
        if (!Validacion.esEmailValido(etEmail.getText().toString())){
            sonValidos = false;
            etEmail.setError("El email es incorrecto.");
        }
        return sonValidos;
    }

    private boolean existeCampoVacio() {
        boolean hayCampos = false;
        final String MENSAJE_CAMPO_VACIO = "Este campo es obligatorio.";
        if (etNombre.getText().toString().equals("")){
            hayCampos = true;
            etNombre.setError(MENSAJE_CAMPO_VACIO);
        }
        if (etEmail.getText().toString().equals("")){
            hayCampos = true;
            etEmail.setError(MENSAJE_CAMPO_VACIO);
        }
        if (etAsunto.getText().toString().equals("")){
            hayCampos = true;
            etAsunto.setError(MENSAJE_CAMPO_VACIO);
        }
        if (etMensaje.getText().toString().equals("")){
            hayCampos = true;
            etMensaje.setError(MENSAJE_CAMPO_VACIO);
        }
        return hayCampos;
    }

    public void mostrarDialogo(String mensaje){
        if (pDialog.isShowing()){
            pDialog.setMessage(mensaje);
        }else {
            pDialog.setMessage(mensaje);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();
        }
    }

    public void ocultarDialogo(){
        if (pDialog.isShowing()){
            pDialog.dismiss();
        }
    }
}
