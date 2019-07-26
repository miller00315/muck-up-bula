package br.com.miller.muckup.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Departament implements Parcelable {

    private String title;
    private String id;
    private String idStore;
    private ArrayList<Offer> offers;
    private String city;

    public void setCity(String city) { this.city = city; }

    public String getCity() { return city; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdStore() {
        return idStore;
    }

    public void setIdStore(String idStore) {
        this.idStore = idStore;
    }

    public ArrayList<Offer> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<Offer> offers) {
        this.offers = offers;
    }

    public Departament(Object o){

        if(o instanceof HashMap){

            HashMap hashMap = (HashMap) o;

            this.city = Objects.requireNonNull(hashMap.get("city")).toString();

            if(hashMap.get("idStore") != null)
            this.idStore = Objects.requireNonNull(hashMap.get("idStore")).toString();
            this.title = Objects.requireNonNull(hashMap.get("title")).toString();
            this.id = Objects.requireNonNull(hashMap.get("id")).toString();
            this.offers = new ArrayList<>();

            if(hashMap.containsKey("offers")){

                HashMap temp = (HashMap) hashMap.get("offers");

                if(temp != null) {

                    for(Object offer : temp.values()){

                        offers.add(new Offer(offer));
                    }
                }

            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.id);
        dest.writeString(this.idStore);
        dest.writeTypedList(this.offers);
        dest.writeString(this.city);
    }

    public Departament() {
    }

    protected Departament(Parcel in) {
        this.title = in.readString();
        this.id = in.readString();
        this.idStore = in.readString();
        this.offers = in.createTypedArrayList(Offer.CREATOR);
        this.city = in.readString();
    }

    public static final Parcelable.Creator<Departament> CREATOR = new Parcelable.Creator<Departament>() {
        @Override
        public Departament createFromParcel(Parcel source) {
            return new Departament(source);
        }

        @Override
        public Departament[] newArray(int size) {
            return new Departament[size];
        }
    };
}
