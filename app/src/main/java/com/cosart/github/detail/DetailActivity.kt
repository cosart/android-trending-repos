package com.cosart.github.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cosart.github.R
import com.cosart.github.data.RepositoryData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var repositoryData: RepositoryData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        intent.extras?.apply {
            repositoryData = getParcelable(EXTRA_REPOSITORY) ?: return

            Picasso.get()
                    .load(repositoryData.owner.avatar_url)
                    .into(avatar)

            name.text = repositoryData.full_name
            owner.text = repositoryData.owner.login
            stars.text = getString(R.string.detail_star_gazers, repositoryData.stargazers_count.toString())
            score.text = getString(R.string.detail_score, repositoryData.score.toString())
            description.text = repositoryData.description
        }
    }
}

const val EXTRA_REPOSITORY = "EXTRA_REPOSITORY"