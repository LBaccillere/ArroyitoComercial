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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import jf.desarrollos.arroyitocomercial.Controlador.Validacion;
import jf.desarrollos.arroyitocomercial.Controlador.Volley.AppController;
import jf.desarrollos.arroyitocomercial.R;
import jf.desarrollos.arroyitocomercial.Utilidades.Constantes;

public class PublicitarmeFragment extends Fragment {
    private final String TAG = PublicitarmeFragment.class.getSimpleName();

    private EditText etNombreComercio;
    private EditText etRubro;
    private EditText etDireccion;
    private EditText etTelefono;
    private EditText etDescripcion;
    private EditText etEmail;
    private EditText etHorario;
    private EditText etSitioWeb;
    private ProgressDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.publicitarme_fragment, container, false);
        inicializarComponentes(v);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    private void inicializarComponentes(View v) {
        etNombreComercio = (EditText) v.findViewById(R.id.etNombreComercio);
        etRubro = (EditText) v.findViewById(R.id.etRubro);
        etEmail = (EditText) v.findViewById(R.id.etEmail);
        etTelefono = (EditText) v.findViewById(R.id.etTelefono);
        etDireccion = (EditText) v.findViewById(R.id.etDireccion);
        etSitioWeb = (EditText) v.findViewById(R.id.etSitioWeb);
        etDescripcion = (EditText) v.findViewById(R.id.etDescripcion);
        etHorario = (EditText) v.findViewById(R.id.etHorario);
        Button btnBorrar = (Button) v.findViewById(R.id.btnBorrar);
        Button btnEnviar = (Button) v.findViewById(R.id.btnEnviar);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        setHasOptionsMenu(true);
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarCampos();
            }
        });

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

        final String nombreComercio = etNombreComercio.getText().toString();
        final String rubro = etRubro.getText().toString();
        final String direccion = etDireccion.getText().toString();
        final String telefono = etTelefono.getText().toString();
        final String descripcion = etDescripcion.getText().toString();
        final String email = etEmail.getText().toString();
        final String horario = etHorario.getText().toString();
        final String sitioWeb = etSitioWeb.getText().toString();

        final String CONSULTA = "INSERT INTO nuevo_comercio " +
                "(nombre,rubro,direccion,telefono,descripcion,email,horario,sitio_web) " +
                "VALUES ('"+nombreComercio+"','"+rubro+"','"+direccion+"','"+telefono+"','"+descripcion+"','" +
                email+"','"+horario+"','"+sitioWeb+"')";
        final String OBJETO = "nuevo_comercio";

        Log.i(TAG, "CONSULTA : " + CONSULTA);

        HashMap<String, String> map = new HashMap<>();
        map.put("consulta", CONSULTA);
        map.put("nombreObjeto", OBJETO);
        map.put("nombre", nombreComercio);
        map.put("rubro",rubro);
        map.put("direccion", direccion);
        map.put("telefono",telefono);
        map.put("descripcion",descripcion);
        map.put("email_remitente",email);
        map.put("horario",horario);
        map.put("sitio_web",sitioWeb);

        Log.i(TAG,"URL : "+Constantes.SET_SQL_EMAIL_NUEVO_COMERCIO);

        JSONObject jsonObject = new JSONObject(map);
        mostrarDialogo("Enviando, espere...");
        AppController.getInstancia().addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Constantes.SET_SQL_EMAIL_NUEVO_COMERCIO,
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
                                Log.e(TAG,error.getMessage());
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
                }
        );
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
        if (etNombreComercio.getText().toString().equals("")){
            hayCampos = true;
            etNombreComercio.setError(MENSAJE_CAMPO_VACIO);
        }
        if (etRubro.getText().toString().equals("")){
            hayCampos = true;
            etRubro.setError(MENSAJE_CAMPO_VACIO);
        }
        if (etDireccion.getText().toString().equals("")){
            hayCampos = true;
            etDireccion.setError(MENSAJE_CAMPO_VACIO);
        }
        if (etTelefono.getText().toString().equals("")){
            hayCampos = true;
            etTelefono.setError(MENSAJE_CAMPO_VACIO);
        }
        return hayCampos;
    }

    private void limpiarCampos() {
        etNombreComercio.setText("");
        etRubro.setText("");
        etDireccion.setText("");
        etTelefono.setText("");
        etDescripcion.setText("");
        etEmail.setText("");
        etSitioWeb.setText("");
        etNombreComercio.setFocusable(true);
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
