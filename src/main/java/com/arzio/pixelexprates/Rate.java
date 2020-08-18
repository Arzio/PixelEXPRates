package com.arzio.pixelexprates;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class Rate {

    @Setting
    private int minLevel;

    @Setting
    private int maxLevel;

    @Setting
    private double expMultiplier;
	
    /* Mandatory empty constructor for Configurate compatibility */
    public Rate() {

    }

    public Rate(int minLevel, int maxLevel, double multiplier) {
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.expMultiplier = multiplier;
    }

    public boolean isInStage(Pokemon pixelmon) {
        int level = pixelmon.getLevel();
        return level >= this.minLevel && level <= this.maxLevel;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public double getExpMultiplier() {
        return expMultiplier;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "minLevel=" + minLevel +
                ", maxLevel=" + maxLevel +
                ", expMultiplier=" + expMultiplier +
                '}';
    }
}
