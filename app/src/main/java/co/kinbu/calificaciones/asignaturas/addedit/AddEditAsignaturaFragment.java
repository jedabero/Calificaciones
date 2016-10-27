package co.kinbu.calificaciones.asignaturas.addedit;

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
 * Created by jedabero on 26/10/16.
 */

public final class AddEditAsignaturaFragment extends Fragment implements
        AddEditAsignaturaContract.View {

    private OnAddEditAsignaturaFragmentInteractionListener mListener;

    private AddEditAsignaturaContract.Presenter mPresenter;

    private TextView mNombre;

    public static AddEditAsignaturaFragment newInstance() {

        Bundle args = new Bundle();

        AddEditAsignaturaFragment fragment = new AddEditAsignaturaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AddEditAsignaturaFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.addedit_asignatura_fragment, container, false);
        mNombre = (TextView) root.findViewById(R.id.asignatura_nombre);

        setHasOptionsMenu(true);
        setRetainInstance(true);
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddEditAsignaturaFragmentInteractionListener) {
            mListener = (OnAddEditAsignaturaFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnAddEditAsignaturaFragmentInteractionListener");
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
                mPresenter.saveAsignatura(mNombre.getText().toString());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.addedit_asignatura_fragment_menu, menu);
    }

    @Override
    public void setPresenter(@NonNull AddEditAsignaturaContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showEmptyAsignaturaError() {
        ViewUtils.showMessage(mNombre, getString(R.string.empty_asignatura_message));
    }

    @Override
    public void showAsignaturas(String periodoId) {
        mListener.onShowAsignaturas(periodoId);
    }

    @Override
    public void setNombre(String nombre) {
        mNombre.setText(nombre);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    public interface OnAddEditAsignaturaFragmentInteractionListener {

        void onShowAsignaturas(String periodoId);

    }
}
