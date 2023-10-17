package moretrinketslots.patches.bosses;

import moretrinketslots.Settings;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.server.ServerClient;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.hostile.bosses.VoidWizard;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ConditionLootItem;
import net.bytebuddy.asm.Advice;

import java.util.function.BiFunction;

@ModMethodPatch(target = VoidWizard.class, name = "getPrivateLootTable", arguments = {})
public class VoidWizardPrivateLootTablePatch {

    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    static boolean onEnter(@Advice.This VoidWizard voidWizard) {
        return true;
    }

    public static BiFunction<GameRandom, Object[], Boolean> condition = new BiFunction<GameRandom, Object[], Boolean>() {
        @Override
        public Boolean apply(GameRandom t, Object[] u) {
            ServerClient client = (ServerClient)LootTable.expectExtra(ServerClient.class, u, 1);
            return client != null && (client.playerMob.getInv()).trinkets.getSize() < Settings.emptypendantMaxSize;
        }
    };

    @Advice.OnMethodExit
    static void onExit(@Advice.This VoidWizard voidWizard, @Advice.Return(readOnly = false) LootTable privateLootTable) {
        privateLootTable = new LootTable(
                (LootItemInterface) VoidWizard.uniqueDrops,
                (LootItemInterface) new ConditionLootItem("emptypendant", condition)
        );
    }
}