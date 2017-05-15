package com.example.admin.loginsignpage;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by admin on 14/02/17.
 */

public class CustomAdapter  extends ArrayAdapter<String> {

    public CustomAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }


}
