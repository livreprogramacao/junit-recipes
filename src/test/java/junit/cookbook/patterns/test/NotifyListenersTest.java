package junit.cookbook.patterns.test;

import junit.cookbook.patterns.Observable;
import junit.cookbook.patterns.Observer;
import junitx.framework.PrivateTestCase;
import org.easymock.MockControl;

public class NotifyListenersTest extends PrivateTestCase {
    public NotifyListenersTest(String name) {
        super(name);
    }

    public void testNotifyListeners() throws Exception {
        MockControl observerControl = MockControl
                .createControl(Observer.class);
        Observer observer = (Observer) observerControl
                .getMock();

        observer.update();
        observerControl.setVoidCallable();

        observerControl.replay();

        Observable observable = new Observable();
        observable.attach(observer);

        invoke(observable, "notifyObservers", new Object[0]);

        observerControl.verify();
    }

    public void testMultipleListeners() throws Exception {
        MockControl observerControl = MockControl
                .createControl(Observer.class);
        Observer observer = (Observer) observerControl
                .getMock();

        observer.update();
        observerControl.setVoidCallable(5);

        observerControl.replay();

        Observable observable = new Observable();
        for (int i = 0; i < 5; i++)
            observable.attach(observer);

        invoke(observable, "notifyObservers", new Object[0]);

        observerControl.verify();
    }
}