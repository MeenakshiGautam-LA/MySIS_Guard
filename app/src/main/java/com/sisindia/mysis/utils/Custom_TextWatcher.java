package com.sisindia.mysis.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.sisindia.mysis.listener.TextWatcherListener;

public class Custom_TextWatcher {
    private TextWatcherListener listener;
    private EditText editText;
    private Integer viewId;

    public Custom_TextWatcher(EditText editText, TextWatcherListener textWatcherListener,int viewId) {
        this.editText = editText;
        this.viewId = viewId;
        listener = textWatcherListener;
        inputTextWatcherMethod();
    }

    private void inputTextWatcherMethod() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!CSStringUtil.isEmptyStr(s.toString())) {
                    listener.onTextChangeMethod(s.toString(),viewId);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
