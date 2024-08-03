package net.kmidnight.midnightsfwmod.capabilities;

public class Stamina implements IStamina{
    private float stamina;

    @Override
    public void consume(float points) {
        this.stamina = Math.max(0, this.stamina - points);
    }

    @Override
    public void fill(float points) {
        this.stamina += points;
    }

    @Override
    public void set(float points) {
        this.stamina = points;
    }

    @Override
    public float getStamina() {
        return this.stamina;
    }
}
