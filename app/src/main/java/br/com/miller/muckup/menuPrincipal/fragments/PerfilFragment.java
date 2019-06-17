package br.com.miller.muckup.menuPrincipal.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import br.com.miller.muckup.R;
import br.com.miller.muckup.api.FirebaseBuy;
import br.com.miller.muckup.api.FirebaseCart;
import br.com.miller.muckup.api.FirebaseImage;
import br.com.miller.muckup.api.FirebaseUser;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.models.User;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PerfilFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SharedPreferences sharedPreferences;
    private FirebaseUser firebaseUser;
    private FirebaseImage firebaseImage;
    private User user;
    private ImageView imageUser;
    private TextView name, phone, address, email, countCart, countBuy;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CardView buys;
    CardView cart;

    private OnFragmentInteractionListener mListener;

    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        firebaseUser = new FirebaseUser(getContext());
        firebaseImage = new FirebaseImage(getContext());

        sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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

        bindViews();

        return view;
    }

    public void receiverUser(User user){

        if(this.isVisible()) {

            name.setText(user.getName().concat(" ").concat(user.getSurname()));
            phone.setText(user.getPhone());
            email.setText(user.getEmail());
            address.setText(user.getAddress() != null ? user.getAddress().getAddress() :
                    sharedPreferences.getString(Constants.USER_ADDRESS, ""));

            firebaseImage.downloadFirebaseImage("users", user.getCity(),
                    user.getId_firebase().concat(".jpg"),
                    imageUser);

            FirebaseBuy.getBuysCount(user.getCity(), user.getId_firebase(), countBuy);
            FirebaseCart.getCartCount(user.getCity(), user.getId_firebase(), countCart);
        }

    }

    private void bindViews(){


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

        firebaseUser.getUserData(sharedPreferences.getString(Constants.USER_CITY, ""),
                sharedPreferences.getString(Constants.USER_ID_FIREBASE, ""));

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Bundle bundle) {
        if (mListener != null) {
            mListener.onFragmentInteraction(bundle);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Bundle bundle);
    }
}
