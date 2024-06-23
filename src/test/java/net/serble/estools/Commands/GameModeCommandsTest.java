package net.serble.estools.Commands;

import net.serble.estools.EsToolsUnitTest;
import net.serble.estools.ServerApi.EsGameMode;
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
}
