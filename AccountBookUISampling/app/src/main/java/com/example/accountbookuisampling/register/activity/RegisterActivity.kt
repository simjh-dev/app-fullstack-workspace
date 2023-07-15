package com.example.accountbookuisampling.register.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.accountbookuisampling.R
import com.example.accountbookuisampling.databinding.ActivityRegisterBinding
import com.example.accountbookuisampling.register.fragment.ConsumptionFragment
import com.example.accountbookuisampling.register.fragment.IncomeFragment
import com.example.accountbookuisampling.register.fragment.TransferFragment
import com.example.accountbookuisampling.util.CONSUMPTION
import com.example.accountbookuisampling.util.INCOME
import com.example.accountbookuisampling.util.TRANSFER
import com.google.android.material.tabs.TabLayout


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 액티비티 시작시 애니메이션
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)

        initTabLayout()
        initFrameLayout()
        setClickEvent()
    }

    private fun initTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                setFragment(tab?.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                setFragment(tab?.position)
            }
        })
    }

    private fun initFrameLayout() {
        setFragment(0)
    }

    private fun setFragment(position: Int?) {
        position?.let {
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = when (it) {
                0 -> {
                    binding.title = INCOME
                    IncomeFragment.getInstance()
                }
                1 -> {
                    binding.title = CONSUMPTION
                    ConsumptionFragment.getInstance()
                }
                2 -> {
                    binding.title = TRANSFER
                    TransferFragment.getInstance()
                }
                else -> throw NotImplementedError()
            }
            transaction.replace(binding.frameLayout.id, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun setClickEvent() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.tvBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // 액티비티 종료시 애니메이션
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out)
    }
}