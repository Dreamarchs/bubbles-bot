package com.mythic.madjayq;

import com.google.inject.Inject;
import com.google.protobuf.Message;
import com.mythic.madjayq.generated.Guild.GuildCharacter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuildMemberServiceImpl implements GuildMemberService {

    private static final Logger log = LoggerFactory.getLogger(GuildMemberServiceImpl.class);

    private final GuildDAO guildDAO;
    private final StatusService statusService;
    @Inject
    public GuildMemberServiceImpl(GuildDAO guildDAO, StatusService statusService) {
        this.guildDAO = guildDAO;
        this.statusService = statusService;
        this.statusService.setServiceStatus(GuildMemberService.class, ServiceStatus.PENDING);
    }

    @Override
    public GuildCharacter getGuildCharacter(String name) {
        try {
            return guildDAO.getCharacter(name);
        }
        catch (Exception ex) {
            log.error("An exception occurred while attempting to access guild character data from DAO", ex);
            return null;
        }
    }

    @Override
    public void setCharacterDkp(String characterName, int newDkp) throws Exception {
        GuildCharacter guildCharacter = getGuildCharacter(characterName);
        if(guildCharacter == null) {
            guildCharacter = GuildCharacter.newBuilder().setGuildie(characterName)
                    .setDkp(newDkp).build();
        }
        else {
            guildCharacter = guildCharacter.toBuilder().setDkp(newDkp).build();
        }
        guildDAO.writeCharacter(characterName, guildCharacter);
    }

    @Override
    public void deleteCharacter(String characterName) throws Exception {

    }

    @Override
    public void start() throws Exception {
        guildDAO.load();
        statusService.setServiceStatus(GuildMemberService.class, ServiceStatus.OK);
    }
}
