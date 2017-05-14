package com.example.forget.projectpet;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ClearEditText extends RelativeLayout {
    private TextWatcher textWatcher;
    private EditText editText;
    private ImageButton clearButton;

    public ClearEditText(Context context){
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        init();
    }

    public void init(){
        inflate(getContext(), R.layout.clear_edit_text, this);
        editText = (EditText) findViewById(R.id.clear_edit_text_text);
        clearButton = (ImageButton) findViewById(R.id.clear_edit_text_clear);
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
