package com.yoti.assignment.sdkbackendtest.configuration;

import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableMongoAuditing
@EnableReactiveMongoRepositories
public class MongoConfiguration {
}


