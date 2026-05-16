package dev.rinchan.waystoneemeralds.payment;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.fml.ModList;

public final class EmeraldPayment {
    private static final int EMERALD_BLOCK_VALUE = 9;
    private static final List<ResourceLocation> LIGHTMAN_PAYMENT_CANDIDATES = List.of(
        ResourceLocation.withDefaultNamespace("emerald"),
        ResourceLocation.fromNamespaceAndPath("lightmanscurrency", "coin_emerald")
    );

    private EmeraldPayment() {
    }

    public static boolean canPay(Player player, int emeralds) {
        if (emeralds <= 0 || player.getAbilities().instabuild) {
            return true;
        }
        int inventoryUnits = countInventoryUnits(player);
        return inventoryUnits >= emeralds || canExtractLightmanUnits(player, emeralds - inventoryUnits);
    }

    public static int consume(Player player, int emeralds) {
        if (emeralds <= 0 || player.getAbilities().instabuild) {
            return 0;
        }
        int inventoryUnits = countInventoryUnits(player);
        if (inventoryUnits < emeralds && !canExtractLightmanUnits(player, emeralds - inventoryUnits)) {
            return 0;
        }

        int remaining = emeralds;
        int consumedInventory = 0;
        for (int slot = 0; slot < player.getInventory().getContainerSize() && remaining > 0; slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.is(Items.EMERALD)) {
                int used = Math.min(stack.getCount(), remaining);
                stack.shrink(used);
                consumedInventory += used;
                remaining -= used;
            } else if (stack.is(Items.EMERALD_BLOCK)) {
                int blocks = Math.min(stack.getCount(), (remaining + EMERALD_BLOCK_VALUE - 1) / EMERALD_BLOCK_VALUE);
                int covered = Math.min(remaining, blocks * EMERALD_BLOCK_VALUE);
                int change = blocks * EMERALD_BLOCK_VALUE - covered;
                stack.shrink(blocks);
                consumedInventory += covered;
                remaining -= covered;
                giveInventoryEmeralds(player, change);
            }
        }
        player.getInventory().setChanged();

        if (remaining > 0 && !extractLightmanUnits(player, remaining, false)) {
            giveInventoryEmeralds(player, consumedInventory);
            return 0;
        }
        return consumedInventory;
    }

    public static void giveInventoryEmeralds(Player player, int emeralds) {
        if (emeralds <= 0) {
            return;
        }
        ItemStack stack = new ItemStack(Items.EMERALD, emeralds);
        if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
        }
    }

    private static int countInventoryUnits(Player player) {
        int total = 0;
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.is(Items.EMERALD)) {
                total += stack.getCount();
            } else if (stack.is(Items.EMERALD_BLOCK)) {
                total += stack.getCount() * EMERALD_BLOCK_VALUE;
            }
        }
        return total;
    }

    private static boolean canExtractLightmanUnits(Player player, int units) {
        return units <= 0 || extractLightmanUnits(player, units, true);
    }

    private static Optional<Item> lightmanPaymentItem() {
        if (!ModList.get().isLoaded("lightmanscurrency")) {
            return Optional.empty();
        }
        for (ResourceLocation id : LIGHTMAN_PAYMENT_CANDIDATES) {
            Optional<Item> item = BuiltInRegistries.ITEM.getOptional(id);
            if (item.isPresent() && isLightmanMoneyItem(item.get())) {
                return item;
            }
        }
        return Optional.empty();
    }

    private static boolean isLightmanMoneyItem(Item item) {
        try {
            Class<?> coinApiClass = Class.forName("io.github.lightman314.lightmanscurrency.api.money.coins.CoinAPI");
            Object api = coinApiClass.getMethod("getApi").invoke(null);
            Method chainDataOfCoin = coinApiClass.getMethod("ChainDataOfCoin", Item.class);
            return chainDataOfCoin.invoke(api, item) != null;
        } catch (ReflectiveOperationException | LinkageError ignored) {
            return false;
        }
    }

    private static boolean extractLightmanUnits(Player player, int units, boolean simulation) {
        Optional<Item> paymentItem = lightmanPaymentItem();
        if (paymentItem.isEmpty()) {
            return false;
        }
        try {
            Class<?> moneyApiClass = Class.forName("io.github.lightman314.lightmanscurrency.api.money.MoneyAPI");
            Object api = moneyApiClass.getMethod("getApi").invoke(null);
            Object handler = moneyApiClass.getMethod("GetPlayersMoneyHandler", Player.class).invoke(api, player);
            if (handler == null) {
                return false;
            }

            Class<?> coinValueClass = Class.forName("io.github.lightman314.lightmanscurrency.api.money.value.builtin.CoinValue");
            Object value = coinValueClass.getMethod("fromItemOrValue", Item.class, int.class, long.class)
                .invoke(null, paymentItem.get(), units, (long)units);

            Method extract = handler.getClass().getMethod("extractMoney", Class.forName("io.github.lightman314.lightmanscurrency.api.money.value.MoneyValue"), boolean.class);
            Object remainder = extract.invoke(handler, value, simulation);
            return (Boolean)remainder.getClass().getMethod("isEmpty").invoke(remainder);
        } catch (ReflectiveOperationException | LinkageError ignored) {
            return false;
        }
    }
}
