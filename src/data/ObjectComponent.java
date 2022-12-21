package data;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public abstract class ObjectComponent {
    private static final HashMap<String, Constructor<?>> componentRegistry = new HashMap<>();

    public final String id;
    public GameObject gameObject;

    public ObjectComponent() {
        id = getClass().getSimpleName();
    }

    public abstract void init();
    public abstract void start();
    public abstract void update();

    public static void registerComponent(String id, Constructor<?> constructor) {
        componentRegistry.put(id, constructor);
    }

    public static ObjectComponent createComponent(String id) {
        try {
            return (ObjectComponent) componentRegistry.get(id).newInstance();
        }
        catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
