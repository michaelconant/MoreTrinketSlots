package moretrinketslots.patches.server;

import moretrinketslots.modclasses.settings.MTSWorldFile;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.network.server.Server;
import net.bytebuddy.asm.Advice;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

@ModMethodPatch(target = Server.class, name = "saveAll", arguments = {boolean.class})
public class SaveAllPatch {
    @Advice.OnMethodEnter
    static void onEnter(@Advice.This Server server) {
        System.out.println("save");
        //MTSWorldFile.saveSettings(server.world);
    }
}