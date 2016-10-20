package co.kinbu.calificaciones.periodos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import co.kinbu.calificaciones.data.Periodo;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 * Created by jedabero on 19/10/16.
 */

public final class PeriodosFragment extends Fragment implements PeriodosContract.View {

    private static final String TAG = "PeriodosFragment";
    private PeriodosContract.Presenter mPeriodosPresenter;

    private PeriodosAdapter mPeriodosAdapter;

    private RecyclerView mPeriodosView;
    private View mNoPeriodosView;
    private TextView mNoPeriodosText;
    private Button mAddPeriodo;

    public PeriodosFragment() { }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PeriodosFragment.
     */
    public static PeriodosFragment newInstance() {
        return new PeriodosFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Log.d(TAG, "onCreate: " + getArguments());
        }
        mPeriodosAdapter = new PeriodosAdapter(new ArrayList<Periodo>(0), mItemListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPeriodosPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull PeriodosContract.Presenter presenter) {
        mPeriodosPresenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPeriodosPresenter.result(requestCode, resultCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.periodos_fragment, container, false);

        mPeriodosView = (RecyclerView) root.findViewById(R.id.list_periodos);

        mPeriodosView.setAdapter(mPeriodosAdapter);

        mNoPeriodosView = root.findViewById(R.id.no_periodos);
        mNoPeriodosText = (TextView) root.findViewById(R.id.no_periodos_text);
        mAddPeriodo = (Button) root.findViewById(R.id.add_periodo);
        mAddPeriodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddPeriodo();
            }
        });

        SwipeRefreshLayout srl = (SwipeRefreshLayout) root.findViewById(R.id.periodos_fragment);
        srl.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPeriodosPresenter.loadPeriodos(false);
            }
        });

        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                mPeriodosPresenter.loadPeriodos(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.periodos_fragment_menu, menu);
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl = (SwipeRefreshLayout) getView().findViewById(R.id.periodos_fragment);
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void showPeriodos(List<Periodo> periodos) {
        mPeriodosAdapter.replaceData(periodos);

        mPeriodosView.setVisibility(View.VISIBLE);
        mNoPeriodosView.setVisibility(View.GONE);
    }

    @Override
    public void showAddPeriodo() {
        // TODO
    }

    @Override
    public void showPeriodoDetailsUi(String periodoId) {
        // TODO
    }

    @Override
    public void showLoadingPeriodosError() {
        showMessage(getString(R.string.loading_periodos_error));
    }

    @Override
    public void showNoPeriodos() {
        showNoPeriodosView(getString(R.string.no_periodos), false);
    }

    @Override
    public void showSuccessfullySavedMessage() {
        showMessage(getString(R.string.successfully_saved_periodo));
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    private void showNoPeriodosView(String mainText, boolean showAddView) {
        mPeriodosView.setVisibility(View.GONE);
        mNoPeriodosView.setVisibility(View.VISIBLE);

        mNoPeriodosText.setText(mainText);
        mAddPeriodo.setVisibility(showAddView ? View.VISIBLE : View.GONE);
    }

    private PeriodoItemListener mItemListener = new PeriodoItemListener() {
        @Override
        public void onPeriodoClick(Periodo periodo) {
            mPeriodosPresenter.openPeriodoDetails(periodo);
        }
    };

}
