package com.taskagile.domain.application.impl;

import java.util.List;

import javax.transaction.Transactional;

import com.taskagile.domain.application.CardListService;
import com.taskagile.domain.application.commands.AddCardListCommand;
import com.taskagile.domain.common.event.DomainEventPublisher;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.cardlist.CardList;
import com.taskagile.domain.model.cardlist.CardListRepository;
import com.taskagile.domain.model.cardlist.events.CardListAddedEvent;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class CardListServiceImpl implements CardListService{

  private CardListRepository cardListRepository;
  private DomainEventPublisher domainEventPublisher;

  public CardListServiceImpl(CardListRepository cardListRepository, DomainEventPublisher domainEventPublisher) {
    this.cardListRepository = cardListRepository;
    this.domainEventPublisher = domainEventPublisher;
  }

  @Override
	public List<CardList> findByBoardId(BoardId boardId) {
    return cardListRepository.findByBoardId(boardId);
	}

	@Override
	public CardList addCardList(AddCardListCommand command) {
    CardList cardList = CardList.create(command.getBoardId(), command.getUserId(), command.getName(), command.getPosition());
    cardListRepository.save(cardList);
    domainEventPublisher.publish(new CardListAddedEvent(this, cardList));

    return cardList;
	}
}
