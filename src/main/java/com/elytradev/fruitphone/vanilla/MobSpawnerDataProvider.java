package com.elytradev.fruitphone.vanilla;

import java.util.List;

import com.elytradev.concrete.reflect.accessor.Accessor;
import com.elytradev.concrete.reflect.accessor.Accessors;
import com.elytradev.probe.api.IProbeData;
import com.elytradev.probe.api.UnitDictionary;
import com.elytradev.probe.api.impl.ProbeData;

import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class MobSpawnerDataProvider implements VanillaDataProvider<TileEntityMobSpawner> {
	private Accessor<Integer> delay      = Accessors.findField(MobSpawnerBaseLogic.class, "field_98286_b", "spawnDelay",    "a");
	private Accessor<Integer> maxDelay   = Accessors.findField(MobSpawnerBaseLogic.class, "field_98293_h", "maxSpawnDelay", "g");
	private Accessor<Integer> spawnCount = Accessors.findField(MobSpawnerBaseLogic.class, "field_98294_i", "spawnCount",    "h");
	
	@Override
	public void provideProbeData(TileEntityMobSpawner te, List<IProbeData> li) {
		ITextComponent entityName = te.getSpawnerBaseLogic().getCachedEntity().getDisplayName();
		Integer count = spawnCount.get(te.getSpawnerBaseLogic());
		TextComponentTranslation entityLine = new TextComponentTranslation("fruitphone.mobSpawner.mob", count, entityName);
		li.add(new ProbeData().withLabel(entityLine));
		
		int ticksLeft = delay.get(te.getSpawnerBaseLogic());
		int ticksMax = maxDelay.get(te.getSpawnerBaseLogic());
		if (ticksMax<=ticksLeft) ticksMax=ticksLeft;
		li.add(new ProbeData()
				.withLabel(new TextComponentTranslation("fruitphone.mobSpawner.timeLeft"))
				.withBar(0, ticksLeft, ticksMax, UnitDictionary.TICKS));
	}

}
