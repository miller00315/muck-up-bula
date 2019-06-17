package br.com.miller.muckup.helpers;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import br.com.miller.muckup.R;
import br.com.miller.muckup.models.Offer;
import br.com.miller.muckup.models.Result;
import br.com.miller.muckup.models.Store;

public class SearchHelper {

    public static List<String> Dicas(){

        List<String> dicas = new ArrayList<>();

        dicas.add("Teste");
        dicas.add("Paracetamol");

        return dicas;
    }

    public static SimpleCursorAdapter setSuggestionCursor(Context context){

        return  new SimpleCursorAdapter(context,
                android.R.layout.simple_list_item_1,
                null,
                new String[]{"Teste", "Paracetamol"},
                new int[]{android.R.layout.simple_list_item_1},
                0);
    }

    public static ArrayList<Offer> bd(Context context){

        ArrayList<Offer> dados = new ArrayList<>();

        Resources resources = context.getResources();
        Uri uri = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(R.mipmap.medicamenot))
                .appendPath(resources.getResourceTypeName(R.mipmap.medicamenot))
                .appendPath(resources.getResourceEntryName(R.mipmap.medicamenot))
                .build();

        Offer result = new Offer();
        Offer result1 = new Offer();
        Offer result2 = new Offer();

        result.setDescription("Farmácia do dia");
        result.setId(0);
        result.setTitle("Paracetamol");
        result.setSendValue(3.00);
        result.setValue(5.00);
        result.setImage("");
        result.setIndication("Dor de cabeça");
        result.setNoIndication("Dengue");
        result.setActive("Acído acetilsalicilico");
        result.setStore("Farmácia do dia");
        result.setStoreId(0);

        dados.add(result);

        result1.setDescription("Farmácia bom preço");
        result1.setId(1);
        result1.setTitle("Paracetamol");
        result1.setSendValue(2.90);
        result1.setValue(6.50);
        result1.setImage("");
        result1.setIndication("Dor de cabeça");
        result1.setNoIndication("Dengue");
        result1.setActive("Acído acetilsalicilico");
        result1.setStore("Farmácia bom preço");
        result1.setStoreId(0);

        dados.add(result1);

        result2.setDescription("Farmácia Legal");
        result2.setId(2);
        result2.setTitle("Paracetamol");
        result2.setSendValue(3.00);
        result2.setValue(4.00);
        result2.setImage("");
        result2.setIndication("Dor de cabeça");
        result2.setNoIndication("Dengue");
        result2.setActive("Acído acetilsalicilico");
        result2.setStore("Farmácia Legal");
        result2.setStoreId(0);

        dados.add(result2);

        return  dados;
    }


    public static ArrayList<Offer> bdOffersFarmacia(Context context){

        ArrayList<Offer> dados = new ArrayList<>();

        SimpleDateFormat fmt = new SimpleDateFormat("dd/mm", Locale.getDefault());

        Resources resources = context.getResources();
        Uri uri = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(R.mipmap.medicamenot))
                .appendPath(resources.getResourceTypeName(R.mipmap.medicamenot))
                .appendPath(resources.getResourceEntryName(R.mipmap.medicamenot))
                .build();

        Offer result = new Offer();
        Offer result1 = new Offer();
        Offer result2 = new Offer();

        result.setDescription("Valido até ".concat(fmt.format(Calendar.getInstance().getTime())));
        result.setId(1);
        result.setTitle("Paracetamol");
        result.setSendValue(5.90);
        result.setValue(5.00);
        result.setImage("");
        result.setIndication("Dor de cabeça");
        result.setNoIndication("Dengue");
        result.setActive("Acído acetilsalicilico");
        result.setStore("Farmácia do dia");
        result.setStoreId(0);

        dados.add(result);

        result1.setDescription("Válido até ".concat(fmt.format(Calendar.getInstance().getTime())));
        result1.setId(2);
        result1.setTitle("Paracetamol");
        result1.setSendValue(4.80);
        result1.setValue(5.00);
        result1.setImage("");
        result1.setIndication("Dor de cabeça");
        result1.setNoIndication("Dengue");
        result1.setActive("Acído acetilsalicilico");
        result1.setStore("Farmácia bom preço");
        result1.setStoreId(1);

        dados.add(result1);

        result2.setDescription("Válido até ".concat(fmt.format(Calendar.getInstance().getTime())));
        result2.setId(2);
        result2.setTitle("Paracetamol");
        result2.setSendValue(3.10);
        result2.setValue(6.00);
        result2.setImage("");
        result2.setIndication("Dor de cabeça");
        result2.setNoIndication("Dengue");
        result2.setActive("Acído acetilsalicilico");
        result2.setStore("Farmácia bom preço");
        result2.setStoreId(1);

        dados.add(result2);

        return dados;
    }

    public static ArrayList<Store> storeBd(Context context){

        ArrayList<Store> stores = new ArrayList<>();

        Resources resources = context.getResources();
        Uri uri = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(R.mipmap.medicamenot))
                .appendPath(resources.getResourceTypeName(R.mipmap.medicamenot))
                .appendPath(resources.getResourceEntryName(R.mipmap.medicamenot))
                .build();

        Store store = new Store();
        Store store1 = new Store();
        Store store2 = new Store();

        store.setDescription("A melhor da cidade");
        store.setName("Farmácia do dia");
        //store.setImage(uri);
        store.setId(0);
        store.setAddress("Rua firmino Sales, centro");
        store.setPhone("35 3826-0589");
        store.setTime("24 h");
        store.setDepartaments(HelperStore.getDepartaments(context));

        stores.add(store);

        store1.setDescription("A mais barata");
        store1.setName("Farmácia bom preço");
       // store1.setImage(uri);
        store1.setId(1);
        store1.setAddress("Rua Paulo Menicucci, centro");
        store1.setPhone("35 3821-1589");
        store1.setTime("24 h");
        store1.setDepartaments(HelperStore.getDepartaments(context));

        stores.add(store1);

        store2.setDescription("24 horas por dia");
        store2.setName("Farmácia Legal");
       // store2.setImage(uri);
        store2.setId(2);
        store2.setAddress("Rua Perreira Carvalho, centro");
        store2.setPhone("35 3822-0576");
        store2.setTime("24 h");
        store2.setDepartaments(HelperStore.getDepartaments(context));

        stores.add(store2);

        return stores;
    }



    public static ArrayList<Offer> consultarDados(String text, Context context){

        ArrayList<Offer> results = new ArrayList<>();

        for(Offer res : bd(context)){

            if(res.getTitle().toLowerCase().contains(text.toLowerCase()) ||
            res.getDescription().toLowerCase().contains(text.toLowerCase())){

                results.add(res);
            }
        }

        return results;

    }

    public static Offer consultarDados(int id, Context context){

        Offer offer = null;

        for(Offer res : bd(context)){

            if(res.getId() == id)
                offer = res;
        }

        return offer;

    }

}
