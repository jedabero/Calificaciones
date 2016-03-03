package co.kinbu.calificaciones;

import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

/**
 * NotaRecyclerViewAdapter
 * Created by jedabero on 2/03/16.
 */
public class NotaRecyclerViewAdapter extends RecyclerView.Adapter<NotaRecyclerViewAdapter.NotaHolder> {

    private List<Nota> notas;

    public NotaRecyclerViewAdapter(List<Nota> notas/*, listener*/) {
        this.notas = notas;
    }

    @Override
    public NotaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nota_peso_list_item, parent, false);
        return new NotaHolder(view);
    }

    @Override
    public void onBindViewHolder(NotaHolder holder, int position) {
        holder.nota = notas.get(position);
        holder.valorText.setText(String.valueOf(holder.nota.getValor()));
        holder.pesoText.setText(String.valueOf(holder.nota.getPeso()));
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    class NotaHolder extends RecyclerView.ViewHolder {

        public final View view;
        public final TextInputEditText valorText;
        public final TextInputEditText pesoText;
        public Nota nota;

        public NotaHolder(View itemView) {
            super(itemView);
            view = itemView;
            valorText = (TextInputEditText) view.findViewById(R.id.text_nota);
            pesoText = (TextInputEditText) view.findViewById(R.id.text_peso);
        }
    }
}

