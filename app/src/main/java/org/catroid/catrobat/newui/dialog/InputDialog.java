package org.catroid.catrobat.newui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.catroid.catrobat.newui.R;

public abstract class InputDialog extends AppCompatDialogFragment {

    protected static final String TITLE = "title";
    protected static final String INPUT_LABEL = "inputLabel";
    protected static final String POSITIVE_BUTTON = "positive";
    protected static final String NEGATIVE_BUTTON = "negative";
    protected static final String ALLOW_EMPTY_INPUT = "allowEmptyInput";

    protected EditText input;
    protected boolean allowEmptyInput;

    protected abstract View inflateLayout(LayoutInflater inflater);

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        allowEmptyInput = getArguments().getBoolean(ALLOW_EMPTY_INPUT, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(getArguments().getInt(TITLE));

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflateLayout(inflater);

        builder.setView(view);
        ((TextView) view.findViewById(R.id.input_label)).setText(getArguments()
                .getInt(INPUT_LABEL));

        input = (EditText) view.findViewById(R.id.input);

        builder.setPositiveButton(getArguments().getInt(POSITIVE_BUTTON), null);

        builder.setNegativeButton(getArguments().getInt(NEGATIVE_BUTTON),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        handleNegativeButtonClick(dialogInterface);
                    }
                });

        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                showKeyboard();
                Button buttonPositive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (handlePositiveButtonClick()) {
                            dismiss();
                        }
                    }
                });
                if (!allowEmptyInput) {
                    input.addTextChangedListener(getInputTextWatcher(buttonPositive));

                    if (input.getText().length() == 0) {
                        buttonPositive.setEnabled(false);
                    }
                }
            }
        });

        return alertDialog;
    }

    protected abstract boolean handlePositiveButtonClick();

    protected void handleNegativeButtonClick(DialogInterface dialogInterface) {
        onCancel(dialogInterface);
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        dismiss();
    }

    protected void showKeyboard() {
        if (input.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

            imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
        }
    }


    protected TextWatcher getInputTextWatcher(final Button positiveButton) {
        return new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    positiveButton.setEnabled(false);
                } else {
                    positiveButton.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() == 0) {
                    positiveButton.setEnabled(false);
                } else {
                    positiveButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }
}
