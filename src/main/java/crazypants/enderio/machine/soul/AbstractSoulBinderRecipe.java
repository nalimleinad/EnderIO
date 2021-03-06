package crazypants.enderio.machine.soul;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.ItemStack;
import crazypants.enderio.EnderIO;
import crazypants.enderio.ModObject;
import crazypants.enderio.config.Config;
import crazypants.enderio.machine.IMachineRecipe;
import crazypants.enderio.machine.MachineRecipeInput;
import crazypants.enderio.machine.IMachineRecipe.ResultStack;
import crazypants.enderio.material.FrankenSkull;

public abstract class AbstractSoulBinderRecipe implements IMachineRecipe, ISoulBinderRecipe {

  private int energyRequired;
  private String uid;
  private Class<?> entityClass;
  private int xpRequired;
  
  
  protected AbstractSoulBinderRecipe(int energyRequired, int xpRequired, String uid, Class<?> entityClass) {  
    this.energyRequired = energyRequired;
    this.xpRequired = xpRequired;
    this.uid = uid;
    this.entityClass = entityClass;    
  }

  @Override
  public String getUid() {
    return uid;
  }
    
  @Override
  public int getExperienceRequired() {  
    return xpRequired;
  }

  @Override
  public int getEnergyRequired(MachineRecipeInput... inputs) {
    return getEnergyRequired();
  }

  @Override
  public boolean isRecipe(MachineRecipeInput... inputs) {
    int validCount = 0;
    for(MachineRecipeInput input : inputs) {
      if(isValidInput(input)) {
        validCount++;
      } else {
        return false;
      }
    }
    return validCount == 2;
  }

  @Override
  public ResultStack[] getCompletedResult(float randomChance, MachineRecipeInput... inputs) {
    String mobType = null;
    for(MachineRecipeInput input : inputs) {
      if(input != null && EnderIO.itemSoulVessel.containsSoul(input.item)) {
        mobType = EnderIO.itemSoulVessel.getMobTypeFromStack(input.item);
      }
    }
    if(!getSupportedSouls().contains(mobType)) {
      return new ResultStack[0];
    }
    ItemStack resultStack = getOutputStack(mobType);
    ItemStack soulVessel = new ItemStack(EnderIO.itemSoulVessel);    
    return new ResultStack[] {new ResultStack(soulVessel), new ResultStack(resultStack)};
  }


  @Override
  public float getExperianceForOutput(ItemStack output) {
    return 0;
  }

  @Override
  public boolean isValidInput(MachineRecipeInput input) {
    if(input == null || input.item == null) {
      return false;
    }
    int slot = input.slotNumber;
    ItemStack item = input.item;
    if(slot == 0) {      
      return  getSupportedSouls().contains(EnderIO.itemSoulVessel.getMobTypeFromStack(item));
    } 
    if(slot == 1) {
      return item.isItemEqual(getInputStack());
    }
    return false;    
  }

  @Override
  public String getMachineName() {
    return ModObject.blockSoulBinder.unlocalisedName;
  }

  @Override
  public List<MachineRecipeInput> getQuantitiesConsumed(MachineRecipeInput[] inputs) {    
    List<MachineRecipeInput> result = new ArrayList<MachineRecipeInput>(inputs.length);
    for(MachineRecipeInput input : inputs) {
      if(input != null && input.item != null) {
        ItemStack resStack = input.item.copy();
        resStack.stackSize = 1;
        MachineRecipeInput mri = new MachineRecipeInput(input.slotNumber, resStack);
        result.add(mri);
      }      
    }    
    return result;
  }
  
  protected ItemStack getOutputStack(String mobType) {
    return getOutputStack();
  }

  @Override
  public List<String> getSupportedSouls() {    
    return Collections.singletonList((String)EntityList.classToStringMapping.get(entityClass));
  }

  @Override
  public int getEnergyRequired() {
    return energyRequired;
  }

  public void setEnergyRequired(int energyRequired) {
    this.energyRequired = energyRequired;
  }


}
