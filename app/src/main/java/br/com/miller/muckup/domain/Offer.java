package br.com.miller.muckup.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Objects;

public class Offer implements Parcelable {

    private String id;
    private double value;
    private int quantity;
    private String description;
    private String title;
    private String type;
    private String city;
    private double sendValue;
    private String image;
    private String indication;
    private String noIndication;
    private String active;
    private String store;
    private String storeId;
    private String departamentId;
    private String cartId;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getSendValue() {
        return sendValue;
    }

    public void setSendValue(double sendValue) {
        this.sendValue = sendValue;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public String getNoIndication() {
        return noIndication;
    }

    public void setNoIndication(String noIndication) {
        this.noIndication = noIndication;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getDepartamentId() {
        return departamentId;
    }

    public void setDepartamentId(String departamentId) {
        this.departamentId = departamentId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeDouble(this.value);
        dest.writeInt(this.quantity);
        dest.writeString(this.description);
        dest.writeString(this.title);
        dest.writeString(this.type);
        dest.writeString(this.city);
        dest.writeDouble(this.sendValue);
        dest.writeString(this.image);
        dest.writeString(this.indication);
        dest.writeString(this.noIndication);
        dest.writeString(this.active);
        dest.writeString(this.store);
        dest.writeString(this.storeId);
        dest.writeString(this.departamentId);
        dest.writeString(this.cartId);
    }

    public Offer() {
    }

    public Offer(Object o){

        if(o instanceof HashMap){

            HashMap hashMap = (HashMap) o;

            this.id = Objects.requireNonNull(hashMap.get("id")).toString();
            this.value = Double.valueOf(Objects.requireNonNull(hashMap.get("value")).toString());
            this.quantity = Integer.valueOf(Objects.requireNonNull(hashMap.get("quantity")).toString());
            this.description = Objects.requireNonNull(hashMap.get("description")).toString();
            this.title = Objects.requireNonNull(hashMap.get("title")).toString();
            this.type = Objects.requireNonNull(hashMap.get("type")).toString();
            this.city = Objects.requireNonNull(hashMap.get("city")).toString();
            this.sendValue = Double.valueOf(Objects.requireNonNull(hashMap.get("sendValue")).toString());
            this.image = Objects.requireNonNull(hashMap.get("image")).toString();
            this.indication = Objects.requireNonNull(hashMap.get("indication")).toString();
            this.noIndication = Objects.requireNonNull(hashMap.get("noIndication")).toString();
            this.active = Objects.requireNonNull(hashMap.get("active")).toString();
            this.store = Objects.requireNonNull(hashMap.get("store")).toString();
            this.storeId = Objects.requireNonNull(hashMap.get("storeId")).toString();
            this.departamentId = Objects.requireNonNull(hashMap.get("departamentId")).toString();

        }
    }

    protected Offer(Parcel in) {
        this.id = in.readString();
        this.value = in.readDouble();
        this.quantity = in.readInt();
        this.description = in.readString();
        this.title = in.readString();
        this.type = in.readString();
        this.city = in.readString();
        this.sendValue = in.readDouble();
        this.image = in.readString();
        this.indication = in.readString();
        this.noIndication = in.readString();
        this.active = in.readString();
        this.store = in.readString();
        this.storeId = in.readString();
        this.departamentId = in.readString();
        this.cartId = in.readString();
    }

    public static final Parcelable.Creator<Offer> CREATOR = new Parcelable.Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel source) {
            return new Offer(source);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };

}
