package io.github.limaechocharlie.mockid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.simprints.libsimprints.Constants
import kotlinx.android.synthetic.main.activity_identify.*


class IdentifyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_identify)

        var apiKey = getIntent().getStringExtra(Constants.SIMPRINTS_API_KEY)
        var moduleId = getIntent().getStringExtra(Constants.SIMPRINTS_MODULE_ID)
        var userId = getIntent().getStringExtra(Constants.SIMPRINTS_USER_ID)
        var metadata = getIntent().getStringExtra(Constants.SIMPRINTS_METADATA)

        view_api_key.text = getString(R.string.api_key, apiKey)
        view_module_id.text = getString(R.string.module_id, moduleId)
        view_user_id.text = getString(R.string.user_id, userId)
        view_metadata.text = getString(R.string.metadata, metadata)

        if( checkInputs(apiKey, moduleId, userId, metadata) )
            view_vailid_input.setImageResource(R.drawable.ic_check_circle_black)


        return_to_caller.setOnClickListener {
            //val resultData = Intent(Constants.SIMPRINTS_IDENTIFY_INTENT)
            //resultData.putExtra(Constants.SIMPRINTS_REGISTRATION, registrationResult)
            setResult(Constants.SIMPRINTS_CANCELLED)//, resultData)
            finish()
        }
    }

    fun checkInputs(apiKey:String?, moduleId:String?, userId:String?, metaData:String? ):Boolean{
        return apiKey != null && moduleId != null && userId != null
    }
}
