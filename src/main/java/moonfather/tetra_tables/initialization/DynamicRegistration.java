package moonfather.tetra_tables.initialization;

import moonfather.tetra_tables.Constants;
import moonfather.tetra_tables.blocks.TetraTable;
import moonfather.tetra_tables.blocks.WoodenBlockItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import se.mickelus.tetra.TetraItemGroup;

public class DynamicRegistration
{
    public static void registerBlocksForThirdPartyWood(RegistryEvent<Item> event)
    {
        //if (! event.getRegistryKey().equals(Registry.ITEM_REGISTRY)) { return; } // Registries.BLOCK is too early
        // above is always true in 1.18

        if (! ModList.get().isLoaded("workshop_for_handsome_adventurer"))
        {
            return;
        }
        try  // because of unfreeze fuckery
        {
            final String mc = "minecraft";
            boolean frozen = ((ForgeRegistry) ForgeRegistries.BLOCKS).isLocked();
            if (frozen) { ((ForgeRegistry) ForgeRegistries.BLOCKS).unfreeze(); }

            for (String wood : DynamicWoodListAccessor.getWoodIds())
            {
                if (DynamicWoodListAccessor.getHostMod(wood).equals(mc) && ForgeRegistries.BLOCKS.containsKey(new ResourceLocation(Constants.MODID, "tetra_table_" + wood)))
                {
                    continue; // done manually, acacia for example
                }
                Block block = new TetraTable();
                Item item = new WoodenBlockItem(block, TetraItemGroup.instance);
                ForgeRegistries.BLOCKS.register(block.setRegistryName("tetra_table_" + wood));
                ForgeRegistries.ITEMS.register(item.setRegistryName("tetra_table_" + wood));
                Registration.blocks_table3.add(() -> block);
            }
            if (frozen) { ((ForgeRegistry) ForgeRegistries.BLOCKS).freeze(); }
        }
        catch (Exception ignored)
        {
            System.out.println(ignored.getMessage());
        }
    }
}
