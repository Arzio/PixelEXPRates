package com.arzio.pixelexprates;

import com.google.inject.Inject;
import com.pixelmonmod.pixelmon.api.events.ExperienceGainEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.Optional;

public class EXPApplier {

    @Inject
    private PixelEXPRates plugin;

    @SubscribeEvent
    public void onEXPGain(ExperienceGainEvent event) {
        Optional<Rate> currentStage = plugin.getCurrentRate(event.pokemon.getPokemon());

        // Has current rate
        if (currentStage.isPresent()) {
            Rate rate = currentStage.get();

            // EXP multiplier is different than default
            if (rate.getExpMultiplier() != 1.0D) {
                // Applies the multiplier to the current gained experience
                double result = ((double) event.getExperience()) * rate.getExpMultiplier();
                event.setExperience((int) result);

                String oQueGanhou = rate.getExpMultiplier() > 1.0D ? "EXP extra" : "menos EXP";
                
                // Tells the rates
                Player player = (Player) event.pokemon.getPlayerOwner();
                player.sendMessage(
                        Text.builder("Seu pokémon ganhou "+oQueGanhou+". Clique aqui e veja as ")
                                .append(Text.builder("EXP rates.").style(TextStyles.ITALIC).build())
                                .color(TextColors.GRAY)
                                .onHover(TextActions.showText(Text.of("/rates")))
                                .onClick(TextActions.runCommand("/rates"))
                                .build()
                );
            }
        }
    }
}
