package jcomposition.example.viper.base;

import jcomposition.example.viper.RouterBase;
import jcomposition.example.viper.base.interfaces.IRouter;

public class Router<R extends RouterBase> implements IRouter<R> {
    @Override
    public void takeRouter(R router) {

    }

    @Override
    public void dropRouter(R router) {

    }

    @Override
    public void hasRouter(R router) {

    }
}
