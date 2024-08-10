package com.grupal.appnime.logic.usercase.login

import com.grupal.appnime.data.firebase.FireStoreRepository
import com.grupal.appnime.ui.entities.users.UserLogin
import kotlinx.coroutines.flow.flow

class GetUserByEmailAndPasswordUserCase {
    suspend fun invoke(id:String) = flow<Result<UserLogin>>{
        FireStoreRepository().getUserById(id)
            .onSuccess {
                emit(Result.success(it.first()))
            }
            .onFailure {error->
                emit(Result.failure(error))
            }
    }
}