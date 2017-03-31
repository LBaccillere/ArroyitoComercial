package jf.desarrollos.arroyitocomercial.Vista.Adaptador.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import jf.desarrollos.arroyitocomercial.Modelo.Rubro;
import jf.desarrollos.arroyitocomercial.R;
import jf.desarrollos.arroyitocomercial.Utilidades.RoundedLetterView;

public class RubroViewHolder extends RecyclerView.ViewHolder {

    private TextView mFileNameTextView;
    public RoundedLetterView mRoundedLetterView;

    public RubroViewHolder(final View itemView) {
        super(itemView);
        mRoundedLetterView = (RoundedLetterView) itemView.findViewById(R.id.rlv_name_view);
        mFileNameTextView = (TextView) itemView.findViewById(R.id.tv_name_holder);
    }

    public void bind(Context contexto, Rubro model, int position) {
        if (model.getNombre().length() == 0) {
            mRoundedLetterView.setTitleText("A");
        } else {
            mRoundedLetterView.setTitleText(model.getNombre().substring(0, 1).toUpperCase());
        }
        if (position % 2 == 0) {
            mRoundedLetterView.setBackgroundColor(contexto.getResources().getColor(R.color.recycler_view_selected_2));
        } else {
            mRoundedLetterView.setBackgroundColor(contexto.getResources().getColor(R.color.recycler_view_selected_1));
        }
        mFileNameTextView.setText(model.getNombre());
    }
}
