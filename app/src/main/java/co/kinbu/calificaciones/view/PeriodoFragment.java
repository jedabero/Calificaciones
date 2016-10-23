package co.kinbu.calificaciones.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

import co.kinbu.calificaciones.R;
import co.kinbu.calificaciones.data.Periodo;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PeriodoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PeriodoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PeriodoFragment extends Fragment {

    public static final String TAG = "PeriodoFragment";

    private OnFragmentInteractionListener mListener;

    private RecyclerView mAsignaturasView;
    private TextView mPromedioView;
    private TextInputEditText mNombreView;

    private Periodo mPeriodo;

    public PeriodoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PeriodosFragment.
     */
    public static PeriodoFragment newInstance() {
        PeriodoFragment fragment = new PeriodoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Log.d(TAG, "onCreate: ");
        }
        setHasOptionsMenu(true);
        if (mPeriodo == null) mPeriodo = new Periodo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_periodo, container, false);
        initInstances(view);

        mNombreView.setText(mPeriodo.getNombre());
        //mNombreView.addTextChangedListener();

        updatePromedio();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mAsignaturasView.setLayoutManager(layoutManager);

        return view;
    }

    private void initInstances(View view) {
        mNombreView = (TextInputEditText) view.findViewById(R.id.periodo_nombre);
        mPromedioView = (TextView) view.findViewById(R.id.periodo_promedio);
        mAsignaturasView = (RecyclerView) view.findViewById(R.id.list_asignaturas);
    }

    public void updatePromedio() {
        String p = String.format(Locale.getDefault(), "%,.2f" , mPeriodo.getPromedio());
        mPromedioView.setText(p);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }
}
