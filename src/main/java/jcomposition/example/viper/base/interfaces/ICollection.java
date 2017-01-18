package jcomposition.example.viper.base.interfaces;


import java.util.List;

import jcomposition.api.annotations.Bind;
import jcomposition.example.viper.base.Collection;

@Bind(Collection.class)
public interface ICollection<I extends IPresenter> {
    I getItemAt(int index);
    List<I> getItems();
    void setItems(List<I> items);
    boolean isLoading();
    void setLoading(boolean isLoading);
    void onItemSelected(int index);
    void reloadData();
}
