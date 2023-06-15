package com.example.myapplication

import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentEditarFornecedorBinding


class EditarFornecedorFragment : Fragment() {
    private var fornecedores: Fornecedores?= null
    private var _binding: FragmentEditarFornecedorBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEditarFornecedorBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_guardar_cancelar

        val fornecedores = EditarFornecedorFragmentArgs.fromBundle(requireArguments()).fornecedores

        if (fornecedores != null) {
            binding.editTextNomeFornecedor.setText(fornecedores.nome_fornecedor)
            binding.editTextContactoFornecedor.setText(fornecedores.contacto_fornecedor)

        }

        this.fornecedores = fornecedores
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


        if (fornecedores == null) {
            val fornecedor = Fornecedores(
                nome_fornecedor,
                contacto_fornecedor
            )

            insereFornecedor(fornecedor)
        } else {
            val fornecedor = fornecedores!!
            fornecedor.nome_fornecedor = nome_fornecedor
            fornecedor.contacto_fornecedor = contacto_fornecedor

            alteraFornecedor(fornecedor)
        }
    }

    private fun alteraFornecedor(fornecedores: Fornecedores) {
        val enderecoFornecedor = Uri.withAppendedPath(ProdutoContentProvider.ENDERECO_FORNECEDOR, fornecedores.id.toString())
        val fornecedoresAlterados = requireActivity().contentResolver.update(enderecoFornecedor, fornecedores.toContentValues(), null, null)

        if (fornecedoresAlterados == 1) {
            Toast.makeText(requireContext(), R.string.fornecedor_guardado_com_sucesso, Toast.LENGTH_LONG).show()
            voltaListaFornecedores()
        } else {
            binding.editTextNomeFornecedor.error = getString(R.string.erro_guardar_fornecedor)
        }
    }

    private fun insereFornecedor(
        fornecedores: Fornecedores
    ) {

        val id = requireActivity().contentResolver.insert(
            ProdutoContentProvider.ENDERECO_FORNECEDOR,
            fornecedores.toContentValues()
        )

        if (id == null) {
            binding.editTextNomeFornecedor.error = getString(R.string.erro_guardar_fornecedor)
            return
        }

        Toast.makeText(requireContext(), getString(R.string.fornecedor_guardado_com_sucesso), Toast.LENGTH_SHORT).show()
        voltaListaFornecedores()
    }
}