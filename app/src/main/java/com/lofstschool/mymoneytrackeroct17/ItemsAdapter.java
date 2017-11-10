package com.lofstschool.mymoneytrackeroct17;

import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private List <Item> Items = new ArrayList<>();

    public ItemsAdapter() {
        Items.add(new Item("Молоко", 35));
        Items.add(new Item("Зубная паста", 1500));
        Items.add(new Item("Мясо", 200));
        Items.add(new Item("Телек", 1000));
        Items.add(new Item("ТВ приставка", 550));
        Items.add(new Item("Молоко", 35));
        Items.add(new Item("Зубная паста", 1500));
        Items.add(new Item("Мясо", 200));
        Items.add(new Item("Телек", 1000));
        Items.add(new Item("ТВ приставка", 550));
        Items.add(new Item("Молоко", 35));
        Items.add(new Item("Зубная паста", 1500));
        Items.add(new Item("Мясо", 200));
        Items.add(new Item("Телек", 1000));
        Items.add(new Item("ТВ приставка", 550));
        Items.add(new Item("Молоко", 35));
        Items.add(new Item("Зубная паста", 1500));
        Items.add(new Item("Мясо", 200));
        Items.add(new Item("Телек", 1000));
        Items.add(new Item("ТВ приставка", 550));

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent,
        false );
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.price.setText(String.valueOf(Items.get(position).getPrice()));
        holder.name.setText(Items.get(position).getName());
    }

    @Override
    public int getItemCount() {

        return Items.size();
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
            name.setText(item.getName());
            //price.setText(String.valueOf(item.getPrice()));
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
            stringBuilder.append(String.valueOf(item.getPrice()));
            stringBuilder.append("&#8381;");
            price.setText(stringBuilder);
        }
    }

}
