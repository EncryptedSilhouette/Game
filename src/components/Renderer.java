package components;

import data.ObjectComponent;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Renderer extends ObjectComponent {
    private static final HashMap<String, BufferedImage> textureRegistry = new HashMap<>();

    public static void registerTexture(String id, BufferedImage texture) {
        textureRegistry.put(id, texture);
    }

    public static BufferedImage getTexture(String id) {
        return textureRegistry.get(id);
    }

    @Override
    public void init() {

    }

    @Override
    public void start() {

    }

    @Override
    public void update() {

    }
}