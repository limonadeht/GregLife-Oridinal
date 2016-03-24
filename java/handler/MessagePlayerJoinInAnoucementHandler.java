package handler;

import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;
import util.MessagePlayerJoinInAnnouncement;
import util.MessagePlayerProperties;

public class MessagePlayerJoinInAnoucementHandler implements IMessageHandler<MessagePlayerJoinInAnnouncement, MessagePlayerProperties> {
    @Override
    public MessagePlayerProperties onMessage(MessagePlayerJoinInAnnouncement message, MessageContext ctx) {
        //UUIDの文字列を受け取る
        String uuidString = message.getUuid();
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        //取得したPlayerが同一UUIDを持つか判定
        if (player.getGameProfile().getId().toString().equals(uuidString)) {
            //クライアント側にデータを送る
            return new MessagePlayerProperties(player);
        }
        //UUIDが違っていた場合、同期処理を呼ばない
        return null;
    }
}