package com.example.yummy.search.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.core_ui.extension.handleOptional
import com.example.core_ui.extension.plusAssign
import com.example.widget.progressbutton.ProgressButtonState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random.Default.nextInt
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PromoStyleViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

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
        vm = SearchViewModel().apply {
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
    fun `verify if cleanFilters() turn all food categories to false and leave just one empty item in the foodIngredientList`() {
        vm.apply {
            val randomFoodCategory = categoryFoodList.random()
            randomFoodCategory.value = true

            foodIngredientList += validFoodIngredient
            foodIngredientList += validFoodIngredient

            cleanFilters()

            isHealthySelected.observeForever(booleanObserver)

            assertFalse(randomFoodCategory.value.handleOptional())

            isHealthySelected.removeObserver(booleanObserver)

            foodIngredientList.observeForever(listFoodIngredientObserver)

            assertTrue(
                foodIngredientList.value?.count() == 1 &&
                        foodIngredientList.value?.firstOrNull()?.description?.value.isNullOrEmpty().handleOptional() &&
                        foodIngredientList.value?.firstOrNull()?.amount?.value.isNullOrEmpty().handleOptional()
            )

            foodIngredientList.removeObserver(listFoodIngredientObserver)


        }
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

            foodIngredientList += validFoodIngredient

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
    fun `verify if categoryFilteredFieldsCount is equal to 1 when onlyis DessertSelected is true`() {
        vm.run { assertFilteredFieldsCount(isDessertSelected, count = 1) }
    }

    @Test
    fun `verify if categoryFilteredFieldsCount is equal to 1 when onlyis SnackSelected is true`() {
        vm.run { assertFilteredFieldsCount(isSnackSelected, count = 1) }
    }

    @Test
    fun `verify if categoryFilteredFieldsCount is equal to 1 when onlyis MainCourseSelected is true`() {
        vm.run { assertFilteredFieldsCount(isMainCourseSelected, count = 1) }
    }

    @Test
    fun `verify if categoryFilteredFieldsCount is equal to 1 when onlyis FastFoodSelected is true`() {
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

            foodIngredientList += validFoodIngredient

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

            foodIngredientList += validFoodIngredient

            assertEquals(totalFilteredFields.value, 2)

            totalFilteredFields.removeObserver(intObserver)
        }
    }

    // Search Recipe Section
    @Test
    fun `verify if onSearchRecipe is false when creating the SearchViewModel`() {
        vm.apply {
            onSearchRecipe.observeForever(booleanObserver)
            assertFalse(onSearchRecipe.value.handleOptional())
            onSearchRecipe.removeObserver(booleanObserver)
        }
    }

    @Test
    fun `verify if searchRecipe() changes the onSearchRecipe to true`() {
        vm.apply {
            onSearchRecipe.observeForever(booleanObserver)
            searchRecipe()
            assertTrue(onSearchRecipe.value.handleOptional())
            onSearchRecipe.removeObserver(booleanObserver)
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

}