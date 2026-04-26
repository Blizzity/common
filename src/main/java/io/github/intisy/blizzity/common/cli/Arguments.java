package io.github.intisy.blizzity.common.cli;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Command-line argument parser and .env/ directory reader.
 * <p>
 * Loads variables from {@code .env/} directories, CLI arguments, and explicit maps,
 * falling back to {@link System#getenv(String)} for unresolved keys.
 * @author Finn Birich
 */
@SuppressWarnings("unused")
public class Arguments {

    public final Map<String, String> arguments;

    public Arguments() {
        this(new HashMap<String, String>());
    }

    public Arguments(String... args) {
        this(Parser.parseArgs(args));
    }

    public Arguments(Map<String, String> args) {
        this.arguments = new HashMap<String, String>();
        this.arguments.putAll(loadFromEnvDir());
        this.arguments.putAll(args);
    }

    public void add(String[] args) {
        Map<String, String> parsed = Parser.parseArgs(args);
        this.arguments.putAll(parsed);
    }

    public void set(String name, Object value) {
        arguments.put(name, value.toString());
    }

    /**
     * Loads additional variables from an arbitrary directory containing env files.
     * Each file may contain {@code KEY=VALUE} lines or be a bare-value file named by key.
     */
    public void addSearchPath(Path envDir) {
        if (envDir == null) return;
        File dir = envDir.toFile();
        if (!dir.exists() || !dir.isDirectory()) return;
        arguments.putAll(loadDir(dir));
    }

    public boolean getAsBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public boolean getAsBoolean(String key, boolean fallback) {
        return Boolean.parseBoolean(get(key, String.valueOf(fallback)));
    }

    public Map<String, String> getArguments() {
        return arguments;
    }

    public String get(String key) {
        String value = resolve(key);
        if (value != null) return value;
        throw new IllegalStateException("Required variable \"" + key + "\" not found and no fallback set");
    }

    public String get(String key, String fallback) {
        String value = resolve(key);
        return value != null ? value : fallback;
    }

    private String resolve(String key) {
        String value = arguments.get(key);
        if (value != null) return value;

        String formattedKey = key.toUpperCase().replace(".", "_");
        value = arguments.get(formattedKey);
        if (value != null) return value;

        String env = System.getenv(formattedKey);
        if (env != null && !env.isEmpty()) return env;

        return null;
    }

    private Map<String, String> loadFromEnvDir() {
        File envDir = findEnvDir();
        return loadDir(envDir);
    }

    private Map<String, String> loadDir(File dir) {
        Map<String, String> vars = new HashMap<String, String>();
        if (dir == null || !dir.exists() || !dir.isDirectory()) return vars;
        File[] files = dir.listFiles();
        if (files == null) return vars;
        for (File file : files) {
            if (!file.isFile()) continue;
            try {
                String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
                boolean foundKeys = false;
                for (String line : content.split("\\R")) {
                    line = line.trim();
                    if (!line.isEmpty() && !line.startsWith("#")) {
                        String[] parts = line.split("=", 2);
                        if (parts.length == 2) {
                            vars.put(parts[0].trim(), parts[1].trim());
                            foundKeys = true;
                        }
                    }
                }
                if (!foundKeys) {
                    vars.put(file.getName(), content.trim());
                }
            } catch (IOException e) {
                System.err.println("[Arguments] WARNING: Could not read .env file: " + file.getName());
            }
        }
        return vars;
    }

    private static File findEnvDir() {
        File[] candidates = {
                new File(".env"),
                new File("../.env"),
                new File("backend/.env")
        };
        for (File candidate : candidates) {
            if (candidate.exists() && candidate.isDirectory()) return candidate;
        }
        return null;
    }

    public static class Parser {
        public static Map<String, String> parseArgs(String[] args) {
            Map<String, String> map = new HashMap<String, String>();
            for (String arg : args) {
                if (arg.startsWith("--")) {
                    arg = arg.substring(2);
                    String[] keyValue = arg.split("=", 2);
                    String key = keyValue[0];
                    String value = keyValue.length == 2 ? keyValue[1] : "true";
                    map.put(key, value);
                }
            }
            return map;
        }
    }
}
