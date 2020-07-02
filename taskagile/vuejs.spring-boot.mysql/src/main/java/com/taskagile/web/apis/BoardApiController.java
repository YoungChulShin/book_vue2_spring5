package com.taskagile.web.apis;

import com.taskagile.domain.application.BoardService;
import com.taskagile.domain.application.TeamService;
import com.taskagile.domain.common.security.CurrentUser;
import com.taskagile.domain.model.board.Board;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.user.SimpleUser;
import com.taskagile.web.payload.CreateBoardPayload;
import com.taskagile.web.results.ApiResult;
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

    public BoardApiController(BoardService boardService, TeamService teamService) {
        this.boardService = boardService;
        this.teamService = teamService;
    }

    @PostMapping("api/boards")
    public ResponseEntity<ApiResult> createBoard(
        @RequestBody CreateBoardPayload payload,
        @CurrentUser SimpleUser currentUser) {

            Board board = boardService.createBoard(payload.toCommand(currentUser.getUserId()));
            return CreateBoardResult.build(board);
    }

    // @GetMapping("api/boards/{boardId}")
    // public ResponseEntity<ApiResult> getBoard(@PathVariable long rawBoardId) {

    //   BoardId boardId = new BoardId(rawBoardId);
    //   Board board = boardService.findById(boardId);
    //   if (board == null) {
    //     return Result.notFound();
    //   }

    //   List<User> members = boardService.findMembers(board);

    //   Team team = null;
    //   if (!board.isPersonal()) {
    //     team = teamService.findById(board.getTeamId());
    //   }
    // }
}
