package jf.desarrollos.arroyitocomercial.Vista.Adaptador;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import jf.desarrollos.arroyitocomercial.Vista.Fragmento.DescripcionComercioFragment;
import jf.desarrollos.arroyitocomercial.Vista.Fragmento.EventosDeComercioFragment;
import jf.desarrollos.arroyitocomercial.Vista.Fragmento.OfertasDeComercioFragment;

public class MiComercioFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] =
            new String[] {"Información", "Ofertas", "Eventos"};
    private int idComercio;

    public MiComercioFragmentPagerAdapter(FragmentManager fm, int idComercio) {
        super(fm);
        this.idComercio = idComercio;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle args = new Bundle();
        args.putInt("idComercio", idComercio);

        Fragment descripcionComercioFragment = new DescripcionComercioFragment();
        descripcionComercioFragment.setArguments(args);

        Fragment ofertasDeComercioFragment = new OfertasDeComercioFragment();
        ofertasDeComercioFragment.setArguments(args);

        Fragment eventosDeComercioFragment = new EventosDeComercioFragment();
        eventosDeComercioFragment.setArguments(args);

        Fragment f = null;

        switch(position) {
            case 0:
                f = descripcionComercioFragment;
                break;
            case 1:
                f = ofertasDeComercioFragment;
                break;
            case 2:
                f = eventosDeComercioFragment;
                break;
        }
        return f;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
