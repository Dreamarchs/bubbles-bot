package com.mythic.madjayq.commands;

import com.google.common.eventbus.EventBus;
import com.mythic.madjayq.DiscordService;
import com.mythic.madjayq.GuildMemberService;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class WipeCharacterCommand extends DiscordCommand {

    private final GuildMemberService guildMemberService;

    public WipeCharacterCommand(DiscordService discordService, EventBus eventBus, GuildMemberService guildMemberService) {
        super(discordService, eventBus);
        this.eventBus.register(this);
        this.guildMemberService = guildMemberService;
    }

    @Override
    void usage(User sender) throws Exception {

    }

    @Override
    void execute(User sender, TextChannel channel, String[] arguments) throws Exception {
        if(arguments.length != 2) {
            return;
        }
        String name = arguments[1];
        guildMemberService.deleteCharacter(name);
        discordService.sendMessageToChannel(channel, "Wiped character info for: " + name);
    }
}
