package  com.example.foodmap.Repository

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.foodmap.Repository.FoodReviewItem

@Dao
interface FoodReviewListDao {

    @MapInfo(keyColumn = "id")
    @Query("SELECT * FROM todoitems_table order by id ASC")
    fun getToDoItems(): Flow<Map<Int, FoodReviewItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(toDoItem: FoodReviewItem)

    @Query("DELETE FROM todoitems_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM todoitems_table WHERE id = :id")
    suspend fun getItem(id: Int): FoodReviewItem

    @Query("UPDATE todoitems_table SET completed=:completed WHERE id=:toDoId")
    suspend fun updateCompleted(toDoId: Int, completed: Int)

    @Query("DELETE FROM todoitems_table WHERE id=:id")
    suspend fun deleteItem(id: Int)

    @Update
    suspend fun updateItem(toDoItem: FoodReviewItem)

}