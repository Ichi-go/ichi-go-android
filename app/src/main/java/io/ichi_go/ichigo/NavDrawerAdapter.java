package io.ichi_go.ichigo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Russell on 2/22/2015.
 */
public class NavDrawerAdapter extends RecyclerView.Adapter<NavDrawerAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    List<NavItem> data = Collections.emptyList();
    private Context context;

    /**
     * The constructor for a navigation drawer
     * @param context the context that is creating the drawer
     * @param data the data to be displayed in the drawer
     */
    public NavDrawerAdapter(Context context, List<NavItem> data){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    /**
     * Inflates a view for a row in a navigation drawer
     * @param parent the parent of the row
     * @param viewType the type of view being created
     * @return the newly created view
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_navigation_drawer, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    /**
     * Sets a view to display a certain row
     * @param holder the holder of the view to be modified
     * @param position the position of the row the view is of
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavItem current = data.get(position);
        holder.title.setText(current.getTitle());
        holder.icon.setImageResource(current.getIconId());
    }

    /**
     * Gets the amount of rows in the navigation drawer
     * @return the amount of rows
     */
    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * The holder of a view that is used to display a row
     */
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView icon;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.list_text);
            icon = (ImageView) itemView.findViewById(R.id.list_icon);
        }
    }
}
