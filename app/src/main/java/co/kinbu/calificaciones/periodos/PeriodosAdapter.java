package co.kinbu.calificaciones.periodos;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import co.kinbu.calificaciones.R;
import co.kinbu.calificaciones.data.Periodo;
import co.kinbu.calificaciones.util.ViewUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 * Created by jedabero on 20/10/16.
 */

public final class PeriodosAdapter extends RecyclerView.Adapter<PeriodosAdapter.PeriodoHolder> {

    private List<Periodo> mPeriodos;
    private PeriodoItemListener mItemListener;


    public PeriodosAdapter(List<Periodo> periodos, PeriodoItemListener itemListener) {
        setList(periodos);
        mItemListener = itemListener;
    }

    private void setList(@NonNull List<Periodo> periodos) {
        mPeriodos = checkNotNull(periodos);
    }

    public void replaceData(List<Periodo> periodos) {
        setList(periodos);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mPeriodos.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public PeriodoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.periodo_list_item, parent, false);
        return new PeriodoHolder(view);
    }

    @Override
    public void onBindViewHolder(final PeriodoHolder holder, final int position) {
        final Periodo periodo = mPeriodos.get(holder.getAdapterPosition());
        holder.setPeriodo(periodo);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.onPeriodoClick(periodo);
            }
        });
    }

    class PeriodoHolder extends RecyclerView.ViewHolder {

        final View view;
        final TextView nombreText;
        final TextView promedioText;
        Periodo periodo;

        PeriodoHolder(View itemView) {
            super(itemView);
            view = itemView;
            nombreText = (TextView) view.findViewById(R.id.periodo_nombre);
            promedioText = (TextView) view.findViewById(R.id.periodo_promedio);
        }

        void setPeriodo(Periodo periodo) {
            this.periodo = periodo;
            nombreText.setText(periodo.getNombre());
            promedioText.setText(ViewUtils.formatPromedio(periodo.getPromedio()));
        }
    }
}
