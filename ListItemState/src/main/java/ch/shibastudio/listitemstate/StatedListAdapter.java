package ch.shibastudio.listitemstate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shibakaneki on 16.11.13.
 */
public class StatedListAdapter extends BaseAdapter{

    static class StatedListItemHolder{
        TextView tvLabel;
        CheckBox cbState;
        RelativeLayout container;
        ObserverFlipper flipper;
    }

    private Context mCtx;
    private ArrayList<StatedListItem> mItems;
    private LayoutInflater mInflater;

    private CompoundButton.OnCheckedChangeListener mCheckListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int pos = Integer.parseInt(buttonView.getTag().toString());
            if(mItems.get(pos).checked != isChecked){
                mItems.get(pos).checked = isChecked;
                notifyDataSetChanged();
            }
        }
    };

    public StatedListAdapter(Context c){
        mCtx = c;
        mItems = new ArrayList<StatedListItem>();
        mInflater = LayoutInflater.from(c);
    }

    public void addItem(StatedListItem item){
        mItems.add(item);
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<StatedListItem> items){
        for(StatedListItem it : items){
            mItems.add(it);
        }
        notifyDataSetChanged();
    }

    public void clear(){
        mItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StatedListItemHolder holder = null;
        if(null == convertView){
            convertView = mInflater.inflate(R.layout.row_statedlist, null);
            holder = new StatedListItemHolder();
            holder.tvLabel = (TextView)convertView.findViewById(R.id.label);
            holder.cbState = (CheckBox)convertView.findViewById(R.id.checkbox);
            holder.container = (RelativeLayout)convertView.findViewById(R.id.row_container);
            holder.flipper = (ObserverFlipper)convertView.findViewById(R.id.flipper);
            convertView.setTag(holder);
        }else{
            holder = (StatedListItemHolder)convertView.getTag();
        }

        holder.cbState.setTag(position);
        holder.tvLabel.setText(mItems.get(position).label);
        holder.cbState.setChecked(mItems.get(position).checked);
        holder.cbState.setOnCheckedChangeListener(mCheckListener);
        holder.container.setBackgroundResource(mItems.get(position).checked ? R.drawable.selector_studied : R.drawable.selector_statedlist);
        holder.flipper.flip(ModeController.getInstance().mode());

        return convertView;
    }
}
