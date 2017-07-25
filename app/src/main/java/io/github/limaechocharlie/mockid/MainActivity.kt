package io.github.limaechocharlie.mockid

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.os.Parcelable
import android.widget.ArrayAdapter
import com.simprints.libsimprints.Constants
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private var simprintsIntent = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter.createFromResource(this,
                R.array.intents_array, android.R.layout.simple_spinner_item)
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spinner_intent.adapter = adapter

        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        edit_api_key.setText( sharedPref.getString(getString(R.string.pref_api_key),""))
        edit_module_id.setText( sharedPref.getString(getString(R.string.pref_module_id_key),""))
        edit_username.setText( sharedPref.getString(getString(R.string.pref_username_key),""))


        call_simprints.setOnClickListener {
            val apiKey = edit_api_key.editableText.toString()
            val moduleId = edit_module_id.editableText.toString()
            val username = edit_username.editableText.toString()
            val spinnerValue = spinner_intent.selectedItem.toString()

            val editor = sharedPref.edit()
            editor.putString(getString(R.string.pref_api_key), apiKey)
            editor.putString(getString(R.string.pref_module_id_key), moduleId)
            editor.putString(getString(R.string.pref_username_key), username)
            editor.commit()

            simprintsIntent= when(spinnerValue){
                "Register"->Constants.SIMPRINTS_REGISTER_INTENT
                "Identify"->Constants.SIMPRINTS_IDENTIFY_INTENT
                "Verify"->Constants.SIMPRINTS_VERIFY_INTENT
                else->throw IllegalArgumentException()
            }


            // build intent
            val intent = Intent(simprintsIntent)
            intent.putExtra(Constants.SIMPRINTS_API_KEY, apiKey)
            intent.putExtra(Constants.SIMPRINTS_USER_ID, username)
            intent.putExtra(Constants.SIMPRINTS_MODULE_ID, moduleId)
            intent.putExtra("resultFormat", "ODKv01")


            startActivityForResult(intent, 1) // 1 is your requestCode for the callback
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Constants.SIMPRINTS_OK && data?.extras != null) {
            view_guid_return.text = data.extras.getString("guid", "null")
            view_confidence_return.text = data.extras.getString("confidence", "null")
            view_tier_return.text = data.extras.getString("tier", "null")
        }
        else {
            val errorMsg = when( resultCode ){
                Constants.SIMPRINTS_OK->R.string.error_missing_intent
                Constants.SIMPRINTS_CANCELLED->R.string.error_cancelled
                Constants.SIMPRINTS_MISSING_API_KEY->R.string.error_missing_api_key
                Constants.SIMPRINTS_INVALID_API_KEY->R.string.error_invalid_api_key
                Constants.SIMPRINTS_MISSING_USER_ID->R.string.error_missing_user_id
                Constants.SIMPRINTS_MISSING_MODULE_ID->R.string.error_missing_module_id
                Constants.SIMPRINTS_INVALID_INTENT_ACTION->R.string.error_invalid_intent
                Constants.SIMPRINTS_INVALID_UPDATE_GUID->R.string.error_invalid_update_guid
                Constants.SIMPRINTS_MISSING_UPDATE_GUID->R.string.error_missing_update_guid
                Constants.SIMPRINTS_MISSING_VERIFY_GUID->R.string.error_missing_verify_guid
                Constants.SIMPRINTS_INVALID_METADATA->R.string.error_invalid_metadata
                Constants.SIMPRINTS_VERIFY_GUID_NOT_FOUND_ONLINE->R.string.error_verify_guid_not_found_online
                Constants.SIMPRINTS_VERIFY_GUID_NOT_FOUND_OFFLINE->R.string.error_verify_guid_not_found_offline
                else->throw IllegalArgumentException()
            }
            Toast.makeText(applicationContext,errorMsg,10).show()
        }
    }
}
