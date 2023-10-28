package creditcrab.omron.Common.Items.Equipment;

import creditcrab.omron.ModItems;
import creditcrab.omron.Omron;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.ISpecialArmor;

import java.util.List;

public class OmronArmor extends ItemArmor implements ISpecialArmor {

    public static final String[] slotNames = {"helmet","chestplate","leggings","boots"};
    @SideOnly(Side.CLIENT)
    private IIcon helmetIcon, helmetIconBroken;

    @SideOnly(Side.CLIENT)
    private IIcon plateIcon, plateIconBroken;

    @SideOnly(Side.CLIENT)
    private IIcon leggingsIcon, leggingsIconBroken;

    @SideOnly(Side.CLIENT)
    private IIcon bootsIcon, bootsIconBroken;

    public OmronArmor(int armorType){
        super (ItemArmor.ArmorMaterial.GOLD,0,armorType);
        setMaxDamage(10);
        setCreativeTab(Omron.tabOmron);
        this.setUnlocalizedName(slotNames[armorType]+"Omron");
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source,double damage, int slot){
        return new ArmorProperties(1,1, Integer.MAX_VALUE);
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot){
        return 5;
    }

    @Override
    public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_)
    {
        return false;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot){
        if (source != DamageSource.fall){
            if (getDamage(stack) + damage >= getMaxDamage()){
                setDamage(stack,getMaxDamage());
                //stack.setStackDisplayName(slotNames[slotNames.length -(1+slot)] + "OmronBroken");

            }
            else{
                stack.damageItem(damage,entity);
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer entityPlayer, List list, boolean par4){
        if (getDamage(stack) == getMaxDamage()){
            list.add("Broken");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister){
        this.itemIcon = iconRegister.registerIcon("omron:omronHelmet");
        this.helmetIcon = iconRegister.registerIcon("omron:omronHelmet");
        this.helmetIconBroken = iconRegister.registerIcon("omron:omronHelmetBroken");
        this.plateIcon = iconRegister.registerIcon("omron:omronChestplate");
        this.plateIconBroken = iconRegister.registerIcon("omron:omronChestplateBroken");
        this.leggingsIcon = iconRegister.registerIcon("omron:omronLeggings");
        this.leggingsIconBroken = iconRegister.registerIcon("omron:omronLeggingsBroken");
        this.bootsIcon = iconRegister.registerIcon("omron:omronBoots");
        this.bootsIconBroken = iconRegister.registerIcon("omron:omronBootsBroken");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage){
        if (damage == getMaxDamage()){
            if (this.equals(ModItems.helmetOmron)){
                return this.helmetIconBroken;
            }
            else if(this.equals(ModItems.chestplateOmron)){
                return this.plateIconBroken;
            }
            else if(this.equals(ModItems.leggingsOmron)){
                return  this.leggingsIconBroken;
            }
            else if(this.equals(ModItems.bootsOmron)){
                return this.bootsIconBroken;
            }
        }
        else{
            if (this.equals(ModItems.helmetOmron)){
                return this.helmetIcon;
            }
            else if(this.equals(ModItems.chestplateOmron)){
                return this.plateIcon;
            }
            else if(this.equals(ModItems.leggingsOmron)){
                return  this.leggingsIcon;
            }
            else if(this.equals(ModItems.bootsOmron)){
                return this.bootsIcon;
            }

        }
        return this.itemIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type){
        if (this.armorType == 2)return "Omron:textures/models/armor/omron_layer_2.png";
        return "Omron:textures/models/armor/omron_layer_1.png";
    }


}
