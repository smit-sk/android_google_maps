package com.example.workingwithgooglemaps

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val names = listOf(
            "SmitKumar Khokhariya",
            "Dhruvin Mulani",
            "Dhruv Sagpariya",
            "Grishma Dave"
        )

        val formattedList = buildString {
            names.forEach {
                append("&#8226; ") // Unicode for bullet point
                append(it)
                append("<br/>") // New line after each name
            }
        }

        val nameListTextView: TextView =  view.findViewById<TextView>(R.id.nameListTextView)
        nameListTextView.text = Html.fromHtml(formattedList, Html.FROM_HTML_MODE_LEGACY)

        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()

        }
    }
}


