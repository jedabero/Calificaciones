package co.kinbu.calificaciones.util;

import android.text.TextWatcher;

/**
 * CustomTextWatcher
 * Created by jedabero on 15/03/16.
 */
public abstract class CustomTextWatcher implements TextWatcher {

    protected String oldText;
    protected String newText;

    public CustomTextWatcher() { }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        oldText = s.toString();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        newText = s.toString();
    }

}
