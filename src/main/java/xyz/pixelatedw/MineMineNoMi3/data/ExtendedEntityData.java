package xyz.pixelatedw.MineMineNoMi3.data;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import xyz.pixelatedw.MineMineNoMi3.Values;

public class ExtendedEntityData implements IExtendedEntityProperties {

	public final static String EXT_PROP_NAME = "IEEPShared";
	private final EntityLivingBase entity;

	private boolean isInCombatMode = false;

	
	private int doriki, dorikiCmd, belly, bellyCmd, extol, extolCmd, cola = 100, maxCola = 100, hakiTimer = 0,
			ultraCola = 0, gear = 1;
	
	private int guiPage = 0;
	
	private long bounty, bountyCmd;
	private String akumaNoMiUsed = "n/a", faction = "n/a", race = "n/a", fightStyle = "n/a", crew = "n/a",
			zoanPoint = "n/a";
	private boolean isLogia, hasShadow = true, hasHeart = true, firstTime = true, hasHakiActive = false,
			hasBusoHakiActive = false, hasKenHakiActive = false, kilo = false, hasYamiPower = false,
			hasColaBackpack = false, isInAirWorld = false;
	private float damageMultiplier = 1;

	private String tempPreviousAbility = "";

	private int punchBusoExp, itemBusoExp, kenExp, haoExp;

	private String[] extraEffects = new String[32];

	public ExtendedEntityData(EntityLivingBase entity) {
		this.entity = entity;
	}

	public static final void register(EntityLivingBase entity) {
		entity.registerExtendedProperties(ExtendedEntityData.EXT_PROP_NAME, new ExtendedEntityData(entity));
	}

	public static final ExtendedEntityData get(EntityLivingBase entity) {
		return (ExtendedEntityData) entity.getExtendedProperties(EXT_PROP_NAME);
	}

	public EntityLivingBase getEntity() {
		return this.entity;
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound props = new NBTTagCompound();

		props.setInteger("Doriki", this.doriki);
		props.setInteger("DorikiCmd", this.dorikiCmd);
		props.setInteger("Belly", this.belly);
		props.setInteger("BellyCmd", this.bellyCmd);
		props.setInteger("Extol", this.extol);
		props.setInteger("ExtolCmd", this.extolCmd);
		props.setInteger("Cola", this.cola);
		props.setInteger("MaxCola", this.maxCola);
		props.setInteger("UltraCola", this.ultraCola);
		props.setInteger("Gear", this.gear);

		props.setFloat("DamageMultiplier", this.damageMultiplier);

		props.setLong("Bounty", this.bounty);
		props.setLong("BountyCmd", this.bountyCmd);

		props.setString("AkumaNoMi", this.akumaNoMiUsed);
		props.setString("Faction", this.faction);
		props.setString("Race", this.race);
		props.setString("FightStyle", this.fightStyle);
		props.setString("Crew", this.crew);
		props.setString("ZoanPoint", this.zoanPoint);

		props.setBoolean("isLogia", this.isLogia);
		props.setBoolean("hasShadow", this.hasShadow);
		props.setBoolean("hasHeart", this.hasHeart);
		props.setBoolean("firstTime", this.firstTime);
		props.setBoolean("hasKiloActive", this.kilo);
		props.setBoolean("hasHakiActive", this.hasHakiActive);
		props.setBoolean("hasBusoHakiActive", this.hasBusoHakiActive);
		props.setBoolean("hasKenHakiActive", this.hasKenHakiActive);
		props.setBoolean("hasYamiPower", this.hasYamiPower);
		props.setBoolean("hasColaBackpack", this.hasColaBackpack);
		props.setBoolean("isInAirWorld", this.isInAirWorld);

		props.setBoolean("isInCombatMode", this.isInCombatMode);

		// Haki Exp
		props.setInteger("PunchBusoExp", this.punchBusoExp);
		props.setInteger("ItemBusoExp", this.itemBusoExp);
		props.setInteger("KenExp", this.kenExp);
		props.setInteger("HaoExp", this.haoExp);
		
		props.setInteger("guiPage", this.guiPage);

		for (int i = 0; i < this.extraEffects.length; i++)
			if (this.extraEffects[i] != null && !this.extraEffects[i].isEmpty())
				props.setString("extraEffect_" + i, this.extraEffects[i]);

		compound.setTag(EXT_PROP_NAME, props);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound props = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);

		this.doriki = props.getInteger("Doriki");
		this.dorikiCmd = props.getInteger("DorikiCmd");
		this.belly = props.getInteger("Belly");
		this.bellyCmd = props.getInteger("BellyCmd");
		this.extol = props.getInteger("Extol");
		this.extolCmd = props.getInteger("ExtolCmd");
		this.cola = props.getInteger("Cola");
		this.maxCola = props.getInteger("MaxCola");
		this.ultraCola = props.getInteger("UltraCola");
		this.gear = props.getInteger("Gear");

		this.damageMultiplier = props.getFloat("DamageMultiplier");

		this.bounty = props.getLong("Bounty");
		this.bountyCmd = props.getLong("BountyCmd");

		this.akumaNoMiUsed = props.getString("AkumaNoMi");
		this.faction = props.getString("Faction");
		this.race = props.getString("Race");
		this.fightStyle = props.getString("FightStyle");
		this.crew = props.getString("Crew");
		this.zoanPoint = props.getString("ZoanPoint");

		this.isLogia = props.getBoolean("isLogia");
		this.hasShadow = props.getBoolean("hasShadow");
		this.hasHeart = props.getBoolean("hasHeart");
		this.firstTime = props.getBoolean("firstTime");
		this.kilo = props.getBoolean("hasKiloActive");
		this.hasHakiActive = props.getBoolean("hasHakiActive");
		this.hasBusoHakiActive = props.getBoolean("hasBusoHakiActive");
		this.hasKenHakiActive = props.getBoolean("hasKenHakiActive");
		this.hasYamiPower = props.getBoolean("hasYamiPower");
		this.hasColaBackpack = props.getBoolean("hasColaBackpack");
		this.isInAirWorld = props.getBoolean("isInAirWorld");

		this.isInCombatMode = props.getBoolean("isInCombatMode");

		// Haki Exp
		this.punchBusoExp = props.getInteger("PunchBusoExp");
		this.itemBusoExp = props.getInteger("ItemBusoExp");
		this.kenExp = props.getInteger("KenExp");
		this.haoExp = props.getInteger("HaoExp");
		
		this.guiPage = props.getInteger("guiPage");

		for (int i = 0; i < this.extraEffects.length; i++)
			this.extraEffects[i] = props.getString("extraEffect_" + i);
	}

	@Override
	public void init(Entity entity, World world) {
	}

	public int getKingHakiExp() {
		return this.haoExp;
	}

	public void addKingHakiExp(int i) {
		if (this.haoExp + i >= Values.MAX_HAKI_EXP)
			return;
		if (this.haoExp + i < 0)
			this.haoExp = 0;
		else
			this.haoExp = this.haoExp + i;
	}

	public int getObservationHakiExp() {
		return this.kenExp;
	}

	public void addObservationHakiExp(int i) {
		if (this.kenExp + i >= Values.MAX_HAKI_EXP)
			return;
		if (this.kenExp + i < 0)
			this.kenExp = 0;
		else
			this.kenExp = this.kenExp + i;
	}

	public int getImbuingHakiExp() {
		return this.itemBusoExp;
	}

	public void addImbuingHakiExp(int i) {
		if (this.itemBusoExp + i >= Values.MAX_HAKI_EXP)
			return;
		if (this.itemBusoExp + i < 0)
			this.itemBusoExp = 0;
		else
			this.itemBusoExp = this.itemBusoExp + i;
	}

	public int getHardeningHakiExp() {
		return this.punchBusoExp;
	}

	public void addHardeningHakiExp(int i) {
		if (this.punchBusoExp + i >= Values.MAX_HAKI_EXP)
			return;
		if (this.punchBusoExp + i < 0)
			this.punchBusoExp = 0;
		else
			this.punchBusoExp = this.punchBusoExp + i;
	}

	public float getDamageMultiplier() {
		return this.damageMultiplier;
	}

	public void setDamageMultiplier(float i) {
		this.damageMultiplier = i;
	}

	public void setCombatMode(boolean value) {
		this.isInCombatMode = value;
	}

	public boolean isInCombatMode() {
		return this.isInCombatMode;
	}

	public int getDoriki() {
		return doriki;
	}

	public void alterDoriki(int i) {
		if (doriki + i < 0)
			doriki = 0;
		else
			doriki = doriki + i;
	}

	public void setDoriki(int i) {
		doriki = i;
	}

	public int getDorikiFromCommand() {
		return dorikiCmd;
	}

	public void alterDorikiFromCommand(int i) {
		if (dorikiCmd + i < 0)
			dorikiCmd = 0;
		else
			dorikiCmd = dorikiCmd + i;
	}

	public void setDorikiFromCommand(int i) {
		dorikiCmd = i;
	}

	public int getExtol() {
		return this.extol;
	}

	public void alterExtol(int i) {
		if (extol + i < 0)
			extol = 0;
		else
			extol = extol + i;
	}

	public void setExtol(int i) {
		this.extol = i;
	}

	public int getExtolFromCommand() {
		return this.extolCmd;
	}

	public void alterExtolFromCommand(int i) {
		if (extolCmd + i < 0)
			extolCmd = 0;
		else
			extolCmd = extolCmd + i;
	}

	public void setExtolFromCommand(int i) {
		this.extolCmd = i;
	}

	public int getBelly() {
		return this.belly;
	}

	public void alterBelly(int i) {
		if (belly + i < 0)
			belly = 0;
		else
			belly = belly + i;
	}

	public void setBelly(int i) {
		this.belly = i;
	}

	public int getBellyFromCommand() {
		return this.bellyCmd;
	}

	public void alterBellyFromCommand(int i) {
		if (bellyCmd + i < 0)
			bellyCmd = 0;
		else
			bellyCmd = bellyCmd + i;
	}

	public void setBellyFromCommand(int i) {
		this.bellyCmd = i;
	}

	public long getBounty() {
		return this.bounty;
	}

	public void alterBounty(long i) {
		if (bounty + i < 0)
			bounty = 0;
		else
			bounty = bounty + i;
	}

	public void setBounty(long i) {
		this.bounty = i;
	}

	public long getBountyFromCommand() {
		return this.bountyCmd;
	}

	public void alterBountyFromCommand(long i) {
		if (bountyCmd + i < 0)
			bountyCmd = 0;
		else
			bountyCmd = bountyCmd + i;
	}

	public void setBountyFromCommand(long i) {
		this.bountyCmd = i;
	}

	public int getCola() {
		return this.cola;
	}

	public void alterCola(int i) {
		if (cola + i < 0)
			cola = 0;
		else if (cola + i > getMaxCola())
			cola = getMaxCola();
		else
			cola = cola + i;
	}

	public void setCola(int i) {
		this.cola = i;
	}

	public int getUltraColaConsumed() {
		return this.ultraCola;
	}

	public void setUltraCola(int i) {
		this.ultraCola = i;
	}

	public void addUltraCola() {
		this.ultraCola++;
	}

	public int getMaxCola() {
		return this.maxCola;
	}

	public void setMaxCola(int maxCola) {
		this.maxCola = maxCola;
	}

	public boolean isLogia() {
		return this.isLogia;
	}

	public void setIsLogia(boolean i) {
		this.isLogia = i;
	}

	public String getUsedFruit() {
		return this.akumaNoMiUsed;
	}

	public boolean hasDevilFruit() {
		return !this.akumaNoMiUsed.isEmpty() && !this.akumaNoMiUsed.equalsIgnoreCase("n/a");
	}

	public void setUsedFruit(String name) {
		this.akumaNoMiUsed = name;
	}

	public boolean hasHeart() {
		return this.hasHeart;
	}

	public void setHasHeart(boolean b) {
		this.hasHeart = b;
	}

	public boolean hasShadow() {
		return this.hasShadow;
	}

	public void setHasShadow(boolean b) {
		this.hasShadow = b;
	}

	public void setGear(int i) {
		this.gear = i;
	}

	public int getGear() {
		return this.gear;
	}

	public String getFightStyle() {
		return this.fightStyle;
	}

	public boolean isSwordsman() {
		return this.fightStyle.equalsIgnoreCase("swordsman");
	}

	public boolean isSniper() {
		return this.fightStyle.equalsIgnoreCase("sniper");
	}

	public boolean isMedic() {
		return this.fightStyle.equalsIgnoreCase("doctor");
	}

	public boolean isWeatherWizard() {
		return this.fightStyle.equalsIgnoreCase("art of weather");
	}

	public boolean hasFightingStyle() {
		return !this.fightStyle.equalsIgnoreCase("n/a");
	}

	public void setFightStyle(String i) {
		this.fightStyle = i;
	}

	public String getRace() {
		return this.race;
	}

	public boolean isHuman() {
		return this.race.equalsIgnoreCase("human");
	}

	public boolean isFishman() {
		return this.race.equalsIgnoreCase("fishman");
	}

	public boolean isCyborg() {
		return this.race.equalsIgnoreCase("cyborg");
	}

	public boolean hasRace() {
		return !this.race.equalsIgnoreCase("n/a");
	}

	public void setRace(String i) {
		this.race = i;
	}

	public String getFaction() {
		return this.faction;
	}

	public boolean isPirate() {
		return this.faction.equalsIgnoreCase("pirate");
	}

	public boolean isMarine() {
		return this.faction.equalsIgnoreCase("marine");
	}

	public boolean isBountyHunter() {
		return this.faction.equalsIgnoreCase("bountyhunter");
	}

	public boolean isRevolutionary() {
		return this.faction.equalsIgnoreCase("revolutionary");
	}

	public boolean hasFaction() {
		return !this.faction.equalsIgnoreCase("n/a");
	}

	public void setFaction(String i) {
		this.faction = i;
	}

	public String getCrew() {
		return this.crew;
	}

	public void setCrew(String crewName) {
		this.crew = crewName;
	}

	public String getZoanPoint() {
		return this.zoanPoint;
	}

	public void setZoanPoint(String i) {
		this.zoanPoint = i;
	}

	public boolean isFirstTime() {
		return this.firstTime;
	}

	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}

	public void firstTimePass() {
		this.firstTime = false;
	}

	public boolean hasBusoHakiActive() {
		return hasBusoHakiActive;
	}

	public void triggerBusoHaki(boolean isBusoHakiActive) {
		this.hasBusoHakiActive = isBusoHakiActive;
	}

	public boolean hasKenHakiActive() {
		return hasKenHakiActive;
	}

	public void triggerKenHaki(boolean isKenHakiActive) {
		this.hasKenHakiActive = isKenHakiActive;
	}

	public boolean hasHakiActive() {
		return hasHakiActive;
	}

	public void triggerActiveHaki(boolean isHakiActive) {
		this.hasHakiActive = isHakiActive;
	}

	public int getHakiTimer() {
		return hakiTimer;
	}

	public void addHakiTimer() {
		hakiTimer++;
	}

	public void decHakiTimer() {
		hakiTimer--;
	}

	public void resetHakiTimer() {
		hakiTimer = 0;
	}

	public void setKilo(boolean kilo) {
		this.kilo = kilo;
	}

	public boolean getKilo() {
		return kilo;
	}

	public void setYamiPower(boolean bool) {
		this.hasYamiPower = bool;
	}

	public boolean hasYamiPower() {
		return hasYamiPower;
	}

	public void setColaBackpack(boolean bool) {
		this.hasColaBackpack = bool;
	}

	public boolean hasColaBackpack() {
		return hasColaBackpack;
	}

	public void setInAirWorld(boolean value) {
		this.isInAirWorld = value;
	}

	public boolean isInAirWorld() {
		return this.isInAirWorld;
	}

	public void setTempPreviousAbility(String temp) {
		this.tempPreviousAbility = temp;
	}

	public String getTempPreviousAbility() {
		return this.tempPreviousAbility;
	}
	
	public boolean addExtraEffect(String eff) {
		for (int i = 0; i < this.extraEffects.length; i++) {
			if (this.extraEffects[i] == null || this.extraEffects[i].isEmpty()) {
				this.extraEffects[i] = eff;
				return true;
			}
		}

		return false;
	}

	public void removeExtraEffects(String eff) {
		for (int i = 0; i < this.extraEffects.length; i++) {
			if (this.extraEffects[i] != null && !this.extraEffects[i].isEmpty()) {
				this.extraEffects[i] = null;
				break;
			}
		}
	}

	public boolean hasExtraEffects(String eff) {
		for (int i = 0; i < this.extraEffects.length; i++) {
			if (this.extraEffects[i] != null && !this.extraEffects[i].isEmpty()
					&& this.extraEffects[i].equalsIgnoreCase(eff)) {
				return true;
			}
		}

		return false;
	}

	public String[] getExtraEffects() {
		return this.extraEffects;
	}

	public int getGuiPage() {
		return guiPage;
	}

	public void setGuiPage(int guiPage) {
		this.guiPage = guiPage;
	}
}