package ml.qizd.qizdmod;

import ml.qizd.qizdmod.mixin_interfaces.INoseOwner;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.network.PacketByteBuf;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ModTrackedDataHandlers {
    public static TrackedDataHandler<Optional<INoseOwner.Nose>> NOSE = new TrackedDataHandler<>() {
        @Override
        public void write(PacketByteBuf buf, Optional<INoseOwner.Nose> value) {
            buf.writeInt(value.isEmpty() ? -1 : value.get().getSizeInCm());
        }

        @Override
        public Optional<INoseOwner.Nose> read(PacketByteBuf buf) {
            int i = buf.readInt();
            return i == -1 ? Optional.empty() : Optional.of(new INoseOwner.Nose(i));
        }

        @Override
        public Optional<INoseOwner.Nose> copy(Optional<INoseOwner.Nose> value) {
            return value.isEmpty() ? Optional.empty() : Optional.of(new INoseOwner.Nose(value.get().getSizeInCm()));
        }
    };

    public static void register() {
        TrackedDataHandlerRegistry.register(NOSE);
    }
}
