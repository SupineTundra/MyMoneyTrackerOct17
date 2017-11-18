package com.lofstschool.mymoneytrackeroct17;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lofstschool.mymoneytrackeroct17.api.Api;

import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.lofstschool.mymoneytrackeroct17.Item.TYPE_UNKNOWN;

public class ItemsFragment extends android.support.v4.app.Fragment {

    private static final int LOADER_ITEMS = 0;
    private static final int LOADER_ADD = 1;

    private static final  String KEY_TYPE = "TYPE";

    private  String type = TYPE_UNKNOWN;

    private ItemsAdapter adapter;
    private Api api;

    public static  ItemsFragment createItemsFragment(String type){
        ItemsFragment fragment = new ItemsFragment();

        Bundle bundle = new Bundle();
        bundle.putString(ItemsFragment.KEY_TYPE, type);

        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getString(KEY_TYPE, TYPE_UNKNOWN);

        if (type.equals(TYPE_UNKNOWN)){
            throw new IllegalStateException("Unknown Fragment Type");
        }
        adapter = new ItemsAdapter();
        api = ((App) getActivity().getApplication()).getApi();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        RecyclerView recycler = view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);
        FloatingActionButton fab = view.findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(getActivity(), AddActivity.class);
               intent.putExtra (AddActivity.EXTRA_TYPE, type);
               startActivityForResult(intent, AddActivity.RC_ADD_ITEM );

            }
        });
        loadItems();
    }

    private void  loadItems(){
        getLoaderManager().initLoader(LOADER_ITEMS, null, new LoaderManager.LoaderCallbacks<List<Item>>()
        {

            @Override
            public Loader<List<Item>> onCreateLoader(int id, Bundle args) {
                return new AsyncTaskLoader<List<Item>>(getContext()) {
                    @Override
                    public List<Item> loadInBackground() {
                        try {
                            List<Item> items = api.items(type).execute().body();
                            return items;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                                                    }
                                                            }
                };
            }

            @Override
            public void onLoadFinished(Loader<List<Item>> loader, List<Item> items) {
                if (items == null) {
                    showError("Произошла ошибка");
                } else {
                    adapter.setItems(items);
                }
            }

            @Override
            public void onLoaderReset(Loader<List<Item>> loader) {

            }
        }).forceLoad();
    }

    private void  addItems() {
        getLoaderManager().restartLoader(LOADER_ADD, null, new LoaderManager.LoaderCallbacks<Result.AddResult>()

                 {

                    @Override
                    public Loader<Result.AddResult> onCreateLoader(int id, Bundle args) {
                        return null;
                    }

                    @Override
                    public void onLoadFinished(Loader<Result.AddResult> loader, Result.AddResult items) {

                    }

                    @Override
                    public void onLoaderReset(Loader<Result.AddResult> loader) {

                    }
                }).forceLoad();
    }


    private void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AddActivity.RC_ADD_ITEM && resultCode == RESULT_OK) {
            Item item = (Item) data.getSerializableExtra(AddActivity.RESULT_ITEM);
            String str = Integer.toString(item.price);
            Toast toast = Toast.makeText(getContext(),item.name+" "+str, Toast.LENGTH_LONG);
            toast.show();
        }
    }


   /* private void  loadItems(){
        new LoadItemsTask(new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case ITEMS_LOADED:
                        adapter.setItems((List<Item>)msg.obj);
                        break;

                    case ITEMS_ERROR:
                        showError((String)msg.obj);
                        break;
                }
            }
        }).start();

    }
    private static final  int ITEMS_LOADED = 0;
    private static final  int ITEMS_ERROR = 1;

    private  class LoadItemsTask implements  Runnable{
        private  Thread thread;
        private Handler handler;

        public LoadItemsTask (Handler handler) {
            thread = new Thread (this );
            this.handler = handler;
        }

        public void start(){
            thread.start();
        }

        @Override
        public void run() {
            try {
                List<Item> items = api.items(type).execute().body();
                handler.obtainMessage(ITEMS_LOADED, items).sendToTarget();
            } catch (Exception e) {
                e.printStackTrace();
                handler.obtainMessage(ITEMS_ERROR, e.getMessage()).sendToTarget();
            }
        }
    }*/


}