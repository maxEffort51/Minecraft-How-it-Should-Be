package net.aaronterry.helper.main;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HelperModInitializer implements ModInitializer {
    protected static String modId;
    protected static boolean debugMode;
    protected static int debugLevels;
    protected static Logger LOGGER;
    protected static final List<Runnable> initializers = new ArrayList<>();

    protected static void _create(String id, boolean debug, int levels) { modId = id; debugMode = debug; debugLevels = levels; LOGGER = LoggerFactory.getLogger(modId); }

    public static Builder create(String id) { _create(id, false, 0); return new Builder(); }
    public static Builder create(String id, boolean debug) { _create(id, debug, debug ? 1 : 0); return new Builder(); }
    public static Builder create(String id, int levels) { _create(id, true, levels); return new Builder(); }

    @Override
    public void onInitialize() { initializers.forEach(Runnable::run); }

    public static String id() { return modId; }

    public static void debugMode(boolean debug) { debugMode = debug; }
    public static boolean isDebugging() { return debugMode; }

    public static void debugLevel(int level) { debugLevels = level; }
    public static int getLevel() { return debugLevels; }
    public Logger logger() { return LOGGER; }

    protected static void _debug(int level, String data) { if (debugMode && level < debugLevels) LOGGER.info(data); }
    protected static void _debug(int level, String data, Object... objects) { if (debugMode && level < debugLevels) LOGGER.info(data, objects); }

    public static void debug(String data) { _debug(0, data); }
    public static void debug(String data, Object... objects) { _debug(0, data, objects); }
    public static void debug(int level, String data) { _debug(level, data); }
    public static void debug(int level, String data, Object... objects) { _debug(level, data, objects); }

    public static class Builder {
        protected Builder() { }
        public Builder add(Runnable runnable) { initializers.add(runnable); return new Builder(); }
        public Builder addAll(Runnable... runnables) { initializers.addAll(List.of(runnables)); return new Builder(); }
    }
}
