package moonfather.tetra_tables.initialization;

import moonfather.tetra_tables.Constants;
import moonfather.tetra_tables.blocks.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Registration
{
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MODID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Constants.MODID);

    public static void init()
    {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCK_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    /////////////////////////////////////////////////

    public static final List<Supplier<Block>> blocks_table3 = new ArrayList<>();
    public static final String[] woodTypes = { "oak", "spruce", "jungle", "birch", "dark_oak", "acacia", "mangrove", "warped", "crimson" };

    static
    {
        // small tables
        for (String woodType : Registration.woodTypes)
        {
            RegistryObject<Block> block = BLOCKS.register("tetra_table_" + woodType, TetraTable::new);
            blocks_table3.add(block);
            FromBlock(block);
        }
    }



    private static RegistryObject<Item> FromBlock(RegistryObject<Block> block)
    {
        return ITEMS.register(block.getId().getPath(), () -> new WoodenBlockItem(block.get()));
    }

    private static Block[] ListToArray(List<Supplier<Block>> list) {
        Block[] result = new Block[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i).get();
        }
        return result;
    }

    ////////////////////////////////////////////////////////

    public static final RegistryObject<BlockEntityType<TetraTableBlockEntity>> TETRA_TABLE_BE = BLOCK_ENTITIES.register("tetra_table_be", () -> BlockEntityType.Builder.of(TetraTableBlockEntity::new, ListToArray(blocks_table3)).build(null));
}
