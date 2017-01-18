package jcomposition.example.viper.base;


import jcomposition.api.annotations.ShareProtected;
import jcomposition.example.viper.ViewCallbacksBase;
import jcomposition.example.viper.base.interfaces.IView;


public class View<V extends ViewCallbacksBase> implements IView<V> {
    @Override
    public void takeView(V view) {
        onTakeView(view);
    }

    @Override
    public void dropView(V view) {

    }

    @Override
    public void hasView(V view) {

    }

    @ShareProtected
    protected void onTakeView(V view) {
    }

    protected void lol() {
        System.gc();
    }
}
