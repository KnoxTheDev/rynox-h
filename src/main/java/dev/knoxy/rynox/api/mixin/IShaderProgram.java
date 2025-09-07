package dev.knoxy.rynox.api.mixin;

import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value={ShaderProgram.class})
public interface IShaderProgram {
    @Accessor(value="loadedUniforms")
    Map<String, GlUniform> getUniformsHook();
}
