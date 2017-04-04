package mx.itesm.projectprotravel;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lrocg on 04/04/17.
 */

public class UserAdapter extends BaseAdapter {

    ArrayList<User> dataSource;
    Activity activity;

    public UserAdapter(ArrayList<User> dataSource, Activity activity ){
        this.dataSource=dataSource;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view==null){
            view=activity.getLayoutInflater().inflate(R.layout.viajeros,null);

        }

        //
        TextView nameText=(TextView)view.findViewById(R.id.textViewNombre);


        User user=dataSource.get(position);
        nameText.setText(user.getName());


        return view;
    }
}
