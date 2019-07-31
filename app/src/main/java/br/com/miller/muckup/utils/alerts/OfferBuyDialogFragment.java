package br.com.miller.muckup.utils.alerts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import br.com.miller.muckup.R;
import br.com.miller.muckup.domain.Offer;
import br.com.miller.muckup.utils.StringUtils;

public class OfferBuyDialogFragment extends DialogFragment {

    private static final String DIALOG_TAG = OfferBuyDialogFragment.class.getName();

    private OfferBuyDialogListener offerBuyDialogListener;

    public static OfferBuyDialogFragment newInstance(Bundle bundle){

        OfferBuyDialogFragment offerBuyDialogFragment = new OfferBuyDialogFragment();
        offerBuyDialogFragment.setArguments(bundle);
        return offerBuyDialogFragment;
    }

    public void setOfferBuyDialogListener(OfferBuyDialogListener offerBuyDialogListener){

        this.offerBuyDialogListener = offerBuyDialogListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.layout_alert_offer_buy, container, false);

        final NumberPicker numberPicker = view.findViewById(R.id.number_picker_dialog);
        TextView product = view.findViewById(R.id.offer_name);
        TextView price = view.findViewById(R.id.offer_value);
        Button confirm = view.findViewById(R.id.confirm);
        Button deleteItem = view.findViewById(R.id.delete_item);

        numberPicker.setMinValue(1);// restricted number to minimum value i.e 1
        numberPicker.setMaxValue(30);// restricked number to maximum value i.e. 31
        numberPicker.setWrapSelectorWheel(true);



        if(getArguments() != null){

            final Offer offer = getArguments().getParcelable("offer");

            if(offer != null) {
                product.setText(offer.getTitle());
                price.setText(StringUtils.doubleToMonetaryString(offer.getValue()));
                numberPicker.setValue(offer.getQuantity());
            }

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(offer != null) {
                        offer.setQuantity(numberPicker.getValue());
                        offerBuyDialogListener.onOfferBuyEdited(offer);
                    }

                    dismiss();
                }
            });

            deleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(offer != null)
                        offerBuyDialogListener.onOfferBuyDeleted(offer);

                    dismiss();
                }
            });

        }else{
            dismiss();
        }

        return view;
    }

    public void openDialog(FragmentManager fm) {
        if (fm.findFragmentByTag(DIALOG_TAG) == null) {
            show(fm, DIALOG_TAG);
        }
    }

    public interface OfferBuyDialogListener{

        void onOfferBuyEdited(Offer offer);
        void onOfferBuyDeleted(Offer offer);
    }
}
