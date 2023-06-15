package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentListaFornecedoresBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ListaFornecedoresFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaFornecedoresFragment : Fragment() {
    private var _binding: FragmentListaFornecedoresBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_fornecedores, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapterFornecedores = AdapterFornecedores()
        binding.recyclerViewFornecedores.adapter = adapterFornecedores
        binding.recyclerViewFornecedores.layoutManager = LinearLayoutManager(requireContext())
    }

    companion object {

    }
}