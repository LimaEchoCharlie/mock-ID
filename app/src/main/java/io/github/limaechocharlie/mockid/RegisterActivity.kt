package io.github.limaechocharlie.mockid

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.simprints.libsimprints.Constants
import kotlinx.android.synthetic.main.activity_identify.*
import android.content.Intent



class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        api_key.text = getString(R.string.api_key, getIntent().getStringExtra(Constants.SIMPRINTS_API_KEY))
        module_id.text = getString(R.string.module_id, getIntent().getStringExtra(Constants.SIMPRINTS_MODULE_ID))
        user_id.text = getString(R.string.user_id, getIntent().getStringExtra(Constants.SIMPRINTS_USER_ID))
        metadata.text = getString(R.string.metadata, getIntent().getStringExtra(Constants.SIMPRINTS_METADATA))

        return_to_caller.setOnClickListener {
            setResult(Constants.SIMPRINTS_CANCELLED)//, resultData)
            finish()
        }
    }
}
