package com.filipibrentegani.marvelheroes.heroeslist.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.filipibrentegani.marvelheroes.entity.domain.HeroRoom
import com.filipibrentegani.marvelheroes.heroeslist.BaseTest
import com.filipibrentegani.marvelheroes.localpersistence.HeroDao
import com.nhaarman.mockitokotlin2.any
import junit.framework.Assert.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class FavoriteHeroesRepositoryTest : BaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var localPersistenceMock: HeroDao

    private val repository: FavoriteHeroesRepository by lazy {
        FavoriteHeroesRepository(
            localPersistenceMock
        )
    }

    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
        FavoriteHeroesRepository.invalidatedCache = true
        FavoriteHeroesRepository.updatedListHero.clear()
    }

    @Test
    fun whenRequestAFavoriteHeroAndCacheIsInvalidated_repositoryUpdateCacheAndReturn() {
        runBlockingTest {
            FavoriteHeroesRepository.invalidatedCache = true
            Mockito.`when`(localPersistenceMock.getAll()).thenReturn(
                listOf(
                    HeroRoom(
                        1,
                        "name",
                        "description",
                        "thumbnail",
                        true,
                        "{\"available\":0,\"collectionUri\":\"\",\"heroId\":1,\"items\":[]}",
                        "{\"available\":0,\"collectionUri\":\"\",\"heroId\":1,\"items\":[]}",
                        "{\"available\":0,\"collectionUri\":\"\",\"heroId\":1,\"items\":[]}",
                        "{\"available\":0,\"collectionUri\":\"\",\"heroId\":1,\"items\":[]}"
                    )
                )
            )

            val hero = repository.getFavoriteHero(1)

            assertEquals("name", hero?.name)
            assertEquals("description", hero?.description)
            assertTrue(hero?.favorite ?: false)
            assertEquals(1, FavoriteHeroesRepository.updatedListHero.size)
            Mockito.verify(localPersistenceMock).getAll()
        }
    }

    @Test
    fun whenRequestAFavoriteHero_repositoryReturnFromCache() {
        runBlockingTest {
            FavoriteHeroesRepository.updatedListHero.add(getHero(1))
            FavoriteHeroesRepository.invalidatedCache = false

            val heroes = repository.getFavoriteHeroes()

            assertEquals(1, heroes.size)
            assertEquals("name", heroes[0].name)
            assertEquals("description", heroes[0].description)
            assertFalse(heroes[0].favorite)
            Mockito.verifyNoInteractions(localPersistenceMock)
        }
    }

    @Test
    fun whenRequestAFavoriteHeroWithInvalidatedCache_repositoryReturnFromCache() {
        runBlockingTest {
            FavoriteHeroesRepository.invalidatedCache = true
            Mockito.`when`(localPersistenceMock.getAll()).thenReturn(
                listOf(
                    HeroRoom(
                        1,
                        "name",
                        "description",
                        "thumbnail",
                        true,
                        "{\"available\":0,\"collectionUri\":\"\",\"heroId\":1,\"items\":[]}",
                        "{\"available\":0,\"collectionUri\":\"\",\"heroId\":1,\"items\":[]}",
                        "{\"available\":0,\"collectionUri\":\"\",\"heroId\":1,\"items\":[]}",
                        "{\"available\":0,\"collectionUri\":\"\",\"heroId\":1,\"items\":[]}"
                    )
                )
            )

            val heroes = repository.getFavoriteHeroes()

            assertEquals(1, heroes.size)
            assertEquals("name", heroes[0].name)
            assertEquals("description", heroes[0].description)
            assertTrue(heroes[0].favorite)
            Mockito.verify(localPersistenceMock).getAll()
        }
    }

    @Test
    fun whenRequestAddFavoriteHero_repositoryCallPersistenceAndInvalidateCache() {
        runBlockingTest {
            repository.addFavoriteHeroes(getHero(1))

            assertTrue(FavoriteHeroesRepository.invalidatedCache)
            Mockito.verify(localPersistenceMock).insertFavorite(any())
        }
    }

    @Test
    fun whenRequestRemoveFavoriteHero_repositoryCallPersistenceAndInvalidateCache() {
        runBlockingTest {
            repository.removeFavoriteHero(getHero(1))

            assertTrue(FavoriteHeroesRepository.invalidatedCache)
            Mockito.verify(localPersistenceMock).removeFavorite(1)
        }
    }
}