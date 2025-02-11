package dev.langchain4j.store.embedding.cloudsql;

import static dev.langchain4j.internal.ValidationUtils.*;
import static dev.langchain4j.utils.HelperUtils.*;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.engine.PostgresEngine;
import dev.langchain4j.store.embedding.EmbeddingStore;
import java.util.*;

public class PostgresEmbeddingStore implements EmbeddingStore<TextSegment> {

  private final PostgresEngine engine;
  private final String tableName;
  private String schemaName;
  private String contentColumn;
  private String embeddingColumn;
  private String idColumn;
  private List<String> metadataColumns;

  public PostgresEmbeddingStore(
      PostgresEngine engine,
      String tableName,
      String schemaName,
      String contentColumn,
      String embeddingColumn,
      String idColumn,
      List<String> metadataColumns,
      String metadataJsonColumn) {
    this.engine = engine;
    this.tableName = tableName;
    this.schemaName = schemaName;
    this.contentColumn = contentColumn;
    this.embeddingColumn = embeddingColumn;
    this.idColumn = idColumn;
    this.metadataColumns = metadataColumns;
  }

  /**
   * Adds multiple embeddings to the store.
   *
   * @param embeddings a list of embeddings to be added to the store.
   * @param embedded a list of original contents that were embedded.
   */
  @Override
  public List<String> addAll(List<Embedding> embeddings, List<TextSegment> embedded) {
    List<String> ids = new ArrayList<>();
    //   to be implemented
    return ids;
  }

  /**
   * Adds multiple embeddings to the store.
   *
   * @param embeddings a list of embeddings to be added to the store.
   */
  @Override
  public List<String> addAll(List<Embedding> embeddings) {
    // to be implemented
    List<String> ids = new ArrayList<String>();
    return ids;
  }

  /**
   * Adds a given embedding to the store.
   *
   * @param embedding the embedding to be added to the store.
   */
  @Override
  public String add(Embedding embedding) {
    // to be implemented
    return "";
  }

  /**
   * Adds a given embedding to the store.
   *
   * @param embedding the embedding to be added to the store.
   * @param textSegment original content that was embedded.
   */
  @Override
  public String add(Embedding embedding, TextSegment textSegment) {
    // to be implemented
    return "";
  }

  /**
   * Adds a given embedding to the store.
   *
   * @param embedding The embedding to be added to the store.
   */
  @Override
  public void add(String id, Embedding embedding) {
    // to be implemented
  }
}
