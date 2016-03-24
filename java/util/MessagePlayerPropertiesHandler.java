package util;

import common.SimplyGenerators;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessagePlayerPropertiesHandler implements IMessageHandler<MessagePlayerProperties, IMessage> {

    @SuppressWarnings("static-access")
	@Override
    public IMessage onMessage(MessagePlayerProperties message, MessageContext ctx) {
        //Client側にIExtendedEntityPropertiesを渡す。
        ExtendedPlayerProperties.get(SimplyGenerators.Instance.commonProxy.getEntityPlayerInstance()).loadNBTData(message.data);
        //REPLYは送らないので、nullを返す。
        return null;
    }
}