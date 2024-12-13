package net.aaronterry.helper.main;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class HelperModInitializer implements ModInitializer {
    protected static String modId;
    protected static boolean debugMode;
    protected static int debugLevels;
    protected static Logger LOGGER;
    protected static final List<Runnable> initializers = Collections.synchronizedList(new ArrayList<>());

    private static void _create(String id, boolean debug, int levels) { modId = id; debugMode = debug; debugLevels = levels; LOGGER = LoggerFactory.getLogger(modId); }

    // CREATE
    public static Builder create(String id) { _create(id, false, 0); return new Builder(); }
    public static Builder create(String id, boolean debug) { _create(id, debug, debug ? 1 : 0); return new Builder(); }
    public static Builder create(String id, int levels) { _create(id, true, levels); return new Builder(); }

    public abstract void init();

    @Override public void onInitialize() {
        try {
            init();
        } catch(Exception e) {
            debug("Problem with initializing mod {}: {}", modId, e.getMessage());
        }
        initializers.forEach(initializer -> {
            try { initializer.run(); } catch (Exception e) {
                debug("Problem with running initializer {}: {}", initializer, e.getMessage());
            }
        });
    }

    // SET AND GET
    public static String id() { return modId; }
    public static void debugMode(boolean debug) { debugMode = debug; }
    public static boolean isDebugging() { return debugMode; }
    public static void debugLevel(int level) { debugLevels = level; }
    public static int getLevel() { return debugLevels; }
    public Logger logger() { return LOGGER; }

    // RESOURCES
    private static void _resource(IdentifiableResourceReloadListener listener, ResourceType type) {
        ResourceManagerHelper helper = ResourceManagerHelper.get(type);
        helper.registerReloadListener(listener);
    }
    public static void resource(ResourceType type, IdentifiableResourceReloadListener... listeners) {
        for (IdentifiableResourceReloadListener listener : listeners) {
            try { _resource(listener, type); } catch (Exception e) {
                debug("Problem with loading resource {}: {}", listener.getName(), e.getMessage());
            }
        }
    }
    public static void resource(IdentifiableResourceReloadListener... listeners) { resource(ResourceType.SERVER_DATA, listeners); }

    // DEBUG
    protected static void _debug(int level, String data) { if (debugMode && level < debugLevels) LOGGER.info(data); }
    protected static void _debug(int level, String data, Object... objects) { if (debugMode && level < debugLevels) LOGGER.info(data, objects); }
    public static void debug(String data) { _debug(0, data); }
    public static void debug(String data, Object... objects) { _debug(0, data, objects); }
    public static void debug(int level, String data) { _debug(level, data); }
    public static void debug(int level, String data, Object... objects) { _debug(level, data, objects); }

    public static class Builder {
        protected Builder() { }
        public Builder add(Runnable runnable) { initializers.add(runnable); return this; }
        public void addAll(Runnable... runnables) { initializers.addAll(List.of(runnables));
        }
    }
}
