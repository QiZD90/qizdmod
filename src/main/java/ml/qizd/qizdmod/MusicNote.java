package ml.qizd.qizdmod;

import java.util.Map;

public class MusicNote {
    private int noteNumber = 0; // C0 is considered a 0 note

    public static final MusicNote C4 = new MusicNote(12 * 4);
    public static final MusicNote C5 = new MusicNote(12 * 5);
    private static final Map<Character, Integer> notes = Map.of(
            'C', 0,
            'D', 2,
            'E', 4,
            'F', 5,
            'G', 7,
            'A', 9,
            'B', 11
    );

    private MusicNote(int noteNumber) {
        this.noteNumber = noteNumber;
    }

    public static MusicNote of(String string) {
        if (string.length() < 2 || string.length() > 3) {
            throw new IllegalArgumentException();
        }

        Character noteLetter = string.charAt(0);
        if (!notes.containsKey(noteLetter)) {
            throw new IllegalArgumentException();
        }

        int note = notes.get(noteLetter);
        if (string.length() == 3) {
            if (string.charAt(1) == '#') {
                note += 1;
            } else if (string.charAt(1) == 'b') {
                note -= 1;
            } else {
                throw new IllegalArgumentException();
            }
        }

        char octaveLetter = string.charAt(string.length() - 1);
        if (octaveLetter < '0' || octaveLetter > '9') {
            throw new IllegalArgumentException();
        }
        int octave = octaveLetter - '0';

        return new MusicNote(octave * 12 + note);
    }

    public boolean lower(MusicNote b) {
        return this.noteNumber < b.noteNumber;
    }

    public static float ratio(MusicNote a, MusicNote b) {
        return (float) Math.pow(1.05946, a.noteNumber - b.noteNumber);
    }
}
