package br.com.miller.muckup.helpers;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;

import br.com.miller.muckup.helpers.SearchHelper;
import br.com.miller.muckup.models.Address;
import br.com.miller.muckup.models.Buy;
import br.com.miller.muckup.models.Departament;
import br.com.miller.muckup.models.Offer;
import br.com.miller.muckup.models.Product;

public class HelperStore {

    public static ArrayList<Offer> getListOfBuysFromDepartaments(Context context, int id){

        ArrayList<Offer> offers = new ArrayList<>();

        for(Departament dep: getDepartaments(context)){

            if(id == dep.getId()){

                offers.addAll(dep.getOffers());
            }
        }

        return offers;
    }

    public static ArrayList<Departament> getDepartaments(Context context){

        ArrayList<Departament> departaments = new ArrayList<>();

        ArrayList<Offer> offers = SearchHelper.bdOffersFarmacia(context);

        Departament departament = new Departament();
        Departament departament1 = new Departament();

        departament.setId(0);
        departament.setName("Perfumaria");
        departament.setOffers(offers);

        departaments.add(departament);

        departament1.setId(1);
        departament1.setName("Cosméticos");
        departament1.setOffers(offers);

        departaments.add(departament1);

        return departaments;
    }

    public static ArrayList<Buy> getBuys(Context context){

        ArrayList<Buy> buys = new ArrayList<>();
        Buy buy = new Buy();
        Address address = new Address();

        address.setAddress("Travessa Independência, 35, Centro");

        buy.setId("003826517975");

        buy.setReceiverDate(Calendar.getInstance().getTime());

        buy.setDeliverDate(Calendar.getInstance().getTime());

        buy.setSolicitationDate(Calendar.getInstance().getTime());

        //buy.setAddress(address);

        buy.setProducts(SearchHelper.bd(context));

        buys.add(buy);

        return buys;
    }

}
