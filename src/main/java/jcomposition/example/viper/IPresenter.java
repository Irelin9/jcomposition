package jcomposition.example.viper;

import jcomposition.api.annotations.Bind;
import jcomposition.api.annotations.Composition;
import jcomposition.example.generics.ExampleClass;

@Bind(Presenter.class)
@Composition(name = "PresenterGenerated")
public interface IPresenter<V extends ExampleClass> extends IView<V>{
    void finish();
    void restore();
}
