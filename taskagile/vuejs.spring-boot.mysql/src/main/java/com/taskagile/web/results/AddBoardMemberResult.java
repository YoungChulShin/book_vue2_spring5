package com.taskagile.web.results;

import com.taskagile.domain.model.user.User;

import org.springframework.http.ResponseEntity;

public class AddBoardMemberResult {

  public static ResponseEntity<ApiResult> build(User user) {
    ApiResult apiResult = ApiResult.blank()
      .add("id", user.getId())
      .add("shortName", user.getInitials());

    return Result.ok(apiResult);
  }
}
