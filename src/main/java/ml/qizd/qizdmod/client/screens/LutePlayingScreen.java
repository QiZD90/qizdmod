package ml.qizd.qizdmod.client.screens;

import ml.qizd.qizdmod.Instruments;
import ml.qizd.qizdmod.MusicNote;
import ml.qizd.qizdmod.Qizdmod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class LutePlayingScreen extends Screen {
    World world;
    PlayerEntity user;
    private InstrumentNoteButtonWidget[] buttons;
    private final int[][] keyboard_layout = {
            {GLFW.GLFW_KEY_Q, GLFW.GLFW_KEY_W, GLFW.GLFW_KEY_E, GLFW.GLFW_KEY_R, GLFW.GLFW_KEY_T, GLFW.GLFW_KEY_Y, GLFW.GLFW_KEY_U},
            {GLFW.GLFW_KEY_A, GLFW.GLFW_KEY_S, GLFW.GLFW_KEY_D, GLFW.GLFW_KEY_F, GLFW.GLFW_KEY_G, GLFW.GLFW_KEY_H, GLFW.GLFW_KEY_J},
            {GLFW.GLFW_KEY_Z, GLFW.GLFW_KEY_X, GLFW.GLFW_KEY_C, GLFW.GLFW_KEY_V, GLFW.GLFW_KEY_B, GLFW.GLFW_KEY_N, GLFW.GLFW_KEY_M}
    };
    private final MusicNote[][] notes = {
            {MusicNote.of("C5"), MusicNote.of("D5"), MusicNote.of("Eb5"), MusicNote.of("F5"), MusicNote.of("G5"), MusicNote.of("Ab5"), MusicNote.of("Bb5")},
            {MusicNote.of("C4"), MusicNote.of("D4"), MusicNote.of("Eb4"), MusicNote.of("F4"), MusicNote.of("G4"), MusicNote.of("Ab4"), MusicNote.of("Bb4")},
            {MusicNote.of("C3"), MusicNote.of("D3"), MusicNote.of("Eb3"), MusicNote.of("F3"), MusicNote.of("G3"), MusicNote.of("Ab3"), MusicNote.of("Bb3")},
    };

    private final MusicNote[][] shiftPressedNotes = {
            {MusicNote.of("C5"), MusicNote.of("D5"), MusicNote.of("Eb5"), MusicNote.of("F5"), MusicNote.of("G5"), MusicNote.of("Ab5"), MusicNote.of("B5")},
            {MusicNote.of("C4"), MusicNote.of("D4"), MusicNote.of("Eb4"), MusicNote.of("F4"), MusicNote.of("G4"), MusicNote.of("Ab4"), MusicNote.of("B4")},
            {MusicNote.of("C3"), MusicNote.of("D3"), MusicNote.of("Eb3"), MusicNote.of("F3"), MusicNote.of("G3"), MusicNote.of("Ab3"), MusicNote.of("B3")},
    };

    private final String[][] keys = {
            {"Q", "W", "E", "R", "T", "Y", "U"},
            {"A", "S", "D", "F", "G", "H", "J"},
            {"Z", "X", "C", "V", "B", "N", "M"}
    };

    public LutePlayingScreen(World world, PlayerEntity user) {
        super(Text.of("Instrument screen"));
        this.world = world;
        this.user = user;
    }

    public void playNote(MusicNote note) {
        Instruments.playLuteNote(world, user, note);

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeEnumConstant(Instruments.Type.Lute);
        buf.writeInt(note.getNote());
        ClientPlayNetworking.send(Qizdmod.PLAYED_NOTE, buf);
    }

    @Override
    protected void init() {
        buttons = new InstrumentNoteButtonWidget[21];

        int LEFT_MARGIN = 12, UPPER_MARGIN = 12;
        int MARGIN = 8, BUTTON_SIZE = 24;

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 7; column++) {
                int finalRow = row;
                int finalColumn = column;

                if (column == 6) {
                    buttons[row * 7 + column] = new ShiftModifiedInstrumentNoteButtonWidget(
                            LEFT_MARGIN + column * MARGIN + column * BUTTON_SIZE,
                            UPPER_MARGIN + row * MARGIN + row * BUTTON_SIZE,
                            BUTTON_SIZE,
                            BUTTON_SIZE,
                            keys[row][column],
                            () -> playNote(Screen.hasShiftDown()
                                    ? shiftPressedNotes[finalRow][finalColumn]
                                    : notes[finalRow][finalColumn])
                    );
                } else {
                    buttons[row * 7 + column] = new InstrumentNoteButtonWidget(
                            LEFT_MARGIN + column * MARGIN + column * BUTTON_SIZE,
                            UPPER_MARGIN + row * MARGIN + row * BUTTON_SIZE,
                            BUTTON_SIZE,
                            BUTTON_SIZE,
                            keys[row][column],
                            () -> playNote(Screen.hasShiftDown()
                                    ? shiftPressedNotes[finalRow][finalColumn]
                                    : notes[finalRow][finalColumn])
                    );
                }
            }
        }


        for (InstrumentNoteButtonWidget widget : buttons) {
            addDrawableChild(widget);
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        MusicNote note = null;
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 7; column++) {
                if (keyboard_layout[row][column] == keyCode) {
                    if (Screen.hasShiftDown()) {
                        note = shiftPressedNotes[row][column];
                    } else {
                        note = notes[row][column];
                    }

                    buttons[row * 7 + column].click();
                }
            }
        }

        if (note != null) {
            playNote(note);
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}