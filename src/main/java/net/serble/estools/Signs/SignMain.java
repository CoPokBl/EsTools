package net.serble.estools.Signs;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import net.serble.estools.ServerApi.EsAction;
import net.serble.estools.ServerApi.EsEventListener;
import net.serble.estools.ServerApi.EsEventResult;
import net.serble.estools.ServerApi.Events.EsPlayerInteractEvent;
import net.serble.estools.ServerApi.Events.EsSignChangeEvent;
import net.serble.estools.ServerApi.Interfaces.EsEvent;
import net.serble.estools.ServerApi.Interfaces.EsSign;

import java.util.HashMap;

public class SignMain implements EsEventListener {
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

        Main.registerEvents(new SignMain());
    }

    public static void addSign(SignType signType, String conversion, String sign) {
        signConversions.put(conversion, sign);
        signs.put(sign, signType);
    }

    public void interact(EsPlayerInteractEvent e) {
        if (e.getAction().equals(EsAction.RightClickBlock) && e.getClickedBlock() instanceof EsSign) {
            EsSign state = (EsSign) e.getClickedBlock();

            SignType signType = signs.get(state.getLine(0));

            if (signType != null) {
                e.setUseInteractedBlock(EsEventResult.DENY);
                signType.run(e.getPlayer(), state.getLines());
            }
        }
    }

    public void changeSign(EsSignChangeEvent e) {
        if (!e.getPlayer().hasPermission("estools.signs")) {
            return;
        }

        String str = signConversions.get(e.getLine(0).toLowerCase());

        if (str != null) {
            e.setLine(0, str);
        }
    }

    @Override
    public void executeEvent(EsEvent event) {
        if (event instanceof EsPlayerInteractEvent) {
            interact((EsPlayerInteractEvent) event);
            return;
        }

        if (event instanceof EsSignChangeEvent) {
            changeSign((EsSignChangeEvent) event);
        }
    }
}
