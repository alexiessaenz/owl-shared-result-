package com.naldana.owltrivia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.naldana.owltrivia.databinding.FragmentGameBinding

/**
 * A simple [Fragment] subclass.
 */
class GameFragment : Fragment() {

    data class Question(
        val text: String,
        val answers: List<String>
    )

    private val questions: MutableList<Question> = mutableListOf(
        Question(
            text = "¿Cuantos grados pueden girar su cabeza los buhos?",
            answers = listOf("270", "180", "90", "360")
        ),
        Question(
            text = "¿Cómo se le llama a un grupo de buhos?",
            answers = listOf("Parlamento", "Manada", "Tribu", "Camada")
        ),
        Question(
            text = "Los buhos pueden llegar a comer otras aves pequeñas",
            answers = listOf("Si", "Solo aves grandes", "No comen aves", "Son frutiveros")
        ),
        Question(
            text = "¿ A cúal mochuelo (bebé) los buhos alimentan primero? ",
            answers = listOf(
                "Al mayor y fuerte",
                "A todos por igual",
                "A ninguno, aprenden a cazar casi despúes que nacen",
                "Ninguna es correcta"
            )
        ),
        Question(
            text = "¿Por qué los buhos son tan buenos cazadores?",
            answers = listOf(
                "Su vuelo sumamente silencioso",
                "Su vuelo sumamente silencioso",
                "Su rapidez de vuelo",
                "Su perfecta coordinacion"
            )
        )
    )

    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 3)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
            inflater, R.layout.fragment_game, container, false
        )

        // Shuffles
        randomizeQuestions()

        // Bind data
        binding.game = this

        // set Click
        binding.submitButton.setOnClickListener { view: View ->
            val checkId = binding.radioGroupQuestion.checkedRadioButtonId

            if (-1 != checkId) {
                var answerIndex = 0
                when (checkId) {
                    R.id.radioOptionB -> answerIndex = 1
                    R.id.radioOptionC -> answerIndex = 2
                    R.id.radioOptionD -> answerIndex = 3
                }

                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    questionIndex++

                    if (questionIndex < numQuestions) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                        binding.radioGroupQuestion.clearCheck()
                        binding.invalidateAll()
                    } else {
                        //view.findNavController()
                            //.navigate(R.id.action_gameFragment_to_gameWonFragment)
                        view.findNavController()
                            .navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(numQuestions,questionIndex)) //with safe arguments
                    }
                } else {
                    //view.findNavController()
                      //  .navigate(R.id.action_gameFragment_to_gameOverFragment)
                    view.findNavController()
                        .navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment()) //with safe arguments
                }
            }
        }

        return binding.root
    }

    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    private fun setQuestion() {
        currentQuestion = questions[questionIndex]

        answers = currentQuestion.answers.toMutableList()

        answers.shuffle()

        (activity as AppCompatActivity)
            .supportActionBar?.title = getString(
            R.string.title_owl_trivia_question,
            questionIndex + 1, numQuestions
        )
    }

}
