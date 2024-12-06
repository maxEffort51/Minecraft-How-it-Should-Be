package net.aaronterry.hisb.exploration.item.custom.structure;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.aaronterry.hisb.HisbMod;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

public class BookScrapDataLoader implements IdentifiableResourceReloadListener {
    private static final Map<String, List<String>> bookScrapTexts = new ConcurrentHashMap<>();
    private static final Identifier loaderId = Identifier.of(HisbMod.id(), "book_scrap_json_loader");

    public static String get(String dim) {
        List<String> texts = bookScrapTexts.getOrDefault(dim, List.of("An error must've occurred loading the text :("));
        return texts.get(new Random().nextInt(texts.size()));
    }

    @Override
    public Identifier getFabricId() {
        return loaderId;
    }

    @Override
    public CompletableFuture<Void> reload(Synchronizer synchronizer, ResourceManager manager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
        return CompletableFuture.runAsync(() -> {
            HisbMod.debug("Loading book scrap data");
            bookScrapTexts.clear();
            try {
                Resource resource = manager.getResource(Identifier.of(HisbMod.id(), "custom/book_scrap.json")).orElseThrow();
                InputStream stream = resource.getInputStream();
                try {
                    JsonObject json = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();
                    json.entrySet().forEach(entry -> {
                        String dim = entry.getKey();
                        JsonArray jsonTexts = entry.getValue().getAsJsonArray();
                        List<String> scrapTexts = new ArrayList<>();
                        for (JsonElement text : jsonTexts) {
                            scrapTexts.add(text.getAsString());
                        }
                        bookScrapTexts.put(dim, scrapTexts);
                        HisbMod.debug("Loaded book scrap text for dimension: " + dim);
                    });
                } finally {
                    stream.close();
                }
            } catch (Exception e) {
                System.err.println("Error loading book scrap data from json file: " + e.getMessage());
            }
        }, applyExecutor);
    }
}
