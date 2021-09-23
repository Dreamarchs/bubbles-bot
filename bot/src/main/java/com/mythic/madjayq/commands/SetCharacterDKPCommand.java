package com.mythic.madjayq.commands;

import com.google.common.eventbus.EventBus;
import com.mythic.madjayq.DiscordService;
import com.mythic.madjayq.GuildMemberService;
import com.mythic.madjayq.generated.Guild;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;

public class SetCharacterDKPCommand extends DiscordCommand {
    private final GuildMemberService guildMemberService;

    public SetCharacterDKPCommand(DiscordService discordService, EventBus eventBus, GuildMemberService guildMemberService) {
        super(discordService, eventBus);
        this.eventBus.register(this);
        this.guildMemberService = guildMemberService;
    }

    @Override
    void usage(User user) throws Exception {
        discordService.sendMessage(user.getIdLong(), "Invalid usage");
    }

    @Override
    void execute(User user, TextChannel channel, String[] arguments) throws Exception{
        if(arguments.length != 3) {
            usage(user);
            return;
        }
        String name = arguments[1];
        int newDkp = Integer.parseInt(arguments[2]);
        guildMemberService.setCharacterDkp(name, newDkp);
        discordService.sendMessageToChannel(channel, buildDkpMessageEmbed(guildMemberService.getGuildCharacter(name)));
    }


    private MessageEmbed buildDkpMessageEmbed(Guild.GuildCharacter character) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("DKP Summary for: " + character.getGuildie(), null);
        eb.setColor(Color.magenta);
        eb.setDescription("Your total available DKP is: " + character.getDkp());
        eb.setThumbnail("https://wow.zamimg.com/images/logos/share-icon.png");
        return eb.build();
    }
}
