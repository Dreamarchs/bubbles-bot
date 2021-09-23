package com.mythic.madjayq;

import com.mythic.madjayq.generated.Guild.GuildCharacter;

import java.util.Collection;

public interface GuildDAO {
    void load() throws Exception;
    GuildCharacter getCharacter(String name) throws Exception;
    Collection<GuildCharacter> getCharacters(String... names) throws Exception;

    void writeCharacter(String name, GuildCharacter character) throws Exception;
    void deleteCharacter(String name) throws Exception;
}
