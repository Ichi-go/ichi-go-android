package io.ichi_go.ichigo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import io.ichi_go.ichigo.data.model.Event;

/**
 * Created by ichigo on 4/25/15.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter{

    private Context context;
    private HashMap<String, List<Event>> channelHashmap;
    private List<String> channels;

    public ExpandableListAdapter(Context context, HashMap<String, List<Event>> channelHashmap, List<String> channels) {
        this.context = context;
        this.channelHashmap = channelHashmap;
        this.channels = channels;

    }

    @Override
    public int getGroupCount() {
        return channels.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return channelHashmap.get(channels.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return channels.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return channelHashmap.get(channels.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String groupTitle = (String) getGroup(groupPosition);
        if(convertView == null) {
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.row_expandable_list_parent, parent, false);
        }
        TextView parentText = (TextView) convertView.findViewById(R.id.parent_text);
        parentText.setText(groupTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_expandable_list_child, parent, false);
        }
        TextView childText = (TextView) convertView.findViewById(R.id.child_text);
        childText.setText(((Event) getChild(groupPosition,childPosition)).getName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
