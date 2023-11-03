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

@ModMethodPatch(target = ChangeTrinketSlotsItem.class, name = "canPlace", arguments = {Level.class, int.class, int.class, PlayerMob.class, InventoryItem.class, PacketReader.class})
public class ChangeTrinketSlotsItemCanPlacePatch {

    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    static boolean onEnter(@Advice.This ChangeTrinketSlotsItem self) {
        if (self instanceof EmptyPendantItem) {
            return true;
        } else return self instanceof WizardSocketItem;
    }


    @Advice.OnMethodExit
    static void onExit(@Advice.This ChangeTrinketSlotsItem self, @Advice.Argument(3) PlayerMob player, @Advice.Return(readOnly = false) String output) {
        if (self instanceof EmptyPendantItem) {
            output = IncrementTrinketSlotsItem.canPlace(player, Settings.emptypendantMaxSize);
        }
        if (self instanceof WizardSocketItem) {
            output = IncrementTrinketSlotsItem.canPlace(player, Settings.wizardsocketMaxSize);
        }
    }
}