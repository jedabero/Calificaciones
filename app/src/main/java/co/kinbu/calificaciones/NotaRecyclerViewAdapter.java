package co.kinbu.calificaciones;

import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.List;

import co.kinbu.calificaciones.data.model.Nota;
import co.kinbu.calificaciones.util.CustomTextWatcher;
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
                removeItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    public void removeItem (int position) {
        if (position < notas.size()) {
            final Nota n = notas.get(position);
            mListener.onDeleteNota(n);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, notas.size());
        }
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

    abstract class NumberTextWatcher extends CustomTextWatcher {

        protected Nota mNota;
        protected AfterTextChangedWorker mWorker;

        public NumberTextWatcher(Nota nota, AfterTextChangedWorker worker) {
            this.mNota = nota;
            this.mWorker = worker;
        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                Number oldValor = null;
                try {
                    oldValor = mWorker.parseNumber(oldText);
                } catch (NumberFormatException nfe) {
                    if (BuildConfig.DEBUG) nfe.printStackTrace();
                }
                Number newValor = mWorker.parseNumber(newText);
                if (oldValor == null || mWorker.compare(newValor, oldValor) != 0) {
                    mWorker.callListener(mNota, newValor);
                }
            } catch (NumberFormatException nfe) {
                if (BuildConfig.DEBUG) nfe.printStackTrace();
            }
        }

    }

    private interface AfterTextChangedWorker {
        void callListener(Nota n, Number newValor);
        Number parseNumber(String n);
        int compare(Number newValor, Number oldValor);
    }

    class NotaValorWatcher extends NumberTextWatcher {

        public NotaValorWatcher(Nota nota) {
            super(nota, new AfterTextChangedWorker() {
                @Override
                public void callListener(Nota n, Number newValor) {
                    mListener.onNotaValorListener(n, (Double) newValor);
                }

                @Override
                public Number parseNumber(String n) {
                    return Double.parseDouble(n);
                }

                @Override
                public int compare(Number newValor, Number oldValor) {
                    return ((Double) newValor).compareTo((Double) oldValor);
                }
            });
        }

    }

    class NotaPesoWatcher extends NumberTextWatcher {

        public NotaPesoWatcher(Nota nota) {
            super(nota, new AfterTextChangedWorker() {
                @Override
                public void callListener(Nota n, Number newValor) {
                    mListener.onNotaPesoListener(n, (Integer) newValor);
                }

                @Override
                public Number parseNumber(String n) {
                    return Integer.parseInt(n);
                }

                @Override
                public int compare(Number newValor, Number oldValor) {
                    return ((Integer) newValor).compareTo((Integer) oldValor);
                }
            });
        }

    }

}

