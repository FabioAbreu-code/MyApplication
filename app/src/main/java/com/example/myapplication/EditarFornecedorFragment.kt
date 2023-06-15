package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentEditarFornecedorBinding


class EditarFornecedorFragment : Fragment() {
    private var _binding: FragmentEditarFornecedorBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditarFornecedorBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_guardar_cancelar
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_guardar -> {
                guardar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaFornecedores()
                true
            }
            else -> false
        }
    }

    private fun voltaListaFornecedores() {
        findNavController().navigate(R.id.action_editarFornecedorFragment_to_listaFornecedoresFragment)
    }

    private fun guardar() {
        val nome_fornecedor = binding.editTextNomeFornecedor.text.toString()
        if (nome_fornecedor.isBlank()) {
            binding.editTextNomeFornecedor.error = getString(R.string.nome_do_fornecedor_obrigatorio)
            binding.editTextNomeFornecedor.requestFocus()
            return
        }

        val contacto_fornecedor = binding.editTextContactoFornecedor.text.toString()
        if (contacto_fornecedor.isBlank()) {
            binding.editTextContactoFornecedor.error = getString(R.string.contacto_fornecedor_obrigatorio)
            binding.editTextContactoFornecedor.requestFocus()
            return
        }


        val fornecedor = Fornecedores(
            nome_fornecedor,
            contacto_fornecedor
        )

        val id = requireActivity().contentResolver.insert(
            ProdutoContentProvider.ENDERECO_FORNECEDOR,
            fornecedor.toContentValues()
        )

        if (id == null) {
            binding.editTextNomeFornecedor.error = getString(R.string.erro_guardar_fornecedor)
            return
        }

        Toast.makeText(requireContext(), getString(R.string.fornecedor_guardado_com_sucesso), Toast.LENGTH_SHORT).show()
        voltaListaFornecedores()
    }
}