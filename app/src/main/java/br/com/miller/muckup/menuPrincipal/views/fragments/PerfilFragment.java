package br.com.miller.muckup.menuPrincipal.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import br.com.miller.muckup.R;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.menuPrincipal.adapters.Item;
import br.com.miller.muckup.menuPrincipal.presenters.PerfilPresenter;
import br.com.miller.muckup.menuPrincipal.tasks.PerfilTasks;
import br.com.miller.muckup.domain.User;
import br.com.miller.muckup.menuPrincipal.views.activities.MyBuys;
import br.com.miller.muckup.menuPrincipal.views.activities.MyCart;
import br.com.miller.muckup.utils.alerts.EditTextDialogFragment;
import br.com.miller.muckup.utils.alerts.ImageDialogFragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class PerfilFragment extends Fragment implements
        PerfilTasks.Presenter,
        ImageDialogFragment.ImageDialogFragmentListener,
        Item.OnAdapterInteract,
        EditTextDialogFragment.EditTextFragmentListener {

    private SharedPreferences sharedPreferences;
    private PerfilPresenter perfilPresenter;
    private ImageView imageUser, buttonEditImage;
    private TextView name, phone, address, email, countCart, countBuy;
    private User user;

    public static final String ID = PerfilFragment.class.getName();

    private CardView buys;
    private CardView cart;

    private OnFragmentInteractionListener mListener;

    public PerfilFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        perfilPresenter = new PerfilPresenter(this);
        sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        buys = view.findViewById(R.id.buy_perfil);
        cart = view.findViewById(R.id.cart_perfil);
        name = view.findViewById(R.id.name_perfil);
        phone = view.findViewById(R.id.phone_perfil);
        address = view.findViewById(R.id.address_perfil);
        email = view.findViewById(R.id.email_perfil);
        imageUser = view.findViewById(R.id.image_perfil);
        countBuy = view.findViewById(R.id.perfil_buy);
        countCart = view.findViewById(R.id.perfil_cart);
        buttonEditImage = view.findViewById(R.id.edit_image_perfil);

        bindViews();

        return view;
    }

    private void bindViews(){

        imageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                mListener.onFragmentInteraction(bundle);
            }
        });

        buys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("code", MyBuys.ID);
                mListener.onFragmentInteraction(bundle);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("code", MyCart.ID);
                mListener.onFragmentInteraction(bundle);
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putInt("view", R.layout.layout_single_edit_text_alert_fragment);

                bundle.putInt("inputType", InputType.TYPE_CLASS_PHONE);

                bundle.putString("type", Constants.USER_PHONE);

                bundle.putString("hint", "Telefone");

                bundle.putString("text", user.getPhone());

                openAlert(bundle);
            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putInt("view", R.layout.layout_single_edit_text_alert_fragment);

                bundle.putInt("inputType", InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);

                bundle.putString("type", Constants.USER_ADDRESS);

                bundle.putString("hint", "Endereço");

                bundle.putString("text", user.getAddress() != null ? user.getAddress().getAddress() : "");

                openAlert(bundle);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putInt("view", R.layout.layout_single_edit_text_alert_fragment);

                bundle.putInt("inputType", InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                bundle.putString("type", Constants.USER_EMAIL);

                bundle.putString("hint", "Email");

                bundle.putString("text", user.getEmail());

                openAlert(bundle);

            }
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                bundle.putInt("view", R.layout.layout_double_edit_text_alert_fragment);

                bundle.putString("type", Constants.USER_NAME);

                bundle.putInt("firstInputType", InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                bundle.putInt("secondInputType", InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

                bundle.putString("firstText", user.getName());

                bundle.putString("secondText", user.getSurname());

                bundle.putString("firstHint", "Nome");

                bundle.putString("secondHint", "Sobrenome");

                openAlert(bundle);
            }
        });

        buttonEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                onAdapterInteract(bundle);
            }
        });

    }

    public void openAlert(Bundle bundle) {

        EditTextDialogFragment editTextDialogFragment = EditTextDialogFragment.newInstance(bundle);

        editTextDialogFragment.setListener(this);

        editTextDialogFragment.openDialog(getFragmentManager());
    }

    public void setView(User user) {

        if(user != null) {

            this.user = user;

            if (this.isVisible()) {

                name.setText(user.getName().substring(0, 1).toUpperCase().concat(user.getName().substring(1)).concat(" ").concat(user.getSurname().substring(0, 1).toUpperCase().concat(user.getSurname().substring(1))));
                phone.setText(user.getPhone());
                email.setText(user.getEmail());
                address.setText(user.getAddress() != null ? user.getAddress().getAddress() : sharedPreferences.getString(Constants.USER_ADDRESS, ""));
                perfilPresenter.getUserImage(user);
            }
        }else
            Toast.makeText(getContext(), "Usuario", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();

        perfilPresenter.getUserDate(sharedPreferences.getString(Constants.USER_CITY, ""),
                         sharedPreferences.getString(Constants.USER_ID_FIREBASE, ""));
    }

    public void setImageAlert(Intent data){

        Bundle bundle = new Bundle();

        ImageDialogFragment imageDialogFragment = ImageDialogFragment.newInstance(bundle);

        imageDialogFragment.setListenerIntent(this, data);

        imageDialogFragment.openDialog(getFragmentManager());

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void getPerfilSuccess(User user) { setView(user); }

    @Override
    public void getPerfilFaield() { Toast.makeText(getContext(), "Erro ao obter dados do perfil, tente novamente.", Toast.LENGTH_LONG).show(); }

    @Override
    public void onPerfilUpdatedFail() { Toast.makeText(getContext(), "Erro ao atualizar o perfil, tente novamente.", Toast.LENGTH_LONG).show(); }

    @Override
    public void onPerfilUpdatedSuccess(User user) {
        setView(user);
        perfilPresenter.editSharedPreferences(user, sharedPreferences);
    }

    @Override
    public void onImageUpdateSuccess(Bitmap bitmap) { this.imageUser.setImageBitmap(bitmap); }

    @Override
    public void onImageUpdateFailed() { Toast.makeText(getContext(), "Erro ao atualizar a imagem do perfil, tente novamente.", Toast.LENGTH_LONG).show(); }

    @Override
    public void onBuyCountSuccess(int buyCount) { countBuy.setText(String.valueOf(buyCount));}

    @Override
    public void onBuyCountFailded() { countBuy.setText(String.valueOf(0)); }

    @Override
    public void onCartCountSuccess(int cartCount) { countCart.setText(String.valueOf(cartCount));}

    @Override
    public void onDownloadImgeSucess(Bitmap bitmap) { imageUser.setImageBitmap(bitmap);}

    @Override
    public void onDownloadImageFailed() { }

    @Override
    public void onCartCountFailed() { countCart.setText("0");}

    @Override
    public void onAdapterInteract(Bundle bundle) {

        bundle.putString("code", ID);

        mListener.onFragmentInteraction(bundle);

    }

    @Override
    public void onImageDialogFragmentListener(Bitmap bitmap, int result) {

        if(result == RESULT_OK){
            perfilPresenter.updateImage(user, bitmap);
        }else if(result == RESULT_CANCELED){
            Toast.makeText(getContext(), "Erro ao tratar a imagem.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onEditTextDialogFragmentResult(Bundle bundle) { perfilPresenter.updateUser(user, bundle);}


    public interface OnFragmentInteractionListener { void onFragmentInteraction(Bundle bundle);}
}
