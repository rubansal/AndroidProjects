package com.codingblocks.recorder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class ViewRecordingAdapter extends RecyclerView.Adapter<ViewRecordingAdapter.ViewRecordingViewHolder> {

    private Context context;
    private DBHelper dbHelper;

    public ViewRecordingAdapter(Context context) {
        this.context=context;
        dbHelper=new DBHelper(context);
    }

    @Override
    public ViewRecordingAdapter.ViewRecordingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li= (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=li.inflate(R.layout.item_row,parent,false);
        return new ViewRecordingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewRecordingAdapter.ViewRecordingViewHolder holder, int position) {
        Record item=getItem(position);
        holder.name.setText(item.getName());
        long itemDuration=item.getLength();
        long minutes= TimeUnit.MILLISECONDS.toMinutes(itemDuration);
        long seconds= TimeUnit.MILLISECONDS.toSeconds(itemDuration)-
                TimeUnit.MINUTES.toSeconds(minutes);
        holder.length.setText(String.format("%02d:%02d",minutes,seconds));
        String formattedDate=formateDate(item.getDate(),"LLL dd/yyyy hh:mm a");
        holder.date.setText(formattedDate);
    }

    @Override
    public int getItemCount() {
        return dbHelper.getCount();
    }

    public class ViewRecordingViewHolder extends RecyclerView.ViewHolder {
        TextView name,length,date;

        public ViewRecordingViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            length=itemView.findViewById(R.id.length);
            date=itemView.findViewById(R.id.date);
        }
    }

    public Record getItem(int position){
        return dbHelper.getItemAt(position);
    }

    private String formateDate(long milliseconds,String dateFormat){
        SimpleDateFormat formatter=new SimpleDateFormat(dateFormat);
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return formatter.format(calendar.getTime());
    }
}
