package danilwhale.sillyrendering.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import danilwhale.sillyrendering.config.option.OptionAffect;
import danilwhale.sillyrendering.config.option.OptionNotice;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.Collections;
import java.util.List;

public class SillyRenderingModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> YetAnotherConfigLib.create(SillyRenderingConfig.HANDLER, (defaults, config, builder) ->
                        builder.title(Component.translatable("gui.sillyrendering.config.title"))
                                .category(dev.isxander.yacl3.api.ConfigCategory.createBuilder()
                                        .name(Component.translatable("gui.sillyrendering.config.category.main.title"))
                                        // options
                                        .option(SillyRenderingModMenuIntegration.<Float>createOptionBuilder("intensity", ConfigCategory.MAIN)
                                                .binding(SillyRenderingConfig.DEFAULT_INTENSITY,
                                                        () -> config.intensity,
                                                        (v) -> config.intensity = v
                                                )
                                                .controller(opt -> FloatSliderControllerBuilder.create(opt).range(0.1f, 100.0f).step(0.1f))
                                                .build())
                                        .option(SillyRenderingModMenuIntegration.<Boolean>createOptionBuilder("randomizeVertex", ConfigCategory.MAIN, List.of(OptionNotice.CONFIG_FILE_MAY_NEED_EDITING), List.of(OptionAffect.ALL_TEXTURES))
                                                .binding(SillyRenderingConfig.DEFAULT_RANDOMIZE_VERTEX,
                                                        () -> config.randomizeVertex,
                                                        (v) -> config.randomizeVertex = v
                                                )
                                                .controller(TickBoxControllerBuilder::create)
                                                .build())
                                        .option(SillyRenderingModMenuIntegration.<Boolean>createOptionBuilder("randomizeTextureOffset", ConfigCategory.MAIN, List.of(OptionNotice.CONFIG_FILE_MAY_NEED_EDITING), List.of(OptionAffect.ALL_TEXTURES))
                                                .binding(SillyRenderingConfig.DEFAULT_RANDOMIZE_TEXTURE_OFFSET,
                                                        () -> config.randomizeTextureOffset,
                                                        (v) -> config.randomizeTextureOffset = v
                                                )
                                                .controller(TickBoxControllerBuilder::create)
                                                .build())
                                        // will you shut up about unchecked shit
                                        .option(SillyRenderingModMenuIntegration.<Boolean>createOptionBuilder("randomizeNormal", ConfigCategory.MAIN, Collections.<OptionNotice>emptyList(), List.of(OptionAffect.ENTITIES))
                                                .binding(SillyRenderingConfig.DEFAULT_RANDOMIZE_NORMAL,
                                                        () -> config.randomizeNormal,
                                                        (v) -> config.randomizeNormal = v
                                                )
                                                .controller(TickBoxControllerBuilder::create)
                                                .build())
                                        .option(SillyRenderingModMenuIntegration.<Boolean>createOptionBuilder("randomizeColor", ConfigCategory.MAIN, List.of(OptionNotice.RELOAD_REQUIRED), List.of(OptionAffect.ALL_TEXTURES))
                                                .binding(SillyRenderingConfig.DEFAULT_RANDOMIZE_COLOR,
                                                        () -> config.randomizeColor,
                                                        (v) -> config.randomizeColor = v
                                                )
                                                .controller(TickBoxControllerBuilder::create)
                                                .build())
                                        .option(SillyRenderingModMenuIntegration.<Boolean>createOptionBuilder("randomizeLight", ConfigCategory.MAIN, List.of(OptionNotice.RELOAD_REQUIRED), List.of(OptionAffect.GUIS, OptionAffect.LIQUIDS, OptionAffect.FIRE_TEXTURE))
                                                .binding(SillyRenderingConfig.DEFAULT_RANDOMIZE_LIGHT,
                                                        () -> config.randomizeLight,
                                                        (v) -> config.randomizeLight = v
                                                )
                                                .controller(TickBoxControllerBuilder::create)
                                                .build())
                                        .option(SillyRenderingModMenuIntegration.<Boolean>createOptionBuilder("useClassicRandom", ConfigCategory.MAIN)
                                                .binding(SillyRenderingConfig.DEFAULT_USE_CLASSIC_RANDOM,
                                                        () -> config.useClassicRandom,
                                                        (v) -> config.useClassicRandom = v
                                                )
                                                .controller(TickBoxControllerBuilder::create)
                                                .build())
                                        // build category
                                        .build()))
                .generateScreen(parent);
    }

    private static <T> Option.Builder<T> createOptionBuilder(String name, ConfigCategory category) {
        return createOptionBuilder(name, category, Collections.emptyList(), Collections.emptyList());
    }

    // just enforces name and desc components
    // I don't like how messy yacl is, so I did this to attempt to clean that area up a little.
    private static <T> Option.Builder<T> createOptionBuilder(String name, ConfigCategory category, List<OptionNotice> notices, List<OptionAffect> effects) {
        return Option.<T>createBuilder()
                .name(Component.translatable(String.format("gui.sillyrendering.config.category.%s.option.%s", category, name)))
                .description(OptionDescription.of(getDescription(name, category, notices, effects)));
    }

    private static Component getDescription(String name, ConfigCategory category, List<OptionNotice> notices, List<OptionAffect> affects) {
        MutableComponent c = Component.translatable(String.format("gui.sillyrendering.config.category.%s.option.%s.description", category, name));

        notices.forEach((n) -> {
            c.append("\n\n").append(n.comp);
        });

        if (affects.isEmpty()) return c;

        // I could probably turn this into some weird OptionAffectGroup or whatever and have presets, but I'm too lazy
        c.append("\n\nAffects:");
        affects.forEach((e) -> {
            if (e == OptionAffect.ALL_TEXTURES) {
                c.append("\n - ").append(e.comp)
                        .append("\n    - ")
                        .append(OptionAffect.BLOCKS.comp)
                        .append("\n    - ")
                        .append(OptionAffect.ENTITIES.comp)
                        .append("\n    - ")
                        .append(OptionAffect.GUIS.comp);
            } else {
                c.append("\n - ").append(e.comp);
            }
        });

        return c;
    }
}
