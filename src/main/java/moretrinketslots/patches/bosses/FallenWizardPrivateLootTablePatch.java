package moretrinketslots.patches.bosses;

import moretrinketslots.Settings;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.server.ServerClient;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.hostile.bosses.FallenWizardMob;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ConditionLootItem;
import net.bytebuddy.asm.Advice;

import java.util.function.BiFunction;

@ModMethodPatch(target = FallenWizardMob.class, name = "getPrivateLootTable", arguments = {})
public class FallenWizardPrivateLootTablePatch {

    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    static boolean onEnter(@Advice.This FallenWizardMob fallenWizardMob) {
        return true;
    }

    public static BiFunction<GameRandom, Object[], Boolean> condition = new BiFunction<GameRandom, Object[], Boolean>() {
        @Override
        public Boolean apply(GameRandom t, Object[] u) {
            ServerClient client = (ServerClient)LootTable.expectExtra(ServerClient.class, u, 1);
            return client != null && (client.playerMob.getInv()).trinkets.getSize() < Settings.wizardsocketMaxSize;
        }
    };

    @Advice.OnMethodExit
    static void onExit(@Advice.This FallenWizardMob fallenWizardMob, @Advice.Return(readOnly = false) LootTable privateLootTable) {
        privateLootTable = new LootTable(
                (LootItemInterface) FallenWizardMob.uniqueDrops,
                (LootItemInterface) new ConditionLootItem("wizardsocket", condition)
        );
    }
}