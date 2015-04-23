package io.ichi_go.ichigo;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Russell on 4/23/2015.
 */
public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private List<Event> data = Collections.emptyList();

    public EventListAdapter(Context context, List<Event> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.event_list_row, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        Event current = data.get(i);
        viewHolder.name.setText(current.getName());
        //viewHolder.description.setText(current.getDescription());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        //TextView description;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.event_list_name);
            //description = (TextView) itemView.findViewById(R.id.event_list_description);
        }
    }

}
