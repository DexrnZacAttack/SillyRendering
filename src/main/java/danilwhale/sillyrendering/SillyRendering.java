package danilwhale.sillyrendering;

import danilwhale.sillyrendering.config.SillyRenderingConfig;
import net.fabricmc.api.ClientModInitializer;

public class SillyRendering implements ClientModInitializer {
    public static SillyRenderingConfig config;

    @Override
    public void onInitializeClient() {
        SillyRenderingConfig.HANDLER.load();
        config = SillyRenderingConfig.HANDLER.instance();
    }
}
