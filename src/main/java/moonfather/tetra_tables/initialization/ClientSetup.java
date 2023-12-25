package moonfather.tetra_tables.initialization;

import net.minecraftforge.client.event.EntityRenderersEvent;
import se.mickelus.tetra.blocks.workbench.WorkbenchTESR;

public class ClientSetup
{
    public static void RegisterRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(Registration.TETRA_TABLE_BE.get(),	WorkbenchTESR::new);
    }
}
