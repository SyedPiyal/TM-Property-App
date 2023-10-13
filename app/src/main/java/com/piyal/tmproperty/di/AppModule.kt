package com.piyal.tmproperty.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.piyal.tmproperty.repository.PropertyRepository
import com.piyal.tmproperty.repository.PropertyService
import com.piyal.tmproperty.repository.UserRepository
import com.piyal.tmproperty.repository.UserService
import com.piyal.tmproperty.ui.home.HomeViewModel
import com.piyal.tmproperty.ui.login.LoginViewModel
import com.piyal.tmproperty.ui.signup.SignUpViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideUserService(firebaseAuth: FirebaseAuth, firestore: FirebaseFirestore): UserService {
        return UserService(firebaseAuth, firestore)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userService: UserService): UserRepository {
        return UserRepository(userService)
    }

    @Provides
    @Singleton
    fun provideSignUpViewModel(userRepository: UserRepository): SignUpViewModel {
        return SignUpViewModel(userRepository)
    }

    @Provides
    @Singleton
    fun provideLoginViewModel(userRepository: UserRepository): LoginViewModel {
        return LoginViewModel(userRepository)
    }

    @Provides
    @Singleton
    fun providePropertyService(firestore: FirebaseFirestore): PropertyService {
        return PropertyService(firestore)
    }

    @Provides
    @Singleton
    fun providePropertyRepository(propertyService: PropertyService): PropertyRepository {
        return PropertyRepository(propertyService)
    }

    @Provides
    @Singleton
    fun provideHomeViewModel(propertyRepository: PropertyRepository): HomeViewModel {
        return HomeViewModel(propertyRepository)
    }

    // Add other dependencies as needed
}

