package ariamis.net;

import ariamis.Ariamis;
import ariamis.items.ItemRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import org.lwjgl.opengl.GL11;

public class ClientHandler {
    private static final ResourceLocation sixthSenseTexture = new ResourceLocation(Ariamis.MODID+":textures/mod/sixth_sense.png");
    private static final ResourceLocation sixthSenseOverlayTexture = new ResourceLocation(Ariamis.MODID+":textures/mod/sixth_sense_overlay.png");
    @SubscribeEvent
    public void onRenderLivingEvent(RenderLivingEvent.Post event){
        Minecraft mc = Minecraft.getMinecraft();
        if(mc.thePlayer.isPotionActive(ItemRegistry.sixthSense.id) && event.entity != mc.thePlayer
                && mc.thePlayer.getActivePotionEffect(ItemRegistry.sixthSense) != null
                && event.entity.getDistanceToEntity(mc.thePlayer) < 20*(1+mc.thePlayer.getActivePotionEffect(ItemRegistry.sixthSense).getAmplifier()*0.25f)){
            Tessellator tessellator = Tessellator.instance;
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_LIGHTING);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDisable(GL11.GL_DEPTH_TEST);

            GL11.glTranslated(event.x, event.y + event.entity.height * 0.6, event.z);

            // This counteracts the reverse rotation behaviour when in front f5 view.
            // Fun fact: this is a bug with vanilla too! Look at a snowball in front f5 view, for example.
            float yaw = mc.gameSettings.thirdPersonView == 2 ? RenderManager.instance.playerViewX : -RenderManager.instance.playerViewX;
            GL11.glRotatef(180 - RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(yaw, 1.0F, 0.0F, 0.0F);

            tessellator.startDrawingQuads();

            mc.renderEngine.bindTexture(sixthSenseTexture);

            tessellator.addVertexWithUV(-0.6, 0.6, 0, 0, 0);
            tessellator.addVertexWithUV(0.6, 0.6, 0, 1, 0);
            tessellator.addVertexWithUV(0.6, -0.6, 0, 1, 1);
            tessellator.addVertexWithUV(-0.6, -0.6, 0, 0, 1);

            tessellator.draw();

            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);

            GL11.glPopMatrix();
        }
    }

    public float alpha(int timer){
        if(timer<10)return 0.1F*timer;
        else if(timer>30)return 1F-0.1F*(30-timer);
        else return 1F;
    }



    @SubscribeEvent
    public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Post event) {
        if (event.type == RenderGameOverlayEvent.ElementType.HELMET
                && Minecraft.getMinecraft().thePlayer.isPotionActive(ItemRegistry.sixthSense)) {

            GL11.glPushMatrix();

            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            Minecraft.getMinecraft().renderEngine.bindTexture(sixthSenseOverlayTexture);
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(0.0D, (double) event.resolution.getScaledHeight(), -90.0D, 0.0D, 1.0D);
            tessellator.addVertexWithUV((double) event.resolution.getScaledWidth(), (double) event.resolution.getScaledHeight(), -90.0D, 1.0D, 1.0D);
            tessellator.addVertexWithUV((double) event.resolution.getScaledWidth(), 0.0D, -90.0D, 1.0D, 0.0D);
            tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
            tessellator.draw();
            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            GL11.glPopMatrix();
        }
    }
}
