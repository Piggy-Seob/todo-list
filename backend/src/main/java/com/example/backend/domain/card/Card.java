package com.example.backend.domain.card;

import java.time.LocalDateTime;

public class Card {
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

    public Card(String title, String writer, String content, Long position, Long memberId, CardType cardType) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.position = position;
        this.memberId = memberId;
        this.cardType = cardType;
    }

    public Card(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Card(long id, String writer, Long position, String title, String content, String cardType, LocalDateTime createdAt, LocalDateTime lastModifiedAt, boolean visible, long memberId) {
        this.id = id;
        this.writer = writer;
        this.position = position;
        this.title = title;
        this.content = content;
        this.cardType = CardType.valueOf(cardType);
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.visible = true;
        this.memberId = memberId;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public boolean isVisible() {
        return visible;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public CardType getCardType() {
        return cardType;
    }

    public String getWriter() {
        return writer;
    }

    public Long getPosition() {
        return position;
    }

    public Long getMemberId() {
        return memberId;
    }
}

