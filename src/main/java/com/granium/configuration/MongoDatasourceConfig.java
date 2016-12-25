package com.granium.configuration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
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

    @Value("${spring.datasource.mongo.url:NULL}")
    private String mongourl;

    @Override
    public Mongo mongo() throws Exception {
        Mongo mongo = new MongoClient(new MongoClientURI(mongourl));
        return mongo;
    }
}
