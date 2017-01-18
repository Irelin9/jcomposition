package jcomposition.example.viper.logic;

import jcomposition.api.annotations.Bind;
import jcomposition.api.annotations.Composition;
import jcomposition.example.generics.collections.ICollection;
import jcomposition.example.viper.RouterBase;
import jcomposition.example.viper.ViewCallbacksBase;
import jcomposition.example.viper.base.interfaces.IPresenter;
import jcomposition.example.viper.base.interfaces.IRouter;

@Bind(MainPresenter.class)
@Composition(name = "MainPresenterGenerated")
public interface IMainPresenter<V extends ViewCallbacksBase, R extends RouterBase>
    extends IPresenter<V>, IRouter<R>, ICollection<IPresenter> {
}
