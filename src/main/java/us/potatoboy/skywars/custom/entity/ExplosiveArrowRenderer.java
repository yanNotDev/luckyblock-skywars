package us.potatoboy.skywars.custom.entity;

import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;
import net.fabricmc.api.EnvType;
import us.potatoboy.skywars.SkyWars;

@Environment(EnvType.CLIENT)
public class ExplosiveArrowRenderer
        extends ProjectileEntityRenderer<ExplosiveArrowEntity> {
    public static final Identifier TEXTURE = SkyWars.identifier("textures/entity/projectiles/explosive_arrow.png");

    public ExplosiveArrowRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(ExplosiveArrowEntity explosiveArrowEntity) {
        return TEXTURE;
    }
}
