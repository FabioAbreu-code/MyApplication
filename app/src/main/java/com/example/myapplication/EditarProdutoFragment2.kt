package com.example.myapplication

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentEditarProduto2Binding



private const val ID_LOADER_FORNECEDORES = 0

class EditarProdutoFragment2 : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentEditarProduto2Binding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditarProduto2Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loader = LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_FORNECEDORES, null, this)

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
                voltaListaProdutos()
                true
            }
            else -> false
        }
    }

    private fun voltaListaProdutos() {
        findNavController().navigate(R.id.action_novoProdutoFragment2_to_listaProdutosFragment)
    }

    private fun guardar() {
        val nome_produto = binding.editTextNomeProduto.text.toString()
        if (nome_produto.isBlank()) {
            binding.editTextNomeProduto.error = getString(R.string.nome_produto_obrigatorio)
            binding.editTextNomeProduto.requestFocus()
            return
        }

        val desc_produto = binding.editTextDesProduto.text.toString()
        if (desc_produto.isBlank()) {
            binding.editTextDesProduto.error = getString(R.string.desc_produto_obrigatorio)
            binding.editTextDesProduto.requestFocus()
            return
        }

        val fornecedorId = binding.spinnerFornecedores.selectedItemId


        val produto = Produtos2(
            nome_produto,
            desc_produto,
            Fornecedores("?","?", fornecedorId),

        )

        val id = requireActivity().contentResolver.insert(
            ProdutoContentProvider.ENDERECO_PRODUTO2,
            produto.toContentValues()
        )

        if (id == null) {
            binding.editTextNomeProduto.error = getString(R.string.erro_guardar_produto)
            return
        }

        Toast.makeText(requireContext(), getString(R.string.produto_guardado_com_sucesso), Toast.LENGTH_SHORT).show()
        voltaListaProdutos()
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            ProdutoContentProvider.ENDERECO_FORNECEDOR,
            TabelaFornecedores.CAMPOS,
            null, null,
            TabelaFornecedores.NOME_FORNECEDOR
        )
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    override fun onLoaderReset(loader: Loader<Cursor>) {
        binding.spinnerFornecedores.adapter = null
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is *not* allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See [ FragmentManager.openTransaction()][androidx.fragment.app.FragmentManager.beginTransaction] for further discussion on this.
     *
     *
     * This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     *
     *  *
     *
     *The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a [android.database.Cursor]
     * and you place it in a [android.widget.CursorAdapter], use
     * the [android.widget.CursorAdapter.CursorAdapter] constructor *without* passing
     * in either [android.widget.CursorAdapter.FLAG_AUTO_REQUERY]
     * or [android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER]
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     *  *  The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a [android.database.Cursor] from a [android.content.CursorLoader],
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * [android.widget.CursorAdapter], you should use the
     * [android.widget.CursorAdapter.swapCursor]
     * method so that the old Cursor is not closed.
     *
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        if (data == null) {
            binding.spinnerFornecedores.adapter = null
            return
        }

        binding.spinnerFornecedores.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaFornecedores.NOME_FORNECEDOR),
            intArrayOf(android.R.id.text1),
            0
        )
    }
}