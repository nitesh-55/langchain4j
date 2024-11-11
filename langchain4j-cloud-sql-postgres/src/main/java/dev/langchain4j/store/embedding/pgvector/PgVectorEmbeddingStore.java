package dev.langchain4j.store.embedding.pgvector;

import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.data.segment.TextSegment;
import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;
import java.util.*;

import static dev.langchain4j.utils.HelperUtils.*;

public class PgVectorEmbeddingStore implements EmbeddingStore<TextSegment> {

    protected PgVectorEmbeddingStore(
        String host,
        Integer port,
        String user,
        String password,
        String database
    ) {
    createDataSource(host, port, user, password, database);
    }

    private static DataSource createDataSource(String host, Integer port, String user, String password, String database) {
    host = ensureNotBlank(host, "host");
    port = ensureGreaterThanZero(port, "port");
    user = ensureNotBlank(user, "user");
    password = ensureNotBlank(password, "password");
    database = ensureNotBlank(database, "database");

    PGSimpleDataSource source = new PGSimpleDataSource();
    source.setServerNames(new String[]{host});
    source.setPortNumbers(new int[]{port});
    source.setDatabaseName(database);
    source.setUser(user);
    source.setPassword(password);

    return source;
    }

    @Override
    public List<String> addAll(List<Embedding> embeddings, List<TextSegment> embedded) {
        List<String> ids= new ArrayList<>();
     //   to be implemented
        return ids;
    }
}