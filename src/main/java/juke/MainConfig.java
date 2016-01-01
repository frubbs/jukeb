package juke;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

//@Configuration
//@ComponentScan(basePackages = "juke")
public class MainConfig {

    //@Bean
    public BasicDataSource dataSource() throws URISyntaxException {
        String DBURL = "postgres://lkluyrjftapmnr:RXV3pD55Qs7RKzTkGGZzz0Alqa@ec2-107-22-170-249.compute-1.amazonaws.com:5432/ddus4l7hg555dc";


        URI dbUri = new URI(DBURL);

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);

        return basicDataSource;
    }

}