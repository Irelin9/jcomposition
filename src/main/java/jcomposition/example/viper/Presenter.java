package jcomposition.example.viper;


import jcomposition.example.generics.ExampleClass;

public class Presenter<V extends ExampleClass> extends PresenterGenerated<V> {
    @Override
    public void finish() {

    }

    @Override
    public void restore() {

    }

//    @Override
//    public void takeView(V view) {
//        super.takeView(view);
//        onTakeView(view);
//    }

    /*@Override
    protected void onTakeView(V view) {
        super.onTakeView(view);

        lol();
        System.out.println("");
    }*/
}
