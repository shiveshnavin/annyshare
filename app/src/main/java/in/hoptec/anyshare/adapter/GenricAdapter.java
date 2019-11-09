package in.hoptec.anyshare.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.hoptec.anyshare.R;

import java.util.List;



public class GenricAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<T> feedItemList;
    private Context mContext;

    public GenricAdapter(Context context, List<T> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_feed,viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        final int pos=viewHolder.getAdapterPosition();
        final T item=feedItemList.get(pos);
    }



    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder
    {

        public TextView title;
        public ImageView del,icon;
        public View base;
        public LinearLayout root;
        public TextView date,desc;


        public CustomViewHolder(View v) {
            super(v);

            base=v;
            title =(TextView) base.findViewById(R.id.name);
            date=(TextView) base.findViewById(R.id.btm_t0);
            desc=(TextView) base.findViewById(R.id.btm_t1);
            root=(LinearLayout) base.findViewById(R.id.root);
            del=(ImageView) base.findViewById(R.id.del);
            icon=(ImageView) base.findViewById(R.id.icon);



        }
    }

    public void click(int pos, T cat)
    {





    }


    public void clickLong(int pos)
    {


    }







}

