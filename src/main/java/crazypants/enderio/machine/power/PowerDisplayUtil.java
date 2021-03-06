package crazypants.enderio.machine.power;

import java.text.NumberFormat;

import net.minecraft.item.ItemStack;
import cofh.api.energy.IEnergyContainerItem;
import crazypants.enderio.config.Config;
import crazypants.util.Lang;

public class PowerDisplayUtil {

  private static final NumberFormat INT_NF = NumberFormat.getIntegerInstance();

  private static final NumberFormat FLOAT_NF = NumberFormat.getInstance();

  public static String perTickStr() {
    return Lang.localize("power.tick");
  }

  public static String ofStr() {
    return Lang.localize("gui.powerMonitor.of");
  }
 
  static {
    FLOAT_NF.setMinimumFractionDigits(1);
    FLOAT_NF.setMaximumFractionDigits(1);
  }

  public static String getStoredEnergyString(ItemStack item) {
    if(item == null) {
      return null;
    }
    if(! (item.getItem() instanceof IEnergyContainerItem) ) {
      return null;
    }

    IEnergyContainerItem ci = (IEnergyContainerItem)item.getItem();
    return Lang.localize("item.tooltip.power")+ " "+ PowerDisplayUtil.formatPower(ci.getEnergyStored(item)) + "/"
    + PowerDisplayUtil.formatPower(ci.getMaxEnergyStored(item)) + " " + PowerDisplayUtil.abrevation();
  }

  public static String formatPowerPerTick(int powerPerTick) {
    return formatPower(powerPerTick) + " " + abrevation() + perTickStr();
  }

  public static String formatStoredPower(int amount, int capacity) {
    return formatPower(amount) + "/" + formatPower(capacity) + " " + PowerDisplayUtil.abrevation();
  }

  
  public static String formatPower(int powerRF) {
    return INT_NF.format(powerRF);
  }
  
  public static String formatPowerFloat(float averageRfTickSent) {
    return FLOAT_NF.format(averageRfTickSent);
  }

  public static Integer parsePower(String power) {
    if(power == null) {
      return null;
    }
    try {
      Number d = INT_NF.parse(power);
      if(d == null) {
        return null;
      }
      return d.intValue();
    } catch (Exception e) {
      return null;
    }
  }

  public static String abrevation() {
    return Lang.localize("power.rf");
  }

}
