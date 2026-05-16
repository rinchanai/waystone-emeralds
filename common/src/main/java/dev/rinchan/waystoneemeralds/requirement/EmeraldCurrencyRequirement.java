package dev.rinchan.waystoneemeralds.requirement;

import dev.rinchan.waystoneemeralds.payment.EmeraldPayment;
import java.util.List;
import net.blay09.mods.waystones.api.requirement.WarpRequirement;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class EmeraldCurrencyRequirement implements WarpRequirement {
    private int emeralds;
    private int consumedEmeralds;

    public EmeraldCurrencyRequirement(int emeralds) {
        this.emeralds = Math.max(0, emeralds);
    }

    @Override
    public boolean canAfford(Player player) {
        return EmeraldPayment.canPay(player, emeralds);
    }

    @Override
    public void consume(Player player) {
        consumedEmeralds = EmeraldPayment.consume(player, emeralds);
    }

    @Override
    public void rollback(Player player) {
        if (consumedEmeralds > 0) {
            EmeraldPayment.giveInventoryEmeralds(player, consumedEmeralds);
            consumedEmeralds = 0;
        }
    }

    @Override
    public void appendHoverText(Player player, List<Component> tooltip) {
        if (emeralds > 0) {
            tooltip.add(Component.translatable("tooltip.waystone_emeralds.emerald_cost", emeralds));
        }
    }

    @Override
    public boolean isEmpty() {
        return emeralds <= 0;
    }

    public int getEmeralds() {
        return emeralds;
    }

    public void setEmeralds(int emeralds) {
        this.emeralds = Math.max(0, emeralds);
    }
}
