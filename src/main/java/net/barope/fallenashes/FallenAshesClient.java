package net.barope.fallenashes;

import net.barope.fallenashes.abilities.Iron_Burn;
import net.barope.fallenashes.abilities.Steel_Burn;
import net.barope.fallenashes.util.ModTags;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.lwjgl.glfw.GLFW;

public class FallenAshesClient implements ClientModInitializer {
    private static final int STEEL_KEY_CODE = GLFW.GLFW_KEY_R;
    private static KeyBinding steelKey;
    private static final int IRON_KEY_CODE = GLFW.GLFW_KEY_G;
    private static KeyBinding ironKey;
    
    @Override
    public void onInitializeClient() {
        // Create a dummy KeyBinding so it shows up in controls menu
        steelKey = new KeyBinding(
                "key.fallenashes.steel",
                InputUtil.Type.KEYSYM,
                STEEL_KEY_CODE,
                "category.fallenashes.allomancy"
        );
        ironKey = new KeyBinding(
                "key.fallenashes.iron",
                InputUtil.Type.KEYSYM,
                IRON_KEY_CODE,
                "category.fallenashes.allomancy"
        );
        KeyBindingHelper.registerKeyBinding(steelKey);
        KeyBindingHelper.registerKeyBinding(ironKey);

        final int MAX_RANGE = 20;

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            // Directly poll the keyboard state for GLFW key
            long windowHandle = MinecraftClient.getInstance().getWindow().getHandle();

            boolean isBurningSteel = InputUtil.isKeyPressed(windowHandle, STEEL_KEY_CODE);
            if (isBurningSteel) {
                Steel_Burn.trigger(client.player);
            }
            boolean isBurningIron = InputUtil.isKeyPressed(windowHandle, IRON_KEY_CODE);
            if (isBurningIron) {
                Iron_Burn.trigger(client.player);
            }
        });
    }
}