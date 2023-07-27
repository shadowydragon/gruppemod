package net.shadowydragon.gruppemod.item.custom;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class MetalDetectorItem extends Item {
    public MetalDetectorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {

        if (!pContext.getLevel().isClientSide)
        {
            BlockPos posClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();

            for (int i = 0; i < posClicked.getY() + 64; i++) {

                BlockState state = pContext.getLevel().getBlockState(posClicked.below(i));

                if (isValuableBlock(state))
                {
                    outputCoordinatesBlock(posClicked.below(i), player, state.getBlock());
                    return InteractionResult.SUCCESS;
                }
            }

            player.sendSystemMessage(Component.literal("No Valuable ore found"));
            pContext.getItemInHand().hurtAndBreak(1, player,
                    player1 -> player1.broadcastBreakEvent(player1.getUsedItemHand()));
            
        }
        

        return InteractionResult.SUCCESS;
    }

    private void outputCoordinatesBlock(BlockPos pos, Player player, Block block) {
        player.sendSystemMessage(Component.literal("Found: " + I18n.get(block.getDescriptionId()) + " at: \nX: " +
                pos.getX() + "\nY: " + pos.getY() + "\nZ: " + pos.getZ()));
    }

    private boolean isValuableBlock(BlockState state) {
        return state.is(Blocks.IRON_ORE) || state.is(Blocks.DIAMOND_ORE);
    }
}
