package ml.qizd.qizdmod;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;

public class Instruments {
    public enum Type {
        Lyre,
        Lute,
        BoneFlute,
        Piano
    }

    public static void playNote(World world, PlayerEntity player, Instruments.Type type, MusicNote note) {
        switch (type) {
            case Lyre -> playLyreNote(world, player, note);
            case Lute -> playLuteNote(world, player, note);
            case Piano -> playPianoNote(world, player, note);
            default -> {}
        }
    }

    public static void playLyreNote(World world, PlayerEntity player, MusicNote note) {
        if (note.lower(MusicNote.C5)) {
            world.playSound(
                    player,
                    player.getBlockPos(),
                    ModSounds.HARP_NOTE_C4,
                    SoundCategory.AMBIENT,
                    1f,
                    MusicNote.ratio(note, MusicNote.C4)
            );
        } else {
            world.playSound(
                    player,
                    player.getBlockPos(),
                    ModSounds.HARP_NOTE_C5,
                    SoundCategory.AMBIENT,
                    1f,
                    MusicNote.ratio(note, MusicNote.C5)
            );
        }
    }

    public static void playLuteNote(World world, PlayerEntity player, MusicNote note) {
        if (note.lower(MusicNote.C5)) {
            world.playSound(
                    player,
                    player.getBlockPos(),
                    ModSounds.LUTE_NOTE_C4,
                    SoundCategory.AMBIENT,
                    1f,
                    MusicNote.ratio(note, MusicNote.C4)
            );
        } else {
            world.playSound(
                    player,
                    player.getBlockPos(),
                    ModSounds.LUTE_NOTE_C5,
                    SoundCategory.AMBIENT,
                    1f,
                    MusicNote.ratio(note, MusicNote.C5)
            );
        }
    }

    public static void playPianoNote(World world, PlayerEntity player, MusicNote note) {
        world.playSound(
                player,
                player.getBlockPos(),
                ModSounds.PIANO_NOTE_C4,
                SoundCategory.AMBIENT,
                1f,
                MusicNote.ratio(note, MusicNote.C4)
        );
    }
}
