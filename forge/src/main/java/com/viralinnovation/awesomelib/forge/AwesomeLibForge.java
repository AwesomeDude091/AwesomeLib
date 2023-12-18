package com.viralinnovation.awesomelib.forge;

import dev.architectury.platform.forge.EventBuses;
import com.viralinnovation.awesomelib.AwesomeLib;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(AwesomeLib.MOD_ID)
public class AwesomeLibForge {
    public AwesomeLibForge() {
		// Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(AwesomeLib.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        AwesomeLib.init();
    }
}