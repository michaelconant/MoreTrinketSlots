package moretrinketslots.customclasses;

import necesse.engine.network.Packet;
import necesse.engine.network.PacketReader;
import necesse.engine.network.packet.PacketUpdateTrinketSlots;
import necesse.engine.network.server.ServerClient;
import necesse.entity.mobs.PlayerMob;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.placeableItem.consumableItem.ChangeTrinketSlotsItem;
import necesse.level.maps.Level;

//class used to implement a new mod item
public class IncrementTrinketSlotsItem extends ChangeTrinketSlotsItem {

    public int minimumSlots;
    public int incrementAmount;
    public IncrementTrinketSlotsItem(int trinketSlots, int minimumSlots, int incrementAmount) {
        //trinketSlots is now used for the maximum trinkets slots the player can get with this item
        super(trinketSlots);
        //the minimum trinket slots the item sets the player to
            //it does not matter what the increment is, if the player has less than the minimum slots they will have their slots increased to the minimum
            //for example: the player is at 5 slots, minimumSlots is set to 7, and incrementAmount is set to 1 then the player will be set to 7 slots
        this.minimumSlots = minimumSlots;
        //incrementAmount is the amount of trinket slots the player gains on each use of the item
        this.incrementAmount = incrementAmount;
    }

    //what to do when the item is successfully used
    //this is just a slightly modified version of how the game implements this function
    public InventoryItem onPlace(Level level, int x, int y, PlayerMob player, InventoryItem item, PacketReader contentReader) {
        return IncrementTrinketSlotsItem.onPlace(this, level, player, item, this.trinketSlots, this.minimumSlots, this.incrementAmount);
    }

    //check if the item can be used
    public String canPlace(Level level, int x, int y, PlayerMob player, InventoryItem item, PacketReader contentReader) {
        return IncrementTrinketSlotsItem.canPlace(player, this.trinketSlots);
    }

    public static InventoryItem onPlace(ChangeTrinketSlotsItem self, Level level, PlayerMob player, InventoryItem item, int maxSlots, int minSlots, int incrementSize) {
        if (level.isServerLevel()) {
            int currentTrinketSlots = (player.getInv()).trinkets.getSize();
            (player.getInv()).trinkets.changeSize(Math.min(Math.max(currentTrinketSlots + incrementSize, minSlots), maxSlots));
            player.equipmentBuffManager.updateTrinketBuffs();
            ServerClient serverClient = player.getServerClient();
            serverClient.closeContainer(false);
            serverClient.updateInventoryContainer();
            if (serverClient.achievementsLoaded()) {
                (serverClient.achievements()).MAGICAL_DROP.markCompleted(serverClient);
            }
            (level.getServer()).network.sendToAllClients((Packet)new PacketUpdateTrinketSlots(serverClient));
        }
        if (self.singleUse) item.setAmount(item.getAmount() - incrementSize);

        return item;
    }

    public static String canPlace(PlayerMob player, int maxSlots) {
        if (maxSlots <= 0) {
            return null;
        } else if ((player.getInv()).trinkets.getSize() >= maxSlots) {
            return "incorrectslots";
        } else {
            return null;
        }
    }
}