package com.sergokuzneczow.domain.phone_mask_converter_case

import com.sergokuzneczow.database.api.DatabaseDataSourceApi
import com.sergokuzneczow.model.Post
import com.sergokuzneczow.network.api.NetworkDataSourceApi
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

public interface SyncPostSourcesCaseApi {

    public fun execute(scope: CoroutineScope)
}

public class SyncPostSourcesCaseImpl @Inject constructor(
    private val database: DatabaseDataSourceApi,
    private val network: NetworkDataSourceApi,
) : SyncPostSourcesCaseApi {

    private val intervalAfterException: Duration = 2.seconds
    private val intervalAfterSync: Duration = 10.seconds

    override fun execute(scope: CoroutineScope) {
        scope.launch(Dispatchers.IO) {
            while (true) {
                runCatching { synchronize() }
                    .onSuccess { delay(intervalAfterSync) }
                    .onSuccess { delay(intervalAfterException) }
            }
        }
    }

    private suspend fun synchronize() {
        val actual: List<Post> = network.getPosts()
        database.setPosts(actual)
    }
}