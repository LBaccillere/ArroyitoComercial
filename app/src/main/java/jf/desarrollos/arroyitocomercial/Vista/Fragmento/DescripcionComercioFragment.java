package jf.desarrollos.arroyitocomercial.Vista.Fragmento;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Calendar;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import jf.desarrollos.arroyitocomercial.Controlador.Busqueda;
import jf.desarrollos.arroyitocomercial.Controlador.Operacion;
import jf.desarrollos.arroyitocomercial.Controlador.Volley.AppController;
import jf.desarrollos.arroyitocomercial.Modelo.Comercio;
import jf.desarrollos.arroyitocomercial.Modelo.Direccion;
import jf.desarrollos.arroyitocomercial.Modelo.Email;
import jf.desarrollos.arroyitocomercial.Modelo.Horario;
import jf.desarrollos.arroyitocomercial.Modelo.SitioWeb;
import jf.desarrollos.arroyitocomercial.Modelo.Tabla;
import jf.desarrollos.arroyitocomercial.Modelo.Telefono;
import jf.desarrollos.arroyitocomercial.R;
import jf.desarrollos.arroyitocomercial.Utilidades.Constantes;

public class DescripcionComercioFragment extends Fragment implements OnMapReadyCallback {
    private final String TAG = DescripcionComercioFragment.class.getSimpleName();

    private NetworkImageView nivComercio;
    private TextView tvNombre;
    private TextView tvRubros;
    private TextView tvDescripcion;
    private TextView tvDireccion;
    private ImageView ivDireccion;
    private TextView tvTelefono;
    private ImageView ivTelefono;
    private TextView tvEmail;
    private ImageView ivEmail;
    private TableLayout tblHorario;
    private TextView tvCabeceraDia;
    private TextView tvDia;
    private TextView tvCabeceraHorario;
    private TextView tvHorario;
    private ImageView ivHorario;
    private TextView tvSitioWeb;
    private ImageView ivSitioWeb;
    private ProgressDialog pDialog;
    private ProgressBar pBar;
    private FloatingActionButton fabCall;
    private MyMapsFragment miMapFragment;
    private View fragmento;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.descripcion_comercio_fragment, container, false);
        inicializarComponentes(v);
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppController.getInstancia().cancelPendingRequests();
    }

    private void inicializarComponentes(View v) {
        nivComercio = (NetworkImageView) v.findViewById(R.id.nivComercio);
        tvNombre = (TextView) v.findViewById(R.id.tvNombre);
        tvRubros = (TextView) v.findViewById(R.id.tvRubros);
        tvDescripcion = (TextView) v.findViewById(R.id.tvDescripcion);
        tvDireccion = (TextView) v.findViewById(R.id.tvDireccion);
        ivDireccion = (ImageView) v.findViewById(R.id.ivDireccion);
        tvTelefono = (TextView) v.findViewById(R.id.tvTelefono);
        ivTelefono = (ImageView) v.findViewById(R.id.ivTelefono);
        tvEmail = (TextView) v.findViewById(R.id.tvEmail);
        ivEmail = (ImageView) v.findViewById(R.id.ivEmail);
        ivEmail.setVisibility(View.INVISIBLE);
        tblHorario = (TableLayout) v.findViewById(R.id.tblHorario);
        tvCabeceraDia = (TextView) v.findViewById(R.id.tvCabeceraDia);
        tvDia = (TextView) v.findViewById(R.id.tvDia);
        tvCabeceraHorario = (TextView) v.findViewById(R.id.tvCabeceraHorario);
        tvHorario = (TextView) v.findViewById(R.id.tvHorario);
        ivHorario = (ImageView) v.findViewById(R.id.ivHorario);
        tvSitioWeb = (TextView) v.findViewById(R.id.tvSitioWeb);
        ivSitioWeb = (ImageView) v.findViewById(R.id.ivSitioWeb);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pBar = (ProgressBar) v.findViewById(R.id.pBar);
        pBar.setVisibility(View.GONE);
        fabCall = (FloatingActionButton) v.findViewById(R.id.fabCall);
        fabCall.setVisibility(View.GONE);
        fragmento = v.findViewById(R.id.map);
        miMapFragment = MyMapsFragment.newInstance();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.map, miMapFragment)
                .commit();
        miMapFragment.getMapAsync(this);
        Bundle bundle = getArguments();
        final int idComercio = bundle.getInt("idComercio");
        if (idComercio != 0) {
            mostrarDialogo("Cargando datos del comercio, espere...");
            Busqueda busqueda = new Busqueda();
            cargarDatosComercio(busqueda.getComercio(idComercio));
            ocultarDialogo();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMapAux) {
        googleMap = googleMapAux;
        Bundle bundle = getArguments();
        final int idComercio = bundle.getInt("idComercio");
        if (idComercio != 0) {
            Busqueda busqueda = new Busqueda();
            Comercio comercio = busqueda.getComercio(idComercio);
            if (!comercio.getDirecciones().isEmpty()) {
                crearMarcadores(comercio);
                int posicionDireccion = 0;
                mostrarDireccionEnElMapa(comercio, posicionDireccion);
            } else {
                Log.i(TAG,"esconde mapa 1");
                fragmento.setVisibility(View.GONE);
            }
        } else {
            Log.i(TAG,"esconde mapa 2");
            fragmento.setVisibility(View.GONE);
        }
    }

    private void crearMarcadores(Comercio comercio) {
        try {
            Busqueda busqueda = new Busqueda();
            int cantidadMarcadores = 0;
            for (Direccion direccion :comercio.getDirecciones()) {
                if (!direccion.getLatitud().isEmpty() && !direccion.getLongitud().isEmpty()) {
                    LatLng posicionComercio = new LatLng(
                            Double.parseDouble(direccion.getLatitud()),
                            Double.parseDouble((direccion.getLongitud())));

                    googleMap.addMarker(new MarkerOptions()
                            .position(posicionComercio)
                            .title(comercio.getNombreFantasia())
                            .snippet(comercio.getRubro().getNombre() + " / " + comercio.getSubrubro().getNombre())
                            .draggable(false)
                            .visible(true)
                            .flat(false));

                    cantidadMarcadores ++;
                }
            }
            if (cantidadMarcadores == 0) {
                Log.i(TAG,"esconde mapa 3");
                fragmento.setVisibility(View.GONE);
            }
        }catch (Exception e) {
            Log.i(TAG,"esconde mapa 4");
            fragmento.setVisibility(View.GONE);
        }
    }

    private void mostrarDireccionEnElMapa(Comercio comercio, int posicionDireccion) {
        try {
            if (!comercio.getDirecciones().get(posicionDireccion).getLatitud().isEmpty() && !comercio.getDirecciones().get(posicionDireccion).getLongitud().isEmpty()) {

                Log.i(TAG,"ENTRO IF");
                LatLng posicionComercio = new LatLng(
                        Double.parseDouble(comercio.getDirecciones().get(posicionDireccion).getLatitud()),
                        Double.parseDouble(comercio.getDirecciones().get(posicionDireccion).getLongitud()));

                Log.i(TAG,"posicionComercio "+posicionComercio);
                CameraPosition cameraPosition = CameraPosition.builder()
                        .target(posicionComercio)
                        .zoom(16)
                        .build();

                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            } else {
                Log.i(TAG,"esconde mapa 5");
                fragmento.setVisibility(View.GONE);
            }
        }catch (Exception e) {
            Log.i(TAG,"error esconde "+e.getMessage());
            Log.i(TAG,"esconde mapa 6");
            //fragmento.setVisibility(View.GONE);
        }
    }

    private void cargarDatosComercio(final Comercio comercio) {

        Log.i(TAG,"comercio = "+comercio);
        if (comercio != null) {
            if (!comercio.getFotos().isEmpty()) {
                Log.i(TAG,"fotos != vacio");
                int ultimoNombreArchivo = comercio.getFotos().size() - 1;
                final String URL = Constantes.GET_SQL_IMAGEN + "nombre-archivo=" + comercio.getFotos().get(ultimoNombreArchivo).getNombreArchivo() + "&carpeta=comercio_frente";
                nivComercio.setImageUrl(URL, cargarImagen(URL));
            } else {
                Log.i(TAG,"fotos == vacio");
                final String URL = Constantes.GET_SQL_IMAGEN + "nombre-archivo=logo&carpeta=body";
                nivComercio.setImageUrl(URL, cargarImagen(URL));
            }
        }

        tvNombre.setText(comercio.getNombreFantasia());

        StringBuilder rubros = new StringBuilder();
        if (comercio.getRubro() != null) {
            rubros.append(comercio.getRubro().getNombre());
            if (comercio.getSubrubro() != null) {
                rubros.append(" / ");
            }
        }

        if (comercio.getSubrubro() != null) {
            rubros.append(comercio.getSubrubro().getNombre());
        }

        if (!rubros.toString().isEmpty()){
            tvRubros.setVisibility(View.VISIBLE);
            tvRubros.setText(rubros.toString());
        } else {
            tvRubros.setVisibility(View.GONE);
        }


        if (!comercio.getDescripcion().isEmpty() || !comercio.getDescripcion().equals("")) {
            tvDescripcion.setText(comercio.getDescripcion());
        } else {
            tvDescripcion.setVisibility(View.GONE);
        }

        if (!comercio.getDirecciones().isEmpty()) {
            inicializarComponenteDireccion(comercio);
            inicializarComponenteMapa();

            StringBuilder direcciones = new StringBuilder();
            int i = 1;
            for (Direccion direccion : comercio.getDirecciones()) {
                direcciones.append(direccion.getNombre());
                direcciones.append(" ");
                direcciones.append(direccion.getAltura());
                if (i < comercio.getDirecciones().size()) {
                    direcciones.append("\n");
                    direcciones.append("\n");
                }
                i++;
            }
            tvDireccion.setText(direcciones);
        } else {
            tvDireccion.setVisibility(View.GONE);
            ivDireccion.setVisibility(View.GONE);
        }

        if (!comercio.getTelefonos().isEmpty()) {
            inicializarComponentesLLamada(comercio);
            StringBuilder telefonos = new StringBuilder();
            int i = 1;
            for (Telefono telefono : comercio.getTelefonos()) {
                telefonos.append(telefono.getNumero());
                if (i < comercio.getTelefonos().size()) {
                    telefonos.append("\n");
                    telefonos.append("\n");
                }
                i++;
            }
            tvTelefono.setText(telefonos);
        } else {
            tvTelefono.setVisibility(View.GONE);
            ivTelefono.setVisibility(View.GONE);
        }

        if (!comercio.getEmails().isEmpty()) {

            ivEmail.setVisibility(View.VISIBLE);
            StringBuilder emails = new StringBuilder();
            int i = 1;
            for (Email email : comercio.getEmails()) {
                emails.append(email.getDireccion());
                if (i < comercio.getEmails().size()) {
                    emails.append("\n");
                    emails.append("\n");
                }
                i++;
            }
            tvEmail.setText(emails);
        } else {
            ivEmail.setVisibility(View.GONE);
            Log.i(TAG,"ESCONDE IMAGEN EMAIL");
            tvEmail.setVisibility(View.GONE);
        }

        if (!comercio.getHorarios().isEmpty()) {

            StringBuilder dia = new StringBuilder();
            StringBuilder horario = new StringBuilder();

            int cantEspacio;
            int cantA;
            int cantY;
            int i = 0;

            for (Horario horarioComercio : comercio.getHorarios()) {

                if (!horarioComercio.getAperturaMañana().toString().equals("00:00:00") &&
                        !horarioComercio.getCierreMañana().toString().equals("00:00:00") &&
                        !horarioComercio.getAperturaTarde().toString().equals("00:00:00") &&
                        !horarioComercio.getCierreTarde().toString().equals("00:00:00")) {

                    cantEspacio = 0;
                    cantA = 0;
                    cantY = 0;
                    String espacio = " ";
                    String letraA = "a";
                    String letraY = "y";

                    dia.append(horarioComercio.getDia());
                    dia.append("\n");


                    if (!horarioComercio.getAperturaMañana().equals("")) {
                        horario.append(Operacion.formatearHora(horarioComercio.getAperturaMañana().toString()));
                        horario.append(espacio);
                        cantEspacio++;
                    }

                    if (!horarioComercio.getCierreMañana().equals("")) {
                        if (cantEspacio == 1){
                            horario.append(letraA);
                            cantA++;
                            horario.append(espacio);
                            cantEspacio++;
                        }
                        horario.append(Operacion.formatearHora(horarioComercio.getCierreMañana().toString()));
                        horario.append(espacio);
                        cantEspacio++;
                    }

                    if (!horarioComercio.getAperturaTarde().equals("")) {
                        if (cantEspacio == 3) {
                            horario.append(letraY);
                            cantY++;
                            horario.append(espacio);
                            cantEspacio++;
                        }
                        horario.append(Operacion.formatearHora(horarioComercio.getAperturaTarde().toString()));
                        horario.append(espacio);
                        cantEspacio++;
                    }

                    if (!horarioComercio.getCierreTarde().equals("")) {
                        if (cantEspacio == 5 || cantEspacio == 1 || cantEspacio == 2) {
                            horario.append(letraA);
                            cantA++;
                            horario.append(espacio);
                            cantEspacio++;
                        }
                        horario.append(Operacion.formatearHora(horarioComercio.getCierreTarde().toString()));
                        cantEspacio++;
                    }

                    horario.append("\n");
                }
                i++;
            }
            tvDia.setText(dia.toString());
            tvHorario.setText(horario.toString());
        } else {
            tblHorario.setVisibility(View.GONE);
            ivHorario.setVisibility(View.GONE);
        }

        if (!comercio.getSitiosWebs().isEmpty()) {
            inicializarComponenteSitioWeb(comercio);
            StringBuilder sitiosWebs = new StringBuilder();
            int i = 1;
            for (SitioWeb sitioWeb : comercio.getSitiosWebs()) {
                sitiosWebs.append(sitioWeb.getUrl());
                if (i < comercio.getSitiosWebs().size()) {
                    sitiosWebs.append("\n");
                    sitiosWebs.append("\n");
                }
                i++;
            }
            tvSitioWeb.setText(sitiosWebs);
        } else {
            tvSitioWeb.setVisibility(View.GONE);
            ivSitioWeb.setVisibility(View.GONE);
        }
    }

    private void inicializarComponenteSitioWeb(final Comercio comercio) {
        tvSitioWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarOpcionesSitioWeb(comercio);
            }
        });
    }

    private void iniciarSitioWeb(Comercio comercio, int posicionSitioWeb) {
        try{
            Uri uriUrl = Uri.parse(comercio.getSitiosWebs().get(posicionSitioWeb).getUrl());
            Intent intent = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(intent);
        } catch (Exception e) {
            Snackbar.make(getView(), Constantes.MENSAJE_ERROR_SITIO_WEB, Snackbar.LENGTH_LONG).show();
        }

    }

    private void inicializarComponenteDireccion(final Comercio comercio) {
        tvDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarOpcionesDireccion(comercio);
            }
        });
    }

    private void inicializarComponenteMapa() {

        Bundle bundle = getArguments();
        final int idComercio = bundle.getInt("idComercio");
        if (idComercio != 0) {
            Busqueda busqueda = new Busqueda();
            Comercio comercio = busqueda.getComercio(idComercio);
            if (!comercio.getDirecciones().isEmpty()) {
                int posicionDireccion = 0;
                if (comercio.getDirecciones().get(posicionDireccion).getLatitud() != "" && comercio.getDirecciones().get(posicionDireccion).getLongitud() != "") {
                    mostrarDireccionEnElMapa(comercio, posicionDireccion);
                } else {
                    Log.i(TAG,"esconde mapa 7");
                    fragmento.setVisibility(View.GONE);
                }
            } else {
                Log.i(TAG,"esconde mapa 8");
                fragmento.setVisibility(View.GONE);
            }
        } else {
            Log.i(TAG,"esconde mapa 9");
            fragmento.setVisibility(View.GONE);
        }
    }

    public void mostrarOpcionesDireccion(final Comercio comercio) {
        final String MENSAJE_OPCION_FOTO = "Selecciona una Dirección";

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        final AlertDialog alerta = alertDialog.create();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View convertView = inflater.inflate(R.layout.opciones, null);
        alerta.setView(convertView);
        alerta.setTitle(MENSAJE_OPCION_FOTO);
        ListView lv = (ListView) convertView.findViewById(R.id.lvOpciones);
        String[] direcciones = new String[comercio.getDirecciones().size()];
        int i = 0;
        if (!comercio.getDirecciones().isEmpty()){
            for (Direccion direccion : comercio.getDirecciones()) {
                direcciones[i] = direccion.getNombre();
                i++;
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,direcciones);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                alerta.dismiss();
                if (comercio.getDirecciones().get(position).getLatitud() != "" && comercio.getDirecciones().get(position).getLongitud() != "") {
                    mostrarDireccionEnElMapa(comercio, position);
                } else {
                    Log.i(TAG,"esconde mapa 10");
                    fragmento.setVisibility(View.GONE);
                }
            }
        });
        alerta.show();
    }

    public void mostrarOpcionesSitioWeb(final Comercio comercio) {
        final String MENSAJE_OPCION_FOTO = "Selecciona un Sitio Web";

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        final AlertDialog alerta = alertDialog.create();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View convertView = inflater.inflate(R.layout.opciones, null);
        alerta.setView(convertView);
        alerta.setTitle(MENSAJE_OPCION_FOTO);
        ListView lv = (ListView) convertView.findViewById(R.id.lvOpciones);
        String[] sitiosWebs = new String[comercio.getSitiosWebs().size()];
        int i = 0;
        if (!comercio.getSitiosWebs().isEmpty()){
            for (SitioWeb sitioWeb : comercio.getSitiosWebs()) {
                sitiosWebs[i] = sitioWeb.getUrl();
                i++;
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,sitiosWebs);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                alerta.dismiss();
                iniciarSitioWeb(comercio, position);
            }
        });
        alerta.show();
    }

    private void inicializarComponentesLLamada(final Comercio comercio) {
        fabCall.setVisibility(View.VISIBLE);
        fabCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (comercio.getTelefonos().size() == 1) {
                    ArrayList<Telefono> telefono = comercio.getTelefonos();
                    llamarComercio(telefono.get(0).getNumero());
                } else {
                    mostrarOpcionesLlamada(comercio);
                }
            }
        });

        tvTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comercio.getTelefonos().size() == 1) {
                    ArrayList<Telefono> telefono = comercio.getTelefonos();
                    llamarComercio(telefono.get(0).getNumero());
                } else {
                    mostrarOpcionesLlamada(comercio);
                }
            }
        });
    }

    public void mostrarOpcionesLlamada(final Comercio comercio) {
        final String MENSAJE_OPCION_FOTO = "Selecciona una Teléfono";

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        final AlertDialog alerta = alertDialog.create();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View convertView = inflater.inflate(R.layout.opciones, null);
        alerta.setView(convertView);
        alerta.setTitle(MENSAJE_OPCION_FOTO);
        ListView lv = (ListView) convertView.findViewById(R.id.lvOpciones);
        String[] telefonos = new String[comercio.getTelefonos().size()];
        int i = 0;
        if (!comercio.getTelefonos().isEmpty()){
            for (Telefono telefono : comercio.getTelefonos()) {
                telefonos[i] = telefono.getNumero();
                i++;
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,telefonos);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                alerta.dismiss();
                Busqueda busqueda = new Busqueda();
                llamarComercio(comercio.getTelefonos().get(position).getNumero());
            }
        });
        alerta.show();
    }

    public void llamarComercio(String telefono) {
        final String MENSAJE_FALLO_LLAMADA = "Fallo la llamada.";
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + telefono));
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            getActivity().startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {
            Crouton.makeText(getActivity(), MENSAJE_FALLO_LLAMADA, Style.ALERT).show();
        }
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

    private ImageLoader cargarImagen(String URL) {

        ImageLoader imageLoader = AppController.getInstancia().getCargaImagen();

        imageLoader.get(URL, new ImageLoader.ImageListener() {

            public void onErrorResponse(VolleyError arg0) {
                pBar.setVisibility(View.GONE);
                nivComercio.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                nivComercio.setImageResource(R.drawable.icono_inicio);
                // set an error image if the download fails
            }

            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    pBar.setVisibility(View.GONE);
                    nivComercio.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    nivComercio.setImageBitmap(response.getBitmap());
                } else {
                    pBar.setVisibility(View.VISIBLE);
                    // set the loading image while the download is in progress
                }
            }
        });

        return imageLoader;
    }
}
