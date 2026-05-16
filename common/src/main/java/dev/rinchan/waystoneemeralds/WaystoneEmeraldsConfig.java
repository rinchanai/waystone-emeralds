package dev.rinchan.waystoneemeralds;

import java.util.List;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class WaystoneEmeraldsConfig {
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.BooleanValue ENABLE_EMERALD_COSTS;
    public static final ModConfigSpec.IntValue SAME_DIMENSION_COST;
    public static final ModConfigSpec.IntValue INTERDIMENSIONAL_COST;
    public static final ModConfigSpec.DoubleValue SCROLL_COST_MULTIPLIER;
    public static final ModConfigSpec.IntValue MIN_COST;
    public static final ModConfigSpec.IntValue MAX_COST;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("teleports");
        ENABLE_EMERALD_COSTS = builder
            .comment("When true, Waystone Emeralds replaces Waystones' warp requirement list at runtime with the emerald-cost rules below. Set false to leave Waystones' own config untouched.")
            .define("enableEmeraldCosts", true);
        SAME_DIMENSION_COST = builder
            .comment("Emerald units charged for a same-dimension Waystones teleport.")
            .defineInRange("sameDimensionCost", 1, 0, Integer.MAX_VALUE);
        INTERDIMENSIONAL_COST = builder
            .comment("Emerald units charged for an interdimensional Waystones teleport.")
            .defineInRange("interdimensionalCost", 3, 0, Integer.MAX_VALUE);
        SCROLL_COST_MULTIPLIER = builder
            .comment("Multiplier applied when teleporting from a Waystones scroll. 0 means scroll teleports only consume the scroll item itself.")
            .defineInRange("scrollCostMultiplier", 0.0D, 0.0D, 1024.0D);
        MIN_COST = builder
            .comment("Minimum emerald units charged after all modifiers.")
            .defineInRange("minCost", 0, 0, Integer.MAX_VALUE);
        MAX_COST = builder
            .comment("Maximum emerald units charged after all modifiers.")
            .defineInRange("maxCost", 3, 0, Integer.MAX_VALUE);
        builder.pop();
        SPEC = builder.build();
    }

    private WaystoneEmeraldsConfig() {
    }

    public static List<String> warpRequirements() {
        return List.of(
            "[is_not_interdimensional] add_emerald_cost(" + SAME_DIMENSION_COST.get() + ")",
            "[is_interdimensional] add_emerald_cost(" + INTERDIMENSIONAL_COST.get() + ")",
            "[source_is_scroll] multiply_emerald_cost(" + formatDouble(SCROLL_COST_MULTIPLIER.get()) + ")",
            "min_emerald_cost(" + MIN_COST.get() + ")",
            "max_emerald_cost(" + MAX_COST.get() + ")"
        );
    }

    private static String formatDouble(double value) {
        if (value == Math.rint(value)) {
            return Integer.toString((int)value);
        }
        return Double.toString(value);
    }
}
