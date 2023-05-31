package io.wetfloo.cutaway.data.repository.createeditprofile

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import dagger.hilt.android.qualifiers.ApplicationContext
import io.wetfloo.cutaway.core.common.DispatcherProvider
import io.wetfloo.cutaway.core.common.runSuspendCatching
import io.wetfloo.cutaway.data.api.GeneralApi
import io.wetfloo.cutaway.data.api.model.ProfileInformationDto
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class RealCreateEditProfileRepository @Inject constructor(
    private val api: GeneralApi,
    private val dispatchers: DispatcherProvider,
    @ApplicationContext private val context: Context,
) : CreateEditProfileRepository {
    override suspend fun createProfile(profileInformation: ProfileInformation) = runSuspendCatching {
        api.createProfile(ProfileInformationDto.fromData(profileInformation))
    }.map { profileInformation }

    override suspend fun updateProfile(profileInformation: ProfileInformation) = runSuspendCatching {
        api.updateProfile(ProfileInformationDto.fromData(profileInformation))
    }.map { profileInformation }

    override suspend fun deleteProfile(profileId: String) = runSuspendCatching {
        api.deleteProfile(profileId)
    }

    override suspend fun uploadImage(uri: Uri): Result<String, Throwable> {
        val cursor = context.contentResolver.query(
            /* uri = */ uri,
            /* projection = */ null,
            /* selection = */ null,
            /* selectionArgs = */ null,
            /* sortOrder = */ null,
        )?.apply {
            moveToFirst()
        } ?: return Err(NullPointerException())

        return cursor.use {
            val columnIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            if (columnIndex == -1) {
                return Err(Exception("columnIndex is -1"))
            }
            val filePath = cursor.getString(columnIndex)
            val file = File(filePath)
            val requestFile = file
                .asRequestBody(
                    contentType = context
                        .contentResolver
                        .getType(uri)
                        ?.toMediaTypeOrNull(),
                )

            val multipartBody = MultipartBody
                .Part
                .createFormData(
                    name = "file",
                    filename = file.name,
                    body = requestFile,
                )

            runSuspendCatching {
                api.createImage(multipartBody).imageId
            }
        }
    }
}
