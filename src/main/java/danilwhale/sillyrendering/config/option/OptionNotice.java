package danilwhale.sillyrendering.config.option;

import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;

public enum OptionNotice {
    RELOAD_REQUIRED(Component.translatable("gui.sillyrendering.config.option_notice.world_reload_required")),
    CONFIG_FILE_MAY_NEED_EDITING(Component.translatable("gui.sillyrendering.config.option_notice.may_need_manual_editing"));

    public final Component comp;

    OptionNotice(@Nullable Component comp) {
        this.comp = comp;
    }
}
