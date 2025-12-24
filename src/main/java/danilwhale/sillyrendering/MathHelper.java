package danilwhale.sillyrendering;

// welcome back MCP
public class MathHelper {
    public static float random() {
        float value = (float) (SillyRendering.config.useClassicRandom ? Math.random() : (Math.random() - Math.random()));
        return value * SillyRendering.config.intensity;
    }
}
