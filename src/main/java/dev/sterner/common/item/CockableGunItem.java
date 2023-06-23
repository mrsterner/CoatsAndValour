package dev.sterner.common.item;

import dev.sterner.common.util.GunProperties;
import dev.sterner.common.util.ParticleUtils;
import dev.sterner.common.util.ProjectileProperties;
import dev.sterner.common.util.RecoilHandler;
import dev.sterner.registry.CAVParticleTypes;
import mod.azure.azurelib.animatable.GeoItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.function.ValueLists;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

public abstract class CockableGunItem extends Item implements GeoItem {
    private final RecoilHandler handler;
    private final GunProperties gunProperties;
    private final String CURRENT_PROJECTILE_PROPERTIES = "CurrentProperties";
    private final String COCKED = "Cocked";
    private final String GUN = "Gun";

    public CockableGunItem(Settings settings, GunProperties gunProperties) {
        super(settings.maxCount(1));
        this.handler = new RecoilHandler();
        this.gunProperties = gunProperties;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        user.setCurrentHand(hand);

        switch (getCockedStage(stack)) {
            case UNCOCKED -> halfCock(user, stack);
            case HALFCOCKED -> prime(user, stack);
            case PRE_PRIME -> prePrime(user, stack);
            case PRIME -> finishPrime(user, stack);
            case FINISH_PRIME -> ramrod(user, stack);
            case RAMROD -> cock(user, stack);
            case COCKED -> shoot(user, stack);
        }
        return TypedActionResult.consume(stack);
    }

    public TagKey<Item> getAmmoTag() {
        return null;
    }

    private void halfCock(PlayerEntity user, ItemStack stack) {
        if (user.isSneaking()) {
            modifyGun(stack, getCockedStage(stack).next());
        }
    }

    private void prePrime(PlayerEntity user, ItemStack stack) {
        ItemStack offHand = user.getOffHandStack();
        ItemStack newOffHan = offHand.split(1);
        if (offHand.isIn(getAmmoTag()) && newOffHan.getItem() instanceof AmmoItem ammoItem) {
            if (offHand.getCount() > 1) {
                if (!user.getInventory().insertStack(offHand)) {
                    user.dropItem(offHand, true);
                }
            }
            newOffHan.getOrCreateNbt().putInt("Exposed", 1);
            user.setStackInHand(Hand.MAIN_HAND, new ItemStack(newOffHan.getItem(), 1));

            increaseAmmo(stack, ammoItem.getProjectileProperties().getAmmoType());
        }
    }

    private void prime(PlayerEntity user, ItemStack stack) {

    }

    private void finishPrime(PlayerEntity user, ItemStack stack) {

    }

    private void ramrod(PlayerEntity user, ItemStack stack) {

    }

    private void cock(PlayerEntity user, ItemStack stack) {

    }

    public String getShootAnimation() {
        return null;
    }

    public boolean isMuzzling() {
        return false;
    }

    private boolean shoot(PlayerEntity player, ItemStack itemStack) {
        World world = player.getWorld();
        //Todo make ammo have this instead of hardcoded

        if (!world.isClient() && getShootAnimation() != null) {
            triggerAnim(player, GeoItem.getOrAssignId(itemStack, (ServerWorld) world), "shoot_controller", getShootAnimation());
        }
        GunInfo gunInfo = getGunProps(itemStack);
        ProjectileProperties projectileProperties = gunInfo.getAmmoItem();

        int shots = decreaseAmmo(itemStack, gunProperties.getShots());
        handler.recoil(player, projectileProperties.getRecoilPower());
        if (projectileProperties.getSound() != null) {
            world.playSound(null, player.getBlockPos(), projectileProperties.getSound(), SoundCategory.PLAYERS);
        }

        Vec3d vec3d = player.getCameraPosVec(1);
        Vec3d vec3d2 = player.getRotationVec(1);
        Vec3d vec3d3 = vec3d.add(vec3d2.x * gunProperties.getRange(), vec3d2.y * gunProperties.getRange(), vec3d2.z * gunProperties.getRange());

        ParticleUtils.generateParticleVector(world, player, (ParticleEffect) CAVParticleTypes.SMOKE, 1, 5, true);

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

    private List<AmmoInfo> getLoadedAmmoInfo(ItemStack stack) {
        NbtCompound gun = stack.getOrCreateNbt().getCompound(GUN);
        NbtList nbtList = gun.getList("AmmoInfoList", NbtElement.COMPOUND_TYPE);
        List<AmmoInfo> outList = new ArrayList<>();
        for (int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            ProjectileProperties.AmmoType type = ProjectileProperties.AmmoType.byId(nbtCompound.getInt("AmmoType"));
            int count = nbtCompound.getInt("Count");
            outList.add(new AmmoInfo(type, count));
        }

        return outList;
    }

    private GunInfo.Cock getCockedStage(ItemStack stack) {
        NbtCompound gun = stack.getOrCreateNbt().getCompound(GUN);
        return GunInfo.Cock.byId(gun.getInt(COCKED));
    }

    public void increaseAmmo(ItemStack stack, ProjectileProperties.AmmoType ammoType) {
        GunInfo info = getGunProps(stack);
        List<AmmoInfo> newList = info.getAmmoInfoList();

        for (AmmoInfo ammoInfo : newList) {
            if (ammoInfo.type == ammoType && getTotalAmmo(stack) < gunProperties.getMaxAmmo()) {
                ammoInfo.count = ammoInfo.count + 1;
                break;
            }
        }
        modifyGun(stack, newList);
    }

    public int decreaseAmmo(ItemStack stack, int amount) {
        GunInfo info = getGunProps(stack);
        List<AmmoInfo> newList = info.getAmmoInfoList();
        int i = 0;
        for (int j = 0; j < amount; j++) {
            if (!newList.isEmpty()) {
                newList.get(0).count--;
                i++;
                if (newList.get(0).count <= 0) {
                    newList.remove(0);
                }
            }
        }

        modifyGun(stack, newList);
        return i;
    }

    private int getTotalAmmo(ItemStack itemStack) {
        int i = 0;
        var info = getGunProps(itemStack).getAmmoInfoList();
        for (AmmoInfo ammoInfo : info) {
            i += ammoInfo.count;
        }
        return i;
    }

    public ItemStack modifyGun(ItemStack stack, GunInfo.Cock cock) {
        GunInfo info = getGunProps(stack);
        return modifyGun(stack, info.getAmmoItem(), info.getAmmoInfoList(), cock);
    }

    public ItemStack modifyGun(ItemStack stack, List<AmmoInfo> ammoInfoList) {
        GunInfo info = getGunProps(stack);
        return modifyGun(stack, info.getAmmoItem(), ammoInfoList, info.getCockStage());
    }

    public ItemStack modifyGun(ItemStack stack, ProjectileProperties ammoItem, List<AmmoInfo> ammoInfoList, GunInfo.Cock stage) {
        NbtCompound gun = ammoItem.writeNbt(ammoItem);
        NbtList nbtList = new NbtList();
        for (AmmoInfo ammoInfo : ammoInfoList) {
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.putInt("Count", ammoInfo.count);
            nbtCompound.putInt("AmmoType", ammoInfo.type.getId());
            nbtList.add(nbtCompound);
        }
        if (!nbtList.isEmpty()) {
            gun.put("AmmoInfoList", nbtList);
        }

        gun.putInt(COCKED, stage.getId());
        stack.getOrCreateNbt().put(GUN, gun);
        return stack;
    }

    public GunInfo getGunProps(ItemStack stack) {
        NbtCompound nbtCompound = stack.getOrCreateNbt().getCompound(GUN);
        ProjectileProperties properties = ProjectileProperties.readNbt(nbtCompound);
        return GunInfo.of(GunInfo.Cock.byId(nbtCompound.getInt(COCKED)), properties, getLoadedAmmoInfo(stack));
    }

    public static class AmmoInfo {
        private final ProjectileProperties.AmmoType type;
        private int count;

        public AmmoInfo(ProjectileProperties.AmmoType type, int count) {
            this.count = count;
            this.type = type;
        }
    }

    public static class GunInfo {
        private final List<AmmoInfo> ammoInfoList;
        private final Cock cockStage;
        private final ProjectileProperties ammoItem;

        public GunInfo(Cock cockStage, ProjectileProperties ammoItem, List<AmmoInfo> ammoInfoList) {
            this.cockStage = cockStage;
            this.ammoInfoList = ammoInfoList;
            this.ammoItem = ammoItem;
        }

        public List<AmmoInfo> getAmmoInfoList() {
            return ammoInfoList;
        }

        public Cock getCockStage() {
            return cockStage;
        }

        public ProjectileProperties getAmmoItem() {
            return ammoItem;
        }

        public static GunInfo of(Cock cockStage, ProjectileProperties ammoItem, List<AmmoInfo> ammoInfoList) {
            return new GunInfo(cockStage, ammoItem, ammoInfoList);
        }

        enum Cock implements StringIdentifiable {
            UNCOCKED(0, "UnCocked"),
            HALFCOCKED(1, "HalfCocked"),
            PRE_PRIME(2, "PrePrime"),
            PRIME(3, "Prime"),
            FINISH_PRIME(4, "FinishPrime"),
            RAMROD(5, "Ramrod"),
            COCKED(6, "Cocked");

            private final String name;
            private final int id;
            private static final IntFunction<Cock> BY_ID = ValueLists.createIdToValueFunction(Cock::getId, values(), ValueLists.OutOfBoundsHandling.ZERO);

            Cock(int id, String name) {
                this.name = name;
                this.id = id;
            }

            public int getId() {
                return this.id;
            }

            public static Cock byId(int id) {
                return BY_ID.apply(id);
            }

            @Override
            public String asString() {
                return this == UNCOCKED ? UNCOCKED.name : this == HALFCOCKED ? HALFCOCKED.name : this == PRE_PRIME ? PRE_PRIME.name : this == PRIME ? PRIME.name : this == FINISH_PRIME ? FINISH_PRIME.name : this == RAMROD ? RAMROD.name : COCKED.name;
            }

            public Cock next() {
                int nextIndex = (this.ordinal() + 1) % values().length;
                return values()[nextIndex];
            }
        }
    }
}