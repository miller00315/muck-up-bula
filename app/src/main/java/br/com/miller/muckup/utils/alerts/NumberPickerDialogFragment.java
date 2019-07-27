package br.com.miller.muckup.utils.alerts;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import org.jetbrains.annotations.NotNull;

import br.com.miller.muckup.R;

public class NumberPickerDialogFragment extends DialogFragment {

    private static final String DIALOG_TAG = NumberPickerDialogFragment.class.getSimpleName();

    private NumberPickerDialogFragmentListener numberPickerDialogFragmentListener;

    public void setListener(NumberPickerDialogFragmentListener numberPickerDialogFragmentListener){this.numberPickerDialogFragmentListener = numberPickerDialogFragmentListener; }

    public static NumberPickerDialogFragment newInstance(Bundle bundle){

        NumberPickerDialogFragment numberPickerDialogFragment = new NumberPickerDialogFragment();

        numberPickerDialogFragment.setArguments(bundle);

        return numberPickerDialogFragment;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.layout_alert_number_picker, container, false);

        final NumberPicker numberPicker = view.findViewById(R.id.number_picker_dialog);
        Button confirm = view.findViewById(R.id.confirm);
        Button cancel = view.findViewById(R.id.cancel);

        numberPicker.setMinValue(1);// restricted number to minimum value i.e 1
        numberPicker.setMaxValue(30);// restricked number to maximum value i.e. 31
        numberPicker.setWrapSelectorWheel(true);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getArguments() != null)
                    getArguments().putInt("quantity", numberPicker.getValue());

                numberPickerDialogFragmentListener.onDialogFragmentListener(getArguments());

                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    public void openDialog(FragmentManager fm) {
        if (fm.findFragmentByTag(DIALOG_TAG) == null) {
            show(fm, DIALOG_TAG);
        }
    }

    public interface  NumberPickerDialogFragmentListener{ void onDialogFragmentListener(Bundle bundle); }
}
