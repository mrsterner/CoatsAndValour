package dev.sterner.mixin;

import dev.sterner.CoatsAndValour;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(InGameHud.class)
public final class InGameHudMixin {
    @Unique
    private static final Identifier GANGRENE_HEARTS = CoatsAndValour.id("textures/gui/gangrene_hearts.png");

    @Inject(method = "renderHealthBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawHeart(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/gui/hud/InGameHud$HeartType;IIIZZ)V", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    public void renderHealthBar(DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci, InGameHud.HeartType heartType, int i, int j, int k, int l, int m, int n, int o, int p, int q) {
        drawGangreneHeart(context, p, q, 0, 0, false, false);
        ci.cancel();
    }

    @Unique
    private void drawGangreneHeart(DrawContext context, int x, int y, int u, int v, boolean blinking, boolean half) {
        context.drawTexture(GANGRENE_HEARTS, x, y, u, v, 9, 9);
    }
}
