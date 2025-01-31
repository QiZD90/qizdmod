package ml.qizd.qizdmod;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {
    public static final SoundEvent HARP_NOTE_C4 = new SoundEvent(new Identifier("qizdmod", "harp_c4"));
    public static final SoundEvent HARP_NOTE_C5 = new SoundEvent(new Identifier("qizdmod", "harp_c5"));
    public static final SoundEvent LUTE_NOTE_C4 = new SoundEvent(new Identifier("qizdmod", "lute_c4"));
    public static final SoundEvent LUTE_NOTE_C5 = new SoundEvent(new Identifier("qizdmod", "lute_c5"));
    public static final SoundEvent PIANO_NOTE_C4 = new SoundEvent(new Identifier("qizdmod", "piano_c4"));

    public static final SoundEvent BLOCK_BELL_USE
        = new SoundEvent(new Identifier("minecraft", "block.bell.use"), 256f);

    public static void register() {
        Registry.register(Registry.SOUND_EVENT, HARP_NOTE_C4.getId(), HARP_NOTE_C4);
        Registry.register(Registry.SOUND_EVENT, HARP_NOTE_C5.getId(), HARP_NOTE_C5);
        Registry.register(Registry.SOUND_EVENT, LUTE_NOTE_C4.getId(), LUTE_NOTE_C4);
        Registry.register(Registry.SOUND_EVENT, LUTE_NOTE_C5.getId(), LUTE_NOTE_C5);
        Registry.register(Registry.SOUND_EVENT, PIANO_NOTE_C4.getId(), PIANO_NOTE_C4);
        Registry.register(Registry.SOUND_EVENT, new Identifier("qizdmod", "block.bell.use"), BLOCK_BELL_USE);
    }
}
