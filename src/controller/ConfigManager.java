package controller;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    private static final String USER_CONFIG_PATH = "user/control.config";
    private static final String DEFAULT_CONFIG_RESOURCE = "/config/control.config";
    private final File configFile = new File(USER_CONFIG_PATH);

    private final Map<String, Integer> keyBindings = new HashMap<>();

    public ConfigManager() {
        if (!configFile.exists()) {
            copyDefaultConfig();
        }
        loadConfig();
    }

    private void copyDefaultConfig() {
        try (InputStream in = getClass().getResourceAsStream(DEFAULT_CONFIG_RESOURCE)) {
            if (in == null) {
                throw new FileNotFoundException("Default config file not found in resources.");
            }

            File parentDir = configFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            // Ghi đè bất kể file đã tồn tại hay chưa
            try (OutputStream out = new FileOutputStream(configFile, false)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadConfig() {
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("=")) {
                    String[] parts = line.split("=");
                    String action = parts[0].trim();
                    int keyCode = Integer.parseInt(parts[1].trim());
                    keyBindings.put(action, keyCode);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public int getKeyCode(String action) {
        return keyBindings.getOrDefault(action, -1);
    }

    public void updateKey(String action, int newKeyCode) {
        keyBindings.put(action, newKeyCode);
    }

    public void saveConfig() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
            for (Map.Entry<String, Integer> entry : keyBindings.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetToDefault() {
        copyDefaultConfig();
        keyBindings.clear();
        loadConfig();
    }

    public Map<String, Integer> getAllKeyBindings() {
        return new HashMap<>(keyBindings);
    }
}
