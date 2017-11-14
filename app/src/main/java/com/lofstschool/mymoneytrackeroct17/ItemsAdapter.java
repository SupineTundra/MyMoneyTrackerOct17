package com.lofstschool.mymoneytrackeroct17;

import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private List<Item> items = new ArrayList<>();

    public void setItems (List<Item> items){
        this.items = items;
        notifyDataSetChanged();
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
        private    TextView price;

        ItemViewHolder(View ItemView) {


            super(ItemView);
            name = itemView.findViewById(R.id.item_name);
            price = itemView.findViewById(R.id.item_price);
        }
        void bind (Item item){
            name.setText(item.name);
            SpannableStringBuilder sB = new SpannableStringBuilder();
            sB.append(String.valueOf(item.price));
            sB.append(" ");
            sB.append(Html.fromHtml("&#8381;"));
            price.setText(sB);
        }
    }

}
