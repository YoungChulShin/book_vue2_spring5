package com.taskagile.web.apis;

import java.util.List;

import com.taskagile.domain.application.BoardService;
import com.taskagile.domain.application.CardListService;
import com.taskagile.domain.application.CardService;
import com.taskagile.domain.application.TeamService;
import com.taskagile.domain.common.security.CurrentUser;
import com.taskagile.domain.model.board.Board;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.card.Card;
import com.taskagile.domain.model.cardlist.CardList;
import com.taskagile.domain.model.team.Team;
import com.taskagile.domain.model.user.SimpleUser;
import com.taskagile.domain.model.user.User;
import com.taskagile.web.payload.CreateBoardPayload;
import com.taskagile.web.results.ApiResult;
import com.taskagile.web.results.BoardResult;
import com.taskagile.web.results.CreateBoardResult;
import com.taskagile.web.results.Result;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class BoardApiController {

    private BoardService boardService;
    private TeamService teamService;
    private CardService cardService;
    private CardListService cardListService;

    public BoardApiController(BoardService boardService,
                              TeamService teamService,
                              CardListService cardListService,
                              CardService cardService) {
        this.boardService = boardService;
        this.teamService = teamService;
        this.cardListService = cardListService;
        this.cardService = cardService;
    }

    @PostMapping("api/boards")
    public ResponseEntity<ApiResult> createBoard(
        @RequestBody CreateBoardPayload payload,
        @CurrentUser SimpleUser currentUser) {

            Board board = boardService.createBoard(payload.toCommand(currentUser.getUserId()));
            return CreateBoardResult.build(board);
    }

    @GetMapping("api/boards/{boardId}")
    public ResponseEntity<ApiResult> getBoard(@PathVariable long rawBoardId) {

      // 보드 조회
      BoardId boardId = new BoardId(rawBoardId);
      Board board = boardService.findById(boardId);
      if (board == null) {
        return Result.notFound();
      }

      // 멤버 조회
      List<User> members = boardService.findMembers(boardId);

      // 팀 조회
      Team team = null;
      if (!board.isPersonal()) {
        team = teamService.findById(board.getTeamId());
      }

      // 카드 리스트 조회
      List<CardList> cardLists = cardListService.findByBoardId(boardId);

      // 카드 조회
      List<Card> cards = cardService.findByBoardId(boardId);

      return BoardResult.build(team, board, members, cardLists, cards);
    }
}
