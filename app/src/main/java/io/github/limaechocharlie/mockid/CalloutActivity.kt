package io.github.limaechocharlie.mockid

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.simprints.libsimprints.Constants
import com.simprints.libsimprints.Registration
import kotlinx.android.synthetic.main.activity_callout.*
import java.util.*


class CalloutActivity : AppCompatActivity() {

    private var registerFragment: RegisterFragment? = null
    private var identifyFragment: IdentifyFragment? = null
    private var verifyFragment: VerifyFragment? = null

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)
        setContentView(R.layout.activity_callout)

        val apiKey = intent.getStringExtra(Constants.SIMPRINTS_API_KEY)
        val moduleId = intent.getStringExtra(Constants.SIMPRINTS_MODULE_ID)
        val userId = intent.getStringExtra(Constants.SIMPRINTS_USER_ID)
        val metadata = intent.getStringExtra(Constants.SIMPRINTS_METADATA)
        val intentSent = intent.action

        if( checkInputs(apiKey, moduleId, userId, metadata, intentSent) )
            view_valid_input.setImageResource(R.drawable.ic_check_circle_black)

        view_api_key.text = getString(R.string.api_key, apiKey)
        view_module_id.text = getString(R.string.module_id, moduleId)
        view_user_id.text = getString(R.string.user_id, userId)
        view_metadata.text = getString(R.string.metadata, metadata)
        view_intent.text = getString(R.string.intent, intentSent)


        when (intentSent) {
            Constants.SIMPRINTS_REGISTER_INTENT -> {
                return_to_caller.setOnClickListener {
                    val guid = UUID.randomUUID().toString()
                    val registrationResult = Registration( guid )
                    val resultData = Intent(Constants.SIMPRINTS_REGISTER_INTENT)
                    resultData.putExtra(Constants.SIMPRINTS_REGISTRATION, registrationResult)
                    setResult(Constants.SIMPRINTS_OK, resultData)
                    finish()
                }

                registerFragment = Fragment.instantiate(this@CalloutActivity,
                        RegisterFragment::class.java.name) as RegisterFragment

                supportFragmentManager.beginTransaction().add(R.id.flCalloutFragment, registerFragment).commit()
            }
            Constants.SIMPRINTS_IDENTIFY_INTENT -> {
                // TODO
                return_to_caller.setOnClickListener {
                    setResult(Constants.SIMPRINTS_CANCELLED)//, resultData)
                    finish()
                }

                identifyFragment = Fragment.instantiate(this@CalloutActivity,
                        IdentifyFragment::class.java.name) as IdentifyFragment

                supportFragmentManager.beginTransaction().add(R.id.flCalloutFragment, identifyFragment).commit()
            }
            Constants.SIMPRINTS_VERIFY_INTENT -> {
                // TODO
                return_to_caller.setOnClickListener {
                    setResult(Constants.SIMPRINTS_CANCELLED)//, resultData)
                    finish()
                }

                verifyFragment = Fragment.instantiate(this@CalloutActivity,
                        VerifyFragment::class.java.name) as VerifyFragment

                supportFragmentManager.beginTransaction().add(R.id.flCalloutFragment, verifyFragment).commit()
            }
        }


    }

    fun checkInputs(apiKey:String?, moduleId:String?, userId:String?, metaData:String?, intentSent:String? ):Boolean{
        return apiKey != null && moduleId != null && userId != null && intentSent != null
    }
}