package moretrinketslots.devtest;

import necesse.inventory.item.toolItem.swordToolItem.SwordToolItem;

// Just used to quickly kill mods to test if my mod's drops are working properly
// Extends CustomSwordToolItem
public class TestSwordItem extends SwordToolItem {

    // Weapon attack textures are loaded from resources/player/weapons/<itemStringID>

    public TestSwordItem() {
        super(0);
        this.rarity = Rarity.LEGENDARY;
        this.attackAnimTime.setBaseValue(300);
        this.attackDamage.setBaseValue(90000);
        this.attackRange.setBaseValue(90000);
        this.knockback.setBaseValue(400);
    }

}
