package co.kinbu.calificaciones.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import java.util.Locale;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 * Created by jedabero on 20/10/16.
 */

public class ViewUtils {

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static void replaceFragmentOnActivity(@NonNull FragmentManager fragmentManager,
                                                 @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);

        String fragmentClass = fragment.getClass().getName();

        boolean fragmentPopped = fragmentManager.popBackStackImmediate(fragmentClass, 0);
        if (!fragmentPopped && fragmentManager.findFragmentByTag(fragmentClass) == null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(frameId, fragment, fragmentClass);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.addToBackStack(fragmentClass);
            transaction.commit();
        }
    }

    @SuppressWarnings("all")
    public static void showMessage(@Nullable View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static String formatPromedio(double promedio) {
        return String.format(Locale.getDefault(), "%.2f", promedio);
    }
}
