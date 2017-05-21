package com.example.forget.projectpet;

import android.content.Context;
import android.graphics.Rect;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SearchEditText extends RelativeLayout {
    private TextView.OnEditorActionListener onEditorActionListener;
    private OnFocusChangeListener onFocusChangeListener;
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

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId){
                    case EditorInfo.IME_ACTION_SEARCH:
                        if(onEditorActionListener != null)
                            onEditorActionListener.onEditorAction(v, actionId, event);
                        return true;
                }
                return false;
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (hasFocus()) {
                    int visible = View.INVISIBLE;
                    if(0 < editText.getText().length()){
                        visible = View.VISIBLE;
                    }
                    clearButton.setVisibility(visible);
                }
            }

            public void afterTextChanged(Editable s) {}
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

                if(onFocusChangeListener != null){
                    onFocusChangeListener.onFocusChange(view, hasFocus);
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                editText.setText(null);
                if(onEditorActionListener != null)
                    onEditorActionListener.onEditorAction(editText, EditorInfo.IME_ACTION_SEARCH, null);
            }
        });
    }

    public void setOnEditorActionListener(TextView.OnEditorActionListener _onEditorActionListener){
        onEditorActionListener = _onEditorActionListener;
    }

    public void setOnFocusChangeListener(OnFocusChangeListener _onFocusChangeListener){
        onFocusChangeListener = _onFocusChangeListener;
    }

    public void clearText(){
        editText.setText(null);
    }
}
