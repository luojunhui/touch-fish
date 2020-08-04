package cn.luojunhui.touchfish.config;

import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/***
 * ConfigServiceImpl.class
 * @author junhui
 */
@State(name = "config", storages = @Storage("bookSettings.xml"))
public class ConfigServiceImpl implements ConfigService {
    private Config config;

    public ConfigServiceImpl() {
        this.config = new Config();
    }

    @Override
    public void setState(@NotNull Config config) {
        this.config = config;
    }

    @Nullable
    @Override
    public Config getState() {
        return this.config;
    }

    @Override
    public void loadState(@NotNull Config config) {
        XmlSerializerUtil.copyBean(config, this.config);
    }

    @Override
    public void noStateLoaded() {

    }
}
