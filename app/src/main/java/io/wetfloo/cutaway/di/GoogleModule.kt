package io.wetfloo.cutaway.di

import android.content.Context
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallClient
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GoogleModule {
    @Provides
    fun provideBarcodeScanner(
        @ApplicationContext context: Context,
    ): GmsBarcodeScanner {
        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
            )
            .build()

        return GmsBarcodeScanning.getClient(
            /* context = */
            context,
            /* options = */
            options,
        )
    }

    @Provides
    fun provideModuleInstallClient(
        @ApplicationContext context: Context,
    ): ModuleInstallClient = ModuleInstall.getClient(context)
}
