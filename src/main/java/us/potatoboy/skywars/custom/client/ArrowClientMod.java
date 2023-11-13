package us.potatoboy.skywars.custom.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import us.potatoboy.skywars.custom.SWItems;
import us.potatoboy.skywars.custom.entity.ExplosiveArrowRenderer;
import us.potatoboy.skywars.custom.entity.MobArrowRenderer;

@Environment(EnvType.CLIENT)
public class ArrowClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModModelPredicateProvider.registerModModels();
        EntityRendererRegistry.register(SWItems.EXPLOSIVE_ARROW_ENTITY,
                ExplosiveArrowRenderer::new);
        EntityRendererRegistry.register(SWItems.MOB_ARROW_ENTITY,
                MobArrowRenderer::new);
    }
}
