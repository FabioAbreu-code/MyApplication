package com.example.myapplication
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentEliminarFornecedorBinding
import com.google.android.material.snackbar.Snackbar

class EliminarFornecedorFragment : Fragment() {
    private lateinit var fornecedores: Fornecedores
    private var _binding: FragmentEliminarFornecedorBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEliminarFornecedorBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_eliminar


        fornecedores = EliminarFornecedorFragmentArgs.fromBundle(requireArguments()).fornecedores

        binding.textViewNomeFornecedor.text = fornecedores.nome_fornecedor
        binding.textViewContactoFornecedor.text = fornecedores.contacto_fornecedor

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_eliminar -> {
                eliminar()
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
        findNavController().navigate(R.id.action_eliminarFornecedorFragment_to_listaFornecedoresFragment)
    }

    private fun eliminar() {
        val enderecoFornecedor = Uri.withAppendedPath(ProdutoContentProvider.ENDERECO_FORNECEDOR, fornecedores.id.toString())
        val numFornecedoresEliminados = requireActivity().contentResolver.delete(enderecoFornecedor, null, null)

        if (numFornecedoresEliminados == 1) {
            Toast.makeText(requireContext(), getString(R.string.fornecedor_eliminado_com_sucesso), Toast.LENGTH_LONG).show()
            voltaListaFornecedores()
        } else {
            Snackbar.make(binding.textViewNomeFornecedor, getString(R.string.erro_eliminar_fornecedor), Snackbar.LENGTH_INDEFINITE)
        }
    }
}