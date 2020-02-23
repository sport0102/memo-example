package com.aiden.memo.presentation.feature.write

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.aiden.memo.R
import com.aiden.memo.databinding.ActivityWriteBinding
import com.aiden.memo.presentation.base.BaseActivity
import com.aiden.memo.presentation.event.EventObserver
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import gun0912.tedbottompicker.TedBottomPicker
import kotlinx.android.synthetic.main.dialog_default.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class WriteActivity : BaseActivity<ActivityWriteBinding, WriteViewModel>(R.layout.activity_write) {
    override val viewModel by viewModel<WriteViewModel>()
    private val intentKeyWriteType = "writeType"
    private val intentKeyId = "id"
    private val imageMaxCount = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val writeType = intent?.getSerializableExtra(intentKeyWriteType) as WriteType
        val memoId = intent?.getStringExtra(intentKeyId)
        setLayout()
        setViewModel(writeType, memoId)
    }

    private fun setLayout() {
        binding {
            writeRvSeletedImage.adapter = SelectedImageListAdapter(viewModel)
            writeRvImageLink.adapter = LinkListAdapter(viewModel)

            writeBtnImageSelect.setOnClickListener {
                val permissionListener = object : PermissionListener {
                    override fun onPermissionGranted() {
                        TedBottomPicker.with(this@WriteActivity)
                            .setPeekHeight(resources.displayMetrics.heightPixels)
                            .showTitle(false)
                            .setCompleteButtonText(getString(R.string.write_select_image_complete))
                            .setEmptySelectionText(getString(R.string.write_select_image_no_select))
                            .setSelectMaxCount(imageMaxCount)
                            .setSelectedUriList(viewModel.imageUriList.value?.map {
                                Uri.parse(
                                    it
                                )
                            })
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

            writeBtnLinkSelect.setOnClickListener {
                showDefaultDialog(onYes = {
                    if (it.isNotEmpty()) {
                        viewModel.addImageLink(it)
                    }
                })
            }

            writeBtnSave.setOnClickListener {
                viewModel.checkData().run {
                    if (this) viewModel.saveMemo()
                }
            }

        }
    }

    private fun setViewModel(
        writeType: WriteType,
        memoId: String?
    ) {
        viewModel.setWriteType(writeType, memoId)

        viewModel.isDataLoadingError.observe(this, EventObserver {
            if (it.second) {
                toastM("${it.first}")
            }
        })

        viewModel.imageUriList.observe(this, Observer {
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

        viewModel.memo.observe(this, EventObserver {
            viewModel.setMemoData()
        })

        viewModel.imageLinkList.observe(this, Observer {
            (binding.writeRvImageLink.adapter as LinkListAdapter).setList(it)
        })
    }

    private fun showDefaultDialog(
        onYes: (String) -> Unit
    ) {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(
            R.layout.dialog_default,
            null
        )
        builder.setView(view).setCancelable(true)
        val dialog = builder.create()
        dialog.show()
        view.dialog_default_no.setOnClickListener {
            dialog.dismiss()
        }
        view.dialog_default_yes.setOnClickListener {
            dialog.dismiss()
            onYes(view.dialog_default_et.text.toString())
        }
    }
}