package jcomposition.example.viper.base.interfaces;

import jcomposition.api.annotations.Bind;
import jcomposition.example.viper.ViewCallbacksBase;
import jcomposition.example.viper.base.View;

@Bind(View.class)
public interface IView<V extends ViewCallbacksBase> {
    void takeView(V view);
    void dropView(V view);
    void hasView(V view);
}
