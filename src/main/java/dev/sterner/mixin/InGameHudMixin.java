package dev.sterner.mixin;

import dev.sterner.CoatsAndValour;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow protected abstract void drawHeart(DrawContext context, InGameHud.HeartType type, int x, int y, int v, boolean blinking, boolean halfHeart);

    @Unique
    private static final Identifier GANGRENE_HEARTS = CoatsAndValour.id("textures/gui/gangrene_hearts.png");

    @Inject(method = "renderHealthBar", at = @At("TAIL"))
    public void renderHealthBar(DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci) {
        drawGangreneHeart(context, null, x, y, 0, 0, false, false, false);
    }

    @Unique
    private void drawGangreneHeart(DrawContext context, InGameHud.HeartType type, int x, int y, int u, int v, boolean blinking, boolean half, boolean gangrene) {
        if (gangrene) {
            context.drawTexture(GANGRENE_HEARTS, x, y, u, v, 9, 9);
        } else {
            drawHeart(context, type, x, y, v, blinking, half);
        }
    }
}
