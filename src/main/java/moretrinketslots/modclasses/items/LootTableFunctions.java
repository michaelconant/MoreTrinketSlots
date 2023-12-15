package moretrinketslots.modclasses.items;

import moretrinketslots.modclasses.settings.MTSConfig;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.entity.mobs.Mob;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ConditionLootItem;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.inventory.lootTable.lootItem.LootItemList;
import necesse.inventory.lootTable.lootItem.RotationLootItem;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

public class LootTableFunctions {

    public static void add(LootTable lootTable, TrinketSlotsItemConfig itemConfig) {
        lootTable.items.add(new ConditionLootItem(itemConfig.itemID, (r, o) -> {
            ServerClient client = LootTable.expectExtra(ServerClient.class, o, 1);
            int playerSlotCount = (client.playerMob.getInv()).trinkets.getSize();
            return playerSlotCount < itemConfig.maxSlots;
        }));
    }

    public static void remove(LootTable lootTable, String targetItemID) {
        //look through all entries in the loot table
        lootTable.items.removeIf(lootTableElement -> remove(lootTableElement, targetItemID));
    }
    public static boolean remove(LootItemInterface loot, String targetItemID) {
        if (loot instanceof LootItemList) {
            ((LootItemList) loot).removeIf(item -> remove(item, targetItemID));
            return false;
        } else if (loot instanceof RotationLootItem) {
            ((RotationLootItem) loot).items.removeIf(item -> remove(item, targetItemID));
            return ((RotationLootItem) loot).items.isEmpty();
        } else {
            return Objects.equals(((LootItem) loot).itemStringID, targetItemID);
        }
    }

    public static void updateLootTables() {
        removeItemsFromLootTables();
        addItemsToLootTables();
    }

    public static void addItemsToLootTables() {
        for (Map.Entry<String, TrinketSlotsItemConfig> entry : MTSConfig.items.entrySet()) {
            try {
                Class<?> cls = Class.forName(entry.getKey());
                Field field = cls.getField("privateLootTable");
                field.setAccessible(true);
                Object obj = field.get(field);
                LootTableFunctions.add((LootTable)obj, entry.getValue());
                System.out.print(entry.getKey());
                System.out.println(((LootTable) obj).items);
            } catch (Exception e) {
                try {
                    Mob tempMob = MobRegistry.getMob(entry.getKey(), MTSConfig.level);
                    LootTableFunctions.add(tempMob.getPrivateLootTable(), entry.getValue());
                    System.out.print(entry.getKey());
                    System.out.println(tempMob.getPrivateLootTable().items);
                } catch (Exception f) {
                    MTSConfig.items.remove(entry.getKey());
                }
            }
        }
    }

    public static void removeItemsFromLootTables() {
        for (Map.Entry<String, TrinketSlotsItemConfig> entry : MTSConfig.items.entrySet()) {
            try {
                Class<?> cls = Class.forName(entry.getKey());
                Field field = cls.getField("privateLootTable");
                field.setAccessible(true);
                Object obj = field.get(field);
                LootTableFunctions.remove((LootTable)obj, entry.getValue().itemID);
            } catch (Exception e) {
                try {
                    Mob tempMob = MobRegistry.getMob(entry.getKey(), MTSConfig.level);
                    LootTableFunctions.remove(tempMob.getPrivateLootTable(), entry.getValue().itemID);
                } catch (Exception f) {
                    MTSConfig.items.remove(entry.getKey());
                }
            }
        }
    }
}