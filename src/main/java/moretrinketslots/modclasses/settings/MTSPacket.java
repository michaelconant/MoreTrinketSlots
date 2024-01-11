package moretrinketslots.modclasses.settings;

import necesse.engine.commands.PermissionLevel;
import necesse.engine.network.NetworkPacket;
import necesse.engine.network.Packet;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.client.Client;
import necesse.engine.network.packet.PacketPermissionUpdate;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;

public class MTSPacket extends Packet {

    public MTSPacket(byte[] data) {
        super(data);
    }

    public MTSPacket() {
        PacketWriter writer = new PacketWriter(this);
        MTSWorldFile.setupContentPacket(writer);
    }

    public void processServer(NetworkPacket packet, Server server, ServerClient client) {
        if (client.getPermissionLevel().getLevel() >= PermissionLevel.ADMIN.getLevel()) {
            MTSWorldFile.applyContentPacket(new PacketReader(this));
            server.network.sendToAllClients(this);
            MTSWorldFile.saveSettings(server.world);
            System.out.println("processServer, changed mod settings");
        } else {
            System.out.println(client.getName() + "tried to change " + MTSConfig.modAcronym + " settings without permissions");
            server.network.sendPacket(new MTSPacket(), client);
            server.network.sendPacket(new PacketPermissionUpdate(client.getPermissionLevel()), client);
        }
    }

    public void processClient(NetworkPacket packet, Client client) {
        MTSWorldFile.applyContentPacket(new PacketReader(this));
    }
}