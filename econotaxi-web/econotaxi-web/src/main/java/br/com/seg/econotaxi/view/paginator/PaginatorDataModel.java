package br.com.seg.econotaxi.view.paginator;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * Classe abstrata que deve ser herdada por todas as classes de paginação do sistema.
 *
 * Criado em 7 de jun de 2017
 * @author Wellington Carvalho
 */
public abstract class PaginatorDataModel<T> extends LazyDataModel<T> {

    // Constantes
	private static final long serialVersionUID = 906062402345473880L;
	private static final int INITIAL_PAGE_SIZE = 10;
    protected static final int INITIAL_PAGE = 0;

    // Atributos
    private transient List<T> lista;

    /**
     * Construtor da classe.
     */
    public PaginatorDataModel() {
        super();
        setPageSize(INITIAL_PAGE_SIZE);
    }

    /**
     * Método responsável por realizar a consulta.
     */
    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        setPageSize(pageSize);
        setRowCount(obterRowCount().intValue());

        if(getRowCount() > 0) {
            lista = obterListResult(first, pageSize, sortField);
        }

        return lista;
    }

    protected abstract Long obterRowCount();
    protected abstract List<T> obterListResult(int first, int pageSize, String sortField);

    // Métodos get/set
    @Override
    public void setRowIndex( int rowIndex ) {
        if ( rowIndex == -1 || getPageSize() == 0 ) {
            super.setRowIndex( -1 );
        } else
            super.setRowIndex( rowIndex % getPageSize() );
    }
    
    @Override
    public Object getRowKey(T object) {
        return getCodigo(object);
    }
    
    @Override
    public T getRowData(String rowKey) {
        T object = null;
        for(T t : lista) {
            if(getCodigo(t).equals(rowKey)) {
                object = t;
            }
        }
        return object;
    }
    
    protected abstract Object getCodigo(T object);

}