package moretrinketslots.commands;

import moretrinketslots.modclasses.settings.MTSConfig;
import necesse.engine.commands.CmdParameter;
import necesse.engine.commands.CommandLog;
import necesse.engine.commands.ModularChatCommand;
import necesse.engine.commands.PermissionLevel;
import necesse.engine.commands.parameterHandlers.*;
import necesse.engine.localization.message.GameMessageBuilder;
import necesse.engine.network.client.Client;
import necesse.engine.network.packet.PacketPlayerInventory;
import necesse.engine.network.packet.PacketUpdateTrinketSlots;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.engine.network.server.ServerSaveHandler;
import necesse.engine.save.LoadData;
import necesse.engine.util.GameRandom;
import necesse.engine.world.WorldFile;
import necesse.inventory.InventorySlot;

import java.util.LinkedList;
import java.util.Objects;

public class TrinketSlotsCommand extends ModularChatCommand {

    //Save handler used to output status of the save after using the command to console
    ServerSaveHandler.SaveInterruptedOrFinishedEventHandler saveHandler = new ServerSaveHandler.SaveInterruptedOrFinishedEventHandler() {
        public void onSaveInterrupted(ServerSaveHandler newSaveHandler) {
            System.out.println("World save interrupted, changes to trinket slots not applied properly");
        }

        public void onSaveFinished() {
            System.out.println("World saved successfully");
        }
    };

    public TrinketSlotsCommand() {
        super(MTSConfig.modAcronym + ".trinketslots", "Change player(s) trinket slots size", PermissionLevel.OWNER, false,
            new CmdParameter[] {
                new CmdParameter(
                    "player/all/online/offline",
                    new MultiParameterHandler(
                        new ParameterHandler[] {
                            new PresetStringParameterHandler(new String[] {"all", "online", "offline"}),
                            new ServerClientParameterHandler(false, true)
                        }
                    )
                ),
                new CmdParameter(
                    "set/reset/max/adjust",
                    new PresetStringParameterHandler(new String[] {"set", "reset", "max", "adjust"})
                ),
                new CmdParameter(
                     "new amount",
                     new IntParameterHandler(
                        -1
                     ),
                    true
                )
            }
        );
    }

    //Attempts to move the extra trinkets to the player's inventory. If they do not have space in their inventory it drops it for them.
    public ServerClient moveTrinketsToInventory(ServerClient target, int newAmount) {
        if (target.playerMob.getInv().equipment.getTrinketSlotsSize() > newAmount) {
            //loop through all items and drop the extra trinkets on the ground
            int maxSlots = target.playerMob.getInv().equipment.getTrinketSlotsSize();
            for (int trinketSlotPos = 0; trinketSlotPos < maxSlots - newAmount; trinketSlotPos++) {
                InventorySlot trinketSlot = target.playerMob.getInv().equipment.getSelectedTrinketsSlot(maxSlots - 1 - trinketSlotPos);
                if (!trinketSlot.isSlotClear()) {
                    //place trinket in inventory slot if it is empty
                    boolean emptySlotFound = false;
                    for (int invPos = 0; invPos < target.playerMob.getInv().main.getSize(); invPos++) {
                        if (target.playerMob.getInv().main.isSlotClear(invPos)) {
                            target.playerMob.getInv().main.setItem(invPos, trinketSlot.getItem());
                            emptySlotFound = true;
                            break;
                        }
                    }

                    //if empty slot not found drop item on ground
                    if (!emptySlotFound) {
                        target.playerMob.dropItem(trinketSlot.getItem());
                    }
                }
            }
        }

        return target;
    }

    public void onlinePlayerTrinketsChangeSize(ServerClient target, int newAmount) {
        if (target.playerMob.getInv().equipment.getTrinketSlotsSize() != newAmount) {

            target = moveTrinketsToInventory(target, newAmount);

            target.playerMob.getInv().equipment.changeTrinketSlotsSize(newAmount);
            target.playerMob.equipmentBuffManager.updateTrinketBuffs();
            target.closeContainer(false);
            target.updateInventoryContainer();
            target.getServer().network.sendToAllClients(new PacketUpdateTrinketSlots(target));
            target.getServer().network.sendToAllClients(new PacketPlayerInventory(target));
        }
    }

    public int getAdjustAmount(int currentSlots) {
        if (currentSlots < MTSConfig.initialSlots) {
            return MTSConfig.initialSlots;
        } else {
            return Math.min(currentSlots, MTSConfig.getMaxSlots());
        }
    }

    public boolean isPlayerOnline(Server server, long auth) {
        for (int i = 0; i < server.getSlots(); i++) {
            if (server.getClient(i) != null && server.getClient(i).authentication == auth) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void runModular(Client client, Server server, ServerClient serverClient, Object[] args, String[] errors, CommandLog logs) {
        GameMessageBuilder msg = new GameMessageBuilder();

        //get the new amount of slots based on the option chosen
        String action = (String)args[1];
        int newSlotAmount = 0;      //default value so compiler stops complaining
        boolean adjustFlag = false; //flag needed to find new slot size for each player
        switch (action) {
            case "reset":
                newSlotAmount = MTSConfig.initialSlots;
                break;
            case "max":
                newSlotAmount = MTSConfig.getMaxSlots();
                break;
            case "adjust":
                adjustFlag = true;
                break;
            case "set":
                newSlotAmount = (Integer)args[2];
                //return
                if (newSlotAmount < 0) {
                    msg.append("Missing/invalid argument <new amount>");
                    logs.add(msg);
                    return;
                }
                break;
        }

        //get sub arguments to see if the player who entered the command is targeting everyone or a specific player
        Object[] subArgs = (Object[])args[0];                   //contains [all, player]
        ServerClient targetClient = (ServerClient)subArgs[1];   //get the player
        if (targetClient != null) {
            //-------------------------------
            //if a specific player was chosen
            //-------------------------------

            if (adjustFlag) {
                int playerSlots = targetClient.playerMob.getInv().equipment.getTrinketSlotsSize();
                if (playerSlots < MTSConfig.initialSlots) {
                    newSlotAmount = MTSConfig.initialSlots;
                } else if (playerSlots > MTSConfig.getMaxSlots()) {
                    newSlotAmount = MTSConfig.getMaxSlots();
                } else {
                    newSlotAmount = targetClient.playerMob.getInv().equipment.getTrinketSlotsSize();
                }
            }

            onlinePlayerTrinketsChangeSize(targetClient, newSlotAmount);
            if (adjustFlag) {
                msg.append(String.format("Adjusted %s's trinket slots to be within %d and %d", targetClient.playerMob.playerName, MTSConfig.initialSlots, MTSConfig.getMaxSlots()));
            } else {
                msg.append(String.format("Changed %s's trinket slots to %d", targetClient.playerMob.playerName, newSlotAmount));
            }

            logs.add(msg);
            server.startFullSave(true, true, null);
            return;
        }

        //-----------------------------------------
        //if all/online/offline players were chosen
        //-----------------------------------------
        String target = (String)subArgs[0];
        //set current online players slots
        if (Objects.equals(target, "all") || Objects.equals(target, "online")) {
            for (int i = 0; i < server.getSlots(); i++) {
                if (server.getClient(i) != null) {
                    //find the player's new trinket slot size if player who entered the command chose to adjust the slot size
                    if (adjustFlag) {
                        newSlotAmount = getAdjustAmount((server.getClient(i).playerMob.getInv()).equipment.getTrinketSlotsSize());
                    }
                    onlinePlayerTrinketsChangeSize(server.getClient(i), newSlotAmount);
                }
            }
        }

        //set all player file trinket slots (offline players only)
        //(only if outside characters are not allowed)
        if (!server.world.settings.allowOutsideCharacters && (Objects.equals(target, "all") || Objects.equals(target, "offline"))) {
            LinkedList<WorldFile> playerFiles = server.world.fileSystem.getPlayerFiles();
            for (WorldFile playerFile : playerFiles) {
                if (!new LoadData(playerFile).isEmpty()) {
                    //get the file name
                    String fileName = playerFile.getFileName().toString();
                    //remove ".dat" from the end of the file name
                    fileName = fileName.substring(0, fileName.length() - 4);
                    //convert the file name to long
                    //it can then be used as the authentication value in the server.world.loadClient function
                    long fileNameLong = Long.valueOf(fileName, 10);

                    //skip the player if they are online
                    if (isPlayerOnline(server, fileNameLong)) {
                        continue;
                    }

                    //create a ServerClient for the offline player
                    ServerClient offlinePlayerClient = server.world.loadClient(
                            playerFile,
                            GameRandom.globalRandom.nextLong(),
                            serverClient.networkInfo,
                            -1,
                            fileNameLong
                    );

                    //find the player's new trinket slot size if player who entered the command chose to adjust the slot size
                    if (adjustFlag) {
                        newSlotAmount = getAdjustAmount(offlinePlayerClient.playerMob.getInv().equipment.getTrinketSlotsSize());
                    }

                    //set the offline player's trinket slot size to the new slot amount and dispose of them
                    if (offlinePlayerClient.playerMob.getInv().equipment.getTrinketSlotsSize() != newSlotAmount) {

                        offlinePlayerClient = moveTrinketsToInventory(offlinePlayerClient, newSlotAmount);

                        offlinePlayerClient.playerMob.getInv().equipment.changeTrinketSlotsSize(newSlotAmount);
                        offlinePlayerClient.saveClient();
                    }
                    offlinePlayerClient.dispose();
                }
            }
        }

        if (adjustFlag) {
            msg.append(String.format("Adjusted %s players trinket slots to be within %d and %d", target, MTSConfig.initialSlots, MTSConfig.getMaxSlots()));
        } else {
            msg.append(String.format("Changed %s players trinket slots to %d", target, newSlotAmount));
        }

        logs.add(msg);
        server.startFullSave(true, true, saveHandler);
    }
}