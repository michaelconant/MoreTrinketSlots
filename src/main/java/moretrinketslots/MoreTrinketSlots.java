package moretrinketslots;

import moretrinketslots.items.VultureRingItem;
import moretrinketslots.items.IcePendantItem;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.*;
import necesse.inventory.item.placeableItem.consumableItem.TestChangeTrinketSlotsItem;

@ModEntry
public class MoreTrinketSlots {
    @SuppressWarnings("unused")
    public void init() {
        //new trinket slot items
        ItemRegistry.registerItem("mtsvulturering", new VultureRingItem(), 100.0F, true);
        ItemRegistry.registerItem("mtsicependant", new IcePendantItem(), 100.0F, true);

        //unobtainable dev items
        ItemRegistry.registerItem("inctrinkets5", new TestChangeTrinketSlotsItem(5), 0.0F, false);
        ItemRegistry.registerItem("dectrinkets5", new TestChangeTrinketSlotsItem(-5), 0.0F, false);
        ItemRegistry.registerItem("inctrinkets10", new TestChangeTrinketSlotsItem(10), 0.0F, false);
        ItemRegistry.registerItem("dectrinkets10", new TestChangeTrinketSlotsItem(-10), 0.0F, false);
    }

    @SuppressWarnings("unused")
    public void initResources() {

    }

    @SuppressWarnings("unused")
    public void postInit() {

    }
}