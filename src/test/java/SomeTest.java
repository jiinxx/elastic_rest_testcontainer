import fr.pilato.elasticsearch.containers.ElasticsearchResource;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SomeTest {
    @Rule
    public ElasticsearchResource elasticsearch = new ElasticsearchResource();

    @BeforeClass
    public static void test(){
        System.out.println("before test");
    }

    @Test
    public void someTestMethod() throws Exception {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "changeme"));

        RestClient client = RestClient.builder(elasticsearch.getHost())
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
                .build();
        Response response = client.performRequest("GET", "/");
        System.out.println(response.toString());
        assertThat(response.getStatusLine().getStatusCode(), is(200));
    }

}
