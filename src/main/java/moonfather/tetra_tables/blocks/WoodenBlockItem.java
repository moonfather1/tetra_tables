package moonfather.tetra_tables.blocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class WoodenBlockItem extends BlockItem
{
    public WoodenBlockItem(@NotNull Block block, Item.Properties properties)
    {
        super(block, properties);
        this.burnTime = 900;
    }

    public WoodenBlockItem(@NotNull Block block, CreativeModeTab tab)
    {
        super(block, new Item.Properties().tab(tab));
        this.burnTime = 900;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType)
    {
        return burnTime;
    }
    private final int burnTime; // plank is 300
}
