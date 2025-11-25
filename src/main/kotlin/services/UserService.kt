package main.kotlin.services

import main.kotlin.data.User
import main.kotlin.repositories.UserRepository

class UserService(private val userRepository: UserRepository) {
private lateinit var loggedUser : User

    fun login(nickname: String, password: String):Boolean{
        val user = userRepository.findByNickname(nickname)
            return if(user !=null && user.password ==password){
                loggedUser = user
                true
            }else{
                false
            }
    }
    fun register(user: User) {
        if (userRepository.registeredUser(user.nickname)) {
            throw Exception("El nickname ya está registrado")
        }
        userRepository.add(user)
    }


    fun getLoggedUser(): User? = loggedUser

}