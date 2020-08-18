package com.arzio.pixelexprates;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import com.arzio.pixelexprates.RatesCommand.ReloadSubcommand;
import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

@Plugin(id = PixelEXPRates.ID, name = PixelEXPRates.NAME, version = PixelEXPRates.VERSION, dependencies = {@Dependency(id = "pixelmon")})
public class PixelEXPRates
{
    public static final String ID = "pixelexprates";
    public static final String NAME = "Pixelmon EXP Rates";
    public static final String VERSION = "1.0.1";

    @Inject
    private Injector injector;

    /** Default shared config loader for this plugin*/
    @Inject
    @DefaultConfig(sharedRoot = true)
    private ConfigurationLoader<CommentedConfigurationNode> configLoader;
    
    /** Default and future shared config path */
    @Inject
    @DefaultConfig(sharedRoot = true)
    private Path configPath;

    // The config root node, cached for performance
    private CommentedConfigurationNode rootNode;

    // Config-related objects
    private List<Rate> rates = new ArrayList<>();

    @Listener
    public void onInit(GameInitializationEvent event) {
        try {
            this.reloadConfig();
        } catch (Exception e) {
            throw new RuntimeException("Falha ao carregar config", e);
        }
        
        CommandSpec subReload = CommandSpec.builder()
            .executor(injector.getInstance(ReloadSubcommand.class))
        	.permission("pixelexprates.reload")
        	.description(Text.of("Recarregar configurações"))
        	.build();
        
        CommandSpec cmdRates = CommandSpec.builder()
        	.executor(injector.getInstance(RatesCommand.class))
	        .description(Text.of("Comando principal"))
	        // "reload" is a child of this command
	        .child(subReload, "reload")
	        .build();
        
        // Registers the command and its children
        Sponge.getCommandManager().register(this, cmdRates, "rates");

        // Registers the listeners of the applier
        Pixelmon.EVENT_BUS.register(injector.getInstance(EXPApplier.class));
    }

    public void reloadConfig() throws IOException, ObjectMappingException {
    	
    	// Saves the default config file without overwriting and only copies if absent
    	Sponge.getAssetManager().getAsset(this, "default.conf").get().copyToFile(this.configPath, false, true);
    	
        this.rootNode = configLoader.load();

        {
            this.rates.clear();
            this.rates.addAll(rootNode.getNode("rates").getList(TypeToken.of(Rate.class)));
        }
    }

    public Optional<Rate> getCurrentRate(Pokemon pokemon) {
        return this.rates.stream().filter(s -> s.isInStage(pokemon)).findFirst();
    }

    public List<Rate> getRates() {
        return Collections.unmodifiableList(this.rates);
    }
}
