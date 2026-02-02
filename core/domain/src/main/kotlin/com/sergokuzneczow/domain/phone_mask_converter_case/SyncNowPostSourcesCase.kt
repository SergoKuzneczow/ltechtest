package com.sergokuzneczow.domain.phone_mask_converter_case

import com.sergokuzneczow.database.api.DatabaseDataSourceApi
import com.sergokuzneczow.model.Post
import com.sergokuzneczow.network.api.NetworkDataSourceApi
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

public interface SyncNowPostSourcesCaseApi {

    public fun execute(scope: CoroutineScope)
}

public class SyncNowPostSourcesCaseImpl @Inject constructor(
    private val database: DatabaseDataSourceApi,
    private val network: NetworkDataSourceApi,
) : SyncNowPostSourcesCaseApi {

    override fun execute(scope: CoroutineScope) {
        scope.launch(Dispatchers.IO) { runCatching { synchronize() } }
    }

    private suspend fun synchronize() {
        val actual: List<Post> = network.getPosts()
        database.setPosts(actual)
    }
}