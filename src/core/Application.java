package core;

import components.Renderer;
import data.GameObject;
import data.ObjectComponent;
import data.Scene;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.*;

public class Application {

    public static final Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
    public static final GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    public boolean running = false;
    private int ticks = 0, tickRate = 0, TICK_LIMIT = 60,
                frames = 0, frameRate = 0, FRAME_LIMIT = 1000, frameErr = 0;
    private JFrame window;
    private BufferStrategy bufferStrategy;
    private Graphics2D graphics;

    protected void init() {
        loadAll();
        createGameWindow();
    }

    public void start() {
        double updateCap = 1000f / TICK_LIMIT,
               startTime, lastTime, newTime,
               unprocessedTime = 0;

        init();
        startTime = System.currentTimeMillis();
        lastTime = System.nanoTime();

        while (running) {
            newTime = System.nanoTime();
            unprocessedTime += (newTime - lastTime) / 1000000;
            lastTime = newTime;

            //Update
            while (unprocessedTime >= updateCap) {
                unprocessedTime -= updateCap;

                //Fixed Update
                //handler.fixedUpdate();
                if (System.currentTimeMillis() - startTime >= 1000) {
                    startTime = System.currentTimeMillis();
                    tickRate = ticks;
                    ticks = 0;
                }
                ticks++;
            }
            //Frame Update
            //handler.frameUpdate();

            //Draw
            try {
                graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
                graphics.setColor(Color.black);
                graphics.fillRect(0, 0, window.getWidth(), window.getHeight());

                //Render
                //handler.render(graphics);

                graphics.dispose();
                bufferStrategy.show();
            }
            catch (Exception e) {
                if (window.getBufferStrategy() == null) {
                    window.createBufferStrategy(2);
                    bufferStrategy = window.getBufferStrategy();
                }
                if (graphics != null) graphics.dispose();
                frameErr++;
            }

            if (System.currentTimeMillis() - startTime >= 1000) {
                startTime = System.currentTimeMillis();
                frameRate = frames;
                frames = 0;
            }
            frames++;
        }
    }

    protected void createGameWindow() {

    }

    public static void loadAll() {
        loadComponents();
        //loadSprites();
        //loadObjects();
        //loadScenes();
    }

    public static void loadComponents() {
        Class<?> componentClass;
        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("components");

        if (stream == null) return;
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        for (String className: reader.lines().toList()) {
            try {
                componentClass = Class.forName("components" + "." + className.substring(0, className.lastIndexOf('.')));
                ObjectComponent.registerComponent(componentClass.getSimpleName(), componentClass.getConstructor());
            }
            catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void loadSprites() {
        FileReader fileReader;
        JSONArray contents;
        try {
            fileReader = new FileReader("res\\sprites.json");
            contents = (JSONArray) new JSONParser().parse(fileReader);
            for (Object data: contents) {
                JSONObject textureData = (JSONObject) data;
                String path = (String) textureData.get("file_path");
                try {
                    Renderer.registerTexture((String) textureData.get("id"), ImageIO.read(new File(path)));
                }
                catch (IOException e) {
                    //print path
                }
            }
        }
        catch (IOException | ParseException e) {
            //throw new RuntimeException(e);
        }
    }

    public static void loadObjects() {
        FileReader fileReader;
        JSONArray contents;
        try {
            fileReader = new FileReader("res\\game_objects.json");
            contents = (JSONArray) new JSONParser().parse(fileReader);
            for (Object objectData: contents) {
                GameObject.registerObject((JSONObject) objectData);
            }
        }
        catch (IOException | ParseException e) {
            //throw new RuntimeException(e);
        }
    }

    public static void loadScenes() {
        FileReader fileReader;
        JSONArray contents;
        try {
            fileReader = new FileReader("res\\scenes.json");
            contents = (JSONArray) new JSONParser().parse(fileReader);
            for (Object sceneData: contents) {
                Scene.registerScene((JSONObject) sceneData);
            }
        }
        catch (IOException | ParseException e) {
            //throw new RuntimeException(e);
        }
    }
}
