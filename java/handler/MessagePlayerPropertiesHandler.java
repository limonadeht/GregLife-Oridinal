package handler;

import common.SimplyGenerators;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import util.ExtendedPlayerProperties;
import util.MessagePlayerProperties;

public class MessagePlayerPropertiesHandler implements IMessageHandler<MessagePlayerProperties, IMessage> {

    @Override
    public IMessage onMessage(MessagePlayerProperties message, MessageContext ctx) {
        //Client側にIExtendedEntityPropertiesを渡す。
        ExtendedPlayerProperties.get(SimplyGenerators.commonProxy.getEntityPlayerInstance()).loadNBTData(message.data);
        //REPLYは送らないので、nullを返す。
        return null;
    }
}
