package crazypants.enderio.power;

import buildcraft.api.mj.IBatteryObject;
import buildcraft.api.mj.MjAPI;
import buildcraft.api.power.IPowerReceptor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;
import crazypants.enderio.config.Config;

public class PowerHandlerUtil {

  public static IPowerInterface create(Object o) {
    if(o instanceof IEnergyHandler) {
      return new PowerInterfaceRF((IEnergyHandler) o);
    }

    if(Config.powerConduitOutputMJ) {
      IBatteryObject battery = MjAPI.getMjBattery(o);
      if(battery != null) {
        return new PowerInterfaceBC2(battery);
      }
      if(o instanceof IPowerReceptor) {
        return new PowerInterfaceBC((IPowerReceptor) o);
      }
    }

    return null;
  }

  public static int getStoredEnergyForItem(ItemStack item) {
    NBTTagCompound tag = item.getTagCompound();
    if(tag == null) {
      return 0;
    }

    if(tag.hasKey("storedEnergy")) {
      double storedMj = tag.getDouble("storedEnergy");
      return (int) (storedMj * 10);
    }

    return tag.getInteger("storedEnergyRF");
  }

  public static void setStoredEnergyForItem(ItemStack item, int storedEnergy) {
    NBTTagCompound tag = item.getTagCompound();
    if(tag == null) {
      tag = new NBTTagCompound();
    }
    tag.setInteger("storedEnergyRF", storedEnergy);
    item.setTagCompound(tag);
  }


  public static int recieveInternal(IInternalPowerReceptor target, int maxReceive, ForgeDirection from, boolean simulate) {
    int result = Math.min(target.getMaxEnergyRecieved(from), maxReceive);
    result = Math.min(target.getMaxEnergyStored() - target.getEnergyStored(), result);
    if(result > 0 && !simulate) {
      target.setEnergyStored(target.getEnergyStored() + result);
    }
    return result;
  }

  
}
