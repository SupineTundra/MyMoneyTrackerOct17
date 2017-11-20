package com.lofstschool.mymoneytrackeroct17;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private List<Item> items = new ArrayList<>();
    private ItemsAdapterListener listener = null;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    public void setItems (List<Item> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public void  setListener (ItemsAdapterListener listener) {
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
        holder.bind(item, position, selectedItems.get(position, false), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        } else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    int getSelectedItemCount() {
        return selectedItems.size();
    }

    List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    Item remove(int pos) {
        final Item item = items.remove(pos);
        notifyItemRemoved(pos);
        return item;
    }



    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView price;
        private Resources resources;


        ItemViewHolder(View ItemView) {
            super(ItemView);
            resources = itemView.getResources();
            name = itemView.findViewById(R.id.item_name);
            price = itemView.findViewById(R.id.item_price);

        }
        void bind (final Item item, final int position,boolean selected, final ItemsAdapterListener listener){
            String ruble = resources.getString(R.string.price, item.price);
            name.setText(item.name);
            price.setText(ruble);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item, position);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClick(item, position);
                    return true;
                }
            });
            itemView.setActivated(selected);
        }
    }

}
