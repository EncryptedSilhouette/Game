package data;

import components.Transform;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.HashMap;

public class GameObject {
    protected static final HashMap<String, JSONObject> objectRegistry = new HashMap<>();

    public final String id;
    protected final HashMap<String, ObjectComponent> objectComponents = new HashMap<>();

    public String name;
    protected GameObject parent;
    protected Transform transform = new Transform();

    private GameObject(String id) {
        this.id = id;
    }

    public void addComponent(ObjectComponent component) {
        objectComponents.put(component.id, component);
        component.gameObject = this;
        component.init();
    }

    public void addComponents(ObjectComponent[] components) {
        for (ObjectComponent component: components) {
            addComponent(component);
        }
    }

    public void removeComponent(String id) {
        objectComponents.remove(id);
    }

    public void removeComponents(String[] ids) {
        for (String id: ids) {
            removeComponent(id);
        }
    }

    public ObjectComponent getComponent(String id) {
        return objectComponents.get(id);
    }

    public boolean hasComponent(String id) {
        return objectComponents.containsKey(id);
    }

    public void start() {
        for (ObjectComponent component: objectComponents.values()) {
            component.start();
        }
    }

    public void update() {
        for (ObjectComponent component: objectComponents.values()) {
            component.update();
        }
    }

    public static void registerObject(JSONObject objectData) {
        objectRegistry.put((String) objectData.get("id"), objectData);
    }

    public static GameObject createGameObject(String id) {
        return new GameObject(id);
    }

    public static GameObject createGameObject(String id, Point position) {
        return new GameObject(id);
    }

    public static GameObject initialize(String id) {
        return new GameObject(id);
    }
}
