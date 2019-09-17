package tpgus.example.com.rros;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private ArrayList<menuListItem> arrayList = new ArrayList<>();
    private OnMenuClickListener listener;

    public MenuAdapter(ArrayList<menuListItem> arrayList, OnMenuClickListener listener) {
        this.listener = listener;
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("CreateViewHolder", "in onCreateViewHolder");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_layout,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Log.v("BindViewHolder", "in onBindViewHolder");
        menuListItem contact = arrayList.get(position);
        holder.name.setText(contact.getmName());
        holder.number.setText(contact.getmNum());
        holder.context.setText(contact.getmContent());

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMenuViewItemClicked(position,view.getId());
                //RecyclerViewOnClick.onItemClick(v, viewHolder.getAdapterPosition());



            }
        });
    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, context, number;
        public MyViewHolder(View itemView) {
            super(itemView);
            Log.v("ViewHolder","in View Holder");
            name = itemView.findViewById(R.id.textView);
            number = itemView.findViewById(R.id.textView2);
            context = itemView.findViewById(R.id.textView3);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onMenuViewItemClicked(getAdapterPosition(),view.getId());
                }
            });

        }


    }
}