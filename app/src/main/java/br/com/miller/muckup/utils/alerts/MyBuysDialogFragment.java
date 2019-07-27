package br.com.miller.muckup.utils.alerts;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import br.com.miller.muckup.R;
import br.com.miller.muckup.domain.Buy;
import br.com.miller.muckup.domain.Status;
import br.com.miller.muckup.store.adapters.ProductsDetailsRecyclerAdapter;

public class MyBuysDialogFragment extends DialogFragment {

    private static final String DIALOG_TAG = MyBuysDialogFragment.class.getName();

    private ProductsDetailsRecyclerAdapter productsDetailsRecyclerAdapter;

    private MyBuysDialogListener myBuysDialogListener;

    private Context context;

    private Buy buy;

    public static MyBuysDialogFragment newInstance(Bundle bundle){

        MyBuysDialogFragment myBuysDialogFragment = new MyBuysDialogFragment();

        myBuysDialogFragment.setArguments(bundle);

        return myBuysDialogFragment;
    }

    public void setMyBuysDialogListener(MyBuysDialogListener myBuysDialogListener){
        this.myBuysDialogListener = myBuysDialogListener;
    }

    public void setProductAdapter(Buy buy, Context context){

        this.buy = buy;

        productsDetailsRecyclerAdapter = new ProductsDetailsRecyclerAdapter(context);
        this.context = context;
        productsDetailsRecyclerAdapter.setArray(buy.getOffers());
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.layout_alert_my_buy, container, false);

        if(getArguments() != null){

            RecyclerView recyclerMyBuy = view.findViewById(R.id.recycler_my_buy);

            TextView header = view.findViewById(R.id.header_buy);
            TextView address = view.findViewById(R.id.address_my_buy);
            TextView totalValue = view.findViewById(R.id.total_value_my_buy);
            TextView storeName = view.findViewById(R.id.store_name);
            TextView sendValue = view.findViewById(R.id.send_value);
            TextView sumValue = view.findViewById(R.id.sum_value);
            TextView status = view.findViewById(R.id.status_my_buy);
            Button close = view.findViewById(R.id.close);
            Button evaluate = view.findViewById(R.id.evalue);

            header.setSelected(true);

            header.setText("Compra: ".concat(buy.getId()));
            storeName.setText(buy.getStoreName());
            sendValue.setText("Taxa envio: R$ ".concat(String.format(Locale.getDefault(),"%.2f",buy.getSendValue())));
            totalValue.setText("Total produtos: R$ ".concat(String.format(Locale.getDefault(),"%.2f", buy.getTotalValue())));
            sumValue.setText("Envio + produtos: R$ ".concat(String.format(Locale.getDefault(),"%.2f", buy.getTotalValue() + buy.getSendValue())));
            address.setText("Enviado para: ".concat(buy.getAddress() != null ? buy.getAddress() : ""));
            status.setText("Status: ".concat(Status.valueOf(buy.getStatus()).getStatus()));

            status.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);

            recyclerMyBuy.setLayoutManager(linearLayoutManager);

            recyclerMyBuy.setHasFixedSize(true);

            recyclerMyBuy.setAdapter(productsDetailsRecyclerAdapter);

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            evaluate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(buy != null)
                        myBuysDialogListener.onEvaluetedItem(buy);

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

    @Override
    public void onDetach() {
        super.onDetach();

        productsDetailsRecyclerAdapter = null;
        myBuysDialogListener = null;
    }

    public interface MyBuysDialogListener {
        void onEvaluetedItem(Buy buy);
    }
}
