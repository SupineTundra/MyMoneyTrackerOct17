package com.lofstschool.mymoneytrackeroct17;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lofstschool.mymoneytrackeroct17.api.AddResult;
import com.lofstschool.mymoneytrackeroct17.api.Api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.lofstschool.mymoneytrackeroct17.Item.TYPE_UNKNOWN;

public class ItemsFragment extends android.support.v4.app.Fragment {

    private static final int LOADER_ITEMS = 0;
    private static final int LOADER_ADD = 1;
    private static final int LOADER_REMOVE = 2;
    private String type = TYPE_UNKNOWN;

    private static final String KEY_TYPE = "TYPE";


    private ItemsAdapter adapter;
    private Api api;

    public android.support.v7.view.ActionMode actionMode;
    private SwipeRefreshLayout refresh;
    private List<Integer> DeletingItemList = new ArrayList<>();

    public static ItemsFragment createItemsFragment(String type) {
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

        if (type.equals(TYPE_UNKNOWN)) {
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
        actionMode = null;

        adapter.setListener(new ItemsAdapterListener() {
            @Override
            public void onItemClick(Item item, int position) {
                if (actionMode != null) {
                    toggleSelection(position);
                    actionMode.invalidate();
                }
            }

            @Override
            public void onItemLongClick(Item item, int position) {
                if (actionMode == null) {
                    actionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(actionModeCallback);
                    toggleSelection(position);
                    actionMode.invalidate();
                } else {
                    return;
                }
            }

            private void toggleSelection(int position) {
                adapter.toggleSelection(position);
            }

        });

        FloatingActionButton fab = view.findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                intent.putExtra(AddActivity.EXTRA_TYPE, type);
                startActivityForResult(intent, AddActivity.RC_ADD_ITEM);

            }
        });
        loadItems();
        refresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override   public void onRefresh() {
                loadItems();
            }
        });

    }

    private void loadItems() {
        getLoaderManager().initLoader(LOADER_ITEMS, null, new LoaderManager.LoaderCallbacks<List<Item>>() {

            @SuppressLint("StaticFieldLeak")
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
                refresh.setRefreshing(true);
                refresh.setRefreshing(false);
            }

            @Override
            public void onLoaderReset(Loader<List<Item>> loader) {

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
            addItem(item);
            //String str = Integer.toString(item.price);
            //Toast toast = Toast.makeText(getContext(), item.name + " " + str, Toast.LENGTH_LONG);
            //toast.show();
        }
    }

    private void addItem(final Item item) {
        getLoaderManager().restartLoader(LOADER_ADD, null, new
                LoaderManager.LoaderCallbacks<AddResult>() {
                    @SuppressLint("StaticFieldLeak")
                    @Override
                    public Loader<AddResult> onCreateLoader(int id, Bundle args) {
                        return new AsyncTaskLoader<AddResult>(getContext()) {
                            @Override
                            public AddResult loadInBackground() {
                                try {
                                    return api.add(item.name, item.price, item.type).execute().body();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    return null;
                                }
                            }
                        };
                    }

                    @Override
                    public void onLoadFinished(Loader<AddResult> loader, AddResult data) {
                        adapter.updateId(item, AddResult.id);
                        refresh.setRefreshing(true);
                        refresh.setRefreshing(false);
                    }

                    @Override
                    public void onLoaderReset(Loader<AddResult> loader) {

                    }
                }).forceLoad();
    }

    private void deleteItem(final Item item) {
        DeletingItemList.add(item.id);
        getLoaderManager().initLoader(LOADER_REMOVE, null, new LoaderManager.LoaderCallbacks() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public Loader onCreateLoader(int id, Bundle args) {
                return new AsyncTaskLoader<Boolean>(getContext()) {
                    @Override
                    public Boolean loadInBackground() {
                        try {
                            for (Integer id : DeletingItemList)
                                api.remove((int) id).execute().body();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        return null;
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader loader, Object data) {
            }

            @Override
            public void onLoaderReset(Loader loader) {}

        }).forceLoad();
    }

    private void removeSelectedItems() {
        for (int i = adapter.getSelectedItems().size() - 1; i >= 0; i--) {
            deleteItem(adapter.getItemByPosition(adapter.getSelectedItems().get(i)));
        }

        for (int i = adapter.getSelectedItems().size() - 1; i >= 0; i--) {
            adapter.remove(adapter.getSelectedItems().get(i));
        }
    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.items_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            String itemCount = getString(R.string.selected, adapter.getSelectedItemCount());
            actionMode.setTitle(itemCount);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_remove:
                    showDialog();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            adapter.clearSelections();
            setNullAction();
            refresh.setRefreshing(false);
        }

    };

    private void setNullAction() {
        actionMode = null;
    }

    private void  showDialog() {
        DialogInterface dialogInterface = new DialogInterface() {
            @Override
            public void onPositiveClick() {
                removeSelectedItems();
                refresh.setRefreshing(true);
                actionMode.finish();
                actionMode = null;
            }

            @Override
            public void onNegativeClick() {
                actionMode.finish();
            }
        };
        ConfirmationDialog confirmationDialog =new ConfirmationDialog();
        confirmationDialog.setListener(dialogInterface);
        confirmationDialog.show(getFragmentManager(), "Confirmation");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            loadItems();
        }

    }
}
