package jcomposition.example.viper.base.interfaces;

import jcomposition.api.annotations.Bind;
import jcomposition.example.viper.RouterBase;
import jcomposition.example.viper.base.Router;

@Bind(Router.class)
public interface IRouter<R extends RouterBase> {
    void takeRouter(R router);
    void dropRouter(R router);
    void hasRouter(R router);
}
