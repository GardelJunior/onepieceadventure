package xyz.pixelatedw.MineMineNoMi3.abilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.Ability;
import xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles.DoruProjectiles;
import xyz.pixelatedw.MineMineNoMi3.lists.ListAttributes;

public class DoruAbilities 
{

	public static Ability[] abilitiesArray = new Ability[] {new DoruDoruArtsMori(), new CandleWall(), new CandleHouse()};

	
	public static class CandleHouse extends Ability
	{
		public CandleHouse() 
		{
			super(ListAttributes.CANDLEHOUSE); 
		}
		
		public void use(EntityPlayer player)
		{		
			if(!player.worldObj.isRemote)
			{
				if(!isOnCooldown)
				{
					for(int y = 0; y <= 3; y++)
					{
						for(int x = 0; x < 1; x++)
							for(int z = -5; z < 5; z++)
								player.worldObj.setBlock(((int) player.posX + 6) - x, (int) player.posY + y, (int) player.posZ - z, Blocks.clay);
						for(int x = 0; x < 1; x++)
							for(int z = -5; z < 5; z++)
								player.worldObj.setBlock(((int) player.posX - 5) - x, (int) player.posY + y, (int) player.posZ - z, Blocks.clay);
						for(int x = -5; x < 5; x++)
							for(int z = 0; z < 1; z++)
								player.worldObj.setBlock((int) player.posX - x, (int) player.posY + y, ((int) player.posZ + 6) - z, Blocks.clay);
						for(int x = -5; x < 5; x++)
							for(int z = 0; z < 1; z++)
								player.worldObj.setBlock((int) player.posX - x, (int) player.posY + y, ((int) player.posZ - 5) - z, Blocks.clay);
					}
					for(int x = -5; x < 5; x++)
						for(int y = 0; y < 1; y++)
							for(int z = -5; z < 5; z++)
						  		player.worldObj.setBlock((int) player.posX - x, ((int) player.posY + 4) + y, (int) player.posZ - z, Blocks.clay);
					
					isOnCooldown = true;
					startCooldown();
				}
			}
		} 
	}
	
	public static class CandleWall extends Ability
	{
		public CandleWall() 
		{
			super(ListAttributes.CANDLEWALL); 
		}
		
		public void use(EntityPlayer player)
		{		
			if(!player.worldObj.isRemote)
			{
				if(!isOnCooldown)
				{
					if(WyHelper.get4Directions(player) == WyHelper.Direction.NORTH)
					{
						for(int x = -3; x <  3; x++)
						for(int y = 0; y <= 3; y++)
						for(int z = -1; z <= 1; z++)
							player.worldObj.setBlock((int) player.posX - x, (int) player.posY + y, ((int) player.posZ - 3) - z, Blocks.clay);
					}
					if(WyHelper.get4Directions(player) == WyHelper.Direction.SOUTH)
					{
						for(int x = -3; x <  3; x++)
						for(int y = 0; y <= 3; y++)
						for(int z = -1; z <= 1; z++)
							player.worldObj.setBlock((int) player.posX - x, (int) player.posY + y, ((int) player.posZ + 2) - z, Blocks.clay);
					}
					if(WyHelper.get4Directions(player) == WyHelper.Direction.EAST)
					{
						for(int x = -1; x < 1; x++)
						for(int y = 0; y <= 3; y++)
						for(int z = -3; z <= 3; z++)
							player.worldObj.setBlock(((int) player.posX + 2) - x, (int) player.posY + y, (int) player.posZ - z, Blocks.clay);
					}
					if(WyHelper.get4Directions(player) == WyHelper.Direction.WEST)
					{
						for(int x = -1; x < 1; x++)
						for(int y = 0; y <= 3; y++)
						for(int z = -3; z <= 3; z++)
							player.worldObj.setBlock(((int) player.posX - 3) - x, (int) player.posY + y, (int) player.posZ - z, Blocks.clay);
					}
					
					isOnCooldown = true;
					startCooldown();
				}
			}
		} 
	}
	
	public static class DoruDoruArtsMori extends Ability
	{
		public DoruDoruArtsMori() 
		{
			super(ListAttributes.DORUDORUARTSMORI); 
		}
		
		public void use(EntityPlayer player)
		{		
			this.projectile = new DoruProjectiles.DoruDoruArtsMori(player.worldObj, player, attr);
			super.use(player);
		} 
	}
	
}
