package com.mythic.madjayq.commands;

public interface DiscordCommandService {
    void registerCommand(String prefixPattern, DiscordCommand command);
}
