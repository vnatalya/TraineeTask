package adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.natalya.task.AppContext;
import com.example.natalya.task.MainActivity;
import com.example.natalya.task.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import bean.User;
import helpers.CircleTransform;

/**
 * Created by Natalya on 04/03/16.
 */
public class ListAdapter  extends ArrayAdapter<User> {
    private Context context;
    private int layoutResourceId;
    public List<User> data;

    public ListAdapter(Context context, int layoutResourceId, List<User> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        UserHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new UserHolder();
            holder.pictureView = (ImageView)row.findViewById(R.id.PictureImageView);
            holder.nameView = (TextView)row.findViewById(R.id.NameTextView);
            holder.titleView = (TextView)row.findViewById(R.id.TitleTetxView);
            holder.textView = (TextView)row.findViewById(R.id.TexttextView);
            holder.button = (ImageButton)row.findViewById(R.id.starButton);

            row.setTag(holder);
        }
        else
        {
            holder = (UserHolder)row.getTag();
        }

        User user = data.get(position);
        holder.nameView.setText(user.nameUrl);
        holder.titleView.setText(user.titleUrl);
        holder.textView.setText(user.textUrl);
        Picasso.with(context)
                .load(user.pictureUrl)
                .resize(250, 200)
                .transform(new CircleTransform())
                .into(holder.pictureView);
        return row;
    }

    static class UserHolder
    {
        ImageView pictureView;
        TextView nameView;
        TextView titleView;
        TextView textView;
        ImageButton button;
    }
}
