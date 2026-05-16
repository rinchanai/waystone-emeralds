package dev.rinchan.waystoneemeralds.client;

import dev.rinchan.waystoneemeralds.requirement.EmeraldCurrencyRequirement;
import net.blay09.mods.waystones.client.requirement.RequirementRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class EmeraldCurrencyRequirementRenderer implements RequirementRenderer<EmeraldCurrencyRequirement> {
    @Override
    public void renderWidget(Player player, EmeraldCurrencyRequirement requirement, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks, int x, int y) {
        if (requirement.getEmeralds() > 0) {
            ItemStack icon = new ItemStack(Items.EMERALD);
            guiGraphics.renderItem(icon, x, y);
            guiGraphics.renderItemDecorations(Minecraft.getInstance().font, icon, x, y, requirement.getEmeralds() > 1 ? String.valueOf(requirement.getEmeralds()) : null);
        }
    }

    @Override
    public int getOrder() {
        return 10;
    }

    @Override
    public int getWidth(Player player, EmeraldCurrencyRequirement requirement) {
        return requirement.getEmeralds() > 0 ? 18 : 0;
    }

}
