package com.springbank.user.query.api.services;

import com.springbank.user.query.api.controllers.response.UserLookupResponse;
import com.springbank.user.query.api.controllers.request.FindAllUsersQuery;
import com.springbank.user.query.api.controllers.request.FindUserByIdQuery;
import com.springbank.user.query.api.controllers.request.SearchUsersQuery;

public interface UserQueryHandler {
    UserLookupResponse getUserById(FindUserByIdQuery query);
    UserLookupResponse searchUsers(SearchUsersQuery query);
    UserLookupResponse getAllUsers(FindAllUsersQuery query);
}
