package moonfather.tetra_tables;

import net.minecraft.advancements.Advancement;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import se.mickelus.tetra.TetraToolActions;

import java.util.HashMap;
import java.util.Map;

public class HammerEvent
{
    private static final ResourceLocation WORKBENCH_ADVANCEMENT = new ResourceLocation("tetra", "upgrades/workbench");
        
        
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event)
    {
        BlockState state = event. getLevel().getBlockState(event.getPos());
        if (state.is(workbench) && ! event.getEntity().isCrouching())
        {
            if (! state.is(Blocks.CRAFTING_TABLE))  // leave that one alone
            {
                if (event.getHand().equals(InteractionHand.MAIN_HAND) && event.getEntity().getMainHandItem().canPerformAction(TetraToolActions.hammer))
                {
                    Block newTable = tryGetTable(state.getBlock());
                    if (newTable != null)
                    {
                        event.getLevel().setBlockAndUpdate(event.getPos(), newTable.defaultBlockState());
                        event.getLevel().playSound(event.getEntity(), event.getPos(), SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 0.5F);
                        if (event.getEntity() instanceof ServerPlayer serverPlayer)
                        {
                            Advancement a = serverPlayer.getServer().getAdvancements().getAdvancement(WORKBENCH_ADVANCEMENT);
                            serverPlayer.getAdvancements().award(a, "hammer");
                        }
                    }
                    else
                    {
                        if (event.getLevel().isClientSide)
                        {
                            if (ModList.get().isLoaded("workshop_for_handsome_adventurer"))
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
    }
    private static final TagKey<Block> workbench = BlockTags.create(new ResourceLocation("forge", "workbench"));;
    private static final Component tableNotFound1 = Component.translatable("message.tetra_tables.tableNotFound1");
    private static final Component tableNotFound2 = Component.translatable("message.tetra_tables.tableNotFound2");

    private static final Map<String, String[]> modPrefixes = new HashMap<>();
    private static Block tryGetTable(Block block)
    {
        ResourceLocation fullId = ForgeRegistries.BLOCKS.getKey(block);
        String id = fullId.getPath();
        // first take care of EveryCompat prefixes
        int slashPosition = id.lastIndexOf('/');
        id = slashPosition < 0 ? id : id.substring(slashPosition + 1); // EveryCompat prefixes done
        // then take care of vct prefixes  and mct prefixes
        if (modPrefixes.isEmpty())
        {
            modPrefixes.put("vct", new String[] {"bop", "eco", "sg", "rue", "quark", "hexerei", "ge", "ft", "bm", "aether", "ap"});
            modPrefixes.put("mctb", new String[] {"bop", "ad", "ru"});
        }
        if (fullId.getNamespace().equals("vct") || fullId.getNamespace().equals("mctb"))
        {
            for (String prefix : modPrefixes.get(fullId.getNamespace()))
            {
                if (id.startsWith(prefix + "_"))
                {
                    id = id.substring(prefix.length() + 1);
                    break;
                }
            }
        } // VCT prefixes done. we'll recognize wood below.  MCT covered too.
        String wood = null;
        int namePos = -1;
        if ((namePos = id.indexOf("simple_table_")) >= 0)
        {
            wood = id.substring(namePos + 13);
        }
        else if ((namePos = id.indexOf("_crafting_table")) >= 0)
        {
            wood = id.substring(0, namePos);
        }
        else if (id.startsWith("crafting_table_"))
        {
            wood = id.substring(15);
        }
        if (wood == null)
        {
            return null;
        }
        ResourceLocation rl = new ResourceLocation(Constants.MODID, "tetra_table_" + wood);
        if (ForgeRegistries.BLOCKS.containsKey(rl))
        {
            return ForgeRegistries.BLOCKS.getValue(rl);
        }
        return null;
    }
}
