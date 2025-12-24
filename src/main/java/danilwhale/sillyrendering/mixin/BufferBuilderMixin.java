package danilwhale.sillyrendering.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.llamalad7.mixinextras.sugar.ref.LocalShortRef;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import danilwhale.sillyrendering.MathHelper;
import danilwhale.sillyrendering.SillyRendering;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BufferBuilder.class)
public class BufferBuilderMixin {
    @Inject(method = "addVertex(FFFIFFIIFFF)V", at = @At("HEAD"))
    private void randomizeAddVertex(
            CallbackInfo ci,
            @Local(ordinal = 0, argsOnly = true) LocalFloatRef x,
            @Local(ordinal = 1, argsOnly = true) LocalFloatRef y,
            @Local(ordinal = 2, argsOnly = true) LocalFloatRef z,
            @Local(ordinal = 3, argsOnly = true) LocalFloatRef u,
            @Local(ordinal = 4, argsOnly = true) LocalFloatRef v) {
        if (SillyRendering.config.randomizeVertex) {
            x.set(x.get() + MathHelper.random());
            y.set(y.get() + MathHelper.random());
            z.set(z.get() + MathHelper.random());
        }

        if (SillyRendering.config.randomizeTextureOffset) {
            u.set((u.get() + MathHelper.random()) % 1.0f);
            v.set((v.get() + MathHelper.random()) % 1.0f);
        }
    }

    @Inject(method = "addVertex(FFF)Lcom/mojang/blaze3d/vertex/VertexConsumer;", at = @At("HEAD"))
    private void randomizeAddVertex(
            CallbackInfoReturnable<VertexConsumer> ci,
            @Local(ordinal = 0, argsOnly = true) LocalFloatRef x,
            @Local(ordinal = 1, argsOnly = true) LocalFloatRef y,
            @Local(ordinal = 2, argsOnly = true) LocalFloatRef z) {
        if (!SillyRendering.config.randomizeVertex) {
            return;
        }

        x.set(x.get() + MathHelper.random());
        y.set(y.get() + MathHelper.random());
        z.set(z.get() + MathHelper.random());
    }

    @Inject(method = "setUv", at = @At("HEAD"))
    private void randomizeSetUv(
            CallbackInfoReturnable<VertexConsumer> ci,
            @Local(ordinal = 0, argsOnly = true) LocalFloatRef u,
            @Local(ordinal = 1, argsOnly = true) LocalFloatRef v) {
        if (!SillyRendering.config.randomizeTextureOffset) {
            return;
        }

        u.set((u.get() + MathHelper.random()) % 1.0f);
        v.set((v.get() + MathHelper.random()) % 1.0f);
    }

    @Inject(method = "uvShort", at = @At("HEAD"))
    private void randomizeUvShort(
            CallbackInfoReturnable<VertexConsumer> ci,
            @Local(ordinal = 0, argsOnly = true) LocalShortRef u,
            @Local(ordinal = 1, argsOnly = true) LocalShortRef v
    ) {
        if (!SillyRendering.config.randomizeTextureOffset) {
            return;
        }

        u.set((short) ((u.get() + MathHelper.random() * Short.MAX_VALUE) % Short.MAX_VALUE));
        v.set((short) ((v.get() + MathHelper.random() * Short.MAX_VALUE) % Short.MAX_VALUE));
    }

    @ModifyVariable(method = "putRgba", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private static int randomizePutRgba(int argb) {
        if (!SillyRendering.config.randomizeColor) {
            return argb;
        }

        // extract color components
        int red = ARGB.red(argb);
        int green = ARGB.green(argb);
        int blue = ARGB.blue(argb);
        int alpha = ARGB.alpha(argb);

        // add random value and modulate result values for cool effects because clamp is boring tbh
        red = (red + (int) (MathHelper.random() * 255.0f)) % 255;
        green = (green + (int) (MathHelper.random() * 255.0f)) % 255;
        blue = (blue + (int) (MathHelper.random() * 255.0f)) % 255;
        // it's a bad idea to modify alpha component

        // pack back to argb and return
        return ARGB.color(red, green, blue, alpha);
    }

    @Inject(method = "setColor(IIII)Lcom/mojang/blaze3d/vertex/VertexConsumer;", at = @At("HEAD"))
    private void randomizeSetColor(
            CallbackInfoReturnable<VertexConsumer> ci,
            @Local(ordinal = 0, argsOnly = true) LocalIntRef r,
            @Local(ordinal = 1, argsOnly = true) LocalIntRef g,
            @Local(ordinal = 2, argsOnly = true) LocalIntRef b
    ) {
        if (!SillyRendering.config.randomizeColor) {
            return;
        }

        r.set((int)((r.get() + MathHelper.random() * 255.0f) % 255.0f));
        g.set((int)((g.get() + MathHelper.random() * 255.0f) % 255.0f));
        b.set((int)((b.get() + MathHelper.random() * 255.0f) % 255.0f));
    }

    @ModifyVariable(method = "setLight", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private int randomizeLight(int uv) {
        if (!SillyRendering.config.randomizeLight) {
            return uv;
        }
        return (uv + (int) (MathHelper.random() * Integer.MAX_VALUE)) % Integer.MAX_VALUE;
    }

    @ModifyVariable(method = "normalIntValue", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private static float randomizeNormalIntValue(float f) {
        if (!SillyRendering.config.randomizeNormal) {
            return f;
        }

        return (f + MathHelper.random()) % 1.0f;
    }
}
