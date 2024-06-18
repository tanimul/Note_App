package org.tanimul.notes.ui.activities

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import org.tanimul.notes.base.BaseActivity
import org.tanimul.notes.databinding.ActivityHomeBinding

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override fun getViewBinding(): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun init(savedInstanceState: Bundle?) {
    }


}