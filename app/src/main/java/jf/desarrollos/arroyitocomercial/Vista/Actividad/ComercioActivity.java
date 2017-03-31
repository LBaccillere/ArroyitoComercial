package jf.desarrollos.arroyitocomercial.Vista.Actividad;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import jf.desarrollos.arroyitocomercial.R;
import jf.desarrollos.arroyitocomercial.Vista.Fragmento.InicioComercioFragment;

public class ComercioActivity extends ActionBarActivity {

    private Toolbar appbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comercio_activity);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        appbar = (Toolbar)findViewById(R.id.appbar);
        setSupportActionBar(appbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.titulo_comercio_activity));
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle bundle = getIntent().getExtras();
        int idComercio = bundle.getInt("idComercio");
        inicializarFragmento(idComercio);
    }

    private void inicializarFragmento(int idComercio) {
        Fragment inicioComercioFragment = new InicioComercioFragment();

        Bundle args = new Bundle();
        args.putInt("idComercio", idComercio);
        inicioComercioFragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, inicioComercioFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                    finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
