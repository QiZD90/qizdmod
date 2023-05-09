package ml.qizd.qizdmod.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class InstrumentNoteButtonWidget extends ClickableWidget {
    protected static final Identifier TEXTURE = new Identifier("minecraft", "textures/gui/container/dispenser.png");
    public static interface OnClickCallback {
        public void onClick();
    }

    private OnClickCallback callback;
    protected Text label;
    public static int MAX_COUNTDOWN_TICKS = 20;
    public int pressedCountdownTicks = 0;
    public InstrumentNoteButtonWidget(int x, int y, int width, int height, String label, OnClickCallback callback) {
        super(x, y, width, height, Text.of(""));
        this.callback = callback;
        this.label = Text.of(label);
    }

    public void onClick(double mouseX, double mouseY) {
        if (callback != null)
            callback.onClick();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.active && this.visible) {
            if (this.isValidClickButton(button)) {
                boolean bl = this.clicked(mouseX, mouseY);
                if (bl) {
                    this.onClick(mouseX, mouseY);
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public void click() {
        this.pressedCountdownTicks = MAX_COUNTDOWN_TICKS;
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
        drawCenteredText(matrices, textRenderer, label, this.x + this.width / 2, this.y + (this.height - 8) / 2, 0);
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {}
}
