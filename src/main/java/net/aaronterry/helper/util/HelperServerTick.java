package net.aaronterry.helper.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class HelperServerTick {
    private static final List<BiConsumer<MinecraftServer, List<ServerPlayerEntity>>> playerListArray = new ArrayList<>();
    public static void playerList(BiConsumer<MinecraftServer, List<ServerPlayerEntity>> newFunc) { playerListArray.add(newFunc); }
    private static final List<Consumer<MinecraftServer>> serverList = new ArrayList<>();
    public static void server(Consumer<MinecraftServer> func) { serverList.add(func); }

    public static void run() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            playerListArray.forEach(consumer -> consumer.accept(server, server.getPlayerManager().getPlayerList())); // player list
            serverList.forEach(consumer -> consumer.accept(server)); // server list
        });
    }
}
