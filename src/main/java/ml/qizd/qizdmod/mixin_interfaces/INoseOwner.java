package ml.qizd.qizdmod.mixin_interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public interface INoseOwner {
    class Nose {
        private static final Random random = new Random();
        int sizeInCm;

        public float getScale() {
            if (sizeInCm <= 7) {
                return 0.7f;
            } else if (sizeInCm <= 12) {
                return 0.8f;
            } else if (sizeInCm <= 15) {
                return 1.0f;
            } else if (sizeInCm <= 20) {
                return 1.2f;
            } else {
                return 1.4f;
            }
        }

        public void writeToNbt(NbtCompound nbt) {
            NbtCompound subNbt = new NbtCompound();
            subNbt.putInt("SizeInCm", this.getSizeInCm());

            nbt.put("Nose", subNbt);
        }

        public static Optional<Nose> readFromNbt(NbtCompound nbt) {
            if (!nbt.contains("Nose") || nbt.get("Nose").getType() != NbtElement.COMPOUND_TYPE)
                return Optional.of(new Nose());

            NbtCompound subNbt = Objects.requireNonNull((NbtCompound) nbt.get("Nose"));
            if (!subNbt.contains("SizeInCm"))
                return Optional.empty();

            return Optional.of(new Nose(subNbt.getInt("SizeInCm")));
        }

        public int getSizeInCm() {
            return sizeInCm;
        }

        public Nose(int sizeInCm) {
            this.sizeInCm = sizeInCm;
        }

        public Nose() {
            this(random.nextInt(4, 25));
        }
    }

    boolean hasNose();
    void setNose(Optional<Nose> nose);
    Optional<Nose> getNose();
}
