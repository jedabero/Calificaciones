package co.kinbu.calificaciones.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import co.kinbu.calificaciones.NotaRecyclerViewAdapter;
import co.kinbu.calificaciones.R;
import co.kinbu.calificaciones.data.model.Asignatura;
import co.kinbu.calificaciones.data.model.Nota;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AsignaturaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AsignaturaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AsignaturaFragment extends Fragment {

    public static final String TAG = "AsignaturaFragment";

    private OnFragmentInteractionListener mListener;

    private RecyclerView mNotasView;
    private NotaRecyclerViewAdapter mNotasAdapter;
    private Asignatura mAsignatura;

    public AsignaturaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment
     *
     * @return A new instance of fragment AsignaturaFragment.
     */
    public static AsignaturaFragment newInstance() {
        /** /AsignaturaFragment fragment =
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);/**/
        return new AsignaturaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** /if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }/**/
        setHasOptionsMenu(true);
        if (mAsignatura == null) mAsignatura = new Asignatura();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notas, container, false);

        TextView nombreView = (TextView) view.findViewById(R.id.asignatura_nombre);
        nombreView.setText(mAsignatura.getNombre());
        TextView promedioView = (TextView) view.findViewById(R.id.asignatura_promedio);
        promedioView.setText(String.valueOf(mAsignatura.getDefinitiva()));
        ImageButton addNotaButton = (ImageButton) view.findViewById(R.id.agregar);
        addNotaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNota(new Nota());
            }
        });

        mNotasView = (RecyclerView) view.findViewById(R.id.list_notas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mNotasView.setLayoutManager(layoutManager);
        mNotasAdapter = new NotaRecyclerViewAdapter(mAsignatura.getNotas(), mListener);
        mNotasView.setAdapter(mNotasAdapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_nota, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcion_agregar:
                addNota(new Nota());
                return true;
            default:
                return super.onOptionsItemSelected(item);
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



    public void setAsignatura(Asignatura asignatura) {
        mAsignatura = asignatura;
        if (mNotasAdapter != null) {
            mNotasAdapter.animateTo(asignatura.getNotas());
            mNotasView.scrollToPosition(0);
        }
    }

    public void addNota(Nota nota) {
        nota = mListener.onAddNota(nota);
        if (mNotasAdapter != null) mNotasAdapter.addItem(nota);
        mNotasView.scrollToPosition(mAsignatura.getNotas().size()-1);
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
        Nota onAddNota(Nota nota);
        void onNotaValorListener(Nota n, Double s);
        void onNotaPesoListener(Nota n, Integer s);
    }
}
