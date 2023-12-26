package moonfather.tetra_tables.initialization;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CreativeTabEvent
{
    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event)
    {
        ResourceKey<CreativeModeTab> key = ResourceKey.create(ResourceKey.createRegistryKey(Registries.CREATIVE_MODE_TAB.location()), new ResourceLocation("tetra", "default"));
        if (event.getTabKey().equals(key))
        {
            for (Supplier<Item> table : itemsToAdd)
            {
                event.accept(table);
            }
        }
    }
    public static List<Supplier<Item>> itemsToAdd = new ArrayList<>();
}
