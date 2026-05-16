package dev.rinchan.waystoneemeralds;

import dev.rinchan.waystoneemeralds.requirement.AddEmeraldCostFunction;
import dev.rinchan.waystoneemeralds.requirement.EmeraldCurrencyRequirementType;
import dev.rinchan.waystoneemeralds.requirement.MaxEmeraldCostFunction;
import dev.rinchan.waystoneemeralds.requirement.MinEmeraldCostFunction;
import dev.rinchan.waystoneemeralds.requirement.MultiplyEmeraldCostFunction;
import net.blay09.mods.waystones.config.WaystonesConfig;
import net.blay09.mods.waystones.requirement.RequirementRegistry;
import net.minecraft.resources.ResourceLocation;

public final class WaystoneEmeralds {
    public static final String MOD_ID = "waystone_emeralds";

    private WaystoneEmeralds() {
    }

    public static void init() {
        RequirementRegistry.register(new EmeraldCurrencyRequirementType());
        RequirementRegistry.register(new AddEmeraldCostFunction());
        RequirementRegistry.register(new MultiplyEmeraldCostFunction());
        RequirementRegistry.register(new MinEmeraldCostFunction());
        RequirementRegistry.register(new MaxEmeraldCostFunction());
    }

    public static void applyConfiguredWarpRequirements() {
        if (!WaystoneEmeraldsConfig.ENABLE_EMERALD_COSTS.get()) {
            return;
        }
        var teleports = WaystonesConfig.getActive().teleports;
        teleports.enableCosts = true;
        teleports.warpRequirements = WaystoneEmeraldsConfig.warpRequirements();
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static ResourceLocation waystonesId(String path) {
        return ResourceLocation.fromNamespaceAndPath("waystones", path);
    }
}
