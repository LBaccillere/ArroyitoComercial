package jf.desarrollos.arroyitocomercial.Vista.Fragmento;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import jf.desarrollos.arroyitocomercial.Controlador.Busqueda;
import jf.desarrollos.arroyitocomercial.Controlador.Volley.AppController;
import jf.desarrollos.arroyitocomercial.Modelo.Publicidad;
import jf.desarrollos.arroyitocomercial.R;
import jf.desarrollos.arroyitocomercial.Utilidades.Constantes;
import jf.desarrollos.arroyitocomercial.Vista.Actividad.ComercioActivity;
import jf.desarrollos.arroyitocomercial.Vista.Adaptador.MiFragmentPagerAdapter;

public class InicioFragment extends Fragment {

    private List<Publicidad> publicidades = new ArrayList<>();
    private NetworkImageView nivPublicidad;
    private Timer myTimer;
    private int randomPublicidad;
    private ProgressBar pBar;
    private String url = "";

    public InicioFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inicio_fragment, container, false);
        inicializarComponentes(view);
        return view;
    }

    private void inicializarComponentes(View v) {

        nivPublicidad = (NetworkImageView) v.findViewById(R.id.nivPublicidad);
        myTimer = new Timer();
        pBar = (ProgressBar) v.findViewById(R.id.pBar);
        pBar.setVisibility(View.GONE);

        ViewPager viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new MiFragmentPagerAdapter(getChildFragmentManager()));

        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.appbartabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);

        nivPublicidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (publicidades.size() > 0){
                    if (!publicidades.get(randomPublicidad).getUrl().equals("")){
                        inicializarActividadSitioWeb();

                    } else if (publicidades.get(randomPublicidad).getComercio() != null){
                        inicializarActividadComercio(publicidades.get(randomPublicidad).getComercio().getId());
                    }
                }
            }
        });

        cargarPublicidades();
    }

    private void inicializarActividadComercio(int idComercio) {
        Intent comercioActivity = new Intent(getActivity(), ComercioActivity.class);
        comercioActivity.putExtra("idComercio", idComercio);
        getActivity().startActivity(comercioActivity);
    }

    private void inicializarActividadSitioWeb() {
        Uri uriUrl = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(intent);
    }

    private void cargarPublicidades() {

        JsonArrayRequest req = new JsonArrayRequest(Constantes.GET_BANNERS, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray solicitud) {
                try {
                    if(solicitud.length() > 0){
                        publicidades.clear();
                        for (int i = 0; i < solicitud.length(); i++) {

                            JSONObject publicidadJSON = (JSONObject) solicitud.get(i);

                            int idPublicidad = publicidadJSON.getInt("id");
                            String foto = publicidadJSON.getString("foto");
                            String url = publicidadJSON.getString("url");
                            String fechaInicio = publicidadJSON.getString("fecha_inicio");
                            String fechaVencimiento = publicidadJSON.getString("fecha_fin");
                            int prioridad = publicidadJSON.getInt("prioridad");
                            int idComercio = publicidadJSON.getInt("id_comercio");

                            Busqueda busqueda = new Busqueda();
                            publicidades.add(new Publicidad(idPublicidad, foto, fechaInicio, fechaVencimiento, prioridad,
                                    busqueda.getComercio(idComercio), url));
                        }
                    }
                    mostrarPublicidades();
                } catch (JSONException e) {
                    nivPublicidad.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    nivPublicidad.setImageDrawable(getResources().getDrawable(R.drawable.icono_inicio));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                nivPublicidad.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                nivPublicidad.setImageDrawable(getResources().getDrawable(R.drawable.icono_inicio));
            }
        });
        AppController.getInstancia().addToRequestQueue(req);
    }

    private void mostrarPublicidades() {
        int minimo = 0;
        int maximo;
        if (publicidades.size() > 1){
            maximo = publicidades.size()-1;
            randomPublicidad = minimo + (int)(Math.random()*maximo);
            myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, 8000);
        } else if (publicidades.size() == 1){
            final String URL = Constantes.GET_SQL_IMAGEN +
                    "nombre-archivo=" + publicidades.get(0).getFoto() +
                    "&carpeta=publicidad";
            url = publicidades.get(0).getUrl();
            nivPublicidad.setImageUrl(URL, cargarImagen(URL));
        } else {
            nivPublicidad.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            nivPublicidad.setImageDrawable(getResources().getDrawable(R.drawable.icono_inicio));
        }
    }

    private void TimerMethod()
    {
        getActivity().runOnUiThread(Timer_Tick);
    }

    public Runnable Timer_Tick = new Runnable() {
        public void run() {
            final String URL = Constantes.GET_SQL_IMAGEN +
                    "nombre-archivo=" + publicidades.get(randomPublicidad).getFoto() +
                    "&carpeta=publicidad";
            nivPublicidad.setImageUrl(URL, cargarImagen(URL));
            randomPublicidad++;
            if (randomPublicidad > publicidades.size()-1){
                randomPublicidad = 0;
            }
            url = publicidades.get(randomPublicidad).getUrl();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        myTimer.cancel();
    }

    private ImageLoader cargarImagen(String URL) {

        ImageLoader imageLoader = AppController.getInstancia().getCargaImagen();

        imageLoader.get(URL, new ImageLoader.ImageListener() {

            public void onErrorResponse(VolleyError arg0) {
                pBar.setVisibility(View.VISIBLE);
                // set an error image if the download fails
            }

            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    pBar.setVisibility(View.GONE);
                    nivPublicidad.setImageBitmap(response.getBitmap());
                } else {
                    pBar.setVisibility(View.VISIBLE);
                    // set the loading image while the download is in progress
                }
            }
        });

        return imageLoader;
    }
}
