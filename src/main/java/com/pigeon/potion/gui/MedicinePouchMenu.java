package com.pigeon.potion.gui;

import com.pigeon.potion.item.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MedicinePouchMenu extends AbstractContainerMenu {

    private final ItemStackHandler handler;
    private final ItemStack pouch;

    // 大箱子：6行 x 9列 = 54格
    private static final int ROWS = 6;
    private static final int COLS = 9;
    private static final int SLOT_COUNT = ROWS * COLS; // 54

    public MedicinePouchMenu(int id, Inventory playerInv, ItemStackHandler handler, ItemStack pouch) {
        super(ModMenuTypes.MEDICINE_POUCH.get(), id);
        this.handler = handler;
        this.pouch = pouch;

        // 药囊格子 6行 x 9列 (起始位置: x=8, y=18)
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                this.addSlot(new SlotItemHandler(handler, col + row * COLS, 8 + col * 18, 18 + row * 18) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return stack.getItem() instanceof net.minecraft.world.item.SplashPotionItem
                                || stack.getItem() instanceof net.minecraft.world.item.LingeringPotionItem;
                    }
                });
            }
        }

        // 玩家背包 3行 x 9列 (起始位置: x=8, y=140)
        int backpackStartY = 140;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, backpackStartY + row * 18));
            }
        }

        // 快捷栏 (起始位置: x=8, y=198)
        int hotbarStartY = 198;
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInv, col, 8 + col * 18, hotbarStartY));
        }
    }

    // 客户端构造
    public MedicinePouchMenu(int id, Inventory playerInv, FriendlyByteBuf buf) {
        this(id, playerInv, new ItemStackHandler(SLOT_COUNT), playerInv.player.getMainHandItem());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            stack = slotStack.copy();

            if (index < SLOT_COUNT) {
                if (!this.moveItemStackTo(slotStack, SLOT_COUNT, SLOT_COUNT + 36, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.moveItemStackTo(slotStack, 0, SLOT_COUNT, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return stack;
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        super.clicked(slotId, button, clickType, player);
    }

    @Override
    public boolean stillValid(Player player) {
        return player.getMainHandItem().is(ModItems.MEDICINE_POUCH.get())
                || player.getOffhandItem().is(ModItems.MEDICINE_POUCH.get());
    }
}