package com.aiden.memo.presentation.feature.write

import android.Manifest
import android.os.Bundle
import com.aiden.memo.R
import com.aiden.memo.databinding.ActivityWriteBinding
import com.aiden.memo.presentation.base.BaseActivity
import com.aiden.memo.presentation.event.EventObserver
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import gun0912.tedbottompicker.TedBottomPicker
import org.koin.androidx.viewmodel.ext.android.viewModel


class WriteActivity : BaseActivity<ActivityWriteBinding, WriteViewModel>(R.layout.activity_write) {
    override val viewModel by viewModel<WriteViewModel>()
    private val intentKeyWriteType = "writeType"
    private val imageMaxCount = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val writeType = intent?.getSerializableExtra(intentKeyWriteType) as WriteType
        setLayout(writeType)
        setViewModel(writeType)
    }

    private fun setLayout(writeType: WriteType) {
        binding {
            writeRvSeletedImage.adapter = SelectedImageListAdapter()

            writeBtnImageSelect.setOnClickListener {
                val permissionListener = object : PermissionListener {
                    override fun onPermissionGranted() {
                        TedBottomPicker.with(this@WriteActivity)
                            .setPeekHeight(resources.displayMetrics.heightPixels)
                            .showTitle(false)
                            .setCompleteButtonText(getString(R.string.write_select_image_complete))
                            .setEmptySelectionText(getString(R.string.write_select_image_no_select))
                            .setSelectMaxCount(imageMaxCount)
                            .showMultiImage {
                                viewModel.setImageList(it)
                            }
                    }

                    override fun onPermissionDenied(deniedPermissions: List<String>) {
                        toastM("${getString(R.string.all_fail_get_permission)}\n${deniedPermissions}")
                    }


                }

                TedPermission.with(this@WriteActivity)
                    .setPermissionListener(permissionListener)
                    .setDeniedMessage(getString(R.string.all_fail_get_permission))
                    .setPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                    )
                    .check()
            }

            writeBtnSave.setOnClickListener {
                viewModel.checkData().run {
                    if (this) viewModel.saveMemo()
                }
            }

        }

        viewModel.isDataLoadingError.observe(this, EventObserver {
            if (it.second) {
                toastM("${it.first}")
            }
        })

        viewModel.imageUriList.observe(this, EventObserver {
            (binding.writeRvSeletedImage.adapter as SelectedImageListAdapter).setList(it)
        })

        viewModel.status.observe(this, EventObserver {
            when (it) {
                WriteStatusType.NO_TITLE -> toastM(getString(R.string.write_no_title))
                WriteStatusType.NO_BODY -> toastM(getString(R.string.write_no_body))
                WriteStatusType.CAN_NOT_SAVE -> toastM(getString(R.string.write_can_not_save))
                WriteStatusType.SAVE_DONE -> {
                    toastM(getString(R.string.write_save_done))
                    finish()
                }
            }
        })
    }

    private fun setViewModel(writeType: WriteType) {
        viewModel.setWriteType(writeType)
    }
}