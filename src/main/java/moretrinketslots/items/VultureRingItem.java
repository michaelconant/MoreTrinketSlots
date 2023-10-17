package moretrinketslots.items;

import moretrinketslots.Settings;
import moretrinketslots.customclasses.IncrementTrinketSlotsItem;
import necesse.engine.localization.Localization;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;

public class VultureRingItem extends IncrementTrinketSlotsItem {
    public VultureRingItem() {
        super(Settings.vultureringMaxSize, Settings.vultureringMinSize, Settings.vultureringIncSize);
        this.rarity = Rarity.UNIQUE;
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "mtsvultureringtip"));
        return tooltips;
    }
}