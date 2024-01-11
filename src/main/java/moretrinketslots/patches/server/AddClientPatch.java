package moretrinketslots.patches.server;

import moretrinketslots.modclasses.settings.MTSPacket;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.Packet;
import necesse.engine.network.networkInfo.NetworkInfo;
import necesse.engine.network.server.Server;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = Server.class, name = "addClient", arguments = {NetworkInfo.class, long.class, String.class, boolean.class})
public class AddClientPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This Server server, @Advice.Return(readOnly = false) boolean result) {
        if (result) {
            server.network.sendToAllClients((Packet) new MTSPacket());
        }
    }
}