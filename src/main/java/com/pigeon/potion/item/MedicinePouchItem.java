package com.pigeon.potion.item;

import com.pigeon.potion.gui.MedicinePouchMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;

public class MedicinePouchItem extends Item {

    public static final String INV_TAG = "PouchInventory";
    public static final String SELECTED_TAG = "SelectedSlot";

    public MedicinePouchItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack pouch = player.getItemInHand(hand);

        initSelectedSlot(pouch);

        if (!level.isClientSide && player instanceof ServerPlayer sp) {
            if (player.isShiftKeyDown()) {
                // 蹲下 + 右键：打开 GUI
                openGUI(sp, pouch);
            } else {
                // 直接右键：投掷选中的药水
                throwSelectedPotion(level, player, pouch);
            }
        }
        return InteractionResultHolder.sidedSuccess(pouch, level.isClientSide);
    }

    private static void initSelectedSlot(ItemStack pouch) {
        CompoundTag tag = pouch.getOrCreateTag();
        if (!tag.contains(SELECTED_TAG)) {
            tag.putInt(SELECTED_TAG, 0);
        }
    }

    private void openGUI(ServerPlayer player, ItemStack pouch) {
        ItemStackHandler handler = getInventory(pouch);
        player.openMenu(new SimpleMenuProvider(
                (id, inv, p) -> new MedicinePouchMenu(id, inv, handler, pouch),
                Component.translatable("item.pigeon_potion.medicine_pouch")
        ));
    }

    private void throwSelectedPotion(Level level, Player player, ItemStack pouch) {
        CompoundTag tag = pouch.getOrCreateTag();
        int selected = tag.getInt(SELECTED_TAG);

        ItemStackHandler handler = getInventory(pouch);

        int total = countRemaining(handler);
        if (total == 0) {
            player.displayClientMessage(Component.translatable("message.pigeon_potion.pouch_empty"), true);
            return;
        }

        if (selected >= total) {
            selected = total - 1;
            tag.putInt(SELECTED_TAG, selected);
        }

        int actualSlot = findActualSlot(handler, selected);

        if (actualSlot == -1) {
            player.displayClientMessage(Component.translatable("message.pigeon_potion.no_selected"), true);
            return;
        }

        ItemStack potion = handler.getStackInSlot(actualSlot);

        if (!potion.isEmpty()) {
            net.minecraft.world.entity.projectile.ThrownPotion thrownPotion =
                    new net.minecraft.world.entity.projectile.ThrownPotion(level, player);
            thrownPotion.setItem(potion.copy());
            thrownPotion.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.5F, 1.0F);
            level.addFreshEntity(thrownPotion);

            removeAndCompress(handler, actualSlot);
            saveInventory(pouch, handler);
            updateSelectedSlotAfterRemove(handler, tag, actualSlot);

            int remaining = countRemaining(handler);
            player.displayClientMessage(Component.translatable("message.pigeon_potion.thrown", remaining), true);

            if (remaining > 0) {
                displayCurrentSelected(player, pouch);
            }
        }
    }

    private static int findActualSlot(ItemStackHandler handler, int selectedIndex) {
        int nonEmptyCount = 0;
        for (int i = 0; i < handler.getSlots(); i++) {
            if (!handler.getStackInSlot(i).isEmpty()) {
                if (nonEmptyCount == selectedIndex) {
                    return i;
                }
                nonEmptyCount++;
            }
        }
        return -1;
    }

    private static void removeAndCompress(ItemStackHandler handler, int slot) {
        for (int i = slot; i < handler.getSlots() - 1; i++) {
            handler.setStackInSlot(i, handler.getStackInSlot(i + 1).copy());
        }
        handler.setStackInSlot(handler.getSlots() - 1, ItemStack.EMPTY);
    }

    private static void updateSelectedSlotAfterRemove(ItemStackHandler handler, CompoundTag tag, int removedSlot) {
        int remaining = countRemaining(handler);
        int currentSelected = tag.getInt(SELECTED_TAG);

        if (remaining == 0) {
            tag.putInt(SELECTED_TAG, 0);
        } else if (currentSelected >= remaining) {
            tag.putInt(SELECTED_TAG, remaining - 1);
        }
    }

    private static int countRemaining(ItemStackHandler handler) {
        int count = 0;
        for (int i = 0; i < handler.getSlots(); i++) {
            if (!handler.getStackInSlot(i).isEmpty()) {
                count++;
            }
        }
        return count;
    }

    // 服务端切换上一个
    public static void switchPreviousServer(ServerPlayer player, ItemStack pouch) {
        CompoundTag tag = pouch.getOrCreateTag();
        int current = tag.getInt(SELECTED_TAG);

        ItemStackHandler handler = getInventory(pouch);
        int total = countRemaining(handler);

        if (total == 0) return;

        int previous = (current - 1 + total) % total;
        tag.putInt(SELECTED_TAG, previous);

        displaySelectedPotionServer(player, handler, previous);
    }

    // 服务端切换下一个
    public static void switchNextServer(ServerPlayer player, ItemStack pouch) {
        CompoundTag tag = pouch.getOrCreateTag();
        int current = tag.getInt(SELECTED_TAG);

        ItemStackHandler handler = getInventory(pouch);
        int total = countRemaining(handler);

        if (total == 0) return;

        int next = (current + 1) % total;
        tag.putInt(SELECTED_TAG, next);

        displaySelectedPotionServer(player, handler, next);
    }

    private static void displaySelectedPotionServer(ServerPlayer player, ItemStackHandler handler, int selectedIndex) {
        int actualSlot = findActualSlot(handler, selectedIndex);

        if (actualSlot == -1) return;

        ItemStack potion = handler.getStackInSlot(actualSlot);
        if (!potion.isEmpty()) {
            String potionName = getPotionDisplayName(potion);
            int total = countRemaining(handler);
            player.displayClientMessage(Component.translatable("message.pigeon_potion.selected", potionName, selectedIndex + 1, total), true);
        }
    }

    private static String getPotionDisplayName(ItemStack potion) {
        var potionType = PotionUtils.getPotion(potion);
        String key = potionType.getName("item.minecraft.potion.effect.");
        return Component.translatable(key).getString();
    }

    public static void displayCurrentSelected(Player player, ItemStack pouch) {
        CompoundTag tag = pouch.getOrCreateTag();
        int selected = tag.getInt(SELECTED_TAG);

        ItemStackHandler handler = getInventory(pouch);
        int total = countRemaining(handler);

        if (total == 0) {
            player.displayClientMessage(Component.translatable("message.pigeon_potion.pouch_empty"), true);
            return;
        }

        if (selected >= total) {
            selected = total - 1;
            tag.putInt(SELECTED_TAG, selected);
        }

        int actualSlot = findActualSlot(handler, selected);
        if (actualSlot == -1) return;

        ItemStack potion = handler.getStackInSlot(actualSlot);
        if (!potion.isEmpty()) {
            String potionName = getPotionDisplayName(potion);
            player.displayClientMessage(
                    Component.translatable("message.pigeon_potion.selected", potionName, selected + 1, total),
                    true
            );
        }
    }

    public static ItemStackHandler getInventory(ItemStack pouch) {
        ItemStackHandler handler = new ItemStackHandler(54) {
            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return stack.getItem() instanceof net.minecraft.world.item.SplashPotionItem
                        || stack.getItem() instanceof net.minecraft.world.item.LingeringPotionItem;
            }

            @Override
            protected void onContentsChanged(int slot) {
                saveInventory(pouch, this);
            }
        };

        CompoundTag tag = pouch.getOrCreateTag();
        if (tag.contains(INV_TAG)) {
            handler.deserializeNBT(tag.getCompound(INV_TAG));
        }
        return handler;
    }

    public static void saveInventory(ItemStack pouch, ItemStackHandler handler) {
        pouch.getOrCreateTag().put(INV_TAG, handler.serializeNBT());
    }
}