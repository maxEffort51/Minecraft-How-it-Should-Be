package net.maxeffort.helper.main;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class HelperDataGenerator implements DataGeneratorEntrypoint {
    protected static final List<FabricDataGenerator.Pack.RegistryDependentFactory<DataProvider>> registryFactories = new ArrayList<>();
    protected static final List<FabricDataGenerator.Pack.Factory<DataProvider>> factories = new ArrayList<>();

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) { finish(generator); }

    public static void finish(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        registryFactories.forEach(pack::addProvider);
        factories.forEach(pack::addProvider);
    }

    @SafeVarargs public static Builder addProviders(FabricDataGenerator.Pack.RegistryDependentFactory<DataProvider>... factories) {
        registryFactories.addAll(List.of(factories)); return new Builder(); }
    @SafeVarargs public static Builder addProviders(FabricDataGenerator.Pack.Factory<DataProvider>... newFactories) {
        factories.addAll(List.of(newFactories)); return new Builder(); }

    public static Builder addProvider(FabricDataGenerator.Pack.RegistryDependentFactory<DataProvider> factory) {
        registryFactories.add(factory); return new Builder(); }
    public static Builder addProvider(FabricDataGenerator.Pack.Factory<DataProvider> factory) {
        factories.add(factory); return new Builder(); }

    public static class Builder {
        private Builder() {}

        public Builder and(FabricDataGenerator.Pack.RegistryDependentFactory<DataProvider> factory) { registryFactories.add(factory); return this; }
        public Builder and(FabricDataGenerator.Pack.Factory<DataProvider> factory) { factories.add(factory); return this; }
        @SafeVarargs public final Builder many(FabricDataGenerator.Pack.RegistryDependentFactory<DataProvider>... factories) {
            registryFactories.addAll(List.of(factories)); return this; }
        @SafeVarargs public final Builder many(FabricDataGenerator.Pack.Factory<DataProvider>... newFactories) {
            factories.addAll(List.of(newFactories)); return this; }
    }
}
