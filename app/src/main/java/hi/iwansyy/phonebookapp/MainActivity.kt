package hi.iwansyy.phonebookapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import hi.iwansyy.phonebookapp.databinding.ActivityMainBinding
import hi.iwansyy.phonebookapp.model.PageModel
import hi.iwansyy.phonebookapp.view.adapter.PagerAdapter
import hi.iwansyy.phonebookapp.view.fragment.FavoriteFragment
import hi.iwansyy.phonebookapp.view.fragment.TodosFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

    }
}