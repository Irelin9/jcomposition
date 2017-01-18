package jcomposition.example.viper.base;

import java.util.ArrayList;
import java.util.List;

import jcomposition.example.viper.base.interfaces.ICollection;
import jcomposition.example.viper.base.interfaces.IPresenter;


public class Collection<I extends IPresenter> implements ICollection<I> {
    private List<I> items = new ArrayList<I>();
    private boolean isLoading;

    @Override
    public I getItemAt(int index) {
        return items.get(index);
    }

    @Override
    public List<I> getItems() {
        return items;
    }

    @Override
    public void setItems(List<I> items) {
        this.items = items;
    }

    @Override
    public boolean isLoading() {
        return isLoading;
    }

    @Override
    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    @Override
    public void onItemSelected(int index) {

    }

    @Override
    public void reloadData() {

    }
}
