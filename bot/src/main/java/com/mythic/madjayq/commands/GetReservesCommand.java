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

public class GetReservesCommand extends DiscordCommand {

    private final GuildMemberService guildMemberService;

    public GetReservesCommand(DiscordService discordService, EventBus eventBus, GuildMemberService guildMemberService) {
        super(discordService, eventBus);
        this.guildMemberService = guildMemberService;
    }
    @Override
    void usage(User sender) throws Exception {
        discordService.sendMessage(sender.getIdLong(), "Invalid usage");
    }

    @Override
    void execute(User sender, TextChannel channel, String[] arguments) throws Exception {
        if(arguments.length != 2) {
            usage(sender);
            return;
        }
        String name = arguments[1];
        Guild.GuildCharacter character = guildMemberService.getGuildCharacter(name);
        discordService.sendMessageToChannel(channel, buildMessageEmbed(character));
    }

    private MessageEmbed buildMessageEmbed(Guild.GuildCharacter character) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Reserves Summary for: " + character.getGuildie(), null);
        eb.setDescription(character.getGuildie() + " has " + character.getReservesList().size() + " reserves!");
        eb.setColor(Color.blue);
        eb.setThumbnail("https://wow.zamimg.com/images/logos/share-icon.png");
        for(String reserve : character.getReservesList()) {
            MessageEmbed.Field field = new MessageEmbed.Field("TODO: Resolve name","[" + reserve + "](http://example.com)", false);
            eb.addField(field);
        }
        return eb.build();
    }
}
