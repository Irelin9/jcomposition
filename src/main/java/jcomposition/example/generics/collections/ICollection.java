package jcomposition.example.generics.collections;


import java.util.List;

import jcomposition.api.annotations.Bind;

@Bind(CollectionBase.class)
public interface ICollection<I extends Object> {
    I getItemAt(int index);

    List<I> getItems();

    void setItems(List<I> items);

    boolean isLoading();

    void onItemSelected(int index);

    void reloadData();
}
