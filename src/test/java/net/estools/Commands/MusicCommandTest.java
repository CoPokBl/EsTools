package net.estools.Commands;

import net.estools.EsToolsUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MusicCommandTest extends EsToolsUnitTest {

    @Test
    public void randomSong() {
        player.resetPlayingSound();
        executeCommand("music");
        Assertions.assertNotNull(player.getPlayingSound());
    }

    @Test
    public void specificSong() {
        player.resetPlayingSound();
        executeCommand("music", "cat");
        Assertions.assertNotNull(player.getPlayingSound());
    }
}
