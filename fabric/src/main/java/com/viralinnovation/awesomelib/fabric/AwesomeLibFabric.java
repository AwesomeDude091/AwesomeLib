package com.viralinnovation.awesomelib.fabric;

import com.viralinnovation.awesomelib.AwesomeLib;
import net.fabricmc.api.ModInitializer;

public class AwesomeLibFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        AwesomeLib.init();
    }
}