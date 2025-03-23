package icu.n501yhappy.catchplayers.Listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.entity.Player;

public class DisListener extends PacketAdapter {
    public DisListener() {
        super(null, ListenerPriority.NORMAL, PacketType.Play.Client.CLIENT_COMMAND);
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        int action = event.getPacket().getIntegers().read(0);

        // 检查动作是否为下骑乘
        if (action == 0) { // 0 对应于玩家下骑乘的动作
            Player player = event.getPlayer();
            event.setCancelled(true);
        }
    }
}
