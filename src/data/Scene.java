package data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Scene {
    private static final HashMap<String, JSONObject> sceneRegistry = new HashMap<>();
    public static final HashMap<String, Scene> activeScenes = new HashMap<>();

    public static Scene currentScene;

    public final String id;
    public final ArrayList<GameObject> gameObjects = new ArrayList<>();

    protected Scene(JSONObject sceneData) {
        id = (String) sceneData.get("id");
        for (Object objectData: (JSONArray) sceneData.get("game_objects")) {
            GameObject gameObject = GameObject.createGameObject((String) ((JSONObject) objectData).get("id"),
                                                                new Point((int) ((JSONObject) objectData).get("pos_x"),
                                                                          (int) ((JSONObject) objectData).get("pos_y")));
        }
    }

    public static void registerScene(JSONObject sceneData) {
        sceneRegistry.put((String) sceneData.get("id"), sceneData);
    }

    public static Scene generateScene(String sceneID) {
        if (activeScenes.containsKey(sceneID)) {
            return currentScene = activeScenes.get(sceneID);
        }
        else {
            return new Scene(sceneRegistry.get(sceneID));
        }
    }
}
