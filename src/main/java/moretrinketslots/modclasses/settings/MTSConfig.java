package moretrinketslots.modclasses.settings;

import moretrinketslots.modclasses.items.LootTableFunctions;
import moretrinketslots.modclasses.items.TrinketSlotsItemConfig;
import necesse.entity.mobs.hostile.bosses.*;
import necesse.level.maps.Level;

import java.util.*;
import java.util.List;

//primarily used to store current settings for this mod
//each world has different drop settings for the trinket slot items dropped from bosses
//this means that the conditions for these drops need to change when a world is loaded
//The item registry is closed at this point and when it is closed we cannot update items in it.
//To work around this the settings for each drop are stored here. Then the condition for each item refers
//to a TrinketSlotsItemConfig object in the items map using the boss name as a key.
//This allows the mod to change the variables here instead and when a world is loaded the mod changes the variables here.
public class MTSConfig {
    public static String modName = "MORETRINKETSLOTS";
    public static String modAcronym = "MTS";
    //item id prefix
    public static String itemPrefix = "moretrinketslots_";
    public static float brokerValue = 100.0F;

    //initial trinket slots
    public static int initialSlots = 0;

    public static Level level;

    public static Map<String, TrinketSlotsItemConfig> items;
    static {
        items = new TreeMap<String, TrinketSlotsItemConfig>();
        setItems(getDefaultItems());
    }

    public static int getDefaultInitialSlots() {
        return 4;
    }

    public static Map<String, TrinketSlotsItemConfig> getDefaultItems() {
        Map<String, TrinketSlotsItemConfig> items = new TreeMap<String, TrinketSlotsItemConfig>();
        items.put("evilsprotector", new TrinketSlotsItemConfig(itemPrefix + "evilsprotector", 0, 0, 1));
        items.put("queenspider", new TrinketSlotsItemConfig(itemPrefix + "queenspider", 0, 0, 1));
        items.put("voidwizard", new TrinketSlotsItemConfig("emptypendant", 5, 5, 1));
        items.put("swampguardian", new TrinketSlotsItemConfig(itemPrefix + "swampguardian", 0, 0, 1));
        items.put("ancientvulture", new TrinketSlotsItemConfig(itemPrefix + "ancientvulture", 6, 6, 1));
        items.put("piratecaptain", new TrinketSlotsItemConfig(itemPrefix + "piratecaptain", 0, 0, 1));
        items.put("reaper", new TrinketSlotsItemConfig(itemPrefix + "reaper", 0, 0, 1));
        items.put("cryoqueen", new TrinketSlotsItemConfig(itemPrefix + "cryoqueen", 7, 7, 1));
        items.put("pestwarden", new TrinketSlotsItemConfig(itemPrefix + "pestwarden", 0, 0, 1));
        items.put(FlyingSpiritsHead.class.getName(), new TrinketSlotsItemConfig(itemPrefix + "sageandgrit", 0, 0, 1));
        items.put("fallenwizard", new TrinketSlotsItemConfig("wizardsocket", 8, 8, 1));
        items.put("motherslime", new TrinketSlotsItemConfig(itemPrefix + "motherslime", 0, 0, 1));
        items.put("nightswarm", new TrinketSlotsItemConfig(itemPrefix + "nightswarm", 0, 0, 1));
        return items;
    }

    public static void emptyItems() {
        MTSConfig.items = new TreeMap<String, TrinketSlotsItemConfig>();
    }

    public static void setDefault() {
        initialSlots = getDefaultInitialSlots();
        emptyItems();
        setItems(getDefaultItems());
    }

    public static void setInitialSlots(int newInitialSlots) {
        initialSlots = newInitialSlots;
    }

    public static void setItems(Map<String, TrinketSlotsItemConfig> newItems) {
        LootTableFunctions.removeItemsFromLootTables();
        emptyItems();
        for (Map.Entry<String, TrinketSlotsItemConfig> entry: newItems.entrySet()) {
            items.put(entry.getKey(), new TrinketSlotsItemConfig(entry.getValue()));
        }
    }

    public static void set(int newInitialSlots, Map<String, TrinketSlotsItemConfig> newItems) {
        setInitialSlots(newInitialSlots);
        setItems(newItems);
    }

    public static boolean containsItemID(String searchItemID) {
        for (Map.Entry<String, TrinketSlotsItemConfig> entry : items.entrySet()) {
            if (Objects.equals(entry.getValue().itemID, searchItemID)) {
                return true;
            }
        }
        return false;
    }

    public static List<TrinketSlotsItemConfig> getConfigs(String searchItemID) {
        List<TrinketSlotsItemConfig> outputList = new ArrayList<TrinketSlotsItemConfig>();
        for (Map.Entry<String, TrinketSlotsItemConfig> entry : items.entrySet()) {
            if (Objects.equals(entry.getValue().itemID, searchItemID)) {
                outputList.add(entry.getValue());
            }
        }
        return outputList;
    }

    public static TrinketSlotsItemConfig getConsolidatedConfig(String searchItemID) {
        List<TrinketSlotsItemConfig> listOfConfigs = getConfigs(searchItemID);
        if (listOfConfigs.isEmpty()) {
            return new TrinketSlotsItemConfig("searchItemID", 0, 0, 0, 0);
        }
        if (listOfConfigs.size() == 1) {
            return listOfConfigs.get(0);
        }
        TrinketSlotsItemConfig outputConfig = listOfConfigs.get(0);
        for (TrinketSlotsItemConfig itemConfig : listOfConfigs) {
            outputConfig.minSlots = Math.min(outputConfig.minSlots, itemConfig.minSlots);
            outputConfig.maxSlots = Math.max(outputConfig.maxSlots, itemConfig.maxSlots);
            outputConfig.increment += itemConfig.increment;
            outputConfig.brokerValue += itemConfig.brokerValue;
        }
        outputConfig.increment /= listOfConfigs.size();
        outputConfig.brokerValue /= listOfConfigs.size();
        return outputConfig;
    }

    public static List<TrinketSlotsItemConfig> getConsolidatedConfigs(Map<String, TrinketSlotsItemConfig> items) {
        //get unique ids
        List<String> uniqueIDS = new ArrayList<String>();
        for (Map.Entry<String, TrinketSlotsItemConfig> entry : items.entrySet()) {
            if (!uniqueIDS.contains(entry.getValue().itemID)) {
                uniqueIDS.add(entry.getValue().itemID);
            }
        }

        //get list of consolidatedConfigs
        List<TrinketSlotsItemConfig> outputList = new ArrayList<TrinketSlotsItemConfig>();
        for (String uniqueID : uniqueIDS) {
            outputList.add(getConsolidatedConfig(uniqueID));
        }
        return outputList;
    }

    public static int getMaxSlots() {
        int max = 0;
        for (Map.Entry<String, TrinketSlotsItemConfig> entry : items.entrySet()) {
            max = Math.max(max, entry.getValue().maxSlots);
        }
        return max;
    }
}