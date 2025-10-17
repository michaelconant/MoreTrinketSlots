package moretrinketslots.patches.server;

import moretrinketslots.modclasses.settings.MTSConfig;
import moretrinketslots.modclasses.settings.MTSPacket;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.Packet;
import necesse.engine.network.networkInfo.NetworkInfo;
import necesse.engine.network.packet.PacketClientInstalledDLC;
import necesse.engine.network.server.Server;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = Server.class, name = "addClient", arguments = {NetworkInfo.class, long.class, String.class, boolean.class, boolean.class, PacketClientInstalledDLC.class})
public class AddClientPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This Server server, @Advice.Return(readOnly = false) boolean result) {
        if (result) {
            System.out.println(MTSConfig.modName + ": sending MTSPacket to new client");
            server.network.sendToAllClients((Packet) new MTSPacket());
        }
    }
}