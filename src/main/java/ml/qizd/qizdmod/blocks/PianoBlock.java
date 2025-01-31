package ml.qizd.qizdmod.blocks;

import ml.qizd.qizdmod.Instruments;
import ml.qizd.qizdmod.Qizdmod;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PianoBlock extends HorizontalFacingBlock {
    public enum Part implements StringIdentifiable {
        LEFT("left"),
        RIGHT("right");

        private final String name;

        Part(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }

    public static final EnumProperty<Part> PART = EnumProperty.of("part", Part.class);

    public PianoBlock() {
        super(FabricBlockSettings
                .of(Material.WOOD)
                .nonOpaque()
                .hardness(1f)
        );

        setDefaultState(getDefaultState().with(PART, Part.LEFT));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.CONSUME;
        }

        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeEnumConstant(Instruments.Type.Piano);
        ServerPlayNetworking.send((ServerPlayerEntity) player, Qizdmod.STARTED_PLAYING, buf);
        return ActionResult.SUCCESS;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        if (world.isClient) {
            return;
        }

        BlockPos secondPartPos = pos.offset(getDirectionTowardsOtherPart(state.get(PART), state.get(FACING)));
        world.setBlockState(secondPartPos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL | Block.SKIP_DROPS);
        //world.syncWorldEvent(player, WorldEvents.BLOCK_BROKEN, secondPartPos, Block.getRawIdFromState(blockState));
        /*if (!world.isClient && player.isCreative()
                && (part = state.get(PART)) == Part.LEFT
                && (blockState = world.getBlockState(blockPos = pos.offset(getDirectionTowardsOtherPart(part, state.get(FACING))))).isOf(this) && blockState.get(PART) == Part.RIGHT) {
            world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL | Block.SKIP_DROPS);
            world.syncWorldEvent(player, WorldEvents.BLOCK_BROKEN, blockPos, Block.getRawIdFromState(blockState));
        }*/
    }

    // TODO: this has a bug when trying to place in spawn protection zone but vanilla beds are bugged too...
    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getPlayerFacing();
        BlockPos blockPos = ctx.getBlockPos();
        BlockPos blockPos2 = blockPos.offset(direction.rotateYClockwise());
        World world = ctx.getWorld();
        if (world.getBlockState(blockPos2).canReplace(ctx) && world.getWorldBorder().contains(blockPos2)) {
            return this.getDefaultState().with(FACING, direction);
        }
        return null;
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.BLOCK;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClient) {
            BlockPos blockPos = pos.offset(state.get(FACING).rotateYClockwise());
            world.setBlockState(blockPos, state.with(PART, Part.RIGHT), Block.NOTIFY_ALL);
            world.updateNeighbors(pos, Blocks.AIR);
            state.updateNeighbors(world, pos, Block.NOTIFY_ALL);
        }
    }

    @Override
    public long getRenderingSeed(BlockState state, BlockPos pos) {
        BlockPos blockPos = pos.offset(state.get(FACING).rotateYClockwise(), state.get(PART) == Part.LEFT ? 0 : 1);
        return MathHelper.hashCode(blockPos.getX(), pos.getY(), blockPos.getZ());
    }

    private static Direction getDirectionTowardsOtherPart(Part part, Direction direction) {
        return part == Part.LEFT ? direction.rotateYClockwise() : direction.rotateYCounterclockwise();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART);
    }
}
