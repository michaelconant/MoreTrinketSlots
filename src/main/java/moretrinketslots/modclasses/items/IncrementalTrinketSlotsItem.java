package moretrinketslots.modclasses.items;

import moretrinketslots.modclasses.settings.MTSConfig;
import necesse.engine.localization.Localization;
import necesse.engine.network.Packet;
import necesse.engine.network.PacketReader;
import necesse.engine.network.packet.PacketUpdateTrinketSlots;
import necesse.engine.network.server.ServerClient;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.placeableItem.consumableItem.ChangeTrinketSlotsItem;
import necesse.level.maps.Level;

//class used to implement a new mod item
public class IncrementalTrinketSlotsItem extends ChangeTrinketSlotsItem {
    public String itemID;

    public IncrementalTrinketSlotsItem(String itemID) {
        super(0);
        this.itemID = itemID;
        this.rarity = Rarity.UNIQUE;
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", itemID+"tip"));
        return tooltips;
    }

    //check if the item can be used
    public String canPlace(Level level, int x, int y, PlayerMob player, InventoryItem item, PacketReader contentReader) {
        if (MTSConfig.containsItemID(itemID)) {
            int maxSlots = MTSConfig.getConsolidatedConfig(itemID).maxSlots;
            if ((player.getInv()).equipment.getTrinketSlotsSize() < maxSlots || maxSlots < 0) {
                return null;
            }
        }
        return "incorrectslots";
    }
    
    //what to do when the item is successfully used
    //this is just a slightly modified version of how the game implements this function
    public InventoryItem onPlace(Level level, int x, int y, PlayerMob player, InventoryItem item, PacketReader contentReader) {
        TrinketSlotsItemConfig itemConfig = MTSConfig.getConsolidatedConfig(itemID);
        if (level.isServer() && itemConfig.increment > 0) {
            (player.getInv()).equipment.changeTrinketSlotsSize(getNewSize(player, itemConfig));
            player.equipmentBuffManager.updateTrinketBuffs();
            ServerClient serverClient = player.getServerClient();
            serverClient.closeContainer(false);
            serverClient.updateInventoryContainer();
            if (serverClient.achievementsLoaded()) {
                (serverClient.achievements()).MAGICAL_DROP.markCompleted(serverClient);
            }
            (level.getServer()).network.sendToAllClients((Packet)new PacketUpdateTrinketSlots(serverClient));
        }
        if (this.singleUse) {
            item.setAmount(item.getAmount() - 1);
        }
        return item;
    }

    //calculates the player's new trinket slot size
    private static int getNewSize(PlayerMob player, TrinketSlotsItemConfig itemConfig) {
        int newSize = Math.max((player.getInv()).equipment.getTrinketSlotsSize() + itemConfig.increment, itemConfig.minSlots);
        //only account for max slots if the user set to 0 or greater
        // negative = no limit
        // 0 = item disabled (disabled items should never technically reach here because they will fail the canPlace)
        // positive = there is a specific limit set
        if (itemConfig.maxSlots >= 0) {
            newSize = Math.min(newSize, itemConfig.maxSlots);
        }
        return newSize;
    }
}