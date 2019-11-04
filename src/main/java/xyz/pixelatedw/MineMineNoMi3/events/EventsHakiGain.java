package xyz.pixelatedw.MineMineNoMi3.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import xyz.pixelatedw.MineMineNoMi3.abilities.HakiAbilities;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.Ability;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.extra.AbilityProperties;
import xyz.pixelatedw.MineMineNoMi3.api.math.WyMathHelper;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;
import xyz.pixelatedw.MineMineNoMi3.helpers.DevilFruitsHelper;
import xyz.pixelatedw.MineMineNoMi3.helpers.ItemsHelper;
import xyz.pixelatedw.MineMineNoMi3.lists.ListAttributes;

public class EventsHakiGain
{
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			ExtendedEntityData props = ExtendedEntityData.get(player);
			AbilityProperties abilityProps = AbilityProperties.get(player);
			ItemStack heldItem = player.getHeldItem();
			
			if(abilityProps.hasHakiAbility(HakiAbilities.KENBUNSHOKU_HAKI_AURA))
			{
				Ability auraKen = abilityProps.getAbilityFromName(ListAttributes.KENBUNSHOKU_HAKI_AURA.getAttributeName());
				boolean hasAuraKenActive = auraKen != null && auraKen.isPassiveActive();
				
				if(props.getObservationHakiExp() >= 200 && hasAuraKenActive)
				{
					if(player.ticksExisted % 200 == 0)
						props.addObservationHakiExp((int) (6 + WyMathHelper.randomWithRange(0, 10)));
				}
			}
		}		
	}
	
	@SubscribeEvent
	public void onEntityAttack(LivingHurtEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer)
		{
			Entity attacker = event.source.getSourceOfDamage();
			EntityPlayer attacked = (EntityPlayer) event.entityLiving;
			ExtendedEntityData props = ExtendedEntityData.get(attacked);
			AbilityProperties abilityProps = AbilityProperties.get(attacked);
			
			if(props.getDoriki() > 1500 && props.getObservationHakiExp() <= 300)
			{
				int exp = (int) (event.ammount / 10);
				if(exp <= 0)
					exp = 1;
				
				props.addObservationHakiExp((int) (exp + WyMathHelper.randomWithRange(0, 2)));
			}
			
			if(props.getObservationHakiExp() > 300 + WyMathHelper.randomWithRange(0, 50))
			{
				this.giveHakiAbility(abilityProps, HakiAbilities.KENBUNSHOKU_HAKI_AURA, attacked);
			}
			
			if(props.getObservationHakiExp() > 600 + WyMathHelper.randomWithRange(0, 100))
			{
				this.giveHakiAbility(abilityProps, HakiAbilities.KENBUNSHOKU_HAKI_FUTURE_SIGHT, attacked);
			}
			
			System.out.println("Imbuing : " + props.getImbuingHakiExp());
			System.out.println("Hardening : " + props.getHardeningHakiExp());
			System.out.println("Observation : " + props.getObservationHakiExp());
			System.out.println("King : " + props.getKingHakiExp());
		}
	}
	
	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event)
	{
		if (event.source.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.source.getEntity();
			ExtendedEntityData props = ExtendedEntityData.get(player);
			AbilityProperties abilityProps = AbilityProperties.get(player);
			EntityLivingBase target = event.entityLiving;
			
			ItemStack heldItem = player.getHeldItem();
			
			if(heldItem != null)
			{
				Ability imbuingBuso = abilityProps.getAbilityFromName(ListAttributes.BUSOSHOKU_HAKI_IMBUING.getAttributeName());
				boolean hasImbuingBusoActive = imbuingBuso != null && imbuingBuso.isPassiveActive();
				
				if(props.getImbuingHakiExp() <= 600 || hasImbuingBusoActive)
				{
					if(ItemsHelper.isSword(heldItem))
						props.addImbuingHakiExp((int) (3 + WyMathHelper.randomWithRange(0, 3)));
					else
						props.addImbuingHakiExp(1);
				}
			}
			else
			{
				Ability hardeningBuso = abilityProps.getAbilityFromName(ListAttributes.BUSOSHOKU_HAKI_HARDENING.getAttributeName());
				boolean hasHardeningBusoActive = hardeningBuso != null && hardeningBuso.isPassiveActive();
				
				if(props.getHardeningHakiExp() <= 600 || hasHardeningBusoActive)
				{
					props.addHardeningHakiExp((int) (6 + WyMathHelper.randomWithRange(0, 3)));
				}
			}
			
			if(props.getDoriki() > 4000 && props.getImbuingHakiExp() > 400 + WyMathHelper.randomWithRange(10, 50))
			{
				this.giveHakiAbility(abilityProps, HakiAbilities.BUSOSHOKU_HAKI_IMBUING, player);
			}
			
			if(props.getDoriki() > 3000 && props.getHardeningHakiExp() > 500 + WyMathHelper.randomWithRange(10, 100))
			{
				this.giveHakiAbility(abilityProps, HakiAbilities.BUSOSHOKU_HAKI_HARDENING, player);
				if(props.getHardeningHakiExp() > 800 + WyMathHelper.randomWithRange(10, 100))
				{
					this.giveHakiAbility(abilityProps, HakiAbilities.BUSOSHOKU_HAKI_FULL_BODY_HARDENING, player);
				}
			}
			
			System.out.println("Imbuing : " + props.getImbuingHakiExp());
			System.out.println("Hardening : " + props.getHardeningHakiExp());
			System.out.println("Observation : " + props.getObservationHakiExp());
			System.out.println("King : " + props.getKingHakiExp());
		}
	}
	
	private void giveHakiAbility(AbilityProperties abilityProps, Ability ability, EntityPlayer player)
	{
		System.out.println(" " + (abilityProps.hasHakiAbility(ability)));
		if(!abilityProps.hasHakiAbility(ability) && !DevilFruitsHelper.verifyIfAbilityIsBanned(ability))
		{
			abilityProps.addHakiAbility(ability);
			WyHelper.sendMsgToPlayer(player, "Obtained " + ability.getAttribute().getAttributeName());
		}
	}
}