package jf.desarrollos.arroyitocomercial.Vista.Fragmento;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jf.desarrollos.arroyitocomercial.R;

public class RootFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.root_fragment, container, false);
        inicializarComponentes(v);
        return v;
    }

    private void inicializarComponentes(View v) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.root_frame, new RubrosFragment());
        transaction.commit();
    }
}
