package net.serble.estools.ServerApi;

@SuppressWarnings("unused")  // Needs the methods for serialiser
public class EsPotionEffect {
    private EsPotionEffectType type;
    private int amp;
    private int duration;

    public EsPotionEffect(EsPotionEffectType type, int amp, int duration) {
        this.type = type;
        this.amp = amp;
        this.duration = duration;
    }

    public EsPotionEffect() {  // Serialiser needs this

    }

    public int getAmp() {
        return amp;
    }

    public int getDuration() {
        return duration;
    }

    public EsPotionEffectType getType() {
        return type;
    }

    public void setAmp(int amp) {
        this.amp = amp;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setType(EsPotionEffectType type) {
        this.type = type;
    }
}
