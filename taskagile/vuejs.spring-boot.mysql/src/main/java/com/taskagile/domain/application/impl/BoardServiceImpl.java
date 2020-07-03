package com.taskagile.domain.application.impl;

import java.util.List;

import javax.transaction.Transactional;

import com.taskagile.domain.application.BoardService;
import com.taskagile.domain.application.commands.CreateBoardCommand;
import com.taskagile.domain.common.event.DomainEventPublisher;
import com.taskagile.domain.model.board.Board;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.board.BoardManagement;
import com.taskagile.domain.model.board.BoardMemberRepository;
import com.taskagile.domain.model.board.BoardRepository;
import com.taskagile.domain.model.board.events.BoardCreatedEvent;
import com.taskagile.domain.model.board.events.BoardMemberAddedEvent;
import com.taskagile.domain.model.user.User;
import com.taskagile.domain.model.user.UserFinder;
import com.taskagile.domain.model.user.UserId;
import com.taskagile.domain.model.user.UserNotFoundException;

import org.springframework.stereotype.Service;

@Transactional
@Service
public class BoardServiceImpl implements BoardService{

  private BoardRepository boardRepository;
  private BoardMemberRepository boardMemberRepository;
  private BoardManagement boardManagement;
  private DomainEventPublisher domainEventPublisher;
  private UserFinder userFinder;

  public BoardServiceImpl(BoardRepository boardRepository,
                          BoardManagement boardManagement,
                          BoardMemberRepository boardMemberRepository,
                          DomainEventPublisher domainEventPublisher,
                          UserFinder userFinder) {

    this.boardRepository = boardRepository;
    this.boardManagement = boardManagement;
    this.boardMemberRepository = boardMemberRepository;
    this.domainEventPublisher = domainEventPublisher;
    this.userFinder = userFinder;
  }

	@Override
	public List<Board> findBoardsByMembership(UserId userId) {
		return boardRepository.findBoardsByMembership(userId);
	}

	@Override
	public Board findById(BoardId boardId) {
		return boardRepository.findById(boardId);
	}

	@Override
	public List<User> findMembers(BoardId boardId) {
		return boardMemberRepository.findMembers(boardId);
  }

  // ApplicationService에서 Board를 생성하는데 구체적인 로직을 알 필요는 없기 때문에 도메인 로직으로 이동한다
	@Override
	public Board createBoard(CreateBoardCommand command) {
    Board board = boardManagement.createBoard(command.getUserId(), command.getName(), command.getDescription(), command.getTeamId());
    domainEventPublisher.publish(new BoardCreatedEvent(this, board));
    return board;
	}

	@Override
	public User addMember(BoardId boardId, String usernameOrEmailAddress) throws UserNotFoundException {
    User user = userFinder.find(usernameOrEmailAddress);
    boardMemberRepository.add(boardId, user.getId());
    domainEventPublisher.publish(new BoardMemberAddedEvent(this, boardId, user));

    return user;
	}
}
