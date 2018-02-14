package xyz.pixelatedw.MineMineNoMi3.api;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.MainMod;

public class WyRegistry
{

	private static int entityID = 100;
	private static int packetID = 0;
	protected static HashMap lang = new HashMap();
	protected static HashMap items = new HashMap();
	
	public static HashMap getLangMap() { return lang; }
	public static HashMap getItemsMap() { return items; }
	
	public static void registerName(String key, String localizedName)
	{
		getLangMap().put(key, localizedName);
	}  
	 
	public static void registerBlock(Block block, String localizedName, float hard, CreativeTabs tab, Class<? extends TileEntity> tile)
	{	
		String truename = WyHelper.getFancyName(localizedName);
		block.setBlockName(truename).setBlockTextureName(ID.PROJECT_ID + ":" + truename).setHardness(hard);
		GameRegistry.registerBlock(block, truename);
		if(tab != null)
			block.setCreativeTab(tab);
		if(tile != null)
			GameRegistry.registerTileEntity(tile, truename);		
		getItemsMap().put(block, localizedName);
		registerName("tile." + truename + ".name", localizedName);
	}
	
	public static void registerItem(Item item, String localizedName)
	{	
		registerItem(item, localizedName, null);
	} 
	
	public static void registerItem(Item item, String localizedName, CreativeTabs tab)
	{	
		String truename = WyHelper.getFancyName(localizedName);
		item.setUnlocalizedName(truename).setTextureName(ID.PROJECT_ID + ":" + truename);
		if(tab != null)
			item.setCreativeTab(tab);
		GameRegistry.registerItem(item, truename);
		getItemsMap().put(item, localizedName);
		registerName("item." + truename + ".name", localizedName);
	}
	
	public static void registerMob(String name, Class<? extends Entity> entity)
	{
		registerMob(name, entity, -1, -1);
	}
	 
	public static void registerMob(String name, Class<? extends Entity> entity, int color1, int color2)
	{
		EntityRegistry.registerModEntity(entity, name, entityID++, MainMod.getMineMineNoMi(), 128, 3, true);
		if(color1 != -1 && color2 != -1)
			EntityList.addMapping(entity, name, entityID++, color2, color2);
		registerName("entity." + name + ".name", name);
	}

	/*public void registerEnchantment(Enchantment enc, String name)
	{
		String truename = WyHelper.getFancyName(name);
		GameRegistry.register(enc.setName(truename));
		enc.setName(truename);
		registerName("enchantment." + truename, name);
	}*/
	
	/*public void registerDimension(String name, int id, Class<? extends WorldProvider> clazz)
	{
		DimensionType.register(name, "_" + WyHelper.instance().getFancyName(name), id, clazz, true);
		DimensionManager.registerDimension(id, DimensionType.getById(id));	
		DimensionManager.createProviderFor(id);
	}*/

}
