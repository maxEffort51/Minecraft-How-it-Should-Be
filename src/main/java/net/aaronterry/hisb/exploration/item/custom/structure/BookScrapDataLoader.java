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

import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class BookScrapDataLoader implements IdentifiableResourceReloadListener {
    private static final Map<String, List<String>> bookScrapTexts = new HashMap<>();
    private static final Identifier loaderId = Identifier.of(HisbMod.id(), "book_scrap_json_loader");

//    public static void load(ResourceManager manager) {
//        try {
//            Resource resource = manager.getResource(Identifier.of(HisbMod.id(), "custoom/book_scrap.json")).orElseThrow();
//            JsonObject json = JsonParser.parseReader(new InputStreamReader(resource.getInputStream())).getAsJsonObject();
//
//            json.entrySet().forEach(entry -> {
//                String dim = entry.getKey();
//                JsonArray jsonTexts = entry.getValue().getAsJsonArray();
//                List<String> scrapTexts = new ArrayList<>();
//                for (JsonElement text : jsonTexts) {
//                    scrapTexts.add(text.getAsString());
//                }
//                bookScrapTexts.put(dim, scrapTexts);
//            });
//        } catch (Exception e) {
//            throw new RuntimeException("Error loading book scrap data from json file: " + e.getMessage());
//        }
//    }

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
            bookScrapTexts.clear();
            try {
                Resource resource = manager.getResource(Identifier.of(HisbMod.id(), "custom/book_scrap.json")).orElseThrow();
                JsonObject json = JsonParser.parseReader(new InputStreamReader(resource.getInputStream())).getAsJsonObject();
                json.entrySet().forEach(entry -> {
                    String dim = entry.getKey();
                    JsonArray jsonTexts = entry.getValue().getAsJsonArray();
                    List<String> scrapTexts = new ArrayList<>();
                    for (JsonElement text : jsonTexts) {
                        scrapTexts.add(text.getAsString());
                    }
                    bookScrapTexts.put(dim, scrapTexts);
                });
            } catch (Exception e) {
                System.err.println("Error loading book scrap data from json file: " + e.getMessage());
            }
        }, prepareExecutor);
    }
}
