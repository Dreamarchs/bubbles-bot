package com.mythic.madjayq.aws;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.BatchGetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.TableKeysAndAttributes;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.mythic.madjayq.GuildDAO;
import com.mythic.madjayq.generated.Guild;
import com.mythic.madjayq.utils.ProtoJsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DynamoDBGuildDAO implements GuildDAO {

    private static final Logger log = LoggerFactory.getLogger(DynamoDBGuildDAO.class);

    private final DynamoDB dynamoDB;
    private static final String TABLE_NAME = "mythic-dkp";

    public DynamoDBGuildDAO(AmazonDynamoDB client) {
        dynamoDB = new DynamoDB(client);
    }


    @Override
    public void load() throws Exception {
        log.info("Acquiring access to dao...");
        //This is just to test we have access to dao, if this fails we're hosed
        dynamoDB.getTable(TABLE_NAME);

    }

    @Override
    public Guild.GuildCharacter getCharacter(String name) throws Exception {
        return getCharacters(name).stream().findFirst().orElse(null);
    }

    @Override
    public Collection<Guild.GuildCharacter> getCharacters(String... names) throws Exception {
        TableKeysAndAttributes tka = new TableKeysAndAttributes(TABLE_NAME);
        for (String name : names) {
            tka.addHashOnlyPrimaryKey("guildie", name);
        }
        BatchGetItemOutcome outcome = dynamoDB.batchGetItem(tka);
        return outcome.getTableItems().get(TABLE_NAME).stream().map(item -> {
            try {
                return ProtoJsonUtil.fromJson(item.toJSON(), Guild.GuildCharacter.class);
            }
            catch (IOException e) {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public void writeCharacter(String name, Guild.GuildCharacter character) throws Exception {
        Item item = Item.fromJSON(ProtoJsonUtil.toJson(character));
        dynamoDB.getTable(TABLE_NAME).putItem(item);
    }

    @Override
    public void deleteCharacter(String name) throws Exception {
        dynamoDB.getTable(TABLE_NAME).deleteItem("guildie", name);
    }
}
