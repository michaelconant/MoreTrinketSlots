package moretrinketslots.patches.player;

import moretrinketslots.modclasses.settings.MTSConfig;
import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.PlayerMob;
import necesse.inventory.PlayerInventoryManager;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = PlayerInventoryManager.class, arguments = {PlayerMob.class})
public class InitialTrinketSlotsPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This PlayerInventoryManager playerInventoryManager) {
        playerInventoryManager.equipment.changeTrinketSlotsSize(MTSConfig.initialSlots);
    }
}