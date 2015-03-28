package is.mjolnir.android;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class InteractiveArrayAdapter extends ArrayAdapter<MjolnirClass> {

    private final List<MjolnirClass> list;
    private final Activity context;

    public InteractiveArrayAdapter(Activity context, List<MjolnirClass> list) {
        super(context, R.layout.select_class, list);
        this.context = context;
        this.list = list;
    }

    static class ViewHolder {
        protected TextView text;
        protected CheckBox checkbox;
        protected ViewGroup layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.select_class, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.class_name);
            viewHolder.checkbox = (CheckBox) view.findViewById(R.id.class_check);
            viewHolder.layout = (ViewGroup) view.findViewById(R.id.class_layout);
            viewHolder.checkbox
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            MjolnirClass element = (MjolnirClass) viewHolder.checkbox
                                    .getTag();
                            element.setRejected(!buttonView.isChecked());
                            notifyDataSetChanged();
                        }
                    });
            viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MjolnirClass element = (MjolnirClass) viewHolder.checkbox
                            .getTag();
                    boolean checked = viewHolder.checkbox.isChecked();
                    element.setRejected(!checked);
                    viewHolder.checkbox.setChecked(!checked);
                    notifyDataSetChanged();
                }
            });
            view.setTag(viewHolder);
            viewHolder.checkbox.setTag(list.get(position));
        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(list.get(position).getName());
        boolean selected = !list.get(position).isRejected();
        holder.checkbox.setChecked(selected);

        /*
        if (selected) {
            holder.layout.setBackgroundResource(R.drawable.is_selector_focused);
        } else {
            holder.layout.setBackground(null);
        }
        */
        return view;
    }
}