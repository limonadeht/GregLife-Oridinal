package util;

import common.item.tool.ItemSwordOfDemon;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class MessageMarks extends AbstractMessage{
String stoneName;

	public MessageMarks() {}

	public MessageMarks(String name) {
		this.stoneName = name;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		ByteBufUtils.writeUTF8String(buffer, this.stoneName);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		this.stoneName = ByteBufUtils.readUTF8String(buffer);
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		if (!(player.getHeldItem().getItem() instanceof ItemSwordOfDemon)) {
			return;
		}

		if(player.getHeldItem().stackSize == 1)
			// Destroy the stack if it's the last item
			player.destroyCurrentEquippedItem();
		else
			player.getHeldItem().stackSize--;
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		if (!(player.getHeldItem().getItem() instanceof ItemSwordOfDemon))
			return;

		if (player.getHeldItem().stackSize > 1 && player.inventory.getFirstEmptyStack() == -1) {
			return;
		}

		// Prepare new stone
		ItemStack heldStack = player.getHeldItem();
		ItemSwordOfDemon stone = (ItemSwordOfDemon) heldStack.getItem();
		ItemStack newStack = new ItemStack(stone);
		stone.mark(stoneName, player, heldStack);

		// Consume Blank Stone and add the new Recall Stone
		if(heldStack.stackSize == 1)
			// Destroy the stack if it's the last item
			player.destroyCurrentEquippedItem();
		else
			heldStack.stackSize--;

		player.inventory.addItemStackToInventory(newStack);
	}
}
