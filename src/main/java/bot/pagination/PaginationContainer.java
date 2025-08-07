package bot.pagination;

import java.util.List;

import bot.view.PaginationView;

public class PaginationContainer<T> {

    private PaginationView<T> template;

    private List<T> data;

    private int pageSize;

    private int page;

    public PaginationContainer(PaginationView<T> template, List<T> data, int pageSize){
        this.template = template.setContainer(this);
        this.data = data;
        this.pageSize = pageSize;
        this.page = 0;
    }

    public List<T> getAllData() {
        return data;
    }

    public List<T> getData() {
        if(data.isEmpty())
            return data;
        return data.subList(Math.min(page * pageSize, data.size()), Math.min((page+1) *pageSize, data.size()));   
    }

    public PaginationView<T> getTemplate() {
        return template;
    }

    public int size(){
        return (this.data.size() / pageSize) +1;
    }

    public boolean hasNextPage(){
        return this.page < this.size()-1;
    }

    public void nextPage(){
        if(hasNextPage())
            this.page++;
    }

    public boolean hasPreviousPage(){
        return this.page > 0;
    }

    public void previousPage(){
        if(hasPreviousPage())
            this.page--;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }
}
