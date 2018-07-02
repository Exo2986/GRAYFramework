package exomaster.grayfw.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@SuppressWarnings({"unchecked", "unused"})
public class Config extends Properties {
    private File configFile;
    private List<ConfigEntry> entries;
    private boolean hasUpdatedConfigFile;

    public Config(String dir) {
        this(new File(dir));
    }

    private Config(File file) {
        super();

        this.configFile = file;
        this.entries = new ArrayList<>();
    }

    public boolean getHasUpdatedConfigFile() {
        return this.hasUpdatedConfigFile;
    }

    public void addConfigEntry(ConfigEntry entry) {
        this.entries.add(entry);
    }

    public <Any> Any getEntry(String name, Class<?> type) {
        if (super.containsKey(name)) {
            String value = super.getProperty(name);
            if (String.class == type) return (Any) value;
            if (Byte.class == type || Byte.TYPE == type) return (Any) Byte.valueOf(value);
            if (Boolean.class == type || Boolean.TYPE == type) return (Any) Boolean.valueOf(value);
            if (Short.class == type || Short.TYPE == type) return (Any) Short.valueOf(value);
            if (Long.class == type || Long.TYPE == type) return (Any) Long.valueOf(value);
            if (Integer.class == type || Integer.TYPE == type) return (Any) Integer.valueOf(value);
            if (Float.class == type || Float.TYPE == type) return (Any) Float.valueOf(value);
            if (Double.class == type || Double.TYPE == type) return (Any) Double.valueOf(value);
        }
        return null;
    }

    public String getEntryAsString(String name) {
        return getEntry(name, String.class);
    }

    public String[] getEntryAsStringArray(String name) {
        return ((String) getEntry(name, String.class)).split(",");
    }

    public String[] getEntryAsStringArray(String name, String splitter) {
        return ((String) getEntry(name, String.class)).split(splitter);
    }

    @Override
    public Object get(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getOrDefault(Object key, Object defaultValue) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getProperty(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        throw new UnsupportedOperationException();
    }

    public void init() {
        File filePath = new File(configFile.getParent());
        if (!filePath.exists()) {
            filePath.mkdirs();
        }

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (FileInputStream stream = new FileInputStream(this.configFile)) {
            super.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        hasUpdatedConfigFile = addMissingEntries();
    }

    private boolean addMissingEntries() {
        boolean updated = false;
        for (Object o : entries) {
            ConfigEntry entry = (ConfigEntry) o;
            if (!this.containsKey(entry.getEntryName())) {
                updated = true;
                try {
                    Files.write(configFile.toPath(), (System.lineSeparator() + entry.getEntryName() + '=' + entry.getDefaultValue()).getBytes(), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            String cfg = new String(Files.readAllBytes(configFile.toPath()), StandardCharsets.UTF_8);
            Files.write(configFile.toPath(), cfg.trim().getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return updated;
    }
}