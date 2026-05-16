package dev.rinchan.waystoneemeralds.requirement;

import dev.rinchan.waystoneemeralds.WaystoneEmeralds;
import net.blay09.mods.waystones.api.requirement.RequirementFunction;
import net.blay09.mods.waystones.api.requirement.WarpRequirementsContext;
import net.blay09.mods.waystones.requirement.RequirementRegistry;
import net.minecraft.resources.ResourceLocation;

public class MinEmeraldCostFunction implements RequirementFunction<EmeraldCurrencyRequirement, RequirementRegistry.IntParameter> {
    @Override
    public ResourceLocation getId() {
        return WaystoneEmeralds.waystonesId("min_emerald_cost");
    }

    @Override
    public ResourceLocation getRequirementType() {
        return EmeraldCurrencyRequirementType.ID;
    }

    @Override
    public Class<RequirementRegistry.IntParameter> getParameterType() {
        return RequirementRegistry.IntParameter.class;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public EmeraldCurrencyRequirement apply(EmeraldCurrencyRequirement requirement, WarpRequirementsContext context, RequirementRegistry.IntParameter parameter) {
        requirement.setEmeralds(Math.max(requirement.getEmeralds(), parameter.value()));
        return requirement;
    }
}
