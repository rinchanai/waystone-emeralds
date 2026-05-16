package dev.rinchan.waystoneemeralds.requirement;

import dev.rinchan.waystoneemeralds.WaystoneEmeralds;
import net.blay09.mods.waystones.api.requirement.RequirementType;
import net.minecraft.resources.ResourceLocation;

public class EmeraldCurrencyRequirementType implements RequirementType<EmeraldCurrencyRequirement> {
    public static final ResourceLocation ID = WaystoneEmeralds.id("emerald_currency");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public EmeraldCurrencyRequirement createInstance() {
        return new EmeraldCurrencyRequirement(0);
    }
}
