package danilwhale.sillyrendering.config.option;

import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;

public enum OptionAffect {
    ALL_TEXTURES(Component.translatable("gui.sillyrendering.config.option_affects.all_textures")),
    ENTITIES(Component.translatable("gui.sillyrendering.config.option_affects.entities")),
    BLOCKS(Component.translatable("gui.sillyrendering.config.option_affects.blocks")),
    LIQUIDS(Component.translatable("gui.sillyrendering.config.option_affects.liquids")),
    GUIS(Component.translatable("gui.sillyrendering.config.option_affects.guis")),
    FIRE_TEXTURE(Component.translatable("gui.sillyrendering.config.option_affects.fire_texture"));

    public final Component comp;

    OptionAffect(@Nullable Component comp) {
        this.comp = comp;
    }
}
