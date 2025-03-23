package moretrinketslots.modclasses.settings;

import moretrinketslots.modclasses.items.LootTableFunctions;
import moretrinketslots.modclasses.items.TrinketSlotsItemConfig;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.registries.MobRegistry;
import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;
import necesse.engine.world.World;
import necesse.engine.world.WorldFile;
import necesse.entity.mobs.Mob;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MTSWorldFile {

    public static WorldFile getFile(World world) {
        return world.fileSystem.file("mtsSettings.cfg");
    }

    public static void saveSettings(World world) {
        if (world == null) throw new NullPointerException("Cannot save settings from null world.");
        getSaveScript().saveScript(MTSWorldFile.getFile(world));
    }


    public static void loadSettings(World world) {
        loadSettings(world, true);
    }

    public static void loadSettings(World world, boolean createFile) {
        if (world == null) throw new NullPointerException("Cannot load MTS settings from null world.");
        WorldFile file = getFile(world);
        if (file.exists() && !file.isDirectory()) {
            loadSaveScript(new LoadData(file));
        } else if (createFile) {
            MTSConfig.setDefault();
            saveSettings(world);
        }
    }

    public static SaveData getSaveScript() {
        SaveData save = new SaveData(MTSConfig.modName);

        save.addInt("initialSlots", MTSConfig.initialSlots);

        SaveData dropsData = new SaveData("bossdrops");
        for (Map.Entry<String, TrinketSlotsItemConfig> entry : MTSConfig.items.entrySet()) {
            SaveData tempItemData = new SaveData(entry.getKey());
            tempItemData.addSafeString("itemID", entry.getValue().itemID);
            tempItemData.addInt("minSlots", entry.getValue().minSlots);
            tempItemData.addInt("maxSlots", entry.getValue().maxSlots);
            tempItemData.addInt("increment", entry.getValue().increment);
            dropsData.addSaveData(tempItemData);
        }
        save.addSaveData(dropsData);

        return save;
    }

    public static void loadSaveScript(LoadData save) {
        LootTableFunctions.removeItemsFromLootTables();
        if (save == null) {
            MTSConfig.setDefault();
            System.out.println(MTSConfig.modName + ": worldSettings.cfg, no load data, loading default config");
        } else {
            System.out.println(MTSConfig.modName + ": worldSettings.cfg found, attempting to load config file");
            MTSConfig.initialSlots = save.getInt("initialSlots", MTSConfig.initialSlots);
            LoadData bossDropData = save.getFirstLoadDataByName("bossdrops");
            List<LoadData> bossDropsLoadData = bossDropData.getLoadData();
            for (LoadData data : bossDropsLoadData) {
                TrinketSlotsItemConfig newItem = new TrinketSlotsItemConfig(
                        data.getSafeString("itemID", MTSConfig.itemPrefix + "genericitem_1"),
                        data.getInt("minSlots", 0),
                        data.getInt("maxSlots", 0),
                        data.getInt("increment", 1)
                );
                try {
                    //try to see if the full class name for the boss was used
                    Class<?> cls = Class.forName(data.getName());   //used to see if class can be found
                    MTSConfig.items.put(data.getName(), newItem);
                    System.out.println(MTSConfig.modName + ": added " + newItem.itemID + " to " + data.getName() + " drops");
                } catch (Exception e) {
                    //try to see if the mobID for the boss was used instead
                    try {
                        Mob tempMob = MobRegistry.getMob(data.getName(), MTSConfig.level);  //used to see if mob can be found
                        MTSConfig.items.put(data.getName(), newItem);
                        System.out.println(MTSConfig.modName + ": added " + newItem.itemID + " to " + data.getName() + " drops");
                    } catch (Exception f) {
                        System.out.println(MTSConfig.modName + ": " + data.getName() + " in settings but class not found");
                    }
                }
            }
        }
        LootTableFunctions.addItemsToLootTables();
    }

    public static void setupContentPacket(PacketWriter writer) {
        writer.putNextString(MTSConfig.modName);
        writer.putNextInt(MTSConfig.initialSlots);
        writer.putNextInt(MTSConfig.items.size());
        for (Map.Entry<String, TrinketSlotsItemConfig> entry : MTSConfig.items.entrySet()) {
            writer.putNextString(entry.getKey());
            writer.putNextString(entry.getValue().itemID);
            writer.putNextInt(entry.getValue().minSlots);
            writer.putNextInt(entry.getValue().maxSlots);
            writer.putNextInt(entry.getValue().increment);
        }
    }

    public static void applyContentPacket(PacketReader reader) {
        LootTableFunctions.removeItemsFromLootTables();
        if (Objects.equals(reader.getNextString(), MTSConfig.modName)) {
            MTSConfig.initialSlots = reader.getNextInt();
            int itemConfigSize = reader.getNextInt();
            for (int i = 0; i < itemConfigSize; i++) {
                String key = reader.getNextString();
                TrinketSlotsItemConfig newItem = new TrinketSlotsItemConfig(
                        reader.getNextString(),
                        reader.getNextInt(),
                        reader.getNextInt(),
                        reader.getNextInt()
                );
                MTSConfig.items.put(key, newItem);
            }
        } else {
            MTSConfig.setDefault();
            System.out.println(MTSConfig.modName + ": settings not found for server");
        }
        LootTableFunctions.addItemsToLootTables();
    }
}