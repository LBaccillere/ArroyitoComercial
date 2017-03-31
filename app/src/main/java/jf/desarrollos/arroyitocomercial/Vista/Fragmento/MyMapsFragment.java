package jf.desarrollos.arroyitocomercial.Vista.Fragmento;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;

public class MyMapsFragment extends SupportMapFragment {

    public MyMapsFragment() {
    }

    public static MyMapsFragment newInstance() {
        return new MyMapsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);

        return root;
    }

}
