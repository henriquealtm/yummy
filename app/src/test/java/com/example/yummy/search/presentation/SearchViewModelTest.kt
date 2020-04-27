package com.example.yummy.search.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.core_ui.extension.handleOptional
import com.example.core_ui.extension.plusAssign
import com.example.network.Resource
import com.example.widget.progressbutton.ProgressButtonState
import com.example.yummy.search.domain.usecase.SearchUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random.Default.nextInt
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SearchViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private var promoStyleUseCase = mockk<SearchUseCase>(relaxed = true)

    private lateinit var vm: SearchViewModel

    private val booleanObserver = Observer<Boolean> {}
    private val stringObserver = Observer<String> {}
    private val intObserver = Observer<Int> {}
    private val progressButtonObserver = Observer<ProgressButtonState> {}
    private val listFoodIngredientObserver = Observer<MutableList<FoodIngredient>> {}

    private val actionTestPrefix = "Prefix -"

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
        vm = SearchViewModel(promoStyleUseCase).apply {
            actionTextPrefix = actionTestPrefix

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
            onNavigateBack.observeForever(booleanObserver)
            assertFalse(onNavigateBack.value.handleOptional())
            onNavigateBack.removeObserver(booleanObserver)
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

    private suspend fun foo() {
        delay(3000)
    }

    // Action Text Section
    @Test
    fun `verify if actionText is equal "$actionTestPrefix - 6" when categoryFilteredFieldsCount is equal to 5 and ingredientFilteredFieldsCount is equal to 1`() {
        vm.apply {
            actionText.observeForever(stringObserver)

            val count = 6

            categoryFoodList.forEach {
                it.value = true
            }

            ingredientUpdatedList += validFoodIngredient

            assertEquals(actionText.value, "$actionTestPrefix $count")

            actionText.removeObserver(stringObserver)
        }
    }

    // Food Category Section
    @Test
    fun `verify if categoryFilteredFieldsCount is equal 0 when creating the SearchViewModel`() {
        vm.apply {
            categoryFilteredFieldsCount.observeForever(intObserver)
            assertEquals(categoryFilteredFieldsCount.value, 0)
            categoryFilteredFieldsCount.removeObserver(intObserver)
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

    // Ingredients Section
    @Test
    fun `verify if ingredientFilteredFieldsCount is equal to 0 when creating the SearchViewModel`() {
        vm.apply {
            ingredientFilteredFieldsCount.observeForever(intObserver)
            assertEquals(ingredientFilteredFieldsCount.value, 0)
            ingredientFilteredFieldsCount.removeObserver(intObserver)
        }
    }

    @Test
    fun `verify if ingredientFilteredFieldsCount is equal to 1 when there is 1 valid ingredient added`() {
        vm.apply {
            ingredientFilteredFieldsCount.observeForever(intObserver)

            ingredientUpdatedList += validFoodIngredient

            assertEquals(ingredientFilteredFieldsCount.value, 1)

            ingredientFilteredFieldsCount.removeObserver(intObserver)
        }
    }

    // Total Filtered Fields Section
    @Test
    fun `verify if totalFilteredFields is equal to 2 when there is 1 food category selected and 1 valid ingredient added`() {
        vm.apply {
            totalFilteredFields.observeForever(intObserver)

            categoryFoodList.random().value = true

            ingredientUpdatedList += validFoodIngredient

            assertEquals(totalFilteredFields.value, 2)

            totalFilteredFields.removeObserver(intObserver)
        }
    }

    @Test
    fun `verify if searchButtonState is DISABLED when creating the SearchViewModel`() {
        vm.apply {
            searchButtonState.observeForever(progressButtonObserver)
            assertEquals(searchButtonState.value, ProgressButtonState.DISABLED)
            searchButtonState.removeObserver(progressButtonObserver)
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
            coEvery { promoStyleUseCase() } returns MutableLiveData(Resource.Loading())

            searchButtonState.observeForever(progressButtonObserver)

            searchRecipe()

            assertEquals(searchButtonState.value, ProgressButtonState.LOADING)

            searchButtonState.removeObserver(progressButtonObserver)
        }
    }

}