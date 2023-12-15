package moretrinketslots.commands;

import moretrinketslots.modclasses.items.TrinketSlotsItemConfig;
import moretrinketslots.modclasses.settings.MTSConfig;
import necesse.engine.commands.CmdParameter;
import necesse.engine.commands.CommandLog;
import necesse.engine.commands.ModularChatCommand;
import necesse.engine.commands.PermissionLevel;
import necesse.engine.commands.parameterHandlers.PresetStringParameterHandler;
import necesse.engine.localization.message.GameMessageBuilder;
import necesse.engine.network.client.Client;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.ItemRegistry;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.Mob;
import necesse.gfx.GameColor;

import java.util.*;
import java.util.stream.Collectors;

public class InfoCommand extends ModularChatCommand {
    public InfoCommand() {
        super(
                MTSConfig.modAcronym + ".info",
                "Displays information about the mod's settings",
                PermissionLevel.USER,
                false,
                new CmdParameter("slots/bosses/items", new PresetStringParameterHandler(new String[]{
                        "slots",
                        "bosses",
                        "items",
                        "all"
                }), false)
        );
    }


    @Override
    public void runModular(Client client, Server server, ServerClient serverClient, Object[] args, String[] errors, CommandLog logs) {
        String option = ((String) args[0]).toLowerCase();
        GameMessageBuilder msg = new GameMessageBuilder();
        String defaultColor = GameColor.WHITE.getColorCode();

        if (option.equals("all")) {
            msg.append(String.format("\n%sHere is all the information about %s\n", GameColor.ITEM_QUEST.getColorCode(), MTSConfig.modName));
        }

        if (option.equals("all") || option.equals("slots")) {
            msg.append(String.format("\n%sPlayers%s start with %s%d%s slots\n",
                    GameColor.ITEM_QUEST.getColorCode(), defaultColor, //Players
                    GameColor.GREEN.getColorCode(), MTSConfig.initialSlots, defaultColor)); //slot number
            msg.append(String.format("%sPlayers%s can get up to %s%d%s slots\n",
                    GameColor.ITEM_QUEST.getColorCode(), defaultColor, //Players
                    GameColor.RED.getColorCode(), MTSConfig.getMaxSlots(), defaultColor));  //slot number
        }

        if (option.equals("all") || option.equals("bosses")) {
            //get boss details
            //get sorted map of config
            LinkedHashMap<String, TrinketSlotsItemConfig> sortedMap = MTSConfig.items.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new
                    ));

            //keys (boss names)
            Set<String> keys = sortedMap.keySet();

            msg.append(String.format("\n%sThe following bosses drop items to increase trinket slots:\n", defaultColor));
            //loop through each key in ascending order based on the item configs
            for (String bossKey : keys) {
                TrinketSlotsItemConfig itemConfig = sortedMap.get(bossKey);
                if (itemConfig.minSlots > 0 && (itemConfig.maxSlots != 0 && itemConfig.increment > 0)) {
                    String bossName;
                    //try to get the display name of the boss
                    try {
                        Class<?> cls = Class.forName(bossKey);
                        @SuppressWarnings("unchecked")
                        int mobID = MobRegistry.getMobID((Class<? extends Mob>) cls);
                        bossName = MobRegistry.getDisplayName(mobID);
                    } catch (Exception e) {
                        try {
                            Mob tempMob = MobRegistry.getMob(bossKey, MTSConfig.level);
                            bossName = MobRegistry.getDisplayName(tempMob.getID());
                        } catch (Exception j) {
                            bossName = bossKey;
                        }
                    }
                    msg.append(String.format("%s%s%s drops %s%s%s if under %s%d%s slots\n",
                            GameColor.GRAY.getColorCode(), bossName, defaultColor,
                            GameColor.LIGHT_GRAY.getColorCode(), ItemRegistry.getDisplayName(ItemRegistry.getItemID(itemConfig.itemID)), defaultColor,
                            GameColor.RED.getColorCode(), itemConfig.maxSlots, defaultColor));
                }
            }
        }

        //get item details
        if (option.equals("all") || option.equals("items")) {
            msg.append(String.format("\n%sThe following items can increase trinket slots:\n", defaultColor));
            //get list of consolidated item configs
            List<TrinketSlotsItemConfig> consolidatedConfigs = MTSConfig.getConsolidatedConfigs(MTSConfig.items);
            //sort list of items configs
            Collections.sort(consolidatedConfigs);
            //display item configs
            for (TrinketSlotsItemConfig itemConfig : consolidatedConfigs) {
                if (itemConfig.minSlots > 0 && (itemConfig.maxSlots != 0 && itemConfig.increment > 0)) {
                    msg.append(String.format("%s%s%s - min:%s%d%s, max:%s%d%s, increment:%s%d%s\n",
                            GameColor.GRAY.getColorCode(),ItemRegistry.getDisplayName(ItemRegistry.getItemID(itemConfig.itemID)), defaultColor,
                            GameColor.GREEN.getColorCode(), itemConfig.minSlots, defaultColor,
                            GameColor.RED.getColorCode(), itemConfig.maxSlots, defaultColor,
                            GameColor.BLUE.getColorCode(), itemConfig.increment, defaultColor));
                }
            }
        }
        logs.add(msg);
    }
}