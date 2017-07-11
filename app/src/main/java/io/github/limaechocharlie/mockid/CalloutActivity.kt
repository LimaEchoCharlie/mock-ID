package io.github.limaechocharlie.mockid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity


class CalloutActivity : AppCompatActivity() {

    private var registerFragment: RegisterFragment? = null

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState) //cual es la diferencia con savedInstanceState
        setContentView(R.layout.activity_callout)

        registerFragment = Fragment.instantiate(this@CalloutActivity,
                RegisterFragment::class.java.name) as RegisterFragment

        supportFragmentManager.beginTransaction().replace(R.id.flCalloutFragment, registerFragment).commit()
    }
}