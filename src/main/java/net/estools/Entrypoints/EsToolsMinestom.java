package net.estools.Entrypoints;

import net.estools.Main;
import net.estools.ServerApi.ServerPlatform;

public class EsToolsMinestom {

    public static void enable() {
        Main main = new Main(ServerPlatform.Minestom, null);
        main.enable();
    }
}
