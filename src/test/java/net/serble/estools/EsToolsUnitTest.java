package net.serble.estools;

import net.serble.estools.Implementation.TestPlayer;
import net.serble.estools.Implementation.TestServer;
import net.serble.estools.Implementation.TestWorld;
import net.serble.estools.ServerApi.Interfaces.EsEvent;
import net.serble.estools.ServerApi.ServerPlatform;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")  // Future proof
public class EsToolsUnitTest {
    public static TestPlayer player;
    public static TestWorld world;
    public static final String playerName = "UnitTester";
    public static SemanticVersion minecraftVersion = new SemanticVersion(1, 21, 0);
    public static SemanticVersion pluginVersion = new SemanticVersion(999, 999, 999);  // Large so it doesn't update
    private static final List<String> console = new ArrayList<>();
    public static final List<TestPlayer> players = new ArrayList<>();

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

    @BeforeAll
    public static void initEnvironment() {
        Main.server = new TestServer();  // Inject the server platform directly
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

    public TestPlayer createPlayer() {  // With random name
        TestPlayer p = new TestPlayer(world, "Player" + (int) (Math.random() * 1000));
        players.add(p);
        world.addEntity(p);
        return p;
    }
}
