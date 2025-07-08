package xin.bbtt.plus.addon.modules;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.events.game.ReceiveMessageEvent;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import xin.bbtt.plus.addon.XinPlus;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AutoQueue extends Module {

    private final JsonObject questions = JsonParser.parseString(new BufferedReader(new InputStreamReader(Objects.requireNonNull(AutoQueue.class.getClassLoader().getResourceAsStream("questions.json")), StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"))).getAsJsonObject();

    public AutoQueue() {
        super(XinPlus.CATEGORY, "auto-queue", "Answering questions automatically when you are queueing.");
    }

    @EventHandler
    private void onReceiveMessage(ReceiveMessageEvent event) {
        if (MeteorClient.mc.getNetworkHandler() == null) return;

        String message = event.getMessage().getString();
        if (!message.contains("丨")) return;

        String[] parts = message.split("丨");
        if (parts.length != 2) return;

        String question = parts[0].replaceAll("<[^>]*>", "").trim();
        String options = parts[1].trim();

        if (!questions.has(question)) return;
        Pattern pattern = Pattern.compile(questions.get(question).getAsString());
        Matcher matcher = pattern.matcher(options);

        if (!matcher.find()) return;

        String answer = matcher.group(1);
        MeteorClient.mc.getNetworkHandler().sendChatMessage(answer);
    }
}
