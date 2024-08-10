package com.grupal.appnime.logic.usercase.login


import com.grupal.appnime.data.firebase.FireStoreRepository
import com.grupal.appnime.ui.entities.users.UserLogin

import kotlinx.coroutines.flow.flow


class SaveUserFireStoreUserCase {

    suspend fun invoke(user: UserLogin) = flow {
        val x = FireStoreRepository().saveUserLogin(user)
        x.onSuccess {
            emit(it)
        }
        x.onFailure {
            emit(false)

        }
    }
}