package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestPlayer;
import net.estools.ServerApi.EsPotionEffect;
import net.estools.ServerApi.EsPotionEffectType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// /eff <effect> [amplifier] [duration] [players]
public class EffCommandTest extends EsToolsUnitTest {

    @Test
    public void noArgs() {
        executeAssertOneError("eff");
    }

    @Test
    public void invalidEffect() {
        executeAssertOneError("eff invalid");
    }

    @Test
    public void validEffect() {
        player.clearPotionEffects();
        executeAssertSuccess("eff speed");

        EsPotionEffectType speed = EsPotionEffectType.fromKey("speed");
        Assertions.assertEquals(1, player.getActivePotionEffects().size());

        EsPotionEffect effect = player.getActivePotionEffects().get(0);
        Assertions.assertEquals(speed, effect.getType());
    }

    @Test
    public void invalidAmplifier() {
        executeAssertOneError("eff speed invalid");
    }

    @Test
    public void validAmplifier() {
        player.clearPotionEffects();
        executeAssertSuccess("eff speed 2");

        EsPotionEffectType speed = EsPotionEffectType.fromKey("speed");
        Assertions.assertEquals(1, player.getActivePotionEffects().size());

        EsPotionEffect effect = player.getActivePotionEffects().get(0);
        Assertions.assertEquals(speed, effect.getType());
        Assertions.assertEquals(1, effect.getAmp());  // Amp is inp-1
    }

    @Test
    public void invalidDuration() {
        executeAssertOneError("eff speed 1 invalid");
    }

    @Test
    public void validDuration() {
        player.clearPotionEffects();
        executeAssertSuccess("eff speed 2 1");

        EsPotionEffectType speed = EsPotionEffectType.fromKey("speed");
        Assertions.assertEquals(1, player.getActivePotionEffects().size());

        EsPotionEffect effect = player.getActivePotionEffects().get(0);
        Assertions.assertEquals(speed, effect.getType());
        Assertions.assertEquals(1, effect.getAmp());
        Assertions.assertEquals(20, effect.getDuration());
    }

    @Test
    public void invalidPlayer() {
        executeAssertOneError("eff speed 2 1 invalid");
    }

    @Test
    public void validPlayer() {
        TestPlayer other = createPlayer();
        other.clearPotionEffects();
        executeAssertSuccess("eff speed 2 1 " + other.getName());

        EsPotionEffectType speed = EsPotionEffectType.fromKey("speed");
        Assertions.assertEquals(1, other.getActivePotionEffects().size());

        EsPotionEffect effect = other.getActivePotionEffects().get(0);
        Assertions.assertEquals(speed, effect.getType());
        Assertions.assertEquals(1, effect.getAmp());
        Assertions.assertEquals(20, effect.getDuration());
    }
}
