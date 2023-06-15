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
import com.example.myapplication.databinding.FragmentEliminarProdutoBinding
import com.google.android.material.snackbar.Snackbar

class EliminarProdutoFragment : Fragment() {
    private lateinit var produtos2: Produtos2
    private var _binding: FragmentEliminarProdutoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEliminarProdutoBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_eliminar


        produtos2 = EliminarProdutoFragmentArgs.fromBundle(requireArguments()).produto2

        binding.textViewNomeProduto.text = produtos2.nome_produto
        binding.textViewDescricaoProduto.text = produtos2.desc_produto
        binding.textViewNomeFornecedor.text = produtos2.id_fornecedor.nome_fornecedor

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
                voltaListaLivros()
                true
            }
            else -> false
        }
    }

    private fun voltaListaLivros() {
        findNavController().navigate(R.id.action_eliminarProdutoFragment_to_listaProdutosFragment)
    }

    private fun eliminar() {
        val enderecoProduto = Uri.withAppendedPath(ProdutoContentProvider.ENDERECO_PRODUTO2, produtos2.id.toString())
        val numProdutosEliminados = requireActivity().contentResolver.delete(enderecoProduto, null, null)

        if (numProdutosEliminados == 1) {
            Toast.makeText(requireContext(), getString(R.string.producto_eliminado_com_sucesso), Toast.LENGTH_LONG).show()
            voltaListaLivros()
        } else {
            Snackbar.make(binding.textViewNomeProduto, getString(R.string.erro_eliminar_produto), Snackbar.LENGTH_INDEFINITE)
        }
    }
}