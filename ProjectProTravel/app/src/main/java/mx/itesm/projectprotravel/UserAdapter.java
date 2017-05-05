package mx.itesm.projectprotravel;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
        TextView statusText=(TextView)view.findViewById(R.id.textViewID);
        ImageView img= (ImageView) view.findViewById(R.id.imageViewEstado);
        //img.setImageResource(R.drawable.);

        User user=dataSource.get(position);
        nameText.setText(user.getName());

        if(user.getStatus().equals("")||user.getStatus().equals("notify")){
            statusText.setText("Unknown");
            img.setImageResource(R.mipmap.ic_radio_button_unchecked_black_24dp);
        }else{
            statusText.setText(user.getStatus());
        }


        if(user.getStatus().equals("buy")){
            img.setImageResource(R.mipmap.ic_shopping_cart_black_24dp);
        }else if(user.getStatus().equals("bath")){
            img.setImageResource(R.mipmap.ic_bathroom24x24);
        }
        else if(user.getStatus().equals("ready")){
            img.setImageResource(R.mipmap.ic_mood_black_24dp);
        }
        else if(user.getStatus().equals("leave")){
            img.setImageResource(R.mipmap.ic_directions_run_black_24dp);
        }


        return view;
    }
}
