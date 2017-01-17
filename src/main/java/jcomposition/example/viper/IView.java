package jcomposition.example.viper;

import jcomposition.api.annotations.Bind;
import jcomposition.example.generics.ExampleClass;

@Bind(View.class)
public interface IView<V extends ExampleClass> {
    void takeView(V view);
    void dropView(V view);
    void hasView(V view);
}
