package com.taskagile.web.apis;

import com.taskagile.domain.application.CardListService;
import com.taskagile.domain.common.security.CurrentUser;
import com.taskagile.domain.model.user.User;
import com.taskagile.web.payload.AddCardListPayload;
import com.taskagile.web.results.ApiResult;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CardListApiController {

  private CardListService cardListService;

  public CardListApiController(CardListService cardListService) {
    this.cardListService = cardListService;
  }

  @PostMapping("/api/card-lists")
  public ResponseEntity<ApiResult> addCardList(@RequestBody AddCardListPayload payload, @CurrentUser User user) {

  }
}
