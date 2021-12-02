package com.jaquadro.minecraft.storagedrawers.network;

import com.jaquadro.minecraft.storagedrawers.StorageDrawers;
import com.jaquadro.minecraft.storagedrawers.block.tile.TileEntityDrawers;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

public class CountUpdateMessage implements IMessage
{
    private int x;
    private int y;
    private int z;
    private int slot;
    private int count;

    public CountUpdateMessage () { }

    public CountUpdateMessage (int x, int y, int z, int slot, int count) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.slot = slot;
        this.count = count;
    }

    @Override
    public void fromBytes (ByteBuf buf) {
        x = buf.readInt();
        y = buf.readShort();
        z = buf.readInt();
        slot = buf.readByte();
        count = buf.readInt();
    }

    @Override
    public void toBytes (ByteBuf buf) {
        buf.writeInt(x);
        buf.writeShort(y);
        buf.writeInt(z);
        buf.writeByte(slot);
        buf.writeInt(count);
    }

    @SideOnly(Side.CLIENT)
    public static class Handler implements IMessageHandler<CountUpdateMessage, IMessage>
    {
        @Override
        public IMessage onMessage (CountUpdateMessage message, MessageContext ctx) {
            if (ctx.side == Side.CLIENT) {
                World world = Minecraft.getMinecraft().theWorld;
                TileEntity tileEntity = world.getTileEntity(message.x, message.y, message.z);
                if (tileEntity instanceof TileEntityDrawers) {
                    ((TileEntityDrawers) tileEntity).clientUpdateCount(message.slot, message.count);
                }
            }

            return null;
        }
    }

    public static class HandlerStub implements IMessageHandler<CountUpdateMessage, IMessage>
    {
        @Override
        public IMessage onMessage (CountUpdateMessage message, MessageContext ctx) {
            FMLLog.log(StorageDrawers.MOD_ID, Level.WARN, "CountUpdateMessage stub handler called.");
            return null;
        }
    }
}
