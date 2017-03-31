package jf.desarrollos.arroyitocomercial.Vista.Adaptador;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import jf.desarrollos.arroyitocomercial.Vista.Fragmento.ComerciosFragment;
import jf.desarrollos.arroyitocomercial.Vista.Fragmento.EventosFragment;
import jf.desarrollos.arroyitocomercial.Vista.Fragmento.OfertasFragment;
import jf.desarrollos.arroyitocomercial.Vista.Fragmento.RootFragment;

public class MiFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] =
            new String[] {"Ofertas", "Rubros", "Comercios"};

    public MiFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment f = null;

        switch(position) {
            case 0:
                f = new OfertasFragment();
                break;
            case 1:
                f = new RootFragment();
                break;
            case 2:
                f = new ComerciosFragment();
                break;
        }
        return f;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
