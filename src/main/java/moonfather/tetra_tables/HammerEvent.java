package moonfather.tetra_tables;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import se.mickelus.tetra.TetraToolActions;

public class HammerEvent
{
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event)
    {
        BlockState state = event. getLevel().getBlockState(event.getPos());
        if (state.is(workbench) && ! event.getEntity().isCrouching())
        {
            if (event.getHand().equals(InteractionHand.MAIN_HAND) && event.getEntity().getMainHandItem().canPerformAction(TetraToolActions.hammer))
            {
                Block newTable = tryGetTable(state.getBlock());
                if (newTable != null)
                {
                    event.getLevel().setBlockAndUpdate(event.getPos(), newTable.defaultBlockState());
                    event.getLevel().playSound(event.getEntity(), event.getPos(), SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 0.5F);
                }
                else
                {
                    if (event.getLevel().isClientSide)
                    {
                        if (ModList.get().isLoaded("everycomp"))
                        {
                            event.getEntity().displayClientMessage(tableNotFound2, true);
                        }
                        else
                        {
                            event.getEntity().displayClientMessage(tableNotFound1, true);
                        }
                    }
                }
                event.setResult(Event.Result.DENY);
                event.setUseItem(Event.Result.DENY);
                event.setUseBlock(Event.Result.DENY);
            }
        }
    }
    private static final TagKey<Block> workbench = BlockTags.create(new ResourceLocation("forge", "workbench"));;
    private static final Component tableNotFound1 = Component.translatable("message.tetra_tables.tableNotFound1");
    private static final Component tableNotFound2 = Component.translatable("message.tetra_tables.tableNotFound2");

    private static Block tryGetTable(Block block)
    {
        String id = ForgeRegistries.BLOCKS.getKey(block).getPath();
        String wood = null;
        int namePos = id.indexOf("simple_table_");
        if (namePos >= 0)
        {
            wood = id.substring(namePos + 13);
        }
        else
        {
            namePos = id.indexOf("_crafting_table");
            if (namePos >= 0)
            {
                wood = id.substring(0, namePos);
            }
        }
        if (wood == null)
        {
            return null;
        }

        String newName = "tetra_table_" + wood;
        for (ResourceLocation key: ForgeRegistries.BLOCKS.getKeys())
        {
            if (key.toString().endsWith(newName))
            {
                return ForgeRegistries.BLOCKS.getValue(key);
            }
        }
        return null;
    }
}
