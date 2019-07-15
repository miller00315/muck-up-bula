package br.com.miller.muckup.evaluate.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import br.com.miller.muckup.R;
import br.com.miller.muckup.evaluate.presenter.EvaluatePresenter;
import br.com.miller.muckup.evaluate.task.Task;
import br.com.miller.muckup.helpers.Constants;
import br.com.miller.muckup.domain.Buy;
import br.com.miller.muckup.domain.Evaluate;

public class EvaluateAct extends AppCompatActivity implements Task.Presenter {

    private EvaluatePresenter presenter;

    private SharedPreferences sharedPreferences;

    private Buy buy;

    private TextView nameStore;

    private RatingBar evaluate;

    private EditText message;

    private View mainLayout, layoutLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);

        Toolbar toolbar =  findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        presenter = new EvaluatePresenter(this);

        Objects.requireNonNull(getSupportActionBar()).setLogo(R.drawable.ic_icon_bula_small);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Avaliar Farmácia");

        sharedPreferences = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);

        bindViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
            finish();

        return false;
    }

    private void bindViews(){

        nameStore = findViewById(R.id.name_store);
        message = findViewById(R.id.message);
        evaluate = findViewById(R.id.evaluate);
        mainLayout = findViewById(R.id.main_layout);
        layoutLoading = findViewById(R.id.loading_layout);

        evaluate.setNumStars(5);

        recoveryBuy(sharedPreferences.getString(Constants.USER_CITY,"" ), sharedPreferences.getString(Constants.USER_ID_FIREBASE, ""), getIntent().getStringExtra("buy_id"));

    }

    public void showLoading(){

        layoutLoading.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);
    }

    public void hideLoading(){
        layoutLoading.setVisibility(View.INVISIBLE);
        mainLayout.setVisibility(View.VISIBLE);
    }

    public void evaluate(View view){
        if(buy != null)this.createEvaluate(buy, Math.round(evaluate.getRating()) , message.getText().toString()); }

    @Override
    public void evaluateBuy( Evaluate evaluate) { presenter.evaluateBuy(evaluate); }

    @Override
    public void onBuyRecoverySuccess(Buy buy) {

        this.buy = buy;

        presenter.verifyAvaliation(buy);

        nameStore.setText(buy.getStoreName());

    }

    @Override
    public void onBuyRecoveryFailed() {

        hideLoading();
        Toast.makeText(this, "Erro ao criar avaliação, tente novamente", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void buyAlreadEvaluated(Evaluate evaluate) {
        hideLoading();
        Toast.makeText(this, "Compra já avaliada", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void recoveryBuy(String city, String userId, String buyId) {
        showLoading();
        presenter.recoveryBuy(city, userId, buyId);
    }


    @Override
    public void createEvaluate(Buy buy, int value, String message) {
        showLoading();
        presenter.createEvaluate(buy, value, message, sharedPreferences.getString(Constants.USER_NAME, ""));
    }

    @Override
    public void onEvaluateCreated(Evaluate evaluate) {
        this.evaluateBuy(evaluate);
    }

    @Override
    public void onEvaluateSuccess() {
        Toast.makeText(this, "Avaliação realizada com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onEvaluateFailed() {
        hideLoading();
        Toast.makeText(this, "Erro ao realizar avaliação, tente novamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void canEvaluate() {
        hideLoading();
        Toast.makeText(this, "Pronto para avaliar.", Toast.LENGTH_SHORT).show();
    }
}
