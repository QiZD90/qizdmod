package ml.qizd.qizdmod.client.screens.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class KeyboardNoteButtonWidget extends InstrumentNoteButtonWidget {
    public enum Type {
        WHITE,
        BLACK
    }

    public Type type;
    @Nullable
    public List<KeyboardNoteButtonWidget> overlapping;
    public KeyboardNoteButtonWidget(int x, int y, int width, int height,
                                    String label, Type type,
                                    @Nullable List<KeyboardNoteButtonWidget> overlapping, OnClickCallback callback) {
        super(x, y, width, height, label, callback);
        this.type = type;
        this.overlapping = overlapping;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.active && this.visible) {
            if (this.isValidClickButton(button)) {
                boolean bl = this.clicked(mouseX, mouseY);
                if (bl && checkOverlapping(mouseX, mouseY)) {
                    this.onClick(mouseX, mouseY);
                    return true;
                }
            }

            return false;
        }

        return false;
    }

    private boolean checkOverlapping(double mouseX, double mouseY) {
        if (this.overlapping == null)
            return true;

        for (KeyboardNoteButtonWidget widget : this.overlapping) {
            if (mouseX > widget.x && mouseX < widget.x + widget.width
                    && mouseY > widget.y && mouseY < widget.y + widget.height)
                return false;
        }
        return true;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        if (this.hovered && checkOverlapping(mouseX, mouseY)) {
            RenderSystem.setShaderColor(0.0F, 1.0F, 1.0F, 1.0F);
        } else if (this.pressedCountdownTicks > 0) {
            float red = this.type == Type.WHITE
                    ? ((float) MAX_COUNTDOWN_TICKS - pressedCountdownTicks) / (float) MAX_COUNTDOWN_TICKS
                    : 1f - ((float) MAX_COUNTDOWN_TICKS - pressedCountdownTicks) / (float) MAX_COUNTDOWN_TICKS;
            RenderSystem.setShaderColor(red, 1.0F, 1.0F, 1.0F);
            this.pressedCountdownTicks--;
        } else {
            if (this.type == Type.WHITE)
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            else
                RenderSystem.setShaderColor(0f, 0f, 0f, 1.0F);
        }
        RenderSystem.setShaderTexture(0, TEXTURE);
        drawTexture(matrices, x, y, 0, 0, width, height);
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        int textColor = this.type == Type.WHITE ? 0 : 0xffffff;
        drawCenteredText(matrices, textRenderer, label, this.x + this.width / 2, this.y + this.height - 10, textColor);
    }
}
