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
 * Adapter used to set up the expandable list in the channel drawer
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

    /**
     * Gets the number of channels being loaded
     * @return the number of channels being loaded
     */
    @Override
    public int getGroupCount() {
        return channels.size();
    }

    /**
     * Gets the number of events in a channel
     * @param groupPosition the position of the channel in the list.
     * @return the number of events in the channel at position groupPosition
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return channelHashmap.get(channels.get(groupPosition)).size();
    }

    /**
     * Gets the name of the channel at a position
     * @param groupPosition the position of the channel
     * @return the String containing the channel's name
     */
    @Override
    public Object getGroup(int groupPosition) {
        return channels.get(groupPosition);
    }

    /**
     * Gets the event at a certain position
     * @param groupPosition the channel the event is located within
     * @param childPosition the position of the event within the channel
     * @return the Event at the specified location
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return channelHashmap.get(channels.get(groupPosition)).get(childPosition);
    }

    /**
     * Gets the id of the channel at the specified position
     * @param groupPosition the position of the channel in question
     * @return the id of the channel
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * Gets the id of the Event at the specified position
     * @param groupPosition the position of the channel the event is in
     * @param childPosition the position of the event in the channel
     * @return the id of the event
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * Finds out if there are stable ids
     * @return true if stable, false otherwise
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Gets a view for a channel and inflates it
     * @param groupPosition the position for the view
     * @param isExpanded whether or not the view is expanded
     * @param convertView the view being changed to our new view
     * @param parent the parent of the view being inflated
     * @return the new view
     */
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

    /**
     * Gets a view for an event and inflates it
     * @param groupPosition the channel position for the view
     * @param childPosition the position of the child within the channel
     * @param isLastChild whether the child is the last one in the group
     * @param convertView the view being changed to our new view
     * @param parent the parent of the view being inflated
     * @return the new view
     */
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

    /**
     * Determines if you can select an event
     * @param groupPosition the channel the event is within
     * @param childPosition the position of the event within the channel
     * @return whether the child is selectable
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
