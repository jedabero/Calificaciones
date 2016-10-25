package co.kinbu.calificaciones.asignaturas;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.kinbu.calificaciones.R;
import co.kinbu.calificaciones.data.Asignatura;
import co.kinbu.calificaciones.util.ViewUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 * Created by jedabero on 24/10/16.
 */

public final class AsigaturasAdapter extends RecyclerView.Adapter<AsigaturasAdapter.AsignaturaHolder> {

    private List<Asignatura> mAsignaturas;
    private AsignaturaItemListener mItemListener;


    public AsigaturasAdapter(List<Asignatura> asignaturas, AsignaturaItemListener itemListener) {
        mAsignaturas = asignaturas;
        mItemListener = itemListener;
    }

    private void setList(@NonNull List<Asignatura> asignaturas) {
        mAsignaturas = checkNotNull(asignaturas);
    }

    public void replaceData(List<Asignatura> asignaturas) {
        setList(asignaturas);
        notifyDataSetChanged();
    }

    @Override
    public AsignaturaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.asignatura_list_item, parent, false);
        return new AsignaturaHolder(view);
    }

    @Override
    public void onBindViewHolder(AsignaturaHolder holder, final int position) {
        final Asignatura asignatura = mAsignaturas.get(holder.getAdapterPosition());
        holder.setAsignatura(asignatura);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.onAsignaturaClick(asignatura);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mAsignaturas.size();
    }

    class AsignaturaHolder extends RecyclerView.ViewHolder {

        final View view;
        final TextView nombreText;
        final TextView promedioText;
        Asignatura asignatura;

        AsignaturaHolder(View itemView) {
            super(itemView);
            view = itemView;
            nombreText = (TextView) view.findViewById(R.id.asignatura_nombre);
            promedioText = (TextView) view.findViewById(R.id.asignatura_promedio);
        }

        void setAsignatura(Asignatura asignatura) {
            this.asignatura = asignatura;
            nombreText.setText(asignatura.getNombre());
            promedioText.setText(ViewUtils.formatPromedio(asignatura.getDefinitiva()));
        }
    }
}
