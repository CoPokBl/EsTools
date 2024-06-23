package net.serble.estools.ServerApi.Implementations.Bukkit.Helpers;

import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsSound;
import org.bukkit.Registry;
import org.bukkit.Sound;

import java.util.HashSet;

/**
 * Min version: 1.0
 */
public class BukkitSoundHelper {

    public static HashSet<EsSound> getSounds() {
        HashSet<EsSound> sounds = new HashSet<>();

        if (Main.minecraftVersion.isAtLeast(1, 16, 4)) {
            for (Sound sound : Registry.SOUNDS) {
                EsSound esSound = EsSound.createUnchecked(sound.getKey().getKey());
                sounds.add(esSound);
            }
        } else if (Main.minecraftVersion.isAtLeast(1, 3, 0)) {
            for (Sound sound : Sound.values()) {
                EsSound esSound = BukkitSoundEnumConverter.convertEnumToKey(sound.name());
                sounds.add(esSound);
            }
        }  // Sounds do not exist in the same way in pre 1.3

        return sounds;
    }
}
