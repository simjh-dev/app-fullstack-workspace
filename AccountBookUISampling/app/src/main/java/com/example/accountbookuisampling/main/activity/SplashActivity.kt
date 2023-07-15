package com.example.accountbookuisampling.main.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.accountbookuisampling.application.BaseApplication
import com.example.accountbookuisampling.databinding.ActivitySplashBinding
import com.example.accountbookuisampling.room.entity.Asset
import com.example.accountbookuisampling.room.entity.Category
import com.example.accountbookuisampling.room.entity.History
import com.example.accountbookuisampling.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.roundToInt
import kotlin.random.Random

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val preferences = getSharedPreferences(TEXT_INIT, Context.MODE_PRIVATE)
        val isInit = preferences.getBoolean(TEXT_INIT, false)
        if (isInit) startMainActivity()
        else {
            CoroutineScope(Dispatchers.IO).launch {
                (application as BaseApplication).assetDao.insertAll(getInitAssetList())
                (application as BaseApplication).categoryDao.insertAll(getInitCategoryList())
                val assetSize = (application as BaseApplication).assetDao.getSize()
                val categorySize = (application as BaseApplication).categoryDao.getSize()
                (application as BaseApplication).historyDao.insertAll(
                    getInitHistoryList(
                        assetSize,
                        categorySize
                    )
                )
                CoroutineScope(Dispatchers.Main).launch {
                    preferences.edit().putBoolean(TEXT_INIT, true).apply()
                    startMainActivity()
                }
            }
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun getInitAssetList(): List<Asset> {
        val list = ArrayList<Asset>()

        var count = 0
        for (name in INIT_INCOME_ASSETS) {
            val item = Asset(
                0,
                0,
                name,
                Random.nextInt(0, 100000),
                if (Math.random() > 0.5) "memo$count" else null
            )
            count++
            list.add(item)
        }

        for (name in INIT_CONSUMPTION_ASSETS) {
            val item = Asset(
                0,
                1,
                name,
                -1,
                if (Math.random() > 0.5) "memo$count" else null
            )
            count++
            list.add(item)
        }

        for (name in INIT_TRANSFER_ASSETS) {
            val item = Asset(
                0,
                2,
                name,
                -1,
                if (Math.random() > 0.5) "memo$count" else null
            )
            count++
            list.add(item)
        }

        return list.toList()
    }

    private fun getInitCategoryList(): List<Category> {
        val list = ArrayList<Category>()

        var count = 0
        for (name in INIT_INCOME_CATEGORIES) {
            val item = Category(
                0,
                0,
                name,
                if (Math.random() > 0.5) "memo$count" else null
            )
            count++
            list.add(item)
        }

        for (name in INIT_CONSUMPTION_CATEGORIES) {
            val item = Category(
                0,
                1,
                name,
                if (Math.random() > 0.5) "memo$count" else null
            )
            count++
            list.add(item)
        }

        for (name in INIT_TRANSFER_CATEGORIES) {
            val item = Category(
                0,
                2,
                name,
                if (Math.random() > 0.5) "memo$count" else null
            )
            count++
            list.add(item)
        }

        return list.toList()
    }

    private fun getInitHistoryList(assetSize: Int, categorySize: Int): List<History> {
        val list = ArrayList<History>()
        for (i in 0..10000) {
            val id = i + 1
            val date = getRandomDate()
            // 0:income, 1:consumption, 2:transfer
            val type = i % 3

            val assetId = randBetween(1, assetSize)
            val assetName = (application as BaseApplication).assetDao.getNameByid(assetId)

            val categoryId = randBetween(1, categorySize)
            val categoryName = (application as BaseApplication).categoryDao.getNameByid(categoryId)

            val amount = Random.nextInt(100, 10000)
            val fee = if (i % 2 == 2) Random.nextInt(0, 1000) else 0
            val detail = if (i % 3 == 0) null else "detail${String.format("%02d", i)}"
            val important = Math.random() < 0.3

            val item = History(
                id,
                type,
                date,
                assetId,
                assetName,
                categoryId,
                categoryName,
                amount,
                fee,
                detail,
                important,
                null,
                null
            )

            list.add(item)
        }
        return list.toList()
    }

    private fun getRandomDate(): String {
        val cal = Calendar.getInstance()

        val year = randBetween(2020, cal.get(Calendar.YEAR))
        cal.set(Calendar.YEAR, year)
        val dayOfYear = randBetween(1, cal.getActualMaximum(Calendar.DAY_OF_YEAR))
        cal.set(Calendar.DAY_OF_YEAR, dayOfYear)

        val yyyy = cal.get(Calendar.YEAR)
        val MM = String.format(
            "%02d",
            cal.get(Calendar.MONTH) + 1
        )
        val dd = String.format("%02d", cal.get(Calendar.DAY_OF_MONTH))

        val hh = String.format("%02d", cal.get(Calendar.HOUR_OF_DAY))
        val mm = String.format("%02d", cal.get(Calendar.MINUTE))

        return "$yyyy$MM$dd$hh$mm"
    }

    private fun randBetween(start: Int, end: Int): Int {
        return start + (Math.random() * (end - start)).roundToInt()
    }
}