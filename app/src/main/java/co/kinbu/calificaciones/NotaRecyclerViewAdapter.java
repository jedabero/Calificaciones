package co.kinbu.calificaciones;

import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.List;

import co.kinbu.calificaciones.data.model.Nota;
import co.kinbu.calificaciones.view.AsignaturaFragment.OnFragmentInteractionListener;

/**
 * NotaRecyclerViewAdapter
 * Created by jedabero on 2/03/16.
 */
public class NotaRecyclerViewAdapter extends RecyclerView.Adapter<NotaRecyclerViewAdapter.NotaHolder> {

    public static String TAG = "NotaRecyclerViewAdapter";

    private OnFragmentInteractionListener mListener;

    private List<Nota> notas;

    public NotaRecyclerViewAdapter(List<Nota> notas, OnFragmentInteractionListener listener) {
        this.notas = notas;
        mListener = listener;
    }

    @Override
    public NotaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nota_peso_list_item, parent, false);
        return new NotaHolder(view);
    }

    @Override
    public void onBindViewHolder(final NotaHolder holder, final int position) {
        holder.nota = notas.get(position);
        holder.valorText.setText(String.valueOf(holder.nota.getValor()));
        holder.pesoText.setText(String.valueOf(holder.nota.getPeso()));

        holder.valorText.addTextChangedListener(new NotaValorWatcher(holder.nota));
        holder.pesoText.addTextChangedListener(new NotaPesoWatcher(holder.nota));
        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteNota(removeItem(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    @Nullable
    public Nota removeItem (int position) {
        if (position < notas.size()) {
            final Nota n = notas.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, notas.size());
            return n;
        }
        return null;
    }

    public void addItem(Nota n) {
        addItem(notas.size(), n);
    }

    public void addItem(int position, Nota n) {
        notas.add(position, n);
        notifyItemInserted(position);
    }

    public void moveItem(int from, int to) {
        final Nota n = notas.remove(from);
        notas.add(to, n);
        notifyItemMoved(from, to);
    }

    public void animateTo(List<Nota> list) {
        applyAndAnimateRemovals(list);
        applyAndAnimateAdditions(list);
        applyAndAnimateMovedItems(list);
    }

    private void applyAndAnimateRemovals(List<Nota> list) {
        for (int i = notas.size() - 1; i >= 0; i--) {
            final Nota u = notas.get(i);
            if (!list.contains(u)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Nota> list) {
        for (int i = 0, count = list.size(); i < count; i++) {
            final Nota u = list.get(i);
            if (!notas.contains(u)){
                addItem(i, u);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Nota> list) {
        for (int to = list.size() -1; to > 0; to--) {
            final Nota u = list.get(to);
            final int from = notas.indexOf(u);
            if (from >= 0 && from != to){
                moveItem(from, to);
            }
        }
    }

    class NotaHolder extends RecyclerView.ViewHolder {

        public final View view;
        public final TextInputEditText valorText;
        public final TextInputEditText pesoText;
        public final ImageButton removeButton;
        public Nota nota;

        public NotaHolder(View itemView) {
            super(itemView);
            view = itemView;
            valorText = (TextInputEditText) view.findViewById(R.id.text_nota);
            pesoText = (TextInputEditText) view.findViewById(R.id.text_peso);
            removeButton = (ImageButton) view.findViewById(R.id.eliminar);
        }

    }

    abstract class CustomTextWatcher implements TextWatcher {

        protected String oldText;
        protected String newText;

        protected Nota mNota;

        public CustomTextWatcher(Nota nota) {
            this.mNota = nota;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            oldText = s.toString();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            newText = s.toString();
        }

    }

    class NotaValorWatcher extends CustomTextWatcher {

        public NotaValorWatcher(Nota nota) {
            super(nota);
        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                Double oldValor = Double.parseDouble(oldText);
                Double newValor = Double.parseDouble(newText);
                if (newValor.compareTo(oldValor) != 0) {
                    mListener.onNotaValorListener(this.mNota, newValor);
                }
            } catch (NumberFormatException nfe) {
                if (BuildConfig.DEBUG) nfe.printStackTrace();
            }
        }

    }

    class NotaPesoWatcher extends CustomTextWatcher {

        public NotaPesoWatcher(Nota nota) {
            super(nota);
        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                Integer oldValor = Integer.parseInt(oldText);
                Integer newValor = Integer.parseInt(newText);
                if (oldValor.compareTo(newValor) != 0) {
                    mListener.onNotaPesoListener(this.mNota, newValor);
                }
            } catch (NumberFormatException nfe) {
                if (BuildConfig.DEBUG) nfe.printStackTrace();
            }
        }

    }

}

