package xyz.pixelatedw.MineMineNoMi3.events;

import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import xyz.pixelatedw.MineMineNoMi3.MainConfig;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.network.PacketQuestSync;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.api.quests.QuestProperties;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;
import xyz.pixelatedw.MineMineNoMi3.entities.mobs.bandits.EntityBandit;
import xyz.pixelatedw.MineMineNoMi3.quests.Quest;
import xyz.pixelatedw.MineMineNoMi3.quests.objectives.IBiomeQuestObjective;
import xyz.pixelatedw.MineMineNoMi3.quests.objectives.IEntityInterationQuestObjective;
import xyz.pixelatedw.MineMineNoMi3.quests.objectives.IHitCounterQuestObjective;
import xyz.pixelatedw.MineMineNoMi3.quests.objectives.IKillEntityQuestObjective;
import xyz.pixelatedw.MineMineNoMi3.quests.objectives.ITimedQuestObjective;
import xyz.pixelatedw.MineMineNoMi3.quests.questlines.swordsmanprogression.QuestSwordsmanProgression01;
import xyz.pixelatedw.MineMineNoMi3.quests.questlines.swordsmanprogression.QuestSwordsmanProgression02;

public class EventsQuestsProgress {
	
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			if(player.worldObj.isRemote) return;
			if(player.worldObj.getWorldTime() % 10 == 0) {
				ExtendedEntityData props = ExtendedEntityData.get(player);
				QuestProperties questProps = QuestProperties.get(player);
	
				if(questProps.getCurrentQuest() != null) {
					int i = 0;
					List<ITimedQuestObjective> objectives = questProps.<ITimedQuestObjective>getObjectivesByType(ITimedQuestObjective.class);
					for(i = 0 ; i < objectives.size() ; i++) objectives.get(i).onTimePassEvent(player);
					
					if(player.worldObj.getWorldTime() % 100 == 0) {
						BiomeGenBase biome = player.worldObj.getBiomeGenForCoords((int)player.posX, (int)player.posZ);
						List<IBiomeQuestObjective> objectives2 = questProps.<IBiomeQuestObjective>getObjectivesByType(IBiomeQuestObjective.class);
						for(i = 0 ; i < objectives2.size() ; i++) objectives2.get(i).onChangeBiome(player, biome);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onEntityInteract(EntityInteractEvent event) {
		EntityPlayer player = event.entityPlayer;
		if(player.worldObj.isRemote) return;
		ExtendedEntityData props = ExtendedEntityData.get(player);
		QuestProperties questProps = QuestProperties.get(player);
		EntityLivingBase target = null;
		if (event.target instanceof EntityLivingBase)
			target = (EntityLivingBase) event.target;

		if (target != null && MainConfig.enableQuests) {
			final EntityLivingBase targetEntity = target;
			
			if(questProps.getCurrentQuest() != null) {
				List<IEntityInterationQuestObjective> it = questProps.<IEntityInterationQuestObjective>getObjectivesByType(IEntityInterationQuestObjective.class);
				for(int i = 0 ; i < it.size() ; i++) it.get(i).onInteractWith(player, targetEntity);
			}
			
			if (target instanceof EntityCow) {
				questProps.addQuest(new QuestSwordsmanProgression01());
				questProps.setCurrentQuest("swordsmanprogression01");
				System.out.println("Quest Adicionada [Servidor]");
			} else if(target instanceof EntityChicken) {
				System.out.println("Quest Atual: " + questProps.getCurrentQuest());
				System.out.println("Quests:");
				for(Quest quest : questProps.getQuests()) System.out.println(quest.getTitle());
				System.out.println("Quests Completas:");
				for(Quest quest : questProps.getCompletedQuests()) System.out.println(quest.getTitle());
			} else if(target instanceof EntityBandit) {
				questProps.addQuest(new QuestSwordsmanProgression02());
				questProps.setCurrentQuest("swordsmanprogression02");
				System.out.println("Quest Adicionada [Servidor]");
			}
		}
	}

	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event) {
		if (event.source.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.source.getEntity();
			if(player.worldObj.isRemote) return;
			ExtendedEntityData props = ExtendedEntityData.get(player);
			QuestProperties questProps = QuestProperties.get(player);
			EntityLivingBase target = event.entityLiving;

			if(questProps.getCurrentQuest() != null) {
				List<IKillEntityQuestObjective> it = questProps.<IKillEntityQuestObjective>getObjectivesByType(IKillEntityQuestObjective.class);
				for(int i = 0 ; i < it.size() ; i++) it.get(i).onKillEntity(player, target);
				WyNetworkHelper.sendTo(new PacketQuestSync(questProps), (EntityPlayerMP) player);
			}
		}
	}

	@SubscribeEvent
	public void onEntityAttackEvent(LivingHurtEvent event) {
		if (event.source.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.source.getEntity();
			if(player.worldObj.isRemote) return;
			ExtendedEntityData props = ExtendedEntityData.get(player);
			QuestProperties questProps = QuestProperties.get(player);
			EntityLivingBase target = event.entityLiving;

			if(questProps.getCurrentQuest() != null) {
				List<IHitCounterQuestObjective> it = questProps.<IHitCounterQuestObjective>getObjectivesByType(IHitCounterQuestObjective.class);
				for(int i = 0 ; i < it.size() ; i++) it.get(i).onHitEntity(player, target, event.source);
			}
		}
	}

	@SubscribeEvent
	public void onToolTip(ItemTooltipEvent event) {
		ItemStack itemStack = event.itemStack;

		if (!itemStack.hasTagCompound())
			return;

		NBTTagCompound questLore = (NBTTagCompound) itemStack.getTagCompound().getTag("QuestLore");

		if (questLore == null)
			return;

		for (int i = 0; i < 10; i++) {
			String loreLine = questLore.getString("lore" + i);

			if (WyHelper.isNullOrEmpty(loreLine))
				continue;

			event.toolTip.add(loreLine);
		}
	}
}
