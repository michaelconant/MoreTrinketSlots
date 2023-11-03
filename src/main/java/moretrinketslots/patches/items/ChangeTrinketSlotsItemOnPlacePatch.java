package moretrinketslots.patches.items;

import moretrinketslots.Settings;
import moretrinketslots.customclasses.IncrementTrinketSlotsItem;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.PacketReader;
import necesse.entity.mobs.PlayerMob;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.placeableItem.consumableItem.ChangeTrinketSlotsItem;
import necesse.inventory.item.placeableItem.consumableItem.EmptyPendantItem;
import necesse.inventory.item.placeableItem.consumableItem.WizardSocketItem;
import necesse.level.maps.Level;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = ChangeTrinketSlotsItem.class, name = "onPlace", arguments = {Level.class, int.class, int.class, PlayerMob.class, InventoryItem.class, PacketReader.class})
public class ChangeTrinketSlotsItemOnPlacePatch {

    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    static boolean onEnter(@Advice.This ChangeTrinketSlotsItem self) {
        if (self instanceof EmptyPendantItem) {
            return true;
        } else return self instanceof WizardSocketItem;
    }

    @Advice.OnMethodExit
    static void onExit(@Advice.This ChangeTrinketSlotsItem self, @Advice.Argument(0) Level level, @Advice.Argument(3) PlayerMob player, @Advice.Argument(4) InventoryItem item, @Advice.Return(readOnly = false) InventoryItem inventoryItem) {
        if (self instanceof EmptyPendantItem) {
            inventoryItem = IncrementTrinketSlotsItem.onPlace(self, level, player, item, Settings.emptypendantMaxSize, Settings.emptypendantMinSize, Settings.emptypendantIncSize);
        }
        if (self instanceof WizardSocketItem) {
            inventoryItem = IncrementTrinketSlotsItem.onPlace(self, level, player, item, Settings.wizardsocketMaxSize, Settings.wizardsocketMinSize, Settings.wizardsocketIncSize);
        }
    }
}