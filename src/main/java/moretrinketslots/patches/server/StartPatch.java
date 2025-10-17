package moretrinketslots.patches.server;

import moretrinketslots.modclasses.settings.MTSConfig;
import moretrinketslots.modclasses.settings.MTSWorldFile;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerHostSettings;
import necesse.engine.util.LevelIdentifier;
import necesse.engine.world.WorldEntity;
import necesse.level.maps.Level;
import net.bytebuddy.asm.Advice;

import java.awt.*;

@ModMethodPatch(target = Server.class, name = "start", arguments = {ServerHostSettings.class, boolean.class})
public class StartPatch {
    @Advice.OnMethodEnter
    static void onEnter(@Advice.This Server server) {
        System.out.println(MTSConfig.modName + ": attempting to load MTSConfig settings from file");
        MTSWorldFile.loadSettings(server.world);
        MTSConfig.level = new Level(
                new LevelIdentifier("mtsproxylevel"),
                1,
                1,
                WorldEntity.getServerWorldEntity(
                        server.world,
                        "1"
                )
        );
    }
}