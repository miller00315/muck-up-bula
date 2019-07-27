package br.com.miller.muckup.utils.alerts;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;

import br.com.miller.muckup.R;
import br.com.miller.muckup.utils.MoneyTextWatcher;

public class EditTextDialogFragment extends DialogFragment {

    private static final String DIALOG_TAG = EditTextDialogFragment.class.getSimpleName();

    private EditTextFragmentListener editTextFragmentListener;

    public static EditTextDialogFragment newInstance(Bundle bundle){

        EditTextDialogFragment editTextDialogFragment = new EditTextDialogFragment();
        editTextDialogFragment.setArguments(bundle);

        return editTextDialogFragment;
    }

    public void setListener(EditTextFragmentListener editTextFragmentListener){ this.editTextFragmentListener = editTextFragmentListener; }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

        View view;

        if(Objects.requireNonNull(getArguments()).getInt("view") == R.layout.layout_single_edit_text_alert_fragment) {

            view = inflater.inflate(R.layout.layout_single_edit_text_alert_fragment, container, false);
            final TextView textView = view.findViewById(R.id.title_single_edit);
            final EditText editText = view.findViewById(R.id.edit_text_alert_fragment);
            Button confirm = view.findViewById(R.id.confirm);
            Button cancel = view.findViewById(R.id.cancel);

            editText.setHint(getArguments().getString("hint"));

            textView.setText(getArguments().getString("hint"));

            if(getArguments().getBoolean("isMoney")){

                editText.addTextChangedListener(new MoneyTextWatcher(editText, Locale.getDefault()));
                editText.setText(String.format(Locale.getDefault(), "%.2f", Double.valueOf(Objects.requireNonNull(getArguments().getString("text")))));


            }else{ editText.setText(getArguments().getString("text")); }

            editText.setInputType(getArguments().getInt("inputType"));

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(getArguments() != null) {

                        getArguments().putString("result", editText.getText().toString());
                        editTextFragmentListener.onEditTextDialogFragmentResult(getArguments());
                    }

                    dismiss();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

        }else{

            view =  inflater.inflate(R.layout.layout_double_edit_text_alert_fragment, container, false);

            final EditText firstEditText = view.findViewById(R.id.first_edit_text_alert_fragment);
            final EditText secondEdiText = view.findViewById(R.id.second_edit_text_alert_fragment);
            TextView textView = view.findViewById(R.id.title_double_edit);
            Button confirm = view.findViewById(R.id.confirm);
            Button cancel = view.findViewById(R.id.cancel);

            textView.setText(getArguments().getString("firstHint"));

            firstEditText.setHint(getArguments().getString("firstHint"));
            secondEdiText.setHint(getArguments().getString("secondHint"));

            firstEditText.setText(getArguments().getString("firstText"));
            secondEdiText.setText(getArguments().getString("secondText"));

            firstEditText.setInputType(getArguments().getInt("firstInputType"));
            secondEdiText.setInputType(getArguments().getInt("secondInputType"));

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(getArguments() != null){

                        getArguments().putString("firstResult", firstEditText.getText().toString());
                        getArguments().putString("secondResult", secondEdiText.getText().toString());
                        editTextFragmentListener.onEditTextDialogFragmentResult(getArguments());
                    }

                    dismiss();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

        }

        return view;

    }

    public void openDialog(FragmentManager fm) {
        if (fm.findFragmentByTag(DIALOG_TAG) == null) {
            show(fm, DIALOG_TAG);
        }
    }

    public interface EditTextFragmentListener { void onEditTextDialogFragmentResult(Bundle bundle); }
}
