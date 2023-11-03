package moretrinketslots;

import moretrinketslots.commands.*;
import moretrinketslots.devtest.TestSwordItem;
import moretrinketslots.modclasses.settings.MTSConfig;
import moretrinketslots.modclasses.items.IncrementalTrinketSlotsItem;
import moretrinketslots.modclasses.items.LootTableFunctions;
import moretrinketslots.modclasses.items.TrinketSlotsItemConfig;
import necesse.engine.commands.CommandsManager;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.*;
import necesse.entity.mobs.hostile.bosses.FallenWizardMob;
import necesse.entity.mobs.hostile.bosses.VoidWizard;
import necesse.inventory.item.placeableItem.consumableItem.TestChangeTrinketSlotsItem;

import java.util.Map;

@ModEntry
public class MoreTrinketSlots {
    @SuppressWarnings("unused")
    public void init() {
        //register/replace trinket slot items
        for (Map.Entry<String, TrinketSlotsItemConfig> entry : MTSConfig.items.entrySet()) {
            TrinketSlotsItemConfig item = entry.getValue();
            if (ItemRegistry.getItem(item.itemID) == null) {
                ItemRegistry.registerItem(item.itemID, new IncrementalTrinketSlotsItem(item.itemID), MTSConfig.brokerValue, true);
            } else {
                ItemRegistry.replaceItem(item.itemID, new IncrementalTrinketSlotsItem(item.itemID), MTSConfig.brokerValue, true);
            }
        }

        //register 10 general placeholder trinket slot items that can be used for bosses from other mods
        for (int i = 1; i <= 9; i++) {
            String itemID = MTSConfig.itemPrefix + "genericitem_" + i;
            ItemRegistry.registerItem(itemID, new IncrementalTrinketSlotsItem(itemID), MTSConfig.brokerValue, true);
        }

        //unobtainable dev items
        ItemRegistry.registerItem(MTSConfig.itemPrefix +"inctrinkets5", new TestChangeTrinketSlotsItem(5), 0.0F, false);
        ItemRegistry.registerItem(MTSConfig.itemPrefix +"dectrinkets5", new TestChangeTrinketSlotsItem(-5), 0.0F, false);
        ItemRegistry.registerItem(MTSConfig.itemPrefix +"inctrinkets10", new TestChangeTrinketSlotsItem(10), 0.0F, false);
        ItemRegistry.registerItem(MTSConfig.itemPrefix +"dectrinkets10", new TestChangeTrinketSlotsItem(-10), 0.0F, false);
        ItemRegistry.registerItem(MTSConfig.itemPrefix + "testsword", new TestSwordItem(), 0, false);


        //remove vanilla trinket slot items from loot tables
        //items are added to the private loot tables when the world is loaded
        LootTableFunctions.remove(VoidWizard.privateLootTable, "emptypendant");
        System.out.println("projectcuriosity.moretrinketslots removed emptypendant from FallenWizardMob.privateLootTable");
        LootTableFunctions.remove(FallenWizardMob.privateLootTable, "wizardsocket");
        System.out.println("projectcuriosity.moretrinketslots removed wizardsocket from FallenWizardMob.privateLootTable");
    }

    @SuppressWarnings("unused")
    public void initResources() {

    }

    @SuppressWarnings("unused")
    public void postInit() {
        CommandsManager.registerServerCommand(new InfoCommand());
        CommandsManager.registerServerCommand(new LoadPresetCommand());
        CommandsManager.registerServerCommand(new TrinketSlotsCommand());
    }
}