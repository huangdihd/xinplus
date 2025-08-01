package xin.bbtt.plus.addon.modules;

import com.mojang.authlib.GameProfile;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.events.game.GameJoinedEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRemoveS2CPacket;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerNotifier extends Module {
    final Map<UUID, GameProfile> players = new HashMap<>();

    public PlayerNotifier() {
        super(Categories.Misc, "player-notifier", "Notifies you of player events.");
    }

    @EventHandler
    private void onAddPlayer(PacketEvent.Receive event) {
        if (event.packet instanceof PlayerListS2CPacket packet) {
            if (MeteorClient.mc.player == null) return;
            packet.getEntries().forEach(entry -> players.put(entry.profileId(), entry.profile()));
            if (packet.getEntries().size() != 1) return;
            PlayerListS2CPacket.Entry entry = packet.getEntries().getFirst();
            if (!packet.getActions().contains(PlayerListS2CPacket.Action.ADD_PLAYER))
                return;
            if (entry.profile().getId().equals(MeteorClient.mc.player.getGameProfile().getId()))
                return;
            info(Text.of("§8[§2+§8]§7" + entry.profile().getName()));
        }
    }
    @EventHandler
    private void onRemovePlayer(PacketEvent.Receive event) {
        if (event.packet instanceof PlayerRemoveS2CPacket(java.util.List<UUID> profileIds)) {
            if (MeteorClient.mc.player == null) return;
            profileIds.forEach(players::remove);
            if (profileIds.size() != 1) return;
            GameProfile profile = players.get(profileIds.getFirst());
            if (profile == null) return;
            if (profile.getId().equals(MeteorClient.mc.player.getGameProfile().getId()))
                return;
            info(Text.of("§8[§c-§8]§7" + profile.getName()));
        }
    }
    @EventHandler
    private void onGameJoin(GameJoinedEvent event) {
        players.clear();
    }
}
