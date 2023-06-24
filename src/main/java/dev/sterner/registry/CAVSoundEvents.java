package dev.sterner.registry;

import dev.sterner.CoatsAndValour;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

public interface CAVSoundEvents {

    SoundEvent BLUNDERBUSS_CLOSE = register("blunderbuss_close");
    SoundEvent BLUNDERBUSS_DISTANT = register("blunderbuss_distant");
    SoundEvent BULLET_CRACK = register("bullet_crack");
    SoundEvent CANON_CLOSE = register("canon_close");
    SoundEvent CANON_DISTANT = register("canon_distant");

    SoundEvent COCKED = register("cocked");
    SoundEvent MUSKET_CLOSE = register("musket_close");
    SoundEvent MUSKET_DISTANT = register("musket_distant");
    SoundEvent PISTOL_PRIMED = register("pistol_primed");
    SoundEvent PISTOL_CLOSE = register("pistol_close");
    SoundEvent PISTOL_DISTANT = register("pistol_distant");
    SoundEvent RAMROD = register("ramrod");

    static SoundEvent register(String id) {
        SoundEvent event = SoundEvent.of(CoatsAndValour.id(id));
        Registry.register(Registries.SOUND_EVENT, id, event);
        return event;
    }

    static void init() {

    }
}
