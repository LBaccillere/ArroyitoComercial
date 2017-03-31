package jf.desarrollos.arroyitocomercial.Vista.Adaptador;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jf.desarrollos.arroyitocomercial.Modelo.Comercio;
import jf.desarrollos.arroyitocomercial.Modelo.Evento;
import jf.desarrollos.arroyitocomercial.Modelo.Notificacion;
import jf.desarrollos.arroyitocomercial.Modelo.Oferta;
import jf.desarrollos.arroyitocomercial.Modelo.Rubro;
import jf.desarrollos.arroyitocomercial.Modelo.Subrubro;
import jf.desarrollos.arroyitocomercial.R;
import jf.desarrollos.arroyitocomercial.Vista.Adaptador.ViewHolder.ComercioViewHolder;
import jf.desarrollos.arroyitocomercial.Vista.Adaptador.ViewHolder.EventoViewHolder;
import jf.desarrollos.arroyitocomercial.Vista.Adaptador.ViewHolder.NotificacionViewHolder;
import jf.desarrollos.arroyitocomercial.Vista.Adaptador.ViewHolder.OfertaViewHolder;
import jf.desarrollos.arroyitocomercial.Vista.Adaptador.ViewHolder.PBarViewHolder;
import jf.desarrollos.arroyitocomercial.Vista.Adaptador.ViewHolder.RubroViewHolder;
import jf.desarrollos.arroyitocomercial.Vista.Adaptador.ViewHolder.SubrubroViewHolder;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener{

    private final LayoutInflater mInflater;
    private final List<Object> mModels;
    private View.OnClickListener listener;
    private Context contexto;

    private final int RUBRO = 0, SUBRUBRO = 1, COMERCIO = 2, OFERTA = 3, EVENTO = 4, NOTIFICACION = 5, PIE = 6;

    public ItemAdapter(Context contexto, List<Object> models) {
        if (contexto != null) {
            mInflater = LayoutInflater.from(contexto);
            this.contexto = contexto;
        } else {
            mInflater = null;
        }
        mModels = new ArrayList<>(models);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        View itemView;

        switch (viewType) {
            case RUBRO:
                itemView = mInflater.inflate(R.layout.item_rubro, parent, false);
                viewHolder = new RubroViewHolder(itemView);
                break;
            case SUBRUBRO:
                itemView = mInflater.inflate(R.layout.item_subrubro, parent, false);
                viewHolder = new SubrubroViewHolder(itemView);
                break;
            case COMERCIO:
                itemView = mInflater.inflate(R.layout.item_comercio, parent, false);
                viewHolder = new ComercioViewHolder(itemView);
                break;
            case OFERTA:
                itemView = mInflater.inflate(R.layout.item_oferta, parent, false);
                itemView.setOnClickListener(this);
                viewHolder = new OfertaViewHolder(itemView);
                break;
            case EVENTO:
                itemView = mInflater.inflate(R.layout.item_evento, parent, false);
                viewHolder = new EventoViewHolder(itemView);
                break;
            case NOTIFICACION:
                itemView = mInflater.inflate(R.layout.item_notificacion, parent, false);
                viewHolder = new NotificacionViewHolder(itemView);
                break;
            case PIE:
                itemView = mInflater.inflate(R.layout.item_pbar, parent, false);
                itemView.setOnClickListener(this);
                viewHolder = new PBarViewHolder(itemView);
                break;
            default:
                itemView = mInflater.inflate(R.layout.item_pbar, parent, false);
                itemView.setOnClickListener(this);
                viewHolder = new PBarViewHolder(itemView);
                break;
        }
        itemView.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case RUBRO:
                RubroViewHolder rubroViewHolder = (RubroViewHolder) holder;
                Rubro rubro = (Rubro) mModels.get(position);
                rubroViewHolder.bind(contexto, rubro, position);
                break;
            case SUBRUBRO:
                SubrubroViewHolder subrubroViewHolder = (SubrubroViewHolder) holder;
                Subrubro subrubro = (Subrubro) mModels.get(position);
                subrubroViewHolder.bind(contexto, subrubro, position);
                break;
            case COMERCIO:
                ComercioViewHolder comercioViewHolder = (ComercioViewHolder) holder;
                Comercio comercio = (Comercio) mModels.get(position);
                comercioViewHolder.bind(contexto, comercio, position);
                break;
            case OFERTA:
                OfertaViewHolder ofertaViewHolder = (OfertaViewHolder) holder;
                Oferta oferta = (Oferta) mModels.get(position);
                ofertaViewHolder.bind(contexto, oferta, position);
                break;
            case EVENTO:
                EventoViewHolder eventoViewHolder = (EventoViewHolder) holder;
                Evento evento = (Evento) mModels.get(position);
                eventoViewHolder.bind(contexto, evento, position);
                break;
            case NOTIFICACION:
                NotificacionViewHolder notificacionViewHolder = (NotificacionViewHolder) holder;
                Notificacion notificacion = (Notificacion) mModels.get(position);
                notificacionViewHolder.bind(contexto, notificacion, position);
                break;
            case PIE:
                PBarViewHolder pBarViewHolder = (PBarViewHolder) holder;
                pBarViewHolder.bind(contexto);
                break;
            default:
                PBarViewHolder pBarDefault = (PBarViewHolder) holder;
                pBarDefault.bind(contexto);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    public void animateTo(List<Object> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    @Override
    public int getItemViewType(int position) {
        if (mModels.get(position) instanceof Rubro) {
            return RUBRO;
        } else if (mModels.get(position) instanceof Subrubro) {
            return SUBRUBRO;
        } else if (mModels.get(position) instanceof Comercio) {
            return COMERCIO;
        } else if (mModels.get(position) instanceof Oferta) {
            return OFERTA;
        } else if (mModels.get(position) instanceof Evento) {
            return EVENTO;
        } else if (mModels.get(position) instanceof Notificacion) {
            return NOTIFICACION;
        } else if (mModels.get(position) instanceof ProgressBar) {
            return PIE;
        }
        return -1;
    }

    private void applyAndAnimateRemovals(List<Object> newModels) {
        for (int i = mModels.size() - 1; i >= 0; i--) {
            final Object model = mModels.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Object> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Object model = newModels.get(i);
            if (!mModels.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Object> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Object model = newModels.get(toPosition);
            final int fromPosition = mModels.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Object removeItem(int position) {
        final Object model = mModels.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Object model) {
        mModels.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Object model = mModels.remove(fromPosition);
        mModels.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }
}
