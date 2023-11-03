package moretrinketslots.patches.bosses;

import moretrinketslots.Settings;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.server.ServerClient;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.hostile.bosses.CryoQueenMob;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.ConditionLootItem;
import necesse.inventory.lootTable.lootItem.MobConditionLootItemList;
import net.bytebuddy.asm.Advice;

import java.util.function.BiFunction;
import java.util.function.Function;

@ModMethodPatch(target = CryoQueenMob.class, name = "getPrivateLootTable", arguments = {})
public class CryoQueenPrivateLootTablePatch {

    @Advice.OnMethodEnter(skipOn = Advice.OnNonDefaultValue.class)
    static boolean onEnter(@Advice.This CryoQueenMob cryoQueenMob) {
        return true;
    }

    public static Function<Mob, Boolean> uniqueDropsCondition = new Function<Mob, Boolean>() {
        @Override
        public Boolean apply(Mob mob) {
            return mob.getLevel() == null || !(mob.getLevel()).isIncursionLevel;
        }
    };

    public static BiFunction<GameRandom, Object[], Boolean> icependantCondition = new BiFunction<GameRandom, Object[], Boolean>() {
        @Override
        public Boolean apply(GameRandom t, Object[] u) {
            ServerClient client = (ServerClient) LootTable.expectExtra(ServerClient.class, u, 1);
            return client != null && (client.playerMob.getInv()).trinkets.getSize() < Settings.icependantMaxSize;
        }
    };

    @Advice.OnMethodExit
    static void onExit(@Advice.This CryoQueenMob cryoQueenMob, @Advice.Return(readOnly = false) LootTable privateLootTable) {
        privateLootTable = new LootTable( new LootItemInterface[] {
                (LootItemInterface)new MobConditionLootItemList(uniqueDropsCondition, new LootItemInterface[]{
                        (LootItemInterface) CryoQueenMob.uniqueDrops,
                }),
                (LootItemInterface) new ConditionLootItem("mtsicependant", icependantCondition)
        });
    }
}