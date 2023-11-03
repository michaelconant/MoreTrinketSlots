package moretrinketslots.devtest;

import necesse.inventory.item.toolItem.swordToolItem.CustomSwordToolItem;

// Just used to quickly kill mods to test if my mod's drops are working properly
// Extends CustomSwordToolItem
public class TestSwordItem extends CustomSwordToolItem {

    // Weapon attack textures are loaded from resources/player/weapons/<itemStringID>

    public TestSwordItem() {
        super(Rarity.UNCOMMON, 300, 90000, 12000, 100, 400);
    }

}
