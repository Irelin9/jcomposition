package jcomposition.example.viper.base;


import jcomposition.example.viper.PresenterGenerated;
import jcomposition.example.viper.ViewCallbacksBase;

public class Presenter<V extends ViewCallbacksBase> extends PresenterGenerated<V> {
    @Override
    public void finish() {

    }

    @Override
    public void restore() {

    }

    @Override
    public void takeView(V view) {
        super.takeView(view);
    }

    /*@Override
    protected void onTakeView(V view) {
        super.onTakeView(view);
    }*/
}
