package com.masterj.RecipeManager;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONObject;

import java.awt.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Scanner;

/**
 * @author Jonathan Miller
 * @version 1.0
 */

/**
 * This is the main class
 */
public class RecipeManager {

    /**
     * This method runs the web server
     * @throws IOException
     * @throws URISyntaxException
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        Console console = System.console();
        if(console == null && !GraphicsEnvironment.isHeadless()){
            String filename = (RecipeManager.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
            System.out.println(filename);
            Runtime.getRuntime().exec(new String[]{"cmd","/c","start","cmd","/k","java -jar \"" + filename.substring(1) + "\""});
        } else {
            int port = 80;
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            System.out.println("Server is now running. Please open a browser and go to 127.0.0.1.");
            server.createContext("/", new RootHandler());
            server.createContext("/add", new AddHandler());
            server.createContext("/remove", new RemoveHandler());
            server.createContext("/about", new AboutHandler());
            server.createContext("/config", new ConfigHandler());
            server.createContext("/addconfig", new AddConfigHandler());
            server.createContext("/removeconfig", new RemoveConfigHandler());
            server.setExecutor(null);
            server.start();
        }
    }

    /**
     * This handles the root path
     */
    public static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange res) throws IOException {
            String filename = null;
            try {
                filename = (RecipeManager.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            File file = new File(filename.substring(1).replace("RecipeManager.jar", "index.html"));
            res.sendResponseHeaders(200, file.length());
            try (OutputStream os = res.getResponseBody()) {
                Files.copy(file.toPath(), os);
            }
        }
    }

    /**
     * This handles the add path
     */
    public static class AddHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange res) throws IOException {
            String filename = null;
            try {
                filename = (RecipeManager.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            File file = new File(filename.substring(1).replace("RecipeManager.jar", "add.html"));
            res.sendResponseHeaders(200, file.length());
            try (OutputStream os = res.getResponseBody()) {
                Files.copy(file.toPath(), os);
            }
        }
    }

    /**
     * This handles the remove path
     */
    public static class RemoveHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange res) throws IOException {
            String filename = null;
            try {
                filename = (RecipeManager.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            File file = new File(filename.substring(1).replace("RecipeManager.jar", "remove.html"));
            res.sendResponseHeaders(200, file.length());
            try (OutputStream os = res.getResponseBody()) {
                Files.copy(file.toPath(), os);
            }
        }
    }

    /**
     * This handles the about path
     */
    public static class AboutHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange res) throws IOException {
            URL main = RecipeManager.class.getResource("RecipeManager.class");
            if (!"file".equalsIgnoreCase(main.getProtocol()))
                throw new IllegalStateException("Main class is not stored in a file.");
            File path = new File(main.getPath());
            File file = new File(path.toString().replace("RecipeManager.class", "about.html"));
            res.sendResponseHeaders(200, file.length());
            try (OutputStream os = res.getResponseBody()) {
                Files.copy(file.toPath(), os);
            }
        }
    }

    /**
     * This handles the config path
     */
    public static class ConfigHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange res) {
            try {
                String FileFolder = "C:\\RecipeManager";
                File directory = new File(FileFolder);
                if (directory.exists()) {
                } else if (!directory.exists()) {
                    directory.mkdir();
                }
                File file = new File(FileFolder + "\\recipes.json");
                if (file.exists()) {
                } else if (!file.exists()) {
                    file.createNewFile();
                }
                Scanner content = new Scanner(file);
                String list = "";
                while (content.hasNextLine()) {
                    list = list + content.next();
                }
                content.close();
                res.sendResponseHeaders(200, file.length());
                try (OutputStream os = res.getResponseBody()) {
                    Files.copy(file.toPath(), os);
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    /**
     * This handles the addconfig path
     */
    public static class AddConfigHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange res) {
            try {
                String FileFolder = "C:\\RecipeManager";
                File directory = new File(FileFolder);
                if (directory.exists()) {
                } else if (!directory.exists()) {
                    directory.mkdir();
                }
                File file = new File(FileFolder + "\\recipes.json");
                if (file.exists()) {
                } else if (!file.exists()) {
                    file.createNewFile();
                }
                Scanner content = new Scanner(file);
                String list = "";
                while (content.hasNextLine()) {
                    list = list + content.next();
                }
                content.close();
                JSONObject obj = new JSONObject(list);
                // get json to add to file
                String req = res.getRequestURI().getQuery();
                String result = java.net.URLDecoder.decode(req, StandardCharsets.UTF_8);
                String json = result.substring(5);
                JSONObject data = new JSONObject(json);
                obj.put(data.getString("title"), data);
                FileWriter f2 = new FileWriter(file, false);
                f2.write(obj.toString());
                f2.close();
                res.sendResponseHeaders(200, file.length());
                try (OutputStream os = res.getResponseBody()) {
                    Files.copy(file.toPath(), os);
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    /**
     * This handles the remove config path
     */
    public static class RemoveConfigHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange res) {
            try {
                String FileFolder = "C:\\RecipeManager";
                File directory = new File(FileFolder);
                if (directory.exists()) {
                } else if (!directory.exists()) {
                    directory.mkdir();
                }
                File file = new File(FileFolder + "\\recipes.json");
                if (file.exists()) {
                } else if (!file.exists()) {
                    file.createNewFile();
                }
                Scanner content = new Scanner(file);
                String list = "";
                while (content.hasNextLine()) {
                    list = list + content.next();
                }
                content.close();
                JSONObject obj = new JSONObject(list);
                // get json to add to file
                String req = res.getRequestURI().getQuery();
                String recipe = req.substring(5);
                obj.remove(recipe);
                FileWriter f2 = new FileWriter(file, false);
                f2.write(obj.toString());
                f2.close();
                res.sendResponseHeaders(200, file.length());
                try (OutputStream os = res.getResponseBody()) {
                    Files.copy(file.toPath(), os);
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }
}
