package jcomposition.example.viper.base.interfaces;

import jcomposition.api.annotations.Bind;
import jcomposition.api.annotations.Composition;
import jcomposition.example.viper.base.Pager;

@Bind(Pager.class)
@Composition(name = "PagerBaseGenerated")
public interface IPager<I extends IPresenter> extends ICollection<I> {
    void loadNextPage();
    int getPageSize();
}
