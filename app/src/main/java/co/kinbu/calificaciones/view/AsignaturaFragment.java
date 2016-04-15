package co.kinbu.calificaciones.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
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
import co.kinbu.calificaciones.data.Asignatura;
import co.kinbu.calificaciones.data.Nota;
import co.kinbu.calificaciones.util.CustomTextWatcher;


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
    private TextInputEditText mNombreView;
    private TextView mPromedioView;
    private ImageButton addNotaButton;
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
        View view = inflater.inflate(R.layout.fragment_asignatura, container, false);
        initInstances(view);

        mNombreView.setText(mAsignatura.getNombre());
        mNombreView.addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (newText.compareTo(oldText) != 0 && !newText.isEmpty()) {
                    mListener.onAsignaturaNombreChange(newText, mAsignatura);
                }
            }
        });

        updatePromedio();

        addNotaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                addNota();
                v.setEnabled(true);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mNotasView.setLayoutManager(layoutManager);
        mNotasView.setAdapter(mNotasAdapter);

        return view;
    }

    private void initInstances(View v) {
        mNombreView = (TextInputEditText) v.findViewById(R.id.asignatura_nombre);
        mPromedioView = (TextView) v.findViewById(R.id.asignatura_promedio);
        addNotaButton = (ImageButton) v.findViewById(R.id.agregar);
        mNotasView = (RecyclerView) v.findViewById(R.id.list_notas);
        mNotasAdapter = new NotaRecyclerViewAdapter(mAsignatura.getNotas(), mListener);
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
                item.setEnabled(false);
                addNota();
                item.setEnabled(true);
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

    public void updatePromedio() {
        String p = String.format("%,.2f" , mAsignatura.getDefinitiva());
        mPromedioView.setText(p);
    }

    public void setAsignatura(Asignatura asignatura) {
        mAsignatura = asignatura;
        if (mNotasAdapter != null) {
            mNotasAdapter.animateTo(asignatura.getNotas());
            mNotasView.scrollToPosition(0);
        }
    }

    public void addNota() {
        mListener.onAddNota();
        if (mNotasAdapter != null)
            mNotasAdapter.notifyItemInserted(mAsignatura.getNotas().size());
        mNotasView.scrollToPosition(mAsignatura.getNotas().size());
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Nota nota);
        void onAsignaturaNombreChange(String nombre, Asignatura asignatura);
        void onDeleteNota(Nota nota);
        void onAddNota();
        void onNotaValorListener(Nota n, Double s);
        void onNotaPesoListener(Nota n, Integer s);
    }
}
