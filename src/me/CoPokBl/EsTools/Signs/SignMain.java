package me.CoPokBl.EsTools.Signs;

import me.CoPokBl.EsTools.CMD;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

public class SignMain implements Listener {

    static HashMap<String, SignType> signs = new HashMap<>();
    static HashMap<String, String> signConversions = new HashMap<>();

    public static void init() {
        addSign(new Disposal(), "[disposal]", CMD.t("&1[Disposal]"));
        addSign(new Give(), "[give]", CMD.t("&1[Give]"));
        addSign(new Heal(), "[heal]", CMD.t("&1[Heal]"));
        addSign(new Feed(), "[feed]", CMD.t("&1[Feed]"));
        addSign(new Balance(), "[balance]", CMD.t("&1[Balance]"));
        addSign(new Repair(), "[repair]", CMD.t("&1[Repair]"));
        addSign(new Sell(), "[sell]", CMD.t("&1[Sell]"));
    }

    public static void addSign(SignType st, String conv, String sign) {
        signConversions.put(conv, sign);
        signs.put(sign, st);
    }

    @EventHandler
    public void interact(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType().name().endsWith("SIGN")) {
            Sign s = (Sign)e.getClickedBlock().getState();

            SignType st = signs.get(s.getLine(0));

            if (st != null) {
                e.setCancelled(true);
                st.run(e.getPlayer(), s.getLines());
            }
        }
    }

    @EventHandler
    public void changeSign(SignChangeEvent e) {
        if (!e.getPlayer().hasPermission("estools.signs"))
            return;

        String str = signConversions.get(e.getLine(0).toLowerCase());

        if (str != null) {
            e.setLine(0, str);
        }
    }
}
