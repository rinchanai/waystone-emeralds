package dev.rinchan.waystoneemeralds.requirement;

import dev.rinchan.waystoneemeralds.WaystoneEmeralds;
import net.blay09.mods.waystones.api.requirement.RequirementFunction;
import net.blay09.mods.waystones.api.requirement.WarpRequirementsContext;
import net.blay09.mods.waystones.requirement.RequirementRegistry;
import net.minecraft.resources.ResourceLocation;

public class MultiplyEmeraldCostFunction implements RequirementFunction<EmeraldCurrencyRequirement, RequirementRegistry.FloatParameter> {
    @Override
    public ResourceLocation getId() {
        return WaystoneEmeralds.waystonesId("multiply_emerald_cost");
    }

    @Override
    public ResourceLocation getRequirementType() {
        return EmeraldCurrencyRequirementType.ID;
    }

    @Override
    public Class<RequirementRegistry.FloatParameter> getParameterType() {
        return RequirementRegistry.FloatParameter.class;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public EmeraldCurrencyRequirement apply(EmeraldCurrencyRequirement requirement, WarpRequirementsContext context, RequirementRegistry.FloatParameter parameter) {
        requirement.setEmeralds(Math.round(requirement.getEmeralds() * parameter.value()));
        return requirement;
    }
}
