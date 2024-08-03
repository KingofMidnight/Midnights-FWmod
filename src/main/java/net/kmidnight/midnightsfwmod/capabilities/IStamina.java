package net.kmidnight.midnightsfwmod.capabilities;

public interface IStamina {
    void consume(float points);
    void fill(float points);
    void set(float points);
    float getStamina();
}
