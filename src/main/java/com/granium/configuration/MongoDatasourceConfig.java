package com.granium.configuration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by Sasha.Chepurnoi on 07.12.16.
 */
@Configuration
@EnableMongoRepositories(
        basePackages = "com.granium.domain.mongo")
public class MongoDatasourceConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "coursework";
    }

    @Override
    public Mongo mongo() throws Exception {
        Mongo mongo = new MongoClient("localhost",28100);
        return mongo;
    }
}
