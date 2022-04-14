package com.example.backend.repository.card.jdbc;


import com.example.backend.domain.card.Card;
import com.example.backend.domain.card.CardType;
import com.example.backend.repository.card.CardRepository;
import com.example.backend.repository.card.CardRepositoryHelper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static java.time.LocalDateTime.now;
import static java.util.Objects.requireNonNull;

@Repository
public class CardJdbcRepository implements CardRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final CardRepositoryHelper repositoryHelper;

    public CardJdbcRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, CardRepositoryHelper repositoryHelper) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.repositoryHelper = repositoryHelper;
    }

    @Override
    public Card save(Card card) {
        String query = "INSERT card (id, writer, position, title, content, card_type, created_at, last_modified_at, `visible`, member_id)" +
                "VALUES (:id, :writer, :position, :title, :content, :cardType, :createdAt, :lastModifiedAt, :visible, :memberId)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String, Object> params = repositoryHelper.getParamMap(card);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(params);
        namedParameterJdbcTemplate.update(query, mapSqlParameterSource, keyHolder);
        long key = requireNonNull(keyHolder.getKey()).longValue();
        return new CardBuilder()
                .id(key)
                .writer(card.getWriter())
                .position(card.getPosition())
                .title(card.getTitle())
                .content(card.getContent())
                .cardType(card.getCardType())
                .createdAt(now())
                .lastModifiedAt(now())
                .visible(card.isVisible())
                .memberId(card.getMemberId())
                .build();
    }

    @Override
    public List<Card> findAll() {
        String query = "SELECT id, " +
                "writer, " +
                "position, " +
                "title, " +
                "content, " +
                "card_type, " +
                "created_at, " +
                "last_modified_at, " +
                "visible, " +
                "member_id " +
                "FROM card WHERE visible = true";
        RowMapper<Card> mapper = CardRepositoryHelper.mapper;
        return namedParameterJdbcTemplate.query(query, mapper);
    }

    @Override
    public Optional<Card> findById(Long id) {
        String query = "SELECT id, writer, position, title, content, card_type, created_at, last_modified_at, visible, member_id " +
                "FROM card WHERE id = :id";
        Map<String, Object> params = Collections.singletonMap("id", id);
        RowMapper<Card> mapper = CardRepositoryHelper.mapper;
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(query, params, mapper));
    }

    @Override
    public Card update(Card card) {
        String query = "UPDATE card SET title=:title, content=:content, card_type=:cardType, position=:position, last_modified_at=:lastModifiedAt WHERE id=:id";
        SqlParameterSource sqlParameterSource = repositoryHelper.getUpdateParameterSource(card);
        namedParameterJdbcTemplate.update(query, sqlParameterSource);
        return card;
    }

    @Override
    public void delete(Long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
        String query = "UPDATE card SET visible=false WHERE id=:id";
        namedParameterJdbcTemplate.update(query, namedParameters);
    }

    private static class CardBuilder {
        private Long id;
        private String writer;
        private Long position;
        private String title;
        private String content;
        private CardType cardType;
        private LocalDateTime createdAt;
        private LocalDateTime lastModifiedAt;
        private boolean visible;
        private Long memberId;

        CardBuilder() {};

        CardBuilder id(Long id) {
            this.id = id;
            return this;
        }

        CardBuilder writer(String writer) {
            this.writer = writer;
            return this;
        }

        CardBuilder position(Long position) {
            this.position = position;
            return this;
        }

        CardBuilder title(String title) {
            this.title = title;
            return this;
        }

        CardBuilder content(String content) {
            this.content = content;
            return this;
        }

        CardBuilder cardType(CardType cardType) {
            this.cardType = cardType;
            return this;
        }

        CardBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        CardBuilder lastModifiedAt(LocalDateTime lastModifiedAt) {
            this.lastModifiedAt = lastModifiedAt;
            return this;
        }

        CardBuilder visible(boolean visible) {
            this.visible = visible;
            return this;
        }

        CardBuilder memberId(Long memberId) {
            this.memberId = memberId;
            return this;
        }

        Card build() {
            return new Card(id, writer, position, title, content, cardType, createdAt, lastModifiedAt, visible, memberId);
        }
    }
}
