package net.kmidnight.midnightsfwmod.capabilities;

public class Mana implements IMana{
    private float mana;

    @Override
    public void consume(float points) {
        this.mana = Math.max(0, this.mana - points);
    }

    @Override
    public void fill(float points) {
        this.mana += points;
    }

    @Override
    public void set(float points) {
        this.mana = points;
    }

    @Override
    public float getMana() {
        return this.mana;
    }
}
