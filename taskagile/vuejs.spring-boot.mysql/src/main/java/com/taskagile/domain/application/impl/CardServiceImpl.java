package com.taskagile.domain.application.impl;

import java.util.List;

import com.taskagile.domain.application.CardService;
import com.taskagile.domain.application.commands.AddCardCommand;
import com.taskagile.domain.application.commands.ChangeCardPositionCommand;
import com.taskagile.domain.common.event.DomainEventPublisher;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.card.Card;
import com.taskagile.domain.model.card.CardRepository;
import com.taskagile.domain.model.card.events.CardAddedEvent;

import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

  private CardRepository cardRepository;
  private DomainEventPublisher domainEventPublisher;

  public CardServiceImpl(CardRepository cardRepository, DomainEventPublisher domainEventPublisher) {
    this.cardRepository = cardRepository;
    this.domainEventPublisher = domainEventPublisher;
  }

	@Override
	public List<Card> findByBoardId(BoardId boardId) {
		return cardRepository.findByBoardId(boardId);
	}

	@Override
	public Card addCard(AddCardCommand command) {
    Card card = Card.create(command.getCardListId(), command.getUserId(), command.getTitle(), command.getPosition());
    cardRepository.save(card);
    domainEventPublisher.publish(new CardAddedEvent(this, card));
    return card;
  }

	@Override
	public void changeCardPositions(ChangeCardPositionCommand command) {
    cardRepository.changePositions(command.getCardPositions());
	}
}
