package moretrinketslots.commands;

import moretrinketslots.modclasses.items.LootTableFunctions;
import moretrinketslots.modclasses.items.TrinketSlotsItemConfig;
import moretrinketslots.modclasses.settings.MTSConfig;
import moretrinketslots.modclasses.settings.MTSPacket;
import moretrinketslots.modclasses.settings.MTSWorldFile;
import necesse.engine.commands.*;
import necesse.engine.commands.parameterHandlers.IntParameterHandler;
import necesse.engine.commands.parameterHandlers.PresetStringParameterHandler;
import necesse.engine.localization.message.GameMessageBuilder;
import necesse.engine.network.Packet;
import necesse.engine.network.client.Client;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.entity.mobs.hostile.bosses.*;

import java.util.Map;
import java.util.TreeMap;

public class LoadPresetCommand extends ModularChatCommand {
    public LoadPresetCommand() {
        super(
                MTSConfig.modAcronym + ".loadpreset",
                "Changes " + MTSConfig.modAcronym + " settings back to the default",
                PermissionLevel.OWNER,
                false,
                new CmdParameter[] {
                        new CmdParameter("default/vanilla/nogain/zerotohero", new PresetStringParameterHandler(
                                "default",
                                "vanilla",
                                "nogain",
                                "zerotohero"
                        ), false),
                        new CmdParameter(
                                "subpreset number",
                                new IntParameterHandler(
                                        -1
                                ),
                                true
                        )
                }
        );
    }

    @Override
    public void runModular(Client client, Server server, ServerClient serverClient, Object[] args, String[] errors, CommandLog logs) {
        String presetOption = ((String) args[0]).toLowerCase();
        int subPresetNumber = (int)args[1];
        GameMessageBuilder msg = new GameMessageBuilder();
        LootTableFunctions.removeItemsFromLootTables();
        int initialSlots = 0;
        Map<String, TrinketSlotsItemConfig> items = new TreeMap<String, TrinketSlotsItemConfig>();
        switch (presetOption) {
            case "vanilla":
                initialSlots = 4;
                items.put("voidwizard", new TrinketSlotsItemConfig("emptypendant", 5, 5, 1));
                items.put("piratecaptain", new TrinketSlotsItemConfig("piratesheath", 6, 6, 1));
                items.put("fallenwizard", new TrinketSlotsItemConfig("wizardsocket", 7, 7, 1));
                break;
            case "zerotohero":
                //default to 6 setting
                if (subPresetNumber < 4) {
                    subPresetNumber = 6;
                }

                //10 or 12 max slots
                if (subPresetNumber >= 10) {
                    //1 slot for each boss before and including Fallen Wizard
                    items.put("evilsprotector", new TrinketSlotsItemConfig(MTSConfig.itemPrefix + "evilsprotector", 1, 1, 1));
                    items.put("queenspider", new TrinketSlotsItemConfig(MTSConfig.itemPrefix + "queenspider", 2, 2, 1));
                    items.put("voidwizard", new TrinketSlotsItemConfig("emptypendant", 3, 3, 1));
                    items.put("swampguardian", new TrinketSlotsItemConfig(MTSConfig.itemPrefix + "swampguardian", 4, 4, 1));
                    items.put("ancientvulture", new TrinketSlotsItemConfig(MTSConfig.itemPrefix + "ancientvulture", 5, 5, 1));
                    items.put("reaper", new TrinketSlotsItemConfig(MTSConfig.itemPrefix + "reaper", 6, 6, 1));
                    items.put("cryoqueen", new TrinketSlotsItemConfig(MTSConfig.itemPrefix + "cryoqueen", 7, 7, 1));
                    items.put("pestwarden", new TrinketSlotsItemConfig(MTSConfig.itemPrefix + "pestwarden", 8, 8, 1));
                    items.put(FlyingSpiritsHead.class.getName(), new TrinketSlotsItemConfig(MTSConfig.itemPrefix + "sageandgrit", 9, 9, 1));
                    items.put("fallenwizard", new TrinketSlotsItemConfig("wizardsocket", 10, 10, 1));
                    if (subPresetNumber >= 11) {
                        items.put("motherslime", new TrinketSlotsItemConfig(MTSConfig.itemPrefix + "motherslime", 11, 11, 1));
                    }
                    if (subPresetNumber >= 12) {
                        items.put("nightswarm", new TrinketSlotsItemConfig(MTSConfig.itemPrefix + "nightswarm", 12, 12, 1));
                        subPresetNumber = 12;
                    }
                    break;
                }

                //8 max slots
                if (subPresetNumber >= 8) {
                    subPresetNumber = 8;
                    items.put("evilsprotector", new TrinketSlotsItemConfig(MTSConfig.itemPrefix + "evilsprotector", 1, 1, 1));
                    items.put("queenspider", new TrinketSlotsItemConfig(MTSConfig.itemPrefix + "queenspider", 2, 2, 1));
                    items.put("voidwizard", new TrinketSlotsItemConfig("emptypendant", 3, 3, 1));
                    items.put("swampguardian", new TrinketSlotsItemConfig(MTSConfig.itemPrefix + "swampguardian", 4, 4, 1));
                    items.put("ancientvulture", new TrinketSlotsItemConfig(MTSConfig.itemPrefix + "ancientvulture", 5, 5, 1));
                    items.put("reaper", new TrinketSlotsItemConfig(MTSConfig.itemPrefix + "reaper", 6, 6, 1));
                    items.put("pestwarden", new TrinketSlotsItemConfig(MTSConfig.itemPrefix + "pestwarden", 7, 7, 1));
                    items.put("fallenwizard", new TrinketSlotsItemConfig("wizardsocket", 8, 8, 1));
                    break;
                }

                //6 max slots
                if (subPresetNumber >= 6) {
                    subPresetNumber = 6;
                    items.put("evilsprotector", new TrinketSlotsItemConfig(MTSConfig.itemPrefix + "evilsprotector", 1, 1, 1));
                    items.put("voidwizard", new TrinketSlotsItemConfig("emptypendant", 2, 2, 1));
                    items.put("ancientvulture", new TrinketSlotsItemConfig(MTSConfig.itemPrefix + "ancientvulture", 3, 3, 1));
                    items.put("reaper", new TrinketSlotsItemConfig(MTSConfig.itemPrefix + "reaper", 4, 4, 1));
                    items.put("pestwarden", new TrinketSlotsItemConfig(MTSConfig.itemPrefix + "pestwarden", 5, 5, 1));
                    items.put("fallenwizard", new TrinketSlotsItemConfig("wizardsocket", 6, 6, 1));
                    break;
                }

                //4 max slots
                subPresetNumber = 4;
                items.put("evilsprotector", new TrinketSlotsItemConfig(MTSConfig.itemPrefix + "evilsprotector", 1, 1, 1));
                items.put("swampguardian", new TrinketSlotsItemConfig(MTSConfig.itemPrefix + "swampguardian", 2, 2, 1));
                items.put("cryoqueen", new TrinketSlotsItemConfig(MTSConfig.itemPrefix + "cryoqueen", 3, 3, 1));
                items.put("fallenwizard", new TrinketSlotsItemConfig("wizardsocket", 4, 4, 1));
                break;
            case "nogain":
                //default to 4 setting
                if (subPresetNumber < 0) {
                    subPresetNumber = 4;
                }
                initialSlots = subPresetNumber;
                break;
            default:
                initialSlots = 4;
                items = MTSConfig.getDefaultItems();
                break;
        }
        MTSConfig.set(initialSlots, items);
        MTSWorldFile.saveSettings(server.world);
        LootTableFunctions.addItemsToLootTables();
        msg.append(String.format("Changed %s settings to %s", MTSConfig.modAcronym, presetOption));
        if (presetOption.equals("zerotohero") || presetOption.equals("nogain")) {
            msg.append(String.format(" %d", subPresetNumber));
        }
        logs.add(msg);

        //send updated settings to clients (for when the server allows outside characters)
        server.network.sendToAllClients((Packet) new MTSPacket());
    }
}
//Default Settings
/*
        items.put("evilsprotector", new TrinketSlotsItemConfig(itemPrefix + "evilsprotector", 0, 0, 1));
        items.put("queenspider", new TrinketSlotsItemConfig(itemPrefix + "queenspider", 0, 0, 1));
        items.put("voidwizard", new TrinketSlotsItemConfig("emptypendant", 5, 5, 1));
        items.put("swampguardian", new TrinketSlotsItemConfig(itemPrefix + "swampguardian", 0, 0, 1));
        items.put("ancientvulture", new TrinketSlotsItemConfig(itemPrefix + "ancientvulture", 6, 6, 1));
        items.put("piratecaptain", new TrinketSlotsItemConfig("piratesheath", 0, 0, 1));
        items.put("reaper", new TrinketSlotsItemConfig(itemPrefix + "reaper", 0, 0, 1));
        items.put("cryoqueen", new TrinketSlotsItemConfig(itemPrefix + "cryoqueen", 7, 7, 1));
        items.put("pestwarden", new TrinketSlotsItemConfig(itemPrefix + "pestwarden", 0, 0, 1));
        items.put("flyingspiritsbody", new TrinketSlotsItemConfig(itemPrefix + "flyingspiritsbody", 0, 0, 1));
        items.put("fallenwizard", new TrinketSlotsItemConfig("wizardsocket", 8, 8, 1));
        items.put("motherslime", new TrinketSlotsItemConfig(itemPrefix + "motherslime", 0, 0, 1));
        items.put("nightswarm", new TrinketSlotsItemConfig(itemPrefix + "nightswarm", 0, 0, 1));
 */