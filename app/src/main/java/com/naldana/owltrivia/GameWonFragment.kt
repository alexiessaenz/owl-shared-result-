package com.naldana.owltrivia

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.naldana.owltrivia.databinding.FragmentGameWonBinding

/**
 * A simple [Fragment] subclass.
 */
class GameWonFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGameWonBinding>(
            inflater, R.layout.fragment_game_won, container, false
        )

        binding.nextMatchButton.setOnClickListener {view: View ->
            view.findNavController()
                //.navigate(R.id.action_gameWonFragment_to_gameFragment)
                .navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
        }
        val args = GameWonFragmentArgs.fromBundle(arguments!!)
        //Toast.makeText(context, "NumCorrect: ${args.numCorrect}, NumQuestions: ${args.numQuestions}", Toast.LENGTH_SHORT).show()
        Snackbar.make((container as View),
            "NumCorrect: ${args.numCorrect}," +
                    " NumQuestions: ${args.numQuestions}",
            Snackbar.LENGTH_LONG).setAction("OK"){
            Log.d("GameWonFragment","SnackBar")
        }.show()

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun getShareIntent(): Intent {
        val args = GameWonFragmentArgs.fromBundle(arguments!!)
        //val shareIntent = Intent(Intent.ACTION_SEND)
        //shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT,"NumCorrect: ${args.numCorrect}," +
                //" NumQuestions: ${args.numQuestions}")
        //return shareIntent
        return Intent(Intent(Intent.ACTION_SEND)).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT," NumQuestions: ${args.numQuestions}"+
                    " NumQuestions: ${args.numQuestions}")
        }
    }
}
