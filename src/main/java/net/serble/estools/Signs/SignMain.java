package net.serble.estools.Signs;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

public class SignMain implements Listener {
    private static final HashMap<String, SignType> signs = new HashMap<>();
    private static final HashMap<String, String> signConversions = new HashMap<>();

    public static void init() {
        addSign(new Disposal(), "[disposal]", EsToolsCommand.translate("&1[Disposal]"));
        addSign(new Give(), "[give]", EsToolsCommand.translate("&1[Give]"));
        addSign(new Heal(), "[heal]", EsToolsCommand.translate("&1[Heal]"));
        addSign(new Feed(), "[feed]", EsToolsCommand.translate("&1[Feed]"));
        addSign(new Balance(), "[balance]", EsToolsCommand.translate("&1[Balance]"));
        addSign(new Repair(), "[repair]", EsToolsCommand.translate("&1[Repair]"));
        addSign(new Sell(), "[sell]", EsToolsCommand.translate("&1[Sell]"));

        Bukkit.getServer().getPluginManager().registerEvents(new SignMain(), Main.plugin);
    }

    public static void addSign(SignType signType, String conversion, String sign) {
        signConversions.put(conversion, sign);
        signs.put(sign, signType);
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void interact(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType().name().endsWith("SIGN")) {
            Sign state = (Sign)e.getClickedBlock().getState();

            SignType signType = signs.get(state.getLine(0));

            if (signType != null) {
                e.setCancelled(true);
                signType.run(e.getPlayer(), state.getLines());
            }
        }
    }

    @EventHandler
    public void changeSign(SignChangeEvent e) {
        if (!e.getPlayer().hasPermission("estools.signs")) {
            return;
        }

        String str = signConversions.get(e.getLine(0).toLowerCase());

        if (str != null) {
            e.setLine(0, str);
        }
    }
}
