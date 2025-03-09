package xin.bbtt.plus.addon;

import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.GithubRepo;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;
import xin.bbtt.plus.addon.modules.AutoQueue;
import xin.bbtt.plus.addon.modules.PlayerNotifier;

public class XinPlus extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("xin");
    @Override
    public void onInitialize() {
        LOG.info("Initializing XinPlus");

        // Modules
        Modules.get().add(new PlayerNotifier());
        Modules.get().add(new AutoQueue());
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "xin.bbtt.plus.addon";
    }

    @Override
    public GithubRepo getRepo() {
        return new GithubRepo("huangdihd", "xinplus");
    }

    @Override
    public String getWebsite() {
        return "https://github.com/huangdihd/XinPlus";
    }
}
