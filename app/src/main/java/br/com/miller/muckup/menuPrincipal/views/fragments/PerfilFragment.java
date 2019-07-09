package br.com.miller.muckup.menuPrincipal.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import br.com.miller.muckup.R;
import br.com.miller.muckup.api.FirebaseBuy;
import br.com.miller.muckup.api.FirebaseCart;
import br.com.miller.muckup.api.FirebaseImage;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.helpers.ImageHelper;
import br.com.miller.muckup.menuPrincipal.presenters.PerfilPresenter;
import br.com.miller.muckup.menuPrincipal.tasks.PerfilTasks;
import br.com.miller.muckup.models.User;
import br.com.miller.muckup.utils.AlertDialogUtils;

public class PerfilFragment extends Fragment implements PerfilTasks.Presenter, AlertDialogUtils.AltertDialogUtilsTask {

    private SharedPreferences sharedPreferences;
    private PerfilPresenter perfilPresenter;
    private FirebaseImage firebaseImage;
    private ImageView imageUser, buttonEditImage;
    private AlertDialogUtils alertDialogUtils;
    private TextView name, phone, address, email, countCart, countBuy;
    private User user;

    CardView buys;
    CardView cart;

    private OnFragmentInteractionListener mListener;

    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        perfilPresenter = new PerfilPresenter(this);
        alertDialogUtils = new AlertDialogUtils(getContext(),this);
        firebaseImage = new FirebaseImage(getContext());
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


            }
        });

        buys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putInt("id", 3);
                mListener.onFragmentInteraction(bundle);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", 4);
                mListener.onFragmentInteraction(bundle);
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onItemPressed(v, phone.getText());
            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemPressed(v, address.getText());
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemPressed(v, email.getText());
            }
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemPressed(v, name.getText());
            }
        });

        buttonEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putInt("id", 5);

                mListener.onFragmentInteraction(bundle);

            }
        });

    }

    public void onItemPressed(View v, Object o) {

        ViewGroup viewGroup = Objects.requireNonNull(getActivity()).findViewById(android.R.id.content);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_alert_edit_perfil, viewGroup, false);

        alertDialogUtils.createAlertEditText(view, o, v.getId());
    }

    public void setView(User user) {

        if(user != null) {

            this.user = user;

            if (this.isVisible()) {

                name.setText(user.getName().substring(0, 1).toUpperCase().concat(user.getName().substring(1)).concat(user.getSurname().substring(0, 1).toUpperCase().concat(user.getSurname().substring(1))));
                phone.setText(user.getPhone());
                email.setText(user.getEmail());
                address.setText(user.getAddress() != null ? user.getAddress().getAddress() : sharedPreferences.getString(Constants.USER_ADDRESS, ""));

                firebaseImage.downloadFirebaseImage("users", user.getCity(), user.getId_firebase().concat(".jpg"), imageUser);
                FirebaseBuy.getBuysCount(user.getCity(), user.getId_firebase(), countBuy);
                FirebaseCart.getCartCount(user.getCity(), user.getId_firebase(), countCart);
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

        ViewGroup viewGroup = Objects.requireNonNull(getActivity()).findViewById(android.R.id.content);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_alert_image, viewGroup, false);

        ImageView imageView = view.findViewById(R.id.image_memory);

        ImageHelper.setImageFromMemory(data, getActivity(), imageView);

        alertDialogUtils.creatAlertImageView(view, 10 );

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
    public void getPerfilSuccess(User user) {
        setView(user);
    }

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
    public void onAlertPositive(Object o, int type) {

        if(o != null){

            if(o instanceof String)
                perfilPresenter.updateUser(this.user, o, type);
            else if (o instanceof Bitmap)
                perfilPresenter.updateImage(this.user, (Bitmap) o);

        }else{
            Toast.makeText(getContext(), "Erro ao tratar dados, tente novamente", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onAlertNeutral(Object o, int type) {

    }

    @Override
    public void onAlertNegative() {

    }

    public interface OnFragmentInteractionListener { void onFragmentInteraction(Bundle bundle);}
}
