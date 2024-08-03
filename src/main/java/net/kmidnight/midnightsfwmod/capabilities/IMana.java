package net.kmidnight.midnightsfwmod.capabilities;

public interface IMana {
    void consume(float points);
    void fill(float points);
    void set(float points);
    float getMana();
}
