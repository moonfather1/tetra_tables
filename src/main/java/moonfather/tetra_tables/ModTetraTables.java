package moonfather.tetra_tables;

import moonfather.tetra_tables.initialization.ClientSetup;
import moonfather.tetra_tables.initialization.CreativeTabEvent;
import moonfather.tetra_tables.initialization.DynamicRegistration;
import moonfather.tetra_tables.initialization.Registration;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MODID)
public class ModTetraTables
{
    public ModTetraTables()
    {
        Registration.init();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::RegisterRenderers));
        MinecraftForge.EVENT_BUS.addListener(HammerEvent::onRightClickBlock);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(CreativeTabEvent::buildContents);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(DynamicRegistration::registerBlocksForThirdPartyWood);
    }
}
