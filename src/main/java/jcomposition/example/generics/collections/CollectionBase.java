package jcomposition.example.generics.collections;


import java.util.ArrayList;
import java.util.List;

public class CollectionBase<I extends Object> implements ICollection<I> {
    private List<I> items = new ArrayList<I>();
    private boolean loading;

    public CollectionBase() {
         setItems(new ArrayList());
    }

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
        return loading;
    }

    protected void setLoading(boolean loading) {
        this.loading = loading;
    }

    @Override
    public void onItemSelected(int index) {

    }

    @Override
    public void reloadData() {

    }
}
