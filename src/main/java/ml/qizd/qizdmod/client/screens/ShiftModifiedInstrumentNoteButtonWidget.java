package ml.qizd.qizdmod.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ShiftModifiedInstrumentNoteButtonWidget extends InstrumentNoteButtonWidget {
    private static Text shiftText = Text.of("⇧");

    public ShiftModifiedInstrumentNoteButtonWidget(int x, int y, int width, int height, String label, OnClickCallback callback) {
        super(x, y, width, height, label, callback);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        if (this.hovered) {
            RenderSystem.setShaderColor(0.0F, 1.0F, 1.0F, 1.0F);
        } else if (this.pressedCountdownTicks > 0) {
            RenderSystem.setShaderColor(((float) MAX_COUNTDOWN_TICKS - pressedCountdownTicks) / (float) MAX_COUNTDOWN_TICKS, 1.0F, 1.0F, 1.0F);
            this.pressedCountdownTicks--;
        } else {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
        RenderSystem.setShaderTexture(0, TEXTURE);
        drawTexture(matrices, x, y, 0, 0, width, height);
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        int color = Screen.hasShiftDown() ? 0x00ffff : 0x000000;
        drawCenteredText(matrices, textRenderer, shiftText, this.x + this.width / 2 - 3, this.y + (this.height - 8) / 2, color);
        drawCenteredText(matrices, textRenderer, label, this.x + this.width / 2 + 3, this.y + (this.height - 8) / 2, 0);
    }
}
