package com.example.forget.projectpet;

import android.content.Context;
import android.graphics.Rect;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class SearchEditText extends RelativeLayout {
    private TextWatcher textWatcher;
    private EditText editText;
    private ImageButton clearButton;

    public SearchEditText(Context context){
        this(context, null);
    }

    public SearchEditText(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        init();
    }

    public boolean hasFocus() {
        if(editText.hasFocus() || clearButton.hasFocus())
            return true;

        return false;
    }

    public void clearFocus() {
        editText.clearFocus();
        clearButton.clearFocus();
    }

    public IBinder getWindowToken() {
        return editText.getWindowToken();
    }

    public boolean isIn(int x, int y){
        Rect editTextRect = new Rect();
        Rect clearButtonRect = new Rect();
        editText.getGlobalVisibleRect(editTextRect);
        clearButton.getGlobalVisibleRect(clearButtonRect);
        if (editTextRect.contains(x, y) || clearButtonRect.contains(x, y)){
            return true;
        }

        return false;
    }

    public void init(){
        inflate(getContext(), R.layout.search_edit_text, this);
        editText = (EditText) findViewById(R.id.search_edit_text_text);
        clearButton = (ImageButton) findViewById(R.id.search_edit_text_clear);
        clearButton.setVisibility(View.INVISIBLE);

        editText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(textWatcher != null){
                    textWatcher.beforeTextChanged(s, start, count, after);
                }
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (hasFocus()) {
                    int visible = View.INVISIBLE;
                    if(0 < editText.getText().length()){
                        visible = View.VISIBLE;
                    }
                    clearButton.setVisibility(visible);
                }

                if(textWatcher != null){
                    textWatcher.onTextChanged(s, start, before, count);
                }
            }

            public void afterTextChanged(Editable s) {
                if(textWatcher != null){
                    textWatcher.afterTextChanged(s);
                }
            }
        });

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(final View view, final boolean hasFocus) {
                int visible = View.INVISIBLE;
                if(hasFocus){
                    if(0 < editText.getText().length()){
                        visible = View.VISIBLE;
                    }
                }

                clearButton.setVisibility(visible);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                editText.setText(null);
            }
        });
    }

    public void addTextChangedListener(TextWatcher _textWatcher){
        textWatcher = _textWatcher;
    }
}
