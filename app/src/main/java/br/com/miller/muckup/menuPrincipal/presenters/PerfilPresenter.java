package br.com.miller.muckup.menuPrincipal.presenters;

import android.content.SharedPreferences;
import android.graphics.Bitmap;

import br.com.miller.muckup.R;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.menuPrincipal.models.PerfilModel;
import br.com.miller.muckup.menuPrincipal.tasks.PerfilTasks;
import br.com.miller.muckup.domain.Address;
import br.com.miller.muckup.domain.User;
public class PerfilPresenter implements PerfilTasks.Model, PerfilTasks.View{

    private PerfilTasks.Presenter presenter;
    private PerfilModel model;

    public PerfilPresenter(PerfilTasks.Presenter presenter) {
        this.presenter = presenter;
        model = new PerfilModel(this);

    }

    @Override
    public void getPerfilSuccess(User user) {
        presenter.getPerfilSuccess(user);
        model.getBuysCount(user.getCity(), user.getId_firebase());
        model.getCartCount(user.getCity(), user.getId_firebase());
    }

    @Override
    public void getPerfilFaield() { presenter.getPerfilFaield(); }

    @Override
    public void onPerfilUpdatedFail() { presenter.onPerfilUpdatedFail(); }

    @Override
    public void onPerfilUpdatedSuccess(User user) { presenter.onPerfilUpdatedSuccess(user); }

    public void editSharedPreferences(User user, SharedPreferences sharedPreferences){

        SharedPreferences.Editor editor= sharedPreferences.edit();

        editor.putString(Constants.USER_ADDRESS, user.getAddress().getAddress());

        editor.apply();

    }

    @Override
    public void onImageUpdateSuccess(Bitmap bitmap) {presenter.onImageUpdateSuccess(bitmap); }

    @Override
    public void onImageUpdateFailed() { presenter.onImageUpdateFailed(); }

    @Override
    public void onBuyCountSuccess(int buyCount) { presenter.onBuyCountSuccess(buyCount);}

    @Override
    public void onBuyCountFailded() { presenter.onBuyCountFailded();}

    @Override
    public void onCartCountSuccess(int cartCount) { presenter.onCartCountSuccess(cartCount);}

    @Override
    public void onCartCountFailed() { presenter.onCartCountFailed();}

    @Override
    public void getImageFromMemory() { }

    @Override
    public void getCartCount(String userCity, String userId) { model.getCartCount(userCity, userId);}

    @Override
    public void updateImage(User user, Bitmap image) { model.updateImage(user, image);}

    @Override
    public void getUserDate(String city, String firebaseId) {
        model.getUserData(city, firebaseId);
    }

    @Override
    public void updateUser(User user, Object o, int type) {

        switch (type){

            case R.id.phone_perfil:{

                if(o instanceof String){

                    user.setPhone((String) o);
                }

            }
            break;

            case R.id.email_perfil:{
                if(o instanceof String){

                    user.setEmail((String) o);
                }
            }
            break;

            case R.id.name_perfil:{

                if(o instanceof String){

                    String[] name = ((String) o).split(" ");

                    user.setName(name[0]);

                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 1; i < name.length; i++) {
                        stringBuilder.append(" ".concat(name[i]));
                    }

                    user.setSurname(stringBuilder.toString());

                }
            }

            break;

            case R.id.address_perfil:{

                Address address = new Address();

                address.setAddress((String) o );
                address.setCity(user.getCity());

                user.setAddress(address);

            }
            break;

            default:
                break;
        }

        model.updateUser(user);

    }

}
