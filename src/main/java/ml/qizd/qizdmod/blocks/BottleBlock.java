package ml.qizd.qizdmod.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.HashMap;
import java.util.Map;

public class BottleBlock extends Block {
    public static final IntProperty COUNT = IntProperty.of("count", 1, 3);
    public static final BottleBlock BOTTLE_BLOCK = new BottleBlock();

    public BottleBlock() {
        super(FabricBlockSettings
                .of(Material.GLASS)
                .sounds(BlockSoundGroup.GLASS)
                .nonOpaque()
        );

        setDefaultState(getDefaultState().with(COUNT, 1));
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        if (!context.shouldCancelInteraction()
                && context.getStack().getItem() == this.asItem()
                && state.get(COUNT) < 3) {
            return true;
        }

        return super.canReplace(state, context);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            return blockState.cycle(COUNT);
        }

        return super.getPlacementState(ctx);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return Block.sideCoversSmallSquare(world, pos.down(), Direction.UP);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COUNT);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext ctx) {
        switch (state.get(COUNT)) {
            case 1: {
                return VoxelShapes.cuboid(0.35f, 0f, 0.35f, 0.65f, 0.8f, 0.65f);
            }

            case 2: {
                return VoxelShapes.cuboid(0.2f, 0f, 0.35f, 0.8f, 0.8f, 0.65f);
            }

            case 3: {
                return VoxelShapes.cuboid(0.2f, 0f, 0.2f, 0.8f, 0.8f, 0.8f);
            }

            default: {
                return VoxelShapes.fullCube();
            }
        }
    }
}
