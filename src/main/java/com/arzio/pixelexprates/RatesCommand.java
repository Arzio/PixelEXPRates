package com.arzio.pixelexprates;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.google.inject.Inject;

public class RatesCommand implements CommandExecutor {

    @Inject
    private PixelEXPRates plugin;

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            src.sendMessage(Text.of("Somente players podem usar este comando"));
            return CommandResult.empty();
        }

        src.sendMessage(Text.builder("Ganho de EXP pelo nivel de seu Pokémon:").color(TextColors.GREEN).build());
        plugin.getRates().stream().forEach(s ->
                src.sendMessage(
                        Text.builder(" Nivel "+s.getMinLevel()+" a "+s.getMaxLevel()).color(TextColors.GRAY)
                                .append(Text.builder(": ").color(TextColors.DARK_GRAY).build())
                                .append(Text.builder(String.format("%.1f", s.getExpMultiplier())+"x EXP em batalhas").color(TextColors.GOLD).build())
                                .build()
                )
        );

        return CommandResult.success();
    }
    
    public static class ReloadSubcommand implements CommandExecutor {
    	
    	@Inject
    	private PixelEXPRates plugin;

		@Override
		public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
            try {
                plugin.reloadConfig();
            } catch (Exception e) {
            	throw new CommandException(Text.of("Falha ao recarregar config"), e);
            }
            src.sendMessage(Text.builder("Configurações recarregadas!").color(TextColors.GREEN).build());
            return CommandResult.success();
		}
    	
    }
}
