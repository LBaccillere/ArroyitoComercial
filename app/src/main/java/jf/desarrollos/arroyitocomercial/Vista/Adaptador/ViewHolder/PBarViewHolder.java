package jf.desarrollos.arroyitocomercial.Vista.Adaptador.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import jf.desarrollos.arroyitocomercial.R;

public class PBarViewHolder extends RecyclerView.ViewHolder {

    private ProgressBar pBar;
    private RelativeLayout layoutPBar;
    private Context contexto;

    public PBarViewHolder(View itemView) {
        super(itemView);

        pBar = (ProgressBar) itemView.findViewById(R.id.pBar);
    }

    public void bind(final Context contexto) {

        this.contexto = contexto;
    }
}
