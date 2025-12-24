package danilwhale.sillyrendering.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class SillyRenderingConfig {
    public static ConfigClassHandler<SillyRenderingConfig> HANDLER = ConfigClassHandler.createBuilder(SillyRenderingConfig.class)
            .id(Identifier.fromNamespaceAndPath("sillyrenderer", "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("sillyrendering.json5"))
                    .setJson5(true)
                    .build())
            .build();

    // these exist because we have to provide defaults to YACL
    public static final float DEFAULT_INTENSITY = 0.1f;
    public static final boolean DEFAULT_RANDOMIZE_VERTEX = true;
    public static final boolean DEFAULT_RANDOMIZE_TEXTURE_OFFSET = false;
    public static final boolean DEFAULT_RANDOMIZE_NORMAL = false;
    public static final boolean DEFAULT_RANDOMIZE_COLOR = false;
    public static final boolean DEFAULT_RANDOMIZE_LIGHT = false;
    public static final boolean DEFAULT_USE_CLASSIC_RANDOM = false;

    @SerialEntry(comment = "The intensity of sillyrendering's effects")
    public float intensity = DEFAULT_INTENSITY;
    @SerialEntry(comment = "Randomizes vertex position values when drawn, which causes texture faces to warp and move around the screen")
    public boolean randomizeVertex = DEFAULT_RANDOMIZE_VERTEX;
    @SerialEntry(comment = "Randomizes textures' UV values when drawn, which causes the texture to point to random offsets in it's texture atlas")
    public boolean randomizeTextureOffset = DEFAULT_RANDOMIZE_TEXTURE_OFFSET;
    @SerialEntry(comment = "Randomizes the normal values of each vertice on every texture when drawn")
    public boolean randomizeNormal = DEFAULT_RANDOMIZE_NORMAL;
    @SerialEntry(comment = "Randomizes the color of each vertice on every texture when drawn")
    public boolean randomizeColor = DEFAULT_RANDOMIZE_COLOR;
    @SerialEntry(comment = "Randomizes brightness values of each vertice on every texture when drawn")
    public boolean randomizeLight = DEFAULT_RANDOMIZE_LIGHT;
    @SerialEntry(comment = "Uses sillyrendering's old random function (regular java.util.Random)")
    public boolean useClassicRandom = DEFAULT_USE_CLASSIC_RANDOM;
}
