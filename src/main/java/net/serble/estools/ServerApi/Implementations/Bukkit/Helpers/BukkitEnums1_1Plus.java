package net.serble.estools.ServerApi.Implementations.Bukkit.Helpers;

import net.serble.estools.ServerApi.EsTeleportCause;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Min version: 1.1
 */
@SuppressWarnings("unused")
public class BukkitEnums1_1Plus {

    public static PlayerTeleportEvent.TeleportCause toBukkitTeleportCause(EsTeleportCause esCause) {
        switch (esCause) {
            case EnderPearl:
                return PlayerTeleportEvent.TeleportCause.ENDER_PEARL;
            case Command:
                return PlayerTeleportEvent.TeleportCause.COMMAND;
            case Plugin:
                return PlayerTeleportEvent.TeleportCause.PLUGIN;
            case NetherPortal:
                return PlayerTeleportEvent.TeleportCause.NETHER_PORTAL;
            case EndPortal:
                return PlayerTeleportEvent.TeleportCause.END_PORTAL;
            case Spectate:
                return PlayerTeleportEvent.TeleportCause.SPECTATE;
            case EndGateway:
                return PlayerTeleportEvent.TeleportCause.END_GATEWAY;
            case ChorusFruit:
                return PlayerTeleportEvent.TeleportCause.CHORUS_FRUIT;
            case Dismount:
                return PlayerTeleportEvent.TeleportCause.DISMOUNT;
            case ExitBed:
                return PlayerTeleportEvent.TeleportCause.EXIT_BED;
            case Unknown:
                return PlayerTeleportEvent.TeleportCause.UNKNOWN;
            default:
                throw new IllegalArgumentException("Unknown EsTeleportCause: " + esCause);
        }
    }

    public static EsTeleportCause fromBukkitTeleportCause(PlayerTeleportEvent.TeleportCause bukkitCause) {
        switch (bukkitCause) {
            case ENDER_PEARL:
                return EsTeleportCause.EnderPearl;
            case COMMAND:
                return EsTeleportCause.Command;
            case PLUGIN:
                return EsTeleportCause.Plugin;
            case NETHER_PORTAL:
                return EsTeleportCause.NetherPortal;
            case END_PORTAL:
                return EsTeleportCause.EndPortal;
            case SPECTATE:
                return EsTeleportCause.Spectate;
            case END_GATEWAY:
                return EsTeleportCause.EndGateway;
            case CHORUS_FRUIT:
                return EsTeleportCause.ChorusFruit;
            case DISMOUNT:
                return EsTeleportCause.Dismount;
            case EXIT_BED:
                return EsTeleportCause.ExitBed;
            case UNKNOWN:
                return EsTeleportCause.Unknown;
            default:
                throw new IllegalArgumentException("Invalid TeleportCause");
        }
    }
}
