package br.com.miller.muckup.menuPrincipal.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import br.com.miller.muckup.R;

public class SearchHint extends CursorAdapter {

    private LayoutInflater cursorInflater;

    public SearchHint(Context context, Cursor c, int flags) {
        super(context, c, flags);

        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.list_suggestion_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView textViewTitle = view.findViewById(R.id.title);
        String title = cursor.getString(cursor.getPosition());
        textViewTitle.setText(title);

    }
}
