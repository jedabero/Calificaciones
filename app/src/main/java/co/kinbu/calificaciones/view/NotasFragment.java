package co.kinbu.calificaciones.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.kinbu.calificaciones.NotaRecyclerViewAdapter;
import co.kinbu.calificaciones.R;
import co.kinbu.calificaciones.data.model.Nota;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotasFragment extends Fragment {

    public static final String TAG = "NotasFragment";

    private OnFragmentInteractionListener mListener;

    private RecyclerView mNotasView;
    private NotaRecyclerViewAdapter mNotasAdapter;
    private List<Nota> mNotas;

    public NotasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment
     *
     * @return A new instance of fragment NotasFragment.
     */
    public static NotasFragment newInstance() {
        /** /NotasFragment fragment =
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);/**/
        return new NotasFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** /if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }/**/
        if (mNotas == null) mNotas = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notas, container, false);

        //TODO MODEL
        TextView nombreView = (TextView) view.findViewById(R.id.asignatura_nombre);
        nombreView.setText(String.valueOf("Asignatura"));
        TextView promedioView = (TextView) view.findViewById(R.id.asignatura_promedio);
        promedioView.setText(String.valueOf(3.2));

        registerForContextMenu(view.findViewById(R.id.asignatura_detalles));

        mNotasView = (RecyclerView) view.findViewById(R.id.list_notas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mNotasView.setLayoutManager(layoutManager);
        mNotasAdapter = new NotaRecyclerViewAdapter(mNotas, mListener);
        mNotasView.setAdapter(mNotasAdapter);

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_nota, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcion_agregar:

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setNotas(List<Nota> notas) {
        mNotas = notas;
        if (mNotasAdapter != null) {
            mNotasAdapter.animateTo(notas);
            mNotasView.scrollToPosition(0);
        }
    }

    public void addNota(Nota nota) {
        mNotas.add(nota);
        if (mNotasAdapter != null) mNotasAdapter.addItem(nota);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Nota nota);
        void onDeleteNota(Nota nota);
    }
}
