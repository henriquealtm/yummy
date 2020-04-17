package com.example.yummy.search.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.core_ui.extension.handleOptional
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

    private val actionTestPrefix = "Prefix -"

    @Before
    @Throws(Exception::class)
    fun prepare() {
        vm = SearchViewModel().apply {
            actionTextPrefix = actionTestPrefix
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

    // Action Text Section
    @Test
    fun `verify if actionText is equal "$actionTestPrefix - 5" when filteredFieldsCount is equal to 5`() {
        vm.apply {
            actionText.observeForever(stringObserver)

            val count = 5

            filteredFieldsCount.value = count

            assertEquals(actionText.value, "$actionTestPrefix $count")

            actionText.removeObserver(stringObserver)
        }
    }

    // Food Category Section
    @Test
    fun `verify if filteredFieldsCount is equal 0 when creating the SearchViewModel`() {
        vm.apply {
            filteredFieldsCount.observeForever(intObserver)
            assertEquals(filteredFieldsCount.value, 0)
            filteredFieldsCount.removeObserver(intObserver)
        }
    }

    @Test
    fun `verify if filteredFieldsCount is equal to 1 when only isHealthySelected is true`() {
        vm.run {
            assertFilteredFieldsCount(isHealthySelected, count = 1)
        }
    }

    @Test
    fun `verify if filteredFieldsCount is equal to 1 when onlyis DessertSelected is true`() {
        vm.run { assertFilteredFieldsCount(isDessertSelected, count = 1) }
    }

    @Test
    fun `verify if filteredFieldsCount is equal to 1 when onlyis SnackSelected is true`() {
        vm.run { assertFilteredFieldsCount(isSnackSelected, count = 1) }
    }

    @Test
    fun `verify if filteredFieldsCount is equal to 1 when onlyis MainCourseSelected is true`() {
        vm.run { assertFilteredFieldsCount(isMainCourseSelected, count = 1) }
    }

    @Test
    fun `verify if filteredFieldsCount is equal to 1 when onlyis FastFoodSelected is true`() {
        vm.run { assertFilteredFieldsCount(isFastFoodSelected, count = 1) }
    }

    @Test
    fun `verify if filteredFieldsCount is equal to 5 when healthy, dessert, snack, mainCourse and fastFood items are true`() {
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
    fun `verify if filteredFieldsCount is equal to 2 after set healthy, dessert, snack, mainCourse and fastFood items to true and then set healthy, dessert and snack items to false`() {
        vm.run {
            filteredFieldsCount.observeForever(intObserver)

            listOf(
                isHealthySelected,
                isDessertSelected,
                isSnackSelected,
                isMainCourseSelected,
                isFastFoodSelected
            ).run {
                forEach { it.value = true }

                val unselectedCount = nextInt(0, count())

                shuffled().take(unselectedCount).forEach { it.value = false }

                val selectedCount = count() - unselectedCount

                assertEquals(filteredFieldsCount.value, selectedCount)
            }

            filteredFieldsCount.removeObserver(intObserver)
        }
    }

    private fun assertFilteredFieldsCount(
        vararg isItemSelectedArray: MutableLiveData<Boolean>,
        count: Int
    ) {
        vm.apply {
            filteredFieldsCount.observeForever(intObserver)

            isItemSelectedArray.forEach {
                it.value = true
            }

            assertEquals(filteredFieldsCount.value, count)

            filteredFieldsCount.removeObserver(intObserver)
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
            searchRecipe()
            onSearchRecipe.observeForever(booleanObserver)
            assertTrue(onSearchRecipe.value.handleOptional())
            onSearchRecipe.removeObserver(booleanObserver)
        }
    }

}