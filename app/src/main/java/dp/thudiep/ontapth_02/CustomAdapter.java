package dp.thudiep.ontapth_02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<Users> empls;
    private int layout;

    public CustomAdapter(Context context, ArrayList<Users> empls, int layout) {
        this.context = context;
        this.empls = empls;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return empls.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(layout,parent,false);
        TextView txtId = view.findViewById(R.id.userID);
        TextView firstName = view.findViewById(R.id.firstName);
        TextView lastName = view.findViewById(R.id.lastName);
        TextView email = view.findViewById(R.id.email);

        txtId.setText(empls.get(position).getId()+"");
        firstName.setText(empls.get(position).getFirstName());
        lastName.setText(empls.get(position).getLastName());
        email.setText(empls.get(position).getEmail());

        return view;
    }
}
