package com.cosart.github.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.cosart.github.R
import com.cosart.github.data.Repository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        intent.extras?.apply {
            repository = getParcelable(EXTRA_REPOSITORY)

            Picasso.get()
                    .load(repository.owner.avatar_url)
                    .into(avatar)

            name.text = repository.full_name
            owner.text = repository.owner.login
            stars.text = getString(R.string.detail_star_gazers, repository.stargazers_count.toString())
            score.text = getString(R.string.detail_score, repository.score.toString())
            description.text = repository.description
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}

const val EXTRA_REPOSITORY = "EXTRA_REPOSITORY"