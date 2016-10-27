package co.kinbu.calificaciones.periodos.addedit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.kinbu.calificaciones.R;
import co.kinbu.calificaciones.util.ViewUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 * Created by jedabero on 22/10/16.
 */

public final class AddEditPeriodoFragment extends Fragment implements AddEditPeriodoContract.View {

    // public static final String

    private OnAddEditPeriodoFragmentInteractionListener mListener;

    private AddEditPeriodoContract.Presenter mAddEditPeriodoPresenter;

    private TextView mNombre;

    public static AddEditPeriodoFragment newInstance() {
        return new AddEditPeriodoFragment();
    }

    public AddEditPeriodoFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mAddEditPeriodoPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull AddEditPeriodoContract.Presenter presenter) {
        mAddEditPeriodoPresenter = checkNotNull(presenter);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.addedit_periodo_fragment, container, false);
        mNombre = (TextView) root.findViewById(R.id.periodo_nombre);

        setHasOptionsMenu(true);
        setRetainInstance(true);
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddEditPeriodoFragmentInteractionListener) {
            mListener = (OnAddEditPeriodoFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnAddEditPeriodoFragmentInteractionListener");
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
            case R.id.menu_save:
                mAddEditPeriodoPresenter.savePeriodo(mNombre.getText().toString());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.addedit_periodo_fragment_menu, menu);
    }

    @Override
    public void showEmptyPeriodoError() {
        ViewUtils.showMessage(mNombre, getString(R.string.empty_periodo_message));
    }

    @Override
    public void showPeriodos() {
        mListener.onShowPeriodos();
    }

    @Override
    public void setNombre(String nombre) {
        mNombre.setText(nombre);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    public interface OnAddEditPeriodoFragmentInteractionListener {

        void onShowPeriodos();

    }

}
