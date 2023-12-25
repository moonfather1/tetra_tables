package moonfather.tetra_tables.blocks;

import moonfather.tetra_tables.initialization.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import se.mickelus.tetra.blocks.workbench.WorkbenchTile;

public class TetraTableBlockEntity extends WorkbenchTile
{
    public TetraTableBlockEntity(BlockPos pos, BlockState state)
    {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType()
    {
        return Registration.TETRA_TABLE_BE.get();
    }
}
