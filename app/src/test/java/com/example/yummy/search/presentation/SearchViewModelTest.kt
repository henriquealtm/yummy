package com.example.yummy.search.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.core_ui.util.handleOptional
import com.example.network.NetworkError
import com.example.network.Resource
import com.example.widget.progressbutton.ProgressButtonState
import com.example.yummy.search.data.SearchTestData.recipeDomainList
import com.example.yummy.search.data.SearchTestData.recipePresentationList
import com.example.yummy.search.domain.usecase.SearchUseCase
import com.example.yummy.search.presentation.model.RecipePresentation
import com.example.yummy.util.assertEqualsObservable
import com.example.yummy.util.assertNullObservable
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random.Default.nextInt
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class SearchViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private var searchUseCase = mockk<SearchUseCase>(relaxed = true)

    private lateinit var vm: SearchViewModel

    private val booleanObserver = Observer<Boolean> {}
    private val stringObserver = Observer<String> {}
    private val intObserver = Observer<Int> {}
    private val progressButtonObserver = Observer<ProgressButtonState> {}
    private val listRecipeDomainObserver = Observer<List<RecipePresentation>> {}
    private val networkErrorObserver = Observer<NetworkError?> {}

    private val cleanCategoryFiltersTestPrefix = "Prefix -"

    // This default values are going to be changed when the FoodIngredient becomes more than just strings
    private val validFoodIngredient = FoodIngredient(
        MutableLiveData("1"),
        MutableLiveData("1"),
        MutableLiveData("1")
    )

    private lateinit var categoryFoodList: List<MutableLiveData<Boolean>>

    @Before
    @Throws(Exception::class)
    fun prepare() {
        vm = SearchViewModel(searchUseCase).apply {
            cleanCategoryFiltersTextPrefix = cleanCategoryFiltersTestPrefix

            categoryFoodList = listOf(
                isHealthySelected,
                isDessertSelected,
                isSnackSelected,
                isMainCourseSelected,
                isFastFoodSelected
            )
        }
    }

    // Navigate Section
    @Test
    fun `verify if onNavigateBack is false when creating the SearchViewModel`() {
        vm.apply {
            assertEqualsObservable(onNavigateBack, booleanObserver, false)
        }
    }

    @Test
    fun `verify if navigateBack() changes the mOnNavigateBack to true`() {
        vm.apply {
            navigateBack()
            onNavigateBack.observeForever(booleanObserver)
            assertTrue(onNavigateBack.value.handleOptional())
            onNavigateBack.removeObserver(booleanObserver)
        }
    }

    @Test
    fun `verify if cleanFilters() turn all food categories to false and leave just one empty item in the foodingredientUpdatedList`() {
//        vm.apply {
//            val randomFoodCategory = categoryFoodList.random()
//            randomFoodCategory.value = true
//
//            ingredientUpdatedList += validFoodIngredient
//            ingredientUpdatedList += validFoodIngredient
//
//            cleanFilters()
//
//            randomFoodCategory.observeForever(booleanObserver)
//
//            assertFalse(randomFoodCategory.value.handleOptional())
//
//            randomFoodCategory.removeObserver(booleanObserver)
//
//            ingredientUpdatedList.observeForever(listFoodIngredientObserver)
//
//            assertTrue(
//                ingredientUpdatedList.value?.count() == 1 &&
//                        ingredientUpdatedList.value?.firstOrNull()?.description?.value.isNullOrEmpty()
//                            .handleOptional() &&
//                        ingredientUpdatedList.value?.firstOrNull()?.amount?.value.isNullOrEmpty()
//                            .handleOptional()
//            )
//
//            ingredientUpdatedList.removeObserver(listFoodIngredientObserver)
//        }
    }

    // Action Text Section
    @Test
    fun `verify if cleanCategoryFiltersText is equal "$actionTestPrefix - 5" when categoryFilteredFieldsCount is equal to 5`() {
        vm.apply {
            cleanCategoryFiltersText.observeForever(stringObserver)

            val count = 5

            categoryFoodList.forEach {
                it.value = true
            }

            assertEquals(cleanCategoryFiltersText.value, "$cleanCategoryFiltersTextPrefix $count")

            cleanCategoryFiltersText.removeObserver(stringObserver)
        }
    }

    // Food Category Section
    @Test
    fun `verify if categoryFilteredFieldsCount is equal 0 when creating the SearchViewModel`() {
        vm.apply {
            assertEqualsObservable(categoryFilteredFieldsCount, intObserver, 0)
        }
    }

    @Test
    fun `verify if categoryFilteredFieldsCount is equal to 1 when only isHealthySelected is true`() {
        vm.run {
            assertFilteredFieldsCount(isHealthySelected, count = 1)
        }
    }

    @Test
    fun `verify if categoryFilteredFieldsCount is equal to 1 when only isDessertSelected is true`() {
        vm.run { assertFilteredFieldsCount(isDessertSelected, count = 1) }
    }

    @Test
    fun `verify if categoryFilteredFieldsCount is equal to 1 when only isSnackSelected is true`() {
        vm.run { assertFilteredFieldsCount(isSnackSelected, count = 1) }
    }

    @Test
    fun `verify if categoryFilteredFieldsCount is equal to 1 when only isMainCourseSelected is true`() {
        vm.run { assertFilteredFieldsCount(isMainCourseSelected, count = 1) }
    }

    @Test
    fun `verify if categoryFilteredFieldsCount is equal to 1 when only isFastFoodSelected is true`() {
        vm.run { assertFilteredFieldsCount(isFastFoodSelected, count = 1) }
    }

    @Test
    fun `verify if categoryFilteredFieldsCount is equal to 5 when healthy, dessert, snack, mainCourse and fastFood items are true`() {
        vm.run {
            assertFilteredFieldsCount(
                isHealthySelected,
                isDessertSelected,
                isSnackSelected,
                isMainCourseSelected,
                isFastFoodSelected,
                count = 5
            )
        }
    }

    @Test
    fun `verify if categoryFilteredFieldsCount is equal to 2 after set healthy, dessert, snack, mainCourse and fastFood items to true and then set healthy, dessert and snack items to false`() {
        vm.run {
            categoryFilteredFieldsCount.observeForever(intObserver)

            categoryFoodList.run {
                forEach { it.value = true }

                val unselectedCount = nextInt(0, count())

                shuffled().take(unselectedCount).forEach { it.value = false }

                val selectedCount = count() - unselectedCount

                assertEquals(categoryFilteredFieldsCount.value, selectedCount)
            }

            categoryFilteredFieldsCount.removeObserver(intObserver)
        }
    }

    private fun assertFilteredFieldsCount(
        vararg isItemSelectedArray: MutableLiveData<Boolean>,
        count: Int
    ) {
        vm.apply {
            categoryFilteredFieldsCount.observeForever(intObserver)

            isItemSelectedArray.forEach {
                it.value = true
            }

            assertEquals(categoryFilteredFieldsCount.value, count)

            categoryFilteredFieldsCount.removeObserver(intObserver)
        }
    }

    // Search Recipe Section
    @Test
    fun `verify if searchButtonState is DISABLED when creating the SearchViewModel`() {
        vm.apply {
            assertEqualsObservable(
                searchButtonState, progressButtonObserver, ProgressButtonState.DISABLED
            )
        }
    }

    @Test
    fun `verify if searchButtonState is ENABLED when totalFilteredFields greater than 0`() {
        vm.apply {
            searchButtonState.observeForever(progressButtonObserver)

            categoryFoodList.random().value = true

            assertEquals(searchButtonState.value, ProgressButtonState.ENABLED)

            searchButtonState.removeObserver(progressButtonObserver)
        }
    }

    @Test
    fun `verify if searchButtonState is LOADING when searchRecipe is Resource_Loading`() {
        vm.apply {
            coEvery { searchUseCase() } returns MutableLiveData(Resource.Loading())

            searchButtonState.observeForever(progressButtonObserver)

            searchRecipe()

            assertEquals(searchButtonState.value, ProgressButtonState.LOADING)

            searchButtonState.removeObserver(progressButtonObserver)
        }
    }

    @Test
    fun `verify if searchSuccess is null when creating the SearchViewModel`() {
        vm.apply {
            assertNullObservable(searchSuccess, listRecipeDomainObserver)
        }
    }

    @Test
    fun `verify if searchSuccess has the expected List of RecipePresentation when recipeResult is Resource_Success`() {
        vm.apply {
            coEvery { searchUseCase() } returns MutableLiveData(
                Resource.Success(recipeDomainList)
            )

            searchRecipe()

            searchSuccess.observeForever(listRecipeDomainObserver)

            assertNotNull(searchSuccess.value)

            assertEquals(searchSuccess.value, recipePresentationList)

            searchSuccess.removeObserver(listRecipeDomainObserver)
        }
    }

    @Test
    fun `verify if searchError is null when creating the SearchViewModel`() {
        vm.apply {
            assertEqualsObservable(searchError, networkErrorObserver, null)
        }
    }

    @Test
    fun `verify if searchError has the expected NetworkError when recipeResult is Resource_Error`() {
        vm.apply {
            coEvery { searchUseCase() } returns MutableLiveData(
                Resource.Error(NetworkError.ConnectionError)
            )

            searchRecipe()

            searchError.observeForever(networkErrorObserver)

            assertNotNull(searchError.value)

            assertEquals(searchError.value, NetworkError.ConnectionError)

            searchError.removeObserver(networkErrorObserver)
        }
    }

}