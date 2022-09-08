package com.technopolitan.mocaspaces.modules

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.fragment.app.Fragment
import com.hbisoft.pickit.PickiT
import com.hbisoft.pickit.PickiTCallbacks
import dagger.Module
import java.util.ArrayList
import javax.inject.Inject

@Module
class PikItModule @Inject constructor(
    private var fragment: Fragment?,
    private var context: Context,
    private var activity: Activity
) : PickiTCallbacks {
    private lateinit var pickiT: PickiT
    private lateinit var callBack: (path: String) -> Unit


    fun init(uri: Uri, callBack: (path: String) -> Unit) {
        this.callBack = callBack
        pickiT = PickiT(context, this, activity)
        pickiT.getPath(uri, Build.VERSION.SDK_INT)
    }

    override fun PickiTonUriReturned() {
        TODO("Not yet implemented")
    }

    override fun PickiTonStartListener() {
        TODO("Not yet implemented")
    }

    override fun PickiTonProgressUpdate(progress: Int) {
        TODO("Not yet implemented")
    }

    override fun PickiTonCompleteListener(
        path: String?,
        wasDriveFile: Boolean,
        wasUnknownProvider: Boolean,
        wasSuccessful: Boolean,
        Reason: String?
    ) {
        path?.let(callBack)
    }

    override fun PickiTonMultipleCompleteListener(
        paths: ArrayList<String>?,
        wasSuccessful: Boolean,
        Reason: String?
    ) {
        TODO("Not yet implemented")
    }
}