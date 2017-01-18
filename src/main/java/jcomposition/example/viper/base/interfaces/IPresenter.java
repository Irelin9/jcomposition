package jcomposition.example.viper.base.interfaces;

import jcomposition.api.annotations.Bind;
import jcomposition.api.annotations.Composition;
import jcomposition.example.viper.ViewCallbacksBase;
import jcomposition.example.viper.base.Presenter;

@Bind(Presenter.class)
@Composition(name = "PresenterGenerated")
public interface IPresenter<V extends ViewCallbacksBase> extends IView<V>{
    void finish();
    void restore();
}
