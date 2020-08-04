package cn.luojunhui.touchfish.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import org.jetbrains.annotations.NotNull;

/**
 * ConfigService.class
 * @author junhui
 */
public interface ConfigService extends PersistentStateComponent<Config> {
    /**
     * 由 Intellij Platform 保证的单例模式
     * @return ConfigService
     */
    static ConfigService getInstance() {
        return ServiceManager.getService(ConfigService.class);
    }

    /**
     * 获取插件配置
     * @return {@link Config}
     */
    @NotNull
    @Override
    Config getState();

    /**
     * 修改插件配置
     * @param state 将被保存的新配置对象
     */
    void setState(@NotNull Config state);

}
