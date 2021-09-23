package com.mythic.madjayq;

import com.google.common.eventbus.EventBus;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.mythic.madjayq.commands.DiscordCommandService;
import com.mythic.madjayq.commands.GetCharacterDKPCommand;
import com.mythic.madjayq.commands.GetReservesCommand;
import com.mythic.madjayq.commands.RequestReservesCommand;
import com.mythic.madjayq.commands.SetCharacterDKPCommand;
import com.mythic.madjayq.commands.WipeCharacterCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MythicBot {
    public static Logger log = LoggerFactory.getLogger(MythicBot.class);

    private final DiscordService discordService;
    private final DiscordCommandService discordCommandService;
    private final GuildMemberService guildMemberService;
    private final EventBus eventBus;

    @Inject
    public MythicBot(DiscordService discordService,
                     DiscordCommandService discordCommandService,
                     GuildMemberService guildMemberService,
                     EventBus eventBus) {
        this.discordService = discordService;
        this.discordCommandService = discordCommandService;
        this.guildMemberService = guildMemberService;
        this.eventBus = eventBus;
    }

    public void start() throws Exception {
        discordService.start();
        guildMemberService.start();
        registerCommands();
    }

    private void registerCommands() {
        discordCommandService.registerCommand("?dkp", new GetCharacterDKPCommand(discordService, eventBus, guildMemberService));
        discordCommandService.registerCommand("?getReserves", new GetReservesCommand(discordService, eventBus, guildMemberService));
        discordCommandService.registerCommand("?reserve", new RequestReservesCommand(discordService, eventBus, guildMemberService));
        discordCommandService.registerCommand("?setDkp", new SetCharacterDKPCommand(discordService, eventBus, guildMemberService));
        discordCommandService.registerCommand("?wipe", new WipeCharacterCommand(discordService, eventBus, guildMemberService));
    }

    public static void main(String[] args) {
        try {
            //TODO: Change stage to PRODUCTION
            Injector injector = Guice.createInjector(Stage.DEVELOPMENT, MythicBotModuleProvider.modules());
            InjectorProvider.set(injector);
            MythicBot bot = injector.getInstance(MythicBot.class);
            log.info("Mythic bot coming online...");
            bot.start();
        }
        catch(Exception ex) {
            log.error("Bot encountered unrecoverable error: ", ex);
        }
    }
}
