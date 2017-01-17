package jcomposition.example.generics.collections;

import jcomposition.api.annotations.Bind;
import jcomposition.api.annotations.Composition;

@Bind(PagerBase.class)
//@Composition(name = "PagerBaseGenerated")
public interface IPager<T extends Object> extends ICollection<T> {
    void loadNextPage();
    int getPageSize();
}
