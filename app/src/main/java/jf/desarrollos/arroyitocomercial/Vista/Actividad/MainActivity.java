package jf.desarrollos.arroyitocomercial.Vista.Actividad;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.parse.ParseAnalytics;

import java.util.ArrayList;
import java.util.List;

import jf.desarrollos.arroyitocomercial.Modelo.ItemMenu;
import jf.desarrollos.arroyitocomercial.R;
import jf.desarrollos.arroyitocomercial.Vista.Adaptador.ItemMenuAdapter;
import jf.desarrollos.arroyitocomercial.Vista.Fragmento.ContactoFragment;
import jf.desarrollos.arroyitocomercial.Vista.Fragmento.EventosFragment;
import jf.desarrollos.arroyitocomercial.Vista.Fragmento.InicioFragment;
import jf.desarrollos.arroyitocomercial.Vista.Fragmento.NotificacionesFragment;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    private Toolbar appbar;
    private DrawerLayout drawerLayout;
    private RelativeLayout drawerPane;
    private ListView lvNav;

    private List<ItemMenu> listNavItems;
    private List<Fragment> listFragments;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        appbar = (Toolbar)findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.titulo_main_activity));

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerPane = (RelativeLayout) findViewById(R.id.drawer_pane);
        lvNav = (ListView) findViewById(R.id.nav_list);

        listNavItems = new ArrayList<>();
        listNavItems.add(new ItemMenu("Inicio",
                R.mipmap.ic_home));
        listNavItems.add(new ItemMenu("Eventos",
                R.mipmap.ic_event));
        listNavItems.add(new ItemMenu("Notificaciones",
                R.mipmap.ic_anunciarme));
        listNavItems.add(new ItemMenu("Contacto",
                R.mipmap.ic_contact));


        ItemMenuAdapter navListAdapter = new ItemMenuAdapter(
                getApplicationContext(), R.layout.item_menu, listNavItems);

        lvNav.setAdapter(navListAdapter);

        listFragments = new ArrayList<>();
        listFragments.add(new InicioFragment());
        listFragments.add(new EventosFragment());
        listFragments.add(new NotificacionesFragment());
        listFragments.add(new ContactoFragment());

        inicializarFragmento();
        lvNav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // replace the fragment with the selection correspondingly:
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.content_frame, listFragments.get(position))
                        .commit();

                lvNav.setItemChecked(position, true);
                drawerLayout.closeDrawer(drawerPane);
            }
        });

        // create listener for drawer layout
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, appbar,
                R.string.drawer_opened, R.string.drawer_closed) {

            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                invalidateOptionsMenu();
                super.onDrawerClosed(drawerView);
            }

        };
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    private void inicializarFragmento() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new InicioFragment()).commit();
        lvNav.setItemChecked(0, true);
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item) || item.getItemId() == android.R.id.home) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);

        // Track app opens.
        // Track app opens.
        ParseAnalytics.trackAppOpened(getIntent());
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        if (extras != null) {
            System.out.println("MESSAGGE " + extras.get("com.parse.Data"));
            System.out.println("Chanell " + extras.get("com.parse.Channel"));
            System.out.println("DATA " + extras.keySet());
            for (String k : extras.keySet()) {
                System.out.println("K " + k);

            }
        }
    }
}