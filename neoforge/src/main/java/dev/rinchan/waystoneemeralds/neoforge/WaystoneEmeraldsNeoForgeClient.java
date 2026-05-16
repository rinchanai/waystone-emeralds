package dev.rinchan.waystoneemeralds.neoforge;

import dev.rinchan.waystoneemeralds.WaystoneEmeralds;
import dev.rinchan.waystoneemeralds.client.EmeraldCurrencyRequirementRenderer;
import dev.rinchan.waystoneemeralds.requirement.EmeraldCurrencyRequirement;
import net.blay09.mods.waystones.client.requirement.RequirementClientRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = WaystoneEmeralds.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class WaystoneEmeraldsNeoForgeClient {
    private WaystoneEmeraldsNeoForgeClient() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> RequirementClientRegistry.registerRenderer(EmeraldCurrencyRequirement.class, new EmeraldCurrencyRequirementRenderer()));
    }
}
