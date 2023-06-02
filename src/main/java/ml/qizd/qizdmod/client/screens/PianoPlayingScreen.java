package ml.qizd.qizdmod.client.screens;

import ml.qizd.qizdmod.Instruments;
import ml.qizd.qizdmod.MusicNote;
import ml.qizd.qizdmod.Qizdmod;
import ml.qizd.qizdmod.client.screens.widgets.KeyboardNoteButtonWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.List;

@Environment(EnvType.CLIENT)
public class PianoPlayingScreen extends Screen {
    ClientWorld world;
    ClientPlayerEntity user;
    private KeyboardNoteButtonWidget[][] buttons;
    private final int[][] keyboard_layout = {
            {GLFW.GLFW_KEY_2, GLFW.GLFW_KEY_3, GLFW.GLFW_KEY_5, GLFW.GLFW_KEY_6, GLFW.GLFW_KEY_7},
            {GLFW.GLFW_KEY_Q, GLFW.GLFW_KEY_W, GLFW.GLFW_KEY_E, GLFW.GLFW_KEY_R, GLFW.GLFW_KEY_T, GLFW.GLFW_KEY_Y, GLFW.GLFW_KEY_U},
            {GLFW.GLFW_KEY_S, GLFW.GLFW_KEY_D, GLFW.GLFW_KEY_G, GLFW.GLFW_KEY_H, GLFW.GLFW_KEY_J},
            {GLFW.GLFW_KEY_Z, GLFW.GLFW_KEY_X, GLFW.GLFW_KEY_C, GLFW.GLFW_KEY_V, GLFW.GLFW_KEY_B, GLFW.GLFW_KEY_N, GLFW.GLFW_KEY_M}
    };
    private final MusicNote[][] notes = {
            {MusicNote.of("C#4"), MusicNote.of("D#4"), MusicNote.of("F#4"), MusicNote.of("G#4"), MusicNote.of("A#4")},
            {MusicNote.of("C4"), MusicNote.of("D4"), MusicNote.of("E4"), MusicNote.of("F4"), MusicNote.of("G4"), MusicNote.of("A4"), MusicNote.of("B4")},
            {MusicNote.of("C#3"), MusicNote.of("D#3"), MusicNote.of("F#3"), MusicNote.of("G#3"), MusicNote.of("A#3")},
            {MusicNote.of("C3"), MusicNote.of("D3"), MusicNote.of("E3"), MusicNote.of("F3"), MusicNote.of("G3"), MusicNote.of("A3"), MusicNote.of("B3")}
    };

    private final String[][] keys = {
            {"2", "3", "5", "6", "7"},
            {"Q", "W", "E", "R", "T", "Y", "U"},
            {"S", "D", "G", "H", "J"},
            {"Z", "X", "C", "V", "B", "N", "M"}
    };

    public PianoPlayingScreen(ClientWorld world, ClientPlayerEntity user) {
        super(Text.of("Instrument screen"));
        this.world = world;
        this.user = user;
    }

    public void playNote(MusicNote note) {
        Instruments.playPianoNote(world, user, note);

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeEnumConstant(Instruments.Type.Piano);
        buf.writeInt(note.getNote());
        ClientPlayNetworking.send(Qizdmod.PLAYED_NOTE, buf);
    }

    @Override
    protected void init() {
        buttons = new KeyboardNoteButtonWidget[4][];

        int LEFT_MARGIN = 12, UPPER_MARGIN = 12;
        int WHITE_BUTTON_H = 64, BLACK_BUTTON_H = 32;
        int WHITE_BUTTON_W = 16, BLACK_BUTTON_W = 10;
        int MARGIN = 8;

        // Creating black buttons
        for (int i = 0; i < 4; i+=2) {
            buttons[i] = new KeyboardNoteButtonWidget[5];
            for (int j = 0; j < 5; j++) {
                int x = LEFT_MARGIN + WHITE_BUTTON_W - BLACK_BUTTON_W / 2 + WHITE_BUTTON_W * j;
                int y = UPPER_MARGIN + (i == 2 ? WHITE_BUTTON_H + MARGIN : 0);
                if (j >= 2) {
                    x += WHITE_BUTTON_W;
                }

                int finalJ = j;
                int finalI = i;
                buttons[i][j] = new KeyboardNoteButtonWidget(
                        x, y, BLACK_BUTTON_W, BLACK_BUTTON_H, keys[i][j],
                        KeyboardNoteButtonWidget.Type.BLACK, null,
                        () -> playNote(notes[finalI][finalJ])
                );
            }
        }

        // Creating white buttons
        for (int i = 1; i < 4; i += 2) {
            buttons[i] = new KeyboardNoteButtonWidget[7];
            for (int j = 0; j < 7; j++) {
                int x = LEFT_MARGIN + WHITE_BUTTON_W * j;
                int y = UPPER_MARGIN + (i == 3 ? WHITE_BUTTON_H + MARGIN : 0);
                List<KeyboardNoteButtonWidget> overlapping = null;
                switch (j) {
                    case 0 -> overlapping = List.of(buttons[i - 1][0]);
                    case 1 -> overlapping = List.of(buttons[i - 1][0], buttons[i - 1][1]);
                    case 2 -> overlapping = List.of(buttons[i - 1][1]);
                    case 3 -> overlapping = List.of(buttons[i - 1][2]);
                    case 4 -> overlapping = List.of(buttons[i - 1][2], buttons[i - 1][3]);
                    case 5 -> overlapping = List.of(buttons[i - 1][3], buttons[i - 1][4]);
                    case 6 -> overlapping = List.of(buttons[i - 1][4]);
                }

                int finalI = i;
                int finalJ = j;
                buttons[i][j] = new KeyboardNoteButtonWidget(
                        x, y, WHITE_BUTTON_W, WHITE_BUTTON_H, keys[i][j],
                        KeyboardNoteButtonWidget.Type.WHITE, overlapping,
                        () -> playNote(notes[finalI][finalJ])
                );

                addDrawableChild(buttons[i][j]);
            }
        }

        for (int i = 0; i < 4; i+=2) {
            for (int j = 0; j < 5; j++) {
                addDrawableChild(buttons[i][j]);
            }
        }

    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        MusicNote note = null;
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < keyboard_layout[row].length; column++) {
                if (keyboard_layout[row][column] == keyCode) {
                    note = notes[row][column];
                    buttons[row][column].click();
                }
            }
        }

        if (note != null) {
            playNote(note);
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}