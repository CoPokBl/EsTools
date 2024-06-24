package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import net.estools.Implementation.TestPlayer;
import net.estools.ServerApi.EsGameMode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameModeCommandsTest extends EsToolsUnitTest {

    @Test
    public void survival() {
        player.setGameMode(EsGameMode.Creative);
        executeCommand("gms");
        Assertions.assertSame(player.getGameMode(), EsGameMode.Survival);
    }

    @Test
    public void creative() {
        player.setGameMode(EsGameMode.Survival);
        executeCommand("gmc");
        Assertions.assertSame(player.getGameMode(), EsGameMode.Creative);
    }

    @Test
    public void adventure() {
        player.setGameMode(EsGameMode.Survival);
        executeCommand("gma");
        Assertions.assertSame(player.getGameMode(), EsGameMode.Adventure);
    }

    @Test
    public void spectator() {
        player.setGameMode(EsGameMode.Survival);
        executeCommand("gmsp");
        Assertions.assertSame(player.getGameMode(), EsGameMode.Spectator);
    }

    @Test
    public void survivalOther() {
        TestPlayer p2 = createPlayer();
        p2.setGameMode(EsGameMode.Creative);
        executeCommand("gms", p2.getName());
        Assertions.assertSame(p2.getGameMode(), EsGameMode.Survival);
    }

    @Test
    public void creativeOther() {
        TestPlayer p2 = createPlayer();
        p2.setGameMode(EsGameMode.Survival);
        executeCommand("gmc", p2.getName());
        Assertions.assertSame(p2.getGameMode(), EsGameMode.Creative);
    }

    @Test
    public void adventureOther() {
        TestPlayer p2 = createPlayer();
        p2.setGameMode(EsGameMode.Survival);
        executeCommand("gma", p2.getName());
        Assertions.assertSame(p2.getGameMode(), EsGameMode.Adventure);
    }

    @Test
    public void spectatorOther() {
        TestPlayer p2 = createPlayer();
        p2.setGameMode(EsGameMode.Survival);
        executeCommand("gmsp", p2.getName());
        Assertions.assertSame(p2.getGameMode(), EsGameMode.Spectator);
    }

    @Test
    public void survivalInvalid() {
        executeAssertError("gms invalid");
    }

    @Test
    public void creativeInvalid() {
        executeAssertError("gmc invalid");
    }

    @Test
    public void adventureInvalid() {
        executeAssertError("gma invalid");
    }

    @Test
    public void spectatorInvalid() {
        executeAssertError("gmsp invalid");
    }
}
