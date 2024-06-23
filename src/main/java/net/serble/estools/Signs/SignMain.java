package net.serble.estools.Signs;

import net.serble.estools.EsToolsCommand;
import net.serble.estools.Main;
import net.serble.estools.SemanticVersion;
import net.serble.estools.ServerApi.EsAction;
import net.serble.estools.ServerApi.EsEventListener;
import net.serble.estools.ServerApi.EsEventResult;
import net.serble.estools.ServerApi.Events.EsPlayerInteractEvent;
import net.serble.estools.ServerApi.Events.EsSignChangeEvent;
import net.serble.estools.ServerApi.Interfaces.EsEvent;
import net.serble.estools.ServerApi.Interfaces.EsSign;
import net.serble.estools.Tuple;

import java.util.HashMap;

public class SignMain implements EsEventListener {
    private static final HashMap<String, SignType> signs = new HashMap<>();
    private static final HashMap<String, Tuple<String, SemanticVersion>> signConversions = new HashMap<>();

    public static void init() {
        addSign(new Disposal(), "[disposal]", EsToolsCommand.translate("&1[Disposal]"), new SemanticVersion(1, 2, 0));
        addSign(new Give(), "[give]", EsToolsCommand.translate("&1[Give]"), new SemanticVersion(1, 1, 0));
        addSign(new Heal(), "[heal]", EsToolsCommand.translate("&1[Heal]"), new SemanticVersion(1, 1, 0));
        addSign(new Feed(), "[feed]", EsToolsCommand.translate("&1[Feed]"), new SemanticVersion(1, 1, 0));
        addSign(new Repair(), "[repair]", EsToolsCommand.translate("&1[Repair]"), new SemanticVersion(1, 1, 0));

        Main.registerEvents(new SignMain());
    }

    public static void addSign(SignType signType, String conversion, String sign, SemanticVersion minVersion) {
        signConversions.put(conversion, new Tuple<>(sign, minVersion));
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

        Tuple<String, SemanticVersion> conversion = signConversions.get(e.getLine(0).toLowerCase());

        if (conversion != null) {
            if (Main.minecraftVersion.isAtLeast(conversion.b())) {
                e.setLine(0, conversion.a());
            } else {
                EsToolsCommand.send(e.getPlayer(), "&cThis sign is requires minecraft version %s or higher", conversion.b().toString());
            }
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
