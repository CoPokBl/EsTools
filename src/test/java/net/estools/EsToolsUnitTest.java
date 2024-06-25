package net.estools;

import net.estools.Implementation.TestItemStack;
import net.estools.Implementation.TestPlayer;
import net.estools.Implementation.TestServer;
import net.estools.Implementation.TestWorld;
import net.estools.ServerApi.EsLocation;
import net.estools.ServerApi.EsMaterial;
import net.estools.ServerApi.Interfaces.EsEvent;
import net.estools.ServerApi.ServerPlatform;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")  // Future-proof
public class EsToolsUnitTest {
    public static TestPlayer player;
    public static TestWorld world;
    public static final String playerName = "UnitTester";
    public static SemanticVersion minecraftVersion = new SemanticVersion(1, 21, 0);
    public static SemanticVersion pluginVersion = new SemanticVersion(999, 999, 999);  // Large so it doesn't update
    private static final List<String> console = new ArrayList<>();
    public static final List<TestPlayer> players = new ArrayList<>();
    public static final List<Runnable> waitingTasks = new ArrayList<>();

    public @NotNull String[] executeCommand(String label, String... args) {
        int initialPlayerChatIndex = player.getChatMessages().size();

        // Execute it
        Main.executeCommand(player, label, args);

        // Get the chat messages that were sent
        List<String> chatMessages = player.getChatMessages();
        List<String> newMessages = new ArrayList<>();
        for (int i = initialPlayerChatIndex; i < chatMessages.size(); i++) {
            newMessages.add(chatMessages.get(i));
        }
        return newMessages.toArray(new String[0]);
    }

    // Run the other overload but split the first space out and use that as label
    public @NotNull String[] executeCommand(String line) {
        String[] parts = line.split(" ", 2);
        return executeCommand(parts[0], parts.length > 1 ? parts[1].split(" ") : new String[0]);
    }

    /**
     * Execute the specific command and assert that the command will send an error message,
     * which is the last message sent by the player. There should only be 1 error.
     * <p>
     * A Java exception will be counted as a bug, not an intended error.
     * Errors are counted as messages starting with "§c" (Red).
     *
     * @param line The command to execute
     */
    public void executeAssertOneError(String line) {
        String[] feedback = executeCommand(line);
        assertError(feedback, true);
        Assertions.assertEquals(1, feedback.length);
    }

    public void executeAssertError(String line) {
        assertError(executeCommand(line), true);
    }

    public void executeAssertSuccess(String line) {
        assertError(executeCommand(line), false);
    }

    public void assertError(String[] feedback, boolean shouldError) {
        Assertions.assertNotEquals(0, feedback.length);
        boolean errored = stripReset(feedback[feedback.length-1]).trim().startsWith("§c");
        String msg = "Expected error message, got: " + stripReset(feedback[feedback.length-1]).trim();
        Assertions.assertEquals(shouldError, errored, msg);
    }

    @BeforeAll
    public static void initEnvironment() {
        // Delete temp folder so we have no data
        File tempFolder = new File(System.getProperty("java.io.tmpdir"), "EsTools");
        Utils.deleteFolder(tempFolder);

        Main.server = new TestServer();  // Inject the server platform directly
        Main.disableUpdater = true;  // Otherwise we will get rate limited
        Main main = new Main(ServerPlatform.Injected, null);
        main.enable();

        world = new TestWorld("testingworld");
        player = new TestPlayer(world, playerName);

        players.add(player);
    }

    public static void consolePrint(String line) {
        console.add(line);
        System.out.println(line);
    }

    public static List<String> getConsole() {
        return console;
    }

    public void triggerEvent(EsEvent event) {
        Main.callEvent(event);
    }

    public String stripColour(String msg) {
        return msg.replaceAll("§[0-9a-fA-F]", "");
    }

    public String stripReset(String msg) {
        return msg.replaceAll("§r", "");
    }

    public TestPlayer createPlayer() {  // With random name
        TestPlayer p = new TestPlayer(world, "Player" + (int) (Math.random() * 1000));
        players.add(p);
        world.addEntity(p);
        return p;
    }

    /** Run all timed tasks that have been scheduled. */
    public void tickTime() {
        for (Runnable runnable : waitingTasks) {
            runnable.run();
        }
        waitingTasks.clear();
    }

    public TestItemStack genTestItem() {
        return new TestItemStack(EsMaterial.fromKey("stone"), 1);
    }

    public EsLocation loc(double x, double y, double z) {
        return new EsLocation(world, x, y, z);
    }

    public void go(double x, double y, double z) {
        player.teleport(loc(x, y, z));
    }
}
