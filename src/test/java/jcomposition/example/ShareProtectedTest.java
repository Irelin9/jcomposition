package jcomposition.example;

import org.junit.Test;

import jcomposition.example.viper.base.Presenter;
import jcomposition.example.viper.ViewCallbacksBase;

public class ShareProtectedTest {
    private final class TestView implements ViewCallbacksBase {}

    @Test
    public void testMethods() {
        Presenter<ViewCallbacksBase> presenter = new Presenter<ViewCallbacksBase>();
        presenter.takeView(new TestView());
    }
}
