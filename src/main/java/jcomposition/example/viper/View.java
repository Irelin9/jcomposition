package jcomposition.example.viper;


import jcomposition.api.annotations.ShareProtected;
import jcomposition.example.generics.ExampleClass;


public class View<V extends ExampleClass> implements IView<V> {
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
