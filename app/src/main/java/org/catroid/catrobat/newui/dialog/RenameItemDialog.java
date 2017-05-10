package org.catroid.catrobat.newui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import org.catroid.catrobat.newui.R;

public class RenameItemDialog extends InputDialog {

    public static final String TAG = RenameItemDialog.class.getSimpleName();
    private RenameItemInterface renameItemInterface;

    public static RenameItemDialog newInstance(int title, int inputLabel, int positiveButton,
                                               int negativeButton, boolean allowEmptyInput, String currentName) {
        RenameItemDialog dialog = new RenameItemDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE, title);
        bundle.putInt(INPUT_LABEL, inputLabel);
        bundle.putInt(POSITIVE_BUTTON, positiveButton);
        bundle.putInt(NEGATIVE_BUTTON, negativeButton);
        bundle.putBoolean(ALLOW_EMPTY_INPUT, allowEmptyInput);
        bundle.putString(INITIAL_INPUT_VALUE, currentName);
        dialog.setArguments(bundle);
        return dialog;
    }

    public void setRenameItemInterface(RenameItemInterface renameItemInterface) {
        this.renameItemInterface = renameItemInterface;
    }

    @Override
    protected View inflateLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.dialog_rename_item, null);
    }

    protected boolean handlePositiveButtonClick() {
        String name = input.getText().toString().trim();

        if (renameItemInterface.isNameValid(name)) {
            renameItemInterface.renameItem(name);
            return true;
        }

        input.setError(getString(R.string.error_invalid_item_name));
        return false;
    }

    public interface RenameItemInterface {

        boolean isNameValid(String itemName);

        void renameItem(String itemName);
    }
}
