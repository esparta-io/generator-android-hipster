package <%= appPackage %>.application;

import <%= appPackage %>.di.MockApplicationComponent;
import <%= appPackage %>.di.components.ApplicationComponent;

public class TestApp extends App {

    @Override
    public ApplicationComponent createComponent() {
        graph = MockApplicationComponent.Initializer.init(this);
        graph.inject(this);
        return graph;
    }

    public void recreateComponents() {
        graph = MockApplicationComponent.Initializer.init(this);
        graph.inject(this);
    }


}
