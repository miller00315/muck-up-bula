package br.com.miller.muckup.utils.alerts;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import br.com.miller.muckup.R;
import br.com.miller.muckup.domain.Offer;

public class CartItemDialogFragment extends DialogFragment {

    private static final String DIALOG_TAG = CartItemDialogFragment.class.getName();

    private CartItemListener cartItemListener;

    public static CartItemDialogFragment newInstance(Bundle bundle){

        CartItemDialogFragment cartItemDialogFragment = new CartItemDialogFragment();
        cartItemDialogFragment.setArguments(bundle);

        return cartItemDialogFragment;
    }

    public void setCartItemListener(CartItemListener cartItemListener){
        this.cartItemListener = cartItemListener;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.layout_alert_cart_item, container, false);

        if(getArguments() != null) {
            final Offer offer = getArguments().getParcelable("offer");

            TextView headerItemCart = view.findViewById(R.id.header_item_cart);
            final NumberPicker numberPicker = view.findViewById(R.id.number_picker_dialog);
            Button buyNowItem = view.findViewById(R.id.buy_now_item);
            Button deletItem = view.findViewById(R.id.delete_item);
            Button cancel = view.findViewById(R.id.cancel);

            numberPicker.setMinValue(1);// restricted number to minimum value i.e 1
            numberPicker.setMaxValue(30);// restricked number to maximum value i.e. 31
            numberPicker.setWrapSelectorWheel(true);

            if (offer != null) {

                headerItemCart.setText(getResources().getString(R.string.o_que_fazer_com).concat(" ").concat(offer.getTitle()));

                buyNowItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        offer.setQuantity(numberPicker.getValue());

                        cartItemListener.buyItem(offer);

                        dismiss();

                    }
                });

                deletItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        cartItemListener.deleteItem(offer);

                        dismiss();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });


            } else {
                dismiss();
            }

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

    public interface CartItemListener {
        void deleteItem(Offer offer);
        void buyItem(Offer offer);
    }
}
