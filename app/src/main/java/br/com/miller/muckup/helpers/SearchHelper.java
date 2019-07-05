package br.com.miller.muckup.helpers;

import android.content.Context;
import android.support.v4.widget.SimpleCursorAdapter;

public class SearchHelper {

    public static SimpleCursorAdapter setSuggestionCursor(Context context, String[] suggestions){

        int[] to = new int[] {android.R.id.text1};

        return new SimpleCursorAdapter(
                context,
                android.R.layout.simple_list_item_1,
                null,
                suggestions,
                to,
                0);
    }

}
