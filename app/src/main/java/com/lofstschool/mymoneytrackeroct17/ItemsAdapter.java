package com.lofstschool.mymoneytrackeroct17;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private List<Item> items = new ArrayList<>();
    private ItemsAdapterListener listener = null;

    public void setItems (List<Item> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public void  setListenet (ItemsAdapterListener listener) {
      this.listener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent,
        false );
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }




    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView price;
        private Resources resources;

        ItemViewHolder(View ItemView, ItemsAdapterListener listener) {
            super(ItemView);
            resources = itemView.getResources();
            name = itemView.findViewById(R.id.item_name);
            price = itemView.findViewById(R.id.item_price);
        }
        void bind (Item item){
            String ruble = resources.getString(R.string.price, item.price);
            name.setText(item.name);
            price.setText(ruble);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

}
