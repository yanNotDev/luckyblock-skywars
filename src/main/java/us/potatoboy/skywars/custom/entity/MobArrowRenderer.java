package us.potatoboy.skywars.custom.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;
import us.potatoboy.skywars.SkyWars;

@Environment(EnvType.CLIENT)
public class MobArrowRenderer
        extends ProjectileEntityRenderer<MobArrowEntity> {
    public static final Identifier TEXTURE = SkyWars.identifier("textures/entity/projectiles/mob_arrow.png");

    public MobArrowRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(MobArrowEntity mobArrowEntity) {
        return TEXTURE;
    }
}
