package dev.rinchan.waystoneemeralds.neoforge;

import dev.rinchan.waystoneemeralds.WaystoneEmeralds;
import dev.rinchan.waystoneemeralds.WaystoneEmeraldsConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;

@Mod(WaystoneEmeralds.MOD_ID)
public class WaystoneEmeraldsNeoForge {
    public WaystoneEmeraldsNeoForge(IEventBus modBus) {
        WaystoneEmeralds.init();
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, WaystoneEmeraldsConfig.SPEC);
        modBus.addListener(this::onConfigLoaded);
        modBus.addListener(this::onConfigReloaded);
        NeoForge.EVENT_BUS.addListener(this::onServerAboutToStart);
    }

    private void onConfigLoaded(ModConfigEvent.Loading event) {
        applyIfOwnConfig(event);
    }

    private void onConfigReloaded(ModConfigEvent.Reloading event) {
        applyIfOwnConfig(event);
    }

    private void applyIfOwnConfig(ModConfigEvent event) {
        if (event.getConfig().getModId().equals(WaystoneEmeralds.MOD_ID)) {
            try {
                WaystoneEmeralds.applyConfiguredWarpRequirements();
            } catch (RuntimeException ignored) {
                // Waystones' own config may not be active yet during early mod config loading.
                // ServerAboutToStartEvent applies the rules again before any warp requirement is resolved.
            }
        }
    }

    private void onServerAboutToStart(ServerAboutToStartEvent event) {
        WaystoneEmeralds.applyConfiguredWarpRequirements();
    }
}
