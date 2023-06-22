package dev.sterner.common.item;

import com.mojang.datafixers.util.Pair;
import dev.sterner.common.util.GunProperties;
import dev.sterner.common.util.ProjectileProperties;
import dev.sterner.common.util.RecoilHandler;
import mod.azure.azurelib.animatable.GeoItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public abstract class CAVGunItem extends Item implements GeoItem {
    private final RecoilHandler handler;
    private final GunProperties gunProperties;
    private final String AMMO = "Ammo";
    private final String LOADING = "Loading";
    private final String GUN = "Gun";

    public CAVGunItem(Settings settings, GunProperties gunProperties) {
        super(settings.maxCount(1));
        this.handler = new RecoilHandler();
        this.gunProperties = gunProperties;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!canFireGun(user)) {
            return TypedActionResult.fail(stack);
        }

        user.setCurrentHand(hand);
        if (user.isSneaking() && getLoadedAmmo(stack) < gunProperties.getMaxAmmo()) {
            modifyGun(stack, true);
            user.setCurrentHand(hand);
            return TypedActionResult.consume(stack);

        } else if (!isLoading(stack) && getLoadedAmmo(stack) > 0) {
            ProjectileProperties projectileProperties = new ProjectileProperties.Builder().damage(2).recoilPower(10).sound(SoundEvents.ENTITY_GENERIC_EXPLODE).damageSource(world.getDamageSources().generic()).material(ProjectileProperties.Material.IRON).build();
            shoot(stack, user, projectileProperties);
            stack.damage(1, user, playerEntity -> playerEntity.sendToolBreakStatus(hand));
            return TypedActionResult.consume(stack);
        }

        return super.use(world, user, hand);
    }

    private boolean isLoading(ItemStack stack) {
        Pair<Integer, Boolean> pair = getGunProps(stack);
        return pair.getSecond();
    }

    public String getShootAnimation() {
        return null;
    }

    public boolean isMuzzling() {
        return false;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        return isLoading(stack) ? loadGun(stack, user) : stack;
    }

    private ItemStack loadGun(ItemStack stack, LivingEntity user) {
        //TODO consume ammo inventory
        increaseAmmo(stack);
        user.getWorld().playSound(null, user.getBlockPos(), gunProperties.getReloadSound(), SoundCategory.PLAYERS);

        Pair<Integer, Boolean> pair = getGunProps(stack);
        modifyGun(stack, pair.getFirst(), false);
        if (user instanceof PlayerEntity) {
            ((PlayerEntity) user).getItemCooldownManager().set(this, 20);
        }

        return stack;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        modifyGun(stack, false);
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return gunProperties.getReloadTicks();
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return isLoading(stack) ? UseAction.BOW : UseAction.NONE;
    }

    private boolean shoot(ItemStack itemStack, PlayerEntity player, ProjectileProperties projectileProperties) {
        World world = player.getWorld();

        if (!world.isClient() && getShootAnimation() != null) {
            triggerAnim(player, GeoItem.getOrAssignId(itemStack, (ServerWorld) world), "shoot_controller", getShootAnimation());
        }

        decreaseAmmo(itemStack);
        handler.recoil(player, projectileProperties.getRecoilPower());
        world.playSound(null, player.getBlockPos(), projectileProperties.getSound(), SoundCategory.PLAYERS);


        Vec3d vec3d = player.getCameraPosVec(1);
        Vec3d vec3d2 = player.getRotationVec(1);
        Vec3d vec3d3 = vec3d.add(vec3d2.x * gunProperties.getRange(), vec3d2.y * gunProperties.getRange(), vec3d2.z * gunProperties.getRange());


        for (int i = 0; i < 5; i++) {
            double step = 1.0;
            Vec3d pos = vec3d.add(vec3d2.x * step * i, vec3d2.y * step * i, vec3d2.z * step * i);
            world.addImportantParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, pos.getX(), pos.getY(), pos.getZ(), 0.0, 0.02, 0.0);
        }

        double distance = Math.pow(gunProperties.getRange(), 2);

        EntityHitResult hitt = ProjectileUtil.getEntityCollision(player.getWorld(), player, vec3d, vec3d3,
                player.getBoundingBox().stretch(vec3d2.multiply(distance)).expand(1.0D, 1.0D, 1.0D),
                (target) -> !target.isSpectator() && player.canSee(target));


        HitResult blockHit = world.raycast(new RaycastContext(vec3d, vec3d3, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, player));

        if (hitt != null && hitt.getEntity() != null && (blockHit.squaredDistanceTo(player) > hitt.getEntity().squaredDistanceTo(player))) {
            hitt.getEntity().damage(projectileProperties.getDamageSource(), projectileProperties.getProjectileDamage());
            return true;
        }

        return false;
    }

    private boolean canFireGun(PlayerEntity player) {
        return !player.isSubmergedInWater();
    }

    private int getLoadedAmmo(ItemStack stack) {
        NbtCompound gun = stack.getOrCreateNbt().getCompound(GUN);
        return gun.getInt(AMMO);
    }

    public void increaseAmmo(ItemStack stack) {
        Pair<Integer, Boolean> pair = getGunProps(stack);
        modifyGun(stack, Math.min(pair.getFirst() + 1, gunProperties.getMaxAmmo()), pair.getSecond());
    }

    public void decreaseAmmo(ItemStack stack) {
        Pair<Integer, Boolean> pair = getGunProps(stack);
        modifyGun(stack, Math.max(pair.getFirst() - 1, 0), pair.getSecond());
    }

    public ItemStack modifyGun(ItemStack stack, int ammo) {
        Pair<Integer, Boolean> pair = getGunProps(stack);
        return modifyGun(stack, ammo, pair.getSecond());
    }

    public ItemStack modifyGun(ItemStack stack, boolean loading) {
        Pair<Integer, Boolean> pair = getGunProps(stack);
        return modifyGun(stack, pair.getFirst(), loading);
    }


    public ItemStack modifyGun(ItemStack stack, int ammo, boolean loading) {
        NbtCompound gun = new NbtCompound();
        gun.putInt(AMMO, ammo);
        gun.putBoolean(LOADING, loading);
        stack.getOrCreateNbt().put(GUN, gun);
        return stack;
    }

    public Pair<Integer, Boolean> getGunProps(ItemStack stack) {
        NbtCompound nbtCompound = stack.getOrCreateNbt().getCompound(GUN);
        return Pair.of(nbtCompound.getInt(AMMO), nbtCompound.getBoolean(LOADING));
    }
}
