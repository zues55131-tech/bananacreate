package com.example.bananacreate.item;

import com.example.bananacreate.cyber.CyberNet;
import com.example.bananacreate.cyber.Cyberware;
import com.example.bananacreate.cyber.CyberwareData;
import com.example.bananacreate.registry.ModAttachments;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

/** Implante corporal: clique direito para instalar no seu proprio corpo (dolorido). */
public class ImplantItem extends Item {
    private final Cyberware type;

    public ImplantItem(Cyberware type, Properties properties) {
        super(properties);
        this.type = type;
    }

    public Cyberware type() {
        return type;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CyberwareData data = CyberwareData.of(player);
        boolean creative = player.getAbilities().instabuild;

        if (data.has(type)) {
            if (!level.isClientSide) {
                player.displayClientMessage(Component.translatable("message.bananacreate.already_installed"), true);
            }
            return InteractionResultHolder.fail(stack);
        }
        if (!creative && player.getHealth() <= 8.0f) {
            if (!level.isClientSide) {
                player.displayClientMessage(Component.translatable("message.bananacreate.too_weak"), true);
            }
            return InteractionResultHolder.fail(stack);
        }

        if (!level.isClientSide) {
            player.setData(ModAttachments.CYBERWARE, data.with(type));
            if (player instanceof ServerPlayer sp) CyberNet.syncToAll(sp);
            if (!creative) {
                player.hurt(level.damageSources().magic(), 6.0f);
                stack.shrink(1);
            }
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ANVIL_USE, SoundSource.PLAYERS, 0.8f, 1.6f);
            player.displayClientMessage(Component.translatable("message.bananacreate.installed",
                    Component.translatable("item.bananacreate." + type.key())), true);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.bananacreate." + type.key() + ".desc")
                .withStyle(net.minecraft.ChatFormatting.GRAY));
        tooltip.add(Component.translatable("item.bananacreate." + type.key() + ".key")
                .withStyle(net.minecraft.ChatFormatting.DARK_AQUA));
    }
}
