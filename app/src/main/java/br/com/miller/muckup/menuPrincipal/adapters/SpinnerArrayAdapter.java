package br.com.miller.muckup.menuPrincipal.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import br.com.miller.muckup.R;
import br.com.miller.muckup.domain.Departament;

public class SpinnerArrayAdapter  extends ArrayAdapter<Departament> {

    private ArrayList<Departament> departaments;
    private Context context;

    public SpinnerArrayAdapter(Context context, ArrayList<Departament> departaments) {
        super(context, 0, departaments);

        this.departaments = departaments;
        this.context = context;
    }

    public static final SpinnerArrayAdapter newInstance(Context context, ArrayList<Departament> departaments){

        SpinnerArrayAdapter spinnerArrayAdapter = new SpinnerArrayAdapter( context,departaments);

        return spinnerArrayAdapter;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_item_view,parent,false);
        }

        TextView textView = convertView.findViewById(R.id.departament_title);

        textView.setText(departaments.get(position).getTitle());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.spinner_item_view,parent,false);
        }

        TextView textView = convertView.findViewById(R.id.departament_title);

        textView.setText(departaments.get(position).getTitle());

        return convertView;
    }

    public ArrayList<Departament> getDepartaments() {
        return departaments;
    }

    public void setDepartaments(ArrayList<Departament> departaments) {
        this.departaments = departaments;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return departaments != null ? departaments.size() : 0;
    }
}
