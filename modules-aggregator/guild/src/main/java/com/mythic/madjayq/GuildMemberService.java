package com.mythic.madjayq;

import com.mythic.madjayq.generated.Guild.GuildCharacter;


public interface GuildMemberService {
    GuildCharacter getGuildCharacter(String characterName);
    void setCharacterDkp(String characterName, int newDkp) throws Exception;
    void deleteCharacter(String characterName) throws Exception;
    void start() throws Exception;
}
