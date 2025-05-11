package controller;

import model.GameState;
import model.mino.Block;

import java.io.*;
import java.util.ArrayList;

public class SaveGame {

    private static final String SAVE_FILE = "user/savegame.config";

    public static void saveGame(GameState gameState) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(PlayManager.staticBlocks);
            oos.writeObject(PlayManager.dropInterval);
            oos.writeObject(gameState);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameState loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            PlayManager.staticBlocks = (ArrayList<Block>) ois.readObject();
            PlayManager.dropInterval = (int) ois.readObject();
            System.out.println("Loaded staticBlocks size: " + PlayManager.staticBlocks.size());
            return (GameState) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
