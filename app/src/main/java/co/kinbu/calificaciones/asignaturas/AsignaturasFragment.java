package co.kinbu.calificaciones.asignaturas;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.kinbu.calificaciones.R;
import co.kinbu.calificaciones.data.Asignatura;
import co.kinbu.calificaciones.util.ViewUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 * Created by jedabero on 24/10/16.
 */

public final class AsignaturasFragment extends Fragment implements AsignaturasContract.View {

    @NonNull
    private static final String ARGUMENT_PERIODO_ID = "PERIODO_ID";

    private OnAsignaturasFragmentInteractionListener mListener;

    private AsignaturasContract.Presenter mPresenter;

    private AsigaturasAdapter mAsigaturasAdapter;

    private View mPeriodoDetails;
    private TextView mNombreView;
    private TextView mPromedioView;
    private RecyclerView mAsignaturasView;

    private View mNoAsignaturasView;
    private TextView mNoAsignaturasText;
    private Button mAddAsignatura;

    public static AsignaturasFragment newInstance(@Nullable String periodoId) {
        Bundle args = new Bundle();
        args.putString(ARGUMENT_PERIODO_ID, periodoId);
        AsignaturasFragment fragment = new AsignaturasFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAsigaturasAdapter = new AsigaturasAdapter(new ArrayList<Asignatura>(0), mItemListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.asignaturas_fragment, container, false);

        mPeriodoDetails = root.findViewById(R.id.periodo_details);
        mNombreView = (TextView) root.findViewById(R.id.periodo_nombre);
        mPromedioView = (TextView) root.findViewById(R.id.periodo_promedio);
        mAsignaturasView = (RecyclerView) root.findViewById(R.id.list_asignaturas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mAsignaturasView.setLayoutManager(layoutManager);
        mAsignaturasView.setAdapter(mAsigaturasAdapter);

        mNoAsignaturasView = root.findViewById(R.id.no_asignaturas);
        mNoAsignaturasText = (TextView) root.findViewById(R.id.no_asignaturas_text);
        mAddAsignatura = (Button) root.findViewById(R.id.add_asignatura);
        mAddAsignatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAsignatura();
            }
        });

        SwipeRefreshLayout srl = (SwipeRefreshLayout) root.findViewById(R.id.asignaturas_fragment);
        srl.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadPeriodo(false);
            }
        });

        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void setPresenter(@NonNull AsignaturasContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAsignaturasFragmentInteractionListener) {
            mListener = (OnAsignaturasFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnAsignaturasFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                mPresenter.loadPeriodo(true);
                break;
            case R.id.menu_edit:
                mPresenter.editPeriodo();
                break;
            case R.id.menu_add:
                mPresenter.addNewAsignatura();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.asignaturas_fragment_menu, menu);
    }

    @Override
    public void setLoadingInticator(final boolean active) {
        if (active) {
            mNombreView.setText(getString(R.string.loading));
        }
        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl = (SwipeRefreshLayout) getView().findViewById(R.id.asignaturas_fragment);
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void showMissingPeriodo() {
        showNoAsignaturasView(getString(R.string.no_periodo), false);
    }

    @Override
    public void showNombre(@NonNull String nombre) {
        mNombreView.setVisibility(View.VISIBLE);
        mNombreView.setText(nombre);
    }

    @Override
    public void showPromedio(double promedio) {
        mPromedioView.setVisibility(View.VISIBLE);
        mPromedioView.setText(ViewUtils.formatPromedio(promedio));
    }

    @Override
    public void showEditPeriodo() {
        // TODO
    }

    @Override
    public void showPeriodoDeleted() {
        // TODO
    }

    @Override
    public void showAsignaturas(List<Asignatura> asignaturas) {
        mAsigaturasAdapter.replaceData(asignaturas);

        mPeriodoDetails.setVisibility(View.VISIBLE);
        mNoAsignaturasView.setVisibility(View.GONE);
    }

    @Override
    public void showAddAsignatura() {
        mListener.onShowAddAsignatura();
    }

    @Override
    public void showAsignaturaDetailsUi(String asignaturaId) {
        mListener.onShowAsignaturaDetailsUi(asignaturaId);
    }

    @Override
    public void showLoadingAsignaturasError() {
        ViewUtils.showMessage(getView(), getString(R.string.loading_asignaturas_error));
    }

    @Override
    public void showNoAsignaturas() {
        showNoAsignaturasView(getString(R.string.no_asignaturas), false);
    }

    @Override
    public void showSuccessfullySavedMessage() {
        ViewUtils.showMessage(getView(), getString(R.string.successfully_saved_asignatura));
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    private void showNoAsignaturasView(String mainText, boolean showAddView) {
        mPeriodoDetails.setVisibility(View.GONE);
        mNoAsignaturasView.setVisibility(View.VISIBLE);

        mNoAsignaturasText.setText(mainText);
        mAddAsignatura.setVisibility(showAddView ? View.VISIBLE : View.GONE);
    }

    private AsignaturaItemListener mItemListener = new AsignaturaItemListener() {
        @Override
        public void onAsignaturaClick(Asignatura asignatura) {
            mPresenter.openAsignaturaDetails(asignatura);
        }
    };

    public interface OnAsignaturasFragmentInteractionListener {

        void onShowAddAsignatura();

        void onShowAsignaturaDetailsUi(String asignaturaId);

    }

}
