package jf.desarrollos.arroyitocomercial.Vista.Fragmento;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jf.desarrollos.arroyitocomercial.R;
import jf.desarrollos.arroyitocomercial.Vista.Adaptador.MiComercioFragmentPagerAdapter;

public class InicioComercioFragment extends Fragment {

    public InicioComercioFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inicio_de_comercio_fragment, container, false);
        inicializarComponentes(view);
        return view;
    }

    private void inicializarComponentes(View v) {
        Bundle bundle = getArguments();
        int idComercio = bundle.getInt("idComercio");

        ViewPager viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new MiComercioFragmentPagerAdapter(
                getChildFragmentManager(), idComercio));

        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.appbartabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);;
    }
}