package junit.cookbook.patterns.test;

import junit.cookbook.patterns.Observable;
import junit.cookbook.patterns.Observer;
import junitx.framework.PrivateTestCase;
import junitx.framework.TestAccessException;

public class ObservableTest
        extends PrivateTestCase
        implements Observer {

    public ObservableTest(String name) {
        super(name);
    }

    public void testAttachObserver()
            throws TestAccessException {

        Observable observable = new Observable();
        observable.attach(this);

        Observer[] observers =
                (Observer[]) get(observable, "observers");

        assertTrue(arrayContains(observers, this));
    }

    private boolean arrayContains(
            Object[] objects,
            Object object) {

        for (int i = 0; i < objects.length; i++)
            if (object.equals(objects[i]))
                return true;

        return false;
    }

    public void update() {
    }
}
