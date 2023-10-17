package moretrinketslots.items;

import moretrinketslots.Settings;
import moretrinketslots.customclasses.IncrementTrinketSlotsItem;
import necesse.engine.localization.Localization;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;

public class IcePendantItem extends IncrementTrinketSlotsItem {
    public IcePendantItem() {
        super(Settings.icependantMaxSize, Settings.icependantMinSize, Settings.icependantIncSize);
        this.rarity = Rarity.UNIQUE;
    }

    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "mtsicependanttip"));
        return tooltips;
    }
}