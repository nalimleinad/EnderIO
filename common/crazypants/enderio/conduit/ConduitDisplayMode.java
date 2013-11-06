package crazypants.enderio.conduit;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public enum ConduitDisplayMode {
  ALL,
  ITEM,
  POWER,
  FLUID,
  REDSTONE;

  public static ConduitDisplayMode next(ConduitDisplayMode mode) {
    int index = mode.ordinal() + 1;
    if(index >= values().length) {
      index = 0;
    }
    return values()[index];
  }

  public static ConduitDisplayMode getDisplayMode(ItemStack equipped) {
    int index = equipped.getItemDamage();
    index = MathHelper.clamp_int(index, 0, ConduitDisplayMode.values().length);
    return ConduitDisplayMode.values()[index];
  }

  public static void setDisplayMode(ItemStack equipped, ConduitDisplayMode mode) {
    if(mode == null || equipped == null) {
      return;
    }
    int index = mode.ordinal();
    index = MathHelper.clamp_int(index, 0, ConduitDisplayMode.values().length);
    equipped.setItemDamage(index);
  }

  public ConduitDisplayMode next() {
    return next(this);
  }
}